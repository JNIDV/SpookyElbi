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
//import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.event.EventHandler;

import entities.Enemy;
import entities.MainCharacter;
import weapons.Bullet;
import weapons.Weapon;

import sprite.Sprite;
import wrappers.LongValue;

import java.util.Random;

public class GameStage {
	public static final int CANVAS_WIDTH = 2400;
	public static final int CANVAS_HEIGHT = 2400;
	public static final int VIEWPORT_WIDTH = 960;
	public static final int VIEWPORT_HEIGHT = 540;
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
	
	public GameStage(Stage stage) {
		this.stage               = stage;
		this.entityCanvas        = new Canvas(GameStage.CANVAS_WIDTH, GameStage.CANVAS_HEIGHT);
		this.scrollPane          = new ScrollPane(this.entityCanvas);
		this.root                = new StackPane(this.scrollPane);
		this.gameProperScene     = new Scene(this.root, GameStage.VIEWPORT_WIDTH, GameStage.VIEWPORT_HEIGHT);
		this.random              = new Random();
		this.graphicsContext     = this.entityCanvas.getGraphicsContext2D();
		this.mainCharacter       = new MainCharacter();
		this.weapon              = new Weapon();
		this.enemies             = new ArrayList<Sprite>();
		this.input               = new ArrayList<String>();
		this.gameOver            = false;
	}
	
	public void runSpookyElbi() {
		this.scrollPane.setPrefViewportWidth(GameStage.VIEWPORT_WIDTH);
		this.scrollPane.setPrefViewportHeight(GameStage.VIEWPORT_HEIGHT);
		this.scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		this.scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
		this.setMainCharacter("images\\mainCharacter.png");
		this.setWeapon("images\\pen.png");
		this.spawnEnemies(10, "images\\Frog.png");
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
	
	public Sprite spawnEnemy(String imageFilename) {
		Sprite newEnemy = new Enemy();
		newEnemy.setImage(imageFilename, GameStage.ENEMY_SIDE, GameStage.ENEMY_SIDE);
		
		double startingX = this.random.nextDouble() * GameStage.CANVAS_WIDTH;
		double startingY = this.random.nextDouble() * GameStage.CANVAS_HEIGHT;
		
		newEnemy.setPosition(startingX, startingY);
		return newEnemy;
	}
	
	public void spawnEnemies(int enemyCount, String imageFilename) {
		for (int i = 0; i < enemyCount; i++) {
			Sprite newEnemy = this.spawnEnemy(imageFilename);
			this.enemies.add(newEnemy);
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
					
					if (code == "R") {
						((Weapon) weapon).reload();
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
					double x = mouseEvent.getSceneX();
					double y = mouseEvent.getSceneY();
					double targetX = x - GameStage.VIEWPORT_WIDTH / 2 - GameStage.WEAPON_OFFSET_X + weaponReference.getPositionX();
					double targetY = y - GameStage.VIEWPORT_HEIGHT / 2 - GameStage.WEAPON_OFFSET_Y + weaponReference.getPositionY();
					System.out.println("Shot at " + targetX + " " + targetY);
					weaponReference.shoot(targetX, targetY);
				}
			}
		);
	}
	
	public void updateEntities(long currentNanoTime, LongValue lastNanoTime) {
		double elapsedTime = (currentNanoTime - lastNanoTime.value) / 1e9;
		lastNanoTime.value = currentNanoTime;
		
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
		this.weapon.setPosition(
			this.mainCharacter.getPositionX() + GameStage.WEAPON_OFFSET_X,
			this.mainCharacter.getPositionY() + GameStage.WEAPON_OFFSET_Y
		);
		
		for (Sprite enemy : this.enemies) {
			Enemy enemyE = (Enemy) enemy;
			
			enemyE.updateDirection(this.mainCharacter, this.enemies);
			
			double velocityX = enemyE.getDirectionX() * enemyE.getSpeedX();
			double velocityY = enemyE.getDirectionY() * enemyE.getSpeedY();
			
			enemy.setVelocity(velocityX, velocityY);
			enemy.update(elapsedTime);
			
			enemyE.speedUp(elapsedTime);
			
			if (enemy.intersects(this.mainCharacter)) {
				((MainCharacter) this.mainCharacter).decreaseHeart();
				
//				if (((MainCharacter) this.mainCharacter).isDead()) {
//					this.gameOver = true;
//					return;
//				}
			}
		}
		
		// Dead bullets
		Iterator<Sprite> bulletIterator = ((Weapon) this.weapon).shotBullets.iterator();
		
		while (bulletIterator.hasNext()) {
			Sprite bullet = bulletIterator.next();
			Bullet bulletE = (Bullet) bullet;
			
			double velocityX = bulletE.getDirectionX() * Bullet.BULLET_SPEED;
			double velocityY = bulletE.getDirectionY() * Bullet.BULLET_SPEED;
			
			bullet.setVelocity(velocityX, velocityY);
			bulletE.update(elapsedTime);
			
			if (bulletE.checkEnemiesCollision(this.enemies) || bulletE.reachedMaxRange()) {
				bulletIterator.remove();
			}
		}
		
		// Dead enemies
		Iterator<Sprite> enemyIterator = this.enemies.iterator();
		
		while (enemyIterator.hasNext()) {
			Enemy enemy = (Enemy) enemyIterator.next();
			
			if (enemy.isDead()) {
				enemyIterator.remove();
			}
		}
	}
	
	public void renderEntities() {
		this.graphicsContext.clearRect(0, 0, GameStage.CANVAS_WIDTH, GameStage.CANVAS_HEIGHT);
		this.graphicsContext.drawImage(new Image("images\\UPLB-Map-Main-Campus.png"), 0, 0);
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
	}
	
	public void updateArea() {
		double viewportX = Math.max(0, this.mainCharacter.getPositionX() - (GameStage.VIEWPORT_WIDTH / 2));
        double viewportY = Math.max(0, this.mainCharacter.getPositionY() - (GameStage.VIEWPORT_HEIGHT / 2));
        this.scrollPane.setHvalue(viewportX / (GameStage.CANVAS_WIDTH - GameStage.VIEWPORT_WIDTH));
        this.scrollPane.setVvalue(viewportY / (GameStage.CANVAS_HEIGHT - GameStage.VIEWPORT_HEIGHT));
	}
	
	public void runAnimation() {
		GameStage selfReference = this;
		LongValue lastNanoTime = new LongValue(System.nanoTime());
		
		new AnimationTimer() {
			@Override
			public void handle(long currentNanoTime) {
//				System.out.println("Current nano time: " + currentNanoTime);
				selfReference.updateEntities(currentNanoTime, lastNanoTime);
				selfReference.updateArea();
				selfReference.renderEntities();
				System.out.println("Hearts: " + ((MainCharacter) selfReference.mainCharacter).getHearts());
				
				if (selfReference.gameOver) {
					this.stop();
				}
				
//				System.out.println("Main character position: " + selfReference.mainCharacter.getPositionX() + " " + selfReference.mainCharacter.getPositionY());
			}
		}.start();
	}
}
