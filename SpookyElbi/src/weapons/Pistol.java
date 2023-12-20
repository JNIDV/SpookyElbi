package weapons;

import javafx.scene.image.Image;
import sprite.Sprite;

public class Pistol extends Weapon {
	public static final long PISTOL_DELAY = 300;
	public static final long PISTOL_RELOAD_DELAY = 2500;
	public static final long PISTOL_DAMAGE = 40;
	public static final double PISTOL_BULLET_SPEED = 500;
	public static final double PISTOL_MAX_DISTANCE = 600;
	public static final Image PISTOL_IMAGE = new Image("images\\pistol.png", 20, 20, true, true);
	public static final String PISTOL_BULLET_IMAGE = "images\\pistolBullet.png";
	
	public Pistol() {
		super(PISTOL_DELAY, PISTOL_RELOAD_DELAY, PISTOL_DAMAGE);
		((Sprite) this).setImage(PISTOL_IMAGE);
		((Weapon) this).setAmmoCount(6);
		((Weapon) this).setBulletImage(PISTOL_BULLET_IMAGE);
		this.bulletSpeed = PISTOL_BULLET_SPEED;
		this.maxDistance = PISTOL_MAX_DISTANCE;
	}
}
