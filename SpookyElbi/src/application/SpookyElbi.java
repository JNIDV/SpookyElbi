package application;

import javafx.application.Application;
import javafx.stage.Stage;

public class SpookyElbi extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) {
		GameStage gameStage = new GameStage(stage);
		gameStage.runSpookyElbi();
	}
}
