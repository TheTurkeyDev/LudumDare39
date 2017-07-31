package com.theprogrammingturkey.ld39.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.theprogrammingturkey.ld39.graphics.Draw2D;

public class GuiButton extends GuiComponent
{
	private Texture texture;

	public GuiButton(int id, float x, float y, float width, float height, String text, Texture texture)
	{
		super(id, x, y, width, height);
		this.texture = texture;
		this.displayText = text;
	}

	public void render()
	{
		if(!visible)
			return;
		if(texture != null)
			Draw2D.drawTextured(x, y, width, height, texture);
		else
			Draw2D.drawRect(x, y, width, height, Color.FIREBRICK, true);
		Draw2D.drawString(x + width / 2, y + (this.height / 2), this.displayText, 0.5f, Color.GREEN, true);
	}

	public Texture getTexture()
	{
		return this.texture;
	}
}
