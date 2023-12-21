/********************************************************
 * 
 * @author       Cabral, Alexa Gwen; Villamin, Jan Neal Isaac
 * @date_created 17:48 2023-12-20
 * 
 ********************************************************/

package powerups;

import javafx.scene.image.Image;

public class Cat extends Pet {
	public static final double CAT_DAMAGE = 10;
	public static final double CAT_SPEED = 15;
	public static final int CAT_HEART = 2;
	public static final Image CAT_IMAGE = new Image("images\\Cat.png", 20, 20, true, true);
	
	public Cat() {
		super(CAT_DAMAGE, CAT_SPEED, CAT_HEART);
		this.setImage(CAT_IMAGE);
	}
}
