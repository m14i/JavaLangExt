package org.m14i.ext.tuples;

public class Tuple4<A, B, C, D> extends Tuple3<A, B, C> {

    private D item4;

    public Tuple4(A item1, B item2, C item3, D item4) {
        super(item1, item2, item3);
        this.item4 = item4;
    }

    public D get4() {
        return item4;
    }

    @Override
    public String toString() {
        return super.toString() + ":" + item4;
    }
}
