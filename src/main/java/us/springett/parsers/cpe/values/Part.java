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
package us.springett.parsers.cpe.values;

import us.springett.parsers.cpe.exceptions.CpeParsingException;

/**
 * <p>
 * The part type for a CPE entry: application, operating system, hardware, any,
 * or NA.</p>
 * <pre>cpe:2.3:<b>[part]</b>:[vendor]:[product]:[version]:[update]:[edition]:[language]:[sw_edition]:[target_sw]:[target_hw]:[other]</pre>
 *
 * @author Jeremy Long
 */
public enum Part {

    /**
     * Application: 'a'.
     */
    APPLICATION("a"),
    /**
     * Operating System: 'o'.
     */
    OPERATING_SYSTEM("o"),
    /**
     * Hardware device: 'h'.
     */
    HARDWARE_DEVICE("h"),
    /**
     * Match ANY: '*'.
     */
    ANY("*"),
    /**
     * Not Application: '-'.
     */
    NA("-");

    /**
     * The abbreviation for the logical value.
     */
    private final String abbreviation;

    /**
     * Constructs a new part.
     *
     * @param abbreviation the abbreviation for the part type
     */
    Part(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    /**
     * Gets the abbreviation for the part component.
     *
     * @return the abbreviation for the part component
     */
    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * Returns the enumeration value for the given abbreviation.
     *
     * @param part the enumeration value
     * @return the enumeration value
     * @throws CpeParsingException thrown if the abbreviation is not a valid
     * Part type
     */
    public static Part getEnum(String part) throws CpeParsingException {
        for (Part p : Part.values()) {
            if (p.getAbbreviation().equals(part)) {
                return p;
            }
        }
        throw new CpeParsingException("Invalid Part Type: " + part);
    }
}
