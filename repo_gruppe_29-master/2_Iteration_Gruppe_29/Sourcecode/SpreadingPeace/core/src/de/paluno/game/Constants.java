package de.paluno.game;

import com.badlogic.gdx.math.Vector2;

//It is bad practice to define constants in the code where they need to be used. In most cases it is better do define most of the literals a program uses in a single place, better even if that is a configuration file that can be tweaked after the source code is compiled.
//This class should store those literals. For demonstration purposes it now only contains one value, but more should be added in the development process.

public class Constants extends java.lang.Object {

	public static final float PHYSICSSTEPLENGTH = 1f / 300f;
	// All the steps of libGDX´ physics simulation should be of the same length.
	public static final float PPM = 32; // PPM= PixerlPerMeters
	public static final float SPEED = 60;
	public static final int BILDSCHIRMBREITE = 1920;
	public static final int BILDSCHIRMHOEHE = 1080;
	
	public static final int FLOWERSIZE=10; //f�r
	public static final int TANKSIZE=55;   //die 
	public static final int TARGETSIZE=30; //collision-hitboxen
	
	
	
	public Constants() {
		
	}
	public static float vectorToAngle (Vector2 vector) {
		return (float)Math.atan2(-vector.x, vector.y);
	}
	public static Vector2 angleToVector (Vector2 outVector, float angle) {
		outVector.x = -(float)Math.asin(angle);
		outVector.y = (float)Math.cos(angle);
		return outVector;
	}
	
}

