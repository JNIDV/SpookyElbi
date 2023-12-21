package powerups;

import java.util.Timer;
import java.util.TimerTask;

import entities.MainCharacter;
import weapons.Weapon;

public class Coffee extends Boost {
	public Coffee(double x, double y) {
		this.positionX = x;
		this.positionY = y;
		this.setImage("images\\coffee.png", 20, 20);
	}
	
	@Override
	public void applyBoost(MainCharacter mainCharacter, Weapon weapon) {
		mainCharacter.setBoostSpeed(100);
		
		Timer timer = new Timer();
		timer.schedule(
			new TimerTask() {
				@Override
				public void run() {
					mainCharacter.setBoostSpeed(-100);
					cancel();
				}
			}, 
			5000
		);
	}
}
