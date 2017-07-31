package com.theprogrammingturkey.ld39.ai;

import com.badlogic.gdx.math.Vector2;
import com.theprogrammingturkey.ld39.game.Computer;
import com.theprogrammingturkey.ld39.game.GameCore;
import com.theprogrammingturkey.ld39.game.Tile;
import com.theprogrammingturkey.ld39.game.Unit;
import com.theprogrammingturkey.ld39.util.CustomEntry;
import com.theprogrammingturkey.ld39.util.MathUtil;

public class AttackPlayerTask extends AiTask
{
	public AttackPlayerTask(Computer cpu)
	{
		super(cpu);
	}

	@Override
	public void init()
	{

	}

	@Override
	public void update()
	{
		if(cpu.getPower() > 200)
		{
			CustomEntry<Double, Vector2> closest = new CustomEntry<Double, Vector2>(Double.MAX_VALUE, null);
			Unit closestUnit = null;
			for(int x = 0; x < GameCore.MAP_WIDTH; x++)
			{
				for(int y = 0; y < GameCore.MAP_HEIGHT; y++)
				{
					Tile t = cpu.getGame().getTileAt(x, y);
					if(t.hasUnit() && t.getUnit().getPlayer().equals(cpu))
					{
						CustomEntry<Double, Vector2> info = getNearestEnemy(t.getUnit());
						if(info.getValue() != null)
						{
							if(closest.getKey() > info.getKey())
							{
								closest = info;
								closestUnit = t.getUnit();
							}
							else if(info.getKey() < 2)
							{
								cpu.addAiTask(new MoveUnitToPosTask(cpu, closestUnit, closest.getValue()));
							}
						}
					}
				}
			}

			if(closest.getValue() != null && cpu.getNumTasks() < 5)
			{
				cpu.addAiTask(new MoveUnitToPosTask(cpu, closestUnit, closest.getValue()));
			}
		}
	}

	public CustomEntry<Double, Vector2> getNearestEnemy(Unit u)
	{
		double nearest = Double.MAX_VALUE;
		Vector2 nearestPos = null;
		for(int x = 0; x < GameCore.MAP_WIDTH; x++)
		{
			for(int y = 0; y < GameCore.MAP_HEIGHT; y++)
			{
				double dist = MathUtil.distance(x, y, (int) u.getPos().x, (int) u.getPos().y);
				Tile t = cpu.getGame().getTileAt(x, y);
				if(t.hasUnit() && !t.getUnit().getPlayer().equals(cpu) && dist < nearest)
				{
					nearestPos = new Vector2(x, y);
					nearest = dist;
				}
			}
		}
		return new CustomEntry<Double, Vector2>(nearest, nearestPos);
	}

}
