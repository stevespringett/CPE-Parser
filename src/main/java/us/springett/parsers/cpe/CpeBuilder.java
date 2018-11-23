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
package us.springett.parsers.cpe;

import us.springett.parsers.cpe.exceptions.CpeParsingException;
import us.springett.parsers.cpe.exceptions.CpeValidationException;
import us.springett.parsers.cpe.util.FormatUtil;
import us.springett.parsers.cpe.values.Part;
import us.springett.parsers.cpe.values.BindValue;

/**
 * A builder for CPE objects.
 *
 * @author Jeremy Long
 */
public class CpeBuilder {

    /**
     * The part type of the CPE.
     */
    private Part part;
    /**
     * The vendor component of the CPE.
     */
    private String vendor;
    /**
     * The product component of the CPE.
     */
    private String product;
    /**
     * The version component of the CPE.
     */
    private String version;
    /**
     * The update component of the CPE.
     */
    private String update;
    /**
     * The edition component of the CPE.
     */
    private String edition;
    /**
     * The language component of the CPE.
     */
    private String language;
    /**
     * The swEdition component of the CPE. Introduced with CPE 2.3.
     */
    private String swEdition;
    /**
     * The targetSw component of the CPE. Introduced with CPE 2.3.
     */
    private String targetSw;
    /**
     * The targetHw component of the CPE. Introduced with CPE 2.3.
     */
    private String targetHw;
    /**
     * The other component of the CPE. Introduced with CPE 2.3.
     */
    private String other;

    /**
     * Constructs a new CPE Builder.
     */
    public CpeBuilder() {
        reset();
    }

    /**
     * Resets the CPE Builder to a clean state.
     */
    private void reset() {
        part = Part.ANY;
        vendor = BindValue.ANY.getAbbreviation();
        product = BindValue.ANY.getAbbreviation();
        version = BindValue.ANY.getAbbreviation();
        update = BindValue.ANY.getAbbreviation();
        edition = BindValue.ANY.getAbbreviation();
        language = BindValue.ANY.getAbbreviation();
        swEdition = BindValue.ANY.getAbbreviation();
        targetSw = BindValue.ANY.getAbbreviation();
        targetHw = BindValue.ANY.getAbbreviation();
        other = BindValue.ANY.getAbbreviation();
    }

    /**
     * <p>
     * Sets the type for the CPE entry: application, operating system, or
     * hardware.</p>
     * <pre>cpe:2.3:<b>[part]</b>:[vendor]:[product]:[version]:[update]:[edition]:[language]:[sw_edition]:[target_sw]:[target_hw]:[other]</pre>
     *
     * @param part the type of CPE entry: application, operating system, or
     * hardware
     * @return the builder
     */
    public CpeBuilder part(final Part part) {
        this.part = part;
        return this;
    }

    /**
     * <p>
     * Sets the type for the CPE entry: application, operating system, or
     * hardware.</p>
     * <pre>cpe:2.3:<b>[part]</b>:[vendor]:[product]:[version]:[update]:[edition]:[language]:[sw_edition]:[target_sw]:[target_hw]:[other]</pre>
     *
     * @param part the type of CPE entry: application, operating system, or
     * hardware
     * @return the builder
     * @throws CpeParsingException thrown if an invalid part type is specified
     */
    public CpeBuilder part(final String part) throws CpeParsingException {
        this.part = Part.getEnum(part);
        return this;
    }

    /**
     * <p>
     * Sets the vendor for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:<b>[vendor]</b>:[product]:[version]:[update]:[edition]:[language]:[sw_edition]:[target_sw]:[target_hw]:[other]</pre>
     *
     * @param vendor the vendor name
     * @return the builder
     */
    public CpeBuilder vendor(final String vendor) {
        this.vendor = FormatUtil.toWellFormed(vendor);
        return this;
    }

    /**
     * <p>
     * Sets the product for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:<b>[product]</b>:[version]:[update]:[edition]:[language]:[sw_edition]:[target_sw]:[target_hw]:[other]</pre>
     *
     * @param product the product name
     * @return the builder
     */
    public CpeBuilder product(final String product) {
        this.product = FormatUtil.toWellFormed(product);
        return this;
    }

    /**
     * <p>
     * Sets the version for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:<b>[version]</b>:[update]:[edition]:[language]:[sw_edition]:[target_sw]:[target_hw]:[other]</pre>
     *
     * @param version the version number
     * @return the builder
     */
    public CpeBuilder version(final String version) {
        this.version = FormatUtil.toWellFormed(version);
        return this;
    }

    /**
     * <p>
     * Sets the update version for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:<b>[update]</b>:[edition]:[language]:[sw_edition]:[target_sw]:[target_hw]:[other]</pre>
     *
     * @param update the update version
     * @return the builder
     */
    public CpeBuilder update(final String update) {
        this.update = FormatUtil.toWellFormed(update);
        return this;
    }

    /**
     * <p>
     * Sets the edition for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:[update]:<b>[edition]</b>:[language]:[sw_edition]:[target_sw]:[target_hw]:[other]</pre>
     *
     * @param edition the edition name
     * @return the builder
     */
    public CpeBuilder edition(final String edition) {
        this.edition = FormatUtil.toWellFormed(edition);
        return this;
    }

    /**
     * <p>
     * Sets the language for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:[update]:[edition]:<b>[language]</b>:[sw_edition]:[target_sw]:[target_hw]:[other]</pre>
     *
     * @param language the language name
     * @return the builder
     */
    public CpeBuilder language(final String language) {
        this.language = FormatUtil.toWellFormed(language);
        return this;
    }

    /**
     * <p>
     * Sets the software edition for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:[update]:[edition]:[language]:<b>[sw_edition]</b>:[target_sw]:[target_hw]:[other]</pre>
     *
     * @param swEdition the software edition
     * @return the builder
     */
    public CpeBuilder swEdition(final String swEdition) {
        this.swEdition = FormatUtil.toWellFormed(swEdition);
        return this;
    }

    /**
     * <p>
     * Sets the target software environment for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:[update]:[edition]:[language]:[sw_edition]:<b>[target_sw]</b>:[target_hw]:[other]</pre>
     *
     * @param targetSw the target software environment
     * @return the builder
     */
    public CpeBuilder targetSw(final String targetSw) {
        this.targetSw = FormatUtil.toWellFormed(targetSw);
        return this;
    }

    /**
     * <p>
     * Sets the target hardware environment for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:[update]:[edition]:[language]:[sw_edition]:[target_sw]:<b>[target_hw]</b>:[other]</pre>
     *
     * @param targetHw the target hardware environment
     * @return the builder
     */
    public CpeBuilder targetHw(final String targetHw) {
        this.targetHw = FormatUtil.toWellFormed(targetHw);
        return this;
    }

    /**
     * <p>
     * Sets the other component for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:[update]:[edition]:[language]:[sw_edition]:[target_sw]:[target_hw]:<b>[other]</b></pre>
     *
     * @param other the other component
     * @return the builder
     */
    public CpeBuilder other(final String other) {
        this.other = FormatUtil.toWellFormed(other);
        return this;
    }

    /**
     * <p>
     * Sets the vendor for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:<b>[vendor]</b>:[product]:[version]:[update]:[edition]:[language]:[sw_edition]:[target_sw]:[target_hw]:[other]</pre>
     *
     * @param vendor the bind value of NA or ANY
     * @return the builder
     */
    public CpeBuilder vendor(final BindValue vendor) {
        this.vendor = vendor.getAbbreviation();
        return this;
    }

    /**
     * <p>
     * Sets the product for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:<b>[product]</b>:[version]:[update]:[edition]:[language]:[sw_edition]:[target_sw]:[target_hw]:[other]</pre>
     *
     *
     * @param product the bind value of NA or ANY
     * @return the builder
     */
    public CpeBuilder product(final BindValue product) {
        this.product = product.getAbbreviation();
        return this;
    }

    /**
     * <p>
     * Sets the version for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:<b>[version]</b>:[update]:[edition]:[language]:[sw_edition]:[target_sw]:[target_hw]:[other]</pre>
     *
     * @param version the bind value of NA or ANY
     * @return the builder
     */
    public CpeBuilder version(final BindValue version) {
        this.version = version.getAbbreviation();
        return this;
    }

    /**
     * <p>
     * Sets the update version for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:<b>[update]</b>:[edition]:[language]:[sw_edition]:[target_sw]:[target_hw]:[other]</pre>
     *
     * @param update the bind value of NA or ANY
     * @return the builder
     */
    public CpeBuilder update(final BindValue update) {
        this.update = update.getAbbreviation();
        return this;
    }

    /**
     * <p>
     * Sets the edition for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:[update]:<b>[edition]</b>:[language]:[sw_edition]:[target_sw]:[target_hw]:[other]</pre>
     *
     * @param edition the bind value of NA or ANY
     * @return the builder
     */
    public CpeBuilder edition(final BindValue edition) {
        this.edition = edition.getAbbreviation();
        return this;
    }

    /**
     * <p>
     * Sets the language for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:[update]:[edition]:<b>[language]</b>:[sw_edition]:[target_sw]:[target_hw]:[other]</pre>
     *
     * @param language the bind value of NA or ANY
     * @return the builder
     */
    public CpeBuilder language(final BindValue language) {
        this.language = language.getAbbreviation();
        return this;
    }

    /**
     * <p>
     * Sets the software edition for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:[update]:[edition]:[language]:<b>[sw_edition]</b>:[target_sw]:[target_hw]:[other]</pre>
     *
     * @param swEdition the bind value of NA or ANY
     * @return the builder
     */
    public CpeBuilder swEdition(final BindValue swEdition) {
        this.swEdition = swEdition.getAbbreviation();
        return this;
    }

    /**
     * <p>
     * Sets the target software environment for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:[update]:[edition]:[language]:[sw_edition]:<b>[target_sw]</b>:[target_hw]:[other]</pre>
     *
     * @param targetSw the bind value of NA or ANY
     * @return the builder
     */
    public CpeBuilder targetSw(final BindValue targetSw) {
        this.targetSw = targetSw.getAbbreviation();
        return this;
    }

    /**
     * <p>
     * Sets the target hardware environment for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:[update]:[edition]:[language]:[sw_edition]:[target_sw]:<b>[target_hw]</b>:[other]</pre>
     *
     * @param targetHw the bind value of NA or ANY
     * @return the builder
     */
    public CpeBuilder targetHw(final BindValue targetHw) {
        this.targetHw = targetHw.getAbbreviation();
        return this;
    }

    /**
     * <p>
     * Sets the other component for the CPE entry.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:[update]:[edition]:[language]:[sw_edition]:[target_sw]:[target_hw]:<b>[other]</b></pre>
     *
     * @param other the bind value of NA or ANY
     * @return the builder
     */
    public CpeBuilder other(final BindValue other) {
        this.other = other.getAbbreviation();
        return this;
    }

    /**
     * <p>
     * Sets the vendor for the CPE entry as a Well Formed string. See the
     * <a href="https://nvlpubs.nist.gov/nistpubs/Legacy/IR/nistir7695.pdf">CPE
     * 2.3 Specification</a> for more information on Well Formed Names.</p>
     * <pre>cpe:2.3:[part]:<b>[vendor]</b>:[product]:[version]:[update]:[edition]:[language]:[sw_edition]:[target_sw]:[target_hw]:[other]</pre>
     *
     * @param vendor the vendor name
     * @return the builder
     */
    public CpeBuilder wfVendor(final String vendor) {
        this.vendor = vendor;
        return this;
    }

    /**
     * <p>
     * Sets the product for the CPE entry as a Well Formed string. See the
     * <a href="https://nvlpubs.nist.gov/nistpubs/Legacy/IR/nistir7695.pdf">CPE
     * 2.3 Specification</a> for more information on Well Formed Names.</p>
     * <pre>cpe:2.3:[part]:[vendor]:<b>[product]</b>:[version]:[update]:[edition]:[language]:[sw_edition]:[target_sw]:[target_hw]:[other]</pre>
     *
     * @param product the product name
     * @return the builder
     */
    public CpeBuilder wfProduct(final String product) {
        this.product = product;
        return this;
    }

    /**
     * <p>
     * Sets the version for the CPE entry as a Well Formed string. See the
     * <a href="https://nvlpubs.nist.gov/nistpubs/Legacy/IR/nistir7695.pdf">CPE
     * 2.3 Specification</a> for more information on Well Formed Names.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:<b>[version]</b>:[update]:[edition]:[language]:[sw_edition]:[target_sw]:[target_hw]:[other]</pre>
     *
     * @param version the version number
     * @return the builder
     */
    public CpeBuilder wfVersion(final String version) {
        this.version = version;
        return this;
    }

    /**
     * <p>
     * Sets the update version for the CPE entry as a Well Formed string. See
     * the
     * <a href="https://nvlpubs.nist.gov/nistpubs/Legacy/IR/nistir7695.pdf">CPE
     * 2.3 Specification</a> for more information on Well Formed Names.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:<b>[update]</b>:[edition]:[language]:[sw_edition]:[target_sw]:[target_hw]:[other]</pre>
     *
     * @param update the update version
     * @return the builder
     */
    public CpeBuilder wfUpdate(final String update) {
        this.update = update;
        return this;
    }

    /**
     * <p>
     * Sets the edition for the CPE entry as a Well Formed string. See the
     * <a href="https://nvlpubs.nist.gov/nistpubs/Legacy/IR/nistir7695.pdf">CPE
     * 2.3 Specification</a> for more information on Well Formed Names.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:[update]:<b>[edition]</b>:[language]:[sw_edition]:[target_sw]:[target_hw]:[other]</pre>
     *
     * @param edition the edition name
     * @return the builder
     */
    public CpeBuilder wfEdition(final String edition) {
        this.edition = edition;
        return this;
    }

    /**
     * <p>
     * Sets the language for the CPE entry as a Well Formed string. See the
     * <a href="https://nvlpubs.nist.gov/nistpubs/Legacy/IR/nistir7695.pdf">CPE
     * 2.3 Specification</a> for more information on Well Formed Names.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:[update]:[edition]:<b>[language]</b>:[sw_edition]:[target_sw]:[target_hw]:[other]</pre>
     *
     * @param language the language name
     * @return the builder
     */
    public CpeBuilder wfLanguage(final String language) {
        this.language = language;
        return this;
    }

    /**
     * <p>
     * Sets the software edition for the CPE entry as a Well Formed Name
     * attribute. See the
     * <a href="https://nvlpubs.nist.gov/nistpubs/Legacy/IR/nistir7695.pdf">CPE
     * 2.3 Specification</a> for more information on Well Formed Names.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:[update]:[edition]:[language]:<b>[sw_edition]</b>:[target_sw]:[target_hw]:[other]</pre>
     *
     * @param swEdition the software edition
     * @return the builder
     */
    public CpeBuilder wfSwEdition(final String swEdition) {
        this.swEdition = swEdition;
        return this;
    }

    /**
     * <p>
     * Sets the target software environment for the CPE entry as a Well Formed
     * Name attribute. See the
     * <a href="https://nvlpubs.nist.gov/nistpubs/Legacy/IR/nistir7695.pdf">CPE
     * 2.3 Specification</a> for more information on Well Formed Names.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:[update]:[edition]:[language]:[sw_edition]:<b>[target_sw]</b>:[target_hw]:[other]</pre>
     *
     * @param targetSw the target software environment
     * @return the builder
     */
    public CpeBuilder wfTargetSw(final String targetSw) {
        this.targetSw = targetSw;
        return this;
    }

    /**
     * <p>
     * Sets the target hardware environment for the CPE entry as a Well Formed
     * Name attribute. See the
     * <a href="https://nvlpubs.nist.gov/nistpubs/Legacy/IR/nistir7695.pdf">CPE
     * 2.3 Specification</a> for more information on Well Formed Names.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:[update]:[edition]:[language]:[sw_edition]:[target_sw]:<b>[target_hw]</b>:[other]</pre>
     *
     * @param targetHw the target hardware environment
     * @return the builder
     */
    public CpeBuilder wfTargetHw(final String targetHw) {
        this.targetHw = targetHw;
        return this;
    }

    /**
     * <p>
     * Sets the other component for the CPE entry as a Well Formed Name
     * attribute. See the
     * <a href="https://nvlpubs.nist.gov/nistpubs/Legacy/IR/nistir7695.pdf">CPE
     * 2.3 Specification</a> for more information on Well Formed Names.</p>
     * <pre>cpe:2.3:[part]:[vendor]:[product]:[version]:[update]:[edition]:[language]:[sw_edition]:[target_sw]:[target_hw]:<b>[other]</b></pre>
     *
     * @param other the other component
     * @return the builder
     */
    public CpeBuilder wfOther(final String other) {
        this.other = other;
        return this;
    }

    /**
     * Builds the CPE Object.
     *
     * @return the CPE Object
     * @throws CpeValidationException thrown if one of the CPE components is
     * invalid
     */
    public Cpe build() throws CpeValidationException {
        Cpe cpe = new Cpe(part, vendor, product, version, update, edition,
                language, swEdition, targetSw, targetHw, other);
        reset();
        return cpe;
    }
}
