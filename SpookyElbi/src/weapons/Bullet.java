package weapons;

import sprite.Sprite;

public class Bullet extends Sprite {
	public final static double BULLET_SPEED = 50;
	private double directionX;
	private double directionY;
	
	public void setDirection(double x, double y, Weapon weapon) {
		double dx = x - ((Sprite) weapon).getPositionX();
		double dy = y - ((Sprite) weapon).getPositionY();
		
		double norm = Math.sqrt(dx * dx + dy * dy);
		
		if (norm == 0) {
			this.directionX = 0;
			this.directionY = 0;
			return;
		}
		
		this.directionX = dx / norm;
		this.directionY = dy / norm;
		
		((Sprite) this).rotateImage(180 * Math.atan(this.directionY / this.directionX) / Math.PI);
	}
	
	public double getDirectionX() {
		return this.directionX;
	}
	
	public double getDirectionY() {
		return this.directionY;
	}
}
