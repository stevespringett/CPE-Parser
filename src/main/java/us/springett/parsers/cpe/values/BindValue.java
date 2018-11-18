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
package us.springett.parsers.cpe.values;

/**
 * The enumerated values that can be used when constructing a CPE value.
 *
 * @author Jeremy Long
 */
public enum BindValue {
    /**
     * Match ANY: '*'.
     */
    ANY("*"),
    /**
     * Not Application: '-'.
     */
    NA("-");

    /**
     * The abbreviation for the bind value.
     */
    private final String abbreviation;

    /**
     * Constructs a new bind value.
     *
     * @param abbreviation the abbreviation for the bind value
     */
    BindValue(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    /**
     * Gets the component abbreviation for the bind value.
     *
     * @return the component abbreviation for the bind value
     */
    public String getAbbreviation() {
        return abbreviation;
    }
}
