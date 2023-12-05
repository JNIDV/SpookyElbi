package weapons;

import java.util.ArrayList;
import sprite.Sprite;

public class Weapon extends Sprite {
	public static final int AMMO_COUNT = 6;
	
	private ArrayList<Sprite> bullets;
	public ArrayList<Sprite> shotBullets;
	
	public Weapon() {
		this.bullets = new ArrayList<Sprite>();
		this.shotBullets = new ArrayList<Sprite>();
		this.reload();
	}
	
	public void shoot(double x, double y) {
		if (this.bullets.isEmpty()) {
			return;
		}
		
		Sprite removedBullet = this.bullets.remove(this.bullets.size() - 1);
		((Bullet) removedBullet).setDirection(x, y, this);
		this.shotBullets.add(removedBullet);
		removedBullet.setPosition(this.positionX, this.positionY);
		
		System.out.println("Current ammo: " + this.bullets.size());
	}
	
	public void reload() {
		while (this.bullets.size() < Weapon.AMMO_COUNT) {
			Sprite bullet = new Bullet();
			this.bullets.add(bullet);
			bullet.setImage("images\\pen.png");
		}
		
		System.out.println("Current ammo: " + this.bullets.size());
	}
}
