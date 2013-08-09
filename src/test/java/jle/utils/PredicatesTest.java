package jle.utils;

import org.junit.Test;

import static jle.Ext.from;
import static org.junit.Assert.assertEquals;

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
