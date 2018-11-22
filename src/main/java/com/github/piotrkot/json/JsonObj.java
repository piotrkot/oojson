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
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;

/**
 * JSON object.
 *
 * @since 1.0
 */
public final class JsonObj implements JsonVal {
    /**
     * Attributes of JSON object.
     */
    private final Map<String, JsonVal> attrs;

    /**
     * Ctor.
     * @param attrs Object attributes.
     */
    public JsonObj(final Iterable<Attr> attrs) {
        this.attrs = JsonObj.asMap(attrs);
    }

    /**
     * Ctor.
     * @param attributes Object attributes.
     */
    public JsonObj(final Attr... attributes) {
        this(Arrays.asList(attributes));
    }

    /**
     * Ctor.
     * @param base JSON object from API.
     */
    public JsonObj(final JsonObject base) {
        this(base.entrySet());
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
     * @param attribute Attribute for json object.
     */
    public JsonObj(final JsonObject base, final Attr attribute) {
        this(base, new IterableOf<>(attribute));
    }

    /**
     * Ctor.
     * @param base JSON object from API.
     * @param attributes Attributes for json object.
     */
    public JsonObj(final JsonObject base, final Iterable<Attr> attributes) {
        this(
            new Joined<>(
                new Mapped<>(
                    entry -> new Attr(entry.getKey(), entry.getValue()),
                    base.entrySet()
                ),
                attributes
            )
        );
    }

    /**
     * Ctor.
     * @param origin JSON object origin.
     * @param change Change of JSON object.
     * @throws Exception When change fails.
     */
    public JsonObj(final JsonObj origin, final Change<JsonObj> change)
        throws Exception {
        this(change.apply(origin).attributes());
    }

    /**
     * Ctor.
     * @param origin JSON object origin.
     * @param changes Changes of JSON object attributes.
     * @throws Exception When change fails.
     */
    public JsonObj(final JsonObj origin,
        final Iterable<Change<JsonObj>> changes) throws Exception {
        this(origin, new Change.Chain(changes));
    }

    /**
     * Ctor.
     * @param origin JSON object origin.
     * @param changes Changes of JSON object attributes.
     * @throws Exception When change fails.
     */
    @SafeVarargs
    public JsonObj(final JsonObj origin, final Change<JsonObj>... changes)
        throws Exception {
        this(
            origin, new Change.Chain(new IterableOf<>(changes))
        );
    }

    /**
     * Ctor.
     * @param attributes Object attributes.
     */
    private JsonObj(final Collection<Map.Entry<String, JsonValue>> attributes) {
        this(
            new Mapped<>(
                entry -> new Attr(entry.getKey(), entry.getValue()),
                attributes
            )
        );
    }

    /**
     * JSON object attributes.
     * @return All attributes.
     */
    public Iterable<Attr> attributes() {
        return new Mapped<>(
            entry -> new Attr(entry.getKey(), entry.getValue()),
            this.attrs.entrySet()
        );
    }

    /**
     * Checks if attribute is present.
     * @param name Attribute name.
     * @return True if attribute exists, false otherwise.
     */
    public boolean contains(final String name) {
        return this.attrs.containsKey(name);
    }

    /**
     * Gets attribute value for given name.
     * @param name Attribute name.
     * @return JSON value.
     * @throws JsonException When parameter is not found.
     */
    public JsonVal get(final String name) throws JsonException {
        if (this.attrs.containsKey(name)) {
            return this.attrs.get(name);
        }
        throw new JsonException(
            String.format("attribute name \"%s\" not found", name)
        );
    }

    /**
     * Gets attribute value for given name or default.
     * @param name Attribute name.
     * @param def Default value.
     * @return JSON value or default if not found.
     */
    public JsonVal get(final String name, final JsonVal def) {
        return this.attrs.getOrDefault(name, def);
    }

    @Override
    public JsonObject jsonValue() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        for (final Map.Entry<String, JsonVal> attr : this.attrs.entrySet()) {
            builder = builder.add(attr.getKey(), attr.getValue().jsonValue());
        }
        return builder.build();
    }

    @Override
    public Object value() {
        throw new UnsupportedOperationException("value");
    }

    /**
     * Create a map of object attributes since Cactoos doesn't support
     *  LinkedHashMap.
     * @param attrs Object attributes.
     * @return New object attributes as map ordered by inserted entries.
     */
    private static Map<String, JsonVal> asMap(final Iterable<Attr> attrs) {
        final Map<String, JsonVal> map = new LinkedHashMap<>();
        for (final Attr attr : attrs) {
            map.put(attr.aname, attr.avalue);
        }
        return map;
    }

    /**
     * Attribute of JSON object.
     */
    public static class Attr {
        /**
         * Attribute name.
         */
        private final String aname;

        /**
         * Attribute value.
         */
        private final JsonVal avalue;

        /**
         * Ctor.
         * @param name Attribute name.
         * @param value Attribute value.
         */
        public Attr(final String name, final JsonVal value) {
            this.aname = name;
            this.avalue = value;
        }

        /**
         * Ctor.
         * @param name Attribute name.
         * @param value Attribute value.
         */
        private Attr(final String name, final JsonValue value) {
            this(name, new ValueFound(value).asValue());
        }

        /**
         * Attribute name.
         * @return String name.
         */
        public String name() {
            return this.aname;
        }

        /**
         * Attribute value.
         * @return Arbitrary JSON value.
         */
        public JsonVal value() {
            return this.avalue;
        }
    }
}
