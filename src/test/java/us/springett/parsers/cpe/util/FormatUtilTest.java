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
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import us.springett.parsers.cpe.exceptions.CpeEncodingException;
import us.springett.parsers.cpe.exceptions.CpeValidationException;

/**
 *
 * @author Jeremy Long
 */
public class FormatUtilTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Test of toWellFormed method, of class FormatUtil.
     */
    @Test
    public void testToWellFormed() {
        exception = ExpectedException.none();
        String value = "test";
        String expResult = "test";
        String result = FormatUtil.toWellFormed(value);
        assertEquals(expResult, result);

        value = "te$st";
        expResult = "te\\$st";
        result = FormatUtil.toWellFormed(value);
        assertEquals(expResult, result);

        value = "test_value";
        expResult = "test\\_value";
        result = FormatUtil.toWellFormed(value);
        assertEquals(expResult, result);

        value = "test-value";
        expResult = "test\\-value";
        result = FormatUtil.toWellFormed(value);
        assertEquals(expResult, result);

        value = "test.value";
        expResult = "test\\.value";
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
        exception = ExpectedException.none();
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
     * Test of transformWfsToCpeUriComponent method, of class FormatUtil.
     *
     * @exception CpeEncodingException thrown if the CPE component is not well
     * formed
     */
    @Test
    public void testTransformWfsToCpeUriComponent() throws CpeEncodingException {
        exception = ExpectedException.none();

        String value = null;
        String expResult = "";
        String result = FormatUtil.transformWfsToCpeUriComponent(value);
        assertEquals(expResult, result);

        value = "*";
        expResult = "";
        result = FormatUtil.transformWfsToCpeUriComponent(value);
        assertEquals(expResult, result);

        value = "-";
        expResult = "-";
        result = FormatUtil.transformWfsToCpeUriComponent(value);
        assertEquals(expResult, result);

        value = "\\~";
        expResult = "%7e";
        result = FormatUtil.transformWfsToCpeUriComponent(value);
        assertEquals(expResult, result);

        value = "\\.";
        expResult = ".";
        result = FormatUtil.transformWfsToCpeUriComponent(value);
        assertEquals(expResult, result);

        value = "\\-";
        expResult = "-";
        result = FormatUtil.transformWfsToCpeUriComponent(value);
        assertEquals(expResult, result);

        value = "\\_";
        expResult = "_";
        result = FormatUtil.transformWfsToCpeUriComponent(value);
        assertEquals(expResult, result);

        value = "V2\\.2";
        expResult = "V2.2";
        result = FormatUtil.transformWfsToCpeUriComponent(value);
        assertEquals(expResult, result);

        value = "test*";
        expResult = "test%02";
        result = FormatUtil.transformWfsToCpeUriComponent(value);
        assertEquals(expResult, result);

        value = "test?";
        expResult = "test%01";
        result = FormatUtil.transformWfsToCpeUriComponent(value);
        assertEquals(expResult, result);

        value = "visual\\_c\\+\\+";
        expResult = "visual_c%2b%2b";
        result = FormatUtil.transformWfsToCpeUriComponent(value);
        assertEquals(expResult, result);
    }

    @Test
    public void testTransformWfsToCpeUriComponentException() throws CpeEncodingException {
        exception.expect(CpeEncodingException.class);

        String value = "test\\";
        FormatUtil.transformWfsToCpeUriComponent(value);
    }

    @Test
    public void testTransformWfsToCpeUriComponentException2() throws CpeEncodingException {
        exception.expect(CpeEncodingException.class);

        String value = "test:";
        FormatUtil.transformWfsToCpeUriComponent(value);
    }

    /**
     * Test of transformWfsToCpeUriComponent method, of class FormatUtil.
     */
    @Test
    public void testTransformWfsToCpeUriComponentPart() {
        exception = ExpectedException.none();

        Part value = null;
        String expResult = "*";
        String result = FormatUtil.encodeCpe22Component(value);
        assertEquals(expResult, result);
    }

    /**
     * Test of transformCpeUriComponentToWfs method, of class FormatUtil.
     *
     * @exception CpeEncodingException thrown if the URI is malformed
     */
    @Test
    public void testTransformCpeUriComponentToWfs() throws CpeEncodingException {
        exception = ExpectedException.none();

        String value = null;
        String expResult = "*";
        String result = FormatUtil.transformCpeUriComponentToWfs(value);
        assertEquals(expResult, result);

        value = "%01";
        expResult = "?";
        result = FormatUtil.transformCpeUriComponentToWfs(value);
        assertEquals(expResult, result);

        value = "%02";
        expResult = "*";
        result = FormatUtil.transformCpeUriComponentToWfs(value);
        assertEquals(expResult, result);

        value = "-";
        expResult = "-";
        result = FormatUtil.transformCpeUriComponentToWfs(value);
        assertEquals(expResult, result);

        value = "test-";
        expResult = "test\\-";
        result = FormatUtil.transformCpeUriComponentToWfs(value);
        assertEquals(expResult, result);

        value = "test_";
        expResult = "test\\_";
        result = FormatUtil.transformCpeUriComponentToWfs(value);
        assertEquals(expResult, result);

        value = "test.";
        expResult = "test\\.";
        result = FormatUtil.transformCpeUriComponentToWfs(value);
        assertEquals(expResult, result);

        value = "2.22";
        expResult = "2\\.22";
        result = FormatUtil.transformCpeUriComponentToWfs(value);
        assertEquals(expResult, result);

        value = "visual_c%2b%2b";
        expResult = "visual\\_c\\+\\+";
        result = FormatUtil.transformCpeUriComponentToWfs(value);
        assertEquals(expResult, result);
    }

    @Test
    public void testTransformCpeUriComponentToWfsException() throws CpeEncodingException {
        exception.expect(CpeEncodingException.class);

        String value = "test%";
        FormatUtil.transformCpeUriComponentToWfs(value);
    }

    @Test
    public void testTransformCpeUriComponentToWfsException2() throws CpeEncodingException {
        exception.expect(CpeEncodingException.class);

        String value = "test:";
        FormatUtil.transformCpeUriComponentToWfs(value);
    }

    /**
     * Test of transfromWfsToFS method, of class FormatUtil.
     */
    @Test
    public void testTransfromWfsToFS() {
        exception = ExpectedException.none();

        String value = null;
        String expResult = "*";
        String result = FormatUtil.transfromWfsToFS(value);
        assertEquals(expResult, result);

        value = "";
        expResult = "*";
        result = FormatUtil.transfromWfsToFS(value);
        assertEquals(expResult, result);

        value = "*";
        expResult = "*";
        result = FormatUtil.transfromWfsToFS(value);
        assertEquals(expResult, result);

        value = "-";
        expResult = "-";
        result = FormatUtil.transfromWfsToFS(value);
        assertEquals(expResult, result);

        value = "visual_c\\+\\+";
        //TODO - is the quoting of the underscore correct
        expResult = "visual_c\\\\+\\\\+";
        result = FormatUtil.transfromWfsToFS(value);
        assertEquals(expResult, result);

        value = "test\\:";
        expResult = "test\\\\:";
        result = FormatUtil.transfromWfsToFS(value);
        assertEquals(expResult, result);
    }

    /**
     * Test of transfromWfsToFS method, of class FormatUtil.
     */
    @Test
    public void testTransfromWfsToFSPart() {
        exception = ExpectedException.none();

        Part value = null;
        String expResult = "*";
        String result = FormatUtil.encodeCpe23Component(value);
        assertEquals(expResult, result);
    }

    /**
     * Test of transfromFsToWfs method, of class FormatUtil.
     */
    @Test
    public void testTransfromFsToWfs() {
        exception = ExpectedException.none();

        String value = null;
        String expResult = "*";
        String result = FormatUtil.transfromFsToWfs(value);
        assertEquals(expResult, result);

        value = "";
        expResult = "*";
        result = FormatUtil.transfromFsToWfs(value);
        assertEquals(expResult, result);

        value = "*";
        expResult = "*";
        result = FormatUtil.transfromFsToWfs(value);
        assertEquals(expResult, result);

        value = "-";
        expResult = "-";
        result = FormatUtil.transfromFsToWfs(value);
        assertEquals(expResult, result);

        value = "visual_c\\\\+\\\\+";
        expResult = "visual\\_c\\+\\+";
        result = FormatUtil.transfromFsToWfs(value);
        assertEquals(expResult, result);
    }
}
