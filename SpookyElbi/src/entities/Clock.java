/********************************************************
 * 
 * @author       Cabral, Alexa Gwen; Villamin, Jan Neal Isaac
 * @date_created 14:48 2023-12-20
 * 
 ********************************************************/

package entities;

import javafx.scene.image.Image;
import sprite.Sprite;

public class Clock extends Enemy {
	public static final double CLOCK_HEALTH = 60;
	public static final double CLOCK_SPEED = 80;
	public static final Image CLOCK_IMAGE = new Image("images\\clock.png", 50, 50, true, true);
	public static final Image CLOCK_RED_IMAGE = new Image("images\\redclock.png", 50, 50, true, true);
	
	public Clock() {
		super(CLOCK_HEALTH, CLOCK_SPEED, CLOCK_SPEED);
		((Sprite) this).setImage(CLOCK_IMAGE, 50, 50);
		((Sprite) this).setRedImage(CLOCK_RED_IMAGE);
		this.drops = 3;
	}
}
