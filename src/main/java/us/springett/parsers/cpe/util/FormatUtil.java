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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import us.springett.parsers.cpe.values.BindValue;
import us.springett.parsers.cpe.values.Part;

/**
 * Set of methods to encode and decode text based on the CPE specification.
 *
 * @author Jeremy Long
 */
public final class FormatUtil {

    /**
     * Private constructor for utility class.
     */
    private FormatUtil() {

    }

    /**
     * Transforms the given string into a "well (partially) formed string". A
     * well formed string has all non-alphanumeric characters escaped with a
     * backslash; however, this implementation does not escape '_', '-', or '.'.
     * If a <code>null</code> value is passed in the default
     * {@link BindValue#ANY} will be returned.
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
        return value.replaceAll("([^0-9A-Za-z._-])", "\\\\$1");
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
     * URL encodes the value for CPE 2.2. If the value is NULL or ANY ('*') then
     * an empty string is returned. If the value is a hyphen ('-') the hyphen is
     * returned un-encoded; otherwise URL encoding is applied.
     *
     * @param value the value to encode
     * @return the encoded value
     */
    public static String encodeCpe22Component(String value) {
        if (value == null || value.isEmpty() || BindValue.ANY.getAbbreviation().equals(value)) {
            return "";
        }
        if (BindValue.NA.getAbbreviation().equals(value)) {
            return value;
        }
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("UTF-8 support is missing?", ex);
        }
    }

    /**
     * URL decodes the value for CPE 2.2. If the value is NULL or an empty
     * string then a the BindValue.ANY ('*') is returned.
     *
     * @param value the value to decode
     * @return the decoded value
     */
    public static String decodeCpe22Component(String value) {
        if (value == null || value.isEmpty()) {
            return BindValue.ANY.getAbbreviation();
        }
        try {
            return URLDecoder.decode(value, "UTF-8").trim();
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("UTF-8 support is missing?", ex);
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
     * Encodes the given value into the CPE 2.3 Formatted String representation.
     *
     * @param value the component value to encode
     * @return the formatted string
     */
    public static String encodeCpe23Component(String value) {
        if (value == null || value.isEmpty()) {
            return BindValue.ANY.getAbbreviation();
        }
        if (BindValue.ANY.getAbbreviation().equals(value) || BindValue.NA.getAbbreviation().equals(value)) {
            return value;
        }
        //TODO - this could likely be done more effeciently then two calls to replaceAll()
        return toWellFormed(value).replaceAll("(\\\\[^._-])", "\\\\$1");
    }

    /**
     * Encodes the given value into the CPE 2.3 Formatted String representation.
     *
     * @param value the component value to encode
     * @return the formatted string
     */
    public static String decodeCpe23Component(String value) {
        if (value == null || value.isEmpty()) {
            return BindValue.ANY.getAbbreviation();
        }
        if (BindValue.ANY.getAbbreviation().equals(value) || BindValue.NA.getAbbreviation().equals(value)) {
            return value;
        }
        //TODO - this could likely be done more effeciently then two calls to replaceAll()
        return fromWellFormed(value.replaceAll("\\\\(\\\\[^._-])", "$1"));
    }
}
