package weapons;

import javafx.scene.image.Image;

public class Pen extends Weapon {
	public static final long PEN_DELAY = 200;
	public static final long PEN_RELOAD_DELAY = 2000;
	public static final long PEN_DAMAGE = 20;
	public static final double PEN_BULLET_SPEED = 300;
	public static final double PEN_MAX_DISTANCE = 300;
	public static final Image PEN_IMAGE = new Image("images\\pen.png", 10, 10, true, true);
	public static final String PEN_BULLET_IMAGE = "images\\pen.png";
	public static final String PEN_MP3 = "pen.mp3";
	public static final String PEN_RELOAD_MP3 = "penReload.mp3";
	
	public Pen() {
		super(PEN_DELAY, PEN_RELOAD_DELAY, PEN_DAMAGE);
		this.setImage(PEN_IMAGE);
		this.setAmmoCount(8);
		this.setBulletImage(PEN_BULLET_IMAGE);
		this.bulletSpeed = PEN_BULLET_SPEED;
		this.maxDistance = PEN_MAX_DISTANCE;
		this.soundFile = PEN_MP3;
		this.reloadFile = PEN_RELOAD_MP3;
		this.reload();
	}
}
