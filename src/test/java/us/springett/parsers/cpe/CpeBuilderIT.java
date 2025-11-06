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

import java.text.ParseException;
import org.junit.jupiter.api.Test;
import org.mitre.cpe.common.LogicalValue;
import org.mitre.cpe.common.WellFormedName;
import org.mitre.cpe.naming.CPENameBinder;
import org.mitre.cpe.naming.CPENameUnbinder;
import us.springett.parsers.cpe.exceptions.CpeEncodingException;
import us.springett.parsers.cpe.exceptions.CpeParsingException;
import us.springett.parsers.cpe.exceptions.CpeValidationException;
import us.springett.parsers.cpe.util.Convert;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test the CPE Builder.
 *
 * @author Jeremy Long
 */
public class CpeBuilderIT {

    /**
     * Test of part method, of class CpeBuilder.
     *
     * @throws CpeParsingException thrown if there is a parsing error
     * @throws java.text.ParseException thrown if there is a parsing error
     * @throws us.springett.parsers.cpe.exceptions.CpeValidationException thrown if there is a parsing error
     * @throws us.springett.parsers.cpe.exceptions.CpeEncodingException thrown if there is a parsing error
     */
    @Test
    public void testBuild() throws CpeParsingException, ParseException, CpeValidationException, CpeEncodingException {
        testReferenceImpl("a", "some:vendor", "product++", "1.0.0", "beta?", "*", "*", "*", "*", "*", "*");
        testReferenceImpl("o", "microsoft", "windows_8", "-", "-", "x64", "*", "*", "*", "*", "*");
    }

    private void testReferenceImpl(String part, String vendor, String product,
            String version, String update, String edition, String language,
            String sw_edition, String target_sw, String target_hw, String other) throws ParseException, CpeParsingException, CpeValidationException, CpeEncodingException {

        WellFormedName wfn = new WellFormedName(toWellFormed(part),
                toWellFormed(vendor), toWellFormed(product),
                toWellFormed(version), toWellFormed(update),
                toWellFormed(edition), toWellFormed(language),
                toWellFormed(sw_edition), toWellFormed(target_sw),
                toWellFormed(target_hw), toWellFormed(other));
        CpeBuilder builder = new CpeBuilder();
        Cpe cpe = builder.part(part).vendor(vendor).product(product).version(version)
                .update(update).edition(edition).swEdition(sw_edition).targetSw(target_sw)
                .targetHw(target_hw).other(other).language(language).build();

        CPENameBinder cpenb = new CPENameBinder();
        System.out.println(cpenb.bindToURI(wfn).toLowerCase());
        System.out.println(cpe.toCpe22Uri().toLowerCase());
        System.out.println(cpenb.bindToFS(wfn));
        System.out.println(cpe.toCpe23FS());
        assertEquals(cpenb.bindToURI(wfn).toLowerCase(), cpe.toCpe22Uri().toLowerCase());
        assertEquals(cpenb.bindToFS(wfn), cpe.toCpe23FS());
    }

    private Object toWellFormed(String value) {
        if ("*".equals(value)) {
            return new LogicalValue("ANY");
        }
        if ("-".equals(value)) {
            return new LogicalValue("NA");
        }
        return Convert.toWellFormed(value);
    }

    @Test
    public void testReference() throws ParseException {
        CPENameBinder cpeBinder = new CPENameBinder();
        CPENameUnbinder cpeUnbinder = new CPENameUnbinder();

        String original = "cpe:2.3:a:embarcadero:embarcadero_c\\+\\+builder_xe6:20.0.15596.9843:*:*:*:*:*:*:*";

        String cpeString = original;

        WellFormedName wfn = cpeUnbinder.unbindFS(cpeString);
        cpeString = cpeBinder.bindToFS(wfn);

        System.out.println("Original:  " + original);
        System.out.println("Processed: " + cpeString);
        assertEquals(original, cpeString);

    }
}
