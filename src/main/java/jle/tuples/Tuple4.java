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


public class Tuple4<A, B, C, D> extends Tuple3<A, B, C> {

    private D item4;

    public static <A, B, C, D> Tuple4<A, B, C, D> make(A item1, B item2, C item3, D item4) {
        return new Tuple4<A, B, C, D>(item1, item2, item3, item4);
    }

    protected Tuple4(A item1, B item2, C item3, D item4) {
        super(item1, item2, item3);
        this.item4 = item4;
    }

    public D _4() {
        return item4;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("(")
                .append(_1())
                .append(",")
                .append(_2())
                .append(",")
                .append(_3())
                .append(",")
                .append(_4())
                .append(")")
                .toString();
    }
}
