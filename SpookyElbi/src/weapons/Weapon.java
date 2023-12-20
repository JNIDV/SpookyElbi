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

public class Weapon extends Sprite {
	private String bulletImage;
	private int ammoCount;
	private long weaponDelay;
	private long reloadDelay;
	private double damage;
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
		
		Sprite removedBullet = this.bullets.remove(this.bullets.size() - 1);
		Sprite selfReference = this;
		((Bullet) removedBullet).setStarting(selfReference.getPositionX(), selfReference.getPositionY());
		((Bullet) removedBullet).setDirection(x, y, this);
		this.shotBullets.add(removedBullet);
		removedBullet.setPosition(this.positionX, this.positionY);
		
//		System.out.println("Current ammo: " + this.bullets.size());
	}
	
	public void reload() {
		while (this.bullets.size() < this.ammoCount) {
			Sprite bullet = new Bullet();
			this.bullets.add(bullet);
			bullet.setImage(this.bulletImage, 10, 10);
			((Bullet) bullet).setDamage(this.damage);
		}
		
//		System.out.println("Current ammo: " + this.bullets.size());
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
}
