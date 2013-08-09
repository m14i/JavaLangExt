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
