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
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.springett.parsers.cpe.exceptions.CpeEncodingException;
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
     * @return the validation status given value;
     * @see us.springett.parsers.cpe.util.Status#isValid()
     */
    public static Status component(String value) {
        if (value != null && !value.isEmpty()) {
            if ("\\-".equals(value)) {
                return Status.SINGLE_QUOTED_HYPHEN;
            }
            for (int x = 0; x < value.length(); x++) {
                char c = value.charAt(x);
                if (c == '?' && x > 0 && x < value.length() - 1
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
     * @return the validation status given value;
     * @see us.springett.parsers.cpe.util.Status#isValid()
     */
    public static Status formattedString(String value) {
        boolean result = true;
        try {
            Cpe23PartIterator instance;
            try {
                instance = new Cpe23PartIterator(value);
            } catch (CpeParsingException ex) {
                LOG.warn("The CPE ({}) is invalid as it is not in the formatted string format", value);
                return Status.INVALID;
            }
            try {
                //part
                Part.getEnum(instance.next());
            } catch (CpeParsingException ex) {
                LOG.warn("The CPE ({}) is invalid as it has an invalid part attribute", value);
                return Status.INVALID_PART;
            }
            Status status;
            //vendor
            status = Validate.component(instance.next());
            if (!status.isValid()) {
                LOG.warn("The CPE ({}) has an invalid vendor - {}", value, status.getMessage());
                return status;
            }
            //product
            status = Validate.component(instance.next());
            if (!status.isValid()) {
                LOG.warn("The CPE ({}) has an invalid product - {}", value, status.getMessage());
                return status;
            }
            //version
            status = Validate.component(instance.next());
            if (!status.isValid()) {
                LOG.warn("The CPE ({}) has an version version - {}", value, status.getMessage());
                return status;
            }
            //update
            status = Validate.component(instance.next());
            if (!status.isValid()) {
                LOG.warn("The CPE ({}) has an invalid update - {}", value, status.getMessage());
                return status;
            }
            //edition
            status = Validate.component(instance.next());
            if (!status.isValid()) {
                LOG.warn("The CPE ({}) has an invalid edition - {}", value, status.getMessage());
                return status;
            }
            //language
            status = Validate.component(instance.next());
            if (!status.isValid()) {
                LOG.warn("The CPE ({}) has an invalid language - {}", value, status.getMessage());
                return status;
            }
            //swEdition
            status = Validate.component(instance.next());
            if (!status.isValid()) {
                LOG.warn("The CPE ({}) has an invalid swEdition - {}", value, status.getMessage());
                return status;
            }
            //targetSw
            status = Validate.component(instance.next());
            if (!status.isValid()) {
                LOG.warn("The CPE ({}) has an invalid targetSw - {}", value, status.getMessage());
                return status;
            }
            //targetHw
            status = Validate.component(instance.next());
            if (!status.isValid()) {
                LOG.warn("The CPE ({}) has an invalid targetHw - {}", value, status.getMessage());
                return status;
            }
            //other
            status = Validate.component(instance.next());
            if (!status.isValid()) {
                LOG.warn("The CPE ({}) has an invalid other attribute - {}", value, status.getMessage());
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
     * @return the validation status given value;
     * @see us.springett.parsers.cpe.util.Status#isValid()
     */
    public static Status cpeUri(String value) {
        try {
            String[] parts = value.split(":");
            if (parts.length > 8 || parts.length == 1 || !"cpe".equalsIgnoreCase(parts[0])) {
                LOG.warn("The CPE ({}) is invalid as it is not in the CPE 2.2 URI format", value);
                return Status.INVALID;
            }
            if (parts.length >= 2 && parts[1].length() == 2) {
                boolean found = false;
                String a = parts[1].substring(1);
                for (Part p : Part.values()) {
                    if (p.getAbbreviation().equals(a)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    LOG.warn("The CPE ({}) is invalid as it has an invalid part attribute", value);
                    return Status.INVALID_PART;
                }
            } else {
                LOG.warn("The CPE ({}) is invalid as it has an invalid part attribute", value);
                return Status.INVALID_PART;
            }
            if (parts.length > 2) {
                if ("*".equals(parts[2])) {
                    LOG.warn("The CPE ({}) has an invalid vendor - asterisk", value);
                    return Status.INVALID;
                }
                Status s = component(Convert.cpeUriToWellFormed(parts[2]));
                if (!s.isValid() || "*".equals(parts[2])) {
                    LOG.warn("The CPE ({}) has an invalid vendor - {}", value, s.getMessage());
                    return s;
                }
            }
            if (parts.length > 3) {
                if ("*".equals(parts[3])) {
                    LOG.warn("The CPE ({}) has an invalid product - asterisk", value);
                    return Status.INVALID;
                }
                Status s = component(Convert.cpeUriToWellFormed(parts[3]));
                if (!s.isValid()) {
                    LOG.warn("The CPE ({}) has an invalid product - {}", value, s.getMessage());
                    return s;
                }
            }
            if (parts.length > 4) {
                if ("*".equals(parts[4])) {
                    LOG.warn("The CPE ({}) has an invalid version - asterisk", value);
                    return Status.INVALID;
                }
                Status s = component(Convert.cpeUriToWellFormed(parts[4]));
                if (!s.isValid()) {
                    LOG.warn("The CPE ({}) has an invalid version - {}", value, s.getMessage());
                    return s;
                }
            }
            if (parts.length > 5) {
                if ("*".equals(parts[5])) {
                    LOG.warn("The CPE ({}) has an invalid update - asterisk", value);
                    return Status.INVALID;
                }
                Status s = component(Convert.cpeUriToWellFormed(parts[5]));
                if (!s.isValid()) {
                    LOG.warn("The CPE ({}) has an invalid update - {}", value, s.getMessage());
                    return s;
                }
            }
            if (parts.length > 6) {
                if (parts[6].startsWith("~")) {
                    if (countCharacter(parts[6], '~') != 5) {
                        LOG.warn("The CPE ({}) has an invalid packed edition - too many entries", value);
                        return Status.INVALID;
                    }
                    String[] unpacked = parts[6].split("~");
                    if (unpacked.length > 1) {
                        if ("*".equals(unpacked[1])) {
                            LOG.warn("The CPE ({}) has an invalid packed edition - asterisk", value);
                            return Status.INVALID;
                        }
                        Status s = component(Convert.cpeUriToWellFormed(unpacked[1]));
                        if (!s.isValid()) {
                            LOG.warn("The CPE ({}) has an invalid packed edition - {}", value, s.getMessage());
                            return s;
                        }
                    }
                    if (unpacked.length > 2) {
                        if ("*".equals(unpacked[2])) {
                            LOG.warn("The CPE ({}) has an invalid packed sw_edition - asterisk", value);
                            return Status.INVALID;
                        }
                        Status s = component(Convert.cpeUriToWellFormed(unpacked[2]));
                        if (!s.isValid()) {
                            LOG.warn("The CPE ({}) has an invalid packed sw_edition - {}", value, s.getMessage());
                            return s;
                        }
                    }
                    if (unpacked.length > 3) {
                        if ("*".equals(unpacked[3])) {
                            LOG.warn("The CPE ({}) has an invalid packed target_sw - asterisk", value);
                            return Status.INVALID;
                        }
                        Status s = component(Convert.cpeUriToWellFormed(unpacked[3]));
                        if (!s.isValid()) {
                            LOG.warn("The CPE ({}) has an invalid packed target_sw - {}", value, s.getMessage());
                            return s;
                        }
                    }
                    if (unpacked.length > 4) {
                        if ("*".equals(unpacked[4])) {
                            LOG.warn("The CPE ({}) has an invalid packed target_hw - asterisk", value);
                            return Status.INVALID;
                        }
                        Status s = component(Convert.cpeUriToWellFormed(unpacked[4]));
                        if (!s.isValid()) {
                            LOG.warn("The CPE ({}) has an invalid packed target_hw - {}", value, s.getMessage());
                            return s;
                        }
                    }
                    if (unpacked.length > 5) {
                        if ("*".equals(unpacked[5])) {
                            LOG.warn("The CPE ({}) has an invalid packed other - asterisk", value);
                            return Status.INVALID;
                        }
                        Status s = component(Convert.cpeUriToWellFormed(unpacked[5]));
                        if (!s.isValid()) {
                            LOG.warn("The CPE ({}) has an invalid packed other - {}", value, s.getMessage());
                            return s;
                        }
                    }
                } else {
                    if ("*".equals(parts[6])) {
                        LOG.warn("The CPE ({}) has an invalid edition - asterisk", value);
                        return Status.INVALID;
                    }
                    Status s = component(Convert.cpeUriToWellFormed(parts[6]));
                    if (!s.isValid()) {
                        LOG.warn("The CPE ({}) has an invalid edition - {}", value, s.getMessage());
                        return s;
                    }
                }
            }
            if (parts.length > 7) {
                if ("*".equals(parts[7])) {
                    LOG.warn("The CPE ({}) has an invalid language - asterisk", value);
                    return Status.INVALID;
                }
                Status s = component(Convert.cpeUriToWellFormed(parts[7]));
                if (!s.isValid()) {
                    LOG.warn("The CPE ({}) has an invalid language - {}", value, s.getMessage());
                    return s;
                }
            }
        } catch (CpeEncodingException ex) {
            LOG.warn("The CPE ({}) has an unencoded special characters", value);
            return Status.INVALID;
        }
        return Status.VALID;
    }

    /**
     * Counts the number of times the char c is contained in the value.
     *
     * @param value the string to search
     * @param c the character to count
     * @return the number of times the char c is contained in the value
     */
    private static long countCharacter(String value, char c) {
        return value.chars().filter(s -> s == c).count();
    }

    /**
     * Validates the given CPE string value to ensure it is either a valid CPE
     * URI or Formatted String.
     *
     * @param value the CPE to validate
     * @return the validation status given value;
     * @see us.springett.parsers.cpe.util.Status#isValid()
     */
    public static Status cpe(String value) {
        if ("cpe:2.3:".regionMatches(0, value, 0, 8)) {
            return formattedString(value);
        }
        return cpeUri(value);
    }
}
