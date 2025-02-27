/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mario.LittleBigEngine.backend;

import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mario
 */
public class LevelMeaningfulnessTest {
    
    private static CreatorDoc doc;
    
    public LevelMeaningfulnessTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        doc = new CreatorDoc(true);
    }

    /**
     * Test of getGameFieldsX method, of class CreatorDoc.
     */
    @Test
    public void testGameFieldsX() {
        System.out.println("Testing amount of FIELDS (X)");
        String message = "X dimension in cells has be more than 1 and less than 51 :/";
        int res = doc.getGameFieldsX();
        boolean passed = true;        
        if(res<2 || res>50) passed = false;         
        assertTrue(message, passed);
    }

    /**
     * Test of getGameFieldsY method, of class CreatorDoc.
     */
    @Test
    public void testGameFieldsY() {
        System.out.println("Testing amount of FIELDS (Y)");
        String message = "Y dimension in cells has be more than 1 and less than 51 :/";
        int res = doc.getGameFieldsY();
        boolean passed = true;        
        if(res<2 || res>50) passed = false;         
        assertTrue(message, passed);
    }

    /**
     * Test of getStartingPosition method, of class CreatorDoc.
     */
    @Test
    public void testStartingPosition() {
        System.out.println("Testing if STARTING POSITION is OK");
        String message = "Starting position is out of bounds or there's a stone, enemy or chest there.";
        boolean passed = true;  
        int[] startingPosition = doc.getStartingPosition();       
        List<Integer> stones = doc.getStones();
        List<Integer> enemies = doc.getEnemies();
        List<Integer> chests = doc.getChests();
        
        for(int i = 0; i<stones.size(); i = i+2){
            if(stones.get(i)==startingPosition[0] && stones.get(i+1)==startingPosition[1]) passed = false;}
        
        for(int i = 0; i<chests.size(); i = i+3){
            if(chests.get(i+1)==startingPosition[0] && chests.get(i+2)==startingPosition[1]) passed = false;}
        
        for(int i = 0; i<enemies.size(); i = i+3){
            if(enemies.get(i+1)==startingPosition[0] && enemies.get(i+2)==startingPosition[1]) passed = false;}
        
        if(startingPosition[0]<0 || startingPosition[1]<0) passed = false;
        if(startingPosition[0]>doc.getGameFieldsX()-1 || startingPosition[1]>doc.getGameFieldsY()-1) passed = false;
          
        assertTrue(message, passed);
    }

    /**
     * Test of getStones method, of class CreatorDoc.
     */
    
    @Test
    public void testStones() {
        System.out.println("Testing if STONES are OK");
        String message = "Stones were NOT entered correctly :/";
        boolean passed = true;      
        List<Integer> stones = doc.getStones();
        List<Integer> enemies = doc.getEnemies();
        List<Integer> chests = doc.getChests();
        
        for(int i = 0; i<stones.size(); i = i+2){
            for(int j = 0; j<chests.size(); j = j+3){
                if(stones.get(i)==chests.get(j+1) && stones.get(i+1)==chests.get(j+2)) passed = false;}
            for(int j = 0; j<enemies.size(); j = j+3){
                if(stones.get(i)==enemies.get(j+1) && stones.get(i+1)==enemies.get(j+2)) passed = false;}
            if(stones.get(i)<0 || stones.get(i+1)<0) passed = false;
            if(stones.get(i)>doc.getGameFieldsX()-1 || stones.get(i+1)>doc.getGameFieldsY()-1) passed = false;
            if(!passed) break;
        }            
        assertTrue(message, passed);
    }
    

    /**
     * Test of getSticks method, of class CreatorDoc.
     */
    @Test
    public void testSticks() {
        System.out.println("Testing if STICKS are OK");
        String message = "Sticks were NOT entered correctly :/";
        boolean passed = true;      
        List<Integer> stones = doc.getStones();
        List<Integer> chests = doc.getChests();
        List<Integer> sticks = doc.getSticks();
        
        for(int i = 0; i<sticks.size(); i = i+2){
            for(int j = 0; j<chests.size(); j = j+3){
                if(sticks.get(i)==chests.get(j+1) && sticks.get(i+1)==chests.get(j+2)) passed = false;}
            for(int j = 0; j<stones.size(); j = j+2){
                if(sticks.get(i)==stones.get(j) && sticks.get(i+1)==stones.get(j+1)) passed = false;}
            if(sticks.get(i)<0 || sticks.get(i+1)<0) passed = false;
            if(sticks.get(i)>doc.getGameFieldsX()-1 || sticks.get(i+1)>doc.getGameFieldsY()-1) passed = false;
            if(!passed) break;
        }            
        assertTrue(message, passed);
    }
    
    @Test
    public void testGetEquippedSticks() {
        System.out.println("Testing amount of EQUIPPED STICKS");
        String message = "You can't have negative amount of sticks :/";
        int res = doc.getEquippedSticks();
        boolean passed = true;        
        if(res<0) passed = false;         
        assertTrue(message, passed);
    }

    /**
     * Test of getEnemies method, of class CreatorDoc.
     */
    @Test
    public void testGetEnemies() {
        System.out.println("Testing if ENEMIES are OK");
        String message = "Enemies were NOT entered correctly :/";
        boolean passed = true;
        List<Integer> enemies = doc.getEnemies();
        
        for(int i = 0; i<enemies.size(); i = i+3){
            if(enemies.get(i)<1 || enemies.get(i)>5) passed = false;
            if(enemies.get(i+1)<0 || enemies.get(i+2)<0) passed = false;
            if(enemies.get(i+1)>doc.getGameFieldsX()-1 || enemies.get(i+2)>doc.getGameFieldsY()-1) passed = false;
            if(!passed) break;
        }
        
        if(enemies.size()<3){
            passed = false;
            message = "There has to be at least one enemy :/";
        }
        assertTrue(message, passed);
    }

    /**
     * Test of getChests method, of class CreatorDoc.
     */
    @Test
    public void testGetChests() {
        System.out.println("Testing if CHESTS are OK");
        String message = "Chests were NOT entered correctly :/";
        boolean passed = true;
        List<Integer> chests = doc.getChests();
        
        for(int i = 0; i<chests.size(); i = i+3){
            if(chests.get(i)<1) passed = false;
            if(chests.get(i+1)<0 || chests.get(i+2)<0) passed = false;
            if(chests.get(i+1)>doc.getGameFieldsX()-1 || chests.get(i+2)>doc.getGameFieldsY()-1) passed = false;
            if(!passed) break;
        }
        
        if(chests.size()<3){
            passed = false;
            message = "There has to be at least one chest :/";
        }
        assertTrue(message, passed);
    }
    
}
