package com.theprogrammingturkey.ld39.ai;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.theprogrammingturkey.ld39.game.Computer;
import com.theprogrammingturkey.ld39.game.GameCore;
import com.theprogrammingturkey.ld39.game.Tile;
import com.theprogrammingturkey.ld39.game.Unit;
import com.theprogrammingturkey.ld39.util.MathUtil;

public class GrabAdjacentPower extends AiTask implements IUnitTask
{
	protected Unit unit;

	public GrabAdjacentPower(Computer cpu, Unit unit)
	{
		super(cpu);
		this.unit = unit;
	}

	@Override
	public void init()
	{

	}

	@Override
	public void update()
	{
		if(!unit.isAlive())
		{
			this.complete = true;
			return;
		}
		Tile t = this.cpu.getGame().getTileAt(unit.getPos());
		List<Vector2> moveLocs = getNearPowers(unit);

		if(moveLocs.size() > 0)
		{
			Vector2 moveLoc = moveLocs.get(GameCore.rand.nextInt(moveLocs.size()));
			Tile newLoc = cpu.getGame().getTileAt(moveLoc);
			if(newLoc != null && newLoc.canUnitMoveHere(t))
			{
				int cost = (int) (MathUtil.distance((int) unit.getPos().x, (int) unit.getPos().y, (int) moveLoc.x, (int) moveLoc.y) * 5);
				if(cpu.getPower() >= cost && cpu.isValidMove(unit, moveLoc))
				{
					cpu.removePower(cost);
					cpu.addPlannedMove(unit, unit.getPos(), moveLoc, cost);
				}
			}
		}
		else
		{
			this.complete = true;
		}
	}

	public List<Vector2> getNearPowers(Unit u)
	{
		List<Vector2> powers = new ArrayList<Vector2>();
		for(int x = -1; x < 2; x++)
		{
			for(int y = -1; y < 2; y++)
			{
				if(!(x == 0 && y == 0))
				{
					if(MathUtil.inBounds((int) u.getPos().x + x, (int) u.getPos().y + y, 0, 0, GameCore.MAP_WIDTH - 1, GameCore.MAP_HEIGHT - 1))
					{
						if(cpu.getGame().getTileAt((int) u.getPos().x + x, (int) u.getPos().y + y).hasPower())
							powers.add(new Vector2(u.getPos().x + x, u.getPos().y + y));
					}
				}
			}
		}
		return powers;
	}

	@Override
	public Unit getUnit()
	{
		return unit;
	}

}
