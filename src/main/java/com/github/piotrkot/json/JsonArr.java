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

import java.io.InputStream;
import java.io.Reader;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import org.cactoos.iterable.Mapped;
import org.cactoos.list.ListEnvelope;
import org.cactoos.list.ListOf;

/**
 * JSON array.
 *
 * @param <T> Type of array elements.
 * @since 1.0
 */
public final class JsonArr<T> extends ListEnvelope<T> implements
    JsonVal<Collection<T>> {
    /**
     * Ctor.
     * @param base JSON array from API.
     */
    public JsonArr(final JsonArray base) {
        this(
            new Mapped<>(
                elem -> (T) new ObjectFound(elem).asObject(),
                base
            )
        );
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
     * @param input Input stream.
     */
    public JsonArr(final InputStream input) {
        this(Json.createReader(input).readArray());
    }

    /**
     * Ctor.
     * @param elems Array elements.
     */
    @SafeVarargs
    public JsonArr(final T... elems) {
        this(Arrays.asList(elems));
    }

    /**
     * Ctor.
     * @param elems Array elements.
     */
    public JsonArr(final Iterable<T> elems) {
        super(new ListOf<>(elems));
    }

    @Override
    public JsonArray jsonValue() {
        return new JsonValueFound(this).asJsonValue().asJsonArray();
    }

    @Override
    public List<T> value() {
        return this;
    }
}
