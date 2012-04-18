package org.m14i.ext.utils;

import org.m14i.ext.methods.Func2;

public class Functions {

    public static Func2<String, String, String> join(final String separator) {
        return new Func2<String, String, String>() {

            boolean first = true;

            @Override
            public String apply(String acc, String x) {
                if (first) {
                    first = false;
                    return acc + x;
                } else {
                    return acc + separator + x;
                }
            }
        };
    }

    public static Func2<Double, Double, Double> sumDouble() {
        return new Func2<Double, Double, Double>() {
            @Override
            public Double apply(Double acc, Double x) {
                return acc + x;
            }
        };
    }

    public static Func2<Long, Long, Long> sumLong() {
        return new Func2<Long, Long, Long>() {
            @Override
            public Long apply(Long acc, Long x) {
                return acc + x;
            }
        };
    }

    public static Func2<Float, Float, Float> sumFloat() {
        return new Func2<Float, Float, Float>() {
            @Override
            public Float apply(Float acc, Float x) {
                return acc + x;
            }
        };
    }

    public static Func2<Integer, Integer, Integer> sumInt() {
        return new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer acc, Integer x) {
                return acc + x;
            }
        };
    }
}
