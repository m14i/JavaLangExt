package org.m14i.ext.iterator;

import junit.framework.Assert;
import org.junit.Test;
import org.m14i.ext.Ext;
import org.m14i.ext.methods.Func1;
import org.m14i.ext.methods.Func2;
import org.m14i.ext.methods.Pred1;
import org.m14i.ext.methods.Proc1;
import org.m14i.ext.tuples.Tuple2;
import org.m14i.ext.utils.Functions;
import org.m14i.ext.utils.Iterators;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.m14i.ext.Ext.from;

public class ExtIterableTest {
    @Test
    public void testAllTrue() throws Exception {
        Collection<String> strings = new LinkedList<String>();
        strings.add("a");
        strings.add("a");
        strings.add("a");

        boolean expected = true;

        boolean actual = Ext.from(strings).all(new Pred1<String>() {
            @Override
            public Boolean apply(String arg) {
                return arg.equals("a");
            }
        });

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testAllFalse() throws Exception {
        Collection<String> strings = new LinkedList<String>();
        strings.add("a");
        strings.add("a");
        strings.add("b");

        boolean expected = false;

        boolean actual = Ext.from(strings).all(new Pred1<String>() {
            @Override
            public Boolean apply(String arg) {
                return arg.equals("a");
            }
        });

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testAnyTrue() throws Exception {
        Collection<String> strings = new LinkedList<String>();
        strings.add("a");
        strings.add("a");
        strings.add("a");
        strings.add("b");

        boolean expected = true;

        boolean actual = Ext.from(strings).any(new Pred1<String>() {
            @Override
            public Boolean apply(String arg) {
                return arg.equals("b");
            }
        });

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testAnyFalse() throws Exception {
        Collection<String> strings = new LinkedList<String>();
        strings.add("a");
        strings.add("a");
        strings.add("a");
        strings.add("a");

        boolean expected = false;

        boolean actual = Ext.from(strings).any(new Pred1<String>() {
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

        long actual = Ext.from(strings).count(new Pred1<String>() {
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

        List<String> actual = Ext.from(strings).filter(new Pred1<String>() {
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

        final List<Tuple2<Integer, String>> expected = new LinkedList<Tuple2<Integer, String>>();
        expected.add(new Tuple2<Integer, String>(97, "A"));
        expected.add(new Tuple2<Integer, String>(97, "A"));
        expected.add(new Tuple2<Integer, String>(98, "B"));
        expected.add(new Tuple2<Integer, String>(97, "A"));
        expected.add(new Tuple2<Integer, String>(97, "A"));

        Ext.from(strings)
                .map(new Func1<String, Integer>() {
                         @Override
                         public Integer apply(String arg) {
                             return arg.hashCode();
                         }
                     },
                        new Func1<String, String>() {
                            @Override
                            public String apply(String arg) {
                                return arg.toUpperCase();
                            }
                        }
                )
                .each(new Proc1<Tuple2<Integer, String>>() {

                    int counter = 0;

                    @Override
                    public void apply(Tuple2<Integer, String> arg) {
                        Assert.assertEquals(expected.get(counter).get1(), arg.get1());
                        Assert.assertEquals(expected.get(counter).get2(), arg.get2());
                        counter++;
                    }
                });
    }

    @Test
    public void testReduce() throws Exception {
        Collection<String> strings = new LinkedList<String>();
        strings.add("a");
        strings.add("b");
        strings.add("c");

        String expected = "/a/b/c/";

        String actual = Ext.from(strings).reduce("/", new Func2<String, String, String>() {
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
    public void testToStrings() throws Exception {
        String expected = "42, 43, 44, 45, 46, 47, 48, 49";
        String actual = Iterators.rangeInt(42, 50).toStrings().reduce("", Functions.join(", "));

        Assert.assertEquals(expected, actual);
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

        List<Tuple2<Integer, String>> actual = Ext.from(ints).zip(strings).as(new ArrayList<Tuple2<Integer, String>>());

        for (int i = 0; i < expected.size(); i++) {
            System.out.println(actual.get(i));

            Assert.assertEquals(expected.get(i).get1(), actual.get(i).get1());
            Assert.assertEquals(expected.get(i).get2(), actual.get(i).get2());
        }
    }

    @Test
    public void testWrap() throws Exception {

        String expected = "a/b/c/a/b/c/a/b/c/a";
        String actual = Ext.from("a", "b", "c").wrap().take(10).reduce("", Functions.join("/"));

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testSort() throws Exception {

        String expected = "1,2,3,4,5";
        String actual = Ext.from(3, 2, 1, 5, 4).sort().toStrings().reduce("", Functions.join(","));

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testSort2() throws Exception {

        String expected = "5,4,3,2,1";
        String actual = Ext.from(3, 2, 1, 5, 4).sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return -a.compareTo(b);
            }
        }).toStrings().reduce("", Functions.join(","));

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testHeadWithEmptyList() {
        Object head = from(new ArrayList()).head();

        assertNull(head);
    }

    @Test
    public void testHead() {
        int head = from(1, 2, 3).head();

        assertEquals(1, head);
    }

}
