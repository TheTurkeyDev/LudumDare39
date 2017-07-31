package com.theprogrammingturkey.ld39;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.theprogrammingturkey.ld39.screens.GameOverScreen;
import com.theprogrammingturkey.ld39.screens.GameScreen;
import com.theprogrammingturkey.ld39.screens.HelpScreen;
import com.theprogrammingturkey.ld39.screens.MainScreen;
import com.theprogrammingturkey.ld39.screens.ScreenManager;
import com.theprogrammingturkey.ld39.screens.SettingsScreen;
import com.theprogrammingturkey.ld39.sounds.SoundManager;

public class LD39Core extends ApplicationAdapter
{

	@Override
	public void create()
	{
		ScreenManager.INSTANCE.addScreen(new GameScreen());
		ScreenManager.INSTANCE.addScreen(new MainScreen());
		ScreenManager.INSTANCE.addScreen(new HelpScreen());
		ScreenManager.INSTANCE.addScreen(new SettingsScreen());
		ScreenManager.INSTANCE.addScreen(new GameOverScreen());

		ScreenManager.INSTANCE.setCurrentScreen("Main_Screen");
	}

	@Override
	public void render()
	{
		ScreenManager.INSTANCE.updateScreen();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		ScreenManager.INSTANCE.renderScreen();
	}

	@Override
	public void dispose()
	{
		SoundManager.disposeSounds();
	}
}
