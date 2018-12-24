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
 * Copyright (c) Steve Springett. All Rights Reserved.
 */
package us.springett.parsers.cpe;

import us.springett.parsers.cpe.exceptions.CpeValidationException;
import us.springett.parsers.cpe.exceptions.CpeParsingException;
import java.util.NoSuchElementException;
import us.springett.parsers.cpe.exceptions.CpeEncodingException;
import us.springett.parsers.cpe.util.Convert;
import us.springett.parsers.cpe.internal.util.Cpe23PartIterator;

/**
 * A Common Platform Enumeration (CPE) parser. This parser is capable of parsing
 * CPE 2.2 and 2.3 strings.
 *
 * @author Jeremy Long
 */
public final class CpeParser {

    /**
     * Private constructor for a utility class.
     */
    private CpeParser() {
    }

    /**
     * Parses a CPE String into an object; the string can be formated as either
     * a CPE 2.2 URI or CPE 2.3 Formatted String.
     *
     * @param cpeString the CPE string to parse
     * @return the CPE object represented by the given cpeString
     * @throws CpeParsingException thrown if the cpeString is invalid
     */
    public static Cpe parse(String cpeString) throws CpeParsingException {
        if (cpeString == null) {
            throw new CpeParsingException("CPE String is null and cannot be parsed");
        } else if (cpeString.regionMatches(0, "cpe:/", 0, 5)) {
            return parse22(cpeString, false);
        } else if (cpeString.regionMatches(0, "cpe:2.3:", 0, 8)) {
            return parse23(cpeString);
        }
        throw new CpeParsingException("The CPE string specified does not conform to the CPE 2.2 or 2.3 specification");
    }

    /**
     * Parses a CPE String into an object with the option of parsing CPE 2.2 URI
     * strings in lenient mode - allowing for CPE values that do not adhere to
     * the specification.
     *
     * @param cpeString the CPE string to parse
     * @param lenient when <code>true</code> the CPE 2.2 parser will put in
     * lenient mode attempting to parse invalid CPE URI values.
     * @return the CPE object represented by the given cpeString
     * @throws CpeParsingException thrown if the cpeString is invalid
     */
    public static Cpe parse(String cpeString, boolean lenient) throws CpeParsingException {
        if (cpeString == null) {
            throw new CpeParsingException("CPE String is null and cannot be parsed");
        } else if (cpeString.regionMatches(0, "cpe:/", 0, 5)) {
            return parse22(cpeString, lenient);
        } else if (cpeString.regionMatches(0, "cpe:2.3:", 0, 8)) {
            return parse23(cpeString);
        }
        throw new CpeParsingException("The CPE string specified does not conform to the CPE 2.2 or 2.3 specification");
    }

    /**
     * Parses a CPE 2.2 URI.
     *
     * @param cpeString the CPE string to parse
     * @return the CPE object represented by the cpeString
     * @throws CpeParsingException thrown if the cpeString is invalid
     */
    protected static Cpe parse22(String cpeString) throws CpeParsingException {
        return parse22(cpeString, false);
    }

    /**
     * Parses a CPE 2.2 URI.
     *
     * @param cpeString the CPE string to parse
     * @param lenient when <code>true</code> the parser will put in lenient mode
     * attempting to parse invalid CPE values.
     * @return the CPE object represented by the cpeString
     * @throws CpeParsingException thrown if the cpeString is invalid
     */
    protected static Cpe parse22(String cpeString, boolean lenient) throws CpeParsingException {
        if (cpeString == null || cpeString.isEmpty()) {
            throw new CpeParsingException("CPE String is null ir enpty - unable to parse");
        }
        CpeBuilder cb = new CpeBuilder();
        String[] parts = cpeString.split(":");
        if (parts.length <= 1 || parts.length > 8) {
            throw new CpeParsingException("CPE String is invalid - too many components specified: " + cpeString);
        }
        if (parts[1].length() != 2) {
            throw new CpeParsingException("CPE String contains a malformed part: " + cpeString);
        }
        try {
            cb.part(parts[1].substring(1));
            if (parts.length > 2) {
                cb.wfVendor(Convert.cpeUriToWellFormed(parts[2], lenient));
            }
            if (parts.length > 3) {
                cb.wfProduct(Convert.cpeUriToWellFormed(parts[3], lenient));
            }
            if (parts.length > 4) {
                cb.wfVersion(Convert.cpeUriToWellFormed(parts[4], lenient));
            }
            if (parts.length > 5) {
                cb.wfUpdate(Convert.cpeUriToWellFormed(parts[5], lenient));
            }
            if (parts.length > 6) {
                unpackEdition(parts[6], cb, lenient);
            }
            if (parts.length > 7) {
                cb.wfLanguage(Convert.cpeUriToWellFormed(parts[7], lenient));
            }
            return cb.build();
        } catch (CpeValidationException | CpeEncodingException ex) {
            throw new CpeParsingException(ex.getMessage());
        }
    }

    /**
     * In a CPE 2.2 URI the new fields from CPE 2.3 may be "packed" into the
     * edition field. If present, each field will be preceeded by a '~'.
     * Example, "~edition~swEdition~targetSw~targetHw~other".
     *
     * @param edition the edition string to unpack
     * @param cb a reference to the CPE Builder to unpack the edition into
     * @param lenient whether or not to use lenient parsing
     * @throws CpeParsingException thrown if the edition value is invalid
     */
    protected static void unpackEdition(String edition, CpeBuilder cb, boolean lenient) throws CpeParsingException {
        if (edition == null || edition.isEmpty()) {
            return;
        }
        try {
            String[] unpacked = edition.split("~");
            if (edition.startsWith("~")) {
                if (unpacked.length > 1) {
                    cb.wfEdition(Convert.cpeUriToWellFormed(unpacked[1], lenient));
                }
                if (unpacked.length > 2) {
                    cb.wfSwEdition(Convert.cpeUriToWellFormed(unpacked[2], lenient));
                }
                if (unpacked.length > 3) {
                    cb.wfTargetSw(Convert.cpeUriToWellFormed(unpacked[3], lenient));
                }
                if (unpacked.length > 4) {
                    cb.wfTargetHw(Convert.cpeUriToWellFormed(unpacked[4], lenient));
                }
                if (unpacked.length > 5) {
                    cb.wfOther(Convert.cpeUriToWellFormed(unpacked[5], lenient));
                }
                if (unpacked.length > 6) {
                    throw new CpeParsingException("Invalid packed edition");
                }
            } else {
                cb.wfEdition(Convert.cpeUriToWellFormed(edition, lenient));
            }
        } catch (CpeEncodingException ex) {
            throw new CpeParsingException(ex.getMessage());
        }
    }

    /**
     * Parses a CPE 2.3 Formatted String.
     *
     * @param cpeString the CPE string to parse
     * @return the CPE object represented by the cpeString
     * @throws CpeParsingException thrown if the cpeString is invalid
     */
    protected static Cpe parse23(String cpeString) throws CpeParsingException {
        if (cpeString == null || cpeString.isEmpty()) {
            throw new CpeParsingException("CPE String is null ir enpty - unable to parse");
        }
        CpeBuilder cb = new CpeBuilder();
        Cpe23PartIterator cpe = new Cpe23PartIterator(cpeString);
        try {
            cb.part(cpe.next());
            cb.wfVendor(Convert.fsToWellFormed(cpe.next()));
            cb.wfProduct(Convert.fsToWellFormed(cpe.next()));
            cb.wfVersion(Convert.fsToWellFormed(cpe.next()));
            cb.wfUpdate(Convert.fsToWellFormed(cpe.next()));
            cb.wfEdition(Convert.fsToWellFormed(cpe.next()));
            cb.wfLanguage(Convert.fsToWellFormed(cpe.next()));
            cb.wfSwEdition(Convert.fsToWellFormed(cpe.next()));
            cb.wfTargetSw(Convert.fsToWellFormed(cpe.next()));
            cb.wfTargetHw(Convert.fsToWellFormed(cpe.next()));
            cb.wfOther(Convert.fsToWellFormed(cpe.next()));
        } catch (NoSuchElementException ex) {
            throw new CpeParsingException("Invalid CPE (too few components): " + cpeString);
        }
        if (cpe.hasNext()) {
            throw new CpeParsingException("Invalid CPE (too many components): " + cpeString);
        }
        try {
            return cb.build();
        } catch (CpeValidationException ex) {
            throw new CpeParsingException(ex.getMessage());
        }
    }
}
