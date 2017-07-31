package com.theprogrammingturkey.ld39.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.theprogrammingturkey.ld39.game.Settings;
import com.theprogrammingturkey.ld39.graphics.Draw2D;
import com.theprogrammingturkey.ld39.graphics.Resources;
import com.theprogrammingturkey.ld39.gui.GuiButton;
import com.theprogrammingturkey.ld39.gui.GuiComponent;

public class GameOverScreen extends Screen
{
	public GameOverScreen()
	{
		super("Game_Over_Screen");
		this.addGuiComponent(new GuiButton(0, (Gdx.graphics.getWidth() / 2) - 400, 350, 200, 50, "Restart", Resources.BUTTON));
		this.addGuiComponent(new GuiButton(1, (Gdx.graphics.getWidth() / 2) + 200, 350, 200, 50, "Main", Resources.BUTTON));
	}

	public void render()
	{
		Draw2D.drawTextured(0, 0, Resources.BACKGROUND);

		if(Settings.player1Won)
			Draw2D.drawString(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - 200, "You Win!", 2, Color.ORANGE, true);
		else
			Draw2D.drawString(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - 200, "You Lose!", 2, Color.ORANGE, true);

		super.render();
	}

	public void onComponentClicked(GuiComponent guic)
	{
		if(guic.getId() == 0)
			ScreenManager.INSTANCE.setCurrentScreen("Game_Screen");
		else if(guic.getId() == 1)
			ScreenManager.INSTANCE.setCurrentScreen("Main_Screen");
	}

}
