/*
 * This file is part of CPE Parser.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright (c) 2018 Jeremy Long. All Rights Reserved.
 */
package us.springett.parsers.cpe.util;

import java.util.regex.Pattern;
import us.springett.parsers.cpe.values.LogicalValue;
import us.springett.parsers.cpe.values.Part;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import us.springett.parsers.cpe.exceptions.CpeEncodingException;

/**
 *
 * @author Jeremy Long
 */
public class ConvertTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Test of toWellFormed method, of class Convert.
     */
    @Test
    public void testToWellFormed() {
        exception = ExpectedException.none();
        String value = "test";
        String expResult = "test";
        String result = Convert.toWellFormed(value);
        assertEquals(expResult, result);

        value = "te$st";
        expResult = "te\\$st";
        result = Convert.toWellFormed(value);
        assertEquals(expResult, result);

        value = "test_value";
        expResult = "test\\_value";
        result = Convert.toWellFormed(value);
        assertEquals(expResult, result);

        value = "test-value";
        expResult = "test\\-value";
        result = Convert.toWellFormed(value);
        assertEquals(expResult, result);

        value = "test.value";
        expResult = "test\\.value";
        result = Convert.toWellFormed(value);
        assertEquals(expResult, result);

        value = null;
        expResult = LogicalValue.ANY.getAbbreviation();
        result = Convert.toWellFormed(value);
        assertEquals(expResult, result);

        value = LogicalValue.ANY.getAbbreviation();
        expResult = "*";
        result = Convert.toWellFormed(value);
        assertEquals(expResult, result);

        value = LogicalValue.NA.getAbbreviation();
        expResult = "-";
        result = Convert.toWellFormed(value);
        assertEquals(expResult, result);
    }

    /**
     * Test of fromWellFormed method, of class Convert.
     */
    @Test
    public void testFromWellFormed() {
        exception = ExpectedException.none();
        String value = "test";
        String expResult = "test";
        String result = Convert.fromWellFormed(value);
        assertEquals(expResult, result);

        value = "te\\$st";
        expResult = "te$st";
        result = Convert.fromWellFormed(value);
        assertEquals(expResult, result);

        value = "c\\+\\+";
        expResult = "c++";
        result = Convert.fromWellFormed(value);
        assertEquals(expResult, result);

        value = "\\\\test";
        expResult = "\\test";
        result = Convert.fromWellFormed(value);
        assertEquals(expResult, result);

        value = "test_value";
        expResult = "test_value";
        result = Convert.fromWellFormed(value);
        assertEquals(expResult, result);

        value = "test-value";
        expResult = "test-value";
        result = Convert.fromWellFormed(value);
        assertEquals(expResult, result);

        value = "test.value";
        expResult = "test.value";
        result = Convert.fromWellFormed(value);
        assertEquals(expResult, result);

        value = null;
        expResult = "*";
        result = Convert.fromWellFormed(value);
        assertEquals(expResult, result);
    }

    /**
     * Test of wellFormedToCpeUri method, of class Convert.
     *
     * @exception CpeEncodingException thrown if the CPE component is not well
     * formed
     */
    @Test
    public void testWellFormedToCpeUri() throws CpeEncodingException {
        exception = ExpectedException.none();

        String value = null;
        String expResult = "";
        String result = Convert.wellFormedToCpeUri(value);
        assertEquals(expResult, result);

        value = "*";
        expResult = "";
        result = Convert.wellFormedToCpeUri(value);
        assertEquals(expResult, result);

        value = "-";
        expResult = "-";
        result = Convert.wellFormedToCpeUri(value);
        assertEquals(expResult, result);

        value = "\\~";
        expResult = "%7e";
        result = Convert.wellFormedToCpeUri(value);
        assertEquals(expResult, result);

        value = "\\.";
        expResult = ".";
        result = Convert.wellFormedToCpeUri(value);
        assertEquals(expResult, result);

        value = "\\-";
        expResult = "-";
        result = Convert.wellFormedToCpeUri(value);
        assertEquals(expResult, result);

        value = "\\_";
        expResult = "_";
        result = Convert.wellFormedToCpeUri(value);
        assertEquals(expResult, result);

        value = "V2\\.2";
        expResult = "V2.2";
        result = Convert.wellFormedToCpeUri(value);
        assertEquals(expResult, result);

        value = "test*";
        expResult = "test%02";
        result = Convert.wellFormedToCpeUri(value);
        assertEquals(expResult, result);

        value = "test?";
        expResult = "test%01";
        result = Convert.wellFormedToCpeUri(value);
        assertEquals(expResult, result);

        value = "visual\\_c\\+\\+";
        expResult = "visual_c%2b%2b";
        result = Convert.wellFormedToCpeUri(value);
        assertEquals(expResult, result);
    }

    @Test
    public void testWellFormedToCpeUriException() throws CpeEncodingException {
        exception.expect(CpeEncodingException.class);

        String value = "test\\";
        Convert.wellFormedToCpeUri(value);
    }

    @Test
    public void testWellFormedToCpeUriException2() throws CpeEncodingException {
        exception.expect(CpeEncodingException.class);

        String value = "test:";
        Convert.wellFormedToCpeUri(value);
    }

    /**
     * Test of wellFormedToCpeUri method, of class Convert.
     */
    @Test
    public void testWellFormedToCpeUri_Part() {
        exception = ExpectedException.none();

        Part value = null;
        String expResult = "*";
        String result = Convert.wellFormedToCpeUri(value);
        assertEquals(expResult, result);
    }

    /**
     * Test of cpeUriToWellFormed method, of class Convert.
     *
     * @exception CpeEncodingException thrown if the URI is malformed
     */
    @Test
    public void testCpeUriToWellFormed() throws CpeEncodingException {
        exception = ExpectedException.none();

        String value = null;
        String expResult = "*";
        String result = Convert.cpeUriToWellFormed(value);
        assertEquals(expResult, result);

        value = "%01";
        expResult = "?";
        result = Convert.cpeUriToWellFormed(value);
        assertEquals(expResult, result);

        value = "%02";
        expResult = "*";
        result = Convert.cpeUriToWellFormed(value);
        assertEquals(expResult, result);

        value = "-";
        expResult = "-";
        result = Convert.cpeUriToWellFormed(value);
        assertEquals(expResult, result);

        value = "test-";
        expResult = "test\\-";
        result = Convert.cpeUriToWellFormed(value);
        assertEquals(expResult, result);

        value = "test_";
        expResult = "test\\_";
        result = Convert.cpeUriToWellFormed(value);
        assertEquals(expResult, result);

        value = "test.";
        expResult = "test\\.";
        result = Convert.cpeUriToWellFormed(value);
        assertEquals(expResult, result);

        value = "2.22";
        expResult = "2\\.22";
        result = Convert.cpeUriToWellFormed(value);
        assertEquals(expResult, result);

        value = "visual_c%2b%2b";
        expResult = "visual\\_c\\+\\+";
        result = Convert.cpeUriToWellFormed(value);
        assertEquals(expResult, result);
    }

    @Test
    public void testCpeUriToWellFormedException() throws CpeEncodingException {
        exception.expect(CpeEncodingException.class);

        String value = "test%";
        Convert.cpeUriToWellFormed(value);
    }

    @Test
    public void testCpeUriToWellFormedException2() throws CpeEncodingException {
        exception.expect(CpeEncodingException.class);

        String value = "test:";
        Convert.cpeUriToWellFormed(value);
    }

    /**
     * Test of wellFormedToFS method, of class Convert.
     */
    @Test
    public void testWellFormedToFS() {
        exception = ExpectedException.none();

        String value = null;
        String expResult = "*";
        String result = Convert.wellFormedToFS(value);
        assertEquals(expResult, result);

        value = "";
        expResult = "*";
        result = Convert.wellFormedToFS(value);
        assertEquals(expResult, result);

        value = "*";
        expResult = "*";
        result = Convert.wellFormedToFS(value);
        assertEquals(expResult, result);

        value = "-";
        expResult = "-";
        result = Convert.wellFormedToFS(value);
        assertEquals(expResult, result);

        value = "visual_c\\+\\+";
        //TODO - is the quoting of the underscore correct
        expResult = "visual_c\\+\\+";
        result = Convert.wellFormedToFS(value);
        assertEquals(expResult, result);

        value = "test\\:";
        expResult = "test\\:";
        result = Convert.wellFormedToFS(value);
        assertEquals(expResult, result);

        value = "1\\.2\\.3";
        expResult = "1.2.3";
        result = Convert.wellFormedToFS(value);
        assertEquals(expResult, result);

        value = "1\\-3";
        expResult = "1-3";
        result = Convert.wellFormedToFS(value);
        assertEquals(expResult, result);

        value = "1\\_3";
        expResult = "1_3";
        result = Convert.wellFormedToFS(value);
        assertEquals(expResult, result);
    }

    /**
     * Test of wellFormedToFS method, of class Convert.
     */
    @Test
    public void testWellFormedToFS_Part() {
        exception = ExpectedException.none();

        Part value = null;
        String expResult = "*";
        String result = Convert.wellFormedToFS(value);
        assertEquals(expResult, result);
    }

    /**
     * Test of fsToWellFormed method, of class Convert.
     */
    @Test
    public void testFsToWellFormed() {
        exception = ExpectedException.none();

        String value = null;
        String expResult = "*";
        String result = Convert.fsToWellFormed(value);
        assertEquals(expResult, result);

        value = "";
        expResult = "*";
        result = Convert.fsToWellFormed(value);
        assertEquals(expResult, result);

        value = "*";
        expResult = "*";
        result = Convert.fsToWellFormed(value);
        assertEquals(expResult, result);

        value = "-";
        expResult = "-";
        result = Convert.fsToWellFormed(value);
        assertEquals(expResult, result);

        value = "visual_c\\+\\+";
        expResult = "visual\\_c\\+\\+";
        result = Convert.fsToWellFormed(value);
        assertEquals(expResult, result);
    }

    /**
     * Test of wellFormedToPattern method, of class Convert.
     */
    @Test
    public void testWellFormedToPattern() {

        String value = "abc";
        Pattern expResult = Pattern.compile("abc");
        Pattern result = Convert.wellFormedToPattern(value);
        assertEquals(expResult.toString(), result.toString());

        value = "?abc?";
        expResult = Pattern.compile(".abc.");
        result = Convert.wellFormedToPattern(value);
        assertEquals(expResult.toString(), result.toString());

        value = "*?abc?*";
        expResult = Pattern.compile(".*.abc..*");
        result = Convert.wellFormedToPattern(value);
        assertEquals(expResult.toString(), result.toString());

        value = "test\\:Pattern1";
        expResult = Pattern.compile("test\\\\\\:Pattern1");
        result = Convert.wellFormedToPattern(value);
        assertEquals(expResult.toString(), result.toString());

//        String test = "test123a\\*bc\\[\\]";
//        Matcher m = expResult.matcher(test);
//        assertTrue(m.matches());
        value = "*???a\\*bc\\[\\]";
        expResult = Pattern.compile(".*...a\\\\\\*bc\\\\\\[\\\\\\]");
        result = Convert.wellFormedToPattern(value);
        assertEquals(expResult.toString(), result.toString());

    }
}
