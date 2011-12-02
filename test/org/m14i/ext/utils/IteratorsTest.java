package org.m14i.ext.utils;

import org.junit.Assert;
import org.junit.Test;
import org.m14i.ext.methods.Proc1;
import org.m14i.ext.tuples.Tuple2;

import java.util.ArrayList;
import java.util.List;


public class IteratorsTest {

    private <T> Proc1<Tuple2<T, T>> getAssertEquals() {
        return new Proc1<Tuple2<T, T>>() {
            @Override
            public void apply(Tuple2<T, T> arg) {
                System.out.println(arg);
                Assert.assertEquals(arg.getItem1(), arg.getItem2());
            }
        };
    }

    @Test
    public void testRangeInt2() throws Exception {

        List<Integer> expected = new ArrayList<Integer>();
        expected.add(10);
        expected.add(9);
        expected.add(8);
        expected.add(7);
        expected.add(6);
        expected.add(5);

        Iterators.rangeInt(10, 4).zip(expected).each(this.<Integer>getAssertEquals());
    }

    @Test
    public void testRangeLong2() throws Exception {

    }

    @Test
    public void testRangeDouble2() throws Exception {

    }

    @Test
    public void testRangeFloat2() throws Exception {

    }

    @Test
    public void testRangeInt() throws Exception {

        List<Integer> expected = new ArrayList<Integer>();
        expected.add(5);
        expected.add(9);
        expected.add(13);
        expected.add(17);
        expected.add(21);
        expected.add(25);
        expected.add(29);

        Iterators.rangeInt(5, 30, 4).zip(expected).each(this.<Integer>getAssertEquals());
    }

    @Test
    public void testRangeLong() throws Exception {

    }

    @Test
    public void testRangeDouble() throws Exception {

    }

    @Test
    public void testRangeFloat() throws Exception {

    }
}
