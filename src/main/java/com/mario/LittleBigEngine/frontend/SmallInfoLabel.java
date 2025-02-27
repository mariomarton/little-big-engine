package com.mario.LittleBigEngine.frontend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * label class used in the menu
 * @author mario
 */
public class SmallInfoLabel extends Label{
    
       private final String FONT_PATH = "src/main/java/com/mario/LittleBigEngine/resources/Rainwood.ttf";
       
    /**
     *
     * @param text text of the label
     */
    public SmallInfoLabel(String text){
           setPrefWidth(90);
           setPrefHeight(19);
           //BackgroundImage backgroundImage = new BackgroundImage(new Image("file:src/main/java/com/mario/LittleBigEngine/resources/hud_back_yellow.jpg",90,19,false,true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null);
           //setBackground(new Background(backgroundImage));
           setAlignment(Pos.CENTER_LEFT);
           //setPadding(new Insets(10,10,10,10));
           setText(text);
           setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
           setLabelFont();        
       }
       
       private void setLabelFont(){
           try {
                setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)),12));
           } catch (FileNotFoundException e) {
               setFont(Font.font("Calibri", FontWeight.BOLD, 14));
           }
           
       }
}
