package com.mario.LittleBigEngine.frontend;
import javafx.scene.layout.AnchorPane;
import javafx.scene.SubScene;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.util.Duration;

/**
 * a custom SubScene class for menu screenss
 * @author mario
 */
public class LittleBigSubscene extends SubScene{
    
    private final String FONT_PATH = "src/main/java/com/mario/LittleBigEngine/resources/Rainwood.ttf";
    private final String BACKGROUND_IMAGE = "file:src/main/java/com/mario/LittleBigEngine/resources/window.jpg";
    private boolean shown = false;

    /**
     * getter for shown variable
     * @return true if scene if shown to the user
     */
    public boolean getShown() {
        return shown;
    }
    
    /**
     * constructor
     */
    public LittleBigSubscene(){
        super(new AnchorPane(), 500, 350);
        prefWidth(500);
        prefHeight(400);
        
        BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE,500,350,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        
        AnchorPane root2 = (AnchorPane) this.getRoot();
        root2.setBackground(new Background(image));
       
    }
    


    /**
     * another constructor for MANUAL screen
     * @param input to differentiate constructors
     */
    public LittleBigSubscene(boolean input){
        super(new AnchorPane(), 500, 500);
        prefWidth(500);
        prefHeight(500);
        
        BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE,500,500,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        
        AnchorPane root2 = (AnchorPane) this.getRoot();
        root2.setBackground(new Background(image));
       
    }
    
    /**
     * shows the scene
     */
    public void showSubScene(){
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.5));
        transition.setNode(this);
        transition.setToX(-600);
        transition.play();
        this.shown = true;

        
    }
    
    /**
     * hides the scene
     */
    public void hideSubScene(){
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.5));
        transition.setNode(this);
        transition.setToX(600);
        transition.play();
        this.shown = false;
        }
       
    /**
     *
     * @return the root of the scene
     */
    public AnchorPane getPane(){
        return (AnchorPane) this.getRoot();
    }
    
}
