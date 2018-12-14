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

import us.springett.parsers.cpe.values.Part;
import us.springett.parsers.cpe.exceptions.CpeValidationException;

/**
 * Object representation of a Common Platform Enumeration (CPE).
 *
 * @author Jeremy Long
 */
public class Cpe extends CpeBase<Cpe> {

    private static final long serialVersionUID = 6336537216995895498L;

    /**
     * Constructs a new immutable CPE object that represents the Well Form Named
     * defined in the CPE 2.3 specification. Specifying <code>null</code> will
     * be set to the default
     * {@link us.springett.parsers.cpe.values.LogicalValue#ANY}. All values
     * passed in must be well formed (i.e. special characters quoted with a
     * backslash).
     *
     * @see <a href="https://cpe.mitre.org/specification/">CPE 2.3</a>
     * @param part the type of entry: application, operating system, or hardware
     * @param vendor the vendor of the CPE entry
     * @param product the product of the CPE entry
     * @param version the version of the CPE entry
     * @param update the update of the CPE entry
     * @param edition the edition of the CPE entry
     * @param language the language of the CPE entry
     * @param swEdition the swEdition of the CPE entry
     * @param targetSw the targetSw of the CPE entry
     * @param targetHw the targetHw of the CPE entry
     * @param other the other of the CPE entry
     * @throws CpeValidationException thrown if one of the CPE entries is
     * invalid
     */
    public Cpe(Part part, String vendor, String product, String version,
            String update, String edition, String language, String swEdition,
            String targetSw, String targetHw, String other)
            throws CpeValidationException {
        super(part, vendor, product, version, update, edition, language,
                swEdition, targetSw, targetHw, other);
    }
}
