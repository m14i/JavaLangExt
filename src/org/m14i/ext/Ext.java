package org.m14i.ext;


import org.m14i.ext.iterator.ExtIterable;
import org.m14i.ext.iterator.ExtIterableImpl;
import org.m14i.ext.methods.Func1;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

public class Ext {
    
    public static <T> ExtIterable<T> ext(Iterable<T> iterable) {
        return new ExtIterableImpl<T>(iterable);
    }

    public static <T> ExtIterable<T> ext(T... items) {
        return new ExtIterableImpl<T>(Arrays.asList(items));
    }

    public static ExtIterable<Byte> ext(byte[] items) {
        Collection<Byte> bytes = new LinkedList<Byte>();
        for (byte item : items) {
            bytes.add(item);
        }
        return ext(bytes);
    }

    public static ExtIterable<Character> ext(char[] items) {
        Collection<Character> chars = new LinkedList<Character>();
        for (char item : items) {
            chars.add(item);
        }
        return ext(chars);
    }

    public static ExtIterable<Integer> ext(int[] items) {
        Collection<Integer> ints = new LinkedList<Integer>();
        for (int item : items) {
            ints.add(item);
        }
        return ext(ints);
    }

    public static ExtIterable<Long> ext(long[] items) {
        Collection<Long> longs = new LinkedList<Long>();
        for (long item : items) {
            longs.add(item);
        }
        return ext(longs);
    }

    public static ExtIterable<Float> ext(float[] items) {
        Collection<Float> floats = new LinkedList<Float>();
        for (float item : items) {
            floats.add(item);
        }
        return ext(floats);
    }

    public static ExtIterable<Double> ext(double[] items) {
        Collection<Double> doubles = new LinkedList<Double>();
        for (double item : items) {
            doubles.add(item);
        }
        return ext(doubles);
    }

    public static <T> T coalesce(T... items) {
        for (T item : items) {
            if (item != null) {
                return item;
            }
        }
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

    public static <Z, T extends Closeable> Z tryWith(T object, Func1<T, Z> func) throws IOException {
        Z result = null;
        try {
            result = func.apply(object);
        } finally {
            object.close();
        }
        return result;
    }
}