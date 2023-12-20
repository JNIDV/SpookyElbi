package weapons;

import javafx.scene.image.Image;
import sprite.Sprite;

public class Shotgun extends Weapon {
	public static final long SHOTGUN_DELAY = 700;
	public static final long SHOTGUN_RELOAD_DELAY = 4000;
	public static final long SHOTGUN_DAMAGE = 100;
	public static final Image SHOTGUN_IMAGE = new Image("images\\shotgun.png", 30, 30, true, true);
	public static final String SHOTGUN_BULLET_IMAGE = "images\\shotgunShell.png";
	
	public Shotgun() {
		super(SHOTGUN_DELAY, SHOTGUN_RELOAD_DELAY, SHOTGUN_DAMAGE);
		((Sprite) this).setImage(SHOTGUN_IMAGE);
		((Weapon) this).setAmmoCount(9);
		((Weapon) this).setBulletImage(SHOTGUN_BULLET_IMAGE);
	}
	
	@Override
	public void shoot(double x, double y) {
		if (this.bullets.isEmpty()) {
			return;
		}
		
		for (int i = 0; i < 3; i++) {
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
		
		
//		System.out.println("Current ammo: " + this.bullets.size());
	}
}
