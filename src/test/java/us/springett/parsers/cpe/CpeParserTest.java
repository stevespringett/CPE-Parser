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

import us.springett.parsers.cpe.exceptions.CpeParsingException;
import us.springett.parsers.cpe.values.Part;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.assertEquals;

public class CpeParserTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Test the Parse method.
     *
     * @throws CpeParsingException thrown if there is a parsing error
     */
    @Test
    public void testParse() throws CpeParsingException {
        exception = ExpectedException.none();

        Cpe cpe = CpeParser.parse("cpe:/a:hiox_india:guest_book:4.0");
        Assert.assertEquals(Part.APPLICATION, cpe.getPart());
        Assert.assertEquals("hiox_india", cpe.getVendor());
        Assert.assertEquals("guest_book", cpe.getProduct());
        Assert.assertEquals("4.0", cpe.getVersion());
        Assert.assertEquals("*", cpe.getUpdate());
        Assert.assertEquals("*", cpe.getEdition());
        Assert.assertEquals("*", cpe.getLanguage());

        cpe = CpeParser.parse("cpe:/a:adobe:flash_player:::~~~chrome~~");
        Assert.assertEquals(Part.APPLICATION, cpe.getPart());
        Assert.assertEquals("adobe", cpe.getVendor());
        Assert.assertEquals("flash_player", cpe.getProduct());
        Assert.assertEquals("*", cpe.getVersion());
        Assert.assertEquals("*", cpe.getUpdate());
        Assert.assertEquals("*", cpe.getEdition());
        Assert.assertEquals("*", cpe.getSwEdition());
        Assert.assertEquals("chrome", cpe.getTargetSw());
        Assert.assertEquals("*", cpe.getTargetHw());
        Assert.assertEquals("*", cpe.getLanguage());

        cpe = CpeParser.parse("cpe:2.3:a:adobe:flash_player:*:*:*:*:*:chrome:*:*");
        Assert.assertEquals(Part.APPLICATION, cpe.getPart());
        Assert.assertEquals("adobe", cpe.getVendor());
        Assert.assertEquals("flash_player", cpe.getProduct());
        Assert.assertEquals("*", cpe.getVersion());
        Assert.assertEquals("*", cpe.getUpdate());
        Assert.assertEquals("*", cpe.getEdition());
        Assert.assertEquals("*", cpe.getLanguage());
        Assert.assertEquals("*", cpe.getSwEdition());
        Assert.assertEquals("chrome", cpe.getTargetSw());
        Assert.assertEquals("*", cpe.getTargetHw());
        Assert.assertEquals("*", cpe.getOther());
    }

    /**
     * Test the parse23 method.
     *
     * @throws CpeParsingException thrown if there is a parsing error
     */
    @Test
    public void testParse23() throws CpeParsingException {
        exception = ExpectedException.none();

        Cpe cpe = CpeParser.parse23("cpe:2.3:a:hiox_india:guest_book:4.0:*:*:*:*:*:*:?");
        Assert.assertEquals(Part.APPLICATION, cpe.getPart());
        Assert.assertEquals("hiox_india", cpe.getVendor());
        Assert.assertEquals("guest_book", cpe.getProduct());
        Assert.assertEquals("4.0", cpe.getVersion());
        Assert.assertEquals("*", cpe.getUpdate());
        Assert.assertEquals("*", cpe.getEdition());
        Assert.assertEquals("*", cpe.getLanguage());
        Assert.assertEquals("*", cpe.getSwEdition());
        Assert.assertEquals("*", cpe.getTargetSw());
        Assert.assertEquals("*", cpe.getTargetHw());
        Assert.assertEquals("?", cpe.getOther());

        cpe = CpeParser.parse23("cpe:2.3:a:test\\::guest_book:4.0:*:*:*:*:*:*:*");
        Assert.assertEquals(Part.APPLICATION, cpe.getPart());
        Assert.assertEquals("test:", cpe.getVendor());
        Assert.assertEquals("guest_book", cpe.getProduct());
        Assert.assertEquals("4.0", cpe.getVersion());
        Assert.assertEquals("*", cpe.getUpdate());
        Assert.assertEquals("*", cpe.getEdition());
        Assert.assertEquals("*", cpe.getLanguage());
        Assert.assertEquals("*", cpe.getSwEdition());
        Assert.assertEquals("*", cpe.getTargetSw());
        Assert.assertEquals("*", cpe.getTargetHw());
        Assert.assertEquals("*", cpe.getOther());
        
        
        cpe = CpeParser.parse23("cpe:2.3:a:jenkins:pipeline\\:build_step:*:*:*:*:*:jenkins:*:*");
        Assert.assertEquals(Part.APPLICATION, cpe.getPart());
        Assert.assertEquals("jenkins", cpe.getVendor());
        Assert.assertEquals("pipeline:build_step", cpe.getProduct());
        Assert.assertEquals("*", cpe.getVersion());
        Assert.assertEquals("*", cpe.getUpdate());
        Assert.assertEquals("*", cpe.getEdition());
        Assert.assertEquals("*", cpe.getLanguage());
        Assert.assertEquals("*", cpe.getSwEdition());
        Assert.assertEquals("jenkins", cpe.getTargetSw());
        Assert.assertEquals("*", cpe.getTargetHw());
        Assert.assertEquals("*", cpe.getOther());
    }

    /**
     * Test of parse22 method, of class CpeParser.
     *
     * @throws Exception thrown if there is a parsing error
     */
    @Test
    public void testParse22() throws Exception {
        exception = ExpectedException.none();

        Cpe cpe = CpeParser.parse22("cpe:/a:microsoft:internet_explorer%01%01%01%01:%01:beta::c%2b%2b");
        Assert.assertEquals(Part.APPLICATION, cpe.getPart());
        Assert.assertEquals("microsoft", cpe.getVendor());
        Assert.assertEquals("internet_explorer????", cpe.getProduct());
        Assert.assertEquals("?", cpe.getVersion());
        Assert.assertEquals("beta", cpe.getUpdate());
        Assert.assertEquals("*", cpe.getEdition());
        Assert.assertEquals("c++", cpe.getLanguage());
        Assert.assertEquals("*", cpe.getSwEdition());
        Assert.assertEquals("*", cpe.getTargetSw());
        Assert.assertEquals("*", cpe.getTargetHw());
        Assert.assertEquals("*", cpe.getOther());

        cpe = CpeParser.parse22("cpe:/a:microsoft:internet_explorer%01%01%01%01:%01");
        Assert.assertEquals(Part.APPLICATION, cpe.getPart());
        Assert.assertEquals("microsoft", cpe.getVendor());
        Assert.assertEquals("internet_explorer????", cpe.getProduct());
        Assert.assertEquals("?", cpe.getVersion());
        Assert.assertEquals("*", cpe.getUpdate());
        Assert.assertEquals("*", cpe.getEdition());
        Assert.assertEquals("*", cpe.getLanguage());
        Assert.assertEquals("*", cpe.getSwEdition());
        Assert.assertEquals("*", cpe.getTargetSw());
        Assert.assertEquals("*", cpe.getTargetHw());
        Assert.assertEquals("*", cpe.getOther());

        cpe = CpeParser.parse22("cpe:/a:microsoft:internet_explorer%01%01%01%01");
        Assert.assertEquals(Part.APPLICATION, cpe.getPart());
        Assert.assertEquals("microsoft", cpe.getVendor());
        Assert.assertEquals("internet_explorer????", cpe.getProduct());
        Assert.assertEquals("*", cpe.getVersion());
        Assert.assertEquals("*", cpe.getUpdate());
        Assert.assertEquals("*", cpe.getEdition());
        Assert.assertEquals("*", cpe.getLanguage());
        Assert.assertEquals("*", cpe.getSwEdition());
        Assert.assertEquals("*", cpe.getTargetSw());
        Assert.assertEquals("*", cpe.getTargetHw());
        Assert.assertEquals("*", cpe.getOther());

        cpe = CpeParser.parse22("cpe:/a:microsoft");
        Assert.assertEquals(Part.APPLICATION, cpe.getPart());
        Assert.assertEquals("microsoft", cpe.getVendor());
        Assert.assertEquals("*", cpe.getProduct());
        Assert.assertEquals("*", cpe.getVersion());
        Assert.assertEquals("*", cpe.getUpdate());
        Assert.assertEquals("*", cpe.getEdition());
        Assert.assertEquals("*", cpe.getLanguage());
        Assert.assertEquals("*", cpe.getSwEdition());
        Assert.assertEquals("*", cpe.getTargetSw());
        Assert.assertEquals("*", cpe.getTargetHw());
        Assert.assertEquals("*", cpe.getOther());

        cpe = CpeParser.parse22("cpe:/a:");
        Assert.assertEquals(Part.APPLICATION, cpe.getPart());
        Assert.assertEquals("*", cpe.getVendor());
        Assert.assertEquals("*", cpe.getProduct());
        Assert.assertEquals("*", cpe.getVersion());
        Assert.assertEquals("*", cpe.getUpdate());
        Assert.assertEquals("*", cpe.getEdition());
        Assert.assertEquals("*", cpe.getLanguage());
        Assert.assertEquals("*", cpe.getSwEdition());
        Assert.assertEquals("*", cpe.getTargetSw());
        Assert.assertEquals("*", cpe.getTargetHw());
        Assert.assertEquals("*", cpe.getOther());
    }

    /**
     * Test of unpackEdition method, of class CpeParser.
     *
     * @throws Exception thrown if there is a parsing error
     */
    @Test
    public void testUnpackEdition() throws Exception {
        exception = ExpectedException.none();

        CpeBuilder cb = new CpeBuilder();
        String edition = null;
        CpeParser.unpackEdition(edition, cb);
        Cpe cpe = cb.build();
        assertEquals("*", cpe.getEdition());
        assertEquals("*", cpe.getSwEdition());
        assertEquals("*", cpe.getTargetSw());
        assertEquals("*", cpe.getTargetHw());
        assertEquals("*", cpe.getOther());

        edition = "";
        CpeParser.unpackEdition(edition, cb);
        cpe = cb.build();
        assertEquals("*", cpe.getEdition());
        assertEquals("*", cpe.getSwEdition());
        assertEquals("*", cpe.getTargetSw());
        assertEquals("*", cpe.getTargetHw());
        assertEquals("*", cpe.getOther());

        edition = "one";
        CpeParser.unpackEdition(edition, cb);
        cpe = cb.build();
        assertEquals("one", cpe.getEdition());
        assertEquals("*", cpe.getSwEdition());
        assertEquals("*", cpe.getTargetSw());
        assertEquals("*", cpe.getTargetHw());
        assertEquals("*", cpe.getOther());

        edition = "~e~sw~ts~th~o";
        CpeParser.unpackEdition(edition, cb);
        cpe = cb.build();
        assertEquals("e", cpe.getEdition());
        assertEquals("sw", cpe.getSwEdition());
        assertEquals("ts", cpe.getTargetSw());
        assertEquals("th", cpe.getTargetHw());
        assertEquals("o", cpe.getOther());

        edition = "~~sw~ts~th~o";
        CpeParser.unpackEdition(edition, cb);
        cpe = cb.build();
        assertEquals("*", cpe.getEdition());
        assertEquals("sw", cpe.getSwEdition());
        assertEquals("ts", cpe.getTargetSw());
        assertEquals("th", cpe.getTargetHw());
        assertEquals("o", cpe.getOther());

        edition = "~e~sw~ts~th~";
        CpeParser.unpackEdition(edition, cb);
        cpe = cb.build();
        assertEquals("e", cpe.getEdition());
        assertEquals("sw", cpe.getSwEdition());
        assertEquals("ts", cpe.getTargetSw());
        assertEquals("th", cpe.getTargetHw());
        assertEquals("*", cpe.getOther());

        edition = "~~sw~ts~th~";
        CpeParser.unpackEdition(edition, cb);
        cpe = cb.build();
        assertEquals("*", cpe.getEdition());
        assertEquals("sw", cpe.getSwEdition());
        assertEquals("ts", cpe.getTargetSw());
        assertEquals("th", cpe.getTargetHw());
        assertEquals("*", cpe.getOther());

        edition = "~~~ts~th~";
        CpeParser.unpackEdition(edition, cb);
        cpe = cb.build();
        assertEquals("*", cpe.getEdition());
        assertEquals("*", cpe.getSwEdition());
        assertEquals("ts", cpe.getTargetSw());
        assertEquals("th", cpe.getTargetHw());
        assertEquals("*", cpe.getOther());

        edition = "~~sw~~~";
        CpeParser.unpackEdition(edition, cb);
        cpe = cb.build();
        assertEquals("*", cpe.getEdition());
        assertEquals("sw", cpe.getSwEdition());
        assertEquals("*", cpe.getTargetSw());
        assertEquals("*", cpe.getTargetHw());
        assertEquals("*", cpe.getOther());

        edition = "~~~~th~";
        CpeParser.unpackEdition(edition, cb);
        cpe = cb.build();
        assertEquals("*", cpe.getEdition());
        assertEquals("*", cpe.getSwEdition());
        assertEquals("*", cpe.getTargetSw());
        assertEquals("th", cpe.getTargetHw());
        assertEquals("*", cpe.getOther());

        edition = "~~~~~";
        CpeParser.unpackEdition(edition, cb);
        cpe = cb.build();
        assertEquals("*", cpe.getEdition());
        assertEquals("*", cpe.getSwEdition());
        assertEquals("*", cpe.getTargetSw());
        assertEquals("*", cpe.getTargetHw());
        assertEquals("*", cpe.getOther());

        edition = "~~~chrome~~";
        CpeParser.unpackEdition(edition, cb);
        cpe = cb.build();
        assertEquals("*", cpe.getEdition());
        assertEquals("*", cpe.getSwEdition());
        assertEquals("chrome", cpe.getTargetSw());
        assertEquals("*", cpe.getTargetHw());
        assertEquals("*", cpe.getOther());
    }

    /**
     * Test the parsing and binding cycles to ensure everything stays the same.
     *
     * @throws CpeParsingException thrown if there is a parsing error
     */
    @Test
    public void testParseBindUnbindCycle() throws Exception {
        exception = ExpectedException.none();

        String initial = "cpe:2.3:a:embarcadero:embarcadero_c\\+\\+builder_xe6:20.0.15596.9843:*:*:en:*:*:*:other";
        String cpeString = initial;
        for (int x = 0; x < 10; x++) {
            Cpe cpe = CpeParser.parse(cpeString);
            cpeString = cpe.toCpe22Uri();
            cpe = CpeParser.parse(cpeString);
            cpeString = cpe.toCpe23FS();
        }
        Assert.assertEquals(initial, cpeString);
    }

    @Test
    public void testInvalidCpeNull() throws Exception {
        exception.expect(CpeParsingException.class);
        CpeParser.parse(null);
    }

    @Test
    public void testInvalidCpeEmpty() throws Exception {
        exception.expect(CpeParsingException.class);
        CpeParser.parse("");
    }

    @Test
    public void testInvalidCpeInvalid() throws Exception {
        exception.expect(CpeParsingException.class);
        CpeParser.parse("invalid");
    }

    @Test
    public void testInvalidCpe22Empty() throws Exception {
        exception.expect(CpeParsingException.class);
        CpeParser.parse22("");
    }

    @Test
    public void testInvalidCpe23Empty() throws Exception {
        exception.expect(CpeParsingException.class);
        CpeParser.parse23("");
    }

    @Test
    public void testInvalidCpe22Null() throws Exception {
        exception.expect(CpeParsingException.class);
        CpeParser.parse22(null);
    }

    @Test
    public void testInvalidCpe23Null() throws Exception {
        exception.expect(CpeParsingException.class);
        CpeParser.parse23(null);
    }

    @Test
    public void testInvalidCpe22Invalid() throws Exception {
        exception.expect(CpeParsingException.class);
        CpeParser.parse22("invalid");
    }

    @Test
    public void testInvalidCpe22Invalid2() throws Exception {
        exception.expect(CpeParsingException.class);
        CpeParser.parse22("1:2:3:4:5:6:7:8:9:10");
    }

    @Test
    public void testInvalidCpe23Invalid() throws Exception {
        exception.expect(CpeParsingException.class);
        CpeParser.parse23("invalid");
    }

    @Test
    public void testInvalidCpe22InvalidPart() throws Exception {
        exception.expect(CpeParsingException.class);
        CpeParser.parse22("cpe:/t:vendor:product:1.0");
    }

    @Test
    public void testInvalidCpe22InvalidPart2() throws Exception {
        exception.expect(CpeParsingException.class);
        CpeParser.parse22("cpe:/ab:vendor:product:1.0");
    }

    @Test
    public void testInvalidCpe23InvalidPart() throws Exception {
        exception.expect(CpeParsingException.class);
        CpeParser.parse23("cpe:2.3:t:vendor:product:1.0:*:*:*:*:*:*:*");
    }

    @Test
    public void testInvalidCpePart() throws Exception {
        exception.expect(CpeParsingException.class);
        CpeParser.parse("cpe:/t:vendor:product:1.0");
    }

    @Test
    public void testInvalidCpe23TooManyParts() throws Exception {
        exception.expect(CpeParsingException.class);
        CpeParser.parse23("cpe:2.3:a:vendor:product:1.0:*:*:*:*:*:*:*:*");
    }

    @Test
    public void testInvalidCpe23TooFewParts() throws Exception {
        exception.expect(CpeParsingException.class);
        CpeParser.parse23("cpe:2.3:a:vendor:product:1.0:*:*:*:*:*:*");
    }

    @Test
    public void testInvalidCpe22Part() throws Exception {
        exception.expect(CpeParsingException.class);
        CpeParser.parse22("cpe:/t:vendor:product:1.0");
    }
}
