/********************************************************
 * 
 * @author       Cabral, Alexa Gwen; Villamin, Jan Neal Isaac
 * @date_created 10:44 2023-12-04
 * 
 ********************************************************/

package weapons;

import javafx.scene.image.Image;

public class Paper extends Weapon {
	public static final long PAPER_DELAY = 50;
	public static final long PAPER_RELOAD_DELAY = 1000;
	public static final long PAPER_DAMAGE = 5;
	public static final double PAPER_BULLET_SPEED = 250;
	public static final double PAPER_MAX_DISTANCE = 350;
	public static final Image PAPER_IMAGE = new Image("images\\paper.png", 15, 15, true, true);
	public static final String PAPER_BULLET_IMAGE = "images\\paper.png";
	public static final String PAPER_THROW_MP3 = "paperThrow.mp3";
	public static final String PAPER_RELOAD_MP3 = "paperReload.mp3";
	
	public Paper() {
		super(PAPER_DELAY, PAPER_RELOAD_DELAY, PAPER_DAMAGE);
		this.setImage(PAPER_IMAGE);
		this.setAmmoCount(15);
		this.setBulletImage(PAPER_BULLET_IMAGE);
		this.bulletSpeed = PAPER_BULLET_SPEED;
		this.maxDistance = PAPER_MAX_DISTANCE;
		this.soundFile = PAPER_THROW_MP3;
		this.reloadFile = PAPER_RELOAD_MP3;
		this.reload();
	}
}
