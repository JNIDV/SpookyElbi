/********************************************************
 * 
 * 
 * 
 * @author       Cabral, Alexa Gwen; Villamin, Jan Neal Isaac
 * @date_created 10:44 2023-12-04
 * 
 ********************************************************/

package application;

import java.io.File;
import java.io.InputStream;
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

import java.util.Random;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class GameTimer extends AnimationTimer {
	public static final long GAME_DURATION_NANO = 10 * 60 * 1_000_000_000L;
	public static final int CANVAS_WIDTH = 3900;
	public static final int CANVAS_HEIGHT = 3600;
	public static final int VIEWPORT_WIDTH = 1080;
	public static final int VIEWPORT_HEIGHT = 720;
	public static final double WEAPON_OFFSET_X = 40;
	public static final double WEAPON_OFFSET_Y = 30;
	public static final double CHARACTER_VELOCITY = 100;
	public static final double CHARACTER_SIDE = 50;
	public static final double ENEMY_SIDE = 50;
	public static final double WEAPON_SIDE = 15;
	public static final ArrayList<String> MAIN_CHARACTER_FRAME = new ArrayList<String>(Arrays.asList(
		"boy.png",
		"frontwalk.png",
		"boy-ulit.png",
		"frontwalk2.png"
	));
	public static final Image HEART_IMAGE = new Image("images\\heart.png", 40, 40, true, true);
	public static final Image PEN_IMAGE   = new Image("images\\pen.png", 10, 10, true, true);
	public static final Image MAP_IMAGE   = new Image("images\\map.png", CANVAS_WIDTH, CANVAS_HEIGHT, true, true);
	public static final ArrayList<String> MUSIC_PLAYLIST = new ArrayList<String>(Arrays.asList(
		"nightShade.mp3",
		"aNightOfDizzySpells.mp3",
		"maze.mp3",
		"underclocked.mp3"
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
	
	private CooldownTimer collisionTimer;
	private CooldownTimer reloadTimer;
	private CooldownTimer weaponTimer;
	private CooldownTimer frogSpawnTimer;
	private CooldownTimer clockSpawnTimer;
	private CooldownTimer examSpawnTimer;
	
	private LongValue startNanoTime;
	private LongValue lastNanoTime;
	private LongValue frogCount;
	private LongValue clockCount;
	private LongValue examCount;
	
	private int gemCount;
	private boolean gameOver;
	private Text timerText;
	
	private MediaPlayer musicPlayer;
	private int currentMusicIndex;
	
	public GameTimer(Stage stage) {
		super();
		this.stage           = stage;
		this.entityCanvas    = new Canvas(GameTimer.CANVAS_WIDTH, GameTimer.CANVAS_HEIGHT);
		this.scrollPane      = new ScrollPane(this.entityCanvas);
		this.timerText       = new Text();
		this.root            = new StackPane(this.scrollPane, this.timerText);
		this.gameProperScene = new Scene(this.root, GameTimer.VIEWPORT_WIDTH, GameTimer.VIEWPORT_HEIGHT);
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
		this.gemCount        = 0;
	}
	
	public void runSpookyElbi() {
		this.playMusic(MUSIC_PLAYLIST.get(0));
		this.timerText.setFont(Font.font(30));
		StackPane.setAlignment(timerText, javafx.geometry.Pos.TOP_RIGHT);
		
		this.scrollPane.setPrefViewportWidth(GameTimer.VIEWPORT_WIDTH);
		this.scrollPane.setPrefViewportHeight(GameTimer.VIEWPORT_HEIGHT);
		this.scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		this.scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
		
		this.setMainCharacter("characterimages\\boy.png");
		this.handleKeyEvents();
		this.handleMouseEvents();
		
		this.stage.setTitle("Spooky Elbi");
		this.stage.setScene(this.gameProperScene);
		this.stage.show();
		
		this.runAnimation();
	}
	
	public void playMusic(String fileName) {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/audio/" + fileName);
            String fullPath = inputStream != null ? new File("").toURI().relativize(new File(fileName).toURI()).getPath() : null;

            if (fullPath != null) {
                Media music = new Media(getClass().getResource("/audio/" + fileName).toExternalForm());
                this.musicPlayer = new MediaPlayer(music);
                this.musicPlayer.setVolume(0.5);
                this.musicPlayer.setOnEndOfMedia(this::playNextMusic);
                this.musicPlayer.play();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playNextMusic() {
        try {
            if (musicPlayer != null) {
                musicPlayer.stop();
                musicPlayer.dispose();
            }
            currentMusicIndex = (currentMusicIndex + 1) % MUSIC_PLAYLIST.size();
            playMusic(MUSIC_PLAYLIST.get(currentMusicIndex));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public void setMainCharacter(String imageFilename) {
		this.mainCharacter.setImage(imageFilename, GameTimer.CHARACTER_SIDE, GameTimer.CHARACTER_SIDE);
		this.mainCharacter.setPosition(GameTimer.CANVAS_WIDTH / 2, GameTimer.CANVAS_HEIGHT / 2);
		((MainCharacter) this.mainCharacter).setHearts(3);
	}
	
	public void setWeapon(String imageFilename) {
		this.weapon.setImage(imageFilename, GameTimer.WEAPON_SIDE, GameTimer.WEAPON_SIDE);
		this.weapon.setPosition(GameTimer.CANVAS_WIDTH / 2 + 10, GameTimer.CANVAS_HEIGHT);
		((Sprite) this.weapon).rotateImage(-45);
		((Weapon) this.weapon).reload();
	}
	
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
		this.weapon.setPosition(GameTimer.CANVAS_WIDTH / 2 + 20, GameTimer.CANVAS_HEIGHT);
		((Sprite) this.weapon).rotateImage(-45);
		((Weapon) this.weapon).reload();
	}
	
	public void spawnFrogs(int frogCount) {
		for (int i = 0; i < frogCount; i++) {
			Sprite newFrog = new Frog();
			double startingX = this.random.nextDouble() * GameTimer.CANVAS_WIDTH;
			double startingY = this.random.nextDouble() * GameTimer.CANVAS_HEIGHT;
			newFrog.setPosition(startingX, startingY);
			this.enemies.add(newFrog);
		}
	}
	
	public void spawnClocks(int clockCount) {
		for (int i = 0; i < clockCount; i++) {
			Sprite newClock = new Clock();
			double startingX = this.random.nextDouble() * GameTimer.CANVAS_WIDTH;
			double startingY = this.random.nextDouble() * GameTimer.CANVAS_HEIGHT;
			newClock.setPosition(startingX, startingY);
			this.enemies.add(newClock);
		}
	}
	
	public void spawnExams(int examCount) {
		for (int i = 0; i < examCount; i++) {
			Sprite newExam = new Exam();
			double startingX = this.random.nextDouble() * GameTimer.CANVAS_WIDTH;
			double startingY = this.random.nextDouble() * GameTimer.CANVAS_HEIGHT;
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
						double targetX = x - GameTimer.VIEWPORT_WIDTH / 2 - GameTimer.WEAPON_OFFSET_X + weaponReference.getPositionX();
						double targetY = y - GameTimer.VIEWPORT_HEIGHT / 2 - GameTimer.WEAPON_OFFSET_Y + weaponReference.getPositionY();
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
			((MainCharacter) this.mainCharacter).setState(((int) (currentNanoTime / 1e8)) % 4);
		} else {
			((MainCharacter) this.mainCharacter).setIsMoving(false);
			((MainCharacter) this.mainCharacter).setState(0);
		}
		
		if (this.input.contains("W")) {
			this.mainCharacter.addVelocity(0, -GameTimer.CHARACTER_VELOCITY);
		}
		
		if (this.input.contains("A")) {
			this.mainCharacter.addVelocity(-GameTimer.CHARACTER_VELOCITY, 0);
		}
		
		if (this.input.contains("S")) {
			this.mainCharacter.addVelocity(0, GameTimer.CHARACTER_VELOCITY);
		}
		
		if (this.input.contains("D")) {
			this.mainCharacter.addVelocity(GameTimer.CHARACTER_VELOCITY, 0);
		}
		
		this.mainCharacter.update(elapsedTime);
	}
	
	public void removeDeadEnemies() {
		Iterator<Sprite> enemyIterator = this.enemies.iterator();
		
		while (enemyIterator.hasNext()) {
			Enemy enemy = (Enemy) enemyIterator.next();
			
			if (enemy.isDead()) {
				this.gemCount++;
				System.out.println("Gem count: " + this.gemCount);
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
			    this.collisionTimer.activateCooldown(2000);
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
			
			double velocityX = bulletE.getDirectionX() * ((Weapon) this.weapon).getBulletSpeed();
			double velocityY = bulletE.getDirectionY() * ((Weapon) this.weapon).getBulletSpeed();
			
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
			this.mainCharacter.getPositionX() + GameTimer.WEAPON_OFFSET_X,
			this.mainCharacter.getPositionY() + GameTimer.WEAPON_OFFSET_Y
		);
		this.updateEnemies(elapsedTime);
		this.updateBullets(elapsedTime);
		
		lastNanoTime.value = currentNanoTime;
	}
	
	public void renderEntities() {
		this.graphicsContext.clearRect(0, 0, GameTimer.CANVAS_WIDTH, GameTimer.CANVAS_HEIGHT);
		this.graphicsContext.drawImage(GameTimer.MAP_IMAGE, 0, 0);
		this.mainCharacter.setImage(
			"characterimages\\" + GameTimer.MAIN_CHARACTER_FRAME.get(((MainCharacter) this.mainCharacter).getState()), 
			GameTimer.CHARACTER_SIDE, 
			GameTimer.CHARACTER_SIDE
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
		double viewportX = this.mainCharacter.getPositionX() - (GameTimer.VIEWPORT_WIDTH / 2);
        double viewportY = this.mainCharacter.getPositionY() - (GameTimer.VIEWPORT_HEIGHT / 2);
        this.scrollPane.setHvalue(viewportX / (GameTimer.CANVAS_WIDTH - GameTimer.VIEWPORT_WIDTH));
        this.scrollPane.setVvalue(viewportY / (GameTimer.CANVAS_HEIGHT - GameTimer.VIEWPORT_HEIGHT));
	}
	
	public void displayHearts() {
		for (int i = 0; i < ((MainCharacter) this.mainCharacter).getHearts(); i++) {
			this.graphicsContext.drawImage(GameTimer.HEART_IMAGE, 
				this.mainCharacter.getPositionX() - (GameTimer.VIEWPORT_WIDTH / 2) + i * 40 + 10, 
				this.mainCharacter.getPositionY() - (GameTimer.VIEWPORT_HEIGHT / 2) + 10
			);
		}
	}
	
	public void displayAmmo() {
		if (!this.reloadTimer.isActiveCooldown()) {
			this.graphicsContext.strokeText(
				"Reload ready!", 
				this.mainCharacter.getPositionX() - (GameTimer.VIEWPORT_WIDTH / 2) + 10, 
				this.mainCharacter.getPositionY() + (GameTimer.VIEWPORT_HEIGHT / 2) - 50
			);
		}
		
		for (int i = 0; i < ((Weapon) this.weapon).getAmmoCount(); i++) {
			this.graphicsContext.drawImage(GameTimer.PEN_IMAGE, 
				this.mainCharacter.getPositionX() - (GameTimer.VIEWPORT_WIDTH / 2) + i * 15 + 10, 
				this.mainCharacter.getPositionY() + (GameTimer.VIEWPORT_HEIGHT / 2) - 20
			);
		}
	}
	
	public void runAnimation() {
		this.startNanoTime = new LongValue(System.nanoTime());
		this.lastNanoTime = new LongValue(startNanoTime.value);
		this.frogCount = new LongValue(5);
		this.clockCount = new LongValue(1);
		this.examCount = new LongValue(0);
		this.clockSpawnTimer.activateCooldown(60000);
		this.examSpawnTimer.activateCooldown(90000);
		this.start();
	}
	
	@Override
	public void handle(long currentNanoTime) {			
		long remainingTime = (GAME_DURATION_NANO - (currentNanoTime - this.startNanoTime.value)) / 1_000_000_000L;
		this.timerText.setText("Time Remaining: " + remainingTime / 60 + " : " + remainingTime % 60 + " ");
		
		if (!this.frogSpawnTimer.isActiveCooldown()) {
			this.spawnFrogs((int) ++this.frogCount.value);
			this.frogSpawnTimer.activateCooldown(30000);
		}
		
		if (!this.clockSpawnTimer.isActiveCooldown()) {
			this.spawnClocks((int) ++this.clockCount.value);
			this.clockSpawnTimer.activateCooldown(60000);
		}
		
		if (!this.examSpawnTimer.isActiveCooldown()) {
			this.spawnExams((int) ++this.examCount.value);
			this.examSpawnTimer.activateCooldown(90000);
		}
		
		this.updateEntities(currentNanoTime, this.lastNanoTime);
		this.updateArea();
		this.renderEntities();
		
		if (this.input.contains("Q")) {
			this.gameOver = true;
		}
		
		if (remainingTime <= 0) {
			remainingTime = 0;
			this.gameOver = true;
		}
		
		if (this.gameOver) {
			this.gemCount += (currentNanoTime - this.startNanoTime.value) / 1_000_000_000L;
			System.out.println("Gem count: " + this.gemCount);
			this.stop();
		}
	}
}
