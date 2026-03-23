package com.exadmax.pointtester.presence;

import java.util.Locale;

public enum PresenceMode {
	PREVENT_IDLE,
	MICRO_JIGGLE,
	DISABLED;

	public static PresenceMode fromProperty(String value) {
		if (value == null || value.isBlank()) {
			return PREVENT_IDLE;
		}

		String normalized = value.trim().toUpperCase(Locale.ROOT).replace('-', '_');
		return switch (normalized) {
			case "PREVENT_IDLE" -> PREVENT_IDLE;
			case "MICRO_JIGGLE" -> MICRO_JIGGLE;
			case "DISABLED" -> DISABLED;
			default -> PREVENT_IDLE;
		};
	}
}

