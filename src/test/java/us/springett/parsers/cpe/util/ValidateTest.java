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

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import us.springett.parsers.cpe.exceptions.CpeValidationException;

/**
 *
 * @author Jeremy Long
 */
public class ValidateTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Test of component method, of class Validate.
     *
     * @throws Exception thrown if there is a parsing error
     */
    @Test
    public void testComponent() throws Exception {
        exception = ExpectedException.none();

        CpeValidationException validationException = null;
        try {
            String value = null;
            Validate.component(value);
            value = "";

            value = "*";
            Validate.component(value);

            value = "?";
            Validate.component(value);

            value = "-";
            Validate.component(value);

            Validate.component(value);
            value = "abc";
            Validate.component(value);
            value = "abc*";
            Validate.component(value);

            value = "*abc*";
            Validate.component(value);

            value = "*abc";
            Validate.component(value);

            value = "ab\\*c";
            Validate.component(value);

            value = "???abc???";
            Validate.component(value);

            value = "???abc";
            Validate.component(value);

            value = "abc???";
            Validate.component(value);

            value = "ab\\?c";
            Validate.component(value);

        } catch (CpeValidationException ex) {
            validationException = ex;
        }
        assertNull(validationException);
    }

    /**
     * Test of component method, of class Validate.
     *
     * @throws Exception thrown if there is a parsing error
     */
    @Test
    public void testValidateComponent2() throws Exception {
        exception.expect(CpeValidationException.class);

        char[] str = {10, 34};
        String value = new String(str);
        Validate.component(value);
    }

    /**
     * Test of component method, of class Validate.
     *
     * @throws Exception thrown if there is a parsing error
     */
    @Test
    public void testValidateComponent3() throws Exception {
        exception.expect(CpeValidationException.class);

        char[] str = {128, 34};
        String value = new String(str);
        Validate.component(value);
    }

    /**
     * Test of component method, of class Validate.
     *
     * @throws Exception thrown if there is a parsing error
     */
    @Test
    public void testValidateComponent4() throws Exception {
        exception.expect(CpeValidationException.class);

        String value = "has a space";
        Validate.component(value);
    }

    /**
     * Test of component method, of class Validate.
     *
     * @throws Exception thrown if there is a parsing error
     */
    @Test
    public void testValidateComponent5() throws Exception {
        exception.expect(CpeValidationException.class);

        String value = "**asterisk";
        Validate.component(value);
    }

    /**
     * Test of component method, of class Validate.
     *
     * @throws Exception thrown if there is a parsing error
     */
    @Test
    public void testValidateComponent6() throws Exception {
        exception.expect(CpeValidationException.class);

        String value = "\\-";
        Validate.component(value);
    }

    /**
     * Test of component method, of class Validate.
     *
     * @throws Exception thrown if there is a parsing error
     */
    @Test
    public void testValidateComponent7() throws Exception {
        exception.expect(CpeValidationException.class);

        String value = "??test?test??";
        Validate.component(value);
    }

    /**
     * Test of component method, of class Validate.
     *
     * @throws Exception thrown if there is a parsing error
     */
    @Test
    public void testValidateComponent8() throws Exception {
        exception.expect(CpeValidationException.class);

        String value = "*test*test*";
        Validate.component(value);
    }

    /**
     * Test of formatedString method, of class Validate.
     */
    @Test
    public void testFormatedString() {
        exception = ExpectedException.none();
        String value = "cpe:2.3:a:misterpark:re\\\\:kyu:1:*:*:*:*:android:*:*";
        assertTrue(Validate.formatedString(value));

        value = "cpe:2.3:o:misterpark:re\\\\:kyu:1:*:*:*:*:android:*:*";
        assertTrue(Validate.formatedString(value));

        value = "cpe:2.3:h:misterpark:re\\\\:kyu:1:*:*:*:*:android:*:*";
        assertTrue(Validate.formatedString(value));

        value = "cpe:2.3:-:misterpark:re\\\\:kyu:1:*:*:*:*:android:*:*";
        assertTrue(Validate.formatedString(value));

        value = "cpe:2.3:*:misterpark:re\\\\:kyu:1:*:*:*:*:android:*:*";
        assertTrue(Validate.formatedString(value));

        value = "cpe:2.3:t:misterpark:re\\\\:kyu:1:*:*:*:*:android:*:*";
        assertFalse(Validate.formatedString(value));

        value = "cpe:2.3:a:misterpark:re\\\\:kyu:1:*:*:*:*:android:*";
        assertFalse(Validate.formatedString(value));

        value = "cpe:2.3:a:misterpark:re\\\\:kyu:1:*:*:*:*:android:*:*:*";
        assertFalse(Validate.formatedString(value));
    }

    /**
     * Test of cpeUri method, of class Validate.
     */
    @Test
    public void testCpeUri() {
        exception = ExpectedException.none();
        String value = "cpe:/a:jlike_project:jlike:1.0::~~~joomla%21~~";
        assertTrue(Validate.cpeUri(value));

        value = "cpe:/a:jlike_project:*:1.0::~~~joomla%21~~";
        assertFalse(Validate.cpeUri(value));

        value = "cpe:/a:jlike_project::1.0::~~~joomla%21~~";
        assertTrue(Validate.cpeUri(value));

        value = "cpe:/a:vendor:product:version:update:edition:language:toomany";
        assertFalse(Validate.cpeUri(value));
    }
}
