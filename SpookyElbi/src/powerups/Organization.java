/********************************************************
 * 
 * The Organization class. The differene is that applyBoost
 * makes the weapon shoot multiple bullets in 4 directions.
 * 
 * @author       Cabral, Alexa Gwen; Villamin, Jan Neal Isaac
 * @date_created 17:49 2023-12-04
 * 
 ********************************************************/

package powerups;

import java.util.Timer;
import java.util.TimerTask;

import entities.MainCharacter;
import weapons.Weapon;

public class Organization extends Boost {
	public Organization(double x, double y) {
		this.positionX = x;
		this.positionY = y;
		this.setImage("images\\orghelp.png", 20, 20);
	}
	
	@Override
	public void applyBoost(MainCharacter mainCharacter, Weapon weapon) {
		weapon.setMultipleBullets(true);
		
		Timer timer = new Timer();
		timer.schedule(
			new TimerTask() {
				@Override
				public void run() {
					weapon.setMultipleBullets(false);
					cancel();
				}
			}, 
			5000
		);
	}
}
