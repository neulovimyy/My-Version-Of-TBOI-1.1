package com.tboi.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tboi.game.TBOIGame;
import com.tboi.game.settings.GameControlSetting;

public class DesktopLauncher {
	public static void main (String[] arg) {
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = GameControlSetting.HEIGHT;
		config.width = GameControlSetting.WIDTH;
		config.resizable = GameControlSetting.RESIZABLE;
		new LwjglApplication(new TBOIGame(), config);
	}
}
