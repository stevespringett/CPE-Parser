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

import org.junit.jupiter.api.Test;
import us.springett.parsers.cpe.exceptions.CpeParsingException;
import us.springett.parsers.cpe.exceptions.CpeValidationException;
import us.springett.parsers.cpe.values.Part;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CpeParserTest {
    /**
     * Test the Parse method.
     *
     * @throws CpeParsingException thrown if there is a parsing error
     */
    @Test
    public void testParse() throws CpeParsingException {
        Cpe cpe = CpeParser.parse("cpe:/a:hiox_india:guest_book:4.0");
        assertEquals(Part.APPLICATION, cpe.getPart());
        assertEquals("hiox_india", cpe.getVendor());
        assertEquals("guest_book", cpe.getProduct());
        assertEquals("4.0", cpe.getVersion());
        assertEquals("*", cpe.getUpdate());
        assertEquals("*", cpe.getEdition());
        assertEquals("*", cpe.getLanguage());

        cpe = CpeParser.parse("cpe:/a:adobe:flash_player:::~~~chrome~~");
        assertEquals(Part.APPLICATION, cpe.getPart());
        assertEquals("adobe", cpe.getVendor());
        assertEquals("flash_player", cpe.getProduct());
        assertEquals("*", cpe.getVersion());
        assertEquals("*", cpe.getUpdate());
        assertEquals("*", cpe.getEdition());
        assertEquals("*", cpe.getSwEdition());
        assertEquals("chrome", cpe.getTargetSw());
        assertEquals("*", cpe.getTargetHw());
        assertEquals("*", cpe.getLanguage());

        cpe = CpeParser.parse("cpe:2.3:a:adobe:flash_player:*:*:*:*:*:chrome:*:*");
        assertEquals(Part.APPLICATION, cpe.getPart());
        assertEquals("adobe", cpe.getVendor());
        assertEquals("flash_player", cpe.getProduct());
        assertEquals("*", cpe.getVersion());
        assertEquals("*", cpe.getUpdate());
        assertEquals("*", cpe.getEdition());
        assertEquals("*", cpe.getLanguage());
        assertEquals("*", cpe.getSwEdition());
        assertEquals("chrome", cpe.getTargetSw());
        assertEquals("*", cpe.getTargetHw());
        assertEquals("*", cpe.getOther());

        cpe = CpeParser.parse("cpe:/a:oracle:connector/j:5.1.27", true);
        assertEquals(Part.APPLICATION, cpe.getPart());
        assertEquals("oracle", cpe.getVendor());
        assertEquals("connector/j", cpe.getProduct());
        assertEquals("5.1.27", cpe.getVersion());
        assertEquals("*", cpe.getUpdate());
        assertEquals("*", cpe.getEdition());
        assertEquals("*", cpe.getLanguage());
        
        
        
        cpe = CpeParser.parse("cpe:2.3:a:disney:where\\'s_my_perry?_free:1.5.1:*:*:*:*:android:*:*", true);
        assertEquals(Part.APPLICATION, cpe.getPart());
        assertEquals("disney", cpe.getVendor());
        assertEquals("where's_my_perry?_free", cpe.getProduct());
        assertEquals("1.5.1", cpe.getVersion());
        assertEquals("android", cpe.getTargetSw());
        
        cpe = CpeParser.parse("cpe:2.3:a:gratta_\\&_vinci?_project:gratta_\\&_vinci?:0.21.13167.93474:*:*:*:*:android:*:*", true);
        assertEquals(Part.APPLICATION, cpe.getPart());
        assertEquals("gratta_&_vinci?_project", cpe.getVendor());
        assertEquals("gratta_&_vinci?", cpe.getProduct());
        assertEquals("0.21.13167.93474", cpe.getVersion());
        assertEquals("android", cpe.getTargetSw());
        
        cpe = CpeParser.parse("cpe:2.3:a:*?t?t?*:t#*:*#:*:*:*:*:*:*:*", true);
        assertEquals(Part.APPLICATION, cpe.getPart());
        assertEquals("*?t?t?*", cpe.getVendor());
        assertEquals("*?t\\?t?*", cpe.getWellFormedVendor());
        assertEquals("t#*", cpe.getProduct());
        assertEquals("t\\#*", cpe.getWellFormedProduct());
        assertEquals("*#", cpe.getVersion());
        assertEquals("*\\#", cpe.getWellFormedVersion());
    }

    @Test
    public void testParseException() {
        assertThatThrownBy(() -> CpeParser.parse("cpe:/a:oracle:connector/j:5.1.27", false))
                .isInstanceOf(CpeParsingException.class);
    }

    /**
     * Test the parse23 method.
     *
     * @throws CpeParsingException thrown if there is a parsing error
     */
    @Test
    public void testParse23() throws CpeParsingException {
        
        Cpe cpe = CpeParser.parse23("cpe:2.3:a:hiox_india:guest_book:4.0:*:*:*:*:*:*:?");
        assertEquals(Part.APPLICATION, cpe.getPart());
        assertEquals("hiox_india", cpe.getVendor());
        assertEquals("guest_book", cpe.getProduct());
        assertEquals("4.0", cpe.getVersion());
        assertEquals("*", cpe.getUpdate());
        assertEquals("*", cpe.getEdition());
        assertEquals("*", cpe.getLanguage());
        assertEquals("*", cpe.getSwEdition());
        assertEquals("*", cpe.getTargetSw());
        assertEquals("*", cpe.getTargetHw());
        assertEquals("?", cpe.getOther());

        cpe = CpeParser.parse23("cpe:2.3:a:test\\::guest_book:4.0:*:*:*:*:*:*:*");
        assertEquals(Part.APPLICATION, cpe.getPart());
        assertEquals("test:", cpe.getVendor());
        assertEquals("guest_book", cpe.getProduct());
        assertEquals("4.0", cpe.getVersion());
        assertEquals("*", cpe.getUpdate());
        assertEquals("*", cpe.getEdition());
        assertEquals("*", cpe.getLanguage());
        assertEquals("*", cpe.getSwEdition());
        assertEquals("*", cpe.getTargetSw());
        assertEquals("*", cpe.getTargetHw());
        assertEquals("*", cpe.getOther());

        cpe = CpeParser.parse23("cpe:2.3:a:jenkins:pipeline\\:build_step:*:*:*:*:*:jenkins:*:*");
        assertEquals(Part.APPLICATION, cpe.getPart());
        assertEquals("jenkins", cpe.getVendor());
        assertEquals("pipeline:build_step", cpe.getProduct());
        assertEquals("*", cpe.getVersion());
        assertEquals("*", cpe.getUpdate());
        assertEquals("*", cpe.getEdition());
        assertEquals("*", cpe.getLanguage());
        assertEquals("*", cpe.getSwEdition());
        assertEquals("jenkins", cpe.getTargetSw());
        assertEquals("*", cpe.getTargetHw());
        assertEquals("*", cpe.getOther());
    }

    /**
     * Test of parse22 method, of class CpeParser.
     *
     * @throws Exception thrown if there is a parsing error
     */
    @Test
    public void testParse22() throws Exception {
        
        Cpe cpe = CpeParser.parse22("cpe:/a:microsoft:internet_explorer%01%01%01%01:%01:beta::c%2b%2b");
        assertEquals(Part.APPLICATION, cpe.getPart());
        assertEquals("microsoft", cpe.getVendor());
        assertEquals("internet_explorer????", cpe.getProduct());
        assertEquals("?", cpe.getVersion());
        assertEquals("beta", cpe.getUpdate());
        assertEquals("*", cpe.getEdition());
        assertEquals("c++", cpe.getLanguage());
        assertEquals("*", cpe.getSwEdition());
        assertEquals("*", cpe.getTargetSw());
        assertEquals("*", cpe.getTargetHw());
        assertEquals("*", cpe.getOther());

        cpe = CpeParser.parse22("cpe:/a:microsoft:internet_explorer%01%01%01%01:%01");
        assertEquals(Part.APPLICATION, cpe.getPart());
        assertEquals("microsoft", cpe.getVendor());
        assertEquals("internet_explorer????", cpe.getProduct());
        assertEquals("?", cpe.getVersion());
        assertEquals("*", cpe.getUpdate());
        assertEquals("*", cpe.getEdition());
        assertEquals("*", cpe.getLanguage());
        assertEquals("*", cpe.getSwEdition());
        assertEquals("*", cpe.getTargetSw());
        assertEquals("*", cpe.getTargetHw());
        assertEquals("*", cpe.getOther());

        cpe = CpeParser.parse22("cpe:/a:microsoft:internet_explorer%01%01%01%01");
        assertEquals(Part.APPLICATION, cpe.getPart());
        assertEquals("microsoft", cpe.getVendor());
        assertEquals("internet_explorer????", cpe.getProduct());
        assertEquals("*", cpe.getVersion());
        assertEquals("*", cpe.getUpdate());
        assertEquals("*", cpe.getEdition());
        assertEquals("*", cpe.getLanguage());
        assertEquals("*", cpe.getSwEdition());
        assertEquals("*", cpe.getTargetSw());
        assertEquals("*", cpe.getTargetHw());
        assertEquals("*", cpe.getOther());

        cpe = CpeParser.parse22("cpe:/a:microsoft");
        assertEquals(Part.APPLICATION, cpe.getPart());
        assertEquals("microsoft", cpe.getVendor());
        assertEquals("*", cpe.getProduct());
        assertEquals("*", cpe.getVersion());
        assertEquals("*", cpe.getUpdate());
        assertEquals("*", cpe.getEdition());
        assertEquals("*", cpe.getLanguage());
        assertEquals("*", cpe.getSwEdition());
        assertEquals("*", cpe.getTargetSw());
        assertEquals("*", cpe.getTargetHw());
        assertEquals("*", cpe.getOther());

        cpe = CpeParser.parse22("cpe:/a:");
        assertEquals(Part.APPLICATION, cpe.getPart());
        assertEquals("*", cpe.getVendor());
        assertEquals("*", cpe.getProduct());
        assertEquals("*", cpe.getVersion());
        assertEquals("*", cpe.getUpdate());
        assertEquals("*", cpe.getEdition());
        assertEquals("*", cpe.getLanguage());
        assertEquals("*", cpe.getSwEdition());
        assertEquals("*", cpe.getTargetSw());
        assertEquals("*", cpe.getTargetHw());
        assertEquals("*", cpe.getOther());
    }

    
    @Test
    public void testParse22Lenient() throws Exception {
        
        Cpe cpe = CpeParser.parse22("cpe:/a:microsoft:internet_explorer%01%01%01%01:%01:beta::c%2b%2b",true);
        assertEquals(Part.APPLICATION, cpe.getPart());
        assertEquals("microsoft", cpe.getVendor());
        assertEquals("internet_explorer????", cpe.getProduct());
        assertEquals("?", cpe.getVersion());
        assertEquals("beta", cpe.getUpdate());
        assertEquals("*", cpe.getEdition());
        assertEquals("c++", cpe.getLanguage());
        assertEquals("*", cpe.getSwEdition());
        assertEquals("*", cpe.getTargetSw());
        assertEquals("*", cpe.getTargetHw());
        assertEquals("*", cpe.getOther());

        cpe = CpeParser.parse22("cpe:/a:microsoft:internet_explorer%01%01%01%01:%01",true);
        assertEquals(Part.APPLICATION, cpe.getPart());
        assertEquals("microsoft", cpe.getVendor());
        assertEquals("internet_explorer????", cpe.getProduct());
        assertEquals("?", cpe.getVersion());
        assertEquals("*", cpe.getUpdate());
        assertEquals("*", cpe.getEdition());
        assertEquals("*", cpe.getLanguage());
        assertEquals("*", cpe.getSwEdition());
        assertEquals("*", cpe.getTargetSw());
        assertEquals("*", cpe.getTargetHw());
        assertEquals("*", cpe.getOther());

        cpe = CpeParser.parse22("cpe:/a:microsoft:internet_explorer%01%01%01%01",true);
        assertEquals(Part.APPLICATION, cpe.getPart());
        assertEquals("microsoft", cpe.getVendor());
        assertEquals("internet_explorer????", cpe.getProduct());
        assertEquals("*", cpe.getVersion());
        assertEquals("*", cpe.getUpdate());
        assertEquals("*", cpe.getEdition());
        assertEquals("*", cpe.getLanguage());
        assertEquals("*", cpe.getSwEdition());
        assertEquals("*", cpe.getTargetSw());
        assertEquals("*", cpe.getTargetHw());
        assertEquals("*", cpe.getOther());

        cpe = CpeParser.parse22("cpe:/a:microsoft",true);
        assertEquals(Part.APPLICATION, cpe.getPart());
        assertEquals("microsoft", cpe.getVendor());
        assertEquals("*", cpe.getProduct());
        assertEquals("*", cpe.getVersion());
        assertEquals("*", cpe.getUpdate());
        assertEquals("*", cpe.getEdition());
        assertEquals("*", cpe.getLanguage());
        assertEquals("*", cpe.getSwEdition());
        assertEquals("*", cpe.getTargetSw());
        assertEquals("*", cpe.getTargetHw());
        assertEquals("*", cpe.getOther());

        cpe = CpeParser.parse22("cpe:/a:",true);
        assertEquals(Part.APPLICATION, cpe.getPart());
        assertEquals("*", cpe.getVendor());
        assertEquals("*", cpe.getProduct());
        assertEquals("*", cpe.getVersion());
        assertEquals("*", cpe.getUpdate());
        assertEquals("*", cpe.getEdition());
        assertEquals("*", cpe.getLanguage());
        assertEquals("*", cpe.getSwEdition());
        assertEquals("*", cpe.getTargetSw());
        assertEquals("*", cpe.getTargetHw());
        assertEquals("*", cpe.getOther());
    }
    
    /**
     * Test of unpackEdition method, of class CpeParser.
     *
     * @throws Exception thrown if there is a parsing error
     */
    @Test
    public void testUnpackEdition() throws Exception {
        
        CpeBuilder cb = new CpeBuilder();
        String edition = null;
        CpeParser.unpackEdition(edition, cb, false);
        Cpe cpe = cb.build();
        assertEquals("*", cpe.getEdition());
        assertEquals("*", cpe.getSwEdition());
        assertEquals("*", cpe.getTargetSw());
        assertEquals("*", cpe.getTargetHw());
        assertEquals("*", cpe.getOther());

        edition = "";
        CpeParser.unpackEdition(edition, cb, false);
        cpe = cb.build();
        assertEquals("*", cpe.getEdition());
        assertEquals("*", cpe.getSwEdition());
        assertEquals("*", cpe.getTargetSw());
        assertEquals("*", cpe.getTargetHw());
        assertEquals("*", cpe.getOther());

        edition = "one";
        CpeParser.unpackEdition(edition, cb, false);
        cpe = cb.build();
        assertEquals("one", cpe.getEdition());
        assertEquals("*", cpe.getSwEdition());
        assertEquals("*", cpe.getTargetSw());
        assertEquals("*", cpe.getTargetHw());
        assertEquals("*", cpe.getOther());

        edition = "~e~sw~ts~th~o";
        CpeParser.unpackEdition(edition, cb, false);
        cpe = cb.build();
        assertEquals("e", cpe.getEdition());
        assertEquals("sw", cpe.getSwEdition());
        assertEquals("ts", cpe.getTargetSw());
        assertEquals("th", cpe.getTargetHw());
        assertEquals("o", cpe.getOther());

        edition = "~~sw~ts~th~o";
        CpeParser.unpackEdition(edition, cb, false);
        cpe = cb.build();
        assertEquals("*", cpe.getEdition());
        assertEquals("sw", cpe.getSwEdition());
        assertEquals("ts", cpe.getTargetSw());
        assertEquals("th", cpe.getTargetHw());
        assertEquals("o", cpe.getOther());

        edition = "~e~sw~ts~th~";
        CpeParser.unpackEdition(edition, cb, false);
        cpe = cb.build();
        assertEquals("e", cpe.getEdition());
        assertEquals("sw", cpe.getSwEdition());
        assertEquals("ts", cpe.getTargetSw());
        assertEquals("th", cpe.getTargetHw());
        assertEquals("*", cpe.getOther());

        edition = "~~sw~ts~th~";
        CpeParser.unpackEdition(edition, cb, false);
        cpe = cb.build();
        assertEquals("*", cpe.getEdition());
        assertEquals("sw", cpe.getSwEdition());
        assertEquals("ts", cpe.getTargetSw());
        assertEquals("th", cpe.getTargetHw());
        assertEquals("*", cpe.getOther());

        edition = "~~~ts~th~";
        CpeParser.unpackEdition(edition, cb, false);
        cpe = cb.build();
        assertEquals("*", cpe.getEdition());
        assertEquals("*", cpe.getSwEdition());
        assertEquals("ts", cpe.getTargetSw());
        assertEquals("th", cpe.getTargetHw());
        assertEquals("*", cpe.getOther());

        edition = "~~sw~~~";
        CpeParser.unpackEdition(edition, cb, false);
        cpe = cb.build();
        assertEquals("*", cpe.getEdition());
        assertEquals("sw", cpe.getSwEdition());
        assertEquals("*", cpe.getTargetSw());
        assertEquals("*", cpe.getTargetHw());
        assertEquals("*", cpe.getOther());

        edition = "~~~~th~";
        CpeParser.unpackEdition(edition, cb, false);
        cpe = cb.build();
        assertEquals("*", cpe.getEdition());
        assertEquals("*", cpe.getSwEdition());
        assertEquals("*", cpe.getTargetSw());
        assertEquals("th", cpe.getTargetHw());
        assertEquals("*", cpe.getOther());

        edition = "~~~~~";
        CpeParser.unpackEdition(edition, cb, false);
        cpe = cb.build();
        assertEquals("*", cpe.getEdition());
        assertEquals("*", cpe.getSwEdition());
        assertEquals("*", cpe.getTargetSw());
        assertEquals("*", cpe.getTargetHw());
        assertEquals("*", cpe.getOther());

        edition = "~~~chrome~~";
        CpeParser.unpackEdition(edition, cb, false);
        cpe = cb.build();
        assertEquals("*", cpe.getEdition());
        assertEquals("*", cpe.getSwEdition());
        assertEquals("chrome", cpe.getTargetSw());
        assertEquals("*", cpe.getTargetHw());
        assertEquals("*", cpe.getOther());

        edition = "~test/test~test?test~test:test~test&test~test*test";
        CpeParser.unpackEdition(edition, cb, true);
        cpe = cb.build();
        assertEquals("test\\/test", cpe.getWellFormedEdition());
        assertEquals("test\\?test", cpe.getWellFormedSwEdition());
        assertEquals("test\\:test", cpe.getWellFormedTargetSw());
        assertEquals("test\\&test", cpe.getWellFormedTargetHw());
        assertEquals("test\\*test", cpe.getWellFormedOther());
    }

    @Test
    public void testUnpackEditionException1() {
        CpeBuilder cb = new CpeBuilder();
        String edition = "~test/test~test?test~test:test~test&test~test*test";
        assertThatThrownBy(() -> CpeParser.unpackEdition(edition, cb, false))
                .isInstanceOf(CpeParsingException.class);
    }

    @Test
    public void testUnpackEditionException2() {
        CpeBuilder cb = new CpeBuilder();
        String edition = "~test~test?test~test:test~test&test~test*test";
        assertThatThrownBy(() -> CpeParser.unpackEdition(edition, cb, false))
                .isInstanceOf(CpeParsingException.class);
    }

    @Test
    public void testUnpackEditionException3() {
        CpeBuilder cb = new CpeBuilder();
        String edition = "~test~test~test:test~test&test~test*test";
        assertThatThrownBy(() -> CpeParser.unpackEdition(edition, cb, false))
                .isInstanceOf(CpeParsingException.class);
    }

    @Test
    public void testUnpackEditionException4() {
        CpeBuilder cb = new CpeBuilder();
        String edition = "~test~test~test~test&test~test*test";
        assertThatThrownBy(() -> CpeParser.unpackEdition(edition, cb, false))
                .isInstanceOf(CpeParsingException.class);
    }

    @Test
    public void testUnpackEditionException5() {
        CpeBuilder cb = new CpeBuilder();
        String edition = "~test~test~test~test~test*test";
        assertThatThrownBy(() -> CpeParser.unpackEdition(edition, cb, false))
                .isInstanceOf(CpeParsingException.class);
    }

    /**
     * Test the parsing and binding cycles to ensure everything stays the same.
     *
     * @throws CpeParsingException thrown if there is a parsing error
     */
    @Test
    public void testParseBindUnbindCycle() throws Exception {
        String initial = "cpe:2.3:a:embarcadero:embarcadero_c\\+\\+builder_xe6:20.0.15596.9843:*:*:en:*:*:*:other";
        String cpeString = initial;
        for (int x = 0; x < 10; x++) {
            Cpe cpe = CpeParser.parse(cpeString);
            cpeString = cpe.toCpe22Uri();
            cpe = CpeParser.parse(cpeString);
            cpeString = cpe.toCpe23FS();
        }
        assertEquals(initial, cpeString);
    }

    @Test
    public void testInvalidCpeNull() {
        assertThatThrownBy(() -> CpeParser.parse(null))
                .isInstanceOf(CpeParsingException.class);
    }

    @Test
    public void testInvalidCpeEmpty() {
        assertThatThrownBy(() -> CpeParser.parse(""))
                .isInstanceOf(CpeParsingException.class);
    }

    @Test
    public void testInvalidCpeInvalid() {
        assertThatThrownBy(() -> CpeParser.parse("invalid"))
                .isInstanceOf(CpeParsingException.class);
    }

    @Test
    public void testInvalidCpe22Empty() {
        assertThatThrownBy(() -> CpeParser.parse22(""))
                .isInstanceOf(CpeParsingException.class);
    }

    @Test
    public void testInvalidCpe23Empty() {
        assertThatThrownBy(() -> CpeParser.parse23(""))
                .isInstanceOf(CpeParsingException.class);
    }

    @Test
    public void testInvalidCpe22Null() {
        assertThatThrownBy(() -> CpeParser.parse22(null))
                .isInstanceOf(CpeParsingException.class);
    }

    @Test
    public void testInvalidCpe23Null() {
        assertThatThrownBy(() -> CpeParser.parse23(null))
                .isInstanceOf(CpeParsingException.class);
    }

    @Test
    public void testInvalidCpe22Invalid() {
        assertThatThrownBy(() -> CpeParser.parse22("invalid"))
                .isInstanceOf(CpeParsingException.class);
    }

    @Test
    public void testInvalidCpe22Invalid2() {
        assertThatThrownBy(() -> CpeParser.parse22("1:2:3:4:5:6:7:8:9:10"))
                .isInstanceOf(CpeParsingException.class);
    }

    @Test
    public void testInvalidCpe23Invalid() {
        assertThatThrownBy(() -> CpeParser.parse23("invalid"))
                .isInstanceOf(CpeParsingException.class);
    }

    @Test
    public void testInvalidCpe22InvalidPart() {
        assertThatThrownBy(() -> CpeParser.parse22("cpe:/t:vendor:product:1.0"))
                .isInstanceOf(CpeParsingException.class);
    }

    @Test
    public void testInvalidCpe22InvalidPart2() {
        assertThatThrownBy(() -> CpeParser.parse22("cpe:/ab:vendor:product:1.0"))
                .isInstanceOf(CpeParsingException.class);
    }

    @Test
    public void testInvalidCpe23InvalidPart() {
        assertThatThrownBy(() -> CpeParser.parse23("cpe:2.3:t:vendor:product:1.0:*:*:*:*:*:*:*"))
                .isInstanceOf(CpeParsingException.class);
    }

    @Test
    public void testInvalidCpePart() {
        assertThatThrownBy(() -> CpeParser.parse("cpe:/t:vendor:product:1.0"))
                .isInstanceOf(CpeParsingException.class);
    }

    @Test
    public void testInvalidCpe23TooManyParts() {
        assertThatThrownBy(() -> CpeParser.parse23("cpe:2.3:a:vendor:product:1.0:*:*:*:*:*:*:*:*"))
                .isInstanceOf(CpeParsingException.class);
    }

    @Test
    public void testInvalidCpe23TooFewParts() {
        assertThatThrownBy(() -> CpeParser.parse23("cpe:2.3:a:vendor:product:1.0:*:*:*:*:*:*"))
                .isInstanceOf(CpeParsingException.class);
    }

    @Test
    public void testInvalidCpe22Part() {
        assertThatThrownBy(() -> CpeParser.parse22("cpe:/t:vendor:product:1.0"))
                .isInstanceOf(CpeParsingException.class);
    }

    @Test
    public void testEmptyPart() throws Exception {
        Cpe cpe = CpeParser.parse22("cpe:/:redhat:enterprise_linux:::hypervisor");
        assertEquals(Part.ANY, cpe.getPart());
        assertEquals("cpe:/*:redhat:enterprise_linux:::hypervisor", cpe.toCpe22Uri());
    }
}
