package code.projetinfo;

import code.projetinfo.normalBlocks.Napsta;
import de.saxsys.javafx.test.JfxRunner;
import org.junit.Test;

import static org.junit.Assert.*;
import code.projetinfo.normalBlocks.Amogous;
import org.junit.runner.RunWith;

@RunWith(JfxRunner.class)
public class ImageBlockTest {
    @Test
    public void rotateCasesTo() {
        //Position is whatever because it's the graphic position we only test backend here
        Amogous impotesteur = new Amogous(new Position(0,0));

        CaseState[][] expected = {{CaseState.FULL, CaseState.FULL}, {CaseState.FULL, CaseState.EMPTY}};
        impotesteur.rotateCasesTo(1);
        assertTrue("Error in Amogous rotation test", impotesteur.getGrid().equals(expected));

        expected = new CaseState[][]{{CaseState.FULL, CaseState.FULL}, {CaseState.EMPTY, CaseState.FULL}};
        impotesteur.rotateCasesTo(1);
        assertTrue("Error in Amogous rotation test", impotesteur.getGrid().equals(expected));

        expected = new CaseState[][]{{CaseState.EMPTY, CaseState.FULL}, {CaseState.FULL, CaseState.FULL}};
        impotesteur.rotateCasesTo(1);
        assertTrue("Error in Amogous rotation test", impotesteur.getGrid().equals(expected));

        expected = new CaseState[][]{{CaseState.FULL, CaseState.EMPTY}, {CaseState.FULL, CaseState.FULL}};
        impotesteur.rotateCasesTo(1);
        assertTrue("Error in Amogous rotation test", impotesteur.getGrid().equals(expected));

        //Test that the modulo condition run correctly
        expected = new CaseState[][]{{CaseState.FULL, CaseState.EMPTY}, {CaseState.FULL, CaseState.FULL}};
        impotesteur.rotateCasesTo(64);
        assertTrue("Error in modulo rotation test", impotesteur.getGrid().equals(expected));

        //Test that there is no useless 0 lines after linear block rotation
        Napsta testBlock = new Napsta(new Position(0,0));

        testBlock.rotateCasesTo(1);

        expected = new CaseState[][] {{CaseState.FULL, CaseState.FULL, CaseState.FULL}};
        assertTrue("Error in napsta rotation test", testBlock.getGrid().equals(expected));

        testBlock.rotateCasesTo(1);

        expected = new CaseState[][] {{CaseState.FULL}, {CaseState.FULL}, {CaseState.FULL}};
        assertTrue("Error in napsta rotation test", testBlock.getGrid().equals(expected));
    }
}