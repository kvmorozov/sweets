package ru.morozov.sweetApp;
	
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ru.morozov.sweetApp.UI.MainScene;
import ru.morozov.sweetApp.config.SystemConfigs;

public class SweetMainApp extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Scene scene = new MainScene(primaryStage, new BorderPane());
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(new Image("icons/candy.png"));
			
			int runsCount = SweetContext.getSystemConfigs().getIntProperty(SystemConfigs.PROPERTY_RUNS_COUNT);
			SweetContext.getSystemConfigs().setIntProperty(SystemConfigs.PROPERTY_RUNS_COUNT, ++runsCount);
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
