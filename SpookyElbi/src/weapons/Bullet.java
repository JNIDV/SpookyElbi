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
import entities.Enemy;

public class Bullet extends Sprite {
	private double damage;
	private double startingX;
	private double startingY;
	private double directionX;
	private double directionY;
	private double maxDistance;
	private int hitCount = 0;
	
	public void setStarting(double x, double y) {
		this.startingX = x;
		this.startingY = y;
	}
	
	public int getHitCount() {
		return this.hitCount;
	}
	
	public void setDirection(double x, double y, Weapon weapon) {
		double dx = x - weapon.getPositionX();
		double dy = y - weapon.getPositionY();
		double norm = Math.sqrt(dx * dx + dy * dy);
		
		if (norm == 0) {
			this.directionX = 0;
			this.directionY = 0;
			return;
		}
		
		this.directionX = dx / norm;
		this.directionY = dy / norm;
		double angle = Math.toDegrees(Math.atan2(this.directionY, this.directionX));
		
		if (angle < 0) {
			angle += 360;
		}
		
		this.rotateImage(angle);
		weapon.rotateImage(angle);
	}
	
	public void setDirection(double x, double y, Weapon weapon, boolean turn) {
		double dx = x - weapon.getPositionX();
		double dy = y - weapon.getPositionY();
		double norm = Math.sqrt(dx * dx + dy * dy);
		
		if (norm == 0) {
			this.directionX = 0;
			this.directionY = 0;
			return;
		}
		
		this.directionX = dx / norm;
		this.directionY = dy / norm;
		double angle = Math.toDegrees(Math.atan2(this.directionY, this.directionX));
		
		if (angle < 0) {
			angle += 360;
		}
		
		this.rotateImage(angle);
		
		if (turn) {
			weapon.rotateImage(angle);
		}
	}
	
	public boolean checkEnemiesCollision(ArrayList<Sprite> enemies) {
		boolean collided = false;
		
		for (Sprite enemy : enemies) {
			if (enemy.intersects(this)) {
				this.hitCount++;
				collided = true;
				((Enemy) enemy).decreaseHealth(this.damage);
				enemy.getHit();
				break;
			}
		}
		
		return collided;
	}
	
	@Override
	public void update(double time) {
		Sprite selfReference = this;
		selfReference.setPosition(
			selfReference.getPositionX() + selfReference.getVelocityX() * time,
			selfReference.getPositionY() + selfReference.getVelocityY() * time
		);	
	}
	
	public double getDirectionX() {
		return this.directionX;
	}
	
	public double getDirectionY() {
		return this.directionY;
	}
	
	public void setDamage(double damage) {
		this.damage = damage;
	}
	
	public void setMaxDistance(double maxDistance) {
		this.maxDistance = maxDistance;
	}
	
	public boolean reachedMaxRange() {
		double dx = this.getPositionX() - this.startingX;
		double dy = this.getPositionY() - this.startingY;
		double distance = Math.sqrt(dx * dx + dy * dy);
		return distance >= this.maxDistance;
	}
}
