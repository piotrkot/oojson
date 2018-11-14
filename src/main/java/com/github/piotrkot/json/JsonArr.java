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

import java.io.Reader;
import java.util.Arrays;
import java.util.Iterator;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonValue;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;

/**
 * JSON array.
 *
 * @since 1.0
 */
public final class JsonArr implements JsonVal {
    /**
     * Elements of JSON array.
     */
    private final Iterable<JsonVal> elems;

    /**
     * Ctor.
     * @param base JSON array from API.
     */
    public JsonArr(final JsonArray base) {
        this(base.iterator());
    }

    /**
     * Ctor.
     * @param reader Reader.
     */
    public JsonArr(final Reader reader) {
        this(Json.createReader(reader).readArray());
    }

    /**
     * Ctor.
     * @param base JSON array from API.
     * @param elem Element of json array.
     */
    public JsonArr(final JsonArray base, final JsonVal elem) {
        this(base, new IterableOf<>(elem));
    }

    /**
     * Ctor.
     * @param base JSON array from API.
     * @param elems Elements of json array.
     */
    public JsonArr(final JsonArray base, final Iterable<JsonVal> elems) {
        this(
            new Joined<>(
                new Mapped<>(
                    value -> new ValueFound(value).asValue(),
                    new IterableOf<>(base.iterator())
                ),
                elems
            )
        );
    }

    /**
     * Ctor.
     * @param elems Array elements.
     */
    public JsonArr(final Iterable<JsonVal> elems) {
        this.elems = elems;
    }

    /**
     * Ctor.
     * @param elems Array elements.
     */
    public JsonArr(final JsonVal... elems) {
        this(Arrays.asList(elems));
    }

    /**
     * Ctor.
     * @param elems Array elements.
     */
    private JsonArr(final Iterator<JsonValue> elems) {
        this(
            new Mapped<>(
                value -> new ValueFound(value).asValue(),
                new IterableOf<>(elems)
            )
        );
    }

    /**
     * JSON array elements.
     * @return All elements.
     */
    public Iterable<JsonVal> elements() {
        return this.elems;
    }

    @Override
    public JsonArray jsonValue() {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (final JsonVal elem : this.elems) {
            builder = builder.add(elem.jsonValue());
        }
        return builder.build();
    }

    @Override
    public Object value() {
        throw new UnsupportedOperationException("value");
    }
}
