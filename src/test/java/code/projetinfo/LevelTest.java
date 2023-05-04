package code.projetinfo;

import code.projetinfo.normalBlocks.Amogous;
import code.projetinfo.normalBlocks.Bob;
import code.projetinfo.normalBlocks.Redky;
import org.junit.Test;
import org.junit.runner.RunWith;
import de.saxsys.javafx.test.JfxRunner;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

@RunWith(JfxRunner.class)
public class LevelTest {
    private Level testlevel = new Level("test", new Cases(3,3, CaseState.EMPTY), new ImageBlock[] {new Amogous(new Position(0,0)), new Redky(new Position(0,0)), new Bob(new Position(0,0))});

    @Test
    public void compareLevels(){
        Level level1 = new Level("test", new Cases(3,3, CaseState.EMPTY), new ImageBlock[] {new Amogous(new Position(0,0)), new Redky(new Position(0,0)), new Bob(new Position(0,0))});
        assertEquals("Error on compareLevels() test 1", testlevel.compareLevels(level1), true);

        Level level2 = new Level("test", new Cases(3,3, CaseState.EMPTY), new ImageBlock[] {new Amogous(new Position(30,0)), new Redky(new Position(0,0)), new Bob(new Position(0,0))});
        assertEquals("Error on compareLevels() test 2", testlevel.compareLevels(level2), false);

        Level level3 = new Level("nottherightname", new Cases(3,3, CaseState.EMPTY), new ImageBlock[] {new Amogous(new Position(0,0)), new Redky(new Position(0,0)), new Bob(new Position(0,0))});
        assertEquals("Error on compareLevels() test 3", testlevel.compareLevels(level3), false);
    }

    @Test
    public void compareGrid(){
        Cases secGrid = new Cases(3,3, CaseState.EMPTY);
        assertEquals("Error in compareGrid() test 1", testlevel.compareGrid(secGrid.getCases()), true);

        secGrid.set(1,1,CaseState.FULL);
        assertEquals("Error in compareGrid() test 2",testlevel.compareGrid(secGrid.getCases()), false);
    }

    @Test
    public void saveAndLoad() throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        testlevel.saveState();
        Level importtest = new Level("test");
        assertEquals(testlevel.compareLevels(importtest), true);
    }

    @Test
    public void isPlacable() {
        assertEquals("Error in isPlacable() in (0,0)",testlevel.isPlacable(testlevel.getBlocks()[0], 0, 0), true);
        assertEquals("Error in isPlacable() in (1,0)",testlevel.isPlacable(testlevel.getBlocks()[0], 1, 0), true);
        assertEquals("Error in isPlacable() in (2,0)",testlevel.isPlacable(testlevel.getBlocks()[0], 2, 0), false);

        assertEquals("Error in isPlacable() in (0,1)",testlevel.isPlacable(testlevel.getBlocks()[0], 0, 1), true);
        assertEquals("Error in isPlacable() in (1,1)",testlevel.isPlacable(testlevel.getBlocks()[0], 1, 1), true);
        assertEquals("Error in isPlacable() in (2,1)",testlevel.isPlacable(testlevel.getBlocks()[0], 2, 1), false);

        assertEquals("Error in isPlacable() in (0,2)",testlevel.isPlacable(testlevel.getBlocks()[0], 0, 2), false);
        assertEquals("Error in isPlacable() in (1,2)",testlevel.isPlacable(testlevel.getBlocks()[0], 1, 2), false);
        assertEquals("Error in isPlacable() in (2,2)",testlevel.isPlacable(testlevel.getBlocks()[0], 2, 2), false);
    }

    @Test
    public void placeandremove() {
        CaseState[][] expected = {
                                  {CaseState.FULL, CaseState.EMPTY, CaseState.EMPTY},
                                  {CaseState.FULL, CaseState.FULL, CaseState.EMPTY},
                                  {CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY}
                                 };

        testlevel.place(testlevel.getBlocks()[0],0,0);
        assertEquals("Error in placeandremove() test 1",testlevel.compareGrid(expected), true);

        expected = new CaseState[][]{
                {CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY},
                {CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY},
                {CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY}
        };

        testlevel.remove(testlevel.getBlocks()[0], 0, 0);
        assertEquals("Error in placeandremove() test 2",testlevel.compareGrid(expected), true);

        expected = new CaseState[][]{
                {CaseState.EMPTY, CaseState.FULL, CaseState.FULL},
                {CaseState.EMPTY, CaseState.EMPTY, CaseState.FULL},
                {CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY}
        };

        testlevel.getBlocks()[0].rotateCasesTo(2);
        testlevel.place(testlevel.getBlocks()[0], 1, 0);
        assertEquals("Error in placeandremove() test 3",testlevel.compareGrid(expected), true);

        expected = new CaseState[][]{
                {CaseState.EMPTY, CaseState.FULL, CaseState.FULL},
                {CaseState.EMPTY, CaseState.FULL, CaseState.FULL},
                {CaseState.EMPTY, CaseState.FULL, CaseState.FULL}
        };
        testlevel.getBlocks()[0].rotateCasesTo(2);
        testlevel.place(testlevel.getBlocks()[0], 1, 1);
        assertEquals("Error in placeandremove() test 4",testlevel.compareGrid(expected), true);

        expected = new CaseState[][]{
                {CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY},
                {CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY},
                {CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY}
        };
        testlevel.remove(testlevel.getBlocks()[0], 1, 1);
        testlevel.getBlocks()[0].rotateCasesTo(2);
        testlevel.remove(testlevel.getBlocks()[0], 1, 0);
        assertEquals("Error in placeandremove() test 5",testlevel.compareGrid(expected), true);
    }
}