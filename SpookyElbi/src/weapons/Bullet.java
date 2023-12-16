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
	public final static double BULLET_SPEED = 150;
	public final static double MAX_DISTANCE = 300;
	
	private double damage;
	private double startingX;
	private double startingY;
	private double directionX;
	private double directionY;
	
	public void setStarting(double x, double y) {
		this.startingX = x;
		this.startingY = y;
	}
	
	public void setDirection(double x, double y, Weapon weapon) {
		double dx = x - ((Sprite) weapon).getPositionX();
		double dy = y - ((Sprite) weapon).getPositionY();
		double norm = Math.sqrt(dx * dx + dy * dy);
		
		if (norm == 0) {
			this.directionX = 0;
			this.directionY = 0;
			return;
		}
		
		this.directionX = dx / norm;
		this.directionY = dy / norm;
		((Sprite) this).rotateImage(180 * Math.atan(this.directionY / this.directionX) / Math.PI);
	}
	
	public boolean checkEnemiesCollision(ArrayList<Sprite> enemies) {
		boolean collided = false;
		
		for (Sprite enemy : enemies) {
			if (enemy.intersects(this)) {
				collided = true;
				((Enemy) enemy).decreaseHealth(this.damage);
				((Enemy) enemy).turnRed();
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
	
	public boolean reachedMaxRange() {
		Sprite selfReference = this;
		double dx = selfReference.getPositionX() - this.startingX;
		double dy = selfReference.getPositionY() - this.startingY;
		double distance = Math.sqrt(dx * dx + dy * dy);
		return distance >= Bullet.MAX_DISTANCE;
	}
}
