package entities;

import javafx.scene.image.Image;
import sprite.Sprite;

public class Frog extends Enemy {
	public static final double FROG_HEALTH = 40;
	public static final double FROG_SPEED = 50;
	public static final Image FROG_IMAGE = new Image("images\\frog.png", 50, 50, true, true);
	public static final Image FROG_RED_IMAGE = new Image("images\\redfrog.png", 50, 50, true, true);
	
	public Frog() {
		super(FROG_HEALTH, FROG_SPEED, FROG_SPEED);
		((Sprite) this).setImage(FROG_IMAGE, 50, 50);
		((Sprite) this).setRedImage(FROG_RED_IMAGE);
	}
}
