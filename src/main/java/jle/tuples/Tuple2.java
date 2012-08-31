package jle.tuples;

public class Tuple2<A, B> {
    private A item1;
    private B item2;

    public Tuple2(A item1, B item2) {
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
        return "(" + item1 + "," + item2 + ")";
    }
}