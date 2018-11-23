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
package us.springett.parsers.cpe.values;

import us.springett.parsers.cpe.exceptions.CpeParsingException;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.*;

/**
 *
 * @author Jeremy Long
 */
public class PartTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Test of values method, of class Part.
     */
    @Test
    public void testValues() {
        exception = ExpectedException.none();

        Part[] expResult = {Part.APPLICATION, Part.OPERATING_SYSTEM, Part.HARDWARE_DEVICE, Part.ANY, Part.NA};
        Part[] result = Part.values();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of valueOf method, of class Part.
     */
    @Test
    public void testValueOf() {
        exception = ExpectedException.none();

        String name = "APPLICATION";
        Part expResult = Part.APPLICATION;
        Part result = Part.valueOf(name);
        assertEquals(expResult, result);

        name = "OPERATING_SYSTEM";
        expResult = Part.OPERATING_SYSTEM;
        result = Part.valueOf(name);
        assertEquals(expResult, result);

        name = "HARDWARE_DEVICE";
        expResult = Part.HARDWARE_DEVICE;
        result = Part.valueOf(name);
        assertEquals(expResult, result);

        name = "ANY";
        expResult = Part.ANY;
        result = Part.valueOf(name);
        assertEquals(expResult, result);

        name = "NA";
        expResult = Part.NA;
        result = Part.valueOf(name);
        assertEquals(expResult, result);
    }

    /**
     * Test of getAbbreviation method, of class Part.
     */
    @Test
    public void testGetAbbreviation() {
        exception = ExpectedException.none();

        Part instance = Part.APPLICATION;
        String expResult = "a";
        String result = instance.getAbbreviation();
        assertEquals(expResult, result);

        instance = Part.OPERATING_SYSTEM;
        expResult = "o";
        result = instance.getAbbreviation();
        assertEquals(expResult, result);

        instance = Part.HARDWARE_DEVICE;
        expResult = "h";
        result = instance.getAbbreviation();
        assertEquals(expResult, result);

        instance = Part.ANY;
        expResult = "*";
        result = instance.getAbbreviation();
        assertEquals(expResult, result);

        instance = Part.NA;
        expResult = "-";
        result = instance.getAbbreviation();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEnum method, of class Part.
     *
     * @throws CpeParsingException thrown if there is a parsing error
     */
    @Test
    public void testGetEnum() throws CpeParsingException {
        exception = ExpectedException.none();

        String part = "a";
        Part expResult = Part.APPLICATION;
        Part result = Part.getEnum(part);
        assertEquals(expResult, result);

        part = "o";
        expResult = Part.OPERATING_SYSTEM;
        result = Part.getEnum(part);
        assertEquals(expResult, result);

        part = "h";
        expResult = Part.HARDWARE_DEVICE;
        result = Part.getEnum(part);
        assertEquals(expResult, result);

        part = "*";
        expResult = Part.ANY;
        result = Part.getEnum(part);
        assertEquals(expResult, result);

        part = "-";
        expResult = Part.NA;
        result = Part.getEnum(part);
        assertEquals(expResult, result);
    }

    /**
     * Test of getEnum method, of class Part.
     *
     * @throws CpeParsingException thrown if there is a parsing error
     */
    @Test
    public void testGetEnumInvalid() throws CpeParsingException {
        exception.expect(CpeParsingException.class);

        String part = "x";
        Part expResult = Part.APPLICATION;
        Part result = Part.getEnum(part);
        assertEquals(expResult, result);
    }
}
