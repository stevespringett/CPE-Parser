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

import java.io.Serializable;

public class Cpe implements Serializable {

    private static final long serialVersionUID = 4558537216395895494L;

    private Part part;
    private String vendor;
    private String product;
    private String version;
    private String update;
    private String edition;
    private String language;
    private String swEdition; // Only available in CPE 2.3
    private String targetSw;  // Only available in CPE 2.3
    private String targetHw;  // Only available in CPE 2.3
    private String other;     // Only available in CPE 2.3

    public Cpe part(final Part part) {
        this.part = part;
        return this;
    }

    public Cpe vendor(final String vendor) {
        this.vendor = vendor;
        return this;
    }

    public Cpe product(final String product) {
        this.product = product;
        return this;
    }

    public Cpe version(final String version) {
        this.version = version;
        return this;
    }

    public Cpe update(final String update) {
        this.update = update;
        return this;
    }

    public Cpe edition(final String edition) {
        this.edition = edition;
        return this;
    }

    public Cpe language(final String language) {
        this.language = language;
        return this;
    }

    public Cpe swEdition(final String swEdition) {
        this.swEdition = swEdition;
        return this;
    }

    public Cpe targetSw(final String targetSw) {
        this.targetSw = targetSw;
        return this;
    }

    public Cpe targetHw(final String targetHw) {
        this.targetHw = targetHw;
        return this;
    }

    public Cpe other(final String other) {
        this.other = other;
        return this;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSwEdition() {
        return swEdition;
    }

    public void setSwEdition(String swEdition) {
        this.swEdition = swEdition;
    }

    public String getTargetSw() {
        return targetSw;
    }

    public void setTargetSw(String targetSw) {
        this.targetSw = targetSw;
    }

    public String getTargetHw() {
        return targetHw;
    }

    public void setTargetHw(String targetHw) {
        this.targetHw = targetHw;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

}
