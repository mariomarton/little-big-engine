package com.mario.LittleBigEngine.backend;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * class for each CELL / GAME FIELD
 * @author mario
 */
public class Cell extends StackPane{
    private int x,y;
    private boolean hasStone;
    private boolean hasStick;
    private boolean chestOpen;
    private boolean visual;
    private int chestValue;

    private static final String GROUND_IMAGE = "file:src/main/java/com/mario/LittleBigEngine/resources/grass.jpg";
    private static final String STICK_IMAGE = "file:src/main/java/com/mario/LittleBigEngine/resources/stick.png";
    private static final String STONE_IMAGE = "file:src/main/java/com/mario/LittleBigEngine/resources/stone.png";
    private static final String CHEST_IMAGE = "file:src/main/java/com/mario/LittleBigEngine/resources/chest.png";
    private static final String OPEN_CHEST_IMAGE = "file:src/main/java/com/mario/LittleBigEngine/resources/chest_open.png";
    
    private ImageView ground, stick, stone, chest;

    /** 
     * getter for X position of the cell
     * @return X position of the cell
     */
    public int getX() {
        return x;
    }

    /** 
     * getter for Y position of the cell
     * @return Y position of the cell
     */
    public int getY() {
        return y;
    }

    /** 
     * getter: returns true if there's a chest on the cell and it's open
     * @return boolean chestOpen
     */
    public boolean getChestOpen() {
        return chestOpen;
    }

    /** 
     * getter for chest value
     * @return chest value
     */
    public int getChestValue() {
        return chestValue;
    }
    
    /**
     *
     * @param x X position of the cell in the grid
     * @param y X position of the cell in the grid
     * @param hasStone boolean - does cell include a stone?
     * @param hasStick boolean - does cell include a stick?
     * @param chestValue value of the possible chest, -1 if there's none
     */
    public Cell(int x, int y, Boolean hasStone, Boolean hasStick, int chestValue){
        this.x = x;
        this.y = y;
        this.hasStone = hasStone;
        this.hasStick = hasStick;
        this.chestValue = chestValue;
        this.chestOpen = false;
        this.visual = true;
        this.fillCell();
    }
    
    /**
     *
     * @param x X position of the cell in the grid
     * @param y X position of the cell in the grid
     * @param hasStone boolean - does cell include a stone?
     * @param hasStick boolean - does cell include a stick?
     * @param chestValue value of the possible chest, -1 if there's none
     * @param test enter parameter if run in a unit test
     */
   public Cell(int x, int y, Boolean hasStone, Boolean hasStick, int chestValue, boolean test){
        this.x = x;
        this.y = y;
        this.hasStone = hasStone;
        this.hasStick = hasStick;
        this.chestValue = chestValue;
        this.chestOpen = false;
        this.visual = false;
    }
    
    private void fillCell(){
        ground = new ImageView(GROUND_IMAGE);
        this.getChildren().add(ground);
        
        if(hasStone){
            stone = new ImageView(STONE_IMAGE);
            this.getChildren().add(stone);
        }
        else if(hasStick){
            stick = new ImageView(STICK_IMAGE);
            this.getChildren().add(stick);
        }
        else if(chestValue>-1){
            chest = new ImageView(CHEST_IMAGE);
            this.getChildren().add(chest);
        }
    }
    
    /**
     * check if the cell is passable
     * @return true if it is passable
     */
    public boolean isPassable(){
        boolean isPassable = true;
        if(this.hasStone || this.chestValue>-1) isPassable = false;
        return isPassable;
    }
    
    /**
     * tries to remove a stick from the cell
     * @return true if successful
     */
    public Boolean removeStick(){
        Boolean removed = false;
        if(this.hasStick){
            this.getChildren().remove(stick);
            this.hasStick = false;
            removed = true;
        }
        return removed;
    }
    
    /**
     * tries to open a chest on the cell
     */
    public void openChest(){
        if(chestValue>=0 && !chestOpen){
            if(visual) chest.setImage(new Image(OPEN_CHEST_IMAGE));
            chestOpen = true;}
    }
        

    }
   
