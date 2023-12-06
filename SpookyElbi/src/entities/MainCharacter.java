package entities;

import sprite.Sprite;
import weapons.Weapon;

public class MainCharacter extends Sprite {
	private int hearts;
//	private Weapon weapon;
	private boolean isMoving;
	private int state;
	
	public void setWeapon(Weapon weapon) {
//		this.weapon = weapon;
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
}
