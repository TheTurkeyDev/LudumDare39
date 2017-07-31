package com.theprogrammingturkey.ld39.game;

public class Settings
{
	public static boolean player1Won = false;
	public static boolean sounds = true;
	public static int soundLevel = 100;

	public static float getSoundLevelF()
	{
		return soundLevel / 100f;
	}
}
