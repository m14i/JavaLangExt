package jle.ext;

import jle.ext.iterator.ExtIterable;
import jle.ext.iterator.ImmutableIterator;

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

    /**
     * Create human-readable string
     */
    public static String humanize(String text) {
        text = text.replaceAll("_", " ").trim();

        String first = text.substring(0, 1);
        String rest = text.substring(1);

        StringBuilder builder = new StringBuilder();
        builder.append(first);

        Character previous = first.toCharArray()[0];
        for (Character current : rest.toCharArray()) {
            if (Character.isLowerCase(previous) && Character.isUpperCase(current))
                builder.append(" ");

            builder.append(Character.toLowerCase(current));
            previous = current;
        }

        return builder.toString();
    }

    /**
     * Try to parse number and ignore NumberFormatException
     */
    public static Double tryParseDouble(String num, double defaultValue) {
        try {
            return Double.parseDouble(num);
        } catch (NumberFormatException ignored) {
        }
        return defaultValue;
    }

    /**
     * Try to parse number and ignore NumberFormatException
     */
    public static Float tryParseFloat(String num, float defaultValue) {
        try {
            return Float.parseFloat(num);
        } catch (NumberFormatException ignored) {
        }
        return defaultValue;
    }

    /**
     * Try to parse number and ignore NumberFormatException
     */
    public static Long tryParseLong(String num, long defaultValue) {
        try {
            return Long.parseLong(num);
        } catch (NumberFormatException ignored) {
        }
        return defaultValue;
    }

    /**
     * Try to parse number and ignore NumberFormatException
     */
    public static Integer tryParseInt(String num, int defaultValue) {
        try {
            return Integer.parseInt(num);
        } catch (NumberFormatException ignored) {
        }

        return defaultValue;
    }
}