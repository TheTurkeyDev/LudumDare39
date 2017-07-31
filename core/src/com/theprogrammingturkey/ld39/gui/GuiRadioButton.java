package com.theprogrammingturkey.ld39.gui;

import com.badlogic.gdx.graphics.Color;
import com.theprogrammingturkey.ld39.graphics.Draw2D;

public class GuiRadioButton extends GuiComponent
{
	private boolean selected = false;

	public GuiRadioButton(int id, float x, float y, float width, float height, boolean selected)
	{
		super(id, x, y, width, height);
		this.selected = selected;
	}

	public void render()
	{
		if(!visible)
			return;
		Draw2D.drawString(x + (width / 2), y + (this.height / 2), this.displayText, 1.75f, Color.WHITE, true);
		Draw2D.drawRect(x, y, width, height, Color.GRAY, true);
		if(this.selected)
			Draw2D.drawRect(x + 4, y + 4, width - 8, height - 8, Color.GREEN, true);
	}

	public void toggleSelected()
	{
		this.selected = !this.selected;
	}

	public boolean isSelected()
	{
		return this.selected;
	}
}
