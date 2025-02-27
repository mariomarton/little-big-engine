package com.mario.LittleBigEngine.frontend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

/**
 *
 * @author mario
 */
public class InfoLabel extends Label {
    
    /**
     * a custom label class used e.g. when picking options in the menu
     */
    public final static String FONT_PATH = "src/main/java/com/mario/LittleBigEngine/resources/Rainwood.ttf";

    /**
     *
     * @param text the text on the label
     */
    public InfoLabel(String text){
        setPrefWidth(330); 
        setPrefHeight(20);
        setText(text);
        setWrapText(true);
        setLabelFont();
        //setAlignment(Pos.CENTER);
    }
    
    private void setLabelFont(){
        
        try {
            setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)),35));
        } catch (FileNotFoundException ex) {
            setFont(Font.font("Calibri",35));
        }
    }
    
    
}
