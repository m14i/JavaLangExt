/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2011 Michael Stöckli
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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

    @SuppressWarnings("unchecked")
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

    @SuppressWarnings("unchecked")
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
