package org.m14i.ext.utils;

import org.m14i.ext.methods.Proc1;

import java.io.PrintStream;

public class Procedures {

    public static <T> Proc1 println() {
        return new Proc1<T>() {
            @Override
            public void apply(T arg) {
                System.out.println(arg);
            }
        };
    }

    public static <T> Proc1 print() {
        return print(System.out);
    }

    public static <T> Proc1 print(final PrintStream printer) {
        return new Proc1<T>() {
            @Override
            public void apply(T arg) {
                printer.print(arg);
            }
        };
    }

}
