package com.theprogrammingturkey.ld39.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.theprogrammingturkey.ld39.game.Settings;
import com.theprogrammingturkey.ld39.graphics.Draw2D;
import com.theprogrammingturkey.ld39.graphics.Resources;
import com.theprogrammingturkey.ld39.gui.GuiButton;
import com.theprogrammingturkey.ld39.gui.GuiComponent;
import com.theprogrammingturkey.ld39.gui.GuiRadioButton;

public class SettingsScreen extends Screen
{
	private GuiRadioButton sounds;
	// private GuiSlider soundLevel;

	public SettingsScreen()
	{
		super("Settings_Screen");
		this.addGuiComponent(new GuiButton(0, (Gdx.graphics.getWidth() / 2) - 169, 10, 338, 75, "Back", Resources.BUTTON));
		super.addGuiComponent(sounds = new GuiRadioButton(1, 300, Gdx.graphics.getHeight() - 225, 16, 16, true));
		// super.addGuiComponent(soundLevel = new GuiSlider(1, 425, Gdx.graphics.getHeight() - 410, 300, 200, Resources.SLIDER, Resources.SLIDER_LINE, 0, 100, 100, 1));
	}

	public void onComponentClicked(GuiComponent guic)
	{
		if(guic.getId() == 0)
			ScreenManager.INSTANCE.setCurrentScreen("Main_Screen");
	}

	public void render()
	{
		Draw2D.drawTextured(0, 0, Resources.BACKGROUND);
		super.render();
		Draw2D.drawString(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - 50, "SETTINGS", 2f, Color.ORANGE, true);
		Draw2D.drawString(100, Gdx.graphics.getHeight() - 200, "Sounds", 0.5f, Color.WHITE, false);
		// Draw2D.drawString(100, Gdx.graphics.getHeight() - 300, "Sound Level : " + soundLevel.getCurrentValue("##0"), 1f, Color.WHITE, false);
	}

	public void update()
	{
		Settings.sounds = sounds.isSelected();
		// Settings.soundLevel = Integer.parseInt(soundLevel.getCurrentValue("##0"));
	}
}
