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

import java.util.NoSuchElementException;
import us.springett.parsers.cpe.util.FormatUtil;
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
     * Parses a CPE String into an object.
     *
     * @param cpeString the CPE string to parse
     * @return the CPE object represented by the given cpeString
     * @throws CpeParsingException thrown if the cpeString is invalid
     */
    public static Cpe parse(String cpeString) throws CpeParsingException {
        if (cpeString == null) {
            throw new CpeParsingException("CPE String is null and cannot be parsed");
        } else if (cpeString.regionMatches(0, "cpe:/", 0, 5)) {
            return parse22(cpeString);
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
        cb.part(parts[1].substring(1));
        if (parts.length > 2) {
            cb.vendor(FormatUtil.decodeCpe22Component(parts[2]));
        }
        if (parts.length > 3) {
            cb.product(FormatUtil.decodeCpe22Component(parts[3]));
        }
        if (parts.length > 4) {
            cb.version(FormatUtil.decodeCpe22Component(parts[4]));
        }
        if (parts.length > 5) {
            cb.update(FormatUtil.decodeCpe22Component(parts[5]));
        }
        if (parts.length > 6) {
            unpackEdition(parts[6], cb);
        }
        if (parts.length > 7) {
            cb.language(FormatUtil.decodeCpe22Component(parts[7]));
        }
        return cb.build();
    }

    /**
     * In a CPE 2.2 URI the new fields from CPE 2.3 may be "packed" into the
     * edition field. If present, each field will be preceeded by a '~'.
     * Example, "~edition~swEdition~targetSw~targetHw~other".
     *
     * @param edition the edition string to unpack
     * @param cb a reference to the CPE Builder to unpack the edition into
     */
    protected static void unpackEdition(String edition, CpeBuilder cb) {
        if (edition == null || edition.isEmpty()) {
            return;
        }
        if (edition.startsWith("~")) {
            String[] unpacked = edition.split("~");
            if (unpacked.length > 1) {
                cb.edition(FormatUtil.decodeCpe22Component(unpacked[1]));
            }
            if (unpacked.length > 2) {
                cb.swEdition(FormatUtil.decodeCpe22Component(unpacked[2]));
            }
            if (unpacked.length > 3) {
                cb.targetSw(FormatUtil.decodeCpe22Component(unpacked[3]));
            }
            if (unpacked.length > 4) {
                cb.targetHw(FormatUtil.decodeCpe22Component(unpacked[4]));
            }
            if (unpacked.length > 5) {
                cb.other(FormatUtil.decodeCpe22Component(unpacked[5]));
            }
        } else {
            cb.edition(FormatUtil.decodeCpe22Component(edition));
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
            cb.vendor(FormatUtil.decodeCpe23Component(cpe.next()));
            cb.product(FormatUtil.decodeCpe23Component(cpe.next()));
            cb.version(FormatUtil.decodeCpe23Component(cpe.next()));
            cb.update(FormatUtil.decodeCpe23Component(cpe.next()));
            cb.edition(FormatUtil.decodeCpe23Component(cpe.next()));
            cb.language(FormatUtil.decodeCpe23Component(cpe.next()));
            cb.swEdition(FormatUtil.decodeCpe23Component(cpe.next()));
            cb.targetSw(FormatUtil.decodeCpe23Component(cpe.next()));
            cb.targetHw(FormatUtil.decodeCpe23Component(cpe.next()));
            cb.other(FormatUtil.decodeCpe23Component(cpe.next()));
        } catch (NoSuchElementException ex) {
            throw new CpeParsingException("Invalid CPE (too few components): " + cpeString);
        }
        if (cpe.hasNext()) {
            throw new CpeParsingException("Invalid CPE (too many components): " + cpeString);
        }
        return cb.build();
    }
}
