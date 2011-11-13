package org.m14i.ext.utils;

import junit.framework.Assert;
import org.junit.Test;
import org.m14i.ext.methods.Proc1;
import org.m14i.ext.tuples.Tuple2;

import java.util.ArrayList;
import java.util.List;


public class IteratorsTest {
    @Test
    public void testRangeInt2() throws Exception {

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

        List<Integer> expecteds = new ArrayList<Integer>();
        expecteds.add(5);
        expecteds.add(9);
        expecteds.add(13);
        expecteds.add(17);
        expecteds.add(21);
        expecteds.add(25);
        expecteds.add(29);

        Iterators.rangeInt(5, 30, 4).zip(expecteds).each(new Proc1<Tuple2<Integer, Integer>>() {
            @Override
            public void apply(Tuple2<Integer, Integer> arg) {
                Assert.assertEquals(arg.getItem1(), arg.getItem2());
            }
        });

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
