package org.m14i.ext;


import org.m14i.ext.iterator.ExtIterable;
import org.m14i.ext.iterator.ImmutableIterator;
import org.m14i.ext.methods.Fn1;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

public class Ext {

    public static <T> ExtIterable<T> from(Iterator<T> iterator) {
        return new ExtIterable<T>(iterator);
    }

    public static <T> ExtIterable<T> from(Iterable<T> iterable) {
        return from(iterable.iterator());
    }

    public static <T> ExtIterable<T> from(T... xs) {
        return from(Arrays.asList(xs));
    }

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

    public static <T> T coalesce(T... xs) {
        for (T x : xs)
            if (x != null)
                return x;

        return null;
    }

    public static boolean isNullOrEmpty(String string) {
        return (null == string || string.isEmpty());
    }

    public static Double tryParseDouble(String num) {
        Double result = null;
        try {
            result = Double.parseDouble(num);
        } catch (NumberFormatException ignored) {
        }
        return result;
    }

    public static Float tryParseFloat(String num) {
        Float result = null;
        try {
            result = Float.parseFloat(num);
        } catch (NumberFormatException ignored) {
        }
        return result;
    }

    public static Long tryParseLong(String num) {
        Long result = null;
        try {
            result = Long.parseLong(num);
        } catch (NumberFormatException ignored) {
        }
        return result;
    }

    public static Integer tryParseInt(String num) {
        Integer result = null;
        try {
            result = Integer.parseInt(num);
        } catch (NumberFormatException ignored) {
        }
        return result;
    }

    public static <Z, T extends Closeable> Z tryWith(T x, Fn1<T, Z> func) throws IOException {
        Z result = null;
        try {
            result = func.apply(x);
        } finally {
            x.close();
        }
        return result;
    }
}