/********************************************************
 * 
 * 
 * 
 * @author       Cabral, Alexa Gwen; Villamin, Jan Neal Isaac
 * @date_created 10:44 2023-12-04
 * 
 ********************************************************/

package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import weapons.Paper;
import weapons.Pen;
import weapons.Pistol;
import weapons.Shotgun;

public class SpookyElbi extends Application {
    private Scene menuScene;
    private Scene settingsScene;
    private Scene aboutGameScene;
    private Scene weaponSelectScene;

    @Override
    public void start(Stage primaryStage) {
    	primaryStage.setTitle("Spooki ELBI");
    	
        // Main Menu [Title]
        StackPane menuLayout = new StackPane();

        Image bg = new Image("scenes\\Menu.png", 1200, 900, true, true);
        ImageView iv1 = new ImageView();
        iv1.setPreserveRatio(true);
        iv1.setImage(bg);

        VBox buttonScreen = new VBox(18);
        buttonScreen.setAlignment(Pos.TOP_CENTER);

        buttonScreen.getChildren().addAll(
        	clearButton(e -> {
        		this.createNewGame(primaryStage);
        		primaryStage.setScene(weaponSelectScene);
    		}, 400, 50),        
            clearButton(e -> primaryStage.setScene(settingsScene), 400, 50),
            clearButton(e -> primaryStage.setScene(aboutGameScene), 400, 50),
            clearButton(e -> primaryStage.close(), 400, 50)
        );

        menuLayout.getChildren().addAll(
            iv1, buttonScreen
        );
        
        menuScene = new Scene(menuLayout, 1200, 680);

        // Settings
        StackPane settingsLayout = new StackPane();

        VBox returnScreen2 = returnScreen(e -> primaryStage.setScene(menuScene));;
        Image bg2 = createimg("scenes\\settings.png");
        ImageView iv2 = new ImageView();
        iv2.setPreserveRatio(true);
        iv2.setImage(bg2);

        settingsLayout.getChildren().addAll(
        	iv2, returnScreen2
        );

        settingsScene = new Scene(settingsLayout, 1200, 680);

        // About Game
        StackPane aboutGameLayout = new StackPane();

        VBox returnScreen3 = returnScreen(e -> primaryStage.setScene(menuScene));
        Image bg3 = createimg("scenes\\settings.png");
        ImageView iv3 = new ImageView();
        iv3.setPreserveRatio(true);
        iv3.setImage(bg3);

        aboutGameLayout.getChildren().addAll(
        	iv3, returnScreen3
        );

        aboutGameScene = new Scene(aboutGameLayout, 1200, 680);

        primaryStage.setScene(menuScene);
        primaryStage.setTitle("Spooky Elbi");
        primaryStage.show();
    }

	public Button clearButton(EventHandler<ActionEvent> handler, double width, double height) {
        Button button = new Button();
        button.setPrefWidth(width);
        button.setPrefHeight(height);
        button.setStyle("-fx-opacity: 25;");
        button.setOnAction(handler);
        return button;
    }

	private VBox returnScreen(EventHandler<ActionEvent> handler) {
        VBox returnScreen = new VBox(18);
        returnScreen.getChildren().addAll(clearButton(handler, 50, 50));
        return returnScreen;
    }

	private Image createimg(String filename){
		Image pic = new Image(filename, 1200, 800, true, true);
		return pic;
	}

    public static void main(String[] args) {
        launch(args);
    }
    
    public Scene getMenuScene() {
    	return this.menuScene;
    }
    
    private void createNewGame(Stage primaryStage) {
    	GameTimer gameTimer = new GameTimer(primaryStage, this);
    	gameTimer.setWeapon(new Paper());
    	
    	// Weapon Select
        AnchorPane weaponSelectLayout = new AnchorPane();
        
        Button paperButton = new Button();
        Image paperImage = new Image("images\\paper.png", 50, 50, true, true);
        ImageView paperImageView = new ImageView(paperImage);
        paperImageView.setFitWidth(50);
        paperImageView.setFitHeight(50);
        paperButton.setGraphic(paperImageView);
        AnchorPane.setLeftAnchor(paperButton, (double) 50);
        AnchorPane.setTopAnchor(paperButton, (double) 200);
        paperButton.setOnAction(
        	e -> {
        		gameTimer.setWeapon(new Paper());
        	}
        );
        
        Button penButton = new Button();
        Image penImage = new Image("images\\pen.png", 50, 50, true, true);
        ImageView penImageView = new ImageView(penImage);
        penImageView.setFitWidth(50);
        penImageView.setFitHeight(50);
        penButton.setGraphic(penImageView);
        AnchorPane.setLeftAnchor(penButton, (double) 150);
        AnchorPane.setTopAnchor(penButton, (double) 200);
        penButton.setOnAction(
        	e -> {
        		gameTimer.setWeapon(new Pen());
        	}
        );
        
        Button pistolButton = new Button();
        Image pistolImage = new Image("images\\pistol.png", 50, 50, true, true);
        ImageView pistolImageView = new ImageView(pistolImage);
        pistolImageView.setFitWidth(50);
        pistolImageView.setFitHeight(50);
        pistolButton.setGraphic(pistolImageView);
        AnchorPane.setLeftAnchor(pistolButton, (double) 250);
        AnchorPane.setTopAnchor(pistolButton, (double) 200);
        pistolButton.setOnAction(
        	e -> {
        		gameTimer.setWeapon(new Pistol());
        	}
        );
        
        Button shotgunButton = new Button();
        Image shotgunImage = new Image("images\\shotgun.png", 50, 50, true, true);
        ImageView shotgunImageView = new ImageView(shotgunImage);
        shotgunImageView.setFitWidth(50);
        shotgunImageView.setFitHeight(50);
        shotgunButton.setGraphic(shotgunImageView);
        AnchorPane.setLeftAnchor(shotgunButton, (double) 350);
        AnchorPane.setTopAnchor(shotgunButton, (double) 200);
        shotgunButton.setOnAction(
        	e -> {
        		gameTimer.setWeapon(new Shotgun());
        	}
        );
        
        Button playButton = new Button();
        AnchorPane.setRightAnchor(playButton, (double) 50);
        AnchorPane.setBottomAnchor(playButton, (double) 50);
        playButton.setOnAction(
    		e -> {
    			gameTimer.runSpookyElbi();
    		}
		);
        
        weaponSelectLayout.getChildren().addAll(
        	paperButton,
        	penButton,
        	pistolButton,
        	shotgunButton,
        	playButton
        );
        
        weaponSelectScene = new Scene(weaponSelectLayout, 1200, 680);
    }
}
