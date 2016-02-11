package com.subzero.runners.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.subzero.runners.Runners;
import com.subzero.services.DesktopGoogleServices;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Runners";
		config.width = 854; // 180/960 = 0.1875
		config.height = 480; // 120/640 = 0.1875
		new LwjglApplication(new Runners(new DesktopGoogleServices()), config);
	}
}
