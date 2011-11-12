package org.m14i.ext.iterator;

import org.m14i.ext.methods.Func1;
import org.m14i.ext.methods.Func2;
import org.m14i.ext.methods.Proc1;
import org.m14i.ext.utils.Procedures;
import org.m14i.ext.tuples.Tuple2;
import org.m14i.ext.utils.Iterators;
import org.m14i.ext.utils.Functions;
import junit.framework.Assert;
import org.junit.Test;

import java.util.*;

import static org.m14i.ext.Ext.ext;

public class ExtIterableTest {
    @Test
    public void testAll() throws Exception {
        Collection<String> strings = new LinkedList<String>();
        strings.add("a");
        strings.add("a");
        strings.add("a");

        boolean expected = true;

        boolean actual = ext(strings).all(new Func1<String, Boolean>() {
            @Override
            public Boolean apply(String arg) {
                return arg.equals("a");
            }
        });

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testAny() throws Exception {
        Collection<String> strings = new LinkedList<String>();
        strings.add("a");
        strings.add("a");
        strings.add("a");
        strings.add("b");

        boolean expected = true;

        boolean actual = ext(strings).any(new Func1<String, Boolean>() {
            @Override
            public Boolean apply(String arg) {
                return arg.equals("b");
            }
        });

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testCount() throws Exception {
        Collection<String> strings = new LinkedList<String>();
        strings.add("a");
        strings.add("a");
        strings.add("b");
        strings.add("a");
        strings.add("b");
        strings.add("a");

        long expected = 4;

        long actual = ext(strings).count(new Func1<String, Boolean>() {
            @Override
            public Boolean apply(String arg) {
                return arg.equals("a");
            }
        });

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testDrop() throws Exception {
        final Integer expected[] = {95, 96, 97, 98, 99};

        Iterators.rangeInt(0, 100).drop(95).each(new Proc1<Integer>() {
            int count = 0;

            @Override
            public void apply(Integer arg) {
                Assert.assertEquals(expected[count++], arg);
            }
        });
    }

    @Test
    public void testFilter() throws Exception {
        Collection<String> strings = new LinkedList<String>();
        strings.add("a");
        strings.add("a");
        strings.add("b");
        strings.add("a");
        strings.add("a");

        List<String> actual = ext(strings).filter(new Func1<String, Boolean>() {
            @Override
            public Boolean apply(String arg) {
                return arg.equals("a");
            }
        }).as(new ArrayList<String>());

        for (String item : actual) {
            Assert.assertTrue(!item.equals("b"));
        }
    }

    @Test
    public void testMap() throws Exception {
        List<String> strings = new LinkedList<String>();
        strings.add("a");
        strings.add("a");
        strings.add("b");
        strings.add("a");
        strings.add("a");

        List<Integer> expected = new LinkedList<Integer>();
        expected.add(97);
        expected.add(97);
        expected.add(98);
        expected.add(97);
        expected.add(97);

        ExtIterable<Integer> it = ext(strings).map(new Func1<String, Integer>() {
            @Override
            public Integer apply(String arg) {
                return arg.hashCode();
            }
        });

        //it.each(Procedures.println());

        List<Integer> actual = it.as(new ArrayList<Integer>());

        for (int i = 0; i < expected.size(); i++) {
            Assert.assertEquals(expected.get(i), actual.get(i));
        }
    }

    @Test
    public void testReduce() throws Exception {
        Collection<String> strings = new LinkedList<String>();
        strings.add("a");
        strings.add("b");
        strings.add("c");

        String expected = "/a/b/c/";

        String actual = ext(strings).reduce("/", new Func2<String, String, String>() {
            @Override
            public String apply(String carry, String item) {
                return carry + item + "/";
            }
        });

        System.out.println(actual);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testStuff() throws Exception {
        // What is the sum of the square roots of numbers 30-100?
        Double sum = Iterators.rangeInt(30, 100).map(new Func1<Integer, Double>() {
            @Override
            public Double apply(Integer arg) {
                return Math.sqrt(arg);
            }
        }).reduce(0.0, Functions.sumDouble());

        System.out.println(sum);
    }

    @Test
    public void testTake() throws Exception {
        final Integer expected[] = {30, 31, 32};

        Iterators.rangeInt(30, 100).take(3).each(new Proc1<Integer>() {
            int count = 0;

            @Override
            public void apply(Integer arg) {
                Assert.assertEquals(expected[count++], arg);
            }
        });
    }

    @Test
    public void testZip() throws Exception {
        List<String> strings = new LinkedList<String>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        strings.add("e");

        List<Integer> ints = new LinkedList<Integer>();
        ints.add(1);
        ints.add(2);
        ints.add(3);
        ints.add(4);
        ints.add(5);

        List<Tuple2<Integer, String>> expected = new LinkedList<Tuple2<Integer, String>>();
        expected.add(new Tuple2<Integer, String>(1, "a"));
        expected.add(new Tuple2<Integer, String>(2, "b"));
        expected.add(new Tuple2<Integer, String>(3, "c"));
        expected.add(new Tuple2<Integer, String>(4, "d"));
        expected.add(new Tuple2<Integer, String>(5, "e"));

        List<Tuple2<Integer, String>> actual = ext(ints).zip(strings).as(new ArrayList<Tuple2<Integer, String>>());

        for (int i = 0; i < expected.size(); i++) {
            System.out.println(actual.get(i));

            Assert.assertEquals(expected.get(i).getItem1(), actual.get(i).getItem1());
            Assert.assertEquals(expected.get(i).getItem2(), actual.get(i).getItem2());
        }
    }

    @Test
    public void testWrap() throws Exception {

        String expected = "a/b/c/a/b/c/a/b/c/a";
        String actual = ext(Arrays.asList("a", "b", "c")).wrap().take(10).reduce("", Functions.join("/"));

        Assert.assertEquals(expected, actual);
    }
}
