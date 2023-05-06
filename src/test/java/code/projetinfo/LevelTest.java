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
        assertTrue("Error on compareLevels() test 1", testlevel.equals(level1));

        Level level2 = new Level("test", new Cases(3,3, CaseState.EMPTY), new ImageBlock[] {new Amogous(new Position(30,0)), new Redky(new Position(0,0)), new Bob(new Position(0,0))});
        assertFalse("Error on compareLevels() test 2", testlevel.equals(level2));

        Level level3 = new Level("nottherightname", new Cases(3,3, CaseState.EMPTY), new ImageBlock[] {new Amogous(new Position(0,0)), new Redky(new Position(0,0)), new Bob(new Position(0,0))});
        assertFalse("Error on compareLevels() test 3", testlevel.equals(level3));
    }

    @Test
    public void compareGrid(){
        Cases secGrid = new Cases(3,3, CaseState.EMPTY);
        assertTrue("Error in compareGrid() test 1", testlevel.getGrid().equals(secGrid.getCases()));

        secGrid.set(1,1,CaseState.FULL);
        assertFalse("Error in compareGrid() test 2", testlevel.getGrid().equals(secGrid.getCases()));
    }

    @Test
    public void saveAndLoad() throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        testlevel.saveState();
        Level importtest = new Level("test");
        assertTrue(testlevel.equals(importtest));
    }

    @Test
    public void isPlacable() {
        assertTrue("Error in isPlacable() in (0,0)", testlevel.isPlacable(testlevel.getBlocks()[0], 0, 0));
        assertTrue("Error in isPlacable() in (1,0)", testlevel.isPlacable(testlevel.getBlocks()[0], 1, 0));
        assertFalse("Error in isPlacable() in (2,0)", testlevel.isPlacable(testlevel.getBlocks()[0], 2, 0));

        assertTrue("Error in isPlacable() in (0,1)", testlevel.isPlacable(testlevel.getBlocks()[0], 0, 1));
        assertTrue("Error in isPlacable() in (1,1)", testlevel.isPlacable(testlevel.getBlocks()[0], 1, 1));
        assertFalse("Error in isPlacable() in (2,1)", testlevel.isPlacable(testlevel.getBlocks()[0], 2, 1));

        assertFalse("Error in isPlacable() in (0,2)", testlevel.isPlacable(testlevel.getBlocks()[0], 0, 2));
        assertFalse("Error in isPlacable() in (1,2)", testlevel.isPlacable(testlevel.getBlocks()[0], 1, 2));
        assertFalse("Error in isPlacable() in (2,2)", testlevel.isPlacable(testlevel.getBlocks()[0], 2, 2));
    }

    @Test
    public void placeandremove() {
        CaseState[][] expected = {
                                  {CaseState.FULL, CaseState.EMPTY, CaseState.EMPTY},
                                  {CaseState.FULL, CaseState.FULL, CaseState.EMPTY},
                                  {CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY}
                                 };

        testlevel.place(testlevel.getBlocks()[0],0,0);
        assertTrue("Error in placeandremove() test 1", testlevel.getGrid().equals(expected));

        expected = new CaseState[][]{
                {CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY},
                {CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY},
                {CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY}
        };

        testlevel.remove(testlevel.getBlocks()[0], 0, 0);
        assertTrue("Error in placeandremove() test 2", testlevel.getGrid().equals(expected));

        expected = new CaseState[][]{
                {CaseState.EMPTY, CaseState.FULL, CaseState.FULL},
                {CaseState.EMPTY, CaseState.EMPTY, CaseState.FULL},
                {CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY}
        };

        testlevel.getBlocks()[0].rotateCasesTo(2);
        testlevel.place(testlevel.getBlocks()[0], 1, 0);
        assertTrue("Error in placeandremove() test 3", testlevel.getGrid().equals(expected));

        expected = new CaseState[][]{
                {CaseState.EMPTY, CaseState.FULL, CaseState.FULL},
                {CaseState.EMPTY, CaseState.FULL, CaseState.FULL},
                {CaseState.EMPTY, CaseState.FULL, CaseState.FULL}
        };
        testlevel.getBlocks()[0].rotateCasesTo(2);
        testlevel.place(testlevel.getBlocks()[0], 1, 1);
        assertTrue("Error in placeandremove() test 4", testlevel.getGrid().equals(expected));

        expected = new CaseState[][]{
                {CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY},
                {CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY},
                {CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY}
        };
        testlevel.remove(testlevel.getBlocks()[0], 1, 1);
        testlevel.getBlocks()[0].rotateCasesTo(2);
        testlevel.remove(testlevel.getBlocks()[0], 1, 0);
        assertTrue("Error in placeandremove() test 5", testlevel.getGrid().equals(expected));
    }
}