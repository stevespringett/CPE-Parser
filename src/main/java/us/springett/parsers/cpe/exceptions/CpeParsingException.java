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
package us.springett.parsers.cpe.exceptions;

/**
 * Exception used when parsing CPE strings.
 *
 * @author Steve Springett
 */
public class CpeParsingException extends Exception {

    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = -4624394628314058224L;

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detailed message
     */
    public CpeParsingException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     *
     * @param message the detailed message
     * @param cause the cause of the exception
     */
    public CpeParsingException(String message, Exception cause) {
        super(message, cause);
    }
}
