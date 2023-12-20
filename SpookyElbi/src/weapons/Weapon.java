/********************************************************
 * 
 * 
 * 
 * @author       Cabral, Alexa Gwen; Villamin, Jan Neal Isaac
 * @date_created 10:44 2023-12-04
 * 
 ********************************************************/

package weapons;

import java.util.ArrayList;
import sprite.Sprite;
import javafx.scene.media.AudioClip;

public class Weapon extends Sprite {
	private String bulletImage;
	private int ammoCount;
	private long weaponDelay;
	private long reloadDelay;
	private double damage;
	protected double bulletSpeed;
	protected double maxDistance;
	protected String soundFile;
	protected ArrayList<Sprite> bullets;
	public ArrayList<Sprite> shotBullets;
	
	public Weapon(long weaponDelay, long reloadDelay, double damage) {
		this.weaponDelay = weaponDelay;
		this.reloadDelay = reloadDelay;
		this.damage = damage;
		this.bullets = new ArrayList<Sprite>();
		this.shotBullets = new ArrayList<Sprite>();
		this.reload();
	}
	
	public void setBulletImage(String bulletImage) {
		this.bulletImage = bulletImage;
	}
	
	public void setAmmoCount(int ammoCount) {
		this.ammoCount = ammoCount;
	}
	
	public void shoot(double x, double y) {
		if (this.bullets.isEmpty()) {
			return;
		}
		
		this.playShotSound();
		Sprite removedBullet = this.bullets.remove(this.bullets.size() - 1);
		Sprite selfReference = this;
		((Bullet) removedBullet).setStarting(selfReference.getPositionX(), selfReference.getPositionY());
		((Bullet) removedBullet).setDirection(x, y, this);
		this.shotBullets.add(removedBullet);
		removedBullet.setPosition(this.positionX, this.positionY);
	}
	
	public void playShotSound() {
		try {
            AudioClip shotSound = new AudioClip(getClass().getResource("/audio/" + this.soundFile).toString());
            
            // Play the sound
            shotSound.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public void reload() {
		while (this.bullets.size() < this.ammoCount) {
			Sprite bullet = new Bullet();
			((Bullet) bullet).setMaxDistance(this.maxDistance);
			this.bullets.add(bullet);
			bullet.setImage(this.bulletImage, 10, 10);
			((Bullet) bullet).setDamage(this.damage);
		}
	}
	
	public long getWeaponDelay() {
		return this.weaponDelay;
	}
	
	public long getReloadDelay() {
		return this.reloadDelay;
	}
	
	public long getAmmoCount() {
		return this.bullets.size();
	}
	
	public double getBulletSpeed() {
		return this.bulletSpeed;
	}
}
