package org.m14i.ext.utils;

import org.m14i.ext.iterator.ExtIterable;
import org.m14i.ext.iterator.ExtIterableImpl;
import org.m14i.ext.iterator.ImmutableIterator;

public class Iterators {

    public static <T> ExtIterable<T> repeat(final T item, final long repetitions) {
        return new ExtIterableImpl<T>(new ImmutableIterator<T>() {

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

    public static ExtIterableImpl<Integer> rangeInt(final Integer end) {
        return rangeInt(0, end, 1);
    }

    public static ExtIterableImpl<Integer> rangeInt(final Integer start, final Integer end) {
        return rangeInt(start, end, start < end ? 1 : -1);
    }

    public static ExtIterable<Long> rangeLong(final Long start, final Long end) {
        return rangeLong(start, end, start < end ? 1L : -1L);
    }

    public static ExtIterable<Double> rangeDouble(final Double start, final Double end) {
        return rangeDouble(start, end, start < end ? 1D : -1D);
    }

    public static ExtIterable<Float> rangeFloat(final Float start, final Float end) {
        return rangeFloat(start, end, start < end ? 1F : -1F);
    }

    public static ExtIterableImpl<Integer> rangeInt(final Integer start, final Integer end, final Integer step) {
        return new ExtIterableImpl<Integer>(new ImmutableIterator<Integer>() {

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

    public static ExtIterable<Long> rangeLong(final Long start, final Long end, final Long step) {
        return new ExtIterableImpl<Long>(new ImmutableIterator<Long>() {

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

    public static ExtIterable<Double> rangeDouble(final Double start, final Double end, final Double step) {
        return new ExtIterableImpl<Double>(new ImmutableIterator<Double>() {

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

    public static ExtIterable<Float> rangeFloat(final Float start, final Float end, final Float step) {
        return new ExtIterableImpl<Float>(new ImmutableIterator<Float>() {

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
