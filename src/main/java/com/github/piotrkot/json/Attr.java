/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2018-2021 piotrkot
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

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * JSON object attribute.
 *
 * @param <T> Attribute type.
 * @since 1.0
 */
@EqualsAndHashCode
@ToString
public final class Attr<T> {
    /**
     * Attribute key.
     */
    private final String key;

    /**
     * Object for key.
     */
    private final T obj;

    /**
     * Ctor.
     * @param key Attribute key.
     * @param obj Object for key.
     */
    public Attr(final String key, final T obj) {
        this.key = key;
        this.obj = obj;
    }

    /**
     * Attribute name.
     * @return Name as String.
     */
    public String name() {
        return this.key;
    }

    /**
     * Attribute value.
     * @return Object value.
     */
    public T value() {
        return this.obj;
    }
}
