package jle.tuples;

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
