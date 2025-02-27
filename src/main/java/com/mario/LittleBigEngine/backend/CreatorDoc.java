/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mario.LittleBigEngine.backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * class that reads the level file
 * @author mario
 */
public class CreatorDoc {
    
    private final String filename_demo = "src/main/java/com/mario/LittleBigEngine/levels/demo_level.txt";
    private final String filename_custom = "src/main/java/com/mario/LittleBigEngine/levels/custom_level.txt";
    private int equippedSticks;
    private int gameFieldsX;
    private int gameFieldsY;
    private int[] startingPosition;
    private List<Integer> stones;
    private List<Integer> sticks;
    private List<Integer> enemies;
    private List<Integer> chests;
    private final boolean custom;
    private static Logger logger;
    private boolean loggerReceived;
    
    /**
     *
     * @param custom true if user chose CUSTOM level, false if DEMO level
     * @param givenLogger this function inherits the engine logger
     */
    public CreatorDoc(boolean custom, Logger givenLogger){
        this.logger = givenLogger;
        loggerReceived = true;
        this.custom = custom;
        if(custom) loadCreatorDoc(filename_custom);
        else loadCreatorDoc(filename_demo);
    }
    
    /**
     * this is a special constructor for testing purposes
     * @param custom true if user chose CUSTOM level, false if DEMO level
     */
    public CreatorDoc(boolean custom){
        loggerReceived = false;
        this.custom = custom;
        if(custom) loadCreatorDoc(filename_custom);
        else loadCreatorDoc(filename_demo);

    }
    //GETTERS

    /**
     * getter for the level filename
     * @return level filename
     */
    public String getFilename() {
        if(custom) return filename_custom;
        else return filename_demo;

    }

    /**
     * getter for the number of equipped sticks
     * @return number of equipped sticks
     */
    public int getEquippedSticks() {
        return equippedSticks;
    }

    /**
     * getter for number of X fields
     * @return number of X fields
     */
    public int getGameFieldsX() {
        return gameFieldsX;
    }

    /**
     * getter for number of Y fields
     * @return number of Y fields
     */
    public int getGameFieldsY() {
        return gameFieldsY;
    }
    
    /**
     * getter for starting position
     * @return starting position
     */
    public int[] getStartingPosition() {
        return startingPosition;
    }

    /**
     * getter for list of stones
     * @return list of stones
     */
    public List<Integer> getStones() {
        return stones;
    }

    /**
     * getter for list of sticks
     * @return list of sticks
     */
    public List<Integer> getSticks() {
        return sticks;
    }

    /**
     * getter for list of enemies
     * @return list of enemies
     */
    public List<Integer> getEnemies() {
        return enemies;
    }

    /**
     * getter for list of chests
     * @return list of chests
     */
    public List<Integer> getChests() {
        return chests;
    }
    
    //THE ACTUAL CODE
    
    
    private void loadCreatorDoc(String filename){
        
        String line = null;
        try {
            
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
           
            // LOAD BASICS
            
            for(int i = 0; i<5; i++){
                line = bufferedReader.readLine();}
            line = line.substring(22, line.length());
            gameFieldsX = Integer.parseInt(line);
            line = bufferedReader.readLine();
            line = line.substring(22, line.length());
            gameFieldsY = Integer.parseInt(line);
            
            bufferedReader.readLine();
            line = bufferedReader.readLine();
            startingPosition = new int[2];
            line = line.substring(23, line.length());
            startingPosition[0] = Integer.parseInt(line);
            line = bufferedReader.readLine();
            line = line.substring(23, line.length());
            startingPosition[1] = Integer.parseInt(line);
            
            // LOAD STONES
            
            boolean stonesEmpty = false;
            for(int i = 0; i<4; i++){
                line = bufferedReader.readLine();}
            if(line.length()<17) stonesEmpty = true;
            if(!stonesEmpty) line = line.substring(13, line.length());
            else line = line.substring(13, line.length()-1);
            int i = 0;
            List<Character> tempList;
            stones = new ArrayList<Integer>();
            if(!stonesEmpty){
            do{
                i++;
                tempList = new ArrayList<Character>();
                do{
                    tempList.add(line.charAt(i));
                    i++;
                }while(line.charAt(i)!='/' && line.charAt(i)!=',' && line.charAt(i)!=']');
                if(line.charAt(i)==',') i++;
                String temp = new String();
                for(char c:tempList){
                    temp= temp + c;}
                int temp_num = Integer.parseInt(temp);
                stones.add(temp_num);
            }while(line.charAt(i)!=']');}

            // LOAD STICKS
            
            boolean emptySticks = false;
            for(i = 0; i<4; i++){
                line = bufferedReader.readLine();}
            line = line.substring(12, line.length());
            equippedSticks = Integer.parseInt(line);
            line = bufferedReader.readLine();
            if(line.length()<16) emptySticks = true;
            if(emptySticks) line = line.substring(13, line.length()-1);
            else line = line.substring(13, line.length());
            sticks = new ArrayList<Integer>();
            i = 0;
            if(!emptySticks){
                do{
                    i++;
                    tempList = new ArrayList<Character>();
                    do{
                        tempList.add(line.charAt(i));
                        i++;
                    }while(line.charAt(i)!='/' && line.charAt(i)!=',' && line.charAt(i)!=']');
                    if(line.charAt(i)==',') i++;
                    String temp = new String();
                    for(char c:tempList){
                        temp= temp + c;}
                    int temp_num = Integer.parseInt(temp);
                    sticks.add(temp_num);
                }while(line.charAt(i)!=']');
            }

            // LOAD CHESTS
            
            for(i = 0; i<4; i++){
                line = bufferedReader.readLine();}
            line = line.substring(3, line.length());
            
            i = 0;
            tempList = new ArrayList<Character>();
            chests = new ArrayList<Integer>();
            i -= 2;
            do{
                tempList = new ArrayList<Character>();
                i+=2;
                do{
                    tempList.add(line.charAt(i));
                    i++;
                }while(line.charAt(i) != '/');
                String temp = new String();
                for(char c:tempList){
                    temp= temp + c;}
                int temp_num = Integer.parseInt(temp);
                chests.add(temp_num);
                for(int y=0; y<=1; y++){
                    i++;
                    tempList = new ArrayList<Character>();
                    do{
                        tempList.add(line.charAt(i));
                        i++;
                    }while(line.charAt(i) != '-' && line.charAt(i) != ',' && line.charAt(i) != ']');
                    temp = new String();
                    for(char c:tempList){
                        temp= temp + c;}
                    temp_num = Integer.parseInt(temp);
                    chests.add(temp_num);
                }
            }while(line.charAt(i) != ']');
            for(i = 0; i<4; i++){
                line = bufferedReader.readLine();}
            line = line.substring(3, line.length());
            
            //LOAD ENEMIES
            i=0;
            tempList = new ArrayList<Character>();
            enemies = new ArrayList<Integer>();
            i -= 2;
            do{
                tempList = new ArrayList<Character>();
                i+=2;
                do{
                    tempList.add(line.charAt(i));
                    i++;
                }while(line.charAt(i) != '/');
                String temp = new String();
                for(char c:tempList){
                    temp= temp + c;}
                int temp_num = Integer.parseInt(temp);
                enemies.add(temp_num);
                for(int y=0; y<=1; y++){
                    i++;
                    tempList = new ArrayList<Character>();
                    do{
                        tempList.add(line.charAt(i));
                        i++;
                    }while(line.charAt(i) != '-' && line.charAt(i) != ',' && line.charAt(i) != ']');
                    temp = new String();
                    for(char c:tempList){
                        temp= temp + c;}
                    temp_num = Integer.parseInt(temp);
                    enemies.add(temp_num);
                }
            }while(line.charAt(i) != ']');
            bufferedReader.close();
            
            if(loggerReceived) logger.info("Reading level file OK :)");
            
            }

        catch(Exception e) {
            if(loggerReceived) logger.log(Level.SEVERE, "Reading level file ERROR!{0}", e);
            else System.err.println("ERROR " + e);
            System.exit(0);
        }
    }
    
}
