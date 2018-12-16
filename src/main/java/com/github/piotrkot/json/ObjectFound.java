/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2018 piotrkot
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.piotrkot.json;

import java.math.BigInteger;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;

/**
 * Object found.
 *
 * @since 1.0
 */
public final class ObjectFound {
    /**
     * JSON value.
     */
    private final JsonValue json;

    /**
     * Ctor.
     * @param json JSON value.
     */
    public ObjectFound(final JsonValue json) {
        this.json = json;
    }

    /**
     * Object found.
     * @return Object value.
     */
    public Object asObject() {
        final Object value;
        switch (this.json.getValueType()) {
            case OBJECT:
                value = new JsonObj((JsonObject) this.json).value();
                break;
            case STRING:
                value = ((JsonString) this.json).getString();
                break;
            case ARRAY:
                value = new JsonArr((JsonArray) this.json).value();
                break;
            case NUMBER:
                value = this.asNumber();
                break;
            case FALSE:
                value = false;
                break;
            case TRUE:
                value = true;
                break;
            case NULL:
                value = null;
                break;
            default:
                throw new UnsupportedOperationException(
                    "JSON type not supported"
                );
        }
        return value;
    }

    /**
     * Number found.
     * @return Number.
     */
    private Object asNumber() {
        final Object value;
        final JsonNumber number = (JsonNumber) this.json;
        if (number.isIntegral()) {
            if (number.intValue() == number.longValue()) {
                value = number.intValue();
            } else if (number.bigIntegerValueExact()
                .equals(BigInteger.valueOf(number.longValue()))) {
                value = number.longValue();
            } else {
                value = number.bigIntegerValueExact();
            }
        } else {
            if (number.bigDecimalValue().toPlainString()
                .equals(String.valueOf(number.doubleValue()))) {
                value = number.doubleValue();
            } else {
                value = number.bigDecimalValue();
            }
        }
        return value;
    }
}
