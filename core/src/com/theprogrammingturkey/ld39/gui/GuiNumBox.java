package com.theprogrammingturkey.ld39.gui;

import com.badlogic.gdx.graphics.Texture;

public class GuiNumBox extends GuiTextBox
{
	private int maxNum = Integer.MAX_VALUE;

	public GuiNumBox(int id, float x, float y, float width, float height, Texture texture)
	{
		super(id, x, y, width, height, texture);
	}

	public void keyTyped(char character)
	{
		if(character == 8)
		{
			this.displayText = this.displayText.substring(0, Math.max(this.displayText.length() - 1, 0));
		}
		else if(character >= 48 && character <= 57)
		{
			this.displayText += character;
			if(Integer.parseInt(this.displayText) > maxNum)
				displayText = "" + maxNum;
		}
	}

	public void setMaxNum(int max)
	{
		this.maxNum = max;
	}

	public int getValue()
	{
		if(this.displayText == "")
			return 0;
		return Integer.parseInt(this.displayText);
	}
}