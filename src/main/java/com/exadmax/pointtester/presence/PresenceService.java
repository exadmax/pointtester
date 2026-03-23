package com.exadmax.pointtester.presence;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PresenceService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PresenceService.class);

	private final WindowsIdleInhibitor idleInhibitor = new WindowsIdleInhibitor();
	private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

	@Value("${pointtester.mode:prevent-idle}")
	private String modeProperty;

	@Value("${pointtester.interval-ms:20000}")
	private long intervalMs;

	@Value("${pointtester.mouse-jiggle-pixels:1}")
	private int jigglePixels;

	private Robot robot;
	private PresenceMode mode;

	@PostConstruct
	public void start() {
		mode = PresenceMode.fromProperty(modeProperty);
		if (mode == PresenceMode.DISABLED) {
			LOGGER.info("Presence mode disabled.");
			return;
		}

		if (intervalMs < 1000) {
			intervalMs = 1000;
		}

		scheduler.scheduleAtFixedRate(this::safeTick, 0, intervalMs, TimeUnit.MILLISECONDS);
		LOGGER.info("Presence mode={} intervalMs={} jigglePixels={}", mode, intervalMs, jigglePixels);
	}

	@PreDestroy
	public void stop() {
		scheduler.shutdownNow();
	}

	private void safeTick() {
		try {
			tick();
		} catch (Exception ex) {
			LOGGER.warn("Presence tick failed: {}", ex.getMessage());
		}
	}

	private void tick() throws AWTException {
		if (mode == PresenceMode.PREVENT_IDLE) {
			boolean inhibited = idleInhibitor.keepSystemAwake();
			if (inhibited) {
				return;
			}

			LOGGER.debug("Idle inhibition unavailable; falling back to micro-jiggle.");
			microJiggle();
			return;
		}

		if (mode == PresenceMode.MICRO_JIGGLE) {
			microJiggle();
		}
	}

	private void microJiggle() throws AWTException {
		if (MouseInfo.getPointerInfo() == null) {
			return;
		}

		if (robot == null) {
			robot = new Robot();
			robot.setAutoDelay(10);
		}

		Point current = MouseInfo.getPointerInfo().getLocation();
		int x = current.x;
		int y = current.y;

		robot.mouseMove(x + jigglePixels, y);
		robot.mouseMove(x, y);
	}
}

