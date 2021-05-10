package de.paluno.game.gameobjects;

import de.paluno.game.screens.Gamemode;

public class FlowerFactory {
	
	public static F_L_O_W_E_R create(Blumenauswahl auswahl, Gamemode playScreen, Tank actor) 
	{
		if (actor instanceof Tower) {
			
			return new TowerFlower(
					playScreen,
					actor.getBody().getPosition().cpy().add(actor.getAim().cpy().nor().scl(95)), 
					actor.getAim(), 
					actor);
			
		} else {
			
		switch (auswahl) {			

			case FASTFLOWER:
				return new FastFlower(
						playScreen, 
						actor.getBody().getPosition().cpy().add(actor.getAim().cpy().nor().scl(30)),
						actor.getAim(), 
						actor);		
				
			case BOUNCEFLOWER: 
				return new BounceFlower(
						playScreen, 
						actor.getBody().getPosition().cpy().add(actor.getAim().cpy().nor().scl(30)),
						actor.getAim(), 
						actor);					
			case SPLITTEDFLOWER:
				return new TripleFlower(
						playScreen,
						actor.getBody().getPosition().cpy().add(actor.getAim().cpy().nor().scl(30)), 
						actor.getAim(),
						actor);

			case NORMALFLOWER:
			default:
				return new NormalFlower(
						playScreen, 
						actor.getBody().getPosition().cpy().add(actor.getAim().cpy().nor().scl(30)), 
						actor.getAim(), 
						actor);					
			}
		}
	}
}
