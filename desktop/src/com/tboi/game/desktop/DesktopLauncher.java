package com.tboi.game.desktop;

import com.tboi.game.settings.DesktopSettings;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tboi.game.TBOIGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = DesktopSettings.HEIGHT;
		config.width = DesktopSettings.WIDTH;
		config.resizable = DesktopSettings.RESIZE;
		new LwjglApplication(new TBOIGame(), config);
	}
}
