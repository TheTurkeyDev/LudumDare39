package com.theprogrammingturkey.ld39.game;

import com.badlogic.gdx.math.Vector2;

public class PlannedMove
{
	private Unit unit;
	private Vector2 oldPos;
	private Vector2 newPos;
	private int cost;

	public PlannedMove(Unit unit, Vector2 oldPos, Vector2 newPos, int cost)
	{
		this.unit = unit;
		this.oldPos = oldPos;
		this.newPos = newPos;
		this.cost = cost;
	}

	public Unit getUnit()
	{
		return this.unit;
	}

	public Vector2 getOldPos()
	{
		return this.oldPos;
	}

	public Vector2 getNewPos()
	{
		return this.newPos;
	}

	public int getCost()
	{
		return this.cost;
	}
}
