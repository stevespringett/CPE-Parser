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
import us.springett.parsers.cpe.util.FormatUtil;
import java.io.Serializable;
import us.springett.parsers.cpe.exceptions.CpeEncodingException;
import us.springett.parsers.cpe.exceptions.CpeValidationException;
import us.springett.parsers.cpe.util.Validate;

/**
 * Object representation of a Common Platform Enumeration (CPE).
 *
 * @author Jeremy Long
 */
public class Cpe implements Serializable {

    private static final long serialVersionUID = 5446537216395895498L;

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
     * {@link us.springett.parsers.cpe.values.BindValue#ANY}. All values passed
     * in must be well formed (i.e. special characters quoted with a backslash).
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
    Cpe(Part part, String vendor, String product, String version,
            String update, String edition, String language, String swEdition,
            String targetSw, String targetHw, String other) throws CpeValidationException {

        Validate.component(vendor);
        Validate.component(product);
        Validate.component(version);
        Validate.component(update);
        Validate.component(edition);
        Validate.component(language);
        Validate.component(swEdition);
        Validate.component(targetSw);
        Validate.component(targetHw);
        Validate.component(other);
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
     * <p>
     * Gets the part for the CPE entry.</p>
     * <pre>cpe:2.3:<b>[part]</b>:[vendor]:[product]:[version]:[update]:[edition]:[language]:[sw_edition]:[target_sw]:[target_hw]:[other]</pre>
     *
     * @return the part for the CPE entry
     */
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
    public String getVendor() {
        return FormatUtil.fromWellFormed(vendor);
    }

    /**
     * <p>
     * Gets the product for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:<b>[product]</b>:[version]:[update]:[edition]:[language]:[sw_edition]:[target_sw]:[target_hw]:[other]</pre>
     *
     * @return the product for the CPE entry
     */
    public String getProduct() {
        return FormatUtil.fromWellFormed(product);
    }

    /**
     * <p>
     * Gets the version for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:<b>[version]</b>:[update]:[edition]:[language]:[sw_edition]:[target_sw]:[target_hw]:[other]</pre>
     *
     * @return the version for the CPE entry
     */
    public String getVersion() {
        return FormatUtil.fromWellFormed(version);
    }

    /**
     * <p>
     * Gets the update for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:<b>[update]</b>:[edition]:[language]:[sw_edition]:[target_sw]:[target_hw]:[other]</pre>
     *
     * @return the update for the CPE entry
     */
    public String getUpdate() {
        return FormatUtil.fromWellFormed(update);
    }

    /**
     * <p>
     * Gets the edition for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:[update]:<b>[edition]</b>:[language]:[sw_edition]:[target_sw]:[target_hw]:[other]</pre>
     *
     * @return the edition for the CPE entry
     */
    public String getEdition() {
        return FormatUtil.fromWellFormed(edition);
    }

    /**
     * <p>
     * Gets the language for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:[update]:[edition]:<b>[language]</b>:[sw_edition]:[target_sw]:[target_hw]:[other]</pre>
     *
     * @return the language for the CPE entry
     */
    public String getLanguage() {
        return FormatUtil.fromWellFormed(language);
    }

    /**
     * <p>
     * Gets the software edition for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:[update]:[edition]:[language]:<b>[sw_edition]</b>:[target_sw]:[target_hw]:[other]</pre>
     *
     * @return the software edition for the CPE entry
     */
    public String getSwEdition() {
        return FormatUtil.fromWellFormed(swEdition);
    }

    /**
     * <p>
     * Gets the target software environment for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:[update]:[edition]:[language]:[sw_edition]:<b>[target_sw]</b>:[target_hw]:[other]</pre>
     *
     * @return the target software environment for the CPE entry.
     */
    public String getTargetSw() {
        return FormatUtil.fromWellFormed(targetSw);
    }

    /**
     * <p>
     * Gets the target hardware environment for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:[update]:[edition]:[language]:[sw_edition]:[target_sw]:<b>[target_hw]</b>:[other]</pre>
     *
     * @return the target hardware environment for the CPE entry
     */
    public String getTargetHw() {
        return FormatUtil.fromWellFormed(targetHw);
    }

    /**
     * <p>
     * Gets the other component for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:[update]:[edition]:[language]:[sw_edition]:[target_sw]:[target_hw]:<b>[other]</b></pre>
     *
     * @return the other component for the CPE entry
     */
    public String getOther() {
        return FormatUtil.fromWellFormed(other);
    }

    /**
     * Converts the CPE into the CPE 2.2 URI format.
     *
     * @return the CPE 2.2 URI format of the CPE
     * @throws CpeEncodingException thrown if the CPE is not well formed
     */
    public String toCpe22Uri() throws CpeEncodingException {
        StringBuilder sb = new StringBuilder("cpe:/");
        sb.append(FormatUtil.encodeCpe22Component(part)).append(":");
        sb.append(FormatUtil.transformWfsToCpeUriComponent(vendor)).append(":");
        sb.append(FormatUtil.transformWfsToCpeUriComponent(product)).append(":");
        sb.append(FormatUtil.transformWfsToCpeUriComponent(version)).append(":");
        sb.append(FormatUtil.transformWfsToCpeUriComponent(update)).append(":");
        //pack the extra fields from CPE 2.3 into the edition field if present
        //when outputing to 2.2 format
        if (!((swEdition.isEmpty() || "*".equals(swEdition))
                && (targetSw.isEmpty() || "*".equals(targetSw))
                && (targetHw.isEmpty() || "*".equals(targetHw))
                && (other.isEmpty() || "*".equals(other)))) {
            sb.append("~")
                    .append(FormatUtil.transformWfsToCpeUriComponent(edition))
                    .append("~")
                    .append(FormatUtil.transformWfsToCpeUriComponent(swEdition))
                    .append("~")
                    .append(FormatUtil.transformWfsToCpeUriComponent(targetSw))
                    .append("~")
                    .append(FormatUtil.transformWfsToCpeUriComponent(targetHw))
                    .append("~")
                    .append(FormatUtil.transformWfsToCpeUriComponent(other))
                    .append(":");
        } else {
            sb.append(FormatUtil.transformWfsToCpeUriComponent(edition)).append(":");
        }
        sb.append(FormatUtil.transformWfsToCpeUriComponent(language));
        return sb.toString().replaceAll("[:]*$", "");
    }

    /**
     * Converts the CPE into the CPE 2.3 Formatted String.
     *
     * @return the CPE 2.3 Formatted String
     */
    public String toCpe23FS() {
        return String.format("cpe:2.3:%s:%s:%s:%s:%s:%s:%s:%s:%s:%s:%s",
                FormatUtil.encodeCpe23Component(part),
                FormatUtil.transfromWfsToFS(vendor),
                FormatUtil.transfromWfsToFS(product),
                FormatUtil.transfromWfsToFS(version),
                FormatUtil.transfromWfsToFS(update),
                FormatUtil.transfromWfsToFS(edition),
                FormatUtil.transfromWfsToFS(language),
                FormatUtil.transfromWfsToFS(swEdition),
                FormatUtil.transfromWfsToFS(targetSw),
                FormatUtil.transfromWfsToFS(targetHw),
                FormatUtil.transfromWfsToFS(other));
    }

    @Override
    public String toString() {
        return toCpe23FS();
    }

}
