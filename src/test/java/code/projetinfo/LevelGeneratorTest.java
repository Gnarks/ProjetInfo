package code.projetinfo;

import code.projetinfo.normalBlocks.Amogous;
import de.saxsys.javafx.test.JfxRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

@RunWith(JfxRunner.class)
public class LevelGeneratorTest {

    @Test
    public void TryCreate() {

        LevelGenerator levelGenerator = new LevelGenerator(new ImageBlock[]{new Amogous(new Position(2,2))},
                1,1,true);

        try {
            levelGenerator.generate();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
}