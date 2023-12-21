package powerups;

import sprite.Sprite;

public class Pet extends Sprite {
	public static final String CAT_DESCRIPTION = "Cat of UPLB\n" +
			"++Health Boost\n" +
			"++Speed Boost\n" +
			"+Damage Boost\n" 
;
	public static final String DOG_DESCRIPTION = "Dog of UPLB\n" +
			"+Health Boost\n" +
			"++Speed Boost\n" +
			"++Damage Boost\n";
	
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
