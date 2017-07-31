package com.theprogrammingturkey.ld39.game;

import com.badlogic.gdx.math.Vector2;

public class Unit
{
	private Player controllPlayer;
	private Vector2 pos;
	private boolean alive = true;

	public Unit(Player player, Vector2 pos)
	{
		this.controllPlayer = player;
		this.pos = pos;
	}

	public Player getPlayer()
	{
		return this.controllPlayer;
	}

	public void setPos(Vector2 pos)
	{
		this.pos = pos;
	}

	public Vector2 getPos()
	{
		return this.pos;
	}

	public void kill()
	{
		alive = false;
	}

	public boolean isAlive()
	{
		return this.alive;
	}
}
