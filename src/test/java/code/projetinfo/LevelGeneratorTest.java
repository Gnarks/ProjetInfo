package code.projetinfo;

import code.projetinfo.normalBlocks.*;
import de.saxsys.javafx.test.JfxRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

@RunWith(JfxRunner.class)
public class LevelGeneratorTest {

    @Test
    public void TryCreate() {

        LevelGenerator levelGenerator = new LevelGenerator(new Class[]{Amogous.class},
                7,7,true);

        try {
            levelGenerator.generate();
        }
        catch (LevelGenerator.GenerateException e ){
            System.out.println(e.getMessage());
        }
    }
}