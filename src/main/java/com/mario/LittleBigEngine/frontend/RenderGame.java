package com.mario.LittleBigEngine.frontend;

import com.mario.LittleBigEngine.backend.Game;
import java.util.Date;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * key class for rendering the game contents
 * @author mario
 */
public class RenderGame {
    
    private Game game;
    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;
    private AnimationTimer gameTimer;
    private ImageView red;
    private Boolean died;
    private EventHandler<ActionEvent> onFinished;
    
    private static final String RED_IMAGE = "file:src/main/java/com/mario/LittleBigEngine/resources/red.png";
    private static final String GAMEOVER_IMAGE = "file:src/main/java/com/mario/LittleBigEngine/resources/gameover.png";
    private static final String GAMEWON_IMAGE = "file:src/main/java/com/mario/LittleBigEngine/resources/gamewon.png";
    
    private Logger logger;
    
    /**
     * setter for logger
     * @param givenLogger the logger
     */
    public void setLogger(Logger givenLogger){
        this.logger = givenLogger;}
    
    /**
     *
     * @param game instance of Game object
     */
    public RenderGame(Game game){
        this.game = game;
    }

    /**
     * getter for gamePane
     * @return gamePane
     */
    public AnchorPane getGamePane() {
        return gamePane;
    }

    /**
     * getter for gameScene
     * @return gameScene
     */
    public Scene getGameScene() {
        return gameScene;
    }

    /**
     * getter for gameStage
     * @return gameStage
     */
    public Stage getGameStage() {
        return gameStage;
    }
    
    /**
     * starts the rendering
     */
    public void inicializeStage(){
        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, game.getWidth(), game.getHeight()+game.CELL_SIZE);
        gameStage = new Stage();
        game.createGrid();
        gameStage.setScene(gameScene);
        died = false;
        createRedOverlay();
        logger.info("Game window running.");
    }
    
    /**
     * the render loop
     */
    public void createGameLoop(){
        gameTimer = new AnimationTimer(){
            long last = 0; 
            int i = 0;
            boolean redShown = false;
            @Override
            public void handle(long now) {
                if(now-last>=30000000){
                    
                    game.chestAction();
                    game.spaceAction();
                    game.setLosingHealth();
                    
                    if(game.getLosingHealth() && !redShown){
                        showRedOverlay();
                        logger.info("Player is getting attacked!");
                        redShown = true;
                    }
                    else if(!game.getLosingHealth() && redShown){
                        hideRedOverlay();
                        if(game.getHealth()>0) logger.info("Yay! Player is survived the attack.");
                        redShown = false;
                    }
                    
                    checkIfDead();
                    checkIfWon();

                    game.checkEnemyLives();
                    game.moveCharacter();

                    game.chestAction();
                    game.updateHUD();
                    game.hideEnemyDamage();
                    
                    if(i>=17){
                        game.moveEnemies();
                        i=0;
                    }
                    else i++;
                    game.spacePressed = false;
                    last = now;
                }
            }
            
        }; 
        gameTimer.start();
        gameStage.show();
    }
    
    private void createRedOverlay(){
        red = new ImageView(RED_IMAGE);
        red.setFitWidth(game.getWidth());
        red.setFitHeight(game.getHeight());
    }

    /**
     * shows red overlay when being hurt
     */
    public void showRedOverlay(){
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.3), red);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        gamePane.getChildren().add(red);          
        fadeIn.play(); 
    }
    
    /**
     * shows GAME OVER when player wins
     * @param won true if the game is won
     */
    public void showGameOverScreen(boolean won){
        ImageView gameOver;
        if(won){gameOver = new ImageView(GAMEWON_IMAGE);}
        else{gameOver = new ImageView(GAMEOVER_IMAGE);}
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), gameOver);
        fadeIn.setFromValue(0.6);
        fadeIn.setToValue(1.0);
        gamePane.getChildren().add(gameOver);
        gameOver.setLayoutX(game.getWidth()/2-45);
        gameOver.setLayoutY(game.getHeight()/2-30);
        final FadeTransition stayStill = new FadeTransition(Duration.seconds(2), gameOver);
        stayStill.setFromValue(1.0);
        stayStill.setToValue(1.0);
        final FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), gameOver);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeIn.play(); 
        fadeIn.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                        stayStill.play();
                }
            });
         stayStill.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                        fadeOut.play();
                }
            });       
        fadeOut.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                        gameStage.close();
                        game.menuStage.show();
                }
            });        
    }

    /**
     * hides the red overlay
     */
    public void hideRedOverlay(){
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.3), red);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.play();
        fadeOut.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    gamePane.getChildren().remove(red);
                }
            });       
    }
    
    private void checkIfDead(){
        if(game.health<=0 && !died){
            logger.info("Player DIED.");
            died = true;
            game.health = 0;
            game.updateHUD();
            showGameOverScreen(false);
            gameTimer.stop();
            game.writeEndGameToFile(false);
        }
    }
    
    private void checkIfWon(){
            if(!game.livingEnemiesLeft() && !game.closedChestsLeft()){
                logger.info("All chests are open. No enemies left.");
                logger.info("Player WON.");
                showGameOverScreen(true);
                gameTimer.stop();
                game.writeEndGameToFile(true);
            }
    }
    
    
}
