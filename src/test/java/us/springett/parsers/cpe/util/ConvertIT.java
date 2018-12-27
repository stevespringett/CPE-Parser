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
package us.springett.parsers.cpe.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import us.springett.parsers.cpe.values.LogicalValue;
import us.springett.parsers.cpe.values.Part;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import us.springett.parsers.cpe.exceptions.CpeEncodingException;

/**
 *
 * @author Jeremy Long
 */
public class ConvertIT {

    private static String referenceToWellFormed(String value) {
        if (value == null) {
            return LogicalValue.ANY.getAbbreviation();
        }
        if (LogicalValue.ANY.getAbbreviation().equals(value)
                || LogicalValue.NA.getAbbreviation().equals(value)) {
            return value;
        }
        return value.replaceAll("([^0-9A-Za-z])", "\\\\$1");
    }

    @Test
    public void testPerformance() throws Exception {
        List<String> values = new ArrayList<String>();

        System.out.println("---------------------------------------------------");
        System.out.println("Performance test building data set");

        for (int i = 0; i < 50000; i++) {
            values.add("aaaaaaaaaaaaaaaaaa:5.3." + i);
        }

        values.forEach(
                (cpe) -> {
                    String res = Convert.toWellFormed(cpe);
                    String ref = referenceToWellFormed(cpe);
                    assertTrue(res.equals(ref));
                }
        );

        System.out.println(
                "Performance test starting reference");
        long startTime = System.currentTimeMillis();

        values.forEach(
                (cpe) -> {
                    referenceToWellFormed(cpe);
                }
        );
        long endTime = System.currentTimeMillis();
        long reference = endTime - startTime;

        System.out.println(
                "Performance test finished reference");

        System.out.println(
                "Performance test starting");
        startTime = System.currentTimeMillis();

        values.forEach(
                (cpe) -> {
                    Convert.toWellFormed(cpe);
                }
        );
        endTime = System.currentTimeMillis();

        System.out.println(
                "Performance test finished");
        System.out.println(
                "---------------------------------------------------");
        long duration = endTime - startTime;

        System.out.println(
                "Num messages = " + values.size());
        System.out.println(
                "Duration = " + duration + "; referenceDuration = " + reference);
        System.out.println(
                "Duration / cpe = " + ((double) duration
                / values.size()));
        System.out.println(
                "---------------------------------------------------");
    }
}
