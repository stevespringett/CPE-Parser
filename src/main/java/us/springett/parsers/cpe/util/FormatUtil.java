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
import us.springett.parsers.cpe.values.BindValue;
import us.springett.parsers.cpe.values.Part;

/**
 * Set of methods to encode and decode text based on the CPE specification.
 *
 * @author Jeremy Long
 */
public final class FormatUtil {

    /**
     * Hexadecimal character sequence used for encoding.
     */
    private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();

    /**
     * Private constructor for utility class.
     */
    private FormatUtil() {

    }

    /**
     * Transforms the given string into a "well formed string". A well formed
     * string has all non-alphanumeric characters escaped with a backslash. If a
     * <code>null</code> value is passed in the default {@link BindValue#ANY}
     * will be returned.
     *
     * @param value the string to format
     * @return the well formed string
     */
    public static String toWellFormed(String value) {
        if (value == null) {
            return BindValue.ANY.getAbbreviation();
        }
        if (BindValue.ANY.getAbbreviation().equals(value)
                || BindValue.NA.getAbbreviation().equals(value)) {
            return value;
        }
        return value.replaceAll("([^0-9A-Za-z])", "\\\\$1");
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
            return BindValue.ANY.getAbbreviation();
        }
        return value.replaceAll("\\\\([^0-9A-Za-z])", "$1");
    }

    /**
     * URL encodes the value for CPE 2.2. If the value is NULL or ANY ('*') then
     * an empty string is returned. If the value is a hyphen ('-') the hyphen is
     * returned un-encoded; otherwise URL encoding is applied.
     *
     * @param value the value to encode
     * @return the encoded value
     */
    public static String encodeCpe22Component(Part value) {
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
    public static String transformWfsToCpeUriComponent(String wellFormed) throws CpeEncodingException {
        if (wellFormed == null || wellFormed.isEmpty() || BindValue.ANY.getAbbreviation().equals(wellFormed)) {
            return "";
        }
        if (BindValue.NA.getAbbreviation().equals(wellFormed)) {
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
     * or an empty string then a the BindValue.ANY ('*') is returned.
     *
     * @param value the CPE URI encoded string to convert
     * @return the well formed string representation of the given value
     * @throws CpeEncodingException thrown if the string provided is not well
     * formed
     */
    public static String transformCpeUriComponentToWfs(String value) throws CpeEncodingException {
        if (value == null || value.isEmpty() || BindValue.ANY.getAbbreviation().equals(value)) {
            return BindValue.ANY.getAbbreviation();
        } else if (BindValue.NA.getAbbreviation().equals(value)) {
            return BindValue.NA.getAbbreviation();
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
                    throw new CpeEncodingException("Invalid CPE URI component - unexpected characters");
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
    public static String encodeCpe23Component(Part value) {
        if (value == null) {
            return BindValue.ANY.getAbbreviation();
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
    public static String transfromWfsToFS(String value) {
        if (value == null || value.isEmpty()) {
            return BindValue.ANY.getAbbreviation();
        }
        if (BindValue.ANY.getAbbreviation().equals(value) || BindValue.NA.getAbbreviation().equals(value)) {
            return value;
        }
        return value.replaceAll("\\\\([._-])", "$1").replaceAll("(\\\\[^._-])", "\\\\$1");
    }

    /**
     * Transforms a formatted string (CPE 2.3 specification) into a Well Formed
     * string.
     *
     * @param value the component value to transform
     * @return the formatted string
     */
    public static String transfromFsToWfs(String value) {
        if (value == null || value.isEmpty()) {
            return BindValue.ANY.getAbbreviation();
        }
        if (BindValue.ANY.getAbbreviation().equals(value) || BindValue.NA.getAbbreviation().equals(value)) {
            return value;
        }
        return value.replaceAll("([._-])", "\\\\$1").replaceAll("\\\\(\\\\[^._-])", "$1");
    }
}
