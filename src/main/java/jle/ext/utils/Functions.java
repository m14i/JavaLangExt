package jle.ext.utils;

import jle.ext.methods.Fn2;

public class Functions {

    public static Fn2<StringBuilder, String, StringBuilder> join(final String separator) {
        return new Fn2<StringBuilder, String, StringBuilder>() {

            boolean first = true;

            @Override
            public StringBuilder apply(StringBuilder acc, String x) {
                if (first) {
                    first = false;
                    return acc.append(x);
                } else {
                    return acc.append(separator).append(x);
                }
            }
        };
    }

    public static Fn2<Double, Double, Double> sumDouble() {
        return new Fn2<Double, Double, Double>() {
            @Override
            public Double apply(Double acc, Double x) {
                return acc + x;
            }
        };
    }

    public static Fn2<Long, Long, Long> sumLong() {
        return new Fn2<Long, Long, Long>() {
            @Override
            public Long apply(Long acc, Long x) {
                return acc + x;
            }
        };
    }

    public static Fn2<Float, Float, Float> sumFloat() {
        return new Fn2<Float, Float, Float>() {
            @Override
            public Float apply(Float acc, Float x) {
                return acc + x;
            }
        };
    }

    public static Fn2<Integer, Integer, Integer> sumInt() {
        return new Fn2<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer acc, Integer x) {
                return acc + x;
            }
        };
    }
}
