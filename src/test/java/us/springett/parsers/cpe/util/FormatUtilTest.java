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

import us.springett.parsers.cpe.values.BindValue;
import us.springett.parsers.cpe.values.Part;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jeremy Long
 */
public class FormatUtilTest {

    /**
     * Test of toWellFormed method, of class FormatUtil.
     */
    @Test
    public void testToWellFormed() {
        String value = "test";
        String expResult = "test";
        String result = FormatUtil.toWellFormed(value);
        assertEquals(expResult, result);

        value = "te$st";
        expResult = "te\\$st";
        result = FormatUtil.toWellFormed(value);
        assertEquals(expResult, result);

        value = "test_value";
        expResult = "test_value";
        result = FormatUtil.toWellFormed(value);
        assertEquals(expResult, result);

        value = "test-value";
        expResult = "test-value";
        result = FormatUtil.toWellFormed(value);
        assertEquals(expResult, result);

        value = "test.value";
        expResult = "test.value";
        result = FormatUtil.toWellFormed(value);
        assertEquals(expResult, result);

        value = null;
        expResult = BindValue.ANY.getAbbreviation();
        result = FormatUtil.toWellFormed(value);
        assertEquals(expResult, result);

        value = BindValue.ANY.getAbbreviation();
        expResult = "*";
        result = FormatUtil.toWellFormed(value);
        assertEquals(expResult, result);

        value = BindValue.NA.getAbbreviation();
        expResult = "-";
        result = FormatUtil.toWellFormed(value);
        assertEquals(expResult, result);
    }

    /**
     * Test of fromWellFormed method, of class FormatUtil.
     */
    @Test
    public void testFromWellFormed() {
        String value = "test";
        String expResult = "test";
        String result = FormatUtil.fromWellFormed(value);
        assertEquals(expResult, result);

        value = "te\\$st";
        expResult = "te$st";
        result = FormatUtil.fromWellFormed(value);
        assertEquals(expResult, result);

        value = "test_value";
        expResult = "test_value";
        result = FormatUtil.fromWellFormed(value);
        assertEquals(expResult, result);

        value = "test-value";
        expResult = "test-value";
        result = FormatUtil.fromWellFormed(value);
        assertEquals(expResult, result);

        value = "test.value";
        expResult = "test.value";
        result = FormatUtil.fromWellFormed(value);
        assertEquals(expResult, result);

        value = null;
        expResult = "*";
        result = FormatUtil.fromWellFormed(value);
        assertEquals(expResult, result);
    }

    /**
     * Test of encodeCpe22Component method, of class FormatUtil.
     */
    @Test
    public void testEncodeCpe22Component() {
        String value = null;
        String expResult = "";
        String result = FormatUtil.encodeCpe22Component(value);
        assertEquals(expResult, result);

        value = "*";
        expResult = "";
        result = FormatUtil.encodeCpe22Component(value);
        assertEquals(expResult, result);

        value = "-";
        expResult = "-";
        result = FormatUtil.encodeCpe22Component(value);
        assertEquals(expResult, result);

        value = "~";
        expResult = "%7E";
        result = FormatUtil.encodeCpe22Component(value);
        assertEquals(expResult, result);

        value = "visual_c++";
        expResult = "visual_c%2B%2B";
        result = FormatUtil.encodeCpe22Component(value);
        assertEquals(expResult, result);
    }

    /**
     * Test of encodeCpe22Component method, of class FormatUtil.
     */
    @Test
    public void testEncodeCpe22ComponentPart() {
        Part value = null;
        String expResult = "*";
        String result = FormatUtil.encodeCpe22Component(value);
        assertEquals(expResult, result);
    }

    /**
     * Test of decodeCpe22Component method, of class FormatUtil.
     */
    @Test
    public void testDecodeCpe22Component() {

        String value = null;
        String expResult = "*";
        String result = FormatUtil.decodeCpe22Component(value);
        assertEquals(expResult, result);

        value = "*";
        expResult = "*";
        result = FormatUtil.decodeCpe22Component(value);
        assertEquals(expResult, result);

        value = "-";
        expResult = "-";
        result = FormatUtil.decodeCpe22Component(value);
        assertEquals(expResult, result);

        value = "visual_c%2b%2b";
        expResult = "visual_c++";
        result = FormatUtil.decodeCpe22Component(value);
        assertEquals(expResult, result);
    }

    /**
     * Test of encodeCpe23Component method, of class FormatUtil.
     */
    @Test
    public void testEncodeCpe23Component() {
        String value = null;
        String expResult = "*";
        String result = FormatUtil.encodeCpe23Component(value);
        assertEquals(expResult, result);

        value = "";
        expResult = "*";
        result = FormatUtil.encodeCpe23Component(value);
        assertEquals(expResult, result);

        value = "*";
        expResult = "*";
        result = FormatUtil.encodeCpe23Component(value);
        assertEquals(expResult, result);

        value = "-";
        expResult = "-";
        result = FormatUtil.encodeCpe23Component(value);
        assertEquals(expResult, result);

        value = "visual_c++";
        //TODO - is the quoting of the underscore correct
        expResult = "visual_c\\\\+\\\\+";
        result = FormatUtil.encodeCpe23Component(value);
        assertEquals(expResult, result);

        value = "test:";
        expResult = "test\\\\:";
        result = FormatUtil.encodeCpe23Component(value);
        assertEquals(expResult, result);
    }

    /**
     * Test of encodeCpe23Component method, of class FormatUtil.
     */
    @Test
    public void testEncodeCpe23ComponentPart() {
        Part value = null;
        String expResult = "*";
        String result = FormatUtil.encodeCpe23Component(value);
        assertEquals(expResult, result);
    }

    /**
     * Test of decodeCpe23Component method, of class FormatUtil.
     */
    @Test
    public void testDecodeCpe23Component() {
        String value = null;
        String expResult = "*";
        String result = FormatUtil.decodeCpe23Component(value);
        assertEquals(expResult, result);

        value = "";
        expResult = "*";
        result = FormatUtil.decodeCpe23Component(value);
        assertEquals(expResult, result);

        value = "*";
        expResult = "*";
        result = FormatUtil.decodeCpe23Component(value);
        assertEquals(expResult, result);

        value = "-";
        expResult = "-";
        result = FormatUtil.decodeCpe23Component(value);
        assertEquals(expResult, result);

        value = "visual_c\\\\+\\\\+";
        expResult = "visual_c++";
        result = FormatUtil.decodeCpe23Component(value);
        assertEquals(expResult, result);
    }

}
