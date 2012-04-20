package org.m14i.ext.utils;

import org.m14i.ext.methods.Pred;

public class Predicates {

    public static Pred isNull() {
        return new Pred() {
            @Override
            public Boolean apply(Object x) {
                return null == x;
            }
        };
    }

    public static Pred isNotNull() {
        return new Pred() {
            @Override
            public Boolean apply(Object x) {
                return null != x;
            }
        };
    }
}
