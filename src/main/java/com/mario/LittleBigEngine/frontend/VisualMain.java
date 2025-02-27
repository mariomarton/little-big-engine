package com.mario.LittleBigEngine.frontend;

import com.mario.LittleBigEngine.backend.Game;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * key class for the menu screens
 * @author mario
 */
public class VisualMain {
    
    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;
    private static final int WIDTH = 960;
    private static final int HEIGTH = 540;
    
    private static final int MENU_BUTTON_START_X = 100;
    private static final int MENU_BUTTON_START_Y = 150;
    
    private LittleBigSubscene settingsScene;
    private LittleBigButton settingsButton;
    private LittleBigSubscene runScene;
    private LittleBigButton runButton;
    private LittleBigButton exitButton;
    
    private LittleBigSubscene sceneToHide;
    private LittleBigSubscene optionPickerSubscene;
    
    private final String text_OptionPicking;
    private final String MANUAL_IMAGE = "file:src/main/java/com/mario/LittleBigEngine/resources/manual_small.jpg";
    
    List<LittleBigButton> menuButtons;
    List<OptionPicker> optionsList;
    private OPTION chosenOption;
    
    private final static Logger logger = Logger.getLogger(VisualMain.class.getName());
    private FileHandler fileHandler;
    
    /**
     * constructor
     */
    public VisualMain(){
        menuButtons = new ArrayList<>();
        mainPane = new AnchorPane();
        mainStage = new Stage();
        mainScene = new Scene(mainPane, WIDTH, HEIGTH);
        mainStage.setScene(mainScene);
        sceneToHide = null;
        text_OptionPicking = "Pick something";
        createButtons();
        createBackground();
        createLogo();
        createSomeScene();
        prepareTheLogger();
    }
    
    /**
     * getter for MainStage
     * @return MainStage
     */
    public Stage getMainStage(){
        return mainStage;
    }
    
    /**
     * getter for the engine logger
     * @return logger
     */
    public Logger getLogger(){
        return logger;
    }
    
    private void prepareTheLogger(){
        try {
            fileHandler = new FileHandler("engineLOG.log",true);
            logger.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();  
            fileHandler.setFormatter(formatter);  
        } catch (IOException | SecurityException ex) {
            Logger.getLogger(VisualMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        logger.info("***NEW SESSION STARTED, Engine running.***\n\n");
    }
    
    private void addMenuButton(LittleBigButton button){
        button.setLayoutX(MENU_BUTTON_START_X);
        button.setLayoutY(MENU_BUTTON_START_Y + menuButtons.size() * 100);
        menuButtons.add(button);
        mainPane.getChildren().add(button);
    }
    
    private LittleBigButton createSomeButton(String text){
        LittleBigButton button = new LittleBigButton(text);
        addMenuButton(button);
        return button;
    }
    
    
    private void createButtons(){
        runButton = createSomeButton("RUN");
        settingsButton = createSomeButton("MANUAL");
        exitButton = createSomeButton("EXIT");
    }  
    
    private void processSubScene(LittleBigSubscene scene){
        if(sceneToHide == scene){
            scene.hideSubScene();
            sceneToHide = null;
        }
        else{
            if(sceneToHide != null){
                sceneToHide.hideSubScene();
            }
            scene.showSubScene();
            sceneToHide = scene;
        }

    }
    
    private void createOptionPickerSubscene(){
        optionPickerSubscene = new LittleBigSubscene();
        mainPane.getChildren().add(optionPickerSubscene);
        
        InfoLabel pickOptionLabel = new InfoLabel(text_OptionPicking);
        pickOptionLabel.setLayoutX(183);
        pickOptionLabel.setLayoutY(163);
        optionPickerSubscene.getPane().getChildren().add(pickOptionLabel);
        optionPickerSubscene.getPane().getChildren().add(createOptionstoChoose());
        optionPickerSubscene.getPane().getChildren().add(createStartButton());
    
    }
    
    private void createManualSubscene(){
        
        ImageView manual = new ImageView(new Image(MANUAL_IMAGE));
        manual.prefWidth(500);
        manual.prefHeight(500);
        settingsScene.getPane().getChildren().add(manual);
    
    }
    
    private void createSomeScene(){
        settingsScene = new LittleBigSubscene(true);
        runScene = new LittleBigSubscene();
        createOptionPickerSubscene();
        createManualSubscene();
        
        settingsButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                processSubScene(settingsScene);
            }
            
            
        });
        
        settingsScene.setLayoutX(960);
        settingsScene.setLayoutY(20);
        
        optionPickerSubscene.setLayoutX(960);
        optionPickerSubscene.setLayoutY(100);
        mainPane.getChildren().add(settingsScene);
        

        runButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){

                processSubScene(optionPickerSubscene);
            }
        });
        
        
        exitButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                mainStage.close();
            }
        });
        

        
    }
    
    private HBox createOptionstoChoose(){
        HBox box = new HBox();
        box.setSpacing(25);
        optionsList = new ArrayList<>();
        for(OPTION option : OPTION.values()){
            final OptionPicker optionToPick = new OptionPicker(option);
            box.getChildren().add(optionToPick);
            optionsList.add(optionToPick);
            optionToPick.setOnMouseClicked(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent event){
                    for(OptionPicker opt : optionsList){
                        opt.setIsChosen(false);
                    }
                        optionToPick.setIsChosen(true);
                        chosenOption = optionToPick.getOption();                        
                }
            });
        }
        box.setLayoutX(153);
        box.setLayoutY(65);
        return box; 
    }
    
    private void createBackground(){
        try {
            Image backgroundImage = new Image("file:src/main/java/com/mario/LittleBigEngine/resources/background.png");
            BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
            mainPane.setBackground(new Background(background));
        } catch (Exception e) {
            System.out.println(e);
            logger.log(Level.SEVERE, "OOPS, BACKGROUND IMAGE NOT FOUND.{0}", e);
        }
        
    }
    
    private void createLogo(){
        final ImageView logo = new ImageView("file:src/main/java/com/mario/LittleBigEngine/resources/enginelogo.png");
        logo.setLayoutX(700);
        logo.setLayoutY(185);
        
        logo.setOnMouseEntered(new EventHandler<MouseEvent>() { 
            @Override
            public void handle(MouseEvent event){
                logo.setEffect(new DropShadow());
            }
    });
        
        logo.setOnMouseExited(new EventHandler<MouseEvent>() { 
            @Override
            public void handle(MouseEvent event){
                logo.setEffect(null);
            }
    });
        
        mainPane.getChildren().add(logo);
    
    }
    
    private LittleBigButton createStartButton(){
        LittleBigButton startButton = new LittleBigButton("Start");
        startButton.setLayoutX(150);
        startButton.setLayoutY(250);
        
        startButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                if(chosenOption != null){
                    Game gameWindow = new Game(chosenOption, logger);
                    gameWindow.createNewGame(mainStage);
                    processSubScene(runScene);
                    logger.log(Level.INFO, "User started the game. Level: {0}", chosenOption);
                }
            }
    
        });
        
        return startButton;
    }

}
