package ft;

import org.junit.Test;

import static ft.LineCumulator.cumulate;
import static org.junit.Assert.assertEquals;

public class LineCumulatorTest {

    @Test
    public void canCumulateOneValue() {
        int[] line1 = new int[]{1};
        int[] line2 = new int[]{2, 3};
        cumulate(line1, line2);
        assertEquals(3, line2[0]);
        assertEquals(4, line2[1]);
    }

    @Test
    public void canCumulateArbitraryAmountOfValues() {
        int[] line1 = new int[]{1, 2};
        int[] line2 = new int[]{3, 4, 5};
        cumulate(line1, line2);
        assertEquals(4, line2[0]);
        assertEquals(6, line2[1]);
        assertEquals(7, line2[2]);
    }
}
