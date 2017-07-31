package com.theprogrammingturkey.ld39.game;

import com.theprogrammingturkey.ld39.graphics.Draw2D;
import com.theprogrammingturkey.ld39.graphics.Resources;

public class Tile
{
	private int x, y;

	private boolean hasPower = false;
	private boolean hasUnit = false;

	private Unit unit;
	private int power = -1;

	public Tile(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public void render(int screenX, int screenY, int size)
	{
		if(unit != null)
			if(unit.getPlayer().isPlayer1())
				Draw2D.drawTextured(screenX + 5, screenY + 5, 50, 50, Resources.BLUE_ROBOT);
			else
				Draw2D.drawTextured(screenX + 5, screenY + 5, 50, 50, Resources.RED_ROBOT);
		if(hasPower)
			Draw2D.drawTextured(screenX + 16, screenY + 16, Resources.BATTERY_TEXTURE);
	}

	public void setUnit(Unit unit)
	{
		this.unit = unit;
		hasUnit = true;
	}

	public Unit removeUnit()
	{
		Unit removed = this.unit;
		unit = null;
		hasUnit = false;
		return removed;
	}

	public Unit getUnit()
	{
		return unit;
	}

	public boolean hasUnit()
	{
		return this.hasUnit;
	}

	public void setPower(int power)
	{
		this.hasPower = true;
		this.power = power;
	}

	public boolean hasPower()
	{
		return this.hasPower;
	}

	public int removePower()
	{
		int powerLeft = power;
		this.power = 0;
		this.hasPower = false;
		return powerLeft;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public boolean isSameLoc(Tile tile)
	{
		return this.x == tile.getX() && this.y == tile.getY();
	}

	public boolean canUnitMoveHere(Tile from)
	{
		if(from == null || !from.hasUnit || (this.hasUnit && this.getUnit().getPlayer().equals(from.getUnit().getPlayer())) || this.isSameLoc(from))
			return false;
		return true;
	}
}
