package com.mario.LittleBigEngine.frontend;

import javafx.scene.text.Font;
import javafx.scene.input.*;
import javafx.scene.control.Button;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.event.EventHandler;
import javafx.scene.effect.DropShadow;

/**
 * a custom button class for the menu buttons
 * @author mario
 */
public class LittleBigButton extends Button {
    
    private final String BUTTON_STYLE_PRESSED = "-fx-background-color: lightgreen; -fx";
    private final String BUTTON_STYLE_NOTPRESSED = "-fx-background-color: DarkSalmon; -fx";
    private final String FONT_PATH = "src/main/java/com/mario/LittleBigEngine/resources/Rainwood.ttf";
    
    /**
     *
     * @param text the text on the button
     */
    public LittleBigButton(String text){
        setText(text);
        setCustomFont();
        setPrefWidth(200);
        setPrefHeight(50);
        setStyle(BUTTON_STYLE_NOTPRESSED);
        initButtonListeners();
    }
    
    private void setCustomFont() { 
        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH), 30));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Calibri", 30));
        }
    }
    
    private void setButtonStyle_Pressed(){
        setStyle(BUTTON_STYLE_PRESSED);
        setPrefHeight(45);
        setLayoutY(getLayoutY() + 5);
    }
    private void setButtonStyle_NotPressed(){
        setStyle(BUTTON_STYLE_NOTPRESSED);
        setPrefHeight(50);
        setLayoutY(getLayoutY() - 5);
    }
    
    private void initButtonListeners(){
        
        setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
              if(event.getButton().equals(MouseButton.PRIMARY)){
                  setButtonStyle_Pressed();}
            }
        });
        
        setOnMouseReleased(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
              if(event.getButton().equals(MouseButton.PRIMARY)){
                  setButtonStyle_NotPressed();}
            }
        });
        
        setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
              setEffect(new DropShadow());
            }
        });
        
        setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
              setEffect(null);
            }
        });
    
    
    }
    
}
