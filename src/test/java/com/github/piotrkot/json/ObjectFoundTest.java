/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2018-2020 piotrkot
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
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Tests for object found class.
 *
 * @since 1.0
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class ObjectFoundTest {
    /**
     * Expected exception.
     */
    @Rule
    public final ExpectedException exp = ExpectedException.none();

    /**
     * Should find Boolean false.
     * @throws Exception When fails.
     */
    @Test
    public void shouldFindBoolFalse() throws Exception {
        MatcherAssert.assertThat(
            new ObjectFound(JsonValue.FALSE).asObject(),
            Matchers.is(false)
        );
    }

    /**
     * Should find Boolean true.
     * @throws Exception When fails.
     */
    @Test
    public void shouldFindBoolTrue() throws Exception {
        MatcherAssert.assertThat(
            new ObjectFound(JsonValue.TRUE).asObject(),
            Matchers.is(true)
        );
    }

    /**
     * Should find String.
     * @throws Exception When fails.
     */
    @Test
    public void shouldFindString() throws Exception {
        MatcherAssert.assertThat(
            new ObjectFound(Json.createValue("")).asObject(),
            Matchers.is("")
        );
    }

    /**
     * Should find null.
     * @throws Exception When fails.
     */
    @Test
    public void shouldFindNull() throws Exception {
        MatcherAssert.assertThat(
            new ObjectFound(JsonValue.NULL).asObject(),
            Matchers.nullValue()
        );
    }

    /**
     * Should find array.
     * @throws Exception When fails.
     */
    @Test
    public void shouldFindArray() throws Exception {
        MatcherAssert.assertThat(
            new ObjectFound(JsonValue.EMPTY_JSON_ARRAY).asObject(),
            Matchers.instanceOf(Collection.class)
        );
    }

    /**
     * Should find object.
     * @throws Exception When fails.
     */
    @Test
    public void shouldFindObject() throws Exception {
        MatcherAssert.assertThat(
            new ObjectFound(JsonValue.EMPTY_JSON_OBJECT).asObject(),
            Matchers.instanceOf(Map.class)
        );
    }

    /**
     * Should find int.
     * @throws Exception When fails.
     */
    @Test
    public void shouldFindInt() throws Exception {
        MatcherAssert.assertThat(
            new ObjectFound(Json.createValue(0)).asObject(),
            Matchers.instanceOf(Integer.class)
        );
    }

    /**
     * Should find long.
     * @throws Exception When fails.
     */
    @Test
    public void shouldFindLong() throws Exception {
        MatcherAssert.assertThat(
            new ObjectFound(Json.createValue(Long.MAX_VALUE)).asObject(),
            Matchers.instanceOf(Long.class)
        );
    }

    /**
     * Should find double.
     * @throws Exception When fails.
     */
    @Test
    public void shouldFindDouble() throws Exception {
        MatcherAssert.assertThat(
            // @checkstyle MagicNumber (1 line)
            new ObjectFound(Json.createValue(0.3)).asObject(),
            Matchers.instanceOf(Double.class)
        );
    }

    /**
     * Should find big integer.
     * @throws Exception When fails.
     */
    @Test
    public void shouldFindBigInteger() throws Exception {
        MatcherAssert.assertThat(
            new ObjectFound(
                Json.createValue(new BigInteger("9223372036854775808"))
            ).asObject(),
            Matchers.instanceOf(BigInteger.class)
        );
    }

    /**
     * Should find big decimal.
     * @throws Exception When fails.
     */
    @Test
    public void shouldFindBigDecimal() throws Exception {
        MatcherAssert.assertThat(
            new ObjectFound(
                Json.createValue(new BigDecimal("9223372036854775808.1111"))
            ).asObject(),
            Matchers.instanceOf(BigDecimal.class)
        );
    }
}
