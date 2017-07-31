package com.theprogrammingturkey.ld39.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.theprogrammingturkey.ld39.graphics.Draw2D;
import com.theprogrammingturkey.ld39.graphics.Resources;
import com.theprogrammingturkey.ld39.gui.GuiButton;
import com.theprogrammingturkey.ld39.gui.GuiComponent;

public class HelpScreen extends Screen
{
	public static final Texture[] HELP_SCREENS = new Texture[] { new Texture("images/help_1.png"), new Texture("images/help_2.png"), new Texture("images/help_3.png"), new Texture("images/help_4.png") };
	public int screenNum = 1;

	private GuiButton next;
	private GuiButton prev;
	private GuiButton back;

	public HelpScreen()
	{
		super("Help_Screen");
		this.addGuiComponent(next = new GuiButton(0, Gdx.graphics.getWidth() - 300, 50, 200, 50, "NEXT", Resources.BUTTON));
		this.addGuiComponent(prev = new GuiButton(1, 100, 50, 200, 50, "PREVIOUS", Resources.BUTTON));
		this.addGuiComponent(back = new GuiButton(2, 100, 50, 200, 50, "BACK", Resources.BUTTON));
	}

	public void onScreenLoad()
	{
		screenNum = 1;
		prev.setVisible(false);
	}

	public void render()
	{
		Draw2D.drawTextured(0, 0, HELP_SCREENS[screenNum - 1]);
		super.render();
	}

	public void onComponentClicked(GuiComponent guic)
	{
		if(guic.getId() == 0)
		{
			screenNum++;
			prev.setVisible(true);
			back.setVisible(false);
			if(screenNum == 4)
				next.setVisible(false);
		}
		else if(guic.getId() == 1)
		{
			screenNum--;
			next.setVisible(true);
			if(screenNum == 1)
			{
				prev.setVisible(false);
				back.setVisible(true);
			}
		}
		else if(guic.getId() == 2)
		{
			ScreenManager.INSTANCE.setCurrentScreen("Main_Screen");
		}
	}
}
