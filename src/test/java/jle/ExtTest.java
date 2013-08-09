package jle;

import jle.utils.Con;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static jle.Ext.*;
import static org.junit.Assert.*;

public class ExtTest {

    @Before
    public void setUp() {
        Con.setPrintEnabled(false);
    }

    @Test
    public void testCoalesce() throws Exception {
        String nullish = null;
        String expected = "something";
        String actual = coalesce(nullish, expected, "something dubious", null);

        assertEquals(expected, actual);
    }

    @Test
    public void testIsNullOrEmptyString() throws Exception {
        String nullish = null;
        String empty = "";
        String something = "something";

        assertTrue(isNullOrEmpty(nullish));
        assertTrue(isNullOrEmpty(empty));
        assertFalse(isNullOrEmpty(something));
    }

    @Test
    public void testIsNullOrEmptyMap() throws Exception {
        Map nullish = null;
        Map empty = new HashMap();
        Map something = new HashMap() {{
            put("me", "here");
        }};

        assertTrue(isNullOrEmpty(nullish));
        assertTrue(isNullOrEmpty(empty));
        assertFalse(isNullOrEmpty(something));
    }

    @Test
    public void testIsNullOrEmptyCollection() throws Exception {
        Collection nullish = null;
        Collection empty = new ArrayList();
        Collection something = new ArrayList() {{
            add("me");
        }};

        assertTrue(isNullOrEmpty(nullish));
        assertTrue(isNullOrEmpty(empty));
        assertFalse(isNullOrEmpty(something));
    }

    @Test
    public void testIsNullOrEmptyArray() throws Exception {
        Integer[] nullish = null;
        Integer[] empty = new Integer[0];
        Integer[] something = {1, 2};

        assertTrue(isNullOrEmpty(nullish));
        assertTrue(isNullOrEmpty(empty));
        assertFalse(isNullOrEmpty(something));
    }

    @Test
    public void testStrForInt() throws Exception {
        assertEquals("4", str(4));
    }
}
