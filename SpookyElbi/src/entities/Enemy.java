/********************************************************
 * 
 * 
 * 
 * @author       Cabral, Alexa Gwen; Villamin, Jan Neal Isaac
 * @date_created 10:44 2023-12-04
 * 
 ********************************************************/

package entities;

import java.util.ArrayList;

import sprite.Sprite;

public class Enemy extends Sprite {
	public static double speedFactor = 1.25;
	public static double healthFactor = 1.25;
	
	protected int drops;
	private double health;
	private double speedX;
	private double speedY;
	private double directionX;
	private double directionY;
	
	public Enemy(double health, double speedX, double speedY) {
		this.health = health;
		this.speedX = speedX;
		this.speedY = speedY;
	}
	
	public void updateDirection(Sprite mainCharacter, ArrayList<Sprite> enemies) {
		double mainCharacterX = mainCharacter.getPositionX();
		double mainCharacterY = mainCharacter.getPositionY();
		
		// Vector <x, y> must be <x1 - x2, y1 - y2> where mc is at (x1, y1) and enemy is at (x2, y2)
		double dx = mainCharacterX - this.positionX;
		double dy = mainCharacterY - this.positionY;
		
		// Norm is sqrt(dx^2 + dy^2)
		double norm = Math.sqrt(dx * dx + dy * dy);
		
		if (norm == 0) {
			this.directionX = 0;
			this.directionY = 0;
			return;
		}
		
		this.directionX = dx / norm;
		this.directionY = dy / norm;
		
		for (Sprite otherEnemy : enemies) {
	        if (otherEnemy != (Sprite) this && otherEnemy.intersects(this)) {
	        	double otherDx = otherEnemy.getPositionX() - this.positionX;
	        	double otherDy = otherEnemy.getPositionY() - this.positionY;
	        	double distance = Math.sqrt(otherDx * otherDx + otherDy * otherDy);

	            // bump
	        	this.directionX -= otherDx / distance;
	        	this.directionY -= otherDy / distance;
	        }
        }
		
		if (mainCharacter.intersects(this)) {
			double otherDx = mainCharacter.getPositionX() - this.positionX;
			double otherDy = mainCharacter.getPositionY() - this.positionY;
			double distance = Math.sqrt(otherDx * otherDx + otherDy * otherDy);

            // bump
			this.directionX -= otherDx / distance;
			this.directionY -= otherDy / distance;
		}
	}
	
	public boolean isDead() {
		return this.health <= 0;
	}
	
	public void speedUp() {
		this.health = Enemy.healthFactor * this.health;
		this.speedX = Enemy.speedFactor * this.speedX;
		this.speedY = Enemy.speedFactor * this.speedY;
	}
	
	public double getDirectionX() {
		return this.directionX;
	}
	
	public double getDirectionY() {
		return this.directionY;
	}
	
	public double getSpeedX() {
		return this.speedX;
	}
	
	public double getSpeedY() {
		return this.speedY;
	}
	
	public void decreaseHealth(double dHealth) {
		System.out.println("Decreased: " + dHealth);
		this.health -= dHealth;
	}
	
	public double getDrops() {
		return this.drops;
	}
}
