package com.theprogrammingturkey.ld39.util;

import com.badlogic.gdx.math.Vector2;

public class MathUtil
{
	public static float clamp(float low, float high, float value)
	{
		if(value < low)
			return low;
		if(value > high)
			return high;
		return value;
	}

	public static int clamp(int low, int high, int value)
	{
		if(value < low)
			return low;
		if(value > high)
			return high;
		return value;
	}

	public static double distance(int x1, int y1, int x2, int y2)
	{
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}

	public static boolean inBounds(int x, int y, int lx, int ly, int ux, int uy)
	{
		return x >= lx && x <= ux && y >= ly && y <= uy;
	}

	public static boolean sameLocation(Vector2 v1, Vector2 v2)
	{
		return v1.x == v2.x && v1.y == v2.y;
	}
}
