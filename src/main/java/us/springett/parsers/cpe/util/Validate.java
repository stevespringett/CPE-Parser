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

import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import us.springett.parsers.cpe.exceptions.CpeParsingException;
import us.springett.parsers.cpe.exceptions.CpeValidationException;
import us.springett.parsers.cpe.internal.util.Cpe23PartIterator;
import us.springett.parsers.cpe.values.Part;

/**
 *
 * @author Jeremy Long
 */
public final class Validate {

    /**
     * The CPE URI validation regular expression.
     */
    private static final Pattern CPE_URI = Pattern.compile("^[c][pP][eE]:/[AHOaho]?(:[A-Za-z0-9._~%-]*){0,6}$");

    /**
     * Private constructor for utility class.
     */
    private Validate() {
        //empty
    }

    /**
     * Validates the component to ensure it meets the CPE 2.3 specification of
     * allowed values.
     *
     * @param value the value to validate
     * @throws CpeValidationException thrown if the string is invalid
     */
    public static void component(String value) throws CpeValidationException {
        if (value != null && !value.isEmpty()) {
            if ("\\-".equals(value)) {
                throw new CpeValidationException("CPE components cannot be a single quoted hyphen.");
            }
            for (int x = 0; x < value.length(); x++) {
                char c = value.charAt(x);
                if (c == '?' && x > 0
                        && !((value.charAt(x - 1) == '?' || value.charAt(x - 1) == '\\')
                        || (x < value.length() - 1 && value.charAt(x + 1) == '?'))) {
                    throw new CpeValidationException("CPE Strings may not contain unquoted question marks except at the beginning or end of the string");
                } else if (Character.isWhitespace(c)) {
                    throw new CpeValidationException("CPE strings may not contain whitespace; consider using an underscore instead.");
                } else if (c < 32 || c > 127) {
                    throw new CpeValidationException("CPE strings may only contain printable characters in the UTF-8 character set between x00 and x7F.");
                } else if (c == '*' && x != 0 && value.charAt(x - 1) == '*') {
                    throw new CpeValidationException("CPE strings may not contain multiple asterisk characters in sequence.");
                } else if (c == '*' && !((x == 0 || x == value.length() - 1)
                        || (x > 0 && '\\' == value.charAt(x - 1)))) {
                    throw new CpeValidationException("CPE strings may only contain unquoted asterisk at the beginning or end of the string.");
                }
            }
        }
    }

    /**
     * Validates the formatted string against the CPE 2.3 specification.
     *
     * @param value the value to validate
     * @return <code>true</code> if the provided value is a valid CPE 2.3
     * formated string; otherwise <code>false</code>
     */
    public static boolean formatedString(String value) {
        boolean result = true;
        try {
            Cpe23PartIterator instance = new Cpe23PartIterator(value);
            //part
            Part.getEnum(instance.next());
            //vendor
            Validate.component(instance.next());
            //product
            Validate.component(instance.next());
            //version
            Validate.component(instance.next());
            //update
            Validate.component(instance.next());
            //edition
            Validate.component(instance.next());
            //language
            Validate.component(instance.next());
            //swEdition
            Validate.component(instance.next());
            //targetSw
            Validate.component(instance.next());
            //targetHw
            Validate.component(instance.next());
            //other
            Validate.component(instance.next());
            if (instance.hasNext()) {
                return false;
            }
        } catch (CpeParsingException | CpeValidationException | NoSuchElementException ex) {
            return false;
        }
        return true;
    }

    /**
     * Validates the CPE URI against the CPE 2.2 specification.
     *
     * @param value the value to validate
     * @return <code>true</code> if the provided value is a valid CPE 2.2
     * formated string; otherwise <code>false</code>
     */
    public static boolean cpeUri(String value) {
        Matcher m = CPE_URI.matcher(value);
        return m.matches();
    }
}
