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
	public static final String PAPER_DESCRIPTION = "\n" + "Damage: 5 \n" +
			"Rate of fire: 100\n" +
			"Number of bullets per ‘magazine’: 15\n" +
			"Penetration: 1\n" +
			"Speed: 10\n" +
			"Range: 5";
	public static final String PEN_DESCRIPTION = "\n" + "Damage: 20\n" +
			"Rate of fire: 50\n" +
			"Number of bullets per ‘magazine’: 8\n" +
			"Penetration: 1\n" +
			"Speed: 10\n" +
			"Range: 8";
	public static final String PISTOL_DESCRIPTION = "\n" + "Damage: 40\n" +
			"Rate of fire: 40\n" +
			"Number of bullets per ‘magazine’: \n" +
			"Penetration: \n" +
			"Speed: 5\n" +
			"Range: 10";
	public static final String SHOTGUN_DESCRIPTION = "\n" + "Damage: 100\n" +
			"Rate of fire: 30\n" +
			"Number of bullets per ‘magazine’: 15\n" +
			"Penetration: 2\n" +
			"Speed: 2\n" +
			"Range: 7";
	
	
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
	public boolean multipleBullets = false;
	
	public Weapon(long weaponDelay, long reloadDelay, double damage) {
		this.weaponDelay = weaponDelay;
		this.reloadDelay = reloadDelay;
		this.damage = damage;
		this.bullets = new ArrayList<Sprite>();
		this.shotBullets = new ArrayList<Sprite>();
	}
	
	public void setMultipleBullets(boolean multipleBullets) {
		this.multipleBullets = multipleBullets;
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
		
		if (this.multipleBullets) {
			this.shootMultiple(x, y);
		}
		
		this.playShotSound();
		return true;
	}
	
	public boolean shootMultiple(double x, double y) {
		double angleOffset = Math.PI / 2;
		
		for (int i = 1; i <= 3; i++) {
			double dx = x - this.getPositionX(), dy = y - this.getPositionY();
			double x1 = this.getPositionX() + dx * Math.cos(angleOffset * i) - dy * Math.sin(angleOffset * i);
			double y1 = this.getPositionY() + dx * Math.sin(angleOffset * i) + dy * Math.cos(angleOffset * i);
			
			this.reloadOne();
			if (!this.shootNoSound(x1, y1, false)) {
				return false;
			}
		}
		
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
	
	public boolean shootNoSound(double x, double y, boolean turn) {
		if (this.bullets.isEmpty()) {
			return false;
		}
		
		Bullet removedBullet = (Bullet) this.bullets.remove(this.bullets.size() - 1);
		removedBullet.setStarting(this.getPositionX(), this.getPositionY());
		removedBullet.setDirection(x, y, this, turn);
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
			this.reloadOne();
		}
	}
	
	private void reloadOne() {
		Sprite bullet = new Bullet();
		((Bullet) bullet).setMaxDistance(this.maxDistance);
		this.bullets.add(bullet);
		bullet.setImage(this.bulletImage, 10, 10);
		((Bullet) bullet).setDamage(this.damage);
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
