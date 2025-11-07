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

import org.junit.jupiter.api.Test;
import us.springett.parsers.cpe.exceptions.CpeParsingException;
import us.springett.parsers.cpe.values.LogicalValue;
import us.springett.parsers.cpe.values.Part;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test the CPE Builder.
 *
 * @author Jeremy Long
 */
public class CpeBuilderTest {
    /**
     * Test of part method, of class CpeBuilder.
     *
     * @throws Exception thrown if there is an error
     */
    @Test
    public void testPart_Part() throws Exception {
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
        String part = "-";
        CpeBuilder instance = new CpeBuilder();
        String expResult = "-";
        Cpe result = instance.part(part).build();
        assertEquals(expResult, result.getPart().getAbbreviation());
    }

    /**
     * Test of part method, of class CpeBuilder.
     *
     */
    @Test
    public void testPart_StringInvalid() {
        String part = "x";
        CpeBuilder instance = new CpeBuilder();
        assertThatThrownBy(() -> instance.part(part))
                .isInstanceOf(CpeParsingException.class);
    }

    /**
     * Test of vendor method, of class CpeBuilder.
     *
     * @throws Exception thrown if there is a parsing error
     */
    @Test
    public void testVendor_String() throws Exception {
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
    public void testVendor_LogicalValue() throws Exception {
        
        LogicalValue vendor = LogicalValue.NA;
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
    public void testProduct_LogicalValue() throws Exception {
        
        LogicalValue product = LogicalValue.NA;
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
    public void testVersion_LogicalValue() throws Exception {
        
        LogicalValue version = LogicalValue.NA;
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
    public void testUpdate_LogicalValue() throws Exception {
        
        LogicalValue update = LogicalValue.NA;
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
    public void testEdition_LogicalValue() throws Exception {
        
        LogicalValue edition = LogicalValue.NA;
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
    public void testLanguage_LogicalValue() throws Exception {
        
        LogicalValue language = LogicalValue.NA;
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
    public void testSwEdition_LogicalValue() throws Exception {
        
        LogicalValue swEdition = LogicalValue.NA;
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
    public void testTargetSw_LogicalValue() throws Exception {
        
        LogicalValue targetSw = LogicalValue.NA;
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
    public void testTargetHw_LogicalValue() throws Exception {
        
        LogicalValue targetHw = LogicalValue.NA;
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
    public void testOther_LogicalValue() throws Exception {
        
        LogicalValue other = LogicalValue.NA;
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
        
        CpeBuilder instance = new CpeBuilder();
        Cpe expResult = new Cpe(Part.ANY, "*", "*", "*", "*", "*", "*", "*", "*", "*", "*");
        Cpe result = instance.build();
        assertEquals(expResult.toString(), result.toString());
    }
}
