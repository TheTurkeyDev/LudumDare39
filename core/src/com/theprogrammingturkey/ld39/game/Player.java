package com.theprogrammingturkey.ld39.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.theprogrammingturkey.ld39.util.MathUtil;

public class Player
{
	protected GameCore game;
	protected int power;
	protected int playerNum;
	protected boolean nextStageReady = false;

	protected int chosenAttackPower = 0;

	protected List<PlannedMove> planedMoves = new ArrayList<PlannedMove>();

	public Player(int playerNum, GameCore game)
	{
		power = 50;
		this.playerNum = playerNum;
		this.game = game;
	}

	public int getPower()
	{
		return this.power;
	}

	public void addPower(int amount)
	{
		this.power += amount;
	}

	public void removePower(int amount)
	{
		this.power -= amount;
	}

	public boolean isPlayer1()
	{
		return this.playerNum == 1;
	}

	public void setReady()
	{
		this.nextStageReady = true;
	}

	public void unready()
	{
		this.nextStageReady = false;
	}

	public boolean isReady()
	{
		return this.nextStageReady;
	}

	public List<PlannedMove> getPlannedMoves()
	{
		return this.planedMoves;
	}

	public void clearPlannedMoves()
	{
		this.planedMoves.clear();
	}

	public void addPlannedMove(Unit unit, Vector2 oldPos, Vector2 newPos, int cost)
	{
		this.planedMoves.add(new PlannedMove(unit, oldPos, newPos, cost));
	}

	public void removePlannedMoveAt(int x, int y)
	{
		for(int i = this.planedMoves.size() - 1; i >= 0; i--)
		{
			PlannedMove move = this.planedMoves.get(i);
			if(move.getOldPos().x == x && move.getOldPos().y == y)
			{
				this.addPower(move.getCost());
				this.planedMoves.remove(i);
				PlannedMove move2 = getPlannedMoveToPosition(new Vector2(x, y));
				while(move2 != null)
				{
					this.addPower(move2.getCost());
					this.planedMoves.remove(move2);
					move2 = getPlannedMoveToPosition(move2.getOldPos());
				}

				return;
			}
		}

	}

	public PlannedMove getPlannedMoveFromUnit(Unit unit)
	{
		for(PlannedMove move : this.planedMoves)
			if(move.getUnit().equals(unit))
				return move;
		return null;
	}

	public PlannedMove getPlannedMoveToPosition(Vector2 pos)
	{
		for(PlannedMove move : this.planedMoves)
			if(MathUtil.sameLocation(move.getNewPos(), pos))
				return move;
		return null;
	}

	public boolean isValidMove(Unit unit, Vector2 newPos)
	{
		PlannedMove move = getPlannedMoveFromUnit(unit);
		if(move != null)
		{
			this.planedMoves.remove(move);
			this.addPower(move.getCost());
		}
		boolean valid = true;

		Tile tile = game.getTileAt((int) newPos.x, (int) newPos.y);
		if(tile != null)
		{
			boolean invalid = false;
			for(PlannedMove pm : planedMoves)
			{
				Vector2 v = pm.getNewPos();
				if(v.x == newPos.x && v.y == newPos.y)
				{
					valid = false;
					invalid = true;
				}
			}
			if(tile.hasUnit() && !invalid)
			{
				if(!tile.getUnit().getPlayer().equals(this))
				{
					valid = true;
				}
				else
				{
					PlannedMove move2 = getPlannedMoveFromUnit(tile.getUnit());
					if(move2 == null || MathUtil.sameLocation(move2.getNewPos(), unit.getPos()))
						valid = false;
					else
						valid = true;
				}
			}
		}
		else
		{
			valid = false;
		}

		if(!valid && move != null)
		{
			this.removePower(move.getCost());
			planedMoves.add(move);
		}

		return valid;
	}

	public int getChosenAttackPower()
	{
		return this.chosenAttackPower;
	}

	public void setCurrentAttackPower(int power)
	{
		this.chosenAttackPower = power;
	}

	public GameCore getGame()
	{
		return this.game;
	}
}
