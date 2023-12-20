package entities;

import javafx.scene.image.Image;
import sprite.Sprite;

public class Exam extends Enemy {
	public static final double EXAM_HEALTH = 100;
	public static final double EXAM_SPEED = 70;
	public static final Image EXAM_IMAGE = new Image("images\\exam.png", 50, 50, true, true);
	public static final Image EXAM_RED_IMAGE = new Image("images\\redexam.png", 50, 50, true, true);
	
	public Exam() {
		super(EXAM_HEALTH, EXAM_SPEED, EXAM_SPEED);
		((Sprite) this).setImage(EXAM_IMAGE, 50, 50);
		((Sprite) this).setRedImage(EXAM_RED_IMAGE);
	}
}
