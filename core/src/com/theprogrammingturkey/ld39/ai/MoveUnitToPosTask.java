package com.theprogrammingturkey.ld39.ai;

import com.badlogic.gdx.math.Vector2;
import com.theprogrammingturkey.ld39.game.Computer;
import com.theprogrammingturkey.ld39.game.Unit;
import com.theprogrammingturkey.ld39.util.MathUtil;

public class MoveUnitToPosTask extends AiTask implements IUnitTask
{
	private Vector2 toPos;
	private Unit unit;

	public MoveUnitToPosTask(Computer cpu, Unit unit, Vector2 pos)
	{
		super(cpu);
		this.toPos = pos;
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
		Vector2 newLoc = new Vector2(0, 0);
		if(unit.getPos().x != toPos.x)
			newLoc.add(unit.getPos().x > toPos.x ? -1 : 1, 0);
		if(unit.getPos().y != toPos.y)
			newLoc.add(0, unit.getPos().y > toPos.y ? -1 : 1);

		if(newLoc.x == 0 && newLoc.y == 0)
		{
			this.complete = true;
			return;
		}

		int cost = (int) (MathUtil.distance(0, 0, (int) newLoc.x, (int) newLoc.y) * 5);
		if(cpu.getPower() >= cost && cpu.isValidMove(unit, unit.getPos().cpy().add(newLoc)))
		{
			cpu.removePower(cost);
			cpu.addPlannedMove(unit, unit.getPos(), unit.getPos().cpy().add(newLoc), cost);
		}
	}

	public Unit getUnit()
	{
		return this.unit;
	}

}
