package org.m14i.ext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.m14i.ext.Ext.coalesce;
import static org.m14i.ext.Ext.from;
import static org.m14i.ext.Ext.isNullOrEmpty;
import static org.m14i.ext.Ext.tryParseDouble;
import static org.m14i.ext.Ext.tryParseFloat;
import static org.m14i.ext.Ext.tryParseInt;
import static org.m14i.ext.Ext.tryParseLong;
import static org.m14i.ext.Ext.tryWith;

import org.junit.Test;
import org.m14i.ext.methods.Fn1;
import org.m14i.ext.methods.Proc;

import java.io.ByteArrayOutputStream;

public class ExtTest {
    @Test
    public void testCoalesce() throws Exception {
        String nullish = null;
        String expected = "something";
        String actual = coalesce(nullish, expected, "something dubious", null);

        assertEquals(expected, actual);
    }

    @Test
    public void testIsNullOrEmpty() throws Exception {
        String nullish = null;
        String empty = "";
        String something = "something";

        assertTrue(isNullOrEmpty(nullish));
        assertTrue(isNullOrEmpty(empty));
        assertFalse(isNullOrEmpty(something));
    }

    @Test
    public void testTryParseDouble() throws Exception {
        String tenPointFive = "10.5";
        Double expected = 10.5;
        Double actual = tryParseDouble(tenPointFive);

        assertEquals(expected, actual);
    }

    @Test
    public void testTryParseFloat() throws Exception {
        String tenPointFive = "10.5";
        Float expected = 10.5F;
        Float actual = tryParseFloat(tenPointFive);

        assertEquals(expected, actual);
    }

    @Test
    public void testTryParseInt() throws Exception {
        String tenPointFive = "10";
        Integer expected = 10;
        Integer actual = tryParseInt(tenPointFive);

        assertEquals(expected, actual);
    }

    @Test
    public void testTryParseLong() throws Exception {
        String tenPointFive = "10";
        Long expected = 10L;
        Long actual = tryParseLong(tenPointFive);

        assertEquals(expected, actual);
    }

    @Test
    public void testTryWith() throws Exception {
        ByteArrayOutputStream out = tryWith(new ByteArrayOutputStream(), new Fn1<ByteArrayOutputStream, ByteArrayOutputStream>() {
            @Override
            public ByteArrayOutputStream apply(ByteArrayOutputStream out) {
                out.write(1);
                out.write(2);
                out.write(3);
                out.write(4);
                return out;
            }
        });



        from(out.toByteArray()).each(new Proc<Byte>() {
            @Override
            public void apply(Byte arg) {
                System.out.println(arg.byteValue());
            }
        });
    }
}
