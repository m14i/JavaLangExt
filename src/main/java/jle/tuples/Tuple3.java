package jle.tuples;

/*
 * #%L
 * JavaLangExt
 * %%
 * Copyright (C) 2011 - 2013 Michael Stöckli
 * %%
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
 * #L%
 */



public class Tuple3<A, B, C> extends Tuple2<A, B> {
    private C item3;

    public static <A, B, C> Tuple3<A, B, C> make(A item1, B item2, C item3) {
        return new Tuple3<A, B, C>(item1, item2, item3);
    }

    protected Tuple3(A item1, B item2, C item3) {
        super(item1, item2);
        this.item3 = item3;
    }

    public C _3() {
        return item3;
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
                .append(")")
                .toString();
    }
}
