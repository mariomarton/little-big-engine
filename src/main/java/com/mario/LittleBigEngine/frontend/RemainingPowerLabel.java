package com.mario.LittleBigEngine.frontend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * class for the enemy power HUD
 * @author mario
 */
public class RemainingPowerLabel extends Label{
    
       private final String FONT_PATH = "src/main/java/com/mario/LittleBigEngine/resources/Rainwood.ttf";
       
    /**
     *
     * @param text the number on the HUD in String form
     */
    public RemainingPowerLabel(String text){
           setPrefWidth(12);
           setPrefHeight(12);
           BackgroundImage backgroundImage = new BackgroundImage(new Image("file:src/main/java/com/mario/LittleBigEngine/resources/black.jpg",12,12,false,true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null);
           setBackground(new Background(backgroundImage));
           setAlignment(Pos.CENTER);
           //setPadding(new Insets(10,10,10,10));
           setText(text);
           setStyle("-fx-text-fill: white; -fx-font-size: 12px;");
           setFont(Font.font("Calibri", FontWeight.BOLD, 12));
       }
       
}
