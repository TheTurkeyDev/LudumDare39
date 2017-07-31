package com.theprogrammingturkey.ld39.game;

import java.util.ArrayList;
import java.util.List;

import com.theprogrammingturkey.ld39.ai.AiTask;
import com.theprogrammingturkey.ld39.ai.AttackPlayerTask;
import com.theprogrammingturkey.ld39.ai.GrabAdjacentPower;
import com.theprogrammingturkey.ld39.ai.IUnitTask;
import com.theprogrammingturkey.ld39.ai.SeekPowerTask;

public class Computer extends Player
{
	public boolean initiated = false;
	private List<AiTask> aiTasks = new ArrayList<AiTask>();

	public Computer(int playerNum, GameCore game)
	{
		super(playerNum, game);
	}

	public void processTurn()
	{
		if(!initiated)
		{
			addAiTask(new SeekPowerTask(this));
			addAiTask(new AttackPlayerTask(this));
			for(int x = 0; x < GameCore.MAP_WIDTH; x++)
			{
				for(int y = 0; y < GameCore.MAP_HEIGHT; y++)
				{
					Tile t = game.getTileAt(x, y);
					if(t.hasUnit() && t.getUnit().getPlayer().equals(this))
					{
						addAiTask(new GrabAdjacentPower(this, t.getUnit()));
					}
				}
			}
			initiated = true;
		}

		for(int i = aiTasks.size() - 1; i >= 0; i--)
		{
			AiTask task = aiTasks.get(i);
			task.update();
			if(task.isComplete())
			{
				aiTasks.remove(i);
				if(task.getOverrodeTask() != null)
					addAiTask(task.getOverrodeTask());
			}
		}
	}

	public void addAiTask(AiTask task)
	{
		if(task instanceof IUnitTask)
		{
			Unit unit = ((IUnitTask) task).getUnit();
			for(int i = aiTasks.size() - 1; i >= 0; i--)
			{
				AiTask task2 = aiTasks.get(i);
				if(task2 instanceof IUnitTask)
				{
					if(((IUnitTask) task2).getUnit().equals(unit))
					{
						task.setOverrodeTask(task2);
						aiTasks.remove(i);
					}
				}
			}
		}
		task.init();
		aiTasks.add(task);
	}

	public boolean isUnitInAiTask(Unit unit)
	{
		for(int i = aiTasks.size() - 1; i >= 0; i--)
		{
			AiTask task = aiTasks.get(i);
			if(task instanceof IUnitTask)
				if(((IUnitTask) task).getUnit().equals(unit))
					return true;
		}
		return false;
	}

	public int getNumTasks()
	{
		return this.aiTasks.size();
	}
}
