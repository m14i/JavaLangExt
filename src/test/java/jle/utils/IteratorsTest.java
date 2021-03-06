/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2011 Michael Stöckli
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package jle.utils;


import jle.methods.Fn2;
import jle.tuples.Tuple2;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class IteratorsTest {

    private <T> Fn2<Integer, Tuple2<T, T>, Integer> assertEqualsAndGetCount() {
        return new Fn2<Integer, Tuple2<T, T>, Integer>() {
            @Override
            public Integer apply(Integer acc, Tuple2<T, T> arg) {
                Con.println(arg);
                Assert.assertEquals(arg._1(), arg._2());
                return acc + 1;
            }
        };
    }

    @Before
    public void setUp() {
        Con.setPrintEnabled(false);
    }


    @Test
    public void testRepeat() throws Exception {
        List<String> hellos = Iterators.repeat("hello", 5).into(new ArrayList<String>());

        assertEquals(5, hellos.size());
    }

    @Test
    public void testRangeInt1() throws Exception {

        List<Integer> expected = new ArrayList<Integer>();
        expected.add(0);
        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(4);
        expected.add(5);

        int count = Iterators.range(6).zip(expected).reduce(0, this.<Integer>assertEqualsAndGetCount());
        Assert.assertEquals(expected.size(), count);
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

        int count = Iterators.range(10, 4).zip(expected).reduce(0, this.<Integer>assertEqualsAndGetCount());
        Assert.assertEquals(expected.size(), count);
    }

    @Test
    public void testRangeLong2() throws Exception {
        List<Long> expected = new ArrayList<Long>();
        expected.add(10L);
        expected.add(9L);
        expected.add(8L);
        expected.add(7L);
        expected.add(6L);
        expected.add(5L);

        int count = Iterators.range(10L, 4L).zip(expected).reduce(0, this.<Long>assertEqualsAndGetCount());
        Assert.assertEquals(expected.size(), count);
    }

    @Test
    public void testRangeDouble2() throws Exception {
        List<Double> expected = new ArrayList<Double>();
        expected.add(10D);
        expected.add(9D);
        expected.add(8D);
        expected.add(7D);
        expected.add(6D);
        expected.add(5D);

        int count = Iterators.range(10D, 4D).zip(expected).reduce(0, this.<Double>assertEqualsAndGetCount());
        Assert.assertEquals(expected.size(), count);
    }

    @Test
    public void testRangeFloat2() throws Exception {
        List<Float> expected = new ArrayList<Float>();
        expected.add(10F);
        expected.add(9F);
        expected.add(8F);
        expected.add(7F);
        expected.add(6F);
        expected.add(5F);

        int count = Iterators.range(10F, 4F).zip(expected).reduce(0, this.<Float>assertEqualsAndGetCount());
        Assert.assertEquals(expected.size(), count);
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

        int count = Iterators.range(5, 30, 4).zip(expected).reduce(0, this.<Integer>assertEqualsAndGetCount());
        Assert.assertEquals(expected.size(), count);
    }

    @Test
    public void testRangeLong() throws Exception {
        List<Long> expected = new ArrayList<Long>();
        expected.add(5L);
        expected.add(9L);
        expected.add(13L);
        expected.add(17L);
        expected.add(21L);
        expected.add(25L);
        expected.add(29L);

        int count = Iterators.range(5L, 30L, 4L).zip(expected).reduce(0, this.<Long>assertEqualsAndGetCount());
        Assert.assertEquals(expected.size(), count);

    }

    @Test
    public void testRangeDouble() throws Exception {
        List<Double> expected = new ArrayList<Double>();
        expected.add(5D);
        expected.add(9D);
        expected.add(13D);
        expected.add(17D);
        expected.add(21D);
        expected.add(25D);
        expected.add(29D);

        int count = Iterators.range(5D, 30D, 4D).zip(expected).reduce(0, this.<Double>assertEqualsAndGetCount());
        Assert.assertEquals(expected.size(), count);
    }

    @Test
    public void testRangeFloat() throws Exception {
        List<Float> expected = new ArrayList<Float>();
        expected.add(5F);
        expected.add(9F);
        expected.add(13F);
        expected.add(17F);
        expected.add(21F);
        expected.add(25F);
        expected.add(29F);

        int count = Iterators.range(5F, 30F, 4F).zip(expected).reduce(0, this.<Float>assertEqualsAndGetCount());
        Assert.assertEquals(expected.size(), count);
    }
}
