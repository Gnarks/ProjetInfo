package code.projetinfo;

import code.projetinfo.normalBlocks.Amogous;
import code.projetinfo.normalBlocks.Bob;
import org.junit.Test;

import static org.junit.Assert.*;

public class LevelTest {
    Position testPos = new Position(25, 25);
    ImageBlock[] testBlockList = {new Amogous(testPos), new Bob(testPos)};
    ImageBlock[] testPlaced;
    Level testLevel = new Level("test", new Cases(3,3, CaseState.EMPTY), testBlockList);
    @Test
    public void saveState() {
    }

    @Test
    public void loadState() {
    }

    @Test
    public void isPlacable() {
        Amogous Bart = new Amogous(testPos);
        //assertTrue(testLevel.isPlacable(new Amogous(testPos), 0, 0));
    }

    @Test
    public void place() {
    }
}