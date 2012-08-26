package jle.utils;

import jle.methods.Proc;

import java.io.PrintStream;

public class Procedures {

    public static <T> Proc println() {
        return new Proc<T>() {
            @Override
            public void apply(T arg) {
                System.out.println(arg);
            }
        };
    }

    public static <T> Proc print() {
        return print(System.out);
    }

    public static <T> Proc print(final PrintStream printer) {
        return new Proc<T>() {
            @Override
            public void apply(T arg) {
                printer.print(arg);
            }
        };
    }

}
