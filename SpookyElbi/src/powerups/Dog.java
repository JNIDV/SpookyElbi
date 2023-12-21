/********************************************************
 * 
 * @author       Cabral, Alexa Gwen; Villamin, Jan Neal Isaac
 * @date_created 17:48 2023-12-20
 * 
 ********************************************************/

package powerups;

import javafx.scene.image.Image;

public class Dog extends Pet {
	public static final double DOG_DAMAGE = 30;
	public static final double DOG_SPEED = 10;
	public static final int DOG_HEART = 1;
	public static final Image DOG_IMAGE = new Image("images\\Dog.png", 20, 20, true, true);
	
	public Dog() {
		super(DOG_DAMAGE, DOG_SPEED, DOG_HEART);
		this.setImage(DOG_IMAGE);
	}
}
