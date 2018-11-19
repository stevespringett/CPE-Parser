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

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jeremy Long
 */
public class BindValueTest {

    /**
     * Test of values method, of class BindValue.
     */
    @Test
    public void testValues() {
        BindValue[] expResult = {BindValue.ANY, BindValue.NA};
        BindValue[] result = BindValue.values();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of valueOf method, of class BindValue.
     */
    @Test
    public void testValueOf() {
        String name = "ANY";
        BindValue expResult = BindValue.ANY;
        BindValue result = BindValue.valueOf(name);
        assertEquals(expResult, result);

        name = "NA";
        expResult = BindValue.NA;
        result = BindValue.valueOf(name);
        assertEquals(expResult, result);
    }

    /**
     * Test of getAbbreviation method, of class BindValue.
     */
    @Test
    public void testGetValue() {
        BindValue instance = BindValue.ANY;
        String expResult = "*";
        String result = instance.getAbbreviation();
        assertEquals(expResult, result);

        instance = BindValue.NA;
        expResult = "-";
        result = instance.getAbbreviation();
        assertEquals(expResult, result);
    }

}
