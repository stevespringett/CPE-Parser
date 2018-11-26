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

        String value = null;
        assertFalse(Validate.component(value).isValid());

        value = "";
        assertFalse(Validate.component(value).isValid());

        value = "*";
        Validate.component(value);

        value = "?";
        assertTrue(Validate.component(value).isValid());

        value = "-";
        assertTrue(Validate.component(value).isValid());

        value = "abc";
        assertTrue(Validate.component(value).isValid());

        value = "abc*";
        assertTrue(Validate.component(value).isValid());

        value = "*abc*";
        assertTrue(Validate.component(value).isValid());

        value = "*abc";
        assertTrue(Validate.component(value).isValid());

        value = "ab\\*c";
        assertTrue(Validate.component(value).isValid());

        value = "???abc???";
        assertTrue(Validate.component(value).isValid());

        value = "???abc";
        assertTrue(Validate.component(value).isValid());

        value = "abc???";
        assertTrue(Validate.component(value).isValid());

        value = "ab\\?c";
        assertTrue(Validate.component(value).isValid());

        value = "*?foobar";
        assertTrue(Validate.component(value).isValid());

        value = "foobar?*";
        assertTrue(Validate.component(value).isValid());

        char[] str = {10, 34};
        value = new String(str);
        assertFalse(Validate.component(value).isValid());

        char[] str2 = {128, 34};
        value = new String(str2);
        assertFalse(Validate.component(value).isValid());

        value = "has a space";
        assertFalse(Validate.component(value).isValid());

        value = "**asterisk";
        assertFalse(Validate.component(value).isValid());

        value = "\\-";
        assertFalse(Validate.component(value).isValid());

        value = "??test?test??";
        assertFalse(Validate.component(value).isValid());

        value = "??test*test??";
        assertFalse(Validate.component(value).isValid());

    }

    /**
     * Test of formattedString method, of class Validate.
     */
    @Test
    public void testFormattedString() {
        exception = ExpectedException.none();
        String value = "cpe:2.3:a:misterpark:re\\\\:kyu:1:*:*:*:*:android:*:*";
        assertTrue(Validate.formattedString(value).isValid());

        value = "cpe:2.3:o:misterpark:re\\\\:kyu:1:*:*:*:*:android:*:*";
        assertTrue(Validate.formattedString(value).isValid());

        value = "cpe:2.3:h:misterpark:re\\\\:kyu:1:*:*:*:*:android:*:*";
        assertTrue(Validate.formattedString(value).isValid());

        value = "cpe:2.3:-:misterpark:re\\\\:kyu:1:*:*:*:*:android:*:*";
        assertTrue(Validate.formattedString(value).isValid());

        value = "cpe:2.3:*:misterpark:re\\\\:kyu:1:*:*:*:*:android:*:*";
        assertTrue(Validate.formattedString(value).isValid());

        value = "cpe:2.3:t:misterpark:re\\\\:kyu:1:*:*:*:*:android:*:*";
        assertFalse(Validate.formattedString(value).isValid());

        value = "cpe:2.3:a:misterpark:re\\\\:kyu:1:*:*:*:*:android:*";
        assertFalse(Validate.formattedString(value).isValid());

        value = "cpe:2.3:a:misterpark:re\\\\:kyu:1:*:*:*:*:android:*:*:*";
        assertFalse(Validate.formattedString(value).isValid());

        value = "cpe:a:misterpark:re\\\\:kyu:1:*:*:*:*:android:*:*:*";
        assertFalse(Validate.formattedString(value).isValid());

        value = "cpe:2.3:a:miste*rpark:re\\\\:kyu:1:*:*:*:*:android:*:*:*";
        assertFalse(Validate.formattedString(value).isValid());

        value = "cpe:2.3:a:misterpark:re?kyu:1:*:*:*:*:android:*:*:*";
        assertFalse(Validate.formattedString(value).isValid());

        value = "cpe:2.3:a:misterpark:re\\\\:kyu::*:*:*:*:android:*:*:*";
        assertFalse(Validate.formattedString(value).isValid());

        value = "cpe:2.3:a:misterpark:re\\\\:kyu:1:**:*:*:*:android:*:*";
        assertFalse(Validate.formattedString(value).isValid());
        value = "cpe:2.3:a:misterpark:re\\\\:kyu:1:*:**:*:*:android:*:*";
        assertFalse(Validate.formattedString(value).isValid());
        value = "cpe:2.3:a:misterpark:re\\\\:kyu:1:*:*:**:*:android:*:*";
        assertFalse(Validate.formattedString(value).isValid());
        value = "cpe:2.3:a:misterpark:re\\\\:kyu:1:*:*:*:**:android:*:*";
        assertFalse(Validate.formattedString(value).isValid());
        value = "cpe:2.3:a:misterpark:re\\\\:kyu:1:*:*:*:*:and?roid:*:*";
        assertFalse(Validate.formattedString(value).isValid());
        value = "cpe:2.3:a:misterpark:re\\\\:kyu:1:*:*:*:*:android:**:*";
        assertFalse(Validate.formattedString(value).isValid());
        value = "cpe:2.3:a:misterpark:re\\\\:kyu:1:*:*:*:*:android:*:**";
        assertFalse(Validate.formattedString(value).isValid());
        value = "cpe:2.3:a:misterpark:re\\\\:kyu:1:*:*:*:*:android:*:*:**";
        assertFalse(Validate.formattedString(value).isValid());
    }

    /**
     * Test of cpeUri method, of class Validate.
     */
    @Test
    public void testCpeUri() {
        exception = ExpectedException.none();
        String value = "cpe:/a:jlike_project:jlike:1.0::~~~joomla%21~~";
        assertTrue(Validate.cpeUri(value).isValid());

        value = "cpe:/a:jlike_project:*:1.0::~~~joomla%21~~";
        assertFalse(Validate.cpeUri(value).isValid());

        value = "cpe:/a:jlike_project::1.0::~~~joomla%21~~";
        assertTrue(Validate.cpeUri(value).isValid());

        value = "cpe:/a:vendor:product:version:update:edition:language:toomany";
        assertFalse(Validate.cpeUri(value).isValid());
    }
    
    /**
     * Test of cpe method, of class Validate.
     */
    @Test
    public void testCpe() {
        exception = ExpectedException.none();
        String value = "cpe:/a:jlike_project:jlike:1.0::~~~joomla%21~~";
        assertTrue(Validate.cpe(value).isValid());
        
        value = "cpe:/a:jlike_pro**ject:jlike:1.0::~~~joomla%21~~";
        assertFalse(Validate.cpe(value).isValid());
        
        value = "cpe:2.3:a:misterpark:re\\\\:kyu:1:*:*:*:*:android:*:*";
        assertTrue(Validate.cpe(value).isValid());
        
        value = "cpe:2.3:a:\\\\-:re\\\\:kyu:1:*:*:*:*:android:*:*:*";
        assertFalse(Validate.cpe(value).isValid());
    }
}
