package com.mario.LittleBigEngine.backend;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * class for each enemy
 * @author mario
 */
public class Enemy {
    
    private final int category;
    private int power;
    private int x,y;
    private char direction;
    private boolean dead;
    private char lastMove;
    private boolean fightingMode;
    private ImageView visual;
    private static final String EASY_IMAGE = "file:src/main/java/com/mario/LittleBigEngine/resources/easy_u.png";
    private static final String MEDIUM_IMAGE = "file:src/main/java/com/mario/LittleBigEngine/resources/medium_u.png";
    private static final String HARD_IMAGE = "file:src/main/java/com/mario/LittleBigEngine/resources/hard_u.png";
    private Image E_U, E_D, E_L, E_R, M_U, M_D, M_L, M_R, H_U, H_D, H_L, H_R;

    /**
     * getter for power of the enemy
     * @return power of the enemy
     */
    public int getPower() {
        return power;
    }

    /**
     * getter for X position
     * @return X position
     */
    public int getX() {
        return x;
    }

    /**
     * getter for Y position
     * @return Y position
     */
    public int getY() {
        return y;
    }

    /**
     * getter for enemy direction
     * @return the direction
     */
    public char getDirection() {
        return direction;
    }

    /**
     * getter for enemy's last move
     * @return enemy's last move
     */
    public char getLastMove() {
        return lastMove;
    }

    /**
     * getter for fighting mode
     * @return true if enemy is in a fight
     */
    public boolean getFightingMode() {
        return fightingMode;
    }

    /**
     * getter for the visual representation of the enemy
     * @return the enemy ImageView object
     */
    public ImageView getVisual() {
        return visual;
    }
    
    /**
     * getter for dead variable
     * @return true if enemy is dead
     */
    public boolean getDead(){
        return dead;
    }
        
    /**
     * setter for fighting mode
     * @param input pass true if enemy is in a fight
     */
    public void setFightingMode(boolean input){
        this.fightingMode = input;
    }
    
    /**
     * setter for enemy's power
     * @param power the power of the enemy
     */
    public void setPower(int power){
        this.power = power;
    }
    
    /**
     * setter for X position
     * @param x
     */
    public void setX(int x){
        this.x = x;
    }
    
    /**
     * setter for Y position
     * @param y
     */
    public void setY(int y){
        this.y = y;
    }
    
    /**
     * setter for last move
     * @param move
     */
    public void setLastMove(char move){
        this.lastMove = move;
    }
    
    /**
     * setter for the dead variable
     * @param dead
     */
    public void setDead(boolean dead){
        this.dead = dead;
    }
    
    /**
     *
     * @param power
     * @param x position X
     * @param y position Y
     */
    public Enemy(int power, int x, int y){
        this.power = power;
        this.x = x;
        this.y = y;
        this.lastMove = '0';
        this.direction = 'u';
        this.fightingMode = false;
        this.category = power;
        this.dead = false;
        createEnemy();
        inicialiseAssets();
    }
    
    private void createEnemy(){
        if(power>0 && power<3){
            visual = new ImageView(EASY_IMAGE);}
        else if(power>=3 && power<5){
            visual = new ImageView(MEDIUM_IMAGE);}
        else if (power==5){
            visual = new ImageView(HARD_IMAGE);}
    }
    
    /**
     *
     * @param dir new direction to rotate to
     */
    public void rotateEnemy(char dir){
        direction = dir;
        if(category>0 && category<3){
            if(dir=='u') this.visual.setImage(E_U);
            else if(dir=='d') this.visual.setImage(E_D);
            else if(dir=='l') this.visual.setImage(E_L);
            else if(dir=='r') this.visual.setImage(E_R);}
        else if(category>=3 && category<5){
            if(dir=='u') this.visual.setImage(M_U);
            else if(dir=='d') this.visual.setImage(M_D);
            else if(dir=='l') this.visual.setImage(M_L);
            else if(dir=='r') this.visual.setImage(M_R);}
        else if(category==5){
            if(dir=='u') this.visual.setImage(H_U);
            else if(dir=='d') this.visual.setImage(H_D);
            else if(dir=='l') this.visual.setImage(H_L);
            else if(dir=='r') this.visual.setImage(H_R);}
    }
    
    private void inicialiseAssets(){
        E_U = new Image("file:src/main/java/com/mario/LittleBigEngine/resources/easy_u.png");
        E_D = new Image("file:src/main/java/com/mario/LittleBigEngine/resources/easy_d.png");        
        E_L = new Image("file:src/main/java/com/mario/LittleBigEngine/resources/easy_l.png");
        E_R = new Image("file:src/main/java/com/mario/LittleBigEngine/resources/easy_r.png");
        M_U = new Image("file:src/main/java/com/mario/LittleBigEngine/resources/medium_u.png");
        M_D = new Image("file:src/main/java/com/mario/LittleBigEngine/resources/medium_d.png");        
        M_L = new Image("file:src/main/java/com/mario/LittleBigEngine/resources/medium_l.png");
        M_R = new Image("file:src/main/java/com/mario/LittleBigEngine/resources/medium_r.png");
        H_U = new Image("file:src/main/java/com/mario/LittleBigEngine/resources/hard_u.png");
        H_D = new Image("file:src/main/java/com/mario/LittleBigEngine/resources/hard_d.png");        
        H_L = new Image("file:src/main/java/com/mario/LittleBigEngine/resources/hard_l.png");
        H_R = new Image("file:src/main/java/com/mario/LittleBigEngine/resources/hard_r.png");          
    }
    
}
