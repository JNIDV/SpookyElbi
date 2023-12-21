/********************************************************
 * 
 * 
 * 
 * @author       Cabral, Alexa Gwen; Villamin, Jan Neal Isaac
 * @date_created 10:44 2023-12-04
 * 
 ********************************************************/

package entities;

import application.GameTimer;
import javafx.scene.image.Image;
import sprite.Sprite;

public class MainCharacter extends Sprite {
	private int hearts = 3;
	private boolean isMoving;
	private int state;
	public boolean isHit;
	private double boostSpeed;
	
	public MainCharacter() {
		((Sprite) this).setRedImage(new Image("characterimages\\yellowboy.png", GameTimer.CHARACTER_SIDE, GameTimer.CHARACTER_SIDE, true, true));
		this.isHit = false;
		this.boostSpeed = 0;
	}
	
	public void increaseHearts(int dHeart) {
		this.hearts += dHeart;
	}
	
	public void setBoostSpeed(double boostSpeed) { 
		this.boostSpeed += boostSpeed;
	}
	
	public double getBoostSpeed() {
		return this.boostSpeed;
	}
	
	public void getHit() {
		this.isHit = true;
		super.getHit();
	}
	
	public boolean getIsMoving() {
		return isMoving;
	}
	
	public void setIsMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}
	
	public int getState() {
		return this.state;
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public void decreaseHeart() {
		this.hearts--;
	}
	
	public boolean isDead() {
		return this.hearts <= 0;
	}
	
	public int getHearts() {
		return this.hearts;
	}
	
	public void setHearts(int hearts) {
		this.hearts = hearts;
	}
}
