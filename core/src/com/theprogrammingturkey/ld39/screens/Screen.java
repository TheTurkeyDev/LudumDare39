package com.theprogrammingturkey.ld39.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.theprogrammingturkey.ld39.gui.GuiButton;
import com.theprogrammingturkey.ld39.gui.GuiComponent;
import com.theprogrammingturkey.ld39.gui.GuiRadioButton;
import com.theprogrammingturkey.ld39.gui.GuiSlider;
import com.theprogrammingturkey.ld39.gui.GuiTextBox;
import com.theprogrammingturkey.ld39.sounds.SoundManager;

public class Screen implements InputProcessor
{
	private String name;

	protected List<GuiComponent> components = new ArrayList<GuiComponent>();

	private GuiComponent lastClicked = null;

	public Screen(String name)
	{
		this.name = name;
	}

	public void onScreenLoad()
	{

	}

	public void onScreenUnload()
	{

	}

	public void update()
	{

	}

	public void render()
	{
		for(GuiComponent guic : components)
			guic.render();
	}

	public String getScreenName()
	{
		return this.name;
	}

	public void addGuiComponent(GuiComponent guic)
	{
		components.add(guic);
	}

	public void removeGuiComponent(GuiComponent guic)
	{
		components.remove(guic);
	}

	public void clearCompnents()
	{
		this.components.clear();
	}

	public void onComponentClicked(GuiComponent guic)
	{

	}

	@Override
	public boolean keyDown(int keycode)
	{
		return false;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		return false;
	}

	@Override
	public boolean keyTyped(char character)
	{
		if(this.lastClicked != null && lastClicked instanceof GuiTextBox)
		{
			((GuiTextBox) this.lastClicked).keyTyped(character);
			return true;
		}
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		if(lastClicked != null && lastClicked instanceof GuiTextBox)
			((GuiTextBox) lastClicked).focused(false);
		lastClicked = null;
		screenY = Gdx.graphics.getHeight() - screenY;
		for(GuiComponent guic : components)
		{
			if((guic.getX() <= screenX && guic.getX() + guic.getWidth() >= screenX) && (guic.getY() <= screenY && guic.getY() + guic.getHeight() >= screenY))
			{
				if(!guic.isVisible())
					continue;
				this.onComponentClicked(guic);
				if(guic instanceof GuiTextBox)
					((GuiTextBox) guic).focused(true);
				else if(guic instanceof GuiButton)
					SoundManager.playSound(SoundManager.BUTTON_PRESS, 1f);
				else if(guic instanceof GuiRadioButton)
					((GuiRadioButton) guic).toggleSelected();
				lastClicked = guic;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		screenY = Gdx.graphics.getHeight() - screenY;
		for(GuiComponent guic : components)
		{
			if((guic.getX() <= screenX && guic.getX() + guic.getWidth() >= screenX) && (guic.getY() <= screenY && guic.getY() + guic.getHeight() >= screenY))
			{
				if(!guic.isVisible())
					continue;
				if(guic instanceof GuiSlider)
					((GuiSlider) guic).dragged(screenX, screenY);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		return false;
	}

	@Override
	public boolean scrolled(int amount)
	{
		return false;
	}
}
