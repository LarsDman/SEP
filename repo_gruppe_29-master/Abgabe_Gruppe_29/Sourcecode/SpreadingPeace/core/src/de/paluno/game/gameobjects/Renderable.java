package de.paluno.game.gameobjects;

public interface Renderable {
//Renderable objects are those that can be rendered, thus being visible to the players.

	void render(com.badlogic.gdx.graphics.g2d.SpriteBatch batch, float delta);
	// batch - The SpriteBatch that the object should be rendered to.
	// delta - The time in seconds between the last frames.
}
