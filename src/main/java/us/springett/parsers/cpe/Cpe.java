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

import us.springett.parsers.cpe.util.Relation;
import us.springett.parsers.cpe.values.Part;
import us.springett.parsers.cpe.util.Convert;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import us.springett.parsers.cpe.exceptions.CpeEncodingException;
import us.springett.parsers.cpe.exceptions.CpeValidationException;
import us.springett.parsers.cpe.util.Status;
import us.springett.parsers.cpe.util.Validate;
import us.springett.parsers.cpe.values.LogicalValue;

import static java.lang.Character.isDigit;

/**
 * Object representation of a Common Platform Enumeration (CPE).
 *
 * @author Jeremy Long
 */
public class Cpe implements ICpe, Serializable {
    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = 545319492322631053L;
    /**
     * The part type of the CPE.
     */
    private final Part part;
    /**
     * The vendor component of the CPE in the Well Formed format.
     */
    private final String vendor;
    /**
     * The product component of the CPE in the Well Formed format.
     */
    private final String product;
    /**
     * The version component of the CPE in the Well Formed format.
     */
    private final String version;
    /**
     * The update component of the CPE in the Well Formed format.
     */
    private final String update;
    /**
     * The edition component of the CPE in the Well Formed format.
     */
    private final String edition;
    /**
     * The language component of the CPE in the Well Formed format.
     */
    private final String language;
    /**
     * The swEdition component of the CPE in the Well Formed format. Introduced
     * with CPE 2.3.
     */
    private final String swEdition;
    /**
     * The targetSw component of the CPE in the Well Formed format. Introduced
     * with CPE 2.3.
     */
    private final String targetSw;
    /**
     * The targetHw component of the CPE in the Well Formed format. Introduced
     * with CPE 2.3.
     */
    private final String targetHw;
    /**
     * The other component of the CPE in the Well Formed format. Introduced with
     * CPE 2.3.
     */
    private final String other;

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
            String targetSw, String targetHw, String other) throws CpeValidationException {

        validate(vendor, product, version, update, edition, language, swEdition, targetSw, targetHw, other);
        this.part = part;
        this.vendor = vendor;
        this.product = product;
        this.version = version;
        this.update = update;
        this.edition = edition;
        this.language = language;
        this.swEdition = swEdition;
        this.targetSw = targetSw;
        this.targetHw = targetHw;
        this.other = other;
    }

    /**
     * Validates the CPE attributes.
     *
     * @param vendor1 the vendor
     * @param product1 the product
     * @param version1 the version
     * @param update1 the update version
     * @param edition1 the edition
     * @param language1 the language
     * @param swEdition1 the software edition
     * @param targetSw1 the target software
     * @param targetHw1 the target hardware
     * @param other1 the other attribute
     * @throws CpeValidationException thrown if one or more of the attributes
     * are invalid
     */
    private void validate(String vendor1, String product1, String version1,
            String update1, String edition1, String language1, String swEdition1,
            String targetSw1, String targetHw1, String other1) throws CpeValidationException {
        Status status = Validate.component(vendor1);
        if (!status.isValid()) {
            throw new CpeValidationException("Invalid vendor component: " + status.getMessage());
        }
        status = Validate.component(product1);
        if (!status.isValid()) {
            throw new CpeValidationException("Invalid product component: " + status.getMessage());
        }
        status = Validate.component(version1);
        if (!status.isValid()) {
            throw new CpeValidationException("Invalid version component: " + status.getMessage());
        }
        status = Validate.component(update1);
        if (!status.isValid()) {
            throw new CpeValidationException("Invalid update component: " + status.getMessage());
        }
        status = Validate.component(edition1);
        if (!status.isValid()) {
            throw new CpeValidationException("Invalid edition component: " + status.getMessage());
        }
        status = Validate.component(language1);
        if (!status.isValid()) {
            throw new CpeValidationException("Invalid language component: " + status.getMessage());
        }
        status = Validate.component(swEdition1);
        if (!status.isValid()) {
            throw new CpeValidationException("Invalid swEdition component: " + status.getMessage());
        }
        status = Validate.component(targetSw1);
        if (!status.isValid()) {
            throw new CpeValidationException("Invalid targetSw component: " + status.getMessage());
        }
        status = Validate.component(targetHw1);
        if (!status.isValid()) {
            throw new CpeValidationException("Invalid targetHw component: " + status.getMessage());
        }
        status = Validate.component(other1);
        if (!status.isValid()) {
            throw new CpeValidationException("Invalid other component: " + status.getMessage());
        }
    }

    /**
     * <p>
     * Gets the part for the CPE entry.</p>
     * <pre>cpe:2.3:<b>[part]</b>:[vendor]:[product]:[version]:[update]:[edition]:[language]:[sw_edition]:[target_sw]:[target_hw]:[other]</pre>
     *
     * @return the part for the CPE entry
     */
    @Override
    public Part getPart() {
        return part;
    }

    /**
     * <p>
     * Gets the vendor for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:<b>[vendor]</b>:[product]:[version]:[update]:[edition]:[language]:[sw_edition]:[target_sw]:[target_hw]:[other]</pre>
     *
     * @return the vendor for the CPE entry
     */
    @Override
    public String getVendor() {
        return Convert.fromWellFormed(vendor);
    }

    /**
     * <p>
     * Gets the product for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:<b>[product]</b>:[version]:[update]:[edition]:[language]:[sw_edition]:[target_sw]:[target_hw]:[other]</pre>
     *
     * @return the product for the CPE entry
     */
    @Override
    public String getProduct() {
        return Convert.fromWellFormed(product);
    }

    /**
     * <p>
     * Gets the version for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:<b>[version]</b>:[update]:[edition]:[language]:[sw_edition]:[target_sw]:[target_hw]:[other]</pre>
     *
     * @return the version for the CPE entry
     */
    @Override
    public String getVersion() {
        return Convert.fromWellFormed(version);
    }

    /**
     * <p>
     * Gets the update for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:<b>[update]</b>:[edition]:[language]:[sw_edition]:[target_sw]:[target_hw]:[other]</pre>
     *
     * @return the update for the CPE entry
     */
    @Override
    public String getUpdate() {
        return Convert.fromWellFormed(update);
    }

    /**
     * <p>
     * Gets the edition for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:[update]:<b>[edition]</b>:[language]:[sw_edition]:[target_sw]:[target_hw]:[other]</pre>
     *
     * @return the edition for the CPE entry
     */
    @Override
    public String getEdition() {
        return Convert.fromWellFormed(edition);
    }

    /**
     * <p>
     * Gets the language for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:[update]:[edition]:<b>[language]</b>:[sw_edition]:[target_sw]:[target_hw]:[other]</pre>
     *
     * @return the language for the CPE entry
     */
    @Override
    public String getLanguage() {
        return Convert.fromWellFormed(language);
    }

    /**
     * <p>
     * Gets the software edition for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:[update]:[edition]:[language]:<b>[sw_edition]</b>:[target_sw]:[target_hw]:[other]</pre>
     *
     * @return the software edition for the CPE entry
     */
    @Override
    public String getSwEdition() {
        return Convert.fromWellFormed(swEdition);
    }

    /**
     * <p>
     * Gets the target software environment for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:[update]:[edition]:[language]:[sw_edition]:<b>[target_sw]</b>:[target_hw]:[other]</pre>
     *
     * @return the target software environment for the CPE entry.
     */
    @Override
    public String getTargetSw() {
        return Convert.fromWellFormed(targetSw);
    }

    /**
     * <p>
     * Gets the target hardware environment for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:[update]:[edition]:[language]:[sw_edition]:[target_sw]:<b>[target_hw]</b>:[other]</pre>
     *
     * @return the target hardware environment for the CPE entry
     */
    @Override
    public String getTargetHw() {
        return Convert.fromWellFormed(targetHw);
    }

    /**
     * <p>
     * Gets the other component for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:[update]:[edition]:[language]:[sw_edition]:[target_sw]:[target_hw]:<b>[other]</b></pre>
     *
     * @return the other component for the CPE entry
     */
    @Override
    public String getOther() {
        return Convert.fromWellFormed(other);
    }

    /**
     * <p>
     * Gets the well formed formatted vendor for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:<b>[vendor]</b>:[product]:[version]:[update]:[edition]:[language]:[sw_edition]:[target_sw]:[target_hw]:[other]</pre>
     *
     * @return the vendor for the CPE entry
     */
    @Override
    public String getWellFormedVendor() {
        return vendor;
    }

    /**
     * <p>
     * Gets the well formed formatted product for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:<b>[product]</b>:[version]:[update]:[edition]:[language]:[sw_edition]:[target_sw]:[target_hw]:[other]</pre>
     *
     * @return the product for the CPE entry
     */
    @Override
    public String getWellFormedProduct() {
        return product;
    }

    /**
     * <p>
     * Gets the well formed formatted version for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:<b>[version]</b>:[update]:[edition]:[language]:[sw_edition]:[target_sw]:[target_hw]:[other]</pre>
     *
     * @return the version for the CPE entry
     */
    @Override
    public String getWellFormedVersion() {
        return version;
    }

    /**
     * <p>
     * Gets the well formed formatted update for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:<b>[update]</b>:[edition]:[language]:[sw_edition]:[target_sw]:[target_hw]:[other]</pre>
     *
     * @return the update for the CPE entry
     */
    @Override
    public String getWellFormedUpdate() {
        return update;
    }

    /**
     * <p>
     * Gets the well formed formatted edition for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:[update]:<b>[edition]</b>:[language]:[sw_edition]:[target_sw]:[target_hw]:[other]</pre>
     *
     * @return the edition for the CPE entry
     */
    @Override
    public String getWellFormedEdition() {
        return edition;
    }

    /**
     * <p>
     * Gets the well formed formatted language for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:[update]:[edition]:<b>[language]</b>:[sw_edition]:[target_sw]:[target_hw]:[other]</pre>
     *
     * @return the language for the CPE entry
     */
    @Override
    public String getWellFormedLanguage() {
        return language;
    }

    /**
     * <p>
     * Gets the well formed formatted software edition for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:[update]:[edition]:[language]:<b>[sw_edition]</b>:[target_sw]:[target_hw]:[other]</pre>
     *
     * @return the software edition for the CPE entry
     */
    @Override
    public String getWellFormedSwEdition() {
        return swEdition;
    }

    /**
     * <p>
     * Gets the well formed formatted target software environment for the CPE
     * entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:[update]:[edition]:[language]:[sw_edition]:<b>[target_sw]</b>:[target_hw]:[other]</pre>
     *
     * @return the target software environment for the CPE entry.
     */
    @Override
    public String getWellFormedTargetSw() {
        return targetSw;
    }

    /**
     * <p>
     * Gets the well formed formatted target hardware environment for the CPE
     * entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:[update]:[edition]:[language]:[sw_edition]:[target_sw]:<b>[target_hw]</b>:[other]</pre>
     *
     * @return the target hardware environment for the CPE entry
     */
    @Override
    public String getWellFormedTargetHw() {
        return targetHw;
    }

    /**
     * <p>
     * Gets the well formed formatted other component for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:[update]:[edition]:[language]:[sw_edition]:[target_sw]:[target_hw]:<b>[other]</b></pre>
     *
     * @return the other component for the CPE entry
     */
    @Override
    public String getWellFormedOther() {
        return other;
    }

    /**
     * Converts the CPE into the CPE 2.2 URI format.
     *
     * @return the CPE 2.2 URI format of the CPE
     * @throws CpeEncodingException thrown if the CPE is not well formed
     */
    @Override
    public String toCpe22Uri() throws CpeEncodingException {
        StringBuilder sb = new StringBuilder("cpe:/");
        sb.append(Convert.wellFormedToCpeUri(part)).append(":");
        sb.append(Convert.wellFormedToCpeUri(vendor)).append(":");
        sb.append(Convert.wellFormedToCpeUri(product)).append(":");
        sb.append(Convert.wellFormedToCpeUri(version)).append(":");
        sb.append(Convert.wellFormedToCpeUri(update)).append(":");
        //pack the extra fields from CPE 2.3 into the edition field if present
        //when outputting to 2.2 format
        if (!((swEdition.isEmpty() || "*".equals(swEdition))
                && (targetSw.isEmpty() || "*".equals(targetSw))
                && (targetHw.isEmpty() || "*".equals(targetHw))
                && (other.isEmpty() || "*".equals(other)))) {
            sb.append("~")
                    .append(Convert.wellFormedToCpeUri(edition))
                    .append("~")
                    .append(Convert.wellFormedToCpeUri(swEdition))
                    .append("~")
                    .append(Convert.wellFormedToCpeUri(targetSw))
                    .append("~")
                    .append(Convert.wellFormedToCpeUri(targetHw))
                    .append("~")
                    .append(Convert.wellFormedToCpeUri(other))
                    .append(":");
        } else {
            sb.append(Convert.wellFormedToCpeUri(edition)).append(":");
        }
        sb.append(Convert.wellFormedToCpeUri(language));
        return sb.toString().replaceAll("[:]*$", "");
    }

    /**
     * Converts the CPE into the CPE 2.3 Formatted String.
     *
     * @return the CPE 2.3 Formatted String
     */
    @Override
    public String toCpe23FS() {
        return String.format("cpe:2.3:%s:%s:%s:%s:%s:%s:%s:%s:%s:%s:%s",
                Convert.wellFormedToFS(part),
                Convert.wellFormedToFS(vendor),
                Convert.wellFormedToFS(product),
                Convert.wellFormedToFS(version),
                Convert.wellFormedToFS(update),
                Convert.wellFormedToFS(edition),
                Convert.wellFormedToFS(language),
                Convert.wellFormedToFS(swEdition),
                Convert.wellFormedToFS(targetSw),
                Convert.wellFormedToFS(targetHw),
                Convert.wellFormedToFS(other));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.part);
        hash = 97 * hash + Objects.hashCode(this.vendor);
        hash = 97 * hash + Objects.hashCode(this.product);
        hash = 97 * hash + Objects.hashCode(this.version);
        hash = 97 * hash + Objects.hashCode(this.update);
        hash = 97 * hash + Objects.hashCode(this.edition);
        hash = 97 * hash + Objects.hashCode(this.language);
        hash = 97 * hash + Objects.hashCode(this.swEdition);
        hash = 97 * hash + Objects.hashCode(this.targetSw);
        hash = 97 * hash + Objects.hashCode(this.targetHw);
        hash = 97 * hash + Objects.hashCode(this.other);
        return hash;
    }

    /**
     * Determines if the CPE matches the given target CPE. This does not follow
     * the CPE 2.3 Specification exactly as there are cases where undefined
     * comparisons will result in either true or false. For instance, 'ANY' will
     * match 'm+wild cards' and NA will return false when the target has 'm+wild
     * cards'.
     *
     * @param target the target CPE to evaluate
     * @return <code>true</code> if the CPE matches the target; otherwise
     * <code>false</code>
     */
    @Override
    public boolean matches(ICpe target) {
        boolean result = true;
        result &= compareAttributes(this.part, target.getPart());
        result &= compareAttributes(this.vendor, target.getWellFormedVendor());
        result &= compareAttributes(this.product, target.getWellFormedProduct());
        result &= compareAttributes(this.version, target.getWellFormedVersion());
        result &= compareAttributes(this.update, target.getWellFormedUpdate());
        result &= compareAttributes(this.edition, target.getWellFormedEdition());
        result &= compareAttributes(this.language, target.getWellFormedLanguage());
        result &= compareAttributes(this.swEdition, target.getWellFormedSwEdition());
        result &= compareAttributes(this.targetSw, target.getWellFormedTargetSw());
        result &= compareAttributes(this.targetHw, target.getWellFormedTargetHw());
        result &= compareAttributes(this.other, target.getWellFormedOther());
        return result;
    }

    /**
     * Determines if the target CPE matches the CPE. This does not follow the
     * CPE 2.3 Specification exactly as there are cases where undefined
     * comparisons will result in either true or false. For instance, 'ANY' will
     * match 'm+wild cards' and NA will return false when the target has 'm+wild
     * cards'.
     *
     * @param target the CPE to evaluate
     * @return <code>true</code> if the target CPE matches CPE; otherwise
     * <code>false</code>
     */
    @Override
    public boolean matchedBy(ICpe target) {
        return target.matches(this);
    }

    /**
     * This does not follow the spec precisely because ANY compared to NA is
     * classified as undefined by the spec; however, in this implementation ANY
     * will match NA and return true.
     *
     * This will compare the left value to the right value and return true if
     * the left matches the right. Note that it is possible that the right would
     * not match the left value.
     *
     * @param left the left value to compare
     * @param right the right value to compare
     * @return <code>true</code> if the left value matches the right value;
     * otherwise <code>false</code>
     */
    protected static boolean compareAttributes(Part left, Part right) {
        return compareAttribute(left, right) != Relation.DISJOINT;
    }

    public static Relation compareAttribute(final Part left, final Part right) {
        //1 6 9 - equals
        if (left == right) {
            return Relation.EQUAL;
        }
        //2 3 - superset (4 does not apply due to enum)
        else if (left == Part.ANY) {
            return Relation.SUPERSET;
        }
        //5 13 - subset (15 does not apply due to enum)
        else if (right == Part.ANY) {
            return Relation.SUBSET;
        }
        return Relation.DISJOINT;
    }

    /**
     * This does not follow the spec precisely because ANY compared to NA is
     * classified as undefined by the spec; however, in this implementation ANY
     * will match NA and return true.
     *
     * This will compare the left value to the right value and return true if
     * the left matches the right. Note that it is possible that the right would
     * not match the left value.
     *
     * @param left the left value to compare
     * @param right the right value to compare
     * @return <code>true</code> if the left value matches the right value;
     * otherwise <code>false</code>
     */
    protected static boolean compareAttributes(String left, String right) {
        return compareAttribute(left, right) != Relation.DISJOINT;
    }

    public static Relation compareAttribute(final String left, final String right) {
        //the numbers below come from the CPE Matching standard
        //Table 6-2: Enumeration of Attribute Comparison Set Relations
        //https://nvlpubs.nist.gov/nistpubs/Legacy/IR/nistir7696.pdf

        if (left.equalsIgnoreCase(right)) {
            //1 6 9 - equals
            return Relation.EQUAL;
        } else if (LogicalValue.ANY.getAbbreviation().equals(left)) {
            //2 3 4 - superset (4 is undefined - treating as true)
            return Relation.SUPERSET;
        } else if (LogicalValue.NA.getAbbreviation().equals(left)
                && LogicalValue.ANY.getAbbreviation().equals(right)) {
            //5 - subset
            return Relation.SUBSET;
        } else if (LogicalValue.NA.getAbbreviation().equals(left)) {
            //7 8 - disjoint, undefined
            return Relation.DISJOINT;
        } else if (LogicalValue.NA.getAbbreviation().equals(right)) {
            //12 16 - disjoint
            return Relation.DISJOINT;
        } else if (LogicalValue.ANY.getAbbreviation().equals(right)) {
            //13 15 - subset
            return Relation.SUBSET;
        }
        //10 11 14 17
        if (containsSpecialCharacter(left)) {
            Pattern p = Convert.wellFormedToPattern(left.toLowerCase());
            Matcher m = p.matcher(right.toLowerCase());
            return m.matches() ? Relation.SUPERSET : Relation.DISJOINT;
        }
        return Relation.DISJOINT;
    }

    /**
     * Determines if the string has an unquoted special character.
     *
     * @param value the string to check
     * @return <code>true</code> if the string contains an unquoted special
     * character; otherwise <code>false</code>
     */
    private static boolean containsSpecialCharacter(String value) {
        for (int x = 0; x < value.length(); x++) {
            char c = value.charAt(x);
            if (c == '?' || c == '*') {
                return true;
            } else if (c == '\\') {
                //skip the next character because it is quoted
                x += 1;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null
            || getClass() != obj.getClass()) {
            return false;
        }

        final Cpe cpe = (Cpe) obj;
        return part == cpe.part
               && Objects.equals(vendor, cpe.vendor)
               && Objects.equals(product, cpe.product)
               && Objects.equals(version, cpe.version)
               && Objects.equals(update, cpe.update)
               && Objects.equals(edition, cpe.edition)
               && Objects.equals(language, cpe.language)
               && Objects.equals(swEdition, cpe.swEdition)
               && Objects.equals(targetSw, cpe.targetSw)
               && Objects.equals(targetHw, cpe.targetHw)
               && Objects.equals(other, cpe.other);
    }

    @Override
    public String toString() {
        return toCpe23FS();
    }

    /**
     * CompareTo is used for sorting, this does not implement any CPE Matching
     * rules.
     *
     * @param otherObject the CPE to compare
     * @return the sort order
     */
    @Override
    public int compareTo(ICpe otherObject) {
        if (otherObject != null) {

            final int before = -1;
            final int equal = 0;
            final int after = 1;

            if (this == otherObject) {
                return equal;
            }
            int r = getPart().getAbbreviation().compareTo(otherObject.getPart().getAbbreviation());
            if (r < 0) {
                return before;
            } else if (r > 0) {
                return after;
            }
            r = getVendor().compareTo(otherObject.getVendor());
            if (r < 0) {
                return before;
            } else if (r > 0) {
                return after;
            }
            r = getProduct().compareTo(otherObject.getProduct());
            if (r < 0) {
                return before;
            } else if (r > 0) {
                return after;
            }
            r = compareVersions(getVersion(), otherObject.getVersion());
            if (r < 0) {
                return before;
            } else if (r > 0) {
                return after;
            }
            r = getUpdate().compareTo(otherObject.getUpdate());
            if (r < 0) {
                return before;
            } else if (r > 0) {
                return after;
            }
            r = getEdition().compareTo(otherObject.getEdition());
            if (r < 0) {
                return before;
            } else if (r > 0) {
                return after;
            }
            r = getLanguage().compareTo(otherObject.getLanguage());
            if (r < 0) {
                return before;
            } else if (r > 0) {
                return after;
            }
            r = getSwEdition().compareTo(otherObject.getSwEdition());
            if (r < 0) {
                return before;
            } else if (r > 0) {
                return after;
            }
            r = getTargetSw().compareTo(otherObject.getTargetSw());
            if (r < 0) {
                return before;
            } else if (r > 0) {
                return after;
            }
            r = getTargetHw().compareTo(otherObject.getTargetHw());
            if (r < 0) {
                return before;
            } else if (r > 0) {
                return after;
            }
            r = getOther().compareTo(otherObject.getOther());
            if (r < 0) {
                return before;
            } else if (r > 0) {
                return after;
            }
            return equal;
        }
        return -1;
    }

    /**
     * Compare version numbers to obtain the correct ordering.
     *
     * @param left the left hand version for comparison
     * @param right the right hand version for comparison
     * @return <code>-1</code> if left is before the right; <code>0</code> if
     * the left and right are equal;<code>1</code> if left is after the right
     */
    protected static int compareVersions(String left, String right) {
        int result = 0;
        final List<Comparable<?>> subLeft = splitVersion(left);
        final List<Comparable<?>> subRight = splitVersion(right);
        final int subMax = Math.min(subLeft.size(), subRight.size());
        for (int x = 0; x < subMax; x++) {
            Object leftPart = subLeft.get(x);
            Object rightPart = subRight.get(x);
            if (leftPart instanceof BigInteger && rightPart instanceof BigInteger) {
                result = ((BigInteger) leftPart).compareTo(((BigInteger) rightPart));
            } else {
                result = leftPart.toString().compareTo(rightPart.toString());
            }
            if (result != 0) {
                return result;
            }
        }

        if (subLeft.size() > subRight.size()) {
            result = 1;
        }
        if (subRight.size() > subLeft.size()) {
            result = -1;
        }

        return result;
    }

    /**
     * Method that split versions for '.', '|', ':' and '-". Then if a token
     * start with a number and then contains letters, it will split it too. For
     * example "12a" is split into ["12", "a"]. This is done to support correct
     * comparison of "5.0.3a", "5.0.9" and "5.0.30".
     *
     * @param s the string to split
     * @return a List of comparable objects containing the tokens to be compared. Not that difference comparable
     *         types are not generally comparable to one another; and it is the caller's responsibility to handle that.
     */
    static List<Comparable<?>> splitVersion(String s) {
        if (s == null || s.isEmpty()) {
            return Collections.emptyList();
        }

        List<Comparable<?>> result = new ArrayList<>();

        StringBuilder token = new StringBuilder(3);
        boolean integerMode = false;

        for (int i = 0; i < s.length(); i++) {
            final char c = s.charAt(i);
            final boolean firstChar = token.length() == 0;

            if (c == '.' || c == '|' || c == ':' || c == '-') {
                if (!firstChar) {
                    // Complete the current token
                    result.add(integerMode ? new BigInteger(token.toString()) : token.toString());
                    token.setLength(0);
                    integerMode = false;
                }
                // skip splitter char
            } else if (isDigit(c)) {
                integerMode |= firstChar && c != '0'; // Start integer mode if first char is a non-zero digit
                token.append(c);
            } else {
                // not a splitter, not a digit
                if (integerMode) {
                    // Always start a new token if in integer mode and encountering a non-digit
                    result.add(new BigInteger(token.toString()));
                    token.setLength(0);
                    integerMode = false;
                }
                token.append(c);
            }
        }
        if (token.length() > 0) {
            result.add(integerMode ? new BigInteger(token.toString()) : token.toString());
        }
        return result;
    }
}
