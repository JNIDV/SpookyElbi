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
	private ArrayList<Integer> boostsUnlocked = new ArrayList<Integer>();
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
	
	public ArrayList<Integer> getBoostsUnlocked() {
		return this.boostsUnlocked;
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
	
	private boolean boostIsBought(int boostIndex) {
		return this.boostsUnlocked.contains(boostIndex);
	}
	
	public void buyWeapon(int weaponIndex) {
		if (this.isBought(weaponIndex)) {
			return;
		}
		
		switch (weaponIndex) {
		case PEN:
			if (this.gems >= PEN_COST) {
				this.gems -= PEN_COST;
			}
			
			this.weaponsUnlocked.add(weaponIndex);
			break;
		case PISTOL:
			if (this.gems >= PISTOL_COST) {
				this.gems -= PISTOL_COST;
			}
			
			this.weaponsUnlocked.add(weaponIndex);
			break;
		case SHOTGUN:
			if (this.gems >= SHOTGUN_COST) {
				this.gems -= SHOTGUN_COST;
			}
			
			this.weaponsUnlocked.add(weaponIndex);
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
				this.gems -= CAT_COST;
			}
			
			this.petsUnlocked.add(petIndex);
			break;
		case DOG:
			if (this.gems >= DOG_COST) {
				this.gems -= DOG_COST;
			}
			
			this.petsUnlocked.add(petIndex);
			break;
		}
	}
	
	public void buyBoost(int boostIndex) {
		if (this.boostIsBought(boostIndex)) {
			return;
		}
		
		switch (boostIndex) {
		case COFFEE:
			if (this.gems >= COFFEE_COST) {
				this.gems -= COFFEE_COST;
			}
			
			this.boostsUnlocked.add(boostIndex);
			break;
		case CRUSH:
			if (this.gems >= CRUSH_COST) {
				this.gems -= CRUSH_COST;
			}
			
			this.boostsUnlocked.add(boostIndex);
			break;
		case ORGANIZATION:
			if (this.gems >= ORGANIZATION_COST) {
				this.gems -= ORGANIZATION_COST;
			}
			
			this.boostsUnlocked.add(boostIndex);
			break;
		}
	}
}
