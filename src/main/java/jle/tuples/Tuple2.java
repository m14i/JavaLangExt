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
package jle.tuples;


public class Tuple2<A, B> {
    private A item1;
    private B item2;

    public static <A, B> Tuple2<A, B> make(A item1, B item2) {
        return new Tuple2<A, B>(item1, item2);
    }

    protected Tuple2(A item1, B item2) {
        this.item1 = item1;
        this.item2 = item2;
    }

    public A _1() {
        return item1;
    }

    public B _2() {
        return item2;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("(")
                .append(_1())
                .append(",")
                .append(_2())
                .append(")")
                .toString();
    }
}
