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

import us.springett.parsers.cpe.values.Part;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jeremy
 */
public class CpeTest {

    /**
     * Test of getPart method, of class Cpe.
     */
    @Test
    public void testGetPart() {
        Cpe instance = new Cpe(Part.ANY, "vendor", "product", "version", "update", 
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        Part expResult = Part.ANY;
        Part result = instance.getPart();
        assertEquals(expResult, result);
    }

    /**
     * Test of getVendor method, of class Cpe.
     */
    @Test
    public void testGetVendor() {
        Cpe instance = new Cpe(Part.ANY, "vendor", "product", "version", "update", 
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        String expResult = "vendor";
        String result = instance.getVendor();
        assertEquals(expResult, result);
    }

    /**
     * Test of getProduct method, of class Cpe.
     */
    @Test
    public void testGetProduct() {
        Cpe instance = new Cpe(Part.ANY, "vendor", "product", "version", "update", 
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        String expResult = "product";
        String result = instance.getProduct();
        assertEquals(expResult, result);
    }

    /**
     * Test of getVersion method, of class Cpe.
     */
    @Test
    public void testGetVersion() {
        Cpe instance = new Cpe(Part.ANY, "vendor", "product", "version", "update", 
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        String expResult = "version";
        String result = instance.getVersion();
        assertEquals(expResult, result);
    }

    /**
     * Test of getUpdate method, of class Cpe.
     */
    @Test
    public void testGetUpdate() {
        Cpe instance = new Cpe(Part.ANY, "vendor", "product", "version", "update", 
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        String expResult = "update";
        String result = instance.getUpdate();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEdition method, of class Cpe.
     */
    @Test
    public void testGetEdition() {
        Cpe instance = new Cpe(Part.ANY, "vendor", "product", "version", "update", 
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        String expResult = "edition";
        String result = instance.getEdition();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLanguage method, of class Cpe.
     */
    @Test
    public void testGetLanguage() {
        Cpe instance = new Cpe(Part.ANY, "vendor", "product", "version", "update", 
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        String expResult = "language";
        String result = instance.getLanguage();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSwEdition method, of class Cpe.
     */
    @Test
    public void testGetSwEdition() {
        Cpe instance = new Cpe(Part.ANY, "vendor", "product", "version", "update", 
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        String expResult = "swEdition";
        String result = instance.getSwEdition();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTargetSw method, of class Cpe.
     */
    @Test
    public void testGetTargetSw() {
        Cpe instance = new Cpe(Part.ANY, "vendor", "product", "version", "update", 
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        String expResult = "targetSw";
        String result = instance.getTargetSw();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTargetHw method, of class Cpe.
     */
    @Test
    public void testGetTargetHw() {
        Cpe instance = new Cpe(Part.ANY, "vendor", "product", "version", "update", 
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        String expResult = "targetHw";
        String result = instance.getTargetHw();
        assertEquals(expResult, result);
    }

    /**
     * Test of getOther method, of class Cpe.
     */
    @Test
    public void testGetOther() {
        Cpe instance = new Cpe(Part.ANY, "vendor", "product", "version", "update", 
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        String expResult = "other";
        String result = instance.getOther();
        assertEquals(expResult, result);
    }

    /**
     * Test of toCpe22Uri method, of class Cpe.
     */
    @Test
    public void testToCpe22Uri() {
        Cpe instance = new Cpe(Part.ANY, "vendor", "product", "version", "update", 
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        String expResult = "cpe:/*:vendor:product:version:update:~edition~swEdition~targetSw~targetHw~other:language";
        String result = instance.toCpe22Uri();
        assertEquals(expResult, result);
        
        
        instance = new Cpe(Part.ANY, "vendor", "product", "version", "update", 
                "edition", "language", "*", "*", "*", "*");
        expResult = "cpe:/*:vendor:product:version:update:edition:language";
        result = instance.toCpe22Uri();
        assertEquals(expResult, result);
        
        instance = new Cpe(Part.ANY, "vendor", "product", "version", "update", 
                "edition", "language", "", "targetSw", "targetHw", "other");
        expResult = "cpe:/*:vendor:product:version:update:~edition~~targetSw~targetHw~other:language";
        result = instance.toCpe22Uri();
        assertEquals(expResult, result);
        
        instance = new Cpe(Part.ANY, "vendor", "product", "version", "update", 
                "edition", "language", "*", "targetSw", "targetHw", "other");
        expResult = "cpe:/*:vendor:product:version:update:~edition~~targetSw~targetHw~other:language";
        result = instance.toCpe22Uri();
        assertEquals(expResult, result);
        
        instance = new Cpe(Part.ANY, "vendor", "product", "version", "update", 
                "edition", "language", "swEdition", "*", "targetHw", "other");
        expResult = "cpe:/*:vendor:product:version:update:~edition~swEdition~~targetHw~other:language";
        result = instance.toCpe22Uri();
        assertEquals(expResult, result);
        
        instance = new Cpe(Part.ANY, "vendor", "product", "version", "update", 
                "edition", "language", "swEdition", "", "targetHw", "other");
        expResult = "cpe:/*:vendor:product:version:update:~edition~swEdition~~targetHw~other:language";
        result = instance.toCpe22Uri();
        assertEquals(expResult, result);
        
        instance = new Cpe(Part.ANY, "vendor", "product", "version", "update", 
                "edition", "language", "swEdition", "targetSw", "*", "other");
        expResult = "cpe:/*:vendor:product:version:update:~edition~swEdition~targetSw~~other:language";
        result = instance.toCpe22Uri();
        assertEquals(expResult, result);
        
        instance = new Cpe(Part.ANY, "vendor", "product", "version", "update", 
                "edition", "language", "swEdition", "targetSw", "", "other");
        expResult = "cpe:/*:vendor:product:version:update:~edition~swEdition~targetSw~~other:language";
        result = instance.toCpe22Uri();
        assertEquals(expResult, result);
        
        instance = new Cpe(Part.ANY, "vendor", "product", "version", "update", 
                "edition", "language", "swEdition", "targetSw", "targetHw", "*");
        expResult = "cpe:/*:vendor:product:version:update:~edition~swEdition~targetSw~targetHw~:language";
        result = instance.toCpe22Uri();
        assertEquals(expResult, result);
        
        instance = new Cpe(Part.ANY, "vendor", "product", "version", "update", 
                "edition", "language", "swEdition", "targetSw", "targetHw", "");
        expResult = "cpe:/*:vendor:product:version:update:~edition~swEdition~targetSw~targetHw~:language";
        result = instance.toCpe22Uri();
        assertEquals(expResult, result);
    }

    /**
     * Test of toCpe23FS method, of class Cpe.
     */
    @Test
    public void testToCpe23FS() {
        Cpe instance = new Cpe(Part.ANY, "vendor", "product", "version", "update", 
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        String expResult = "cpe:2.3:*:vendor:product:version:update:edition:language:swEdition:targetSw:targetHw:other";
        String result = instance.toCpe23FS();
        assertEquals(expResult, result);
    }    
}
