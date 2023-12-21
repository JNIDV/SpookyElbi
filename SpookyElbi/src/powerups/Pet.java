package powerups;

import sprite.Sprite;

public class Pet extends Sprite {
	private double additionalDamage;
	private double additionalSpeed;
	private int additionalHeart;
	
	public Pet(double additionalDamage, double additionalSpeed, int additionalHeart) {
		this.additionalDamage = additionalDamage;
		this.additionalSpeed = additionalSpeed;
		this.additionalHeart = additionalHeart;
	}
	
	public double getAdditionalDamage() {
		return this.additionalDamage;
	}
	
	public double getAdditionalSpeed() {
		return this.additionalSpeed;
	}
	
	public int getAdditionalHeart() {
		return this.additionalHeart;
	}
}
