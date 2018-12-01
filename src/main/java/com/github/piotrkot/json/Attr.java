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

import com.github.piotrkot.json.values.Vbool;
import com.github.piotrkot.json.values.Vnull;
import com.github.piotrkot.json.values.Vnum;
import com.github.piotrkot.json.values.Vstr;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Map;
import javax.json.JsonValue;

/**
 * JSON object attribute.
 *
 * @since 1.0
 */
public interface Attr {
    /**
     * Attribute name.
     * @return Name as String.
     */
    String name();

    /**
     * Attribute value.
     * @return JSON value.
     */
    JsonVal value();

    /**
     * String attribute.
     */
    final class Str implements Attr {
        /**
         * Key.
         */
        private final String key;

        /**
         * Value.
         */
        private final String val;

        /**
         * Ctor.
         * @param key Key.
         * @param val Value.
         */
        public Str(final String key, final String val) {
            this.key = key;
            this.val = val;
        }

        @Override
        public String name() {
            return this.key;
        }

        @Override
        public JsonVal<String> value() {
            return new Vstr(this.val);
        }
    }

    /**
     * Number attribute.
     */
    final class Num implements Attr {
        /**
         * Key.
         */
        private final String key;

        /**
         * Value.
         */
        private final Vnum val;

        /**
         * Ctor.
         * @param key Key.
         * @param val Value.
         */
        private Num(final String key, final Vnum val) {
            this.key = key;
            this.val = val;
        }

        /**
         * Ctor.
         * @param key Key.
         * @param val Value.
         */
        public Num(final String key, final int val) {
            this(key, new Vnum(val));
        }

        /**
         * Ctor.
         * @param key Key.
         * @param val Value.
         */
        public Num(final String key, final long val) {
            this(key, new Vnum(val));
        }

        /**
         * Ctor.
         * @param key Key.
         * @param val Value.
         */
        public Num(final String key, final double val) {
            this(key, new Vnum(val));
        }

        /**
         * Ctor.
         * @param key Key.
         * @param val Value.
         */
        public Num(final String key, final BigInteger val) {
            this(key, new Vnum(val));
        }

        /**
         * Ctor.
         * @param key Key.
         * @param val Value.
         */
        public Num(final String key, final BigDecimal val) {
            this(key, new Vnum(val));
        }

        @Override
        public String name() {
            return this.key;
        }

        @Override
        public JsonVal<Number> value() {
            return this.val;
        }
    }

    /**
     * Boolean attribute.
     */
    final class Bool implements Attr {
        /**
         * Key.
         */
        private final String key;

        /**
         * Value.
         */
        private final boolean val;

        /**
         * Ctor.
         * @param key Key.
         * @param val Value.
         */
        public Bool(final String key, final boolean val) {
            this.key = key;
            this.val = val;
        }

        @Override
        public String name() {
            return this.key;
        }

        @Override
        public JsonVal<Boolean> value() {
            return new Vbool(this.val);
        }
    }

    /**
     * Null attribute.
     */
    final class Null implements Attr {
        /**
         * Key.
         */
        private final String key;

        /**
         * Ctor.
         * @param key Key.
         */
        public Null(final String key) {
            this.key = key;
        }

        @Override
        public String name() {
            return this.key;
        }

        @Override
        public JsonVal value() {
            return new Vnull();
        }
    }

    /**
     * Array attribute.
     */
    final class Arr implements Attr {
        /**
         * Key.
         */
        private final String key;

        /**
         * Value.
         */
        private final JsonArr val;

        /**
         * Ctor.
         * @param key Key.
         * @param val Value.
         */
        public Arr(final String key, final JsonArr val) {
            this.key = key;
            this.val = val;
        }

        @Override
        public String name() {
            return this.key;
        }

        @Override
        public JsonVal<Collection<JsonVal>> value() {
            return this.val;
        }
    }

    /**
     * Object attribute.
     */
    final class Obj implements Attr {
        /**
         * Key.
         */
        private final String key;

        /**
         * Value.
         */
        private final JsonObj val;

        /**
         * Ctor.
         * @param key Key.
         * @param val Value.
         */
        public Obj(final String key, final JsonObj val) {
            this.key = key;
            this.val = val;
        }

        @Override
        public String name() {
            return this.key;
        }

        @Override
        public JsonVal<Map<String, JsonVal>> value() {
            return this.val;
        }
    }

    /**
     * JSON value attribute.
     */
    final class Json implements Attr {
        /**
         * Key.
         */
        private final String key;

        /**
         * Value.
         */
        private final JsonValue val;

        /**
         * Ctor.
         * @param key Key.
         * @param val Value.
         */
        public Json(final String key, final JsonValue val) {
            this.key = key;
            this.val = val;
        }

        @Override
        public String name() {
            return this.key;
        }

        @Override
        public JsonVal value() {
            return new ValueFound(this.val).asValue();
        }
    }

    /**
     * JSON value attribute.
     */
    final class JsonV implements Attr {
        /**
         * Key.
         */
        private final String key;

        /**
         * Value.
         */
        private final JsonVal val;

        /**
         * Ctor.
         * @param key Key.
         * @param val Value.
         */
        public JsonV(final String key, final JsonVal val) {
            this.key = key;
            this.val = val;
        }

        @Override
        public String name() {
            return this.key;
        }

        @Override
        public JsonVal value() {
            return this.val;
        }
    }
}
