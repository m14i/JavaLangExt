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
package jle.iterator;


import jle.Ext;
import jle.methods.Fn1;
import jle.methods.Fn2;
import jle.methods.Pred;
import jle.methods.Proc;
import jle.tuples.Tuple2;
import jle.utils.Con;
import jle.utils.Iterators;
import jle.utils.Predicates;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static jle.Ext.from;
import static jle.utils.Iterators.range;
import static org.junit.Assert.*;

public class ExtIterableTest {

    @Before
    public void setUp() {
        Con.setPrintEnabled(false);
    }

    @Test
    public void testAllTrue() throws Exception {
        Collection<String> strings = new LinkedList<String>();
        strings.add("a");
        strings.add("a");
        strings.add("a");

        boolean expected = true;

        boolean actual = from(strings).all(new Pred<String>() {
            @Override
            public Boolean apply(String arg) {
                return arg.equals("a");
            }
        });

        assertEquals(expected, actual);
    }

    @Test
    public void testAllFalse() throws Exception {
        Collection<String> strings = new LinkedList<String>();
        strings.add("a");
        strings.add("a");
        strings.add("b");

        boolean expected = false;

        boolean actual = from(strings).all(new Pred<String>() {
            @Override
            public Boolean apply(String arg) {
                return arg.equals("a");
            }
        });

        assertEquals(expected, actual);
    }

    @Test
    public void testAnyTrue() throws Exception {
        Collection<String> strings = new LinkedList<String>();
        strings.add("a");
        strings.add("a");
        strings.add("a");
        strings.add("b");

        boolean expected = true;

        boolean actual = from(strings).any(new Pred<String>() {
            @Override
            public Boolean apply(String arg) {
                return arg.equals("b");
            }
        });

        assertEquals(actual, expected);
    }

    @Test
    public void testAnyFalse() throws Exception {
        Collection<String> strings = new LinkedList<String>();
        strings.add("a");
        strings.add("a");
        strings.add("a");
        strings.add("a");

        boolean expected = false;

        boolean actual = from(strings).any(new Pred<String>() {
            @Override
            public Boolean apply(String arg) {
                return arg.equals("b");
            }
        });

        assertEquals(actual, expected);
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

        long actual = from(strings).count(new Pred<String>() {
            @Override
            public Boolean apply(String arg) {
                return arg.equals("a");
            }
        });

        assertEquals(expected, actual);
    }

    @Test
    public void testDrop() throws Exception {
        final Integer expected[] = {95, 96, 97, 98, 99};

        range(0, 100).drop(95).each(new Proc<Integer>() {
            int count = 0;

            @Override
            public void apply(Integer arg) {
                assertEquals(expected[count++], arg);
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

        ExtIterable<String> it = from(strings).filter(new Pred<String>() {
            @Override
            public Boolean apply(String arg) {
                return arg.equals("a");
            }
        });

        for (String x : it)
            assertTrue(!x.equals("b"));
    }

    @Test
    public void testFilterWithNulls() throws Exception {
        Collection<String> strings = new LinkedList<String>();
        strings.add("a");
        strings.add("a");
        strings.add(null);
        strings.add(null);
        strings.add("a");

        List<String> xs = from(strings).filter(Predicates.<String>is(null)).toList();
        assertEquals(2, xs.size());

        for (String x : xs)
            assertNull(x);
    }

    @Test
    public void testReject() throws Exception {
        Collection<String> strings = new LinkedList<String>();
        strings.add("a");
        strings.add("a");
        strings.add("b");
        strings.add("b");
        strings.add("a");
        strings.add("a");

        ExtIterable<String> it = from(strings).reject(new Pred<String>() {
            @Override
            public Boolean apply(String arg) {
                return arg.equals("a");
            }
        });

        for (String x : it)
            assertTrue(x.equals("b"));
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
        expected.add(Tuple2.make(97, "A"));
        expected.add(Tuple2.make(97, "A"));
        expected.add(Tuple2.make(98, "B"));
        expected.add(Tuple2.make(97, "A"));
        expected.add(Tuple2.make(97, "A"));

        from(strings)
                .map(new Fn1<String, Integer>() {
                         @Override
                         public Integer apply(String arg) {
                             return arg.hashCode();
                         }
                     },
                        new Fn1<String, String>() {
                            @Override
                            public String apply(String arg) {
                                return arg.toUpperCase();
                            }
                        }
                )
                .each(new Proc<Tuple2<Integer, String>>() {

                    int counter = 0;

                    @Override
                    public void apply(Tuple2<Integer, String> arg) {
                        assertEquals(expected.get(counter)._1(), arg._1());
                        assertEquals(expected.get(counter)._2(), arg._2());
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

        String actual = from(strings).reduce("/", new Fn2<String, String, String>() {
            @Override
            public String apply(String acc, String x) {
                return acc + x + "/";
            }
        });

        Con.println(actual);

        assertEquals(expected, actual);
    }

    @Test
    public void testTake() throws Exception {
        final Integer expected[] = {30, 31, 32};

        Iterators.range(30, 100).take(3).each(new Proc<Integer>() {
            int count = 0;

            @Override
            public void apply(Integer arg) {
                assertEquals(expected[count++], arg);
            }
        });
    }

    @Test
    public void testToStrings() throws Exception {
        String expected = "42, 43, 44, 45, 46, 47, 48, 49";
        String actual = Iterators.range(42, 50).join(", ");

        assertEquals(expected, actual);
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
        expected.add(Tuple2.make(1, "a"));
        expected.add(Tuple2.make(2, "b"));
        expected.add(Tuple2.make(3, "c"));
        expected.add(Tuple2.make(4, "d"));
        expected.add(Tuple2.make(5, "e"));

        List<Tuple2<Integer, String>> actual = Ext.from(ints).zip(strings).into(new ArrayList<Tuple2<Integer, String>>());

        for (int i = 0; i < expected.size(); i++) {
            Con.println(actual.get(i));

            assertEquals(expected.get(i)._1(), actual.get(i)._1());
            assertEquals(expected.get(i)._2(), actual.get(i)._2());
        }
    }

    @Test
    public void testWrap() throws Exception {
        String expected = "a/b/c/a/b/c/a/b/c/a";
        String actual = Ext.from("a", "b", "c").wrap().take(10).join("/");

        assertEquals(expected, actual);
    }

    @Test
    public void testSort() throws Exception {
        String expected = "1,2,3,4,5";
        String actual = Ext.from(3, 2, 1, 5, 4).sort().join(",");

        assertEquals(expected, actual);
    }

    @Test
    public void testSort2() throws Exception {
        String expected = "5,4,3,2,1";
        String actual = Ext.from(3, 2, 1, 5, 4).sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return -a.compareTo(b);
            }
        }).join(",");

        assertEquals(expected, actual);
    }

    @SuppressWarnings("unchecked")
    @Test(expected = NoSuchElementException.class)
    public void testHeadWithEmptyList() throws Exception {
        from(new ArrayList()).head();
    }

    @Test
    public void testHead() throws Exception {
        int head = from(1, 2, 3).head();

        assertEquals(1, head);
    }

    @Test
    public void testDistinct() throws Exception {
        String result = from("a", "b", "c", "a", "b", "c", "a", "b", "c")
                .distinct()
                .join("");

        assertEquals(3, result.length());
        assertEquals("abc", result);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testFirstWithEmptyList() throws Exception {
        Object first = from(new ArrayList()).first(new Pred() {
            @Override
            public Boolean apply(Object arg) {
                return true;
            }
        });

        assertNull(first);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testFirstNoResult() throws Exception {
        Tuple2<Integer, String> first = from(
                Tuple2.make(1, "a"),
                Tuple2.make(2, "b"),
                Tuple2.make(3, "c"),
                Tuple2.make(4, "b"))
                .first(new Pred<Tuple2<Integer, String>>() {
                    @Override
                    public Boolean apply(Tuple2<Integer, String> arg) {
                        return arg._2().equals("f");
                    }
                });

        assertNull(first);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testFirst() throws Exception {
        Tuple2<Integer, String> first = from(
                Tuple2.make(1, "a"),
                Tuple2.make(2, "b"),
                Tuple2.make(3, "c"),
                Tuple2.make(4, "b"))
                .first(new Pred<Tuple2<Integer, String>>() {
                    @Override
                    public Boolean apply(Tuple2<Integer, String> arg) {
                        return arg._2().equals("b");
                    }
                });

        assertTrue(2 == first._1());
    }

    @Test
    public void testEnumerate() throws Exception {
        from(new long[]{0, 1, 2, 3, 4}).enumerate().each(new Proc<Tuple2<Long, Long>>() {
            @Override
            public void apply(Tuple2<Long, Long> x) {
                assertEquals(x._2(), x._1());
            }
        });
    }

    @Test
    public void testGroup() throws Exception {
        Map<Integer, List<Integer>> group = from(1, 2, 3, 4, 4, 5).group(new Fn1<Integer, Integer>() {
            @Override
            public Integer apply(Integer arg) {
                return arg % 2;
            }
        }).toMap();

        assertEquals(3, group.get(1).size());
        assertEquals(3, group.get(0).size());
    }

    @Test
    public void testGroupDistinct() throws Exception {
        Map<Integer, Set<Integer>> group = from(1, 2, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5).groupDistinct(new Fn1<Integer, Integer>() {
            @Override
            public Integer apply(Integer arg) {
                return arg % 2;
            }
        }).toMap();

        assertEquals(3, group.get(1).size());
        assertEquals(2, group.get(0).size());
    }

    @Test
    public void testRemove() throws Exception {
        String result = from("a", null, "b", null).remove(null).join("-");

        assertEquals("a-b", result);
    }

    @Test
    public void testToSet() throws Exception {
        Set<Integer> actual = from(1, 2, 3, 4, 2, 1).toSet();
        assertEquals(4, actual.size());
        assertTrue(actual.containsAll(Arrays.asList(1, 2, 3, 4)));
    }

    @Test
    public void testFrequency() throws Exception {
        from(1, 2, 3, 8, 8, 8, 8, 8).frequency(new Fn1<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) {
                return integer;
            }
        }).each(new Proc<Tuple2<Integer, Integer>>() {
            @Override
            public void apply(Tuple2<Integer, Integer> arg) {
                switch (arg._1()) {
                    case 8:
                        assertEquals(5L, (long) arg._2());
                        break;
                    default:
                        assertEquals(1L, (long) arg._2());
                }
            }
        });
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testAsCast() throws Exception {
        List<Integer> actual = from(1L, 2L, 3L, 4L)
                .as(Object.class)
                .as(Integer.class)
                .toList();

        assertFalse(actual.get(0) instanceof Integer);
    }

    @Test
    public void testFlattenRemovesNull() throws Exception {
        List<Integer> actual = from(1, 2, 3, 4).map(new Fn1<Integer, List<Integer>>() {
            @Override
            public List<Integer> apply(Integer item) {
                if (item == 3)
                    return null;

                return Arrays.asList(item, item);
            }
        }).flatten().as(Integer.class).toList();

        assertEquals(Arrays.asList(1, 1, 2, 2, 4, 4), actual);
    }

    @Test
    public void testFlattenPreservesDeepNull() throws Exception {
        List<Integer> actual = from(1, 2, 3, 4).map(new Fn1<Integer, List<Integer>>() {
            @Override
            public List<Integer> apply(Integer item) {
                if (item == 3)
                    return Arrays.asList(item, null, item);

                return Arrays.asList(item, item);
            }
        }).flatten().as(Integer.class).toList();

        assertEquals(Arrays.asList(1, 1, 2, 2, 3, null, 3, 4, 4), actual);
    }

}
