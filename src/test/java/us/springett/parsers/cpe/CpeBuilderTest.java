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
package us.springett.parsers.cpe;

import us.springett.parsers.cpe.exceptions.CpeParsingException;
import us.springett.parsers.cpe.values.Part;
import us.springett.parsers.cpe.values.BindValue;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.*;

/**
 * Test the CPE Builder.
 *
 * @author Jeremy Long
 */
public class CpeBuilderTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Test of part method, of class CpeBuilder.
     *
     * @throws Exception thrown if there is an error
     */
    @Test
    public void testPart_Part() throws Exception {
        exception = ExpectedException.none();

        Part part = Part.NA;
        CpeBuilder instance = new CpeBuilder();
        String expResult = "-";
        Cpe result = instance.part(part).build();
        assertEquals(expResult, result.getPart().getAbbreviation());
    }

    /**
     * Test of part method, of class CpeBuilder.
     *
     * @throws Exception thrown if there is a parsing error
     */
    @Test
    public void testPart_String() throws Exception {
        exception = ExpectedException.none();

        String part = "-";
        CpeBuilder instance = new CpeBuilder();
        String expResult = "-";
        Cpe result = instance.part(part).build();
        assertEquals(expResult, result.getPart().getAbbreviation());
    }

    /**
     * Test of part method, of class CpeBuilder.
     *
     * @throws Exception thrown if there is a parsing error
     */
    @Test
    public void testPart_StringInvalid() throws Exception {
        exception.expect(CpeParsingException.class);
        String part = "x";
        CpeBuilder instance = new CpeBuilder();
        instance.part(part);
    }

    /**
     * Test of vendor method, of class CpeBuilder.
     *
     * @throws Exception thrown if there is a parsing error
     */
    @Test
    public void testVendor_String() throws Exception {
        exception = ExpectedException.none();

        String vendor = "vendor";
        CpeBuilder instance = new CpeBuilder();
        String expResult = "vendor";
        Cpe result = instance.vendor(vendor).build();
        assertEquals(expResult, result.getVendor());
    }

    /**
     * Test of product method, of class CpeBuilder.
     *
     * @throws Exception thrown if there is a parsing error
     */
    @Test
    public void testProduct_String() throws Exception {
        exception = ExpectedException.none();

        String product = "product";
        CpeBuilder instance = new CpeBuilder();
        String expResult = "product";
        Cpe result = instance.product(product).build();
        assertEquals(expResult, result.getProduct());
    }

    /**
     * Test of version method, of class CpeBuilder.
     *
     * @throws Exception thrown if there is a parsing error
     */
    @Test
    public void testVersion_String() throws Exception {
        exception = ExpectedException.none();

        String version = "version";
        CpeBuilder instance = new CpeBuilder();
        String expResult = "version";
        Cpe result = instance.version(version).build();
        assertEquals(expResult, result.getVersion());
    }

    /**
     * Test of update method, of class CpeBuilder.
     *
     * @throws Exception thrown if there is a parsing error
     */
    @Test
    public void testUpdate_String() throws Exception {
        exception = ExpectedException.none();

        String update = "update";
        CpeBuilder instance = new CpeBuilder();
        String expResult = "update";
        Cpe result = instance.update(update).build();
        assertEquals(expResult, result.getUpdate());
    }

    /**
     * Test of edition method, of class CpeBuilder.
     *
     * @throws Exception thrown if there is a parsing error
     */
    @Test
    public void testEdition_String() throws Exception {
        exception = ExpectedException.none();

        String edition = "edition";
        CpeBuilder instance = new CpeBuilder();
        String expResult = "edition";
        Cpe result = instance.edition(edition).build();
        assertEquals(expResult, result.getEdition());
    }

    /**
     * Test of language method, of class CpeBuilder.
     *
     * @throws Exception thrown if there is a parsing error
     */
    @Test
    public void testLanguage_String() throws Exception {
        exception = ExpectedException.none();

        String language = "language";
        CpeBuilder instance = new CpeBuilder();
        String expResult = "language";
        Cpe result = instance.language(language).build();
        assertEquals(expResult, result.getLanguage());
    }

    /**
     * Test of swEdition method, of class CpeBuilder.
     *
     * @throws Exception thrown if there is a parsing error
     */
    @Test
    public void testSwEdition_String() throws Exception {
        exception = ExpectedException.none();

        String swEdition = "swEdition";
        CpeBuilder instance = new CpeBuilder();
        String expResult = "swEdition";
        Cpe result = instance.swEdition(swEdition).build();
        assertEquals(expResult, result.getSwEdition());
    }

    /**
     * Test of targetSw method, of class CpeBuilder.
     *
     * @throws Exception thrown if there is a parsing error
     */
    @Test
    public void testTargetSw_String() throws Exception {
        exception = ExpectedException.none();

        String targetSw = "targetSw";
        CpeBuilder instance = new CpeBuilder();
        String expResult = "targetSw";
        Cpe result = instance.targetSw(targetSw).build();
        assertEquals(expResult, result.getTargetSw());
    }

    /**
     * Test of targetHw method, of class CpeBuilder.
     *
     * @throws Exception thrown if there is a parsing error
     */
    @Test
    public void testTargetHw_String() throws Exception {
        exception = ExpectedException.none();

        String targetHw = "targetHw";
        CpeBuilder instance = new CpeBuilder();
        String expResult = "targetHw";
        Cpe result = instance.targetHw(targetHw).build();
        assertEquals(expResult, result.getTargetHw());
    }

    /**
     * Test of other method, of class CpeBuilder.
     *
     * @throws Exception thrown if there is a parsing error
     */
    @Test
    public void testOther_String() throws Exception {
        exception = ExpectedException.none();

        String other = "other";
        CpeBuilder instance = new CpeBuilder();
        String expResult = "other";
        Cpe result = instance.other(other).build();
        assertEquals(expResult, result.getOther());
    }

    /**
     * Test of vendor method, of class CpeBuilder.
     *
     * @throws Exception thrown if there is an error
     */
    @Test
    public void testVendor_BindValue() throws Exception {
        exception = ExpectedException.none();

        BindValue vendor = BindValue.NA;
        CpeBuilder instance = new CpeBuilder();
        String expResult = "-";
        Cpe result = instance.vendor(vendor).build();
        assertEquals(expResult, result.getVendor());
    }

    /**
     * Test of product method, of class CpeBuilder.
     *
     * @throws Exception thrown if there is an error
     */
    @Test
    public void testProduct_BindValue() throws Exception {
        exception = ExpectedException.none();

        BindValue product = BindValue.NA;
        CpeBuilder instance = new CpeBuilder();
        String expResult = "-";
        Cpe result = instance.product(product).build();
        assertEquals(expResult, result.getProduct());
    }

    /**
     * Test of version method, of class CpeBuilder.
     *
     * @throws Exception thrown if there is an error
     */
    @Test
    public void testVersion_BindValue() throws Exception {
        exception = ExpectedException.none();

        BindValue version = BindValue.NA;
        CpeBuilder instance = new CpeBuilder();
        String expResult = "-";
        Cpe result = instance.version(version).build();
        assertEquals(expResult, result.getVersion());
    }

    /**
     * Test of update method, of class CpeBuilder.
     *
     * @throws Exception thrown if there is an error
     */
    @Test
    public void testUpdate_BindValue() throws Exception {
        exception = ExpectedException.none();

        BindValue update = BindValue.NA;
        CpeBuilder instance = new CpeBuilder();
        String expResult = "-";
        Cpe result = instance.update(update).build();
        assertEquals(expResult, result.getUpdate());
    }

    /**
     * Test of edition method, of class CpeBuilder.
     *
     * @throws Exception thrown if there is an error
     */
    @Test
    public void testEdition_BindValue() throws Exception {
        exception = ExpectedException.none();

        BindValue edition = BindValue.NA;
        CpeBuilder instance = new CpeBuilder();
        String expResult = "-";
        Cpe result = instance.edition(edition).build();
        assertEquals(expResult, result.getEdition());
    }

    /**
     * Test of language method, of class CpeBuilder.
     *
     * @throws Exception thrown if there is an error
     */
    @Test
    public void testLanguage_BindValue() throws Exception {
        exception = ExpectedException.none();

        BindValue language = BindValue.NA;
        CpeBuilder instance = new CpeBuilder();
        String expResult = "-";
        Cpe result = instance.language(language).build();
        assertEquals(expResult, result.getLanguage());
    }

    /**
     * Test of swEdition method, of class CpeBuilder.
     *
     * @throws Exception thrown if there is an error
     */
    @Test
    public void testSwEdition_BindValue() throws Exception {
        exception = ExpectedException.none();

        BindValue swEdition = BindValue.NA;
        CpeBuilder instance = new CpeBuilder();
        String expResult = "-";
        Cpe result = instance.swEdition(swEdition).build();
        assertEquals(expResult, result.getSwEdition());
    }

    /**
     * Test of targetSw method, of class CpeBuilder.
     *
     * @throws Exception thrown if there is an error
     */
    @Test
    public void testTargetSw_BindValue() throws Exception {
        exception = ExpectedException.none();

        BindValue targetSw = BindValue.NA;
        CpeBuilder instance = new CpeBuilder();
        String expResult = "-";
        Cpe result = instance.targetSw(targetSw).build();
        assertEquals(expResult, result.getTargetSw());
    }

    /**
     * Test of targetHw method, of class CpeBuilder.
     *
     * @throws Exception thrown if there is an error
     */
    @Test
    public void testTargetHw_BindValue() throws Exception {
        exception = ExpectedException.none();

        BindValue targetHw = BindValue.NA;
        CpeBuilder instance = new CpeBuilder();
        String expResult = "-";
        Cpe result = instance.targetHw(targetHw).build();
        assertEquals(expResult, result.getTargetHw());
    }

    /**
     * Test of other method, of class CpeBuilder.
     *
     * @throws Exception thrown if there is an error
     */
    @Test
    public void testOther_BindValue() throws Exception {
        exception = ExpectedException.none();

        BindValue other = BindValue.NA;
        CpeBuilder instance = new CpeBuilder();
        String expResult = "-";
        Cpe result = instance.other(other).build();
        assertEquals(expResult, result.getOther());
    }

    /**
     * Test of build method, of class CpeBuilder.
     *
     * @throws Exception thrown if there is an error
     */
    @Test
    public void testBuild() throws Exception {
        exception = ExpectedException.none();

        CpeBuilder instance = new CpeBuilder();
        Cpe expResult = new Cpe(Part.ANY, "*", "*", "*", "*", "*", "*", "*", "*", "*", "*");
        Cpe result = instance.build();
        assertEquals(expResult.toString(), result.toString());
    }
}
