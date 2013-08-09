package jle.utils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FunctionsTest {

    @Before
    public void setUp() {
        Con.setPrintEnabled(false);
    }

    @Test
    public void testJoin() throws Exception {

        String actual = Iterators.range(0.0, 4.0, 0.5).join(", ");
        String expected = "0.0, 0.5, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5";

        Con.println(actual);

        assertEquals(expected, actual);
    }

    @Test
    public void testSumDouble() throws Exception {

        Double actual = Iterators.range(0.0, 101.0).reduce(0.0, Functions.sumDouble());
        Double expected = 5050.0;

        Con.println(actual);

        assertEquals(expected, actual);
    }

    @Test
    public void testSumLong() throws Exception {
        Long actual = Iterators.range(0L, 101L).reduce(0L, Functions.sumLong());
        Long expected = 5050L;

        Con.println(actual);

        assertEquals(expected, actual);
    }

    @Test
    public void testSumFloat() throws Exception {
        Float actual = Iterators.range(0.0F, 101.0F).reduce(0.0F, Functions.sumFloat());
        Float expected = 5050.0F;

        Con.println(actual);

        assertEquals(expected, actual);
    }

    @Test
    public void testSumInt() throws Exception {
        Integer actual = Iterators.range(0, 101).reduce(0, Functions.sumInt());
        Integer expected = 5050;

        Con.println(actual);

        assertEquals(expected, actual);
    }
}
