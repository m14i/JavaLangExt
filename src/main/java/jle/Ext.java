package jle;

import jle.iterator.ExtIterable;
import jle.iterator.ImmutableIterator;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class Ext {

    /**
     * Convert iterator to ExtIterable
     */
    public static <T> ExtIterable<T> from(Iterator<T> iterator) {
        return new ExtIterable<T>(iterator);
    }

    /**
     * Convert iterable to ExtIterable
     */
    public static <T> ExtIterable<T> from(Iterable<T> iterable) {
        return from(iterable.iterator());
    }

    /**
     * Convert objects to ExtIterable
     */
    public static <T> ExtIterable<T> from(T... xs) {
        return from(Arrays.asList(xs));
    }

    /**
     * Convert array to ExtIterable
     */
    public static ExtIterable<Byte> from(final byte[] xs) {
        return from(new ImmutableIterator<Byte>() {
            int count = 0;

            @Override
            public boolean hasNext() {
                return count < xs.length;
            }

            @Override
            public Byte next() {
                return xs[count++];
            }
        });
    }

    /**
     * Convert array to ExtIterable
     */
    public static ExtIterable<Character> from(final char[] xs) {
        return from(new ImmutableIterator<Character>() {
            int count = 0;

            @Override
            public boolean hasNext() {
                return count < xs.length;
            }

            @Override
            public Character next() {
                return xs[count++];
            }
        });
    }

    /**
     * Convert array to ExtIterable
     */
    public static ExtIterable<Integer> from(final int[] xs) {
        return from(new ImmutableIterator<Integer>() {
            int count = 0;

            @Override
            public boolean hasNext() {
                return count < xs.length;
            }

            @Override
            public Integer next() {
                return xs[count++];
            }
        });
    }

    /**
     * Convert array to ExtIterable
     */
    public static ExtIterable<Long> from(final long[] xs) {
        return from(new ImmutableIterator<Long>() {
            int count = 0;

            @Override
            public boolean hasNext() {
                return count < xs.length;
            }

            @Override
            public Long next() {
                return xs[count++];
            }
        });
    }

    /**
     * Convert array to ExtIterable
     */
    public static ExtIterable<Float> from(final float[] xs) {
        return from(new ImmutableIterator<Float>() {
            int count = 0;

            @Override
            public boolean hasNext() {
                return count < xs.length;
            }

            @Override
            public Float next() {
                return xs[count++];
            }
        });
    }

    /**
     * Convert array to ExtIterable
     */
    public static ExtIterable<Double> from(final double[] xs) {
        return from(new ImmutableIterator<Double>() {
            int count = 0;

            @Override
            public boolean hasNext() {
                return count < xs.length;
            }

            @Override
            public Double next() {
                return xs[count++];
            }
        });
    }

    /**
     * Return vararg as array
     */
    public static <T> T[] array(T... items) {
        return items;
    }

    /**
     * Returns first object that is not null
     */
    public static <T> T coalesce(T... xs) {
        for (T x : xs)
            if (x != null)
                return x;

        return null;
    }

    /**
     * Check if string is null or empty
     */
    public static boolean isNullOrEmpty(String string) {
        return (null == string || string.isEmpty());
    }

    /**
     * Check if collection is null or empty
     */
    public static boolean isNullOrEmpty(Collection xs) {
        return (null == xs || xs.isEmpty());
    }

    /**
     * Check if map is null or empty
     */
    public static boolean isNullOrEmpty(Map xs) {
        return (null == xs || xs.isEmpty());
    }

    /**
     * Check if array is null or empty
     */
    public static <T> boolean isNullOrEmpty(T... xs) {
        return (null == xs || xs.length == 0);
    }

    /**
     * Stringify object
     */
    public static String str(Object object) {
        return String.valueOf(object);
    }

}