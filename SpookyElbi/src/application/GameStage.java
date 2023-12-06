package application;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
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
	
	public GameStage(Stage stage) {
		this.stage           = stage;
		this.entityCanvas    = new Canvas(GameStage.CANVAS_WIDTH, GameStage.CANVAS_HEIGHT);
		this.scrollPane      = new ScrollPane(this.entityCanvas);
		this.scrollPane.setPrefViewportWidth(GameStage.VIEWPORT_WIDTH);
        this.scrollPane.setPrefViewportHeight(GameStage.VIEWPORT_HEIGHT);
		this.root            = new StackPane(this.scrollPane);
		this.gameProperScene = new Scene(this.root, GameStage.VIEWPORT_WIDTH, GameStage.VIEWPORT_HEIGHT);
		this.random          = new Random();
		this.graphicsContext = this.entityCanvas.getGraphicsContext2D();
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
		this.stage.setTitle("Spooky Elbi");
		this.stage.setScene(this.gameProperScene);
		this.stage.show();
		this.runAnimation();
	}
	
	public void setMainCharacter(String imageFilename) {
		this.mainCharacter.setImage(imageFilename, 20, 20);
		this.mainCharacter.setPosition(750, 600);
	}
	
	public void setWeapon(String imageFilename) {
		this.weapon.setImage(imageFilename, 10, 10);
		this.weapon.setPosition(760, 600);
		((Sprite) this.weapon).rotateImage(-45);
		((Weapon) this.weapon).reload();
	}
	
	public Sprite spawnEnemy(String imageFilename) {
		Sprite newEnemy = new Enemy();
		newEnemy.setImage(imageFilename, 20, 20);
		
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
		this.entityCanvas.setOnMouseClicked(
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
		
		// Dead bullets
		ArrayList<Sprite> bulletsToBeDeleted = new ArrayList<Sprite>();
		
		for (Sprite bullet : ((Weapon) this.weapon).shotBullets) {
			Bullet bulletE = (Bullet) bullet;
			
			double velocityX = bulletE.getDirectionX() * Bullet.BULLET_SPEED;
			double velocityY = bulletE.getDirectionY() * Bullet.BULLET_SPEED;
			
			bullet.setVelocity(velocityX, velocityY);
			bullet.update(elapsedTime);
			
			if (bulletE.checkEnemiesCollision(this.enemies) || bulletE.reachedMaxRange()) {
				bulletsToBeDeleted.add(bullet);
			}
		}
		
		for (int i = (int) bulletsToBeDeleted.size() - 1; i >= 0; i--) {
			Sprite bulletToBeDeleted = bulletsToBeDeleted.remove(i);
			((Weapon) this.weapon).shotBullets.remove(bulletToBeDeleted);
			bulletToBeDeleted = null;
		}
		
		// Dead enemies
		ArrayList<Sprite> deadEnemies = new ArrayList<Sprite>();
		
		for (Sprite enemy : this.enemies) {
			if (((Enemy) enemy).isDead()) {
				deadEnemies.add(enemy);
			}
		}
		
		for (int i = (int) deadEnemies.size() - 1; i >= 0; i--) {
			Sprite deadEnemy = deadEnemies.remove(i);
			this.enemies.remove(deadEnemy);
			deadEnemy = null;
		}
	}
	
	public void renderEntities() {
		this.graphicsContext.clearRect(0, 0, GameStage.CANVAS_WIDTH, GameStage.CANVAS_HEIGHT);
		this.mainCharacter.render(graphicsContext);
		this.weapon.render(graphicsContext);
		
		for (Sprite enemy : this.enemies) {
			enemy.render(this.graphicsContext);
		}
		
		for (Sprite bullet : ((Weapon) this.weapon).shotBullets) {
			bullet.render(this.graphicsContext);
		}
	}
	
	public void updateArea(Rectangle smallArea) {
		double viewportX = Math.max(0, this.mainCharacter.getPositionX() - (GameStage.VIEWPORT_WIDTH / 2));
        double viewportY = Math.max(0, this.mainCharacter.getPositionY() - (GameStage.VIEWPORT_HEIGHT / 2));
		smallArea.setX(viewportX);
		smallArea.setY(viewportY);
		
		if (viewportX + GameStage.VIEWPORT_WIDTH > GameStage.CANVAS_WIDTH) {
            smallArea.setWidth(GameStage.CANVAS_WIDTH - viewportX);
        } else {
        	smallArea.setWidth(GameStage.VIEWPORT_WIDTH);
        }

        if (viewportY + GameStage.VIEWPORT_HEIGHT > GameStage.CANVAS_HEIGHT) {
        	smallArea.setHeight(GameStage.CANVAS_HEIGHT - viewportY);
        } else {
        	smallArea.setHeight(GameStage.VIEWPORT_HEIGHT);
        }
        
        scrollPane.setHvalue(smallArea.getX() / (GameStage.CANVAS_WIDTH - GameStage.VIEWPORT_WIDTH));
        scrollPane.setVvalue(smallArea.getY() / (GameStage.CANVAS_HEIGHT - GameStage.VIEWPORT_HEIGHT));
	}
	
	public void runAnimation() {
		GameStage reference = this;
		LongValue lastNanoTime = new LongValue(System.nanoTime());
		Rectangle smallArea = new Rectangle(GameStage.VIEWPORT_WIDTH, GameStage.VIEWPORT_HEIGHT);
		this.entityCanvas.setClip(smallArea);
		
		new AnimationTimer() {
			@Override
			public void handle(long currentNanoTime) {
				reference.updateEntities(currentNanoTime, lastNanoTime);
				reference.updateArea(smallArea);
				reference.renderEntities();
			}
		}.start();
	}
}
