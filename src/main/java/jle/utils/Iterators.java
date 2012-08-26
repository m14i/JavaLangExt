package jle.utils;

import static jle.Ext.from;

import jle.iterator.ExtIterable;
import jle.iterator.ImmutableIterator;

public class Iterators {

    public static <T> ExtIterable<T> repeat(final T item, final long repetitions) {
        return from(new ImmutableIterator<T>() {

            long counter = 0;

            @Override
            public boolean hasNext() {
                return counter < repetitions;
            }

            @Override
            public T next() {
                counter++;
                return item;
            }
        });
    }

    public static ExtIterable<Integer> range(final Integer end) {
        return range(0, end, 1);
    }

    public static ExtIterable<Integer> range(final Integer start, final Integer end) {
        return range(start, end, start < end ? 1 : -1);
    }

    public static ExtIterable<Long> range(final Long start, final Long end) {
        return range(start, end, start < end ? 1L : -1L);
    }

    public static ExtIterable<Double> range(final Double start, final Double end) {
        return range(start, end, start < end ? 1D : -1D);
    }

    public static ExtIterable<Float> range(final Float start, final Float end) {
        return range(start, end, start < end ? 1F : -1F);
    }

    public static ExtIterable<Integer> range(final Integer start, final Integer end, final Integer step) {
        return from(new ImmutableIterator<Integer>() {

            Integer counter = start;

            @Override
            public boolean hasNext() {
                return step > 0 ? counter < end : end < counter;
            }

            @Override
            public Integer next() {
                Integer current = counter;
                counter += step;
                return current;
            }
        });
    }

    public static ExtIterable<Long> range(final Long start, final Long end, final Long step) {
        return from(new ImmutableIterator<Long>() {

            Long counter = start;

            @Override
            public boolean hasNext() {
                return step > 0 ? counter < end : end < counter;
            }

            @Override
            public Long next() {
                Long current = counter;
                counter += step;
                return current;
            }
        });
    }

    public static ExtIterable<Double> range(final Double start, final Double end, final Double step) {
        return from(new ImmutableIterator<Double>() {

            Double counter = start;

            @Override
            public boolean hasNext() {
                return step > 0 ? counter < end : end < counter;
            }

            @Override
            public Double next() {
                Double current = counter;
                counter += step;
                return current;
            }
        });
    }

    public static ExtIterable<Float> range(final Float start, final Float end, final Float step) {
        return from(new ImmutableIterator<Float>() {

            Float counter = start;

            @Override
            public boolean hasNext() {
                return step > 0 ? counter < end : end < counter;
            }

            @Override
            public Float next() {
                Float current = counter;
                counter += step;
                return current;
            }
        });
    }
}
