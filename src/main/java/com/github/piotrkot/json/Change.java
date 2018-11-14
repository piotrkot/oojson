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

import java.util.Collection;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.cactoos.iterable.Filtered;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Joined;

/**
 * JSON change.
 *
 * @param <T> Type of object being changed.
 * @since 1.0
 */
public interface Change<T> {
    /**
     * Applies a change.
     * @param input Input json.
     * @return Output json.
     * @throws Exception When fails.
     */
    T apply(T input) throws Exception;

    /**
     * Chain of object changes.
     */
    final class Chain implements Change<JsonObj> {
        /**
         * Object changes.
         */
        private final Iterable<? extends Change<JsonObj>> changes;

        /**
         * Ctor.
         * @param changes Object changes.
         */
        public Chain(final Iterable<? extends Change<JsonObj>> changes) {
            this.changes = changes;
        }

        @Override
        public JsonObj apply(final JsonObj input) throws Exception {
            JsonObj temp = input;
            for (final Change<JsonObj> change : this.changes) {
                temp = change.apply(temp);
            }
            return temp;
        }
    }

    /**
     * Object attribute change.
     */
    final class Attr implements Change<JsonObj> {
        /**
         * Pattern for attribute name.
         */
        private final Pattern patt;

        /**
         * Change of JSON value for attribute.
         */
        private final Change<JsonVal> change;

        /**
         * Ctor.
         * @param patt Pattern for attribute name.
         * @param change Change of JSON value for attribute.
         */
        public Attr(final Pattern patt, final Change<JsonVal> change) {
            this.patt = patt;
            this.change = change;
        }

        /**
         * Ctor.
         * @param patt Pattern for attribute name.
         * @param change Change of JSON value for attribute.
         */
        public Attr(final String patt, final Change<JsonVal> change) {
            this(
                Pattern.compile(
                    patt, Pattern.CASE_INSENSITIVE | Pattern.DOTALL
                ),
                change
            );
        }

        @Override
        public JsonObj apply(final JsonObj input) throws Exception {
            final Collection<JsonObj.Attr> attrs = new LinkedList<>();
            for (final JsonObj.Attr attr : input.attributes()) {
                final Matcher matcher = this.patt.matcher(attr.name());
                if (matcher.matches()) {
                    attrs.add(
                        new JsonObj.Attr(
                            attr.name(), this.change.apply(attr.value())
                        )
                    );
                } else {
                    attrs.add(attr);
                }
            }
            return new JsonObj(attrs);
        }
    }

    /**
     * Object attribute add when it's missing.
     */
    final class AttrMiss implements Change<JsonObj> {
        /**
         * Attribute name.
         */
        private final String name;

        /**
         * New JSON value for attribute.
         */
        private final JsonVal value;

        /**
         * Ctor.
         * @param name Attribute name.
         * @param value New JSON value for attribute.
         */
        public AttrMiss(final String name, final JsonVal value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public JsonObj apply(final JsonObj input) throws Exception {
            final Iterable<JsonObj.Attr> attrs;
            if (input.contains(this.name)) {
                attrs = input.attributes();
            } else {
                attrs = new Joined<>(
                    input.attributes(),
                    new IterableOf<>(
                        new JsonObj.Attr(this.name, this.value)
                    )
                );
            }
            return new JsonObj(attrs);
        }
    }

    /**
     * Object attribute delete.
     */
    final class AttrDel implements Change<JsonObj> {
        /**
         * Attribute name.
         */
        private final String name;

        /**
         * Ctor.
         * @param name Attribute name.
         */
        public AttrDel(final String name) {
            this.name = name;
        }

        @Override
        public JsonObj apply(final JsonObj input) throws Exception {
            final Iterable<JsonObj.Attr> attrs;
            if (input.contains(this.name)) {
                attrs = new Filtered<>(
                    attr -> !attr.name().equals(this.name),
                    input.attributes()
                );
            } else {
                attrs = input.attributes();
            }
            return new JsonObj(attrs);
        }
    }
}
