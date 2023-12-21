package application;

import java.util.ArrayList;

public class Shop {
	public static final int PEN_COST = 500;
	public static final int PISTOL_COST = 1500;
	public static final int SHOTGUN_COST = 3500;
	public static final int PAPER = 0;
	public static final int PEN = 1;
	public static final int PISTOL = 2;
	public static final int SHOTGUN = 3;
	
	public static final int CAT_COST = 1500;
	public static final int DOG_COST = 2500;
	public static final int CAT = 4;
	public static final int DOG = 5;
	
	public static final int COFFEE_COST = 50;
	public static final int CRUSH_COST = 60;
	public static final int ORGANIZATION_COST = 70;
	public static final int COFFEE = 6;
	public static final int CRUSH = 7;
	public static final int ORGANIZATION = 8;
	
	private int gems;
	private ArrayList<Integer> boosts = new ArrayList<Integer>();
	private ArrayList<Integer> weaponsUnlocked = new ArrayList<Integer>();
	private ArrayList<Integer> petsUnlocked = new ArrayList<Integer>();
	
	public Shop() {
		this.gems = 10000;
		this.weaponsUnlocked.add(0);
	}
	
	public void setGems(int dGems) {
		this.gems += dGems;
	}
	
	public int getGems() {
		return this.gems;
	}
	
	public ArrayList<Integer> getBoosts() {
		return this.boosts;
	}
	
	public ArrayList<Integer> getWeaponsUnlocked() {
		return this.weaponsUnlocked;
	}
	
	public ArrayList<Integer> getPetsUnlocked() {
		return this.petsUnlocked;
	}
	
	private boolean isBought(int weaponIndex) {
		return this.weaponsUnlocked.contains(weaponIndex);
	}
	
	private boolean petIsBought(int petIndex) {
		return this.petsUnlocked.contains(petIndex);
	}
	
	public void buyWeapon(int weaponIndex) {
		if (this.isBought(weaponIndex)) {
			return;
		}
		
		switch (weaponIndex) {
		case PEN:
			if (this.gems >= PEN_COST) {
				if (!this.weaponsUnlocked.contains(PEN)) {
					this.weaponsUnlocked.add(PEN);
				}
				
				this.gems -= PEN_COST;
			}
			break;
		case PISTOL:
			if (this.gems >= PISTOL_COST) {
				if (!this.weaponsUnlocked.contains(PISTOL)) {
					this.weaponsUnlocked.add(PISTOL);
				}
				
				this.gems -= PISTOL_COST;
			}
			
			break;
		case SHOTGUN:
			if (this.gems >= SHOTGUN_COST) {
				if (!this.weaponsUnlocked.contains(SHOTGUN)) {
					this.weaponsUnlocked.add(SHOTGUN);
				}
				
				this.gems -= SHOTGUN_COST;
			}
			
			break;
		}
	}
	
	public void buyPet(int petIndex) {
		if (petIsBought(petIndex)) {
			return;
		}
		
		switch (petIndex) {
		case CAT:
			if (this.gems >= CAT_COST) {
				if (!this.petsUnlocked.contains(CAT)) {
					this.petsUnlocked.add(CAT);
				}
				
				this.gems -= CAT_COST;
			}
			break;
		case DOG:
			if (this.gems >= DOG_COST) {
				if (!this.petsUnlocked.contains(DOG)) {
					this.petsUnlocked.add(DOG);
				}
				
				this.gems -= DOG_COST;
			}
			
			break;
		}
	}
	
	public void buyBoost(int boostIndex) {
		switch (boostIndex) {
		case COFFEE:
			if (this.gems >= COFFEE_COST) {
				boosts.add(COFFEE);
			}
			
			break;
		case CRUSH:
			if (this.gems >= CRUSH_COST) {
				boosts.add(CRUSH);
			}
			
			break;
		case ORGANIZATION:
			if (this.gems >= ORGANIZATION_COST) {
				boosts.add(ORGANIZATION);
			}
			
			break;
		}
	}
}
