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
package us.springett.parsers.cpe.internal.util;

import us.springett.parsers.cpe.exceptions.CpeParsingException;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.*;

/**
 *
 * @author Jeremy Long
 */
public class Cpe23PartIteratorTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Test of hasNext method, of class Cpe23PartIterator.
     *
     * @throws CpeParsingException should not be thrown
     */
    @Test
    public void testHasNext() throws CpeParsingException {
        Cpe23PartIterator instance = new Cpe23PartIterator("cpe:2.3:a:pocoproject:poco_c\\+\\+_libraries:1.4.5:*:*:*:*:*:*:*");
        boolean expResult = true;
        boolean result = instance.hasNext();
        assertEquals(expResult, result);
    }

    /**
     * Test of constructor method, of class Cpe23PartIterator.
     *
     * @throws CpeParsingException expected exception
     */
    @Test
    public void testConstructorException() throws CpeParsingException {
        exception.expect(CpeParsingException.class);
        Cpe23PartIterator instance = new Cpe23PartIterator("invalid:cpe");
    }

    /**
     * Test of constructor method, of class Cpe23PartIterator.
     *
     * @throws CpeParsingException expected exception
     */
    @Test
    public void testConstructorException1() throws CpeParsingException {
        exception.expect(CpeParsingException.class);
        Cpe23PartIterator instance = new Cpe23PartIterator(null);
    }

    /**
     * Test of next method, of class Cpe23PartIterator.
     *
     * @throws CpeParsingException should not be thrown
     */
    @Test
    public void testNext() throws CpeParsingException {
        Cpe23PartIterator instance = new Cpe23PartIterator("cpe:2.3:a:poco\\:project:poco_c\\+\\+_libraries:1.4.5:u\\\\:*:*:*:*:*:*");
        String[] expResults = {"a", "poco\\:project", "poco_c\\+\\+_libraries", "1.4.5", "u\\\\", "*", "*", "*", "*", "*", "*"};

        for (String expResult : expResults) {
            String result = instance.next();
            assertEquals(expResult, result);
        }
    }
}
