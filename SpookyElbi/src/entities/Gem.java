/********************************************************
 * 
 * The Gem class that extends the Sprite class.
 * 
 * @author       Cabral, Alexa Gwen; Villamin, Jan Neal Isaac
 * @date_created 10:44 2023-12-04
 * 
 ********************************************************/

package entities;

import javafx.scene.media.AudioClip;
import sprite.Sprite;

public class Gem extends Sprite {
	public Gem(double x, double y) {
		this.positionX = x;
		this.positionY = y;
		((Sprite) this).setImage("images\\gem.png", 10, 10);
	}
	
	public void playSound() {
		try {
            AudioClip sound = new AudioClip(getClass().getResource("/audio/gem.mp3").toString());
            sound.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
