package com.theprogrammingturkey.ld39.ai;

import com.theprogrammingturkey.ld39.game.Computer;

public abstract class AiTask
{
	protected Computer cpu;
	protected boolean complete = false;

	private AiTask overrodeTask = null;

	public AiTask(Computer cpu)
	{
		this.cpu = cpu;
	}

	public abstract void init();

	public abstract void update();

	public boolean isComplete()
	{
		return this.complete;
	}

	public AiTask getOverrodeTask()
	{
		return this.overrodeTask;
	}

	public void setOverrodeTask(AiTask task)
	{
		this.overrodeTask = task;
	}
}
