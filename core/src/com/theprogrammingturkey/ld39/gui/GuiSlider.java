package com.theprogrammingturkey.ld39.gui;

import java.text.DecimalFormat;

import com.badlogic.gdx.graphics.Texture;
import com.theprogrammingturkey.ld39.graphics.Draw2D;
import com.theprogrammingturkey.ld39.util.MathUtil;

public class GuiSlider extends GuiComponent
{
	private Texture slider;
	private Texture sliderLine;

	private int lowerBound;
	private int upperBound;
	private float currentValue;
	private float step;

	public GuiSlider(int id, float x, float y, float width, float height, Texture slider, Texture sliderLine, int lowerBound, int upperBound, int currentValue, float step)
	{
		super(id, x, y, width, height);
		this.slider = slider;
		this.sliderLine = sliderLine;
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		this.currentValue = currentValue;
		this.step = step;
	}

	public void render()
	{
		if(!visible)
			return;

		Draw2D.drawTextured(x, y, width, height, sliderLine);

		float xOffset = (MathUtil.clamp(0f, 1f, (float) (currentValue - lowerBound) / (upperBound - lowerBound)) * width);

		Draw2D.drawTextured(x + xOffset - 15, y + (height / 4), slider);
	}

	public void dragged(int screenX, int screenY)
	{
		if(screenX < x)
		{
			currentValue = this.lowerBound;
		}
		else if(screenX > x + width)
		{
			currentValue = this.upperBound;
		}
		else
		{
			float xOffset = ((screenX - x) / width) * (upperBound - lowerBound);

			float rem = xOffset % step;

			if(rem / (float) step > 0.5)
				currentValue = lowerBound + xOffset + (1 - rem);
			else
				currentValue = lowerBound + xOffset - rem;
		}
	}

	public String getCurrentValue(String format)
	{
		DecimalFormat formatter = new DecimalFormat();
		formatter.applyLocalizedPattern(format);
		return formatter.format(currentValue);
	}
}