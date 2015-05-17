package ru.morozov.sweetApp;
	
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ru.morozov.sweetApp.UI.MainScene;

public class SweetMainApp extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Scene scene = new MainScene(primaryStage, new VBox());
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(new Image("icons/candy.png"));
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
