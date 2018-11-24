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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.springett.parsers.cpe.exceptions.CpeParsingException;
import us.springett.parsers.cpe.internal.util.Cpe23PartIterator;
import us.springett.parsers.cpe.values.Part;

/**
 * Validation utility to validate CPE URI, Formated Strings, and the individual
 * attributes of a CPE.
 *
 * @author Jeremy Long
 */
public final class Validate {

    /**
     * A reference to the logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(Validate.class);
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
     * @return the validation status of the component
     */
    public static Status component(String value) {
        if (value != null && !value.isEmpty()) {
            if ("\\-".equals(value)) {
                return Status.SINGLE_QUOTED_HYPHEN;
            }
            for (int x = 0; x < value.length(); x++) {
                char c = value.charAt(x);
                if (c == '?' && x > 0
                        && !((value.charAt(x - 1) == '?' || value.charAt(x - 1) == '*' || value.charAt(x - 1) == '\\')
                        || (x < value.length() - 1 && (value.charAt(x + 1) == '?' || value.charAt(x + 1) == '*')))) {
                    return Status.UNQUOTED_QUESTION_MARK;
                } else if (Character.isWhitespace(c)) {
                    return Status.WHITESPACE;
                } else if (c < 32 || c > 127) {
                    return Status.NON_PRINTABLE;
                } else if (c == '*' && x != 0 && value.charAt(x - 1) == '*') {
                    return Status.ASTERISK_SEQUENCE;
                } else if (c == '*' && !((x == 0 || x == value.length() - 1)
                        || (x > 0 && '\\' == value.charAt(x - 1)))) {
                    return Status.UNQUOTED_ASTERISK;
                }
            }
            return Status.VALID;
        }
        return Status.EMPTY;
    }

    /**
     * Validates the formatted string against the CPE 2.3 specification.
     *
     * @param value the value to validate
     * @return <code>true</code> if the provided value is a valid CPE 2.3
     * formated string; otherwise <code>false</code>
     */
    public static Status formattedString(String value) {
        boolean result = true;
        try {
            Cpe23PartIterator instance;
            try {
                instance = new Cpe23PartIterator(value);
            } catch (CpeParsingException ex) {
                LOG.warn("The CPE is invalid as it is not in the formatted string format", ex);
                return Status.INVALID;
            }
            try {
                //part
                Part.getEnum(instance.next());
            } catch (CpeParsingException ex) {
                LOG.warn("The CPE is invalid as it has an invalid part attribute", ex);
                return Status.INVALID_PART;
            }
            Status status;
            //vendor
            status = Validate.component(instance.next());
            if (!status.isValid()) {
                LOG.warn("The CPE has an invalid vendor - " + status.getMessage());
                return status;
            }
            //product
            status = Validate.component(instance.next());
            if (!status.isValid()) {
                LOG.warn("The CPE has an invalid product - " + status.getMessage());
                return status;
            }
            //version
            status = Validate.component(instance.next());
            if (!status.isValid()) {
                LOG.warn("The CPE has an version vendor - " + status.getMessage());
                return status;
            }
            //update
            status = Validate.component(instance.next());
            if (!status.isValid()) {
                LOG.warn("The CPE has an invalid update - " + status.getMessage());
                return status;
            }
            //edition
            status = Validate.component(instance.next());
            if (!status.isValid()) {
                LOG.warn("The CPE has an invalid edition - " + status.getMessage());
                return status;
            }
            //language
            status = Validate.component(instance.next());
            if (!status.isValid()) {
                LOG.warn("The CPE has an invalid language - " + status.getMessage());
                return status;
            }
            //swEdition
            status = Validate.component(instance.next());
            if (!status.isValid()) {
                LOG.warn("The CPE has an invalid swEdition - " + status.getMessage());
                return status;
            }
            //targetSw
            status = Validate.component(instance.next());
            if (!status.isValid()) {
                LOG.warn("The CPE has an invalid targetSw - " + status.getMessage());
                return status;
            }
            //targetHw
            status = Validate.component(instance.next());
            if (!status.isValid()) {
                LOG.warn("The CPE has an invalid targetHw - " + status.getMessage());
                return status;
            }
            //other
            status = Validate.component(instance.next());
            if (!status.isValid()) {
                LOG.warn("The CPE has an invalid other attribute - " + status.getMessage());
                return status;
            }
            if (instance.hasNext()) {
                LOG.warn(Status.TOO_MANY_ELEMENTS.getMessage());
                return Status.TOO_MANY_ELEMENTS;
            }
        } catch (NoSuchElementException ex) {
            LOG.warn(Status.TOO_FEW_ELEMENTS.getMessage());
            return Status.TOO_FEW_ELEMENTS;
        }
        return Status.VALID;
    }

    /**
     * Validates the CPE URI against the CPE 2.2 specification.
     *
     * @param value the value to validate
     * @return <code>true</code> if the provided value is a valid CPE 2.2
     * formated string; otherwise <code>false</code>
     */
    public static Status cpeUri(String value) {
        Matcher m = CPE_URI.matcher(value);
        if (m.matches()) {
            return Status.VALID;
        }
        return Status.INVALID;
    }

    /**
     * Validates the given CPE string value to ensure it is either a valid CPE
     * URI or Formatted String.
     *
     * @param value the CPE to validate
     * @return <code>true</code> if the CPE is valid; otherwise
     * <code>false</code>
     */
    public static Status cpe(String value) {
        if ("cpe:2.3:".regionMatches(0, value, 0, 8)) {
            return formattedString(value);
        }
        return cpeUri(value);
    }
}
