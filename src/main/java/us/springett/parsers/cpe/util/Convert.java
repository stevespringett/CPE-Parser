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

import us.springett.parsers.cpe.exceptions.CpeEncodingException;
import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.springett.parsers.cpe.values.LogicalValue;
import us.springett.parsers.cpe.values.Part;

/**
 * Set of methods to encode and decode text based on the CPE specification.
 *
 * @author Jeremy Long
 */
public final class Convert {

    /**
     * Hexadecimal character sequence used for encoding.
     */
    private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();
    /**
     * A reference to the logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(Convert.class);

    /**
     * Private constructor for utility class.
     */
    private Convert() {

    }

    /**
     * Transforms the given string into a "well formed string". A well formed
     * string has all non-alphanumeric characters escaped with a backslash. If a
     * <code>null</code> value is passed in the default {@link LogicalValue#ANY}
     * will be returned.
     *
     * @param value the string to format
     * @return the well formed string
     */
    public static String toWellFormed(String value) {
        if (value == null) {
            return LogicalValue.ANY.getAbbreviation();
        }
        if (LogicalValue.ANY.getAbbreviation().equals(value)
                || LogicalValue.NA.getAbbreviation().equals(value)) {
            return value;
        }
        //return value.replaceAll("([^0-9A-Za-z])", "\\\\$1");
        StringBuilder buffer = new StringBuilder(value);
        for (int x = 0; x < buffer.length(); x++) {
            char c = buffer.charAt(x);
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9'))) {
                buffer.insert(x++, '\\');
            }
        }
        return buffer.toString();
    }

    /**
     * Transforms the given well formed string into a non-escaped string. A well
     * formed string has all non-alphanumeric characters escaped with a
     * backslash.
     *
     * @param value the well formed string
     * @return the string value represented by the well formed string
     */
    public static String fromWellFormed(String value) {
        if (value == null) {
            return LogicalValue.ANY.getAbbreviation();
        }
        //return value.replaceAll("\\\\([^0-9A-Za-z])", "$1");
        StringBuilder buffer = new StringBuilder(value);
        char p = ' ';
        for (int x = 0; x < buffer.length() - 1; x++) {
            char c = buffer.charAt(x);
            if (c == '\\' && p != '\\') {
                buffer.delete(x, x + 1);
                x -= 1;
            }
            p = c;
        }
        return buffer.toString();
    }

    /**
     * URL encodes the value for CPE 2.2. If the value is NULL or ANY ('*') then
     * an empty string is returned. If the value is a hyphen ('-') the hyphen is
     * returned un-encoded; otherwise URL encoding is applied.
     *
     * @param value the value to encode
     * @return the encoded value
     */
    public static String wellFormedToCpeUri(Part value) {
        if (value == null) {
            return Part.ANY.getAbbreviation();
        }
        return value.getAbbreviation();
    }

    /**
     * CPE URL encodes the well formed string. If the value is NULL or ANY ('*')
     * then an empty string is returned. If the value is a hyphen ('-') the
     * hyphen is returned un-encoded; otherwise URL encoding is applied as
     * specified in the CPE 2.2 format.
     *
     * @param wellFormed the well formed string to convert
     * @return the CPE URI encoded representation of the well formed string
     * @throws CpeEncodingException thrown if the string provided is not well
     * formed
     */
    public static String wellFormedToCpeUri(String wellFormed) throws CpeEncodingException {
        if (wellFormed == null || wellFormed.isEmpty() || LogicalValue.ANY.getAbbreviation().equals(wellFormed)) {
            return "";
        }
        if (LogicalValue.NA.getAbbreviation().equals(wellFormed)) {
            return wellFormed;
        }
        try {
            byte[] bytes = wellFormed.getBytes("UTF-8");
            StringBuilder sb = new StringBuilder(wellFormed.length() + 10);
            for (int x = 0; x < bytes.length; x++) {
                byte c = bytes[x];
                if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                    sb.append((char) c);
                } else if (c == '\\') {
                    x += 1;
                    if (x >= bytes.length) {
                        throw new CpeEncodingException("Invalid Well Formed string - ends with an unquoted backslash");
                    }
                    c = bytes[x];
                    if (c == '_' || c == '.' || c == '-') {
                        sb.append((char) c);
                    } else {
                        //consider .append(Integer.toHexString(c))
                        sb.append('%')
                                .append(HEX_CHARS[(c & 0xF0) >>> 4])
                                .append(HEX_CHARS[c & 0x0F]);
                    }
                } else if (c == '*') {
                    sb.append("%02");
                } else if (c == '?') {
                    sb.append("%01");
                } else {
                    throw new CpeEncodingException("Invalid Well Formed string - unexpected characters: " + wellFormed);
                }
            }
            return sb.toString();
        } catch (UnsupportedEncodingException ex) {
            throw new CpeEncodingException("UTF-8 encoding is not supported on this JVM?", ex);
        }
    }

    /**
     * CPE URI decodes the value into a well formed string. If the value is NULL
     * or an empty string then a the LogicalValue.ANY ('*') is returned.
     *
     * @param value the CPE URI encoded string to convert
     * @return the well formed string representation of the given value
     * @throws CpeEncodingException thrown if the string provided is not well
     * formed
     */
    public static String cpeUriToWellFormed(String value) throws CpeEncodingException {
        return cpeUriToWellFormed(value, false);
    }

    /**
     * CPE URI decodes the value into a well formed string. If the value is NULL
     * or an empty string then a the LogicalValue.ANY ('*') is returned.
     *
     * @param value the CPE URI encoded string to convert
     * @param lenient whether or not to enable lenient parsing of the CPE URI
     * value
     * @return the well formed string representation of the given value
     * @throws CpeEncodingException thrown if the string provided is not well
     * formed
     */
    public static String cpeUriToWellFormed(String value, boolean lenient) throws CpeEncodingException {
        if (value == null || value.isEmpty() || LogicalValue.ANY.getAbbreviation().equals(value)) {
            return LogicalValue.ANY.getAbbreviation();
        } else if (LogicalValue.NA.getAbbreviation().equals(value)) {
            return LogicalValue.NA.getAbbreviation();
        }
        try {
            byte[] bytes = value.toLowerCase().getBytes("UTF-8");
            StringBuilder sb = new StringBuilder(value.length());
            for (int x = 0; x < bytes.length; x++) {
                char c = (char) bytes[x];
                if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z')) {
                    sb.append(c);
                } else if (c == '_' || c == '.' || c == '-') {
                    sb.append('\\').append(c);
                } else if (c == '%') {
                    if ((2 + x) >= bytes.length) {
                        throw new CpeEncodingException("Invalid CPE URI component - ends with a single percent");
                    }
                    char decoded = (char) (Character.digit(bytes[++x], 16) * 16
                            + Character.digit(bytes[++x], 16));
                    switch (decoded) {
                        case 1:
                            sb.append('?');
                            break;
                        case 2:
                            sb.append('*');
                            break;
                        default:
                            sb.append('\\').append(decoded);
                            break;
                    }
                } else {
                    if (lenient) {
                        //TODO check to ensure that the value is in the ascii range per spec?
                        LOG.debug("Invalid CPE URI part, '%s'; escaping '%s' as a well formatted string", value, c);
                        sb.append('\\').append(c);
                    } else {
                        throw new CpeEncodingException("Invalid CPE URI component - unexpected characters");
                    }
                }
            }
            return sb.toString();
        } catch (UnsupportedEncodingException ex) {
            throw new CpeEncodingException("UTF-8 encoding is not supported on this JVM?", ex);
        }
    }

    /**
     * Encodes the given value into the CPE 2.3 Formatted String representation.
     *
     * @param value the component value to encode
     * @return the formatted string
     */
    public static String wellFormedToFS(Part value) {
        if (value == null) {
            return LogicalValue.ANY.getAbbreviation();
        }
        return value.getAbbreviation();
    }

    /**
     * Transforms a Well Formed string into a formatted string (see CPE 2.3
     * specification).
     *
     * @param value the component value to encode
     * @return the formatted string
     */
    public static String wellFormedToFS(String value) {
        if (value == null || value.isEmpty()) {
            return LogicalValue.ANY.getAbbreviation();
        }
        if (LogicalValue.ANY.getAbbreviation().equals(value) || LogicalValue.NA.getAbbreviation().equals(value)) {
            return value;
        }
        //unquote '.', '_', and '-'
        //return value.replaceAll("\\\\([._-])", "$1");
        StringBuilder buffer = new StringBuilder(value);
        char p = ' ';
        for (int x = 0; x < buffer.length() - 1; x++) {
            char c = buffer.charAt(x);
            if ((c == '.' || c == '_' || c == '-') && p == '\\') {
                buffer.delete(x - 1, x);
                x -= 1;
            }
            p = c;
        }
        return buffer.toString();
    }

    /**
     * Transforms a formatted string (CPE 2.3 specification) into a Well Formed
     * string.
     *
     * @param value the component value to transform
     * @return the formatted string
     */
    public static String fsToWellFormed(String value) {
        return fsToWellFormed(value, false);
    }

    /**
     * Transforms a formatted string (CPE 2.3 specification) into a Well Formed
     * string.
     *
     * @param value the component value to transform
     * @param lenient whether or not to enable lenient parsing of the CPE FS
     * value
     * @return the formatted string
     */
    public static String fsToWellFormed(String value, boolean lenient) {
        if (value == null || value.isEmpty()) {
            return LogicalValue.ANY.getAbbreviation();
        }
        if (LogicalValue.ANY.getAbbreviation().equals(value) || LogicalValue.NA.getAbbreviation().equals(value)) {
            return value;
        }
        //return value.replaceAll("([._-])", "\\\\$1");

        int startLenient = -1;
        int endLenient = value.length() - 1;
        if (lenient) {
            char prev = ' ';
            for (int x = 0; x < value.length(); x++) {
                char c = value.charAt(x);
                if (startLenient < 0 && c != '?' && c != '*') {
                    startLenient = x;
                }
                switch (c) {
                    case '*':
                    case '?':
                        if (prev != '*' && prev != '?') {
                            endLenient = x - 1;
                        }
                        break;
                    case '\\':
                        //skip the next character as it is quoted
                        x += 1;
                        //don't break because we need to set endLenient
                    default:
                        endLenient = value.length() - 1;
                        break;
                }
                prev = c;
            }
        }

        boolean quoted = false;
        StringBuilder buffer = new StringBuilder(value);
        for (int x = 0; x < buffer.length(); x++) {
            char c = buffer.charAt(x);
            if (c == '.' || c == '_' || c == '-') {
                buffer.insert(x++, '\\');
                endLenient += 1;
            } else if (lenient && x >= startLenient && x <= endLenient) {
                if (!quoted && c == '\\') {
                    quoted = true;
                    continue;
                }
                if (!quoted && !((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9'))) {
                    buffer.insert(x++, '\\');
                    endLenient += 1;
                }
                quoted = false;
            }
        }

        return buffer.toString();
    }

    /**
     * Converts a well formed string into a regular expression pattern.
     *
     * @param value the well formed string to convert
     * @return the generated pattern object
     */
    public static Pattern wellFormedToPattern(String value) {
        StringBuilder sb = new StringBuilder(value.length() + 4);
        for (int x = 0; x < value.length(); x++) {
            if (value.charAt(x) == '*') {
                sb.append(".*");
            } else if (value.charAt(x) == '?') {
                sb.append(".");
            } else if (value.charAt(x) == '\\' && (x + 1) < value.length()) {
                sb.append('\\').append(value.charAt(x++)).append('\\').append(value.charAt(x));
            } else if ((value.charAt(x) >= 'a' && value.charAt(x) <= 'z')
                    || (value.charAt(x) >= 'A' && value.charAt(x) <= 'Z')
                    || (value.charAt(x) >= '0' && value.charAt(x) <= '9')) {
                sb.append(value.charAt(x));
            } else {
                sb.append('\\').append(value.charAt(x));
            }
        }
        return Pattern.compile(sb.toString());
    }
}
