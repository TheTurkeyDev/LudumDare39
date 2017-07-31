package com.theprogrammingturkey.ld39.screens;

import com.badlogic.gdx.Gdx;
import com.theprogrammingturkey.ld39.graphics.Draw2D;
import com.theprogrammingturkey.ld39.graphics.Resources;
import com.theprogrammingturkey.ld39.gui.GuiButton;
import com.theprogrammingturkey.ld39.gui.GuiComponent;

public class MainScreen extends Screen
{

	public MainScreen()
	{
		super("Main_Screen");
		this.addGuiComponent(new GuiButton(0, 400, 200, 200, 50, "Start", Resources.BUTTON));
		this.addGuiComponent(new GuiButton(1, Gdx.graphics.getWidth() - 600, 200, 200, 50, "Help", Resources.BUTTON));
		this.addGuiComponent(new GuiButton(2, 550, 100, 200, 50, "Settings", Resources.BUTTON));
		// this.addGuiComponent(new GuiButton(3, Gdx.graphics.getWidth() - 500, 0, 400, 200, "Scores", Resources.BUTTON));
	}

	public void onComponentClicked(GuiComponent guic)
	{
		if(guic.getId() == 0)
			ScreenManager.INSTANCE.setCurrentScreen("Game_Screen");
		else if(guic.getId() == 1)
			ScreenManager.INSTANCE.setCurrentScreen("Help_Screen");
		else if(guic.getId() == 2)
			ScreenManager.INSTANCE.setCurrentScreen("Settings_Screen");
		// else if(guic.getId() == 3)
		// ScreenManager.INSTANCE.setCurrentScreen("Leaderboard Screen");
	}

	public void update()
	{
		super.update();
	}

	public void render()
	{
		Draw2D.drawTextured(0, 0, Resources.GAME_BACKGROUND);
		super.render();
	}
}
