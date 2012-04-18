package org.m14i.ext.tuples;

public class Tuple3<A, B, C> extends Tuple2<A, B> {
    private C item3;

    public Tuple3(A item1, B item2, C item3) {
        super(item1, item2);
        this.item3 = item3;
    }

    public C get3() {
        return item3;
    }

    @Override
    public String toString() {
        return super.toString() + ":" + item3;
    }
}
