/********************************************************
 * 
 * 
 * 
 * @author       Cabral, Alexa Gwen; Villamin, Jan Neal Isaac
 * @date_created 10:44 2023-12-04
 * 
 ********************************************************/

package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javafx.animation.AnimationTimer;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.event.EventHandler;
import entities.Clock;
import entities.Enemy;
import entities.Exam;
import entities.Frog;
import entities.MainCharacter;
import weapons.Bullet;
import weapons.Pen;
import weapons.Shotgun;
import weapons.Weapon;

import sprite.Sprite;
import utils.CooldownTimer;
import wrappers.LongValue;
import weapons.Paper;

import java.util.Random;

public class GameStage {
	public static final long GAME_DURATION_NANO = 10 * 60 * 1_000_000_000L;
	public static final int CANVAS_WIDTH = 3600;
	public static final int CANVAS_HEIGHT = 3600;
	public static final int VIEWPORT_WIDTH = 1200;
	public static final int VIEWPORT_HEIGHT = 670;
	public static final double WEAPON_OFFSET_X = 40;
	public static final double WEAPON_OFFSET_Y = 30;
	public static final double CHARACTER_VELOCITY = 100;
	public static final double CHARACTER_SIDE = 50;
	public static final double ENEMY_SIDE = 50;
	public static final double WEAPON_SIDE = 15;
	public static final ArrayList<String> MAIN_CHARACTER_FRAME = new ArrayList<String>(Arrays.asList(
		"boy.png",
		"frontwalk.png",
		"frontwalk2.png"
	));
	public static final Image HEART_IMAGE = new Image("images\\Heart.png", 30, 30, true, true);
	public static final Image PEN_IMAGE   = new Image("images\\pen.png", 10, 10, true, true);
	public static final Image MAP_IMAGE   = new Image("images\\Map2.png", CANVAS_WIDTH, CANVAS_HEIGHT, true, true);
	
	private Stage stage;
	private StackPane root;
	private ScrollPane scrollPane;
	private Scene gameProperScene;
	private Canvas entityCanvas;
	private Random random;
	private GraphicsContext graphicsContext;
	private Sprite mainCharacter;
	private Sprite weapon;
	private ArrayList<Sprite> enemies;
	private ArrayList<String> input;
	private boolean gameOver;
	private CooldownTimer collisionTimer;
	private CooldownTimer reloadTimer;
	private CooldownTimer weaponTimer;
	private CooldownTimer frogSpawnTimer;
	private CooldownTimer clockSpawnTimer;
	private CooldownTimer examSpawnTimer;
	private Text timerText;
	
	public GameStage(Stage stage) {
		this.stage           = stage;
		this.entityCanvas    = new Canvas(GameStage.CANVAS_WIDTH, GameStage.CANVAS_HEIGHT);
		this.scrollPane      = new ScrollPane(this.entityCanvas);
		this.timerText       = new Text();
		this.root            = new StackPane(this.scrollPane, this.timerText);
		this.gameProperScene = new Scene(this.root, GameStage.VIEWPORT_WIDTH, GameStage.VIEWPORT_HEIGHT);
		this.random          = new Random();
		this.graphicsContext = this.entityCanvas.getGraphicsContext2D();
		this.mainCharacter   = new MainCharacter();
		this.weapon          = new Pen();
		this.enemies         = new ArrayList<Sprite>();
		this.input           = new ArrayList<String>();
		this.gameOver        = false;
		this.collisionTimer  = new CooldownTimer();
		this.reloadTimer     = new CooldownTimer();
		this.weaponTimer     = new CooldownTimer();
		this.frogSpawnTimer  = new CooldownTimer();
		this.clockSpawnTimer = new CooldownTimer();
		this.examSpawnTimer  = new CooldownTimer();
	}
	
	public void runSpookyElbi() {
		this.timerText.setFont(Font.font(30));
		StackPane.setAlignment(timerText, javafx.geometry.Pos.TOP_RIGHT);
		
		this.setWeapon(new Paper());
		
		this.scrollPane.setPrefViewportWidth(GameStage.VIEWPORT_WIDTH);
		this.scrollPane.setPrefViewportHeight(GameStage.VIEWPORT_HEIGHT);
		this.scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		this.scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
		
		this.setMainCharacter("images\\mainCharacter.png");
//		this.setWeapon("images\\pen.png");
		this.handleKeyEvents();
		this.handleMouseEvents();
		
		this.stage.setTitle("Spooky Elbi");
		this.stage.setScene(this.gameProperScene);
		this.stage.show();
		
		this.runAnimation();
	}
	
	public void setMainCharacter(String imageFilename) {
		this.mainCharacter.setImage(imageFilename, GameStage.CHARACTER_SIDE, GameStage.CHARACTER_SIDE);
		this.mainCharacter.setPosition(GameStage.CANVAS_WIDTH / 2, GameStage.CANVAS_HEIGHT / 2);
		((MainCharacter) this.mainCharacter).setHearts(3);
	}
	
	public void setWeapon(String imageFilename) {
		this.weapon.setImage(imageFilename, GameStage.WEAPON_SIDE, GameStage.WEAPON_SIDE);
		this.weapon.setPosition(GameStage.CANVAS_WIDTH / 2 + 10, GameStage.CANVAS_HEIGHT);
		((Sprite) this.weapon).rotateImage(-45);
		((Weapon) this.weapon).reload();
	}
	
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
		this.weapon.setPosition(GameStage.CANVAS_WIDTH / 2 + 20, GameStage.CANVAS_HEIGHT);
		((Sprite) this.weapon).rotateImage(-45);
		((Weapon) this.weapon).reload();
	}
	
	public void spawnFrogs(int frogCount) {
		for (int i = 0; i < frogCount; i++) {
			Sprite newFrog = new Frog();
			double startingX = this.random.nextDouble() * GameStage.CANVAS_WIDTH;
			double startingY = this.random.nextDouble() * GameStage.CANVAS_HEIGHT;
			newFrog.setPosition(startingX, startingY);
			this.enemies.add(newFrog);
		}
	}
	
	public void spawnClocks(int clockCount) {
		for (int i = 0; i < clockCount; i++) {
			Sprite newClock = new Clock();
			double startingX = this.random.nextDouble() * GameStage.CANVAS_WIDTH;
			double startingY = this.random.nextDouble() * GameStage.CANVAS_HEIGHT;
			newClock.setPosition(startingX, startingY);
			this.enemies.add(newClock);
		}
	}
	
	public void spawnExams(int examCount) {
		for (int i = 0; i < examCount; i++) {
			Sprite newExam = new Exam();
			double startingX = this.random.nextDouble() * GameStage.CANVAS_WIDTH;
			double startingY = this.random.nextDouble() * GameStage.CANVAS_HEIGHT;
			newExam.setPosition(startingX, startingY);
			this.enemies.add(newExam);
		}
	}
	
	public void handleKeyEvents() {
		this.gameProperScene.setOnKeyPressed(
			new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent keyEvent) {
					String code = keyEvent.getCode().toString();
					
					if (!input.contains(code)) {
						input.add(code);
					}
					
					if (!reloadTimer.isActiveCooldown() && code == "R") {
						((Weapon) weapon).reload();
						reloadTimer.activateCooldown(((Weapon) weapon).getReloadDelay());
					}
				}
			}
		);
		
		this.gameProperScene.setOnKeyReleased(
			new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent keyEvent) {
					String code = keyEvent.getCode().toString();
					input.remove(code);
				}
			}
		);
	}
	
	public void handleMouseEvents() {
		Weapon weaponReference = (Weapon) this.weapon;
		
		this.entityCanvas.setOnMouseClicked(
			new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					if (!weaponTimer.isActiveCooldown() && !reloadTimer.isActiveCooldown()) {
						double x = mouseEvent.getSceneX();
						double y = mouseEvent.getSceneY();
						double targetX = x - GameStage.VIEWPORT_WIDTH / 2 - GameStage.WEAPON_OFFSET_X + weaponReference.getPositionX();
						double targetY = y - GameStage.VIEWPORT_HEIGHT / 2 - GameStage.WEAPON_OFFSET_Y + weaponReference.getPositionY();
						System.out.println("Shot at " + targetX + " " + targetY);
						weaponReference.shoot(targetX, targetY);
						weaponTimer.activateCooldown(((Weapon) weapon).getWeaponDelay());
					}
				}
			}
		);
	}
	
	public void updateMainCharacter(long currentNanoTime, LongValue lastNanoTime) {
		double elapsedTime = (currentNanoTime - lastNanoTime.value) / 1e9;
		this.mainCharacter.setVelocity(0, 0);
		
		if (this.input.contains("W") || this.input.contains("A") || this.input.contains("S") || this.input.contains("D")) {
			((MainCharacter) this.mainCharacter).setIsMoving(true);
			((MainCharacter) this.mainCharacter).setState((int) (currentNanoTime / 1e8) % 3);
		} else {
			((MainCharacter) this.mainCharacter).setIsMoving(false);
			((MainCharacter) this.mainCharacter).setState(0);
		}
		
		if (this.input.contains("W")) {
			this.mainCharacter.addVelocity(0, -GameStage.CHARACTER_VELOCITY);
		}
		
		if (this.input.contains("A")) {
			this.mainCharacter.addVelocity(-GameStage.CHARACTER_VELOCITY, 0);
		}
		
		if (this.input.contains("S")) {
			this.mainCharacter.addVelocity(0, GameStage.CHARACTER_VELOCITY);
		}
		
		if (this.input.contains("D")) {
			this.mainCharacter.addVelocity(GameStage.CHARACTER_VELOCITY, 0);
		}
		
		this.mainCharacter.update(elapsedTime);
	}
	
	public void removeDeadEnemies() {
		Iterator<Sprite> enemyIterator = this.enemies.iterator();
		
		while (enemyIterator.hasNext()) {
			Enemy enemy = (Enemy) enemyIterator.next();
			
			if (enemy.isDead()) {
				enemyIterator.remove();
			}
		}
	}
	
	public void updateEnemies(double elapsedTime) {
		for (Sprite enemy : this.enemies) {
			Enemy enemyE = (Enemy) enemy;
			enemyE.updateDirection(this.mainCharacter, this.enemies);
			
			double velocityX = enemyE.getDirectionX() * enemyE.getSpeedX();
			double velocityY = enemyE.getDirectionY() * enemyE.getSpeedY();
			
			enemy.setVelocity(velocityX, velocityY);
			enemy.update(elapsedTime);
			
			enemyE.speedUp(elapsedTime);
			
			if (!this.collisionTimer.isActiveCooldown() && this.mainCharacter.intersects(enemy)) {
			    ((MainCharacter) this.mainCharacter).decreaseHeart();
			    ((Sprite) this.mainCharacter).getHit();
			    this.collisionTimer.activateCooldown(1000);
			}
			
			if (((MainCharacter) this.mainCharacter).isDead()) {
				this.gameOver = true;
				return;
			}
		}
		
		this.removeDeadEnemies();
	}
	
	public void updateBullets(double elapsedTime) {
		Iterator<Sprite> bulletIterator = ((Weapon) this.weapon).shotBullets.iterator();
		
		while (bulletIterator.hasNext()) {
			Sprite bullet = bulletIterator.next();
			Bullet bulletE = (Bullet) bullet;
			
			double velocityX = bulletE.getDirectionX() * Bullet.BULLET_SPEED;
			double velocityY = bulletE.getDirectionY() * Bullet.BULLET_SPEED;
			
			bullet.setVelocity(velocityX, velocityY);
			bulletE.update(elapsedTime);
			
			if (bulletE.reachedMaxRange()) {
				bulletIterator.remove();
			} else if (bulletE.checkEnemiesCollision(this.enemies)) {
				if (this.weapon instanceof Shotgun) {
					if (bulletE.getHitCount() >= 2) {
						bulletIterator.remove();
					}
				} else {
					bulletIterator.remove();
				}
			}
		}
	}
	
	public void updateEntities(long currentNanoTime, LongValue lastNanoTime) {
		double elapsedTime = (currentNanoTime - lastNanoTime.value) / 1e9;
			
		this.updateMainCharacter(currentNanoTime, lastNanoTime);
		this.weapon.setPosition(
			this.mainCharacter.getPositionX() + GameStage.WEAPON_OFFSET_X,
			this.mainCharacter.getPositionY() + GameStage.WEAPON_OFFSET_Y
		);
		this.updateEnemies(elapsedTime);
		this.updateBullets(elapsedTime);
		
		lastNanoTime.value = currentNanoTime;
	}
	
	public void renderEntities() {
		this.graphicsContext.clearRect(0, 0, GameStage.CANVAS_WIDTH, GameStage.CANVAS_HEIGHT);
		this.graphicsContext.drawImage(GameStage.MAP_IMAGE, 0, 0);
		this.mainCharacter.setImage(
			"characterimages\\" + GameStage.MAIN_CHARACTER_FRAME.get(((MainCharacter) this.mainCharacter).getState()), 
			GameStage.CHARACTER_SIDE, 
			GameStage.CHARACTER_SIDE
		);
		this.mainCharacter.render(graphicsContext);
		this.weapon.render(graphicsContext);
		
		for (Sprite enemy : this.enemies) {
			enemy.render(this.graphicsContext);
		}
		
		for (Sprite bullet : ((Weapon) this.weapon).shotBullets) {
			bullet.render(this.graphicsContext);
		}
		
		this.displayHearts();
		this.displayAmmo();
	}
	
	public void updateArea() {
		double viewportX = this.mainCharacter.getPositionX() - (GameStage.VIEWPORT_WIDTH / 2);
        double viewportY = this.mainCharacter.getPositionY() - (GameStage.VIEWPORT_HEIGHT / 2);
        this.scrollPane.setHvalue(viewportX / (GameStage.CANVAS_WIDTH - GameStage.VIEWPORT_WIDTH));
        this.scrollPane.setVvalue(viewportY / (GameStage.CANVAS_HEIGHT - GameStage.VIEWPORT_HEIGHT));
	}
	
	public void displayHearts() {
		for (int i = 0; i < ((MainCharacter) this.mainCharacter).getHearts(); i++) {
			this.graphicsContext.drawImage(GameStage.HEART_IMAGE, 
				this.mainCharacter.getPositionX() - (GameStage.VIEWPORT_WIDTH / 2) + i * 35 + 10, 
				this.mainCharacter.getPositionY() - (GameStage.VIEWPORT_HEIGHT / 2) + 10
			);
		}
	}
	
	public void displayAmmo() {
		if (!this.reloadTimer.isActiveCooldown()) {
			this.graphicsContext.strokeText(
				"Reload ready!", 
				this.mainCharacter.getPositionX() - (GameStage.VIEWPORT_WIDTH / 2) + 10, 
				this.mainCharacter.getPositionY() + (GameStage.VIEWPORT_HEIGHT / 2) - 50
			);
		}
		
		for (int i = 0; i < ((Weapon) this.weapon).getAmmoCount(); i++) {
			this.graphicsContext.drawImage(GameStage.PEN_IMAGE, 
				this.mainCharacter.getPositionX() - (GameStage.VIEWPORT_WIDTH / 2) + i * 15 + 10, 
				this.mainCharacter.getPositionY() + (GameStage.VIEWPORT_HEIGHT / 2) - 20
			);
		}
	}
	
	public void runAnimation() {
		LongValue startNanoTime = new LongValue(System.nanoTime());
		LongValue lastNanoTime = new LongValue(startNanoTime.value);
		LongValue frogCount = new LongValue(5);
		LongValue clockCount = new LongValue(1);
		LongValue examCount = new LongValue(0);
		clockSpawnTimer.activateCooldown(60000);
		examSpawnTimer.activateCooldown(90000);
		
		new AnimationTimer() {
			@Override
			public void handle(long currentNanoTime) {
				long remainingTime = (GAME_DURATION_NANO - (currentNanoTime - startNanoTime.value)) / 1_000_000_000L;
				updateEntities(currentNanoTime, lastNanoTime);
				updateArea();
				renderEntities();
				
				if (remainingTime <= 0) {
					remainingTime = 0;
					stop();
				}
				
				timerText.setText("Time Remaining: " + remainingTime / 60 + " : " + remainingTime % 60 + " ");
				
				if (gameOver) {
					this.stop();
				}
				
				if (!frogSpawnTimer.isActiveCooldown()) {
					spawnFrogs((int) ++frogCount.value);
					frogSpawnTimer.activateCooldown(30000);
				}
				
				if (!clockSpawnTimer.isActiveCooldown()) {
					spawnClocks((int) ++clockCount.value);
					clockSpawnTimer.activateCooldown(60000);
				}
				
				if (!examSpawnTimer.isActiveCooldown()) {
					spawnExams((int) ++examCount.value);
					examSpawnTimer.activateCooldown(90000);
				}
			}
		}.start();
	}
}
