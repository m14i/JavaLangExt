package org.m14i.ext;

import org.m14i.ext.methods.Func1;
import org.m14i.ext.methods.Proc1;
import junit.framework.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;

import static org.m14i.ext.Ext.*;

public class ExtTest {
    @Test
    public void testCoalesce() throws Exception {
        String nullish = null;
        String expected = "something";
        String actual = coalesce(nullish, expected, "something dubious", null);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testIsNullOrEmpty() throws Exception {
        String nullish = null;
        String empty = "";
        String something = "something";

        Assert.assertTrue(isNullOrEmpty(nullish));
        Assert.assertTrue(isNullOrEmpty(empty));
        Assert.assertFalse(isNullOrEmpty(something));
    }

    @Test
    public void testTryParseDouble() throws Exception {
        String tenPointFive = "10.5";
        Double expected = 10.5;
        Double actual = tryParseDouble(tenPointFive);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testTryParseFloat() throws Exception {
        String tenPointFive = "10.5";
        Float expected = 10.5F;
        Float actual = tryParseFloat(tenPointFive);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testTryParseInt() throws Exception {
        String tenPointFive = "10";
        Integer expected = 10;
        Integer actual = tryParseInt(tenPointFive);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testTryParseLong() throws Exception {
        String tenPointFive = "10";
        Long expected = 10L;
        Long actual = tryParseLong(tenPointFive);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testTryWith() throws Exception {
        ByteArrayOutputStream out = tryWith(new ByteArrayOutputStream(), new Func1<ByteArrayOutputStream, ByteArrayOutputStream>() {
            @Override
            public ByteArrayOutputStream apply(ByteArrayOutputStream out) {
                out.write(1);
                out.write(2);
                out.write(3);
                out.write(4);
                return out;
            }
        });
        


        from(out.toByteArray()).each(new Proc1<Byte>() {
            @Override
            public void apply(Byte arg) {
                System.out.println(arg.byteValue());
            }
        });
    }
}
