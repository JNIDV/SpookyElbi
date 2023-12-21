/********************************************************
 * 
 * A class that contains the logic of shotgun.
 * 
 * It overrides the shoot since it shoots multiple bullets.
 * 
 * @author       Cabral, Alexa Gwen; Villamin, Jan Neal Isaac
 * @date_created 10:44 2023-12-04
 * 
 ********************************************************/

package weapons;

import javafx.scene.image.Image;

public class Shotgun extends Weapon {
	public static final long SHOTGUN_DELAY = 1200;
	public static final long SHOTGUN_RELOAD_DELAY = 4000;
	public static final long SHOTGUN_DAMAGE = 100;
	public static final double SHOTGUN_BULLET_SPEED = 500;
	public static final double SHOTGUN_MAX_DISTANCE = 400;
	public static final Image SHOTGUN_IMAGE = new Image("images\\shotgun.png", 30, 30, true, true);
	public static final String SHOTGUN_BULLET_IMAGE = "images\\shotgunShell.png";
	public static final String SHOTGUN_MP3 = "shotgun.mp3";
	public static final String SHOTGUN_RELOAD_MP3 = "shotgunReload.mp3";
	
	public Shotgun() {
		super(SHOTGUN_DELAY, SHOTGUN_RELOAD_DELAY, SHOTGUN_DAMAGE);
		this.setImage(SHOTGUN_IMAGE);
		this.setAmmoCount(15);
		this.setBulletImage(SHOTGUN_BULLET_IMAGE);
		this.bulletSpeed = SHOTGUN_BULLET_SPEED;
		this.maxDistance = SHOTGUN_MAX_DISTANCE;
		this.soundFile = SHOTGUN_MP3;
		this.reloadFile = SHOTGUN_RELOAD_MP3;
		this.reload();
	}
	
	@Override
	public boolean shoot(double x, double y) {
		double angleOffset = Math.PI / 24;
		
		for (int i = -2; i <= 2; i++) {
			double dx = x - this.getPositionX(), dy = y - this.getPositionY();
			double x1 = this.getPositionX() + dx * Math.cos(angleOffset * i) - dy * Math.sin(angleOffset * i);
			double y1 = this.getPositionY() + dx * Math.sin(angleOffset * i) + dy * Math.cos(angleOffset * i);
			
			if (!super.shootNoSound(x1, y1)) {
				return false;
			}
			
			if (this.multipleBullets) {
				super.shootMultiple(x1, y1);
			}
		}
		
		this.playShotSound();
		return true;
	}
}
