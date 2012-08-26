package jle.ext;

import static jle.ext.Ext.coalesce;
import static jle.ext.Ext.humanize;
import static jle.ext.Ext.isNullOrEmpty;
import static jle.ext.Ext.str;
import static jle.ext.Ext.tryParseDouble;
import static jle.ext.Ext.tryParseFloat;
import static jle.ext.Ext.tryParseInt;
import static jle.ext.Ext.tryParseLong;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ExtTest {
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
      Integer[] something = { 1, 2 };

      assertTrue(isNullOrEmpty(nullish));
      assertTrue(isNullOrEmpty(empty));
      assertFalse(isNullOrEmpty(something));
   }

   @Test
   public void testTryParseDouble() throws Exception {
      String tenPointFive = "10.5";
      Double expected = 10.5;
      Double actual = tryParseDouble(tenPointFive, 0);

      assertEquals(expected, actual);
   }

   @Test
   public void testTryParseDoubleFallsBackToDefault() throws Exception {
      String tenPointFive = "10.5.0";
      Double expected = 0.0;
      Double actual = tryParseDouble(tenPointFive, 0);

      assertEquals(expected, actual);
   }

   @Test
   public void testTryParseFloat() throws Exception {
      String tenPointFive = "10.5";
      Float expected = 10.5F;
      Float actual = tryParseFloat(tenPointFive, 0);

      assertEquals(expected, actual);
   }

   @Test
   public void testTryParseFloatFallsBackToDefault() throws Exception {
      String tenPointFive = "10.5.0";
      Float expected = 0.0F;
      Float actual = tryParseFloat(tenPointFive, 0);

      assertEquals(expected, actual);
   }

   @Test
   public void testTryParseInt() throws Exception {
      String ten = "10";
      Integer expected = 10;
      Integer actual = tryParseInt(ten, 0);

      assertEquals(expected, actual);
   }

   @Test
   public void testTryParseIntFallsBackToDefault() throws Exception {
      String invalid = "TEN";
      Integer expected = 10;
      Integer actual = tryParseInt(invalid, 10);

      assertEquals(expected, actual);
   }

   @Test
   public void testTryParseIntFallsBackToDefaultWithNullString() throws Exception {
      String str = null;
      Integer expected = 10;
      Integer actual = tryParseInt(str, 10);

      assertEquals(expected, actual);
   }

   @Test
   public void testTryParseLong() throws Exception {
      String tenPointFive = "10";
      Long expected = 10L;
      Long actual = tryParseLong(tenPointFive, 0);

      assertEquals(expected, actual);
   }

   @Test
   public void testTryParseLongFallsBackToDefault() throws Exception {
      String tenPointFive = "10-";
      Long expected = 0L;
      Long actual = tryParseLong(tenPointFive, 0);

      assertEquals(expected, actual);
   }

   @Test
   public void testStrForInt() throws Exception {
      assertEquals("4", str(4));
   }

   @Test
   public void testHumanizeCaps() throws Exception {
      assertEquals("Hello everybody", humanize("HELLO_EVERYBODY"));
      assertEquals("Hello everybody", humanize("_HELLO_EVERYBODY"));
      assertEquals("Hello everybody", humanize("_HELLO_EVERYBODY_"));

   }

   @Test
   public void testHumanizeCamelCase() throws Exception {
      assertEquals("Hello everybody", humanize("HelloEverybody"));
      assertEquals("hello everybody", humanize("helloEverybody"));
   }

   @Test
   public void testHumanizeMixed() throws Exception {
      assertEquals("Hello everybody boo yah", humanize("HelloEverybody_boo_YAH"));
   }
}
