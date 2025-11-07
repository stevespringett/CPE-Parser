/*
 * Copyright 2018 jeremy.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package us.springett.parsers.cpe.util;

import ch.qos.logback.classic.LoggerContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Jeremy Long
 */
public class ValidateIT {

    private static final Pattern CPE_FS = Pattern.compile("cpe:2\\.3:[aho\\*\\-](:(((\\?*|\\*?)([a-zA-Z0-9\\-\\._]|(\\\\[\\\\\\*\\?!\"#$$%&'\\(\\)\\+,/:;<=>@\\[\\]\\^`\\{\\|}~]))+(\\?*|\\*?))|[\\*\\-])){5}(:(([a-zA-Z]{2,3}(-([a-zA-Z]{2}|[0-9]{3}))?)|[\\*\\-]))(:(((\\?*|\\*?)([a-zA-Z0-9\\-\\._]|(\\\\[\\\\\\*\\?!\"#$$%&'\\(\\)\\+,/:;<=>@\\[\\]\\^`\\{\\|}~]))+(\\?*|\\*?))|[\\*\\-])){4}");

    @BeforeEach
    public void setUp() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        context.stop();
    }

    private Status referenceValidate(String value) {
        Matcher m = CPE_FS.matcher(value);
        if (m.matches()) {
            return Status.VALID;
        }
        return Status.INVALID;
    }

    @Test
    public void testPerformance() {
        List<String> cpes = new ArrayList<>();

        System.out.println("---------------------------------------------------");
        System.out.println("Performance test building data set");

        for (int i = 0; i < 50000; i++) {
            switch (i % 3) {
                case 0:
                    cpes.add("cpe:2.3:a:misterpark:re\\:kyu:" + i + ":*:*:*:*:android:*:*:invalid");
                    break;
                case 1:
                    cpes.add("cpe:2.3:a:misterpark:re\\:kyu:" + i + ":*:*:*:*:and*roid:*:*");
                    break;
                default:
                    cpes.add("cpe:2.3:a:misterpark:re\\:kyu:" + i + ":*:*:*:*:android:*:*");
                    break;
            }
        }

        for (int i = 0; i < 10; i++) {
            if (Validate.formattedString(cpes.get(i)).isValid()
                    != referenceValidate(cpes.get(i)).isValid()) {
                throw new RuntimeException("failed reference validation");
            }
        }

        System.out.println("Performance test starting reference");
        long startTime = System.currentTimeMillis();
        cpes.forEach((cpe) -> {
            referenceValidate(cpe);
        });
        long endTime = System.currentTimeMillis();
        long reference = endTime - startTime;
        System.out.println("Performance test finished reference");

        System.out.println("Performance test starting");
        startTime = System.currentTimeMillis();
        cpes.forEach((cpe) -> {
            Validate.formattedString(cpe);
        });
        endTime = System.currentTimeMillis();
        System.out.println("Performance test finished");
        System.out.println("---------------------------------------------------");
        long duration = endTime - startTime;
        System.out.println("Num messages = " + cpes.size());
        System.out.println("Duration = " + duration + "; referenceDuration = " + reference);
        System.out.println("Duration / cpe = " + ((double) duration / cpes.size()));
        System.out.println("---------------------------------------------------");
    }
}
