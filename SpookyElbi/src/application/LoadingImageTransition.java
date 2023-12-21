package application;

import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoadingImageTransition {
	private Main main;
	static int images = 0;
	
	public LoadingImageTransition(Main main) {
		this.main = main;
	}

	public void setStageComponents(Stage primaryStage) {
		Image loadingScreenImage = new Image("scenes//loadingScreen.png", 1200, 900, true, true);
		Image gameLogo = new Image("scenes//gameLogo.png", 1200, 900, true, true);

	    ImageView imageViewofLoadingScreen;
	    ImageView imageViewofGameLogo;
	    
	    // Create ImageViews
	    imageViewofLoadingScreen = new ImageView(loadingScreenImage);
	    imageViewofGameLogo = new ImageView(gameLogo);

	    // Set up fade transition
	    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), imageViewofGameLogo);
	    fadeTransition.setFromValue(0.0);
	    fadeTransition.setToValue(1.0);

        // Set up event handler for when fade transition ends
        fadeTransition.setOnFinished(event -> {
        	if (images == 1) {
        		this.main.startGame(primaryStage);
        		return;
        	}
        	
            imageViewofLoadingScreen.toFront();
            fadeTransition.setNode(imageViewofLoadingScreen);
            fadeTransition.play();
        	images++;
        });

        // Set up initial state
        imageViewofLoadingScreen.toFront();

        // Set up root layout
        StackPane root = new StackPane();
        root.getChildren().addAll(imageViewofLoadingScreen, imageViewofGameLogo);

        // Set up scene
        Scene scene = new Scene(root, 1200, 670);

        // Set up primaryStage
        primaryStage.setScene(scene);
        primaryStage.show();

        // Start the fade transition
        fadeTransition.setNode(imageViewofLoadingScreen);
        fadeTransition.play();
	}
}