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
    
    public static <T> ExtIterable<T> from(Iterable<T> iterable) {
        return new ExtIterableImpl<T>(iterable);
    }

    public static <T> ExtIterable<T> from(T... xs) {
        return new ExtIterableImpl<T>(Arrays.asList(xs));
    }

    public static ExtIterable<Byte> from(byte[] xs) {
        Collection<Byte> bytes = new LinkedList<Byte>();
        for (byte x : xs)
            bytes.add(x);

        return from(bytes);
    }

    public static ExtIterable<Character> from(char[] xs) {
        Collection<Character> chars = new LinkedList<Character>();
        for (char x : xs)
            chars.add(x);

        return from(chars);
    }

    public static ExtIterable<Integer> from(int[] xs) {
        Collection<Integer> ints = new LinkedList<Integer>();
        for (int x : xs)
            ints.add(x);

        return from(ints);
    }

    public static ExtIterable<Long> from(long[] xs) {
        Collection<Long> longs = new LinkedList<Long>();
        for (long x : xs)
            longs.add(x);

        return from(longs);
    }

    public static ExtIterable<Float> from(float[] xs) {
        Collection<Float> floats = new LinkedList<Float>();
        for (float x : xs)
            floats.add(x);

        return from(floats);
    }

    public static ExtIterable<Double> from(double[] xs) {
        Collection<Double> doubles = new LinkedList<Double>();
        for (double x : xs)
            doubles.add(x);

        return from(doubles);
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

    public static <Z, T extends Closeable> Z tryWith(T x, Func1<T, Z> func) throws IOException {
        Z result = null;
        try {
            result = func.apply(x);
        } finally {
            x.close();
        }
        return result;
    }
}