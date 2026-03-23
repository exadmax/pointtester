package com.exadmax.pointtester.presence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PresenceModeTests {

	@Test
	void parsesKnownValues() {
		assertEquals(PresenceMode.PREVENT_IDLE, PresenceMode.fromProperty("prevent-idle"));
		assertEquals(PresenceMode.MICRO_JIGGLE, PresenceMode.fromProperty("micro_jiggle"));
		assertEquals(PresenceMode.DISABLED, PresenceMode.fromProperty("DISABLED"));
	}

	@Test
	void fallsBackToDefaultForUnknownValue() {
		assertEquals(PresenceMode.PREVENT_IDLE, PresenceMode.fromProperty("unknown"));
		assertEquals(PresenceMode.PREVENT_IDLE, PresenceMode.fromProperty(null));
	}
}

