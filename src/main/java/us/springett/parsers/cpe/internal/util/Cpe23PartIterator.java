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
package us.springett.parsers.cpe.internal.util;

import java.util.Iterator;
import java.util.NoSuchElementException;
import us.springett.parsers.cpe.exceptions.CpeParsingException;

/**
 * A utility that will tokenize and iterate over a CPE v2.3 string and return
 * the components sequentially.
 *
 * @author Jeremy Long
 */
public class Cpe23PartIterator implements Iterator<String> {

    /**
     * The CPE string that is being parsed.
     */
    private String cpe;
    /**
     * The position in the string that has been parsed.
     */
    private int pos;

    /**
     * Constructs a new CPE Formated String Iterator.
     *
     * @param cpe the CPE being tokenized
     * @throws CpeParsingException thrown if the CPE is invalid
     */
    public Cpe23PartIterator(String cpe) throws CpeParsingException {
        if (cpe == null || !cpe.startsWith("cpe:2.3:")) {
            throw new CpeParsingException("Invalid 2.3 CPE value: " + cpe);
        }
        this.cpe = cpe;
        pos = 8;
    }

    @Override
    public boolean hasNext() {
        return pos < cpe.length();
    }

    @Override
    public String next() {
        if (pos >= cpe.length()) {
            throw new NoSuchElementException("No remainging parts");
        }
        int end;
        for (end = pos; end < cpe.length(); end++) {
            if (cpe.charAt(end) == ':' && cpe.charAt(end - 1) != '\\') {
                break;
            }
        }
        String part = cpe.substring(pos, end);
        pos = end + 1;
        return part;
    }
}
