package jle.utils;

import java.io.PrintStream;

public class Con {

    private static PrintStream out = System.out;
    private static boolean printEnabled = true;

    public static void println(Object obj) {
        if (printEnabled) {
            out.println(obj);
        }
    }

    public static void setPrintEnabled(boolean enabled) {
        Con.printEnabled = enabled;
    }

    public static void setPrintStream(PrintStream printStream) {
        Con.out = printStream;
    }
}
