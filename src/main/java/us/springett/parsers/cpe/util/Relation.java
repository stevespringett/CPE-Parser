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
package us.springett.parsers.cpe.util;

/**
 * Possible set relations between source WFN and target WFN.
 *
 * @see <a href="https://nvlpubs.nist.gov/nistpubs/Legacy/IR/nistir7696.pdf">NIST CPE Name Matching Specification, Table 6-1</a>
 */
public enum Relation {

    /**
     * The source and target are mutually exclusive or DISJOINT.
     */
    DISJOINT,

    /**
     * The source and target are EQUAL.
     */
    EQUAL,

    /**
     * The source is a SUBSET of the target.
     */
    SUBSET,

    /**
     * The source is a SUPERSET of the target.
     */
    SUPERSET

}
