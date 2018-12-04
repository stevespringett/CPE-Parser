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

import java.text.ParseException;
import org.junit.Assert;
import org.junit.Test;
import org.mitre.cpe.common.LogicalValue;
import org.mitre.cpe.common.WellFormedName;
import org.mitre.cpe.naming.CPENameUnbinder;
import us.springett.parsers.cpe.exceptions.CpeParsingException;
import us.springett.parsers.cpe.util.Convert;

public class CpeParserIT {


    /**
     * Test the Parse method.
     *
     * @throws Exception thrown if there is a parsing error
     */
    @Test
    public void testParse() throws Exception {
        compareReferenceImpl("cpe:/a:pocoproject:poco_c%2b%2b_libraries:1.4.5");
        compareReferenceImpl("cpe:2.3:a:pocoproject:poco_c\\+\\+_libraries:1.4.5:*:*:*:*:*:*:*");
        compareReferenceImpl("cpe:/a:vendor:prod%7duct:1.0.0");
        compareReferenceImpl("cpe:/a:adobe:flash_player:::~~~chrome~~");
        compareReferenceImpl("cpe:2.3:a:adobe:flash_player:*:*:*:*:*:chrome:*:*");
        compareReferenceImpl("cpe:2.3:a:some\\:vendor:c\\+\\+:*:*:*:*:*:chrome:*:*");
    }

    private void compareReferenceImpl(String cpeString) throws ParseException, CpeParsingException {
        Cpe cpe = CpeParser.parse(cpeString);
        CPENameUnbinder cpeUnbinder = new CPENameUnbinder();
        WellFormedName wfn;
        if (cpeString.startsWith("cpe:/")) {
            wfn = cpeUnbinder.unbindURI(cpeString);
        } else {
            wfn = cpeUnbinder.unbindFS(cpeString);
        }
        
        Assert.assertEquals(fromWFN(wfn.get("part")), cpe.getPart().getAbbreviation());
        Assert.assertEquals(fromWFN(wfn.get("vendor")), cpe.getVendor());
        Assert.assertEquals(fromWFN(wfn.get("product")), cpe.getProduct());
        Assert.assertEquals(fromWFN(wfn.get("version")), cpe.getVersion());
        Assert.assertEquals(fromWFN(wfn.get("update")), cpe.getUpdate());
        Assert.assertEquals(fromWFN(wfn.get("edition")), cpe.getEdition());
        Assert.assertEquals(fromWFN(wfn.get("language")), cpe.getLanguage());
        Assert.assertEquals(fromWFN(wfn.get("sw_edition")), cpe.getSwEdition());
        Assert.assertEquals(fromWFN(wfn.get("target_sw")), cpe.getTargetSw());
        Assert.assertEquals(fromWFN(wfn.get("target_hw")), cpe.getTargetHw());
        Assert.assertEquals(fromWFN(wfn.get("other")), cpe.getOther());
        Assert.assertEquals(fromWFN(wfn.get("language")), cpe.getLanguage());
    }

    public String fromWFN(Object wfn) {
        String result;
        if (wfn instanceof LogicalValue) {
            LogicalValue v = (LogicalValue) wfn;
            if (v.isANY()) {
                return "*";
            }
            return "-";
        } else if (wfn instanceof String) {
            result = Convert.fromWellFormed((String) wfn);
        } else {
            result = Convert.fromWellFormed(wfn.toString());
        }
        return result;
    }

    @Test
    public void testReferenceImpl() throws ParseException {
        //the following two CPE strings are identical and are from CVE-2014-0350
        String cpeString22 = "cpe:/a:pocoproject:poco_c%2b%2b_libraries:1.4.5";
        String cpeString23 = "cpe:2.3:a:pocoproject:poco_c\\+\\+_libraries:1.4.5:*:*:*:*:*:*:*";

        CPENameUnbinder cpeUnbinder = new CPENameUnbinder();
        WellFormedName wfn22 = cpeUnbinder.unbindURI(cpeString22);
        WellFormedName wfn23 = cpeUnbinder.unbindFS(cpeString23);

        Assert.assertEquals(wfn22.get("product"), wfn23.get("product"));
    }
}
