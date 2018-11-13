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
package com.github.piotrkot.json.values;

import com.github.piotrkot.json.JsonVal;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.json.JsonNumber;

/**
 * Number value.
 *
 * @since 1.0
 */
public final class Vnum implements JsonVal<Number> {
    /**
     * Json number.
     */
    private final JsonNumber val;

    /**
     * Ctor.
     * @param val Value for number.
     */
    public Vnum(final JsonNumber val) {
        this.val = val;
    }

    /**
     * Ctor.
     * @param val Value for number.
     */
    public Vnum(final int val) {
        this(new IntJson(val));
    }

    /**
     * Ctor.
     * @param val Value for number.
     */
    public Vnum(final long val) {
        this(new LongJson(val));
    }

    /**
     * Ctor.
     * @param val Value for number.
     */
    public Vnum(final double val) {
        this(new BigDecimalJson(val));
    }

    /**
     * Ctor.
     * @param val Value for number.
     */
    public Vnum(final BigInteger val) {
        this(new BigDecimalJson(val));
    }

    /**
     * Ctor.
     * @param val Value for number.
     */
    public Vnum(final BigDecimal val) {
        this(new BigDecimalJson(val));
    }

    @Override
    public JsonNumber jsonValue() {
        return this.val;
    }

    @Override
    public Number value() {
        return this.val.bigDecimalValue();
    }

    /**
     * Json number for API.
     */
    private static final class IntJson implements JsonNumber {
        /**
         * Number value.
         */
        private final int number;

        /**
         * Ctor.
         * @param number Number value.
         */
        IntJson(final int number) {
            this.number = number;
        }

        @Override
        public ValueType getValueType() {
            return ValueType.NUMBER;
        }

        @Override
        public boolean isIntegral() {
            return true;
        }

        @Override
        public int intValue() {
            return this.number;
        }

        @Override
        public int intValueExact() {
            return this.number;
        }

        @Override
        public long longValue() {
            return this.number;
        }

        @Override
        public long longValueExact() {
            return this.number;
        }

        @Override
        public BigInteger bigIntegerValue() {
            return new BigDecimal(this.number).toBigInteger();
        }

        @Override
        public BigInteger bigIntegerValueExact() {
            return new BigDecimal(this.number).toBigIntegerExact();
        }

        @Override
        public double doubleValue() {
            return this.number;
        }

        @Override
        public BigDecimal bigDecimalValue() {
            return new BigDecimal(this.number);
        }

        @Override
        public String toString() {
            return String.valueOf(this.number);
        }
    }

    /**
     * Json number for API.
     */
    private static final class LongJson implements JsonNumber {
        /**
         * Number value.
         */
        private final long number;

        /**
         * Ctor.
         * @param number Number value.
         */
        LongJson(final long number) {
            this.number = number;
        }

        @Override
        public ValueType getValueType() {
            return ValueType.NUMBER;
        }

        @Override
        public boolean isIntegral() {
            return true;
        }

        @Override
        public int intValue() {
            return (int) this.number;
        }

        @Override
        public int intValueExact() {
            return Math.toIntExact(this.number);
        }

        @Override
        public long longValue() {
            return this.number;
        }

        @Override
        public long longValueExact() {
            return this.number;
        }

        @Override
        public BigInteger bigIntegerValue() {
            return new BigDecimal(this.number).toBigInteger();
        }

        @Override
        public BigInteger bigIntegerValueExact() {
            return new BigDecimal(this.number).toBigIntegerExact();
        }

        @Override
        public double doubleValue() {
            return this.number;
        }

        @Override
        public BigDecimal bigDecimalValue() {
            return new BigDecimal(this.number);
        }

        @Override
        public String toString() {
            return String.valueOf(this.number);
        }
    }

    /**
     * Json number for API.
     */
    private static final class BigDecimalJson implements JsonNumber {
        /**
         * Number value.
         */
        private final BigDecimal number;

        /**
         * Ctor.
         * @param number Number value.
         */
        BigDecimalJson(final BigDecimal number) {
            this.number = number;
        }

        /**
         * Ctor.
         * @param number Number value.
         */
        BigDecimalJson(final double number) {
            this(BigDecimal.valueOf(number));
        }

        /**
         * Ctor.
         * @param number Number value.
         */
        BigDecimalJson(final BigInteger number) {
            this(new BigDecimal(number));
        }

        @Override
        public ValueType getValueType() {
            return ValueType.NUMBER;
        }

        @Override
        public boolean isIntegral() {
            return this.number.scale() == 0;
        }

        @Override
        public int intValue() {
            return this.number.intValue();
        }

        @Override
        public int intValueExact() {
            return this.number.intValueExact();
        }

        @Override
        public long longValue() {
            return this.number.longValue();
        }

        @Override
        public long longValueExact() {
            return this.number.longValueExact();
        }

        @Override
        public BigInteger bigIntegerValue() {
            return this.number.toBigInteger();
        }

        @Override
        public BigInteger bigIntegerValueExact() {
            return this.number.toBigIntegerExact();
        }

        @Override
        public double doubleValue() {
            return this.number.doubleValue();
        }

        @Override
        public BigDecimal bigDecimalValue() {
            return this.number;
        }

        @Override
        public String toString() {
            return this.number.toString();
        }
    }
}
