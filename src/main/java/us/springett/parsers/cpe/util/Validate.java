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

import us.springett.parsers.cpe.exceptions.CpeValidationException;

/**
 *
 * @author Jeremy Long
 */
public final class Validate {

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
            for (int x = 0; x < value.length(); x++) {
                char c = value.charAt(x);
                if (Character.isWhitespace(c)) {
                    throw new CpeValidationException("CPE strings may not contain whitespace; consider using an underscore instead.");
                }
                if (c < 32 || c > 127) {
                    throw new CpeValidationException("CPE strings may only contain printable characters in the UTF-8 character set between x00 and x7F.");
                }
                if (c == '*' && x != 0 && value.charAt(x - 1) == '*') {
                    throw new CpeValidationException("CPE strings may not contain multiple asterisk characters in sequence.");
                }
            }
        }
    }
}
