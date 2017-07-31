package com.theprogrammingturkey.ld39.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.theprogrammingturkey.ld39.graphics.Draw2D;

public class GuiTextBox extends GuiComponent
{
	private Texture texture;
	private long tick = 0;
	private int maxLength = 10;

	private String infoText = "";

	private boolean focused = false;

	public GuiTextBox(int id, float x, float y, float width, float height, Texture texture)
	{
		super(id, x, y, width, height);
		this.texture = texture;
		this.displayText = "";
	}

	public void render()
	{
		if(!visible)
			return;
		tick++;

		if(texture == null)
			Draw2D.drawRect(x, y, width, height, Color.WHITE, false);
		else
			Draw2D.drawTextured(x, y, width, height, texture);
		if(this.displayText.equals("") && !this.infoText.equals(""))
		{
			Draw2D.drawString(x + (width / 2), y + (this.height / 2) + 5, this.infoText, 0.5f, Color.LIGHT_GRAY, true);
		}
		else
		{
			if(tick % 120 <= 60 && this.focused)
				Draw2D.drawString(x + (width /2), y + (this.height / 2) + 5, this.displayText + "|", 0.5f, Color.GREEN, true);
			else
				Draw2D.drawString(x + (width / 2), y + (this.height / 2) + 5, this.displayText, 0.5f, Color.GREEN, true);
		}
	}

	public void keyTyped(char character)
	{
		if(character == 8)
		{
			this.displayText = this.displayText.substring(0, Math.max(this.displayText.length() - 1, 0));
		}
		else
		{
			if(this.displayText.length() < maxLength)
				this.displayText += character;
			this.displayText = this.displayText.trim();
		}
	}

	public void setInfoText(String text)
	{
		this.infoText = text;
	}

	public void focused(boolean toggle)
	{
		this.focused = toggle;
	}
}