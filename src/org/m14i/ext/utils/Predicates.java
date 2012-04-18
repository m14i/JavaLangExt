package org.m14i.ext.utils;

import org.m14i.ext.methods.Pred1;

public class Predicates {

    public static Pred1 isNull() {
        return new Pred1() {
            @Override
            public Boolean apply(Object x) {
                return null == x;
            }
        };
    }

    public static Pred1 isNotNull() {
        return new Pred1() {
            @Override
            public Boolean apply(Object x) {
                return null != x;
            }
        };
    }
}
