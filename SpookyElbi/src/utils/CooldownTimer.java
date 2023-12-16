package utils;

import java.util.Timer;
import java.util.TimerTask;

public class CooldownTimer {
	private Timer timer;
    private boolean isCooldownActive = false;

    public CooldownTimer() {
        this.timer = new Timer();
    }

    public void activateCooldown(long cooldownDurationMillis) {
        if (!isCooldownActive) {
            isCooldownActive = true;

            timer.schedule(
            	new TimerTask() {
            		@Override
            		public void run() {
            			isCooldownActive = false;
            			System.out.println("Hello!");
            			cancel(); // Cancel the TimerTask after cooldown duration
            		}
            	}, 
            	cooldownDurationMillis
    		);
        }
    }

    public boolean isCooldownActive() {
        return isCooldownActive;
    }
}
