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
package us.springett.parsers.cpe.exceptions;

/**
 * Exception used when encoding/decoding CPE values.
 *
 * @author Jeremy Long
 */
public class CpeEncodingException extends Exception {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detailed message
     */
    public CpeEncodingException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     *
     * @param message the detailed message
     * @param cause the cause of the exception
     */
    public CpeEncodingException(String message, Exception cause) {
        super(message, cause);
    }

}
