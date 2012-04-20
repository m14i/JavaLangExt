package org.m14i.ext.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.m14i.ext.Ext.from;
import static org.m14i.ext.utils.Predicates.isNotNull;
import static org.m14i.ext.utils.Predicates.isNull;

public class PredicatesTest {
    @Test
    @SuppressWarnings("unchecked")
    public void testIsNull() throws Exception {
        long count = from("a", "b", null, "d").count(isNull());

        assertEquals(1, count);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testIsNotNull() throws Exception {
        String result = from("a", "b", null, "d").filter(isNotNull()).join("/");

        assertEquals("a/b/d", result);
    }
}
