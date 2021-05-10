package de.paluno.game.gameobjects;

import java.util.ArrayList;

import de.paluno.game.screens.Deathmatch;

public class Team {

	protected ArrayList<Tank> tanks = new ArrayList<Tank>();
	
	public Team () {
	}

	public void addTank(Tank tank) {
		this.tanks.add(tank);
	}
	
	public boolean isAlive() {
		for (Tank tank : this.tanks) {
			if (tank.isAlive()) {
				return true;
			}
		}
		
		return false;
	}
	
}
