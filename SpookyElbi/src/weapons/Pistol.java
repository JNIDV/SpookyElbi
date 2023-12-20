package weapons;

import javafx.scene.image.Image;

public class Pistol extends Weapon {
	public static final long PISTOL_DELAY = 300;
	public static final long PISTOL_RELOAD_DELAY = 2500;
	public static final long PISTOL_DAMAGE = 40;
	public static final double PISTOL_BULLET_SPEED = 500;
	public static final double PISTOL_MAX_DISTANCE = 600;
	public static final Image PISTOL_IMAGE = new Image("images\\pistol.png", 20, 20, true, true);
	public static final String PISTOL_BULLET_IMAGE = "images\\pistolBullet.png";
	public static final String PISTOL_MP3 = "pistol.mp3";
	public static final String PISTOL_RELOAD_MP3 = "pistolReload.mp3";
	
	public Pistol() {
		super(PISTOL_DELAY, PISTOL_RELOAD_DELAY, PISTOL_DAMAGE);
		this.setImage(PISTOL_IMAGE);
		this.setAmmoCount(6);
		this.setBulletImage(PISTOL_BULLET_IMAGE);
		this.bulletSpeed = PISTOL_BULLET_SPEED;
		this.maxDistance = PISTOL_MAX_DISTANCE;
		this.soundFile = PISTOL_MP3;
		this.reloadFile = PISTOL_RELOAD_MP3;
		this.reload();
	}
}
