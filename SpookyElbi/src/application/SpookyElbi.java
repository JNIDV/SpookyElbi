package application;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

import entities.Enemy;
import entities.MainCharacter;

import sprite.Sprite;
import wrappers.LongValue;

import java.util.Random;

public class SpookyElbi extends Application {	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) {
		stage.setTitle("Spooky Elbi");
		
		Group root = new Group();
		Scene scene = new Scene(root, 1500, 1200);
		stage.setScene(scene);
		
		Canvas canvas = new Canvas(1500, 1200);
		root.getChildren().add(canvas);
		
		ArrayList<String> input = new ArrayList<String>();
		
		scene.setOnKeyPressed(
			new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent keyEvent) {
					String code = keyEvent.getCode().toString();
					
					if (!input.contains(code)) {
						input.add(code);
					}
				}
			}
		);
		
		scene.setOnKeyReleased(
			new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent keyEvent) {
					String code = keyEvent.getCode().toString();
					input.remove(code);
				}
			}
		);
		
		Random random = new Random();
		
		GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
		
		Sprite mainCharacter = new MainCharacter();
		mainCharacter.setImage("images\\mainCharacter.png");
		
		ArrayList<Sprite> enemies = new ArrayList<Sprite>();
		
		for (int i = 0; i < 10; i++) {
			enemies.add(new Enemy());
			enemies.get(i).setImage("images\\Frog.png");
			double startingX = random.nextDouble() * 1500;
			double startingY = random.nextDouble() * 1200;
			System.out.println(startingX + " " + startingY);
			enemies.get(i).setPosition(startingX, startingY);
		}
		
		LongValue lastNanoTime = new LongValue(System.nanoTime());
		
		new AnimationTimer() {
			@Override
			public void handle(long currentNanoTime) {
				graphicsContext.clearRect(0, 0, 1500, 1200);
				double elapsedTime = (currentNanoTime - lastNanoTime.value) / 1e9;
				lastNanoTime.value = currentNanoTime;
				
				mainCharacter.setVelocity(0, 0);
				
				if (input.contains("W")) {
					mainCharacter.addVelocity(0, -100);
				}
				
				if (input.contains("A")) {
					mainCharacter.addVelocity(-100, 0);
				}
				
				if (input.contains("S")) {
					mainCharacter.addVelocity(0, 100);
				}
				
				if (input.contains("D")) {
					mainCharacter.addVelocity(100, 0);
				}
				
				mainCharacter.update(elapsedTime);
				
				for (int i = 0; i < 10; i++) {
					Sprite enemy = enemies.get(i);
					Enemy enemyE = (Enemy) enemy;
					enemyE.updateDirection(mainCharacter, enemies);
	//				System.out.println(enemyE.getDirectionX() + " " + enemyE.getDirectionY());
					
					enemy.setVelocity(enemyE.getDirectionX() * enemyE.getSpeedX(), enemyE.getDirectionY() * enemyE.getSpeedY());
					enemy.update(elapsedTime);
					enemyE.speedUp(elapsedTime);
					enemy.render(graphicsContext);
				}
				
				mainCharacter.render(graphicsContext);
				
			}
		}.start();
		
		stage.show();
	}
}
