package code.projetinfo;

import code.projetinfo.normalBlocks.*;
import de.saxsys.javafx.test.JfxRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(JfxRunner.class)
public class LevelGeneratorTest {

    @Test
    public void TryGenerate() {

        LevelGenerator levelGenerator = new LevelGenerator(new Class[]{Amogous.class},
                1,1,false);
        try {
           levelGenerator.generate();
        }
        catch (LevelGenerator.GenerateException e ){
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void goodSize() throws LevelGenerator.GenerateException {
        LevelGenerator generator = new LevelGenerator(new Class[]{Amogous.class}, 2,2,false);
        Level test = generator.generate();
        assertTrue((test.getGrid().getCol() == 2 && test.getGrid().getRow() == 3) || (test.getGrid().getCol() == 3 && test.getGrid().getRow() == 2));
    }

}