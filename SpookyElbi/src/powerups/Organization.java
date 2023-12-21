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
