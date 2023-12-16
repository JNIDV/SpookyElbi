package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
//import javafx.scene.Group;
//import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
//import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
//import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
//import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class SpookyElbi extends Application {
    private Scene scene1;
    private Scene scene2;
    private Scene scene3;
    private Scene scene4;

    @Override
    public void start(Stage primaryStage) {
    	primaryStage.setTitle("Spooki ELBI");
    	GameStage gameStage = new GameStage(primaryStage);
    	
        // Main Menu [Title]
        StackPane layout1 = new StackPane();

        Canvas canvas = new Canvas(1200, 800);
//        GraphicsContext gc = canvas.getGraphicsContext2D();
        Image bg = new Image("scenes\\Menu.png", 1200, 900, true, true);
        ImageView iv1 = new ImageView();
        iv1.setPreserveRatio(true);
        iv1.setImage(bg);

        VBox buttonScreen = new VBox(18);
        buttonScreen.setAlignment(Pos.TOP_CENTER);

        buttonScreen.getChildren().addAll(
        	clearButton(e -> gameStage.runSpookyElbi()),
            clearButton(e -> primaryStage.setScene(scene2)),
            clearButton(e -> primaryStage.setScene(scene3)),
            clearButton(e -> primaryStage.setScene(scene4))
        );

        layout1.getChildren().addAll(
            canvas, iv1, buttonScreen
        );
        
        scene1 = new Scene(layout1, 1200, 670);

        // Settings
        StackPane layout2 = new StackPane();

        VBox returnScreen2 = returnScreen(e -> primaryStage.setScene(scene1));;
        Image bg2 = createimg("scenes\\settings.png");
        ImageView iv2 = new ImageView();
        iv2.setPreserveRatio(true);
        iv2.setImage(bg2);

        layout2.getChildren().addAll(
        	iv2, returnScreen2
        );

        scene2 = new Scene(layout2, 1200, 680);

        // About Game
        StackPane layout3 = new StackPane();

        VBox returnScreen3 = returnScreen(e -> primaryStage.setScene(scene1));
        Image bg3 = createimg("scenes\\settings.png");
        ImageView iv3 = new ImageView();
        iv3.setPreserveRatio(true);
        iv3.setImage(bg3);

        layout3.getChildren().addAll(
        	iv3, returnScreen3
        );

        scene3 = new Scene(layout3, 1200, 680);

        // Exit
        StackPane layout4 = new StackPane();

        VBox returnScreen4 = returnScreen(e -> primaryStage.setScene(scene1));
        Image bg4 = createimg("scenes\\settings.png");
        ImageView iv4 = new ImageView();
        iv4.setPreserveRatio(true);
        iv4.setImage(bg4);

        layout4.getChildren().addAll(
        	iv4, returnScreen4
        );

        scene4 = new Scene(layout4, 1200, 680);

        primaryStage.setScene(scene1);
        primaryStage.setTitle("Spooky Elbi");
        primaryStage.show();
    }

	private Button clearButton(EventHandler<ActionEvent> handler) {
        Button button = new Button();
        button.setPrefWidth(400);
        button.setPrefHeight(60);
        button.setStyle("-fx-opacity: 0;");
        button.setOnAction(handler);
        return button;
    }

	private VBox returnScreen(EventHandler<ActionEvent> handler) {
        VBox returnScreen = new VBox(18);
        returnScreen.getChildren().addAll(clearButton(handler));
        return returnScreen;
    }

	private Image createimg(String filename){
		Image pic = new Image(filename, 1200, 900, true, true);
		return pic;
	}

    public static void main(String[] args) {
        launch(args);
    }
}