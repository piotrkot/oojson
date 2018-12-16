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
import java.util.LinkedHashMap;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonObject;
import org.cactoos.collection.CollectionOf;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.cactoos.map.MapEnvelope;

/**
 * JSON object.
 *
 * @since 1.0
 * @checkstyle ClassDataAbstractionCoupling (2 lines)
 */
public final class JsonObj extends MapEnvelope<String, Object> implements
    JsonVal<Map<String, ?>> {
    /**
     * Ctor.
     * @param base JSON object from API.
     */
    public JsonObj(final JsonObject base) {
        this(
            new Mapped<>(
                entry -> new Attr<>(
                    entry.getKey(), new ObjectFound(entry.getValue()).asObject()
                ),
                base.entrySet()
            )
        );
    }

    /**
     * Ctor.
     * @param reader Reader.
     */
    public JsonObj(final Reader reader) {
        this(Json.createReader(reader).readObject());
    }

    /**
     * Ctor.
     * @param input Input stream.
     */
    public JsonObj(final InputStream input) {
        this(Json.createReader(input).readObject());
    }

    /**
     * Ctor.
     * @param base JSON object from API.
     * @param attributes Object attributes.
     */
    public JsonObj(final JsonObject base, final Iterable<Attr<?>> attributes) {
        this(
            new Joined<>(
                new Mapped<>(
                    entry -> new Attr<>(
                        entry.getKey(),
                        new ObjectFound(entry.getValue()).asObject()
                    ),
                    base.entrySet()
                ),
                attributes
            )
        );
    }

    /**
     * Ctor.
     * @param base JSON object from API.
     * @param attributes Object attributes.
     */
    public JsonObj(final JsonObject base, final Attr<?>... attributes) {
        this(base, Arrays.asList(attributes));
    }

    /**
     * Ctor.
     * @param attributes Object attributes.
     */
    public JsonObj(final Collection<Attr<?>> attributes) {
        super(() -> JsonObj.asMap(attributes));
    }

    /**
     * Ctor.
     * @param attributes Object attributes.
     */
    public JsonObj(final Attr<?>... attributes) {
        this(Arrays.asList(attributes));
    }

    /**
     * Ctor.
     * @param attributes Object attributes.
     */
    public JsonObj(final Iterable<Attr<?>> attributes) {
        this(new CollectionOf<>(attributes));
    }

    /**
     * JSON object attributes.
     * @return All attributes.
     * @checkstyle NonStaticMethod (2 lines)
     */
    public Iterable<Attr<?>> attributes() {
        return new Mapped<>(
            entry -> new Attr<>(entry.getKey(), entry.getValue()),
            super.entrySet()
        );
    }

    /**
     * Checks if attribute is present.
     * @param name Attribute name.
     * @return True if attribute exists, false otherwise.
     */
    public boolean contains(final String name) {
        return this.containsKey(name);
    }

    /**
     * Gets attribute value for given name.
     * @param name Attribute name.
     * @param <T> Type of return value.
     * @return JSON value.
     * @throws JsonException When parameter is not found.
     */
    public <T> T get(final String name) throws JsonException {
        if (this.containsKey(name)) {
            return (T) super.get(name);
        }
        throw new JsonException(
            String.format("Attribute name \"%s\" not found", name)
        );
    }

    /**
     * Gets attribute value for given name or default.
     * @param name Attribute name.
     * @param def Default value.
     * @param <T> Type of return value.
     * @return JSON value or default if not found.
     */
    public <T> T get(final String name, final T def) {
        final T value;
        if (this.containsKey(name)) {
            value = (T) super.get(name);
        } else {
            value = def;
        }
        return value;
    }

    @Override
    public JsonObject jsonValue() {
        return new JsonValueFound(this).asJsonValue().asJsonObject();
    }

    @Override
    public Map<String, ?> value() {
        return this;
    }

    /**
     * Create a map of object attributes since Cactoos doesn't support
     *  LinkedHashMap.
     * @param attrs Object attributes.
     * @return New object attributes as map ordered by inserted entries.
     */
    private static Map<String, Object> asMap(final Iterable<Attr<?>> attrs) {
        final Map<String, Object> map = new LinkedHashMap<>();
        for (final Attr<?> attr : attrs) {
            map.put(attr.name(), attr.value());
        }
        return map;
    }
}
