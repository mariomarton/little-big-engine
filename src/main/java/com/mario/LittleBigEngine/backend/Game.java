package com.mario.LittleBigEngine.backend;

import com.mario.LittleBigEngine.frontend.*;
import static com.mario.LittleBigEngine.frontend.OPTION.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
/**
 * a key class that includes the essential game behaviour methods
 * @author mario
 */
public class Game {
    /*
    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;
    */
   
    private static final int SAFE_ZONE = 5; // CELL_SIZE - character pic size

    /**
     * size of one cell in pixels
     */
    public static final int CELL_SIZE = 30;
    private int W;
    private int H;

    /**
     * Stage instance of the menu stage
     */
    public Stage menuStage;
    private ImageView character;
    
    private boolean custom;
    private boolean losingHealth;
    private boolean remainingPowerShown;
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean upPressed;
    private boolean downPressed;

    /**
     * boolean - true is SpaceBar key is pressed
     */
    public boolean spacePressed;
    private char direction;

    private Cell[][] grid;
    private RenderGame render;

    /**
     * the HUD with health
     */
    public SmallInfoLabel healthHUD,

    /**
     * the HUD with number of sticks
     */
    sticksHUD,

    /**
     * the HUD with the amount of money
     */
    moneyHUD;

    /**
     * the HUD that shows up when in a fight
     */
    public RemainingPowerLabel remainingPower;
    private int[] characterLocation;
    private int ownedSticks;

    /**
     * character's current health
     */
    public int health;
    private int money;
    
    private final CreatorDoc doc;
    private final String startingCharacter = "file:src/main/java/com/mario/LittleBigEngine/resources/optionG_u.png";
    
    private Enemy[] enemies;
    Random randomPositionGenerator;
    private static Logger logger;
    
    /**
     *
     * @param chosen chosen level - CUSTOM or DEMO
     * @param givenLogger the engine logger
     */
    public Game(OPTION chosen, Logger givenLogger){
        if(chosen==CUSTOM) custom = true;
        else if(chosen==DEMO) custom = false;
        logger = givenLogger;
        doc = new CreatorDoc(custom,logger);
        grid = new Cell[this.doc.getGameFieldsX()][this.doc.getGameFieldsY()];
        W = CELL_SIZE*this.doc.getGameFieldsX();
        H = CELL_SIZE*this.doc.getGameFieldsY();
        characterLocation = new int[2];
        enemies = new Enemy[this.doc.getEnemies().size()/3];
        ownedSticks = doc.getEquippedSticks();
        direction = 'u';
        health = 100;
        money = 0;
        losingHealth = false;
        remainingPowerShown = false;
        render = new RenderGame(this);
        render.setLogger(logger);
        render.inicializeStage();
        createKeyListeners();
        randomPositionGenerator = new Random();
        createHUD();
    }
    
    //GETTERS, SETTERS

    /**
     * getter for Width in pixels
     * @return Width in pixels
     */
    
    public int getWidth(){
        return W;}
    
    /**
     * getter for Height in pixels
     * @return Height in pixels
     */
    public int getHeight(){
        return H;}
    
    /**
     * getter for menu stage
     * @return menu stage
     */
    public Stage getMenuStage(){
        return menuStage;}
    
    /**
     * getter for loosing health variable
     * @return true if character is being hurt
     */
    public boolean getLosingHealth(){
        return losingHealth;}
    
    /**
     * getter for remaining power shown variable
     * @return true if enemy health HUD is shown
     */
    public boolean getRemainingPowerShown(){
        return remainingPowerShown;}
   
    /**
     * getter for space pressed variable
     * @return true if spacebar is pressed
     */
    public boolean getSpacePressed(){
        return spacePressed;}
    
    /**
     * getter for remainingPower label
     * @return the label
     */
    public RemainingPowerLabel getRemainingPower(){
        return remainingPower;}
    
    /**
     * getter for remaining health
     * @return remaining health
     */
    public int getHealth(){
        return health;}   
    
    /**
     * getter for the engine logger
     * @return engine logger
     */
    public Logger getLogger(){
        return logger;
    }
    
    // THE ACTUAL CODE

    /**
     * creates the grid of cells
     */
    
    public void createGrid(){
        
        Boolean hasStone = false;
        Boolean hasStick = false;
        int chestValue = -1;

        for(int row=0; row<this.doc.getGameFieldsY(); row++){
            for(int col=0; col<this.doc.getGameFieldsX(); col++){
                
                hasStone = cellHasStone(col, row);
                hasStick = cellHasStick(col, row);
                chestValue = cellChestValue(col, row);
                Cell cell = new Cell(col, row, hasStone, hasStick, chestValue); 
                cell.setLayoutX(col*CELL_SIZE);
                cell.setLayoutY(row*CELL_SIZE);
                grid[col][row] = cell;
                render.getGamePane().getChildren().add(cell);    
                
            }
        }         
    }
    
    private Boolean cellHasStone(int x, int y){
        Boolean hasStone = false;
        int item_x;
        int item_y;
        for(int i = 0; i < this.doc.getStones().size(); i=i+2){
            item_x = this.doc.getStones().get(i);
            item_y = this.doc.getStones().get(i+1);
            if(x==item_x && y==item_y) hasStone = true;
        }
        return hasStone;
    }
    
    private Boolean cellHasStick(int x, int y){
        Boolean hasStick = false;
        int item_x;
        int item_y;
        for(int i = 0; i < this.doc.getSticks().size(); i=i+2){
            item_x = this.doc.getSticks().get(i);
            item_y = this.doc.getSticks().get(i+1);
            if(x==item_x && y==item_y) hasStick = true;
        }
        return hasStick;
    }
    
    private int cellChestValue(int x, int y){
        int chestValue = -1;
        int item_x;
        int item_y;
        int item_value;
        for(int i = 0; i < this.doc.getChests().size(); i=i+3){
            item_value = this.doc.getChests().get(i);
            item_x = this.doc.getChests().get(i+1);
            item_y = this.doc.getChests().get(i+2);
            if(x==item_x && y==item_y) chestValue = item_value;
        }
        return chestValue;
    }
    
    private void checkForStick(){
        if(grid[characterLocation[0]][characterLocation[1]].removeStick()){
            ownedSticks++;
        }
    }
    
    private void createKeyListeners(){
        
        render.getGameScene().setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
               if(event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A){
                   leftPressed = true;
               }
               else if(event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D){
                   rightPressed = true;
               }
               else if(event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W){
                   upPressed = true;
               }
               else if(event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S){
                   downPressed = true;
               }
               
               if(event.getCode() == KeyCode.SPACE){
                   spacePressed = false;}
                
            };
        });
        
        render.getGameScene().setOnKeyReleased(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
               if(event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A){
                   leftPressed = false;
               }
               else if(event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D){
                   rightPressed = false;
               }
               else if(event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W){
                   upPressed = false;
               }
               else if(event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S){
                   downPressed = false;
               }
               
               if(event.getCode() == KeyCode.SPACE){
                   spacePressed = true;}
            };
        });
    }

    /**
     *
     * @param menuStage the Stage instance of menu stage
     */
    public void createNewGame(Stage menuStage){
        this.menuStage = menuStage;
        this.menuStage.hide();
        createCharacter();
        createEnemies();
        render.createGameLoop();
    }
    
    private void createCharacter(){
        character = new ImageView(startingCharacter);
        character.setLayoutX(this.doc.getStartingPosition()[0]*CELL_SIZE+SAFE_ZONE/2);
        character.setLayoutY(this.doc.getStartingPosition()[1]*CELL_SIZE+SAFE_ZONE/2);
        render.getGamePane().getChildren().add(character);
        updateCharacterLocation();
    }
    
    private void createEnemies(){
        if(this.doc.getEnemies().size()>=3){
            for(int i = 0; i < this.doc.getEnemies().size(); i=i+3){
                Enemy enemy = new Enemy(this.doc.getEnemies().get(i),
                                        this.doc.getEnemies().get(i+1),
                                        this.doc.getEnemies().get(i+2));
                enemy.getVisual().setLayoutX(this.doc.getEnemies().get(i+1)*CELL_SIZE+SAFE_ZONE/2);
                enemy.getVisual().setLayoutY(this.doc.getEnemies().get(i+2)*CELL_SIZE+SAFE_ZONE/2);
                render.getGamePane().getChildren().add(enemy.getVisual());
                enemies[i/3] = enemy;
            }
        }
    }
    
    private void updateCharacterLocation(){
        characterLocation[0] = (int) (character.getLayoutX()-SAFE_ZONE/2)/CELL_SIZE;
        characterLocation[1] = (int) (character.getLayoutY()-SAFE_ZONE/2)/CELL_SIZE;
    }
    
    private char isCharacterInRange(int x, int y){
        char ret = '0';
        
        if(y>0 && characterLocation[0] == x && characterLocation[1] == y-1){
            ret = 'u';}
        else if(y<this.doc.getGameFieldsY()-1 && characterLocation[0] == x && characterLocation[1] == y+1){
            ret = 'd';}
        else if(x>0 && characterLocation[1] == y && characterLocation[0] == x-1){
            ret = 'l';}
        else if(x<this.doc.getGameFieldsY()-1 && characterLocation[1] == y && characterLocation[0] == x+1){
            ret = 'r';}        
        return ret;
    }
    
    
    private boolean fightCharacter(Enemy enemy){
        char position = isCharacterInRange(enemy.getX(), enemy.getY());
        if(position=='0' && enemy.getPower()>0){
            if(enemy.getFightingMode()){
                losingHealth = false;
                if(health>0) logger.info("Yay! Player surived the attack.");
            }          
            enemy.setFightingMode(false);

            return false;
        }
        else if(position != 0 && enemy.getPower()>0){
            enemy.rotateEnemy(position);
            health -= 10;
            enemy.setFightingMode(true);
            losingHealth = true;           
            return true;}
        else return false;
    }
    
    /**
     * sets if the character is being hurt
     */
    public void setLosingHealth(){
        boolean temp = false;
        for(Enemy enemy: enemies){
            if(enemy.getFightingMode() && enemy.getPower()>0){temp = true;
                                                    break;}
        }
        losingHealth = temp;
    }
    
    
    private void showEnemyDamage(Enemy enemy){
        if(remainingPowerShown) render.getGamePane().getChildren().remove(remainingPower);
            remainingPower = new RemainingPowerLabel(""+enemy.getPower());
            remainingPower.setLayoutX(enemy.getVisual().getLayoutX());
            remainingPower.setLayoutY(enemy.getVisual().getLayoutY());
            render.getGamePane().getChildren().add(remainingPower);
            remainingPowerShown = true;          
    }
    
    /**
     * hides the enemy power HUD
     */
    public void hideEnemyDamage(){
        int j = 0;
        int numOfEnemiesBeingAttackedBy = 0;
        for(int i=0; i<enemies.length; i++){
            if(enemies[i].getFightingMode() && enemies[i].getPower()>0){
                j = i;
                numOfEnemiesBeingAttackedBy++;              
            }    
        }
        if(!characterFacingEnemy(enemies[j]) && remainingPowerShown && numOfEnemiesBeingAttackedBy==1){
                render.getGamePane().getChildren().remove(remainingPower);
                remainingPower = null;
                remainingPowerShown = false;
                }
        else if(numOfEnemiesBeingAttackedBy == 0 ){
            try{render.getGamePane().getChildren().remove(remainingPower);
            remainingPower = null;
            remainingPowerShown = false;}
            catch(Exception e){}
        }
    }
    
    private boolean characterFacingEnemy(Enemy enemy){
        if(enemy.getDirection() == 'l' && direction == 'r') return true;
        else if(enemy.getDirection() == 'r' && direction == 'l') return true;
        else if(enemy.getDirection() == 'u' && direction == 'd') return true;
        else if(enemy.getDirection() == 'd' && direction == 'u') return true;
        else return false;
    }
    
    /**
     * handles fighting when space is pressed
     */
    public void spaceAction(){
        if(spacePressed){
            boolean enemyInRange = false;
            for (Enemy enemy : enemies){
                if(enemy.getFightingMode() && enemy.getPower()>0){
                    //System.out.println("TOOK DAMAGE, E: "+enemy.direction +"; CH: " + direction );
                    enemyInRange = true;
                    if(enemy.getDirection()=='u' && direction=='d'){
                        if(ownedSticks>0){
                            enemy.setPower(enemy.getPower()-1);
                            ownedSticks--;}
                        showEnemyDamage(enemy);
                        spacePressed = false;
                        break;
                    }
                    else if(enemy.getDirection()=='d' && direction=='u') {
                        if(ownedSticks>0){
                            enemy.setPower(enemy.getPower()-1);
                            ownedSticks--;}
                        showEnemyDamage(enemy);
                        spacePressed = false;
                        break;
                    }
                    else if(enemy.getDirection()=='l' && direction=='r') {
                        if(ownedSticks>0){
                            enemy.setPower(enemy.getPower()-1);
                            ownedSticks--;}
                        showEnemyDamage(enemy);
                        spacePressed = false;
                        break;
                    }
                    else if(enemy.getDirection()=='r' && direction=='l') {
                        if(ownedSticks>0){
                            enemy.setPower(enemy.getPower()-1);
                            ownedSticks--;}
                        showEnemyDamage(enemy);
                        spacePressed = false;
                        break;
                    }                   
                    }}
        }      
    }
    
    /**
     * checks if enemies are still alive, kills enemies if their power is zero
     */
    public void checkEnemyLives(){
        for (Enemy enemy : enemies){
            if(enemy.getPower()<=0) {render.getGamePane().getChildren().remove(enemy.getVisual());
                                if(!enemy.getDead() && health>0) logger.info("Enemy killed!");
                                enemy.setFightingMode(false);
                                enemy.setPower(0);
                                enemy.setDead(true);
                                enemy = null;                               
                                ;}
        }
    }
    
    /**
     * handles opening chests
     */
    public void chestAction(){
        boolean chestOpened = false;
        if(direction=='u' && characterLocation[1]>0
           && spacePressed && ownedSticks > 0 &&
           grid[characterLocation[0]][characterLocation[1]-1].getChestValue() > (-1) &&
           grid[characterLocation[0]][characterLocation[1]-1].getChestOpen() == false){
            grid[characterLocation[0]][characterLocation[1]-1].openChest();
            money += grid[characterLocation[0]][characterLocation[1]-1].getChestValue();
            ownedSticks--;
            chestOpened = true;}
        else if(direction=='d' && characterLocation[1]<this.doc.getGameFieldsY()-1
           && spacePressed && ownedSticks > 0 &&
           grid[characterLocation[0]][characterLocation[1]+1].getChestValue() > (-1) &&
           grid[characterLocation[0]][characterLocation[1]+1].getChestOpen() == false){
            grid[characterLocation[0]][characterLocation[1]+1].openChest();
            money += grid[characterLocation[0]][characterLocation[1]+1].getChestValue();
            ownedSticks--;
            chestOpened = true;}
        else if(direction=='l' && characterLocation[0]>0
           && spacePressed && ownedSticks > 0 &&
           grid[characterLocation[0]-1][characterLocation[1]].getChestValue() > (-1) &&
           grid[characterLocation[0]-1][characterLocation[1]].getChestOpen() == false){
            grid[characterLocation[0]-1][characterLocation[1]].openChest();
            money += grid[characterLocation[0]-1][characterLocation[1]].getChestValue();
            ownedSticks--;
            chestOpened = true;}
        else if(direction=='r' && characterLocation[0]<this.doc.getGameFieldsX()-1
            && spacePressed && ownedSticks > 0 &&
           grid[characterLocation[0]+1][characterLocation[1]].getChestValue() > (-1) &&
           grid[characterLocation[0]+1][characterLocation[1]].getChestOpen() == false){
            grid[characterLocation[0]+1][characterLocation[1]].openChest();
            money += grid[characterLocation[0]+1][characterLocation[1]].getChestValue();
            ownedSticks--;
            chestOpened = true;}
        if(chestOpened) logger.info("Chest Opened!");
    }

    private boolean canMove(){
        boolean noObject = false;
        boolean noEnemy = false;
        boolean move = false;
        int x_cell = characterLocation[0];
        int y_cell = characterLocation[1];
        if(direction =='l' && x_cell>0){
            noObject = grid[x_cell-1][y_cell].isPassable();
            noEnemy = isCellClearOfEnemies(grid[x_cell-1][y_cell]);
            if(noObject && noEnemy) move = true;}
        else if(direction =='r' && x_cell<this.doc.getGameFieldsX()-1){
            noObject = grid[x_cell+1][y_cell].isPassable();
            noEnemy = isCellClearOfEnemies(grid[x_cell+1][y_cell]);
            if(noObject && noEnemy) move = true;}
        else if(direction =='u' && y_cell>0){
            noObject = grid[x_cell][y_cell-1].isPassable();
            noEnemy = isCellClearOfEnemies(grid[x_cell][y_cell-1]);
            if(noObject && noEnemy) move = true;}
        else if(direction =='d' && y_cell<this.doc.getGameFieldsY()-1){
            noObject = grid[x_cell][y_cell+1].isPassable();
            noEnemy = isCellClearOfEnemies(grid[x_cell][y_cell+1]);
            if(noObject && noEnemy) move = true;}
         
        return move;
    }
    
    private boolean isCellClearOfEnemies(Cell cell){
        boolean clear = true;
        
        for (Enemy enemy : enemies) {
            if (enemy.getX() == cell.getX() && enemy.getY() == cell.getY()) {
                if(enemy.getPower()>0) clear = false;
            }
        }
        return clear;
    }
       
    /**
     * handles moving the character when arrow or WSAD keys are pressed
     */
    public void moveCharacter(){
        if(leftPressed && !rightPressed && !upPressed && !downPressed){
            if(direction != 'l'){
                rotateCharacter('l');
                direction = 'l';
                return;
            }
            if(character.getLayoutX() > SAFE_ZONE/2 && canMove()){
                character.setLayoutX(character.getLayoutX() - 30);
                leftPressed = false;
                updateCharacterLocation();
                checkForStick();
            }
        }
        else if(!leftPressed && rightPressed && !upPressed && !downPressed){
            if(direction != 'r'){
                rotateCharacter('r');
                direction = 'r';
                return;
                }
                if(character.getLayoutX() < W - SAFE_ZONE - 25 && canMove()){
                    character.setLayoutX(character.getLayoutX() + 30);
                    rightPressed = false;
                    updateCharacterLocation();
                    checkForStick();
                }
            }
        else if(!leftPressed && !rightPressed && upPressed && !downPressed){
            if(direction != 'u'){
                rotateCharacter('u');
                direction = 'u';
                return;
            }
            if(character.getLayoutY() > SAFE_ZONE/2 && canMove()){
                character.setLayoutY(character.getLayoutY() - 30);
                upPressed = false;
                updateCharacterLocation();
                checkForStick();
            }
        }
        else if(!leftPressed && !rightPressed && !upPressed && downPressed){
            if(direction != 'd'){
                rotateCharacter('d');
                direction = 'd';
                return;
            }
            if(character.getLayoutY() < H - SAFE_ZONE - 25 && canMove()){
                character.setLayoutY(character.getLayoutY() + 30);
                downPressed = false;
                updateCharacterLocation();
                checkForStick();
            }
        }
    }
    
    private void rotateCharacter(char newDirection){
        if(newDirection=='l'){
            character.setImage(new Image("file:src/main/java/com/mario/LittleBigEngine/resources/optionG_l.png"));}
        if(newDirection=='r'){
            character.setImage(new Image("file:src/main/java/com/mario/LittleBigEngine/resources/optionG_r.png"));}
        if(newDirection=='u'){
            character.setImage(new Image("file:src/main/java/com/mario/LittleBigEngine/resources/optionG_u.png"));}
        if(newDirection=='d'){
            character.setImage(new Image("file:src/main/java/com/mario/LittleBigEngine/resources/optionG_d.png"));}
    }
    
    /**
     * handles enemies artificial low-intelligence
     */
    public void moveEnemies(){       
        for (Enemy enemy : enemies){
            if(fightCharacter(enemy) == true || enemy.getPower()<=0){
                continue;}
            Random generator = new Random();
            char move = '0';
            char[] options = {'l', 'r', 'u', 'd'};
            int randomIdx;
            boolean canMakeMove = false;
            Cell cell;
            if(enemy.getLastMove()=='0'){
                do{
                    randomIdx = generator.nextInt(options.length);
                    move = options[randomIdx];
                    
                    if(move=='l' && enemy.getX()>0){
                        cell = grid[enemy.getX()-1][enemy.getY()];
                        if(cell.isPassable() && isCellClearOfEnemies(cell)){
                            canMakeMove = true;
                        }}
                    else if(move=='r' && enemy.getX()<this.doc.getGameFieldsX()-1){
                        cell = grid[enemy.getX()+1][enemy.getY()];
                        if(cell.isPassable() && isCellClearOfEnemies(cell)){
                            canMakeMove = true;
                        }}
                    else if(move=='u' && enemy.getY()>0){
                        cell = grid[enemy.getX()][enemy.getY()-1];
                        if(cell.isPassable() && isCellClearOfEnemies(cell)){
                            canMakeMove = true;
                        }}
                    else if(move=='d' && enemy.getY()<this.doc.getGameFieldsY()-1){
                        cell = grid[enemy.getX()][enemy.getY()+1];
                        if(cell.isPassable() && isCellClearOfEnemies(cell)){
                            canMakeMove = true;
                        }}                                        
                }while(canMakeMove != true);}
            
            else{
                do{
                    char m = enemy.getLastMove();
                    char[] bias_options = {'l', 'r', 'u', 'd', m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m};
                    randomIdx = generator.nextInt(bias_options.length);
                    move = bias_options[randomIdx];
                    
                    if(move=='l' && enemy.getX()>0){
                        cell = grid[enemy.getX()-1][enemy.getY()];
                        if(cell.isPassable() && isCellClearOfEnemies(cell)){
                            canMakeMove = true;
                        }}
                    else if(move=='r' && enemy.getX()<this.doc.getGameFieldsX()-1){
                        cell = grid[enemy.getX()+1][enemy.getY()];
                        if(cell.isPassable() && isCellClearOfEnemies(cell)){
                            canMakeMove = true;
                        }}
                    else if(move=='u' && enemy.getY()>0){
                        cell = grid[enemy.getX()][enemy.getY()-1];
                        if(cell.isPassable() && isCellClearOfEnemies(cell)){
                            canMakeMove = true;
                        }}
                    else if(move=='d' && enemy.getY()<this.doc.getGameFieldsY()-1){
                        cell = grid[enemy.getX()][enemy.getY()+1];
                        if(cell.isPassable() && isCellClearOfEnemies(cell)){
                            canMakeMove = true;
                        }}     
                    
                }while(canMakeMove != true);}

            if(move=='l'){
                enemy.rotateEnemy('l');
                enemy.getVisual().setLayoutX(enemy.getVisual().getLayoutX() - 30);
                enemy.setX(enemy.getX()-1);}
            else if(move=='r'){
                enemy.rotateEnemy('r');
                enemy.getVisual().setLayoutX(enemy.getVisual().getLayoutX() + 30);
                enemy.setX(enemy.getX()+1);}
            else if(move=='u'){
                enemy.rotateEnemy('u');
                enemy.getVisual().setLayoutY(enemy.getVisual().getLayoutY() - 30);
                enemy.setY(enemy.getY()-1);}           
            else if(move=='d'){
                enemy.rotateEnemy('d');
                enemy.getVisual().setLayoutY(enemy.getVisual().getLayoutY() + 30);
                enemy.setY(enemy.getY()+1);}
            enemy.setLastMove(move);
            }
        }
    
    private void createHUD(){
        healthHUD = new SmallInfoLabel("" + health);
        sticksHUD = new SmallInfoLabel("" + ownedSticks);
        moneyHUD = new SmallInfoLabel("" + money);
        ImageView black = new ImageView("file:src/main/java/com/mario/LittleBigEngine/resources/black.jpg");
        ImageView heart = new ImageView("file:src/main/java/com/mario/LittleBigEngine/resources/heart.png");
        ImageView stick = new ImageView("file:src/main/java/com/mario/LittleBigEngine/resources/hud_stick.png");
        ImageView money = new ImageView("file:src/main/java/com/mario/LittleBigEngine/resources/coins.png");
        black.setFitWidth(W);
        black.setFitHeight(CELL_SIZE);
        //black.setLayoutX(0);
        black.setLayoutY(H);
        healthHUD.setLayoutX(32);
        healthHUD.setLayoutY(H+5);
        heart.setLayoutX(6);
        heart.setLayoutY(H+4);
        stick.setLayoutX(76);
        stick.setLayoutY(H+4);
        money.setLayoutX(125);
        money.setLayoutY(H+4);
        sticksHUD.setLayoutX(102);
        sticksHUD.setLayoutY(H+5);
        moneyHUD.setLayoutX(152);
        moneyHUD.setLayoutY(H+5);
        render.getGamePane().getChildren().addAll(black,heart,stick,money,healthHUD,sticksHUD,moneyHUD);
    }
    
    /**
     * updates the hud below the game
     */
    public void updateHUD(){
        healthHUD.setText("" + health);
        sticksHUD.setText("" + ownedSticks);
        moneyHUD.setText("" + money);
    }
    
    /**
     * checks whether there's a closed chest left
     * @return true if there is
     */
    public boolean closedChestsLeft(){
        boolean ret = false;
        for(int row=0; row<this.doc.getGameFieldsY(); row++){
            for(int col=0; col<this.doc.getGameFieldsX(); col++){      
                if(!(grid[col][row].getChestOpen()) && grid[col][row].getChestValue()>-1){ret = true;
                                                break;}
        }}
        return ret;
    }
    
    /**
     * checks whether there are alive enemies 
     * @return true if there are
     */
    public boolean livingEnemiesLeft(){
        for(Enemy enemy: enemies){
            if(enemy.getPower()>0) return true;
        }
        return false;
    }
    
    /**
     * writes the end game state to a level-like text file
     * @param won true if player did NOT die
     */
    public void writeEndGameToFile(boolean won){
        try {
            FileHandler newFile = new FileHandler("src/main/java/com/mario/LittleBigEngine/levels/end_game_state.txt");
            logger.info("end_game_state.txt file created");
            if(custom) Files.copy(Paths.get("src/main/java/com/mario/LittleBigEngine/levels/custom_level.txt"),
                                  Paths.get("src/main/java/com/mario/LittleBigEngine/levels/end_game_state.txt"), REPLACE_EXISTING);
            else Files.copy(Paths.get("src/main/java/com/mario/LittleBigEngine/levels/demo_level.txt"),
                                  Paths.get("src/main/java/com/mario/LittleBigEngine/levels/end_game_state.txt"), REPLACE_EXISTING);
            
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
    
}
