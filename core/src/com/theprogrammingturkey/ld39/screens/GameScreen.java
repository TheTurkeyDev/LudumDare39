package com.theprogrammingturkey.ld39.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.theprogrammingturkey.ld39.game.GameCore;
import com.theprogrammingturkey.ld39.graphics.Draw2D;
import com.theprogrammingturkey.ld39.graphics.Resources;
import com.theprogrammingturkey.ld39.gui.GuiButton;
import com.theprogrammingturkey.ld39.gui.GuiComponent;
import com.theprogrammingturkey.ld39.gui.GuiNumBox;
import com.theprogrammingturkey.ld39.util.MathUtil;

public class GameScreen extends Screen
{
	private GameCore game;

	private boolean canPoint = false;
	private Vector2 lastClick = new Vector2(-1, -1);

	private boolean canPan = false;
	private Vector2 lastLoc = new Vector2(-1, -1);
	private boolean panned = false;

	private GuiNumBox powerGiven;
	private GuiButton donePowerSelect;

	private GuiButton resume;
	private GuiButton restart;
	private GuiButton main;

	private boolean paused = false;

	private long lastTouchTime = 0;

	public GameScreen()
	{
		super("Game_Screen");
		this.addGuiComponent(new GuiButton(0, (Gdx.graphics.getWidth() / 2) - 100, Gdx.graphics.getHeight() - 75, 200, 50, "Done", Resources.BUTTON));
		this.addGuiComponent(powerGiven = new GuiNumBox(1, (Gdx.graphics.getWidth() / 2) - 100, 200, 200, 40, null));
		this.addGuiComponent(donePowerSelect = new GuiButton(2, (Gdx.graphics.getWidth() / 2) - 100, 100, 200, 50, "Attack", Resources.BUTTON));

		this.addGuiComponent(resume = new GuiButton(3, (Gdx.graphics.getWidth() / 2) - 400, 350, 200, 50, "Resume", Resources.BUTTON));
		this.addGuiComponent(restart = new GuiButton(4, (Gdx.graphics.getWidth() / 2) - 100, 350, 200, 50, "Restart", Resources.BUTTON));
		this.addGuiComponent(main = new GuiButton(5, (Gdx.graphics.getWidth() / 2) + 200, 350, 200, 50, "Main", Resources.BUTTON));

		powerGiven.setInfoText("Power");
	}

	public void onScreenLoad()
	{
		game = new GameCore();
		resume.setVisible(false);
		restart.setVisible(false);
		main.setVisible(false);
		donePowerSelect.setVisible(false);
		powerGiven.setVisible(false);
		paused = false;
	}

	public void render()
	{
		game.render();

		Draw2D.drawRect(0, Gdx.graphics.getHeight() - 100, Gdx.graphics.getWidth(), 100, Color.BLACK, true);

		Draw2D.drawRect(5, Gdx.graphics.getHeight() - 77, 70, 70, Color.BLUE, true);
		Draw2D.drawTextured(10, Gdx.graphics.getHeight() - 75, Resources.BLUE_ROBOT);
		Draw2D.drawTextured(80, Gdx.graphics.getHeight() - 60, Resources.BATTERY_TEXTURE);
		Draw2D.drawString(120, Gdx.graphics.getHeight() - 30, "" + game.getPlayer1().getPower(), 0.4f, Color.YELLOW, false);

		Draw2D.drawRect(Gdx.graphics.getWidth() - 75, Gdx.graphics.getHeight() - 77, 70, 70, Color.RED, true);
		Draw2D.drawTextured(Gdx.graphics.getWidth() - 72, Gdx.graphics.getHeight() - 75, Resources.RED_ROBOT);
		Draw2D.drawTextured(Gdx.graphics.getWidth() - 115, Gdx.graphics.getHeight() - 60, Resources.BATTERY_TEXTURE);
		Draw2D.drawString(Gdx.graphics.getWidth() - 180, Gdx.graphics.getHeight() - 30, "" + game.getPlayer2().getPower(), 0.4f, Color.YELLOW, false);

		if(game.isAttacking())
		{
			donePowerSelect.setVisible(true);
			powerGiven.setVisible(true);
			powerGiven.setMaxNum(game.getPlayer1().getPower());
			Draw2D.drawRect(300, 50, 700, 300, Color.GRAY, true);
			Draw2D.drawString(450, 325, "Assign Attack Power", 0.5f, Color.YELLOW, false);
		}
		else
		{
			donePowerSelect.setVisible(false);
			powerGiven.setVisible(false);
		}

		if(paused)
		{
			Draw2D.drawRect(200, 200, Gdx.graphics.getWidth() - 400, Gdx.graphics.getHeight() - 250, Color.GRAY, true);
			Draw2D.drawString(Gdx.graphics.getWidth() / 2, 550, "PAUSED", 2, Color.MAROON, true);
		}
		
		if(!panned)
			Draw2D.drawString(Gdx.graphics.getWidth() / 2, 550, "Hold right click and move your mouse to pan!", 0.5f, Color.LIME, true);

		super.render();
	}

	public void update()
	{
		game.update();
	}

	public void onComponentClicked(GuiComponent guic)
	{
		if(!paused)
		{
			if(guic.getId() == 0)
			{
				game.getPlayer1().setReady();
			}
			if(guic.getId() == 2)
			{
				game.getPlayer1().setCurrentAttackPower(this.powerGiven.getValue());
				game.getPlayer1().setReady();
				powerGiven.setDisplayText("");
			}
		}

		if(guic.getId() == 3)
			togglePause();
		else if(guic.getId() == 4)
			ScreenManager.INSTANCE.setCurrentScreen("Game_Screen");
		else if(guic.getId() == 5)
			ScreenManager.INSTANCE.setCurrentScreen("Main_Screen");
	}

	public void togglePause()
	{
		paused = !paused;
		resume.setVisible(paused);
		restart.setVisible(paused);
		main.setVisible(paused);
	}

	@Override
	public boolean scrolled(int amount)
	{
		// game.zoom(amount);
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		if(paused)
			return super.touchDown(screenX, screenY, pointer, button);

		if(!super.touchDown(screenX, screenY, pointer, button))
		{
			if(button == 0)
			{
				if(System.currentTimeMillis() - lastTouchTime < 500 && MathUtil.distance(screenX, screenY, (int) lastClick.x, (int) lastClick.y) < 10)
					game.clearTileAt(screenX, screenY);

				lastTouchTime = System.currentTimeMillis();
				lastClick.set(screenX, screenY);
				game.clicked(screenX, screenY);
				canPoint = true;
			}
			else if(button == 1)
			{
				panned = true;
				lastLoc.set(screenX, screenY);
				canPan = true;
			}
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		if(paused)
			return super.touchUp(screenX, screenY, pointer, button);

		if(button == 0)
		{
			canPoint = false;
			game.processPoint();
		}
		if(button == 1)
		{
			canPan = false;
		}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		if(paused)
			return super.touchDragged(screenX, screenY, pointer);

		if(!super.touchDragged(screenX, screenY, pointer))
		{
			if(canPan)
			{
				game.pan(screenX - (int) lastLoc.x, (int) lastLoc.y - screenY);
				lastLoc.set(screenX, screenY);
			}
			if(canPoint)
			{
				game.setPointLocation(screenX, screenY);
			}
		}
		return true;
	}

	public boolean keyDown(int keycode)
	{
		if(keycode == Keys.ESCAPE)
		{
			this.togglePause();
			return true;
		}
		return super.keyDown(keycode);
	}
}
