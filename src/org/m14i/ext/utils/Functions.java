package org.m14i.ext.utils;

import org.m14i.ext.methods.Func2;

public class Functions {
    
    public static Func2<String, String, String> join(final String separator) {
        return new Func2<String, String, String>() {

            boolean first = true;

            @Override
            public String apply(String carry, String item) {
                if (first) {
                    first = false;
                    return carry + item;
                } else {
                    return carry + separator + item;
                }
            }
        };
    }

    public static Func2<Double, Double, Double> sumDouble() {
        return new Func2<Double, Double, Double>() {
            @Override
            public Double apply(Double carry, Double item) {
                return carry + item;
            }
        };
    }

    public static Func2<Long, Long, Long> sumLong() {
        return new Func2<Long, Long, Long>() {
            @Override
            public Long apply(Long carry, Long item) {
                return carry + item;
            }
        };
    }

    public static Func2<Float, Float, Float> sumFloat() {
        return new Func2<Float, Float, Float>() {
            @Override
            public Float apply(Float carry, Float item) {
                return carry + item;
            }
        };
    }

    public static Func2<Integer, Integer, Integer> sumInt() {
        return new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer carry, Integer item) {
                return carry + item;
            }
        };
    }
}
