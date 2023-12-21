/********************************************************
 * 
 * The boosts class is an abstract class meant to be 
 * inherited by the distinct boosts.
 * 
 * It has a playBoost() method to play the sound
 * of all boosts.
 * 
 * There's also an abstract class applyBoost for all 
 * subclasses.
 * 
 * @author       Cabral, Alexa Gwen; Villamin, Jan Neal Isaac
 * @date_created 10:44 2023-12-04
 * 
 ********************************************************/

package powerups;

import entities.MainCharacter;
import javafx.scene.media.AudioClip;
import sprite.Sprite;
import weapons.Weapon;

public abstract class Boost extends Sprite {
	public static final String COFFEE_DESCRIPTION = "Free Coffee before Exam\n" +
			"+Speed Boost\n" +
			"Duration: 5s";
	public static final String CRUSH_DESCRIPTION = "Crush\n" +
			"Increase Damage\n" +
			"Duration: 5s";
	public static final String ORGANIZATION_DESCRIPTION = "Organizational Help\n"+
			"Shoots Bullets around you\n" +
			"Duration: 5s";
	
	public void playBoostSound() {
		try {
            AudioClip boostSound = new AudioClip(getClass().getResource("/audio/powerUpSound.mp3").toString());
            boostSound.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public abstract void applyBoost(MainCharacter mainCharacter, Weapon weapon);
}
