package de.paluno.game.gameobjects;

public interface PhysicsObject {
//Physics objects are managed by libGDX´ physics simulation. Therefore they should hold a reference to their body object.
//In order to allow the CollisionHandler to filter through the collisions of different PhysicsObjects, it is common practice to set the user data of the fixtures that make up a PhysicsObject´s body to the PhysicsObject instance itself. This way the CollisionHandler can use the instanceof keyword to determine what kind of PhysicsOjbects are actually colliding.

	void setupBody();

	com.badlogic.gdx.physics.box2d.Body getBody();
	//Returns a reference to this object´s body.
	void setBodyToNullReference();
	//After a body is destroyed, the reference to that body object should never be used again. Because if it is, there is a good chance that the entire game will crash. Horribly. Producing a fire that will kill at least five kittens.
	//Though such errors are indeterministic and hard to debug, they can be avoided entirely by only ever having one reference to a body object and setting it to null after the body is destroyed. That way, whenever the reference is used again, a deterministic and easy to debug NullPointerException is thrown. Honestly, handling body references that way is a lot less trouble. Trust me, I´m an engineer.
	//Long story short, this method should just set this instance´s body reference to null, after setting its user data null as well.
	
}