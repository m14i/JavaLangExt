package jle.ext.utils;

import jle.ext.methods.Pred;

public class Predicates {

    public static <T> Pred<T> isNot(final T item) {
        return new Pred<T>() {
            @Override
            public Boolean apply(T x) {
                return x != item;
            }
        };
    }

    public static <T> Pred<T> is(final T item) {
        return new Pred<T>() {
            @Override
            public Boolean apply(T x) {
                return x == item;
            }
        };
    }

    public static <T> Pred<T> invert(final Pred<T> predicate) {
        return new Pred<T>() {
            @Override
            public Boolean apply(T x) {
                return !predicate.apply(x);
            }
        };
    }
}
