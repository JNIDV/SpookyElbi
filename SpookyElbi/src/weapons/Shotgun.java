package weapons;

import javafx.scene.image.Image;
import sprite.Sprite;

public class Shotgun extends Weapon {
	public static final long SHOTGUN_DELAY = 700;
	public static final long SHOTGUN_RELOAD_DELAY = 4000;
	public static final long SHOTGUN_DAMAGE = 100;
	public static final double SHOTGUN_BULLET_SPEED = 500;
	public static final double SHOTGUN_MAX_DISTANCE = 400;
	public static final Image SHOTGUN_IMAGE = new Image("images\\shotgun.png", 30, 30, true, true);
	public static final String SHOTGUN_BULLET_IMAGE = "images\\shotgunShell.png";
	
	public Shotgun() {
		super(SHOTGUN_DELAY, SHOTGUN_RELOAD_DELAY, SHOTGUN_DAMAGE);
		((Sprite) this).setImage(SHOTGUN_IMAGE);
		((Weapon) this).setAmmoCount(9);
		((Weapon) this).setBulletImage(SHOTGUN_BULLET_IMAGE);
		this.bulletSpeed = SHOTGUN_BULLET_SPEED;
		this.maxDistance = SHOTGUN_MAX_DISTANCE;
	}
	
	public void shoot(double x, double y) {
		super.shoot(x, y);
		
		for (int i = 0; i <= 2; i += 2) {
			Sprite removedBullet = this.bullets.remove(this.bullets.size() - 1);
			Sprite selfReference = this;
			((Bullet) removedBullet).setStarting(selfReference.getPositionX(), selfReference.getPositionY());
			double dx = x - selfReference.getPositionX();
			double dy = y - selfReference.getPositionY();
			((Bullet) removedBullet).setDirection(
				selfReference.getPositionX() + dx * Math.cos(Math.PI / 12 * (i - 1)) - dy * Math.sin(Math.PI / 12 * (i - 1)), 
				selfReference.getPositionY() + dx * Math.sin(Math.PI / 12 * (i - 1)) + dy * Math.cos(Math.PI / 12 * (i - 1)), 
				this
			);
			this.shotBullets.add(removedBullet);
			removedBullet.setPosition(this.positionX, this.positionY);
		}
	}
}
