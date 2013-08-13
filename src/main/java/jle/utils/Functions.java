/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2011 Michael Stöckli
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package jle.utils;


import jle.methods.Fn2;

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
