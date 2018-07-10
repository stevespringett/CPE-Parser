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

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CpeParser {

    private static final Pattern SCHEMA_22_OFFICIAL = Pattern.compile("[c][pP][eE]:/[AHOaho]?(:[A-Za-z0-9\\._\\-~%]*){0,6}");
    private static final Pattern SCHEMA_22 = Pattern.compile("[c][pP][eE]:/(?<part>[AHOaho])?(?::)(?<vendor>([A-Za-z0-9\\._\\-~%]*)){0,1}(?::){0,1}(?<product>([A-Za-z0-9\\._\\-~%]*)){0,1}(?::){0,1}(?<version>([A-Za-z0-9\\._\\-~%]*)){0,1}(?::){0,1}(?<update>([A-Za-z0-9\\._\\-~%]*)){0,1}(?::){0,1}(?<edition>([A-Za-z0-9\\._\\-~%]*)){0,1}(?::){0,1}(?<language>([A-Za-z0-9\\._\\-~%]*)){0,1}");

    private static final Pattern SCHEMA_23_OFFICIAL = Pattern.compile("cpe:2\\.3:[aho\\*\\-](:(((\\?*|\\*?)([a-zA-Z0-9\\-\\._]|(\\\\[\\\\\\*\\?!\"#$$%&'\\(\\)\\+,/:;<=>@\\[\\]\\^`\\{\\|}~]))+(\\?*|\\*?))|[\\*\\-])){5}(:(([a-zA-Z]{2,3}(-([a-zA-Z]{2}|[0-9]{3}))?)|[\\*\\-]))(:(((\\?*|\\*?)([a-zA-Z0-9\\-\\._]|(\\\\[\\\\\\*\\?!\"#$$%&'\\(\\)\\+,/:;<=>@\\[\\]\\^`\\{\\|}~]))+(\\?*|\\*?))|[\\*\\-])){4}");
    private static final Pattern SCHEMA_23 = Pattern.compile("cpe:2\\.3:(?<part>[aho\\*\\-])(?::)(?<vendor>(((\\?*|\\*?)([a-zA-Z0-9\\-\\._]|(\\\\[\\\\\\*\\?!\"#$$%&'\\(\\)\\+,\\/:;<=>@\\[\\]\\^`\\{\\|}~]))+(\\?*|\\*?))|[\\*\\-]))(?::)(?<product>(((\\?*|\\*?)([a-zA-Z0-9\\-\\._]|(\\\\[\\\\\\*\\?!\"#$$%&'\\(\\)\\+,\\/:;<=>@\\[\\]\\^`\\{\\|}~]))+(\\?*|\\*?))|[\\*\\-]))(?::)(?<version>(((\\?*|\\*?)([a-zA-Z0-9\\-\\._]|(\\\\[\\\\\\*\\?!\"#$$%&'\\(\\)\\+,\\/:;<=>@\\[\\]\\^`\\{\\|}~]))+(\\?*|\\*?))|[\\*\\-]))(?::)(?<update>(((\\?*|\\*?)([a-zA-Z0-9\\-\\._]|(\\\\[\\\\\\*\\?!\"#$$%&'\\(\\)\\+,\\/:;<=>@\\[\\]\\^`\\{\\|}~]))+(\\?*|\\*?))|[\\*\\-]))(?::)(?<edition>(((\\?*|\\*?)([a-zA-Z0-9\\-\\._]|(\\\\[\\\\\\*\\?!\"#$$%&'\\(\\)\\+,\\/:;<=>@\\[\\]\\^`\\{\\|}~]))+(\\?*|\\*?))|[\\*\\-]))(?::)(?<language>(((\\?*|\\*?)([a-zA-Z0-9\\-\\._]|(\\\\[\\\\\\*\\?!\"#$$%&'\\(\\)\\+,\\/:;<=>@\\[\\]\\^`\\{\\|}~]))+(\\?*|\\*?))|[\\*\\-]))(?::)(?<swEdition>(((\\?*|\\*?)([a-zA-Z0-9\\-\\._]|(\\\\[\\\\\\*\\?!\"#$$%&'\\(\\)\\+,\\/:;<=>@\\[\\]\\^`\\{\\|}~]))+(\\?*|\\*?))|[\\*\\-]))(?::)(?<targetSw>(((\\?*|\\*?)([a-zA-Z0-9\\-\\._]|(\\\\[\\\\\\*\\?!\"#$$%&'\\(\\)\\+,\\/:;<=>@\\[\\]\\^`\\{\\|}~]))+(\\?*|\\*?))|[\\*\\-]))(?::)(?<targetHw>(((\\?*|\\*?)([a-zA-Z0-9\\-\\._]|(\\\\[\\\\\\*\\?!\"#$$%&'\\(\\)\\+,\\/:;<=>@\\[\\]\\^`\\{\\|}~]))+(\\?*|\\*?))|[\\*\\-]))(?::)(?<other>(((\\?*|\\*?)([a-zA-Z0-9\\-\\._]|(\\\\[\\\\\\*\\?!\"#$$%&'\\(\\)\\+,\\/:;<=>@\\[\\]\\^`\\{\\|}~]))+(\\?*|\\*?))|[\\*\\-]))");

    private static final Map<String, Part> PARTS_INDEX = new HashMap<>(Part.values().length);
    static {
        for (Part part: Part.values()) {
            PARTS_INDEX.put(part.getAbbreviation(), part);
        }
    }

    private CpeParser() {}

    private static Part resolvePartByAbbreviation(String part) {
        return PARTS_INDEX.get(part);
    }

    public static Cpe parse(String cpeString) throws CpeParsingException {
        Matcher matcher22 = SCHEMA_22.matcher(cpeString);
        if (matcher22.matches()) {
            final Cpe cpe = new Cpe();
            cpe.setPart(resolvePartByAbbreviation(matcher22.group("part")));
            cpe.setVendor(trimToNull(matcher22.group("vendor")));
            cpe.setProduct(trimToNull(matcher22.group("product")));
            cpe.setVersion(trimToNull(matcher22.group("version")));
            cpe.setUpdate(trimToNull(matcher22.group("update")));
            cpe.setEdition(trimToNull(matcher22.group("edition")));
            cpe.setLanguage(trimToNull(matcher22.group("language")));
            return cpe;
        }

        Matcher matcher23 = SCHEMA_23.matcher(cpeString);
        if (matcher23.matches()) {
            final Cpe cpe = new Cpe();
            cpe.setPart(resolvePartByAbbreviation(matcher23.group("part")));
            cpe.setVendor(trimToNull(matcher23.group("vendor")));
            cpe.setProduct(trimToNull(matcher23.group("product")));
            cpe.setVersion(trimToNull(matcher23.group("version")));
            cpe.setUpdate(trimToNull(matcher23.group("update")));
            cpe.setEdition(trimToNull(matcher23.group("edition")));
            cpe.setLanguage(trimToNull(matcher23.group("language")));
            cpe.setSwEdition(trimToNull(matcher23.group("swEdition")));
            cpe.setTargetSw(trimToNull(matcher23.group("targetSw")));
            cpe.setTargetHw(trimToNull(matcher23.group("targetHw")));
            cpe.setOther(trimToNull(matcher23.group("other")));
            return cpe;
        }
        throw new CpeParsingException("The CPE string specified does not conform to the CPE 2.2 or 2.3 specification");
    }

    public static boolean isValid(String cpeString) {
        return isVersion22(cpeString) || isVersion23(cpeString);
    }

    public static boolean isVersion22(String cpeString) {
        Matcher matcher = SCHEMA_22_OFFICIAL.matcher(cpeString);
        return matcher.matches();
    }

    public static boolean isVersion23(String cpeString) {
        Matcher matcher = SCHEMA_23_OFFICIAL.matcher(cpeString);
        return matcher.matches();
    }

    private static String trimToNull(String input) {
        if (input == null) {
            return null;
        }
        String result = input.trim();
        return (result.equals("")) ? null : result;
    }
}
