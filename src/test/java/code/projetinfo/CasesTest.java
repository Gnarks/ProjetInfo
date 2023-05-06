package code.projetinfo;

import org.junit.Test;

import static org.junit.Assert.*;

public class CasesTest {
    @Test
    public void set() {
        Cases c = new Cases(3,3, CaseState.EMPTY);
        c.set(1,1, CaseState.FULL);

        CaseState[][] expected = new CaseState[][] {{CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY}, {CaseState.EMPTY, CaseState.FULL, CaseState.EMPTY}, {CaseState.EMPTY,CaseState.EMPTY,CaseState.EMPTY}};
        assertTrue("Error in set test", c.equals(expected));
    }

    @Test
    public void remove() {
        Cases c = new Cases(3,3, CaseState.FULL);
        c.remove(1,1);

        CaseState[][] expected = new CaseState[][] {{CaseState.FULL, CaseState.FULL, CaseState.FULL}, {CaseState.FULL, CaseState.EMPTY, CaseState.FULL}, {CaseState.FULL,CaseState.FULL,CaseState.FULL}};
        assertTrue("Error in remove test", c.equals(expected));
    }

    @Test
    public void testEquals() {
        Cases c = new Cases(3,3, CaseState.EMPTY);
        CaseState[][] expected = new CaseState[][] {{CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY}, {CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY}, {CaseState.EMPTY,CaseState.EMPTY,CaseState.EMPTY}};

        assertTrue("Error in Cases.equals test 1", c.equals(expected));

        c.set(1,1,CaseState.FULL);
        assertFalse("Error in Cases.equals test 2", c.equals(expected));
    }
}