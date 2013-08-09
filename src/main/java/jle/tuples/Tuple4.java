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
