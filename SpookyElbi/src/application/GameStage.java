package application;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;

import entities.Enemy;
import entities.MainCharacter;
import weapons.Bullet;
import weapons.Weapon;

import sprite.Sprite;
import wrappers.LongValue;

import java.util.Random;

public class GameStage {
	public static final int WINDOW_WIDTH = 1600;
	public static final int WINDOW_HEIGHT = 900;
	
	private Stage stage;
	private Group root;
	private Scene gameProperScene;
	private Canvas canvas;
	private Random random;
	private GraphicsContext graphicsContext;
	private Sprite mainCharacter;
	private Sprite weapon;
	private ArrayList<Sprite> enemies;
	private ArrayList<String> input;
	
	public GameStage(Stage stage) {
		this.stage           = stage;
		this.root            = new Group();
		this.gameProperScene = new Scene(this.root, GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		this.canvas          = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		this.random          = new Random();
		this.graphicsContext = this.canvas.getGraphicsContext2D();
		this.mainCharacter   = new MainCharacter();
		this.weapon          = new Weapon();
		this.enemies         = new ArrayList<Sprite>();
		this.input           = new ArrayList<String>();
	}
	
	public void runSpookyElbi() {
		this.setMainCharacter("images\\mainCharacter.png");
		this.setWeapon("images\\pen.png");
		this.spawnEnemies(10, "images\\Frog.png");
		this.handleKeyEvents();
		this.handleMouseEvents();
		this.runAnimation();
		this.root.getChildren().add(this.canvas);
		this.stage.setTitle("Spooky Elbi");
		this.stage.setScene(this.gameProperScene);
		this.stage.show();
	}
	
	public void setMainCharacter(String imageFilename) {
		this.mainCharacter.setImage(imageFilename);
		this.mainCharacter.setPosition(750, 600);
	}
	
	public void setWeapon(String imageFilename) {
		this.weapon.setImage(imageFilename);
		this.weapon.setPosition(780, 600);
		((Weapon) this.weapon).reload();
	}
	
	public Sprite spawnEnemy(String imageFilename) {
		Sprite newEnemy = new Enemy();
		newEnemy.setImage(imageFilename);
		
		double startingX = this.random.nextDouble() * GameStage.WINDOW_WIDTH;
		double startingY = this.random.nextDouble() * GameStage.WINDOW_HEIGHT;
		
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
		this.gameProperScene.setOnMouseClicked(
			new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					double x = mouseEvent.getSceneX();
					double y = mouseEvent.getSceneY();
					System.out.println("Shot at " + x + " " + y);
					((Weapon) weapon).shoot(x, y);
				}
			}
		);
	}
	
	public void updateEntities(long currentNanoTime, LongValue lastNanoTime) {
		double elapsedTime = (currentNanoTime - lastNanoTime.value) / 1e9;
		lastNanoTime.value = currentNanoTime;
		
		this.mainCharacter.setVelocity(0, 0);
		this.weapon.setVelocity(0, 0);
		
		if (this.input.contains("W")) {
			this.mainCharacter.addVelocity(0, -100);
			this.weapon.addVelocity(0, -100);
		}
		
		if (this.input.contains("A")) {
			this.mainCharacter.addVelocity(-100, 0);
			this.weapon.addVelocity(-100, 0);
		}
		
		if (this.input.contains("S")) {
			this.mainCharacter.addVelocity(0, 100);
			this.weapon.addVelocity(0, 100);
		}
		
		if (this.input.contains("D")) {
			this.mainCharacter.addVelocity(100, 0);
			this.weapon.addVelocity(100, 0);
		}
		
		this.mainCharacter.update(elapsedTime);
		this.weapon.update(elapsedTime);
		
		for (Sprite enemy : this.enemies) {
			Enemy enemyE = (Enemy) enemy;
			
			enemyE.updateDirection(this.mainCharacter, this.enemies);
			
			double velocityX = enemyE.getDirectionX() * enemyE.getSpeedX();
			double velocityY = enemyE.getDirectionY() * enemyE.getSpeedY();
			
			enemy.setVelocity(velocityX, velocityY);
			enemy.update(elapsedTime);
			
			enemyE.speedUp(elapsedTime);
		}
		
		for (Sprite bullet : ((Weapon) this.weapon).shotBullets) {
			Bullet bulletE = (Bullet) bullet;
			
			double velocityX = bulletE.getDirectionX() * Bullet.BULLET_SPEED;
			double velocityY = bulletE.getDirectionY() * Bullet.BULLET_SPEED;
			
			bullet.setVelocity(velocityX, velocityY);
			bullet.update(elapsedTime);
		}
	}
	
	public void renderEntities() {
		this.graphicsContext.clearRect(0, 0, GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		this.mainCharacter.render(graphicsContext);
		this.weapon.render(graphicsContext);
		
		for (Sprite enemy : this.enemies) {
			enemy.render(this.graphicsContext);
		}
		
		for (Sprite bullet : ((Weapon) this.weapon).shotBullets) {
			bullet.render(this.graphicsContext);
		}
	}
	
	public void runAnimation() {
		GameStage reference = this;
		LongValue lastNanoTime = new LongValue(System.nanoTime());
		
		new AnimationTimer() {
			@Override
			public void handle(long currentNanoTime) {
				reference.updateEntities(currentNanoTime, lastNanoTime);
				reference.renderEntities();
			}
		}.start();
	}
}
