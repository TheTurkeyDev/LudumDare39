package com.theprogrammingturkey.ld39.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.theprogrammingturkey.ld39.LD39Core;

public class DesktopLauncher
{
	public static void main(String[] arg)
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "LudumDare 39";
		config.width = 1280;
		config.height = 720;
		config.resizable = false;
		new LwjglApplication(new LD39Core(), config);
	}
}
