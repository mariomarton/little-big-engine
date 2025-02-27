package com.mario.LittleBigEngine.frontend;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * class for the option for picking between options in the menu
 * @author mario
 */
public class OptionPicker extends VBox {
    
    private ImageView circleImage;
    private ImageView optionImage;
    
    private final String circleNotChosen = "file:src/main/java/com/mario/LittleBigEngine/resources/circle_notchosen.png";
    private final String circleChosen = "file:src/main/java/com/mario/LittleBigEngine/resources/circle_chosen.png";
    
    private OPTION option;
    private boolean isChosen;
    
    /**
     *
     * @param option option from the ENUM
     */
    public OptionPicker(OPTION option){
        circleImage = new ImageView(circleNotChosen);
        optionImage = new ImageView(option.getUrl());
        this.option = option;
        isChosen = false;
        this.setAlignment(Pos.CENTER);
        //this.setSpacing(20);
        this.getChildren().add(optionImage);
        this.getChildren().add(circleImage);
    }

    /**
     * getter for option
     * @return the option
     */
    public OPTION getOption(){
        return option;
    }
    
    /**
     * getter for is chosen
     * @return true if it is chosen
     */
    public boolean getIsChosen(){
        return isChosen;
    }
    
    /**
     * setter for is chosen
     * @param info true if chosen
     */
    public void setIsChosen(boolean info){
       this.isChosen = info;
       String imageToSet = info ? circleChosen : circleNotChosen;
       circleImage.setImage(new Image(imageToSet));
    }
    
}
