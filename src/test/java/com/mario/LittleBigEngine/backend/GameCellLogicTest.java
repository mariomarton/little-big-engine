package com.mario.LittleBigEngine.backend;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test of Cell logics
 * @author mario
 */
public class GameCellLogicTest {
    
    
    private static Cell emptyCell;
    private static Cell stickCell;
    private static Cell chestCell;
    private static Cell stoneCell;
    
    public GameCellLogicTest() {
    }
    
    
    @BeforeClass
    public static void setUpClass() {
        emptyCell = new Cell(0, 0, false, false, -1, true);
        stickCell = new Cell(5, 5, false, true, -1, true);
        chestCell = new Cell(9, 7, false, false, 250, true);
        stoneCell = new Cell(9, 7, true, false, -1, true);
    }

    /**
     * Test of isPassable method, of class Cell.
     */
    @Test
    public void testIsPassable() {
        System.out.println("Testing COLLISION DETECTION");
        boolean passed = true;
        if(!emptyCell.isPassable()) passed = false;
        if(!stickCell.isPassable()) passed = false;
        if(chestCell.isPassable()) passed = false;
        if(stoneCell.isPassable()) passed = false;
        assertTrue("Cell class' isPassable method is not functioning well!", passed);
    }

    /**
     * Test of openChest method, of class Cell.
     */
    @Test
    public void testOpenChest() {
        System.out.println("Testing CHEST LOGIC");
        boolean passed = true;
        
        if(chestCell.getChestOpen()) passed = false;
        
        chestCell.openChest();
        stickCell.openChest();
        stoneCell.openChest();
        emptyCell.openChest();
        
        if(stickCell.getChestOpen() || emptyCell.getChestOpen()
           || stoneCell.getChestOpen()) passed = false;
        
        if(!chestCell.getChestOpen()) passed = false;
        
        assertTrue("Chest logics error! Check the Cell class.", passed);
    }
    
}
