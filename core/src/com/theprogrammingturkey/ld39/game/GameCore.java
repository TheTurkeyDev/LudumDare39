package com.theprogrammingturkey.ld39.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.theprogrammingturkey.ld39.graphics.Draw2D;
import com.theprogrammingturkey.ld39.graphics.Resources;
import com.theprogrammingturkey.ld39.screens.ScreenManager;
import com.theprogrammingturkey.ld39.sounds.SoundManager;
import com.theprogrammingturkey.ld39.util.MathUtil;

public class GameCore
{
	public static final Random rand = new Random();
	public static final int TILE_SIZE = 64;
	public static final int MAP_WIDTH = 25;
	public static final int MAP_HEIGHT = 25;

	private Tile[][] tiles = new Tile[MAP_WIDTH][MAP_HEIGHT];
	private Tile selectedTile = null;
	private int px = 0, py = 0;

	private STAGE gameStage;

	private List<AttackData> attacks = new ArrayList<AttackData>();
	private AttackData currentAttack;

	private int scale = 4;

	private int xOffset = (Gdx.graphics.getWidth() / 2) - ((MAP_WIDTH / 2) * TILE_SIZE);
	private int yOffset = (Gdx.graphics.getHeight() / 2);

	private Player player1;
	private Computer player2;

	public GameCore()
	{
		initMap();
		gameStage = STAGE.PLAN;
	}

	public void initMap()
	{
		player1 = new Player(1, this);
		player2 = new Computer(2, this);
		for(int x = 0; x < MAP_WIDTH; x++)
		{
			for(int y = 0; y < MAP_HEIGHT; y++)
			{
				Tile tile = new Tile(x, y);

				if(x > 6 && x <= MAP_WIDTH - 8 && ((y >= 0 && y < 3) || (y >= MAP_HEIGHT - 3 && y < MAP_HEIGHT)))
					tile.setUnit(new Unit(((y >= 0 && y < 3) ? player1 : player2), new Vector2(x, y)));
				else
					tile.setPower(15);

				tiles[x][y] = tile;
			}
		}
	}

	public void render()
	{
		for(int x = 0; x < MAP_WIDTH; x++)
		{
			for(int y = 0; y < MAP_HEIGHT; y++)
			{
				int xx = ((x * TILE_SIZE) + xOffset);
				int yy = ((y * TILE_SIZE) + yOffset);

				Draw2D.drawTextured(xx, yy, Resources.TILE);
				Draw2D.drawRect(xx, yy, TILE_SIZE, TILE_SIZE, Color.WHITE, false);

				tiles[x][y].render(xx, yy, TILE_SIZE);

				if(selectedTile != null && selectedTile.hasUnit() && selectedTile.getUnit().getPlayer().isValidMove(selectedTile.getUnit(), new Vector2(px, py)))
				{
					int offset = TILE_SIZE / 2;

					Draw2D.drawLine((selectedTile.getX() * TILE_SIZE) + xOffset + offset, (selectedTile.getY() * TILE_SIZE) + yOffset + offset, (px * TILE_SIZE) + xOffset + offset, (py * TILE_SIZE) + yOffset + offset, Color.LIME);

					int cost = (int) (MathUtil.distance(selectedTile.getX(), selectedTile.getY(), px, py) * 5);
					Color drawColor = cost > player1.getPower() ? Color.RED : Color.GREEN;
					Draw2D.drawString((px * TILE_SIZE) + xOffset + offset, (py * TILE_SIZE) + yOffset + offset, "Cost: " + cost, 0.25f, drawColor, false);
				}
			}

			if(selectedTile != null)
				Draw2D.drawRect(((selectedTile.getX() * TILE_SIZE) + xOffset), ((selectedTile.getY() * TILE_SIZE) + yOffset), TILE_SIZE, TILE_SIZE, Color.RED, false);
		}

		for(PlannedMove move : player1.getPlannedMoves())
		{
			Vector2 oldT = move.getOldPos();
			Vector2 newT = move.getNewPos();
			int offset = TILE_SIZE / 2;
			Draw2D.drawLine((oldT.x * TILE_SIZE) + xOffset + offset, (oldT.y * TILE_SIZE) + yOffset + offset, (newT.x * TILE_SIZE) + xOffset + offset, (newT.y * TILE_SIZE) + yOffset + offset, Color.CYAN);
		}

		for(AttackData attack : attacks)
		{
			int xx = (((int) attack.getPlayerLocation().x * TILE_SIZE) + xOffset);
			int yy = (((int) attack.getPlayerLocation().y * TILE_SIZE) + yOffset);
			Draw2D.drawRect(xx, yy, TILE_SIZE, TILE_SIZE, attack == currentAttack ? Color.GREEN : Color.MAROON, false);
			xx = (((int) attack.getCpuLocation().x * TILE_SIZE) + xOffset);
			yy = (((int) attack.getCpuLocation().y * TILE_SIZE) + yOffset);
			Draw2D.drawRect(xx, yy, TILE_SIZE, TILE_SIZE, attack == currentAttack ? Color.GREEN : Color.MAROON, false);
		}
	}

	public void update()
	{
		switch(gameStage)
		{
			case ATTACK:
				if(this.attacks.size() == 0)
				{
					nextStage();
				}
				else if(this.currentAttack == null)
				{
					this.currentAttack = this.attacks.get(0);
					xOffset = (Gdx.graphics.getWidth() / 2) - (int) currentAttack.getPlayerLocation().x * TILE_SIZE;
					yOffset = (Gdx.graphics.getHeight() / 2) - (int) currentAttack.getPlayerLocation().y * TILE_SIZE;
				}
				else
				{
					if(player1.isReady())
					{
						processCurrentAttack();
						this.attacks.remove(0);
						this.currentAttack = null;
					}
				}
				break;
			case PLAN:
				if(player1.isReady())
				{
					player2.processTurn();
					nextStage();
				}
				break;
			default:
				break;

		}
	}

	public void nextStage()
	{
		switch(gameStage)
		{
			case ATTACK:
				int player1Units = 0;
				int player2Units = 0;
				for(int x = 0; x < MAP_WIDTH; x++)
				{
					for(int y = 0; y < MAP_HEIGHT; y++)
					{
						Tile t = tiles[x][y];
						if(t.hasUnit())
						{
							if(t.getUnit().getPlayer().isPlayer1())
								player1Units++;
							else
								player2Units++;
						}
					}
				}
				if(player1.getPower() < 5 || player2.getPower() < 5)
					endGame(player1Units > player2Units);
				else if(player1Units == 0 || player2Units == 0)
					endGame(player1Units != 0);
				else
					this.gameStage = STAGE.PLAN;
				break;
			case PLAN:
				List<PlannedMove> planedMoves = new ArrayList<PlannedMove>();
				planedMoves.addAll(player1.getPlannedMoves());
				player1.clearPlannedMoves();
				planedMoves.addAll(player2.getPlannedMoves());
				player2.clearPlannedMoves();
				processPlanedMoves(planedMoves);
				player1.unready();
				this.gameStage = STAGE.ATTACK;
				break;
			default:
				break;

		}
	}

	public void processCurrentAttack()
	{
		Tile p1Tile = this.getTileAt(this.currentAttack.getPlayerLocation());
		Tile p2Tile = this.getTileAt(this.currentAttack.getCpuLocation());
		int cpuPower = rand.nextInt((int) (Math.min(player2.getPower() * 0.4f, 100)));
		if(player1.getChosenAttackPower() > cpuPower)
		{
			this.getTileAt(this.currentAttack.getCpuLocation()).removeUnit().kill();
			this.moveUnit(this.currentAttack.getPlayerLocation(), this.currentAttack.getPlayerWinLocation());
		}
		else if(player1.getChosenAttackPower() < cpuPower)
		{
			this.getTileAt(this.currentAttack.getPlayerLocation()).removeUnit().kill();
			this.moveUnit(this.currentAttack.getCpuLocation(), this.currentAttack.getCpuWinLocation());
		}
		else
		{
			p1Tile.removeUnit().kill();
			p2Tile.removeUnit().kill();
		}
		player1.removePower(player1.getChosenAttackPower());
		player2.removePower(cpuPower);
		player1.unready();
	}

	public void processPlanedMoves(List<PlannedMove> planedMoves)
	{
		List<PlannedMove> nonAttacks = new ArrayList<PlannedMove>();
		while(planedMoves.size() > 0)
		{
			PlannedMove move = planedMoves.get(0);
			Tile nt = this.getTileAt(move.getNewPos());
			Tile ot = this.getTileAt(move.getOldPos());

			if(nt.hasUnit() && !nt.getUnit().getPlayer().equals(ot.getUnit().getPlayer()))
			{
				PlannedMove deffender = getPlannedMoveForPos(move.getNewPos(), planedMoves);
				if(deffender == null)
					deffender = getPlannedMoveForPos(move.getNewPos(), nonAttacks);

				if(deffender != null)
				{
					if(ot.getUnit().getPlayer().isPlayer1())
						this.attacks.add(new AttackData(deffender.getOldPos(), move.getOldPos(), deffender.getNewPos(), move.getNewPos()));
					else
						this.attacks.add(new AttackData(move.getOldPos(), deffender.getOldPos(), move.getNewPos(), deffender.getNewPos()));
					planedMoves.remove(move);
					planedMoves.remove(deffender);
					nonAttacks.remove(deffender);
				}
				else
				{
					if(ot.getUnit().getPlayer().isPlayer1())
						this.attacks.add(new AttackData(move.getNewPos(), move.getOldPos(), move.getNewPos(), move.getNewPos()));
					else
						this.attacks.add(new AttackData(move.getOldPos(), move.getNewPos(), move.getNewPos(), move.getNewPos()));
					planedMoves.remove(move);
				}
			}
			else
			{
				PlannedMove attacker = getPlannedMoveToPos(move.getNewPos(), planedMoves, move);
				if(attacker == null)
					attacker = getPlannedMoveToPos(move.getNewPos(), nonAttacks, move);

				if(attacker != null)
				{
					if(ot.getUnit().getPlayer().isPlayer1())
						this.attacks.add(new AttackData(attacker.getOldPos(), move.getOldPos(), attacker.getNewPos(), move.getNewPos()));
					else
						this.attacks.add(new AttackData(move.getOldPos(), attacker.getOldPos(), move.getNewPos(), attacker.getNewPos()));
					planedMoves.remove(move);
					planedMoves.remove(attacker);
					nonAttacks.remove(attacker);
				}
				else
				{
					nonAttacks.add(move);
					planedMoves.remove(move);
				}
			}

		}

		List<PlannedMove> delayedMoves = new ArrayList<PlannedMove>();
		do
		{
			nonAttacks.addAll(delayedMoves);
			for(int i = nonAttacks.size() - 1; i >= 0; i--)
			{
				PlannedMove move = nonAttacks.get(i);
				if(this.getTileAt(move.getNewPos()).hasUnit())
					delayedMoves.add(move);
				else
					this.moveUnit(move.getOldPos(), move.getNewPos());
				nonAttacks.remove(i);
			}
		} while(delayedMoves.size() > 0);
	}

	public PlannedMove getPlannedMoveForPos(Vector2 pos, List<PlannedMove> moves)
	{
		for(PlannedMove move : moves)
			if(MathUtil.sameLocation(move.getOldPos(), pos))
				return move;
		return null;
	}

	public PlannedMove getPlannedMoveToPos(Vector2 pos, List<PlannedMove> moves, PlannedMove blacklist)
	{
		for(PlannedMove move : moves)
			if(MathUtil.sameLocation(move.getNewPos(), pos) && !move.equals(blacklist))
				return move;
		return null;
	}

	public void clicked(int x, int y)
	{
		int xx = (x - xOffset) / TILE_SIZE;
		int yy = ((Gdx.graphics.getHeight() - y) - yOffset) / TILE_SIZE;

		if(MathUtil.inBounds(xx, yy, 0, 0, MAP_WIDTH - 1, MAP_HEIGHT - 1))
		{
			px = xx;
			py = yy;
			selectedTile = tiles[px][py];
			if(selectedTile.hasUnit() && selectedTile.getUnit().getPlayer().playerNum == 1)
				SoundManager.playSound(SoundManager.ROBOT_SELECT, 0.5f);
		}
	}

	public void setPointLocation(int x, int y)
	{
		if(this.gameStage == STAGE.PLAN)
		{
			px = MathUtil.clamp(0, MAP_WIDTH - 1, (x - xOffset) / TILE_SIZE);
			py = MathUtil.clamp(0, MAP_HEIGHT - 1, ((Gdx.graphics.getHeight() - y) - yOffset) / TILE_SIZE);
		}
	}

	public void clearTileAt(int x, int y)
	{
		int xx = (x - xOffset) / TILE_SIZE;
		int yy = ((Gdx.graphics.getHeight() - y) - yOffset) / TILE_SIZE;
		if(MathUtil.inBounds(xx, yy, 0, 0, MAP_WIDTH - 1, MAP_HEIGHT - 1))
		{
			this.player1.removePlannedMoveAt(xx, yy);
			SoundManager.playSound(SoundManager.UNDO, 0.75f);
		}
	}

	public void processPoint()
	{
		if(gameStage != STAGE.PLAN)
			return;

		Tile newLoc = tiles[px][py];
		if(selectedTile != null && newLoc != null && selectedTile.hasUnit())
		{
			Unit unit = selectedTile.getUnit();
			Vector2 newPos = new Vector2(px, py);
			int cost = (int) (MathUtil.distance(selectedTile.getX(), selectedTile.getY(), px, py) * 5);
			if(unit.getPlayer().getPower() >= cost && unit.getPlayer().isValidMove(unit, newPos))
			{
				unit.getPlayer().removePower(cost);
				unit.getPlayer().addPlannedMove(unit, new Vector2(selectedTile.getX(), selectedTile.getY()), newPos, cost);
				SoundManager.playSound(SoundManager.ROBOT_PLACE, 0.5f);
			}
		}
		selectedTile = null;
	}

	public void pan(int xChange, int yChange)
	{
		xOffset = xOffset + xChange;
		yOffset = yOffset + yChange;
	}

	public void zoom(int amount)
	{
		scale = MathUtil.clamp(1, 10, scale + amount);
	}

	public Player getPlayer1()
	{
		return this.player1;
	}

	public Player getPlayer2()
	{
		return this.player2;
	}

	public STAGE getGameStage()
	{
		return this.gameStage;
	}

	public boolean isAttacking()
	{
		return currentAttack != null;
	}

	public Tile getTileAt(Vector2 v)
	{
		return this.getTileAt((int) v.x, (int) v.y);
	}

	public Tile getTileAt(int x, int y)
	{
		if(MathUtil.inBounds(x, y, 0, 0, MAP_WIDTH - 1, MAP_HEIGHT - 1))
			return tiles[x][y];
		return null;
	}

	public void moveUnit(Vector2 curentPos, Vector2 newPos)
	{
		Tile newT = this.getTileAt((int) newPos.x, (int) newPos.y);
		Tile oldT = this.getTileAt((int) curentPos.x, (int) curentPos.y);
		if(newT.hasUnit())
			newT.removeUnit().kill();
		Unit unit = oldT.removeUnit();
		if(unit != null)
		{
			unit.setPos(newPos);
			newT.setUnit(unit);
			if(newT.hasPower())
				unit.getPlayer().addPower(newT.removePower());
		}
	}

	public void endGame(boolean player1Won)
	{
		Settings.player1Won = player1Won;
		ScreenManager.INSTANCE.setCurrentScreen("Game_Over_Screen");
	}

	public enum STAGE
	{
		PLAN, ATTACK;
	}
}