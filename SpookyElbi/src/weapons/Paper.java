package weapons;

import javafx.scene.image.Image;
import sprite.Sprite;

public class Paper extends Weapon {
	public static final long PAPER_DELAY = 50;
	public static final long PAPER_RELOAD_DELAY = 1000;
	public static final long PAPER_DAMAGE = 5;
	public static final double PAPER_BULLET_SPEED = 250;
	public static final double PAPER_MAX_DISTANCE = 350;
	public static final Image PAPER_IMAGE = new Image("images\\paper.png", 15, 15, true, true);
	public static final String PAPER_BULLET_IMAGE = "images\\paper.png";
	
	public Paper() {
		super(PAPER_DELAY, PAPER_RELOAD_DELAY, PAPER_DAMAGE);
		((Sprite) this).setImage(PAPER_IMAGE);
		((Weapon) this).setAmmoCount(15);
		((Weapon) this).setBulletImage(PAPER_BULLET_IMAGE);
		this.bulletSpeed = PAPER_BULLET_SPEED;
		this.maxDistance = PAPER_MAX_DISTANCE;
		this.soundFile   = "paperThrow.mp3";
	}
}
