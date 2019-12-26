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

import us.springett.parsers.cpe.values.Part;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.mitre.cpe.common.WellFormedName;
import org.mitre.cpe.matching.CPENameMatcher;
import us.springett.parsers.cpe.values.LogicalValue;

/**
 *
 * @author Jeremy Long
 */
public class CpeIT {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Test of matches method, of class Cpe.
     *
     * @throws java.lang.Exception thrown if there is an error
     */
    @Test
    public void testMatches() throws Exception {

        CPENameMatcher referenceMatcher = new CPENameMatcher();
        org.mitre.cpe.common.LogicalValue any = new org.mitre.cpe.common.LogicalValue("ANY");
        org.mitre.cpe.common.LogicalValue na = new org.mitre.cpe.common.LogicalValue("NA");

        WellFormedName target = new WellFormedName("a", "owasp", "dependency\\-check", "4\\.0\\.0", any, any, any, any, any, any, any);
        WellFormedName source = new WellFormedName("a", "owasp", "dependency\\-check", "4\\.0\\.0", any, any, any, any, any, any, any);
        boolean expResult = referenceMatcher.isEqual(source, target) || referenceMatcher.isSuperset(source, target);

        CpeBuilder builder = new CpeBuilder();
        Cpe cpe = builder.part(Part.APPLICATION).vendor("owasp").product("dependency-check").version("4.0.0").build();
        Cpe instance = builder.part(Part.APPLICATION).vendor("owasp").product("dependency-check").version("4.0.0").build();
        boolean result = instance.matches(cpe);
        assertEquals(expResult, result);

        target = new WellFormedName("a", "owasp", "dependency\\-check", "4\\.0\\.1", any, any, any, any, any, any, any);
        source = new WellFormedName("a", "owasp", "dependency\\-check", "4\\.0\\.0", any, any, any, any, any, any, any);
        expResult = referenceMatcher.isEqual(source, target) || referenceMatcher.isSuperset(source, target);

        cpe = builder.part(Part.APPLICATION).vendor("owasp").product("dependency-check").version("4.0.1").build();
        instance = builder.part(Part.APPLICATION).vendor("owasp").product("dependency-check").version("4.0.0").build();
        result = instance.matches(cpe);
        assertEquals(expResult, result);

        target = new WellFormedName("a", "owasp", "dependency\\-check", "4\\.0\\.0", any, any, any, any, any, any, any);
        source = new WellFormedName("a", "owasp", any, "4\\.0\\.0", any, any, any, any, any, any, any);
        expResult = referenceMatcher.isEqual(source, target) || referenceMatcher.isSuperset(source, target);

        cpe = builder.part(Part.APPLICATION).vendor("owasp").product("dependency-check").version("4.0.0").build();
        instance = builder.part(Part.APPLICATION).vendor("owasp").product(LogicalValue.ANY).version("4.0.0").build();
        result = instance.matches(cpe);
        assertEquals(expResult, result);
        
        target = new WellFormedName("a", "owasp", any, "4\\.0\\.0", any, any, any, any, any, any, any);
        source = new WellFormedName("a", "owasp", "dependency\\-check", "4\\.0\\.0", any, any, any, any, any, any, any);
        expResult = referenceMatcher.isEqual(source, target) 
                || referenceMatcher.isSubset(source, target)
                || referenceMatcher.isSuperset(source, target);

        cpe = builder.part(Part.APPLICATION).vendor("owasp").product(LogicalValue.ANY).version("4.0.0").build();
        instance = builder.part(Part.APPLICATION).vendor("owasp").product("dependency-check").version("4.0.0").build();
        result = instance.matches(cpe);
        assertEquals(expResult, result);
        
        target = new WellFormedName("a", "owasp", "dependency\\-check", "4\\.0\\.0", any, any, any, any, any, any, any);
        source = new WellFormedName("a", "owasp", na, "4\\.0\\.0", any, any, any, any, any, any, any);
        expResult = referenceMatcher.isEqual(source, target) || referenceMatcher.isSuperset(source, target);

        cpe = builder.part(Part.APPLICATION).vendor("owasp").product("dependency-check").version("4.0.0").build();
        instance = builder.part(Part.APPLICATION).vendor("owasp").product(LogicalValue.NA).version("4.0.0").build();
        result = instance.matches(cpe);
        assertEquals(expResult, result);

        target = new WellFormedName("a", "owasp", na, "4\\.0\\.0", any, any, any, any, any, any, any);
        source = new WellFormedName("a", "owasp", "dependency\\-check", "4\\.0\\.0", any, any, any, any, any, any, any);
        expResult = referenceMatcher.isEqual(source, target) || referenceMatcher.isSuperset(source, target);

        cpe = builder.part(Part.APPLICATION).vendor("owasp").product(LogicalValue.NA).version("4.0.0").build();
        instance = builder.part(Part.APPLICATION).vendor("owasp").product("dependency-check").version("4.0.0").build();
        result = instance.matches(cpe);
        assertEquals(expResult, result);

        target = new WellFormedName("a", "owasp", "dependency\\-check", "4\\.0\\.0", any, any, any, any, any, any, any);
        source = new WellFormedName("a", "owasp", "*check", "4\\.0\\.0", any, any, any, any, any, any, any);
        expResult = referenceMatcher.isEqual(source, target) || referenceMatcher.isSuperset(source, target);

        cpe = builder.part(Part.APPLICATION).vendor("owasp").product("dependency-check").version("4.0.0").build();
        instance = builder.part(Part.APPLICATION).vendor("owasp").wfProduct("*check").version("4.0.0").build();
        result = instance.matches(cpe);
        assertEquals(expResult, result);

        target = new WellFormedName("a", "owasp", "dependency", "4\\.0\\.0", any, any, any, any, any, any, any);
        source = new WellFormedName("a", "owasp", "*check", "4\\.0\\.0", any, any, any, any, any, any, any);
        expResult = referenceMatcher.isEqual(source, target) || referenceMatcher.isSuperset(source, target);

        cpe = builder.part(Part.APPLICATION).vendor("owasp").product("dependency").version("4.0.0").build();
        instance = builder.part(Part.APPLICATION).vendor("owasp").wfProduct("*check").version("4.0.0").build();
        result = instance.matches(cpe);
        assertEquals(expResult, result);
    }

    /**
     * Test of matchedBy method, of class Cpe.
     *
     * @throws java.lang.Exception thrown if there is an error
     */
    @Test
    public void testMatchedBy() throws Exception {

        CpeBuilder builder = new CpeBuilder();
        Cpe target = builder.part(Part.APPLICATION).vendor("owasp").product("dependency-check").version("4.0.0").build();
        Cpe instance = builder.part(Part.APPLICATION).vendor("owasp").product("dependency-check").version("4.0.0").build();
        boolean expResult = true;
        boolean result = instance.matchedBy(target);
        assertEquals(expResult, result);

        target = builder.part(Part.APPLICATION).vendor("owasp").product("dependency-check").version("4.0.1").build();
        instance = builder.part(Part.APPLICATION).vendor("owasp").product("dependency-check").version("4.0.0").build();
        expResult = false;
        result = instance.matchedBy(target);
        assertEquals(expResult, result);
    }
}
