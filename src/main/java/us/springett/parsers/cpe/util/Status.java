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

/**
 *
 * @author Jeremy Long
 */
public enum Status {
    /**
     * The CPE or CPE component is valid.
     */
    VALID(true, "The CPE value is valid"),
    /**
     * Unquoted question marks are not allowed except at the beginning or end of
     * a CPE component.
     */
    UNQUOTED_QUESTION_MARK(false, "CPE Strings may not contain unquoted question marks except at the beginning or end of the string"),
    /**
     * Unquoted asterisk characters are not allowed except at the beginning or
     * end of a CPE component.
     */
    UNQUOTED_ASTERISK(false, "CPE strings may only contain unquoted asterisk at the beginning or end of the string"),
    /**
     * A CPE component may not contain a sequence of asterisk characters.
     */
    ASTERISK_SEQUENCE(false, "CPE strings may not contain multiple asterisk characters in sequence"),
    /**
     * A CPE component must only contain printable characters.
     */
    NON_PRINTABLE(false, "CPE strings may only contain printable characters in the UTF-8 character set between x00 and x7F"),
    /**
     * A CPE component must not contain whitespace.
     */
    WHITESPACE(false, "CPE strings may not contain whitespace; consider using an underscore instead"),
    /**
     * A CPE component cannot be a single quoted hyphen.
     */
    SINGLE_QUOTED_HYPHEN(false, "CPE components cannot be a single quoted hyphen"),
    /**
     * The CPE component is either empty or null.
     */
    EMPTY(false, "CPE components may not be empty or null"),
    /**
     * The CPE has too many components.
     */
    TOO_MANY_ELEMENTS(false, "The CPE value has too many components"),
    /**
     * The CPE has too few components.
     */
    TOO_FEW_ELEMENTS(false, "The CPE value has too few components"),
    /**
     * The CPE has an invalid part defined.
     */
    INVALID_PART(false, "The CPE value has an invalid part defined"),
    /**
     * The CPE value is invalid.
     */
    INVALID(false, "The CPE valud is invalid");

    /**
     * Flag indicating whether or not the status is considered valid.
     */
    private final boolean valid;
    /**
     * The message for the status.
     */
    private final String message;

    /**
     * Constructs a new bind value.
     *
     * @param valid a boolean indicating whether or not the status is considered
     * valid.
     * @param message the message for the given status
     */
    Status(boolean valid, String message) {
        this.valid = valid;
        this.message = message;
    }

    /**
     * Returns whether or not the status is considered valid.
     *
     * @return <code>true</code> if the status is valid; otherwise
     * <code>false</code>
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Gets the status message.
     *
     * @return the status message
     */
    public String getMessage() {
        return message;
    }
}
