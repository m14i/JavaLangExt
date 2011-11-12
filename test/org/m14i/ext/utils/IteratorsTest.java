package org.m14i.ext.utils;

import junit.framework.Assert;
import org.junit.Test;
import org.m14i.ext.methods.Proc1;
import org.m14i.ext.tuples.Tuple2;

import java.util.ArrayList;
import java.util.List;


public class IteratorsTest {
    @Test
    public void testAsInt2() throws Exception {

    }

    @Test
    public void testAsLong2() throws Exception {

    }

    @Test
    public void testAsDouble2() throws Exception {

    }

    @Test
    public void testAsFloat2() throws Exception {

    }

    @Test
    public void testAsInt() throws Exception {

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
    public void testAsLong() throws Exception {

    }

    @Test
    public void testAsDouble() throws Exception {

    }

    @Test
    public void testAsFloat() throws Exception {

    }
}
