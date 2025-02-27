package com.mario.LittleBigEngine.frontend;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * JavaFX powered LittleBigEngine
 * this is the main application class
 */
public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        VisualMain manager = new VisualMain();
        primaryStage = manager.getMainStage(); 
        primaryStage.show();
    }

    /**
     * launches the engine
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }

}