package jle.ext.utils;

import static jle.ext.Ext.from;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PredicatesTest {
    @Test
    @SuppressWarnings("unchecked")
    public void testIsNull() throws Exception {
        long count = from("a", "b", null, "d").count(Predicates.<String>is(null));

        assertEquals(1, count);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testIsNotNull() throws Exception {
        String result = from("a", "b", null, "d").filter(Predicates.<String>isNot(null)).join("/");

        assertEquals("a/b/d", result);
    }
}
