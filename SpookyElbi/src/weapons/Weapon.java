package weapons;

import java.util.ArrayList;
import sprite.Sprite;

public class Weapon extends Sprite {
	private ArrayList<Bullet> bullets;
	
	Weapon() {
		this.bullets = new ArrayList<Bullet>();
	}
}
