package com.theprogrammingturkey.ld39.game;

import com.badlogic.gdx.math.Vector2;

public class AttackData
{
	private Vector2 cpuLocation;
	private Vector2 playerLocation;
	private Vector2 cpuWinLocation;
	private Vector2 playerWinLocation;

	public AttackData(Vector2 cpuLocation, Vector2 playerLocation, Vector2 cpuWinLocation, Vector2 playerWinLocation)
	{
		this.cpuLocation = cpuLocation;
		this.playerLocation = playerLocation;
		this.cpuWinLocation = cpuWinLocation;
		this.playerWinLocation = playerWinLocation;
	}

	public Vector2 getCpuLocation()
	{
		return cpuLocation;
	}

	public Vector2 getPlayerLocation()
	{
		return playerLocation;
	}

	public Vector2 getCpuWinLocation()
	{
		return cpuWinLocation;
	}

	public Vector2 getPlayerWinLocation()
	{
		return playerWinLocation;
	}

}
