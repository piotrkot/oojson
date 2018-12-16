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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonValue;

/**
 * JSON Value found.
 *
 * @since 1.0
 */
public final class JsonValueFound {
    /**
     * Object.
     */
    private final Object obj;

    /**
     * Ctor.
     * @param obj Object.
     */
    public JsonValueFound(final Object obj) {
        this.obj = obj;
    }

    /**
     * Value found from API JSON value.
     * @return JSON value.
     * @checkstyle CyclomaticComplexity (2 lines)
     */
    @SuppressWarnings("PMD.CyclomaticComplexity")
    public JsonValue asJsonValue() {
        final JsonValue value;
        if (this.obj instanceof String) {
            value = Json.createValue((String) obj);
        } else if (this.obj instanceof Integer) {
            value = Json.createValue((Integer) this.obj);
        } else if (this.obj instanceof Double) {
            value = Json.createValue((Double) this.obj);
        } else if (this.obj instanceof Long) {
            value = Json.createValue((Long) this.obj);
        } else if (this.obj instanceof BigInteger) {
            value = Json.createValue((BigInteger) this.obj);
        } else if (this.obj instanceof BigDecimal) {
            value = Json.createValue((BigDecimal) this.obj);
        } else if (this.obj instanceof Boolean) {
            if ((Boolean) this.obj) {
                value = JsonValue.TRUE;
            } else {
                value = JsonValue.FALSE;
            }
        } else if (this.obj instanceof Collection<?>) {
            value = Json.createArrayBuilder((Collection<?>) this.obj).build();
        } else if (this.obj instanceof Map<?, ?>) {
            value = Json.createObjectBuilder((Map<String, Object>) this.obj)
                .build();
        } else if (this.obj == null) {
            value = JsonValue.NULL;
        } else {
            throw new UnsupportedOperationException(
                String.format("\"%s\" not supported", this.obj.getClass())
            );
        }
        return value;
    }
}
