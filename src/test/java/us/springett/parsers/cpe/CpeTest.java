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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.AfterClass;
import us.springett.parsers.cpe.util.Relation;
import us.springett.parsers.cpe.values.Part;
import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import us.springett.parsers.cpe.exceptions.CpeValidationException;
import us.springett.parsers.cpe.values.LogicalValue;

/**
 *
 * @author Jeremy Long
 */
public class CpeTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test of constructor, of class Cpe.
     *
     * @throws Exception thrown if there is an error
     */
    @Test
    public void testConstructorError() throws Exception {
        exception.expect(CpeValidationException.class);
        Cpe instance = new Cpe(Part.ANY, "ve*ndor", "product", "version", "update",
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
    }

    /**
     * Test of constructor, of class Cpe.
     *
     * @throws Exception thrown if there is an error
     */
    @Test
    public void testConstructorError1() throws Exception {
        exception.expect(CpeValidationException.class);
        Cpe instance = new Cpe(Part.ANY, "vendor", "pro*duct", "version", "update",
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
    }

    /**
     * Test of constructor, of class Cpe.
     *
     * @throws Exception thrown if there is an error
     */
    @Test
    public void testConstructorError2() throws Exception {
        exception.expect(CpeValidationException.class);
        Cpe instance = new Cpe(Part.ANY, "vendor", "product", "ver*sion", "update",
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
    }

    /**
     * Test of constructor, of class Cpe.
     *
     * @throws Exception thrown if there is an error
     */
    @Test
    public void testConstructorError3() throws Exception {
        exception.expect(CpeValidationException.class);
        Cpe instance = new Cpe(Part.ANY, "vendor", "product", "version", "upd*ate",
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
    }

    /**
     * Test of constructor, of class Cpe.
     *
     * @throws Exception thrown if there is an error
     */
    @Test
    public void testConstructorError4() throws Exception {
        exception.expect(CpeValidationException.class);
        Cpe instance = new Cpe(Part.ANY, "vendor", "product", "version", "update",
                "edit*ion", "language", "swEdition", "targetSw", "targetHw", "other");
    }

    /**
     * Test of constructor, of class Cpe.
     *
     * @throws Exception thrown if there is an error
     */
    @Test
    public void testConstructorError5() throws Exception {
        exception.expect(CpeValidationException.class);
        Cpe instance = new Cpe(Part.ANY, "vendor", "product", "version", "update",
                "edition", "lang*uage", "swEdition", "targetSw", "targetHw", "other");
    }

    /**
     * Test of constructor, of class Cpe.
     *
     * @throws Exception thrown if there is an error
     */
    @Test
    public void testConstructorError6() throws Exception {
        exception.expect(CpeValidationException.class);
        Cpe instance = new Cpe(Part.ANY, "vendor", "product", "version", "update",
                "edition", "language", "swEdi*tion", "targetSw", "targetHw", "other");
    }

    /**
     * Test of constructor, of class Cpe.
     *
     * @throws Exception thrown if there is an error
     */
    @Test
    public void testConstructorError7() throws Exception {
        exception.expect(CpeValidationException.class);
        Cpe instance = new Cpe(Part.ANY, "vendor", "product", "version", "update",
                "edition", "language", "swEdition", "tar*getSw", "targetHw", "other");
    }

    /**
     * Test of constructor, of class Cpe.
     *
     * @throws Exception thrown if there is an error
     */
    @Test
    public void testConstructorError8() throws Exception {
        exception.expect(CpeValidationException.class);
        Cpe instance = new Cpe(Part.ANY, "vendor", "product", "version", "update",
                "edition", "language", "swEdition", "targetSw", "targ*etHw", "other");
    }

    /**
     * Test of constructor, of class Cpe.
     *
     * @throws Exception thrown if there is an error
     */
    @Test
    public void testConstructorError9() throws Exception {
        exception.expect(CpeValidationException.class);
        Cpe instance = new Cpe(Part.ANY, "vendor", "product", "version", "update",
                "edition", "language", "swEdition", "targetSw", "targetHw", "ot*her");
    }

    /**
     * Test of getPart method, of class Cpe.
     *
     * @throws Exception thrown if there is an error
     */
    @Test
    public void testGetPart() throws Exception {
        Cpe instance = new Cpe(Part.ANY, "vendor", "product", "version", "update",
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        Part expResult = Part.ANY;
        Part result = instance.getPart();
        assertEquals(expResult, result);
    }

    /**
     * Test of getVendor method, of class Cpe.
     *
     * @throws Exception thrown if there is an error
     */
    @Test
    public void testGetVendor() throws Exception {
        Cpe instance = new Cpe(Part.ANY, "vendor", "product", "version", "update",
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        String expResult = "vendor";
        String result = instance.getVendor();
        assertEquals(expResult, result);
    }

    /**
     * Test of getProduct method, of class Cpe.
     *
     * @throws Exception thrown if there is an error
     */
    @Test
    public void testGetProduct() throws Exception {
        Cpe instance = new Cpe(Part.ANY, "vendor", "product", "version", "update",
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        String expResult = "product";
        String result = instance.getProduct();
        assertEquals(expResult, result);
    }

    /**
     * Test of getVersion method, of class Cpe.
     *
     * @throws Exception thrown if there is an error
     */
    @Test
    public void testGetVersion() throws Exception {
        Cpe instance = new Cpe(Part.ANY, "vendor", "product", "version", "update",
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        String expResult = "version";
        String result = instance.getVersion();
        assertEquals(expResult, result);
    }

    /**
     * Test of getUpdate method, of class Cpe.
     *
     * @throws Exception thrown if there is an error
     */
    @Test
    public void testGetUpdate() throws Exception {
        Cpe instance = new Cpe(Part.ANY, "vendor", "product", "version", "update",
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        String expResult = "update";
        String result = instance.getUpdate();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEdition method, of class Cpe.
     *
     * @throws Exception thrown if there is an error
     */
    @Test
    public void testGetEdition() throws Exception {
        Cpe instance = new Cpe(Part.ANY, "vendor", "product", "version", "update",
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        String expResult = "edition";
        String result = instance.getEdition();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLanguage method, of class Cpe.
     *
     * @throws Exception thrown if there is an error
     */
    @Test
    public void testGetLanguage() throws Exception {
        Cpe instance = new Cpe(Part.ANY, "vendor", "product", "version", "update",
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        String expResult = "language";
        String result = instance.getLanguage();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSwEdition method, of class Cpe.
     *
     * @throws Exception thrown if there is an error
     */
    @Test
    public void testGetSwEdition() throws Exception {
        Cpe instance = new Cpe(Part.ANY, "vendor", "product", "version", "update",
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        String expResult = "swEdition";
        String result = instance.getSwEdition();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTargetSw method, of class Cpe.
     *
     * @throws Exception thrown if there is an error
     */
    @Test
    public void testGetTargetSw() throws Exception {
        Cpe instance = new Cpe(Part.ANY, "vendor", "product", "version", "update",
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        String expResult = "targetSw";
        String result = instance.getTargetSw();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTargetHw method, of class Cpe.
     *
     * @throws Exception thrown if there is an error
     */
    @Test
    public void testGetTargetHw() throws Exception {
        Cpe instance = new Cpe(Part.ANY, "vendor", "product", "version", "update",
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        String expResult = "targetHw";
        String result = instance.getTargetHw();
        assertEquals(expResult, result);
    }

    /**
     * Test of getOther method, of class Cpe.
     *
     * @throws Exception thrown if there is an error
     */
    @Test
    public void testGetOther() throws Exception {
        Cpe instance = new Cpe(Part.ANY, "vendor", "product", "version", "update",
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        String expResult = "other";
        String result = instance.getOther();
        assertEquals(expResult, result);
    }

    /**
     * Test of toCpe22Uri method, of class Cpe.
     *
     * @throws Exception thrown if an error occurs
     */
    @Test
    public void testToCpe22Uri() throws Exception {
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
                "edition", "language", "swEdition", "targetSw", "*", "other");
        expResult = "cpe:/*:vendor:product:version:update:~edition~swEdition~targetSw~~other:language";
        result = instance.toCpe22Uri();
        assertEquals(expResult, result);

        instance = new Cpe(Part.ANY, "vendor", "product", "version", "update",
                "edition", "language", "swEdition", "targetSw", "targetHw", "*");
        expResult = "cpe:/*:vendor:product:version:update:~edition~swEdition~targetSw~targetHw~:language";
        result = instance.toCpe22Uri();
        assertEquals(expResult, result);
    }

    /**
     * Test of toCpe23FS method, of class Cpe.
     *
     * @throws Exception thrown if there is an error
     */
    @Test
    public void testToCpe23FS() throws Exception {
        Cpe instance = new Cpe(Part.ANY, "vendor", "product", "version", "update",
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        String expResult = "cpe:2.3:*:vendor:product:version:update:edition:language:swEdition:targetSw:targetHw:other";
        String result = instance.toCpe23FS();
        assertEquals(expResult, result);
    }

    /**
     * Test of matches method, of class Cpe.
     *
     * @throws java.lang.Exception thrown if there is an error
     */
    @Test
    public void testMatches() throws Exception {
        CpeBuilder builder = new CpeBuilder();
        Cpe cpe = builder.part(Part.APPLICATION).vendor("owasp").product("dependency-check").version("4.0.0").build();
        Cpe instance = builder.part(Part.APPLICATION).vendor("owasp").product("dependency-check").version("4.0.0").build();
        boolean expResult = true;
        boolean result = instance.matches(cpe);
        assertEquals(expResult, result);

        cpe = builder.part(Part.APPLICATION).vendor("owasp").product("dependency-check").version("4.0.1").build();
        instance = builder.part(Part.APPLICATION).vendor("owasp").product("dependency-check").version("4.0.0").build();
        expResult = false;
        result = instance.matches(cpe);
        assertEquals(expResult, result);

        cpe = builder.part(Part.APPLICATION).vendor("owasp").product("dependency-check").version("4.0.0").build();
        instance = builder.part(Part.APPLICATION).vendor("owasp").product(LogicalValue.ANY).version("4.0.0").build();
        expResult = true;
        result = instance.matches(cpe);
        assertEquals(expResult, result);

        cpe = builder.part(Part.APPLICATION).vendor("owasp").product("dependency-check").version("4.0.0").build();
        instance = builder.part(Part.APPLICATION).vendor("owasp").product(LogicalValue.NA).version("4.0.0").build();
        expResult = false;
        result = instance.matches(cpe);
        assertEquals(expResult, result);

        cpe = builder.part(Part.APPLICATION).vendor("owasp").product(LogicalValue.NA).version("4.0.0").build();
        instance = builder.part(Part.APPLICATION).vendor("owasp").product("dependency-check").version("4.0.0").build();
        expResult = false;
        result = instance.matches(cpe);
        assertEquals(expResult, result);

        cpe = builder.part(Part.APPLICATION).vendor("owasp").product(LogicalValue.ANY).version("4.0.0").build();
        instance = builder.part(Part.APPLICATION).vendor("owasp").product("dependency-check").version("4.0.0").build();
        expResult = true;
        result = instance.matches(cpe);
        assertEquals(expResult, result);

        cpe = builder.part(Part.APPLICATION).vendor("owasp").product("dependency-check").version("4.0.0").build();
        instance = builder.part(Part.APPLICATION).vendor("owasp").wfProduct("*check").version("4.0.0").build();
        expResult = true;
        result = instance.matches(cpe);
        assertEquals(expResult, result);

        cpe = builder.part(Part.APPLICATION).vendor("owasp").product("dependency").version("4.0.0").build();
        instance = builder.part(Part.APPLICATION).vendor("owasp").wfProduct("*check").version("4.0.0").build();
        expResult = false;
        result = instance.matches(cpe);
        assertEquals(expResult, result);
    }

    /**
     * Test of matchedBy method, of class Cpe.
     *
     * @throws java.lang.Exception thrown if there is an error
     */
    @Test
    public void testMatchedBy() throws Exception {
        CpeBuilder builder = new CpeBuilder();
        Cpe target = builder.part(Part.APPLICATION).vendor("owasp").product("dependency-check").version("4.0.0").build();
        Cpe instance = builder.part(Part.APPLICATION).vendor("owasp").product("dependency-check").version("4.0.0").build();
        boolean expResult = true;
        boolean result = instance.matchedBy(target);
        assertEquals(expResult, result);

        target = builder.part(Part.APPLICATION).vendor("owasp").product("dependency-check").version("4.0.1").build();
        instance = builder.part(Part.APPLICATION).vendor("owasp").product("dependency-check").version("4.0.0").build();
        expResult = false;
        result = instance.matchedBy(target);
        assertEquals(expResult, result);
    }

    @Test
    public void testHashCode() throws Exception {
        CpeBuilder builder = new CpeBuilder();
        Cpe instance = builder.part(Part.APPLICATION).vendor("owasp").product("dependency-check").version("4.0.0").build();

        assertEquals(instance.hashCode(), instance.hashCode());

    }

    @Test
    public void testSort() throws Exception {
        List<Cpe> instance = new ArrayList<>();
        CpeBuilder builder = new CpeBuilder();
        instance.add(builder.part(Part.APPLICATION).vendor("owasp").product("dependency-check").version("4.0.0").build());
        instance.add(builder.part(Part.APPLICATION).vendor("owasp").product("dependency-check").version("3.0.0").build());
        instance.add(builder.part(Part.APPLICATION).vendor("owasp").product("dependency-check").version("5.0.0").build());
        instance.add(builder.part(Part.APPLICATION).vendor("owasp").product("dependency-check").version("1.0.0").build());
        assertEquals("1.0.0", instance.get(3).getVersion());
        Collections.sort(instance);

    }

    /**
     * Test of compareTo method, of class Cpe.
     *
     * @throws Exception thrown if there is an error
     */
    @Test
    public void testCompareTo() throws Exception {
        Cpe instance = new Cpe(Part.HARDWARE_DEVICE, "vendor", "product", "version", "update",
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        Cpe obj = instance;
        int expResult = 0;
        int result = instance.compareTo(obj);
        assertEquals(expResult, result);

        expResult = 0;
        obj = new Cpe(Part.HARDWARE_DEVICE, "vendor", "product", "version", "update",
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        result = instance.compareTo(obj);
        assertEquals(expResult, result);

        expResult = 1;
        obj = new Cpe(Part.APPLICATION, "vendor", "product", "version", "update",
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        result = instance.compareTo(obj);
        assertEquals(expResult, result);

        expResult = -1;
        obj = new Cpe(Part.OPERATING_SYSTEM, "vendor", "product", "version", "update",
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        result = instance.compareTo(obj);
        assertEquals(expResult, result);

        expResult = 1;
        obj = new Cpe(Part.HARDWARE_DEVICE, "avendor", "product", "version", "update",
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        result = instance.compareTo(obj);
        assertEquals(expResult, result);

        expResult = -1;
        obj = new Cpe(Part.HARDWARE_DEVICE, "zvendor", "product", "version", "update",
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        result = instance.compareTo(obj);
        assertEquals(expResult, result);

        expResult = 1;
        obj = new Cpe(Part.HARDWARE_DEVICE, "vendor", "aproduct", "version", "update",
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        result = instance.compareTo(obj);
        assertEquals(expResult, result);

        expResult = -1;
        obj = new Cpe(Part.HARDWARE_DEVICE, "vendor", "zproduct", "version", "update",
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        result = instance.compareTo(obj);
        assertEquals(expResult, result);

        expResult = 1;
        obj = new Cpe(Part.HARDWARE_DEVICE, "vendor", "product", "aversion", "update",
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        result = instance.compareTo(obj);
        assertEquals(expResult, result);

        expResult = -1;
        obj = new Cpe(Part.HARDWARE_DEVICE, "vendor", "product", "zversion", "update",
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        result = instance.compareTo(obj);
        assertEquals(expResult, result);

        expResult = 1;
        obj = new Cpe(Part.HARDWARE_DEVICE, "vendor", "product", "version", "aupdate",
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        result = instance.compareTo(obj);
        assertEquals(expResult, result);

        expResult = -1;
        obj = new Cpe(Part.HARDWARE_DEVICE, "vendor", "product", "version", "zupdate",
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        result = instance.compareTo(obj);
        assertEquals(expResult, result);

        expResult = 1;
        obj = new Cpe(Part.HARDWARE_DEVICE, "vendor", "product", "version", "update",
                "aedition", "language", "swEdition", "targetSw", "targetHw", "other");
        result = instance.compareTo(obj);
        assertEquals(expResult, result);

        expResult = -1;
        obj = new Cpe(Part.HARDWARE_DEVICE, "vendor", "product", "version", "update",
                "zedition", "language", "swEdition", "targetSw", "targetHw", "other");
        result = instance.compareTo(obj);
        assertEquals(expResult, result);

        expResult = 1;
        obj = new Cpe(Part.HARDWARE_DEVICE, "vendor", "product", "version", "update",
                "edition", "alanguage", "swEdition", "targetSw", "targetHw", "other");
        result = instance.compareTo(obj);
        assertEquals(expResult, result);

        expResult = -1;
        obj = new Cpe(Part.HARDWARE_DEVICE, "vendor", "product", "version", "update",
                "edition", "zlanguage", "swEdition", "targetSw", "targetHw", "other");
        result = instance.compareTo(obj);
        assertEquals(expResult, result);

        expResult = 1;
        obj = new Cpe(Part.HARDWARE_DEVICE, "vendor", "product", "version", "update",
                "edition", "language", "aswEdition", "targetSw", "targetHw", "other");
        result = instance.compareTo(obj);
        assertEquals(expResult, result);

        expResult = -1;
        obj = new Cpe(Part.HARDWARE_DEVICE, "vendor", "product", "version", "update",
                "edition", "language", "zswEdition", "targetSw", "targetHw", "other");
        result = instance.compareTo(obj);
        assertEquals(expResult, result);

        expResult = 1;
        obj = new Cpe(Part.HARDWARE_DEVICE, "vendor", "product", "version", "update",
                "edition", "language", "swEdition", "atargetSw", "targetHw", "other");
        result = instance.compareTo(obj);
        assertEquals(expResult, result);

        expResult = -1;
        obj = new Cpe(Part.HARDWARE_DEVICE, "vendor", "product", "version", "update",
                "edition", "language", "swEdition", "ztargetSw", "targetHw", "other");
        result = instance.compareTo(obj);
        assertEquals(expResult, result);

        expResult = 1;
        obj = new Cpe(Part.HARDWARE_DEVICE, "vendor", "product", "version", "update",
                "edition", "language", "swEdition", "targetSw", "atargetHw", "other");
        result = instance.compareTo(obj);
        assertEquals(expResult, result);

        expResult = -1;
        obj = new Cpe(Part.HARDWARE_DEVICE, "vendor", "product", "version", "update",
                "edition", "language", "swEdition", "targetSw", "ztargetHw", "other");
        result = instance.compareTo(obj);
        assertEquals(expResult, result);
        expResult = 1;
        obj = new Cpe(Part.HARDWARE_DEVICE, "vendor", "product", "version", "update",
                "edition", "language", "swEdition", "targetSw", "targetHw", "aother");
        result = instance.compareTo(obj);
        assertEquals(expResult, result);

        expResult = -1;
        obj = new Cpe(Part.HARDWARE_DEVICE, "vendor", "product", "version", "update",
                "edition", "language", "swEdition", "targetSw", "targetHw", "zother");
        result = instance.compareTo(obj);
        assertEquals(expResult, result);

        expResult = -1;
        CpeBuilder builder = new CpeBuilder();

        Cpe instance2 = (Cpe) builder.part(Part.APPLICATION).vendor("owasp").product("dependency-check").version("3.0.0").build();
        obj = builder.part(Part.APPLICATION).vendor("owasp").product("dependency-check").version("4.0.0").build();
        result = instance2.compareTo(obj);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Cpe.
     *
     * @throws Exception thrown if there is an error
     */
    @Test
    public void testEquals() throws Exception {
        Object obj = new Cpe(Part.ANY, "vendor", "product", "version", "update",
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");

        Cpe instance = new Cpe(Part.ANY, "vendor", "product", "version", "update",
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        boolean expResult = true;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);

        expResult = true;
        obj = instance;
        result = instance.equals(obj);
        assertEquals(expResult, result);

        obj = new Cpe(Part.APPLICATION, "vendor", "product", "version", "update",
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        expResult = false;
        result = instance.equals(obj);
        assertEquals(expResult, result);

        obj = new Cpe(Part.ANY, "wrong", "product", "version", "update",
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        expResult = false;
        result = instance.equals(obj);
        assertEquals(expResult, result);

        obj = new Cpe(Part.ANY, "vendor", "wrong", "version", "update",
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        expResult = false;
        result = instance.equals(obj);
        assertEquals(expResult, result);

        obj = new Cpe(Part.ANY, "vendor", "product", "wrong", "update",
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        expResult = false;
        result = instance.equals(obj);
        assertEquals(expResult, result);

        obj = new Cpe(Part.ANY, "vendor", "product", "version", "wrong",
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        expResult = false;
        result = instance.equals(obj);
        assertEquals(expResult, result);

        obj = new Cpe(Part.ANY, "vendor", "product", "version", "update",
                "wrong", "language", "swEdition", "targetSw", "targetHw", "other");
        expResult = false;
        result = instance.equals(obj);
        assertEquals(expResult, result);

        obj = new Cpe(Part.ANY, "vendor", "product", "version", "update",
                "edition", "wrong", "swEdition", "targetSw", "targetHw", "other");
        expResult = false;
        result = instance.equals(obj);
        assertEquals(expResult, result);

        obj = new Cpe(Part.ANY, "vendor", "product", "version", "update",
                "edition", "language", "wrong", "targetSw", "targetHw", "other");
        expResult = false;
        result = instance.equals(obj);
        assertEquals(expResult, result);

        obj = new Cpe(Part.ANY, "vendor", "product", "version", "update",
                "edition", "language", "swEdition", "wrong", "targetHw", "other");
        expResult = false;
        result = instance.equals(obj);
        assertEquals(expResult, result);

        obj = new Cpe(Part.ANY, "vendor", "product", "version", "update",
                "edition", "language", "swEdition", "targetSw", "wrong", "other");
        expResult = false;
        result = instance.equals(obj);
        assertEquals(expResult, result);

        obj = new Cpe(Part.ANY, "vendor", "product", "version", "update",
                "edition", "language", "swEdition", "targetSw", "targetHw", "wrong");
        expResult = false;
        result = instance.equals(obj);
        assertEquals(expResult, result);

        expResult = false;
        result = instance.equals("test");
        assertEquals(expResult, result);

        obj = null;
        expResult = false;
        result = instance.equals(obj);
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Cpe.
     *
     * @throws Exception thrown if there is an error
     */
    @Test
    public void testToString() throws Exception {
        Cpe instance = new Cpe(Part.APPLICATION, "vendor", "product", "version", "update",
                "edition", "language", "swEdition", "targetSw", "targetHw", "other");
        String expResult = "cpe:2.3:a:vendor:product:version:update:edition:language:swEdition:targetSw:targetHw:other";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of compareAttributes method, of class Cpe.
     */
    @Test
    public void testCompareAttributes_Part_Part() {
        assertTrue(Cpe.compareAttributes(Part.APPLICATION, Part.APPLICATION));
        assertFalse(Cpe.compareAttributes(Part.APPLICATION, Part.HARDWARE_DEVICE));
        assertFalse(Cpe.compareAttributes(Part.APPLICATION, Part.OPERATING_SYSTEM));
        assertFalse(Cpe.compareAttributes(Part.APPLICATION, Part.NA));
        assertTrue(Cpe.compareAttributes(Part.APPLICATION, Part.ANY));

        assertTrue(Cpe.compareAttributes(Part.ANY, Part.ANY));
        assertTrue(Cpe.compareAttributes(Part.ANY, Part.APPLICATION));
        assertTrue(Cpe.compareAttributes(Part.ANY, Part.OPERATING_SYSTEM));
        assertTrue(Cpe.compareAttributes(Part.ANY, Part.HARDWARE_DEVICE));
        assertTrue(Cpe.compareAttributes(Part.ANY, Part.NA));

        assertTrue(Cpe.compareAttributes(Part.NA, Part.NA));
        assertTrue(Cpe.compareAttributes(Part.NA, Part.ANY));
        assertFalse(Cpe.compareAttributes(Part.NA, Part.APPLICATION));
        assertFalse(Cpe.compareAttributes(Part.NA, Part.HARDWARE_DEVICE));
        assertFalse(Cpe.compareAttributes(Part.NA, Part.OPERATING_SYSTEM));
    }

    @Test
    public void testCompareAttribute_Part_Part_Relation() {
        assertEquals(Relation.EQUAL, Cpe.compareAttribute(Part.APPLICATION, Part.APPLICATION));
        assertEquals(Relation.DISJOINT, Cpe.compareAttribute(Part.APPLICATION, Part.HARDWARE_DEVICE));
        assertEquals(Relation.DISJOINT, Cpe.compareAttribute(Part.APPLICATION, Part.OPERATING_SYSTEM));
        assertEquals(Relation.DISJOINT, Cpe.compareAttribute(Part.APPLICATION, Part.NA));
        assertEquals(Relation.SUBSET, Cpe.compareAttribute(Part.APPLICATION, Part.ANY));

        assertEquals(Relation.EQUAL, Cpe.compareAttribute(Part.ANY, Part.ANY));
        assertEquals(Relation.SUPERSET, Cpe.compareAttribute(Part.ANY, Part.APPLICATION));
        assertEquals(Relation.SUPERSET, Cpe.compareAttribute(Part.ANY, Part.OPERATING_SYSTEM));
        assertEquals(Relation.SUPERSET, Cpe.compareAttribute(Part.ANY, Part.HARDWARE_DEVICE));
        assertEquals(Relation.SUPERSET, Cpe.compareAttribute(Part.ANY, Part.NA));

        assertEquals(Relation.EQUAL, Cpe.compareAttribute(Part.NA, Part.NA));
        assertEquals(Relation.SUBSET, Cpe.compareAttribute(Part.NA, Part.ANY));
        assertEquals(Relation.DISJOINT, Cpe.compareAttribute(Part.NA, Part.APPLICATION));
        assertEquals(Relation.DISJOINT, Cpe.compareAttribute(Part.NA, Part.HARDWARE_DEVICE));
        assertEquals(Relation.DISJOINT, Cpe.compareAttribute(Part.NA, Part.OPERATING_SYSTEM));
    }

    /**
     * Test of compareAttributes method, of class Cpe.
     */
    @Test
    public void testCompareAttributes_String_String() {
        assertTrue(Cpe.compareAttributes("a", "a"));
        assertTrue(Cpe.compareAttributes("a", "A"));
        assertTrue(Cpe.compareAttributes("abc?", "abcd"));
        assertTrue(Cpe.compareAttributes("abc\\:def", "abc\\:def"));
        assertFalse(Cpe.compareAttributes("abc", "abcdef"));
        assertTrue(Cpe.compareAttributes("abc*", "abcdef"));
        assertFalse(Cpe.compareAttributes("abc??", "abcdef"));
    }

    @Test
    public void testCompareAttribute_String_String_Relation() {
        assertEquals(Relation.EQUAL, Cpe.compareAttribute("a", "a"));
        assertEquals(Relation.EQUAL, Cpe.compareAttribute("a", "A"));
        assertEquals(Relation.SUPERSET, Cpe.compareAttribute("abc?", "abcd"));
        assertEquals(Relation.EQUAL, Cpe.compareAttribute("abc\\:def", "abc\\:def"));
        assertEquals(Relation.DISJOINT, Cpe.compareAttribute("abc", "abcdef"));
        assertEquals(Relation.SUPERSET, Cpe.compareAttribute("abc*", "abcdef"));
        assertEquals(Relation.DISJOINT, Cpe.compareAttribute("abc??", "abcdef"));
    }

    /**
     * Test of compareVersions method, of class Cpe.
     */
    @Test
    public void testCompareVersions() {
        String left = "2.1.10";
        String right = "2.1.10";
        assertTrue(Cpe.compareVersions(left, right) == 0);

        left = "2.1";
        right = "2.1.10";
        assertTrue(Cpe.compareVersions(left, right) < 0);

        left = "2.1.10";
        right = "2.1";
        assertTrue(Cpe.compareVersions(left, right) > 0);

        left = "2.1.42";
        right = "2.3.21";
        assertTrue(Cpe.compareVersions(left, right) < 0);

        left = "2.1.10";
        right = "2.1.10-186";
        assertTrue(Cpe.compareVersions(left, right) < 0);

        left = "10.01";
        right = "10.1";
        assertTrue(Cpe.compareVersions(left, right) < 0);

        left = "10.1";
        right = "10.01";
        assertTrue(Cpe.compareVersions(left, right) > 0);

        left = "5.1.23a";
        right = "5.1.23a";
        assertTrue(Cpe.compareVersions(left, right) == 0);

        left = "5.1.23a";
        right = "5.1.23b";
        assertTrue(Cpe.compareVersions(left, right) < 0);

        left = "5.1.23b";
        right = "5.1.23a";
        assertTrue(Cpe.compareVersions(left, right) > 0);

        left = "5.1.9223372036854775807152";
        right = "5.1.932";
        assertTrue(Cpe.compareVersions(left, right) > 0);

        left = "5.1.932";
        right = "5.1.9223372036854775807152";
        assertTrue(Cpe.compareVersions(left, right) < 0);

        left = "alpha";
        right = "alpha";
        assertTrue(Cpe.compareVersions(left, right) == 0);

        assertTrue(Cpe.compareVersions("5.1.9", "5.1.20") < 0);
        assertTrue(Cpe.compareVersions("5.1.20", "5.1.32-bzr") < 0);
        assertTrue(Cpe.compareVersions("5.1.9", "5.1.32-bzr") < 0);
        assertTrue(Cpe.compareVersions("5.1.32-bzr", "5.1.9") > 0);
        assertTrue(Cpe.compareVersions("1-SNAPSHOT", "1.SNAPSHOT") == 0);
        assertTrue(Cpe.compareVersions("1-SNAPSHOT", "1|SNAPSHOT") == 0);
        assertTrue(Cpe.compareVersions("1-SNAPSHOT", "1:SNAPSHOT") == 0);

        assertTrue(Cpe.compareVersions("9", "20") < 0);
        assertTrue(Cpe.compareVersions("20", "32+bzr") < 0);
        assertTrue(Cpe.compareVersions("9", "32+bzr") < 0); // Transitive inconsistency - still fails
    }

    @Test
    public void testSplitVersions() {
        Assertions.assertThat(Cpe.splitVersion(null)).containsExactly();
        Assertions.assertThat(Cpe.splitVersion("")).containsExactly();
        Assertions.assertThat(Cpe.splitVersion("0.1")).containsExactly("0", bi("1"));
        Assertions.assertThat(Cpe.splitVersion("0.134alpha")).containsExactly("0", bi("134"), "alpha");
        Assertions.assertThat(Cpe.splitVersion("0.1+alpha")).containsExactly("0", bi("1"), "+alpha");
        Assertions.assertThat(Cpe.splitVersion("1.2.3")).containsExactly(bi("1"), bi("2"), bi("3"));
        Assertions.assertThat(Cpe.splitVersion("1.2.3b")).containsExactly(bi("1"), bi("2"), bi("3"), "b");
        Assertions.assertThat(Cpe.splitVersion("1.2.3-SNAPSHOT")).containsExactly(bi("1"), bi("2"), bi("3"), "SNAPSHOT");
        Assertions.assertThat(Cpe.splitVersion("1.2.3:|")).containsExactly(bi("1"), bi("2"), bi("3"));
        Assertions.assertThat(Cpe.splitVersion("1-2|3|")).containsExactly(bi("1"), bi("2"), bi("3"));
        Assertions.assertThat(Cpe.splitVersion("5.1.32-bzr")).containsExactly(bi("5"), bi("1"), bi("32"), "bzr");
        Assertions.assertThat(Cpe.splitVersion("5.1.9223372036854775807152")).containsExactly(bi("5"), bi("1"), bi("9223372036854775807152"));
        // Test letter-to-digit transitions (rc10 should split into rc and 10)
        Assertions.assertThat(Cpe.splitVersion("rc10")).containsExactly("rc", bi("10"));
        Assertions.assertThat(Cpe.splitVersion("alpha2")).containsExactly("alpha", bi("2"));
    }

    @Test
    public void testZeroOneA() {
        // Test consistency: letter suffix always splits, even with leading zeros
        Assertions.assertThat(Cpe.splitVersion("1.1a")).containsExactly(bi("1"), bi("1"), "a");
        Assertions.assertThat(Cpe.splitVersion("1.01a")).containsExactly(bi("1"), "01", "a");
        assertTrue(Cpe.compareVersions("1.01a", "1.1a") < 0);
    }

    private static BigInteger bi(String val) {
        return new BigInteger(val);
    }

    /**
     * Test transitivity of version comparison - CRITICAL
     * This test verifies that if a > b and b > c, then a > c
     * Mixed BigInteger/String comparisons can violate transitivity
     */
    @Test
    public void testVersionComparisonTransitivity() {
        // Test case that may expose transitivity violations with mixed types
        String a = "2";
        String b = "11";
        String c = "10";

        int ab = Cpe.compareVersions(a, b);
        int bc = Cpe.compareVersions(b, c);
        int ac = Cpe.compareVersions(a, c);

        // Transitivity requires: if a>b and b>c, then a>c
        if (ab > 0 && bc > 0) {
            assertTrue("Transitivity violation: if 2>11 and 11>10, then 2 must be >10", ac > 0);
        } else if (ab < 0 && bc < 0) {
            assertTrue("Transitivity violation: if 2<11 and 11<10, then 2 must be <10", ac < 0);
        }
    }

    /**
     * Test transitivity with version suffixes
     * From PR #288 - this was noted to potentially "still fail"
     */
    @Test
    public void testTransitivityWithSuffixes() {
        // Individual comparisons from PR
        assertTrue(Cpe.compareVersions("9", "20") < 0);
        assertTrue(Cpe.compareVersions("20", "32+bzr") < 0);
        assertTrue(Cpe.compareVersions("9", "32+bzr") < 0);

        // Verify full transitivity chain
        String[] chain = {"9", "20", "32+bzr"};
        for (int i = 0; i < chain.length - 1; i++) {
            for (int j = i + 1; j < chain.length; j++) {
                assertTrue(
                        String.format("Expected %s < %s for transitivity", chain[i], chain[j]),
                        Cpe.compareVersions(chain[i], chain[j]) < 0
                );
            }
        }
    }

    /**
     * Test leading zero version comparison behavior
     * Documents current intentional behavior where leading zeros are treated as strings
     */
    @Test
    public void testLeadingZeroVersions() {
        // Leading zeros are treated differently than non-leading zeros
        assertNotEquals("01 should not equal 1 if leading zeros are treated as strings",
                0, Cpe.compareVersions("01", "1"));

        // Multiple leading zeros in different segments
        assertTrue(Cpe.compareVersions("1.01", "1.1") != 0);
        assertTrue(Cpe.compareVersions("1.01", "1.02") < 0);

        // Leading zero with suffix
        assertTrue(Cpe.compareVersions("0a", "0b") < 0);
    }

    /**
     * Test comparisons with mixed numeric and string types in same position
     */
    @Test
    public void testMixedTypeComparisons() {
        // Numeric vs string suffix
        assertTrue("Numeric should come before alpha suffix", Cpe.compareVersions("1.2", "1.2a") < 0);
        assertTrue("Alpha comparison", Cpe.compareVersions("1.2z", "1.2a") > 0);

        // Pure string vs numeric in same position
        int cmp = Cpe.compareVersions("1.alpha", "1.2");
        assertNotEquals("String vs numeric should have defined ordering", 0, cmp);

        // Very large number vs string
        assertNotEquals("Large number vs string should have defined ordering",
                0, Cpe.compareVersions("1.9999999999999999999", "1.abc"));
    }

    /**
     * Test empty segments and trailing separators
     */
    @Test
    public void testEmptySegmentsAndTrailingSeparators() {
        // Trailing separators should be ignored
        assertEquals("Trailing separator should be ignored",
                0, Cpe.compareVersions("1.2.3", "1.2.3."));
        assertEquals("Trailing separator should be ignored",
                0, Cpe.compareVersions("1.2.3", "1.2.3:"));

        // Multiple consecutive separators
        assertEquals("Empty segment from double separator",
                0, Cpe.compareVersions("1..2", "1.2"));
        assertEquals("Trailing dash",
                0, Cpe.compareVersions("1-", "1"));
    }

    /**
     * Test real-world version comparisons
     * These are common version patterns seen in CPE data
     */
    @Test
    public void testRealWorldVersions() {
        // Python versions - numeric comparison
        assertTrue("2.7.18 > 2.7.9", Cpe.compareVersions("2.7.18", "2.7.9") > 0);
        assertTrue("3.9.0 < 3.10.0", Cpe.compareVersions("3.9.0", "3.10.0") < 0);

        // Pre-release versions with text
        assertTrue("alpha < beta", Cpe.compareVersions("1.0.0-alpha", "1.0.0-beta") < 0);
        assertTrue("rc1 < rc2", Cpe.compareVersions("1.0.0-rc1", "1.0.0-rc2") < 0);

        // Numeric suffixes - this is tricky: rc10 vs rc2
        // If "rc" splits properly, we get numeric comparison of 10 vs 2
        assertTrue("rc10 > rc2 when numeric", Cpe.compareVersions("1.0.0-rc10", "1.0.0-rc2") > 0);
    }

    /**
     * Test splitVersion with additional leading zero cases
     */
    @Test
    public void testSplitVersionLeadingZeros() {
        // Leading zeros in first position
        Assertions.assertThat(Cpe.splitVersion("0.1")).containsExactly("0", bi("1"));

        // Leading zeros create string tokens (numeric part stays together, no leading zeros stripped)
        // "01" is a string (leading zero indicates not a number, like a date or build ID)
        // "02" is a string (leading zero indicates not a number)
        Assertions.assertThat(Cpe.splitVersion("01.02"))
                .as("Leading zeros indicate string tokens")
                .containsExactly("01", "02");

        // Leading-zero numeric followed by letter: splits at digit->letter boundary for consistency
        // "01a" splits into "01" (string) + "a" (string) for consistency with "1a" -> [1, "a"]
        Assertions.assertThat(Cpe.splitVersion("1.01a"))
                .as("1.01a splits: 1 (numeric), then '01' (string), then 'a' (string)")
                .containsExactly(bi("1"), "01", "a");

        // Pure leading-zero number with no suffix stays together
        Assertions.assertThat(Cpe.splitVersion("04121975"))
                .as("Date-like numbers with all digits stay together")
                .containsExactly("04121975");
    }

    /**
     * Test comprehensive transitivity across multiple version triplets
     */
    @Test
    public void testComprehensiveTransitivity() {
        String[][] testCases = {
                // {a, b, c} where a < b < c should be transitive
                {"1", "2", "3"},
                {"1.0", "1.1", "1.2"},
                {"1.9", "1.10", "1.11"},
                {"2.0.0", "2.0.1", "2.1.0"},
                {"5.0.3a", "5.0.9", "5.0.30"},
                {"1.0-alpha", "1.0-beta", "1.0-rc"},
        };

        for (String[] versions : testCases) {
            String a = versions[0];
            String b = versions[1];
            String c = versions[2];

            int ab = Cpe.compareVersions(a, b);
            int bc = Cpe.compareVersions(b, c);
            int ac = Cpe.compareVersions(a, c);

            String msg = String.format("Transitivity check for %s, %s, %s", a, b, c);

            if (ab < 0 && bc < 0) {
                assertTrue(msg + ": if a<b and b<c then a<c", ac < 0);
            }
        }
    }

    /**
     * Test edge cases with very large numbers exceeding Long.MAX_VALUE
     */
    @Test
    public void testVeryLargeNumbers() {
        // Numbers larger than Long.MAX_VALUE should still compare correctly with BigInteger
        String veryLarge = "99999999999999999999999999999";
        String larger = "999999999999999999999999999999";

        assertTrue("Very large number comparison", Cpe.compareVersions(veryLarge, larger) < 0);
        assertTrue("Large vs small", Cpe.compareVersions(veryLarge, "100") > 0);
    }

    /**
     * Test various separator behaviors
     */
    @Test
    public void testSeparatorBehaviors() {
        // All valid separators should split tokens
        assertEquals("Dot separator", 0, Cpe.compareVersions("1.2.3", "1.2.3"));
        assertEquals("Pipe separator", 0, Cpe.compareVersions("1|2|3", "1.2.3"));
        assertEquals("Colon separator", 0, Cpe.compareVersions("1:2:3", "1.2.3"));
        assertEquals("Dash separator", 0, Cpe.compareVersions("1-2-3", "1.2.3"));

        // Mixed separators in same version
        assertEquals("Mixed separators", 0, Cpe.compareVersions("1.2-3", "1.2.3"));
    }
}
