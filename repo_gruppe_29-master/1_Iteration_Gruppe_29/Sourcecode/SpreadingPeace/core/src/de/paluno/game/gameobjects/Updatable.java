package de.paluno.game.gameobjects;

public interface Updatable {
//Updatable objects should do something on their own. What that is depends on the actual type of the object. Some objects, like worms, need to react to the user input, whilst others, like projectiles, need to perform other tasks.

	void update(float delta);

}
