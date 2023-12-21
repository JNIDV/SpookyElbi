/********************************************************
 * 
 * A class that uses Timer from java.util to perform
 * cooldowns. It uses the schedule method of the Timer class
 * and the Timertask to perform an action in the schedule.
 * 
 * It maintains isActiveCooldown to determine whether
 * it can be used or not.
 * 
 * @author       Cabral, Alexa Gwen; Villamin, Jan Neal Isaac
 * @date_created 16:43 2023-12-16
 * 
 ********************************************************/

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
