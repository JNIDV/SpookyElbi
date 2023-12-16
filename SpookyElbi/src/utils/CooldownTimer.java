package utils;

import java.util.Timer;
import java.util.TimerTask;

public class CooldownTimer {
	private Timer timer;
    private boolean isActiveCooldown = false;

    public CooldownTimer() {
        this.timer = new Timer();
    }

    public void activateCooldown(long delay) {
        if (!this.isActiveCooldown) {
            this.isActiveCooldown = true;

            this.timer.schedule(
            	new TimerTask() {
            		@Override
            		public void run() {
            			isActiveCooldown = false;
            			System.out.println("Hello!");
            			cancel();
            		}
            	}, 
            	delay
    		);
        }
    }

    public boolean isActiveCooldown() {
        return this.isActiveCooldown;
    }
}
