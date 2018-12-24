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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
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
        expResult = false;
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

        expResult = false;
        result = instance.equals(null);
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
        assertFalse(Cpe.compareAttributes(Part.APPLICATION, Part.ANY));

        assertTrue(Cpe.compareAttributes(Part.ANY, Part.ANY));
        assertTrue(Cpe.compareAttributes(Part.ANY, Part.APPLICATION));
        assertTrue(Cpe.compareAttributes(Part.ANY, Part.OPERATING_SYSTEM));
        assertTrue(Cpe.compareAttributes(Part.ANY, Part.HARDWARE_DEVICE));
        assertTrue(Cpe.compareAttributes(Part.ANY, Part.NA));

        assertTrue(Cpe.compareAttributes(Part.NA, Part.NA));
        assertFalse(Cpe.compareAttributes(Part.NA, Part.ANY));
        assertFalse(Cpe.compareAttributes(Part.NA, Part.APPLICATION));
        assertFalse(Cpe.compareAttributes(Part.NA, Part.HARDWARE_DEVICE));
        assertFalse(Cpe.compareAttributes(Part.NA, Part.OPERATING_SYSTEM));
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
        assertTrue(Cpe.compareVersions(left, right) < 0);

        left = "5.1.932";
        right = "5.1.9223372036854775807152";
        assertTrue(Cpe.compareVersions(left, right) > 0);

        left = "alpha";
        right = "alpha";
        assertTrue(Cpe.compareVersions(left, right) == 0);
    }
}
