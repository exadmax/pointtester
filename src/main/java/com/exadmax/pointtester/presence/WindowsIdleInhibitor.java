package com.exadmax.pointtester.presence;

import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;

public class WindowsIdleInhibitor {
	private static final int ES_SYSTEM_REQUIRED = 0x00000001;
	private static final int ES_DISPLAY_REQUIRED = 0x00000002;
	private static final int ES_CONTINUOUS = 0x80000000;

	private static final String WINDOWS_PREFIX = "windows";

	interface Kernel32 extends StdCallLibrary {
		Kernel32 INSTANCE = Native.load("kernel32", Kernel32.class);

		int SetThreadExecutionState(int executionState);
	}

	public boolean keepSystemAwake() {
		if (!isWindows()) {
			return false;
		}

		int state = ES_CONTINUOUS | ES_SYSTEM_REQUIRED | ES_DISPLAY_REQUIRED;
		int result = Kernel32.INSTANCE.SetThreadExecutionState(state);
		return result != 0;
	}

	private boolean isWindows() {
		String osName = System.getProperty("os.name", "").toLowerCase();
		return osName.startsWith(WINDOWS_PREFIX);
	}
}

