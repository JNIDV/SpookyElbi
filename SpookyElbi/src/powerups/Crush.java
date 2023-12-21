package powerups;

import java.util.Timer;
import java.util.TimerTask;

import entities.MainCharacter;
import weapons.Weapon;

public class Crush extends Boost {
	public Crush(double x, double y) {
		this.positionX = x;
		this.positionY = y;
		this.setImage("images\\crush.png", 20, 20);
	}
	
	@Override
	public void applyBoost(MainCharacter mainCharacter, Weapon weapon) {
		weapon.increaseDamage(100);
		
		Timer timer = new Timer();
		timer.schedule(
			new TimerTask() {
				@Override
				public void run() {
					weapon.increaseDamage(-100);
					cancel();
				}
			}, 
			5000
		);
	}
}
