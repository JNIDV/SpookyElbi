package entities;

import java.util.ArrayList;

import sprite.Sprite;

public class Enemy extends Sprite {
	public final static double SPEED_INCREASE = 5;
//	private double health;
	private double directionX;
	private double directionY;
	private double speedX;
	private double speedY;
	
	public Enemy() {
//		this.health = 100;
		this.speedX = 50;
		this.speedY = 50;
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
		
		// directionX = dx / norm
		// directionY = dy / norm
		this.directionX = dx / norm;
		this.directionY = dy / norm;
		
		for (Sprite otherEnemy : enemies) {
	        if (otherEnemy != (Sprite) this && otherEnemy.intersects(this)) {
	        	double otherDx = otherEnemy.getPositionX() - this.positionX;
	        	double otherDy = otherEnemy.getPositionY() - this.positionY;
	        	double distance = Math.sqrt(otherDx * otherDx + otherDy * otherDy);

	            // If too close to another enemy, adjust movement to avoid overlap
	        	this.directionX -= otherDx / distance;
	        	this.directionY -= otherDy / distance;
	        }
        }
		
		if (mainCharacter.intersects(this)) {
			double otherDx = mainCharacter.getPositionX() - this.positionX;
			double otherDy = mainCharacter.getPositionY() - this.positionY;
			double distance = Math.sqrt(otherDx * otherDx + otherDy * otherDy);

            // If too close to mainCharacter, adjust movement to avoid overlap
			this.directionX -= otherDx / distance;
			this.directionY -= otherDy / distance;
		}
	}
	
	public void speedUp(double time) {
		this.speedX += time;
		this.speedY += time;
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
}
