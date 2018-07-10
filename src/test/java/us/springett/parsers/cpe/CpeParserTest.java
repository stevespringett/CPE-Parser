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

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CpeParserTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testValidCpe22() throws Exception {
        exception = ExpectedException.none();
        Cpe cpe = CpeParser.parse("cpe:/a:hiox_india:guest_book:4.0");
        Assert.assertEquals(Part.APPLICATION, cpe.getPart());
        Assert.assertEquals("hiox_india", cpe.getVendor());
        Assert.assertEquals("guest_book", cpe.getProduct());
        Assert.assertEquals("4.0", cpe.getVersion());
        Assert.assertNull(cpe.getUpdate());
        Assert.assertNull(cpe.getEdition());
        Assert.assertNull(cpe.getLanguage());

        cpe = CpeParser.parse("cpe:/a:adobe:flash_player:::~~~chrome~~");
        Assert.assertEquals(Part.APPLICATION, cpe.getPart());
        Assert.assertEquals("adobe", cpe.getVendor());
        Assert.assertEquals("flash_player", cpe.getProduct());
        Assert.assertNull(cpe.getVersion());
        Assert.assertNull(cpe.getUpdate());
        Assert.assertEquals("~~~chrome~~", cpe.getEdition());
        Assert.assertNull(cpe.getLanguage());
    }

    @Test
    public void testInvalidCpe22Part() throws Exception {
        exception.expect(CpeParsingException.class);
        CpeParser.parse("cpe:/t:vendor:product:1.0");
    }

    @Test
    public void testValidCpe23() throws Exception {
        exception = ExpectedException.none();
        Cpe cpe = CpeParser.parse("cpe:2.3:a:hiox_india:guest_book:4.0:*:*:*:*:*:*:*");
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
        Assert.assertEquals("*", cpe.getOther());

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

    @Test
    public void testInvalidCpe23Part() throws Exception {
        exception.expect(CpeParsingException.class);
        CpeParser.parse("cpe:2.3:t:vendor:product:1.0:*:*:*:*:*:*:*");
    }

}
