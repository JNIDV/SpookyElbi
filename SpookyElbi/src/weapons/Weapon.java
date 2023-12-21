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
	protected String reloadFile;
	protected ArrayList<Sprite> bullets;
	public ArrayList<Sprite> shotBullets;
	
	public Weapon(long weaponDelay, long reloadDelay, double damage) {
		this.weaponDelay = weaponDelay;
		this.reloadDelay = reloadDelay;
		this.damage = damage;
		this.bullets = new ArrayList<Sprite>();
		this.shotBullets = new ArrayList<Sprite>();
	}
	
	public void increaseDamage(double dDamage) {
		this.damage += dDamage;
		
		for (Sprite bullet : this.bullets) {
			((Bullet) bullet).setDamage(this.damage);
		}
	}
	
	public void setBulletImage(String bulletImage) {
		this.bulletImage = bulletImage;
	}
	
	public void setAmmoCount(int ammoCount) {
		this.ammoCount = ammoCount;
	}
	
	public boolean shoot(double x, double y) {
		if (!shootNoSound(x, y)) {
			return false;
		}
		
		this.playShotSound();
		return true;
	}
	
	public boolean shootNoSound(double x, double y) {
		if (this.bullets.isEmpty()) {
			return false;
		}
		
		Bullet removedBullet = (Bullet) this.bullets.remove(this.bullets.size() - 1);
		removedBullet.setStarting(this.getPositionX(), this.getPositionY());
		removedBullet.setDirection(x, y, this);
		removedBullet.setPosition(this.positionX, this.positionY);
		this.shotBullets.add(removedBullet);
		return true;
	}
	
	protected void playShotSound() {
		try {
            AudioClip shotSound = new AudioClip(getClass().getResource("/audio/" + this.soundFile).toString());
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
	
	public void reloadWithSound() {
		this.playReloadSound();
		this.reload();
	}
	
	private void playReloadSound() {
		try {
            AudioClip reloadSound = new AudioClip(getClass().getResource("/audio/" + this.reloadFile).toString());
            reloadSound.play();
        } catch (Exception e) {
            e.printStackTrace();
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
