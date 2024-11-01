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

import jakarta.json.JsonNumber;
import jakarta.json.JsonString;
import jakarta.json.JsonValue;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import org.cactoos.list.ListOf;
import org.cactoos.map.MapOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test for values found.
 *
 * @since 1.0
 */
@SuppressWarnings("PMD.TooManyMethods")
final class JsonValueFoundTest {
    /**
     * Should find JSON array.
     * @throws Exception When fails.
     */
    @Test
    void shouldFindArr() throws Exception {
        MatcherAssert.assertThat(
            "Json value from list is an array",
            new JsonValueFound(new ListOf<>()).asJsonValue().toString(),
            Matchers.is("[]")
        );
    }

    /**
     * Should find JSON object.
     * @throws Exception When fails.
     */
    @Test
    void shouldFindObj() throws Exception {
        MatcherAssert.assertThat(
            "Json value from map is an object",
            new JsonValueFound(new MapOf<>()).asJsonValue().toString(),
            Matchers.is("{}")
        );
    }

    /**
     * Should find JSON string.
     * @throws Exception When fails.
     */
    @Test
    void shouldFindStr() throws Exception {
        MatcherAssert.assertThat(
            "Json value from string is a string",
            new JsonValueFound("").asJsonValue(),
            Matchers.instanceOf(JsonString.class)
        );
    }

    /**
     * Should find JSON number from int.
     * @throws Exception When fails.
     */
    @Test
    void shouldFindInt() throws Exception {
        MatcherAssert.assertThat(
            "Json value from number is a number",
            new JsonValueFound(0).asJsonValue(),
            Matchers.instanceOf(JsonNumber.class)
        );
    }

    /**
     * Should find JSON number from long.
     * @throws Exception When fails.
     */
    @Test
    void shouldFindLong() throws Exception {
        MatcherAssert.assertThat(
            "Json value from long number is a number",
            new JsonValueFound(1L).asJsonValue(),
            Matchers.instanceOf(JsonNumber.class)
        );
    }

    /**
     * Should find JSON number from double.
     * @throws Exception When fails.
     */
    @Test
    void shouldFindDouble() throws Exception {
        MatcherAssert.assertThat(
            // @checkstyle MagicNumber (2 line)
            "Json value from decimal is a number",
            new JsonValueFound(.1).asJsonValue(),
            Matchers.instanceOf(JsonNumber.class)
        );
    }

    /**
     * Should find JSON number from big integer.
     * @throws Exception When fails.
     */
    @Test
    void shouldFindBigInteger() throws Exception {
        MatcherAssert.assertThat(
            "Json value from big integer is a number",
            new JsonValueFound(BigInteger.ONE).asJsonValue(),
            Matchers.instanceOf(JsonNumber.class)
        );
    }

    /**
     * Should find JSON number from big decimal.
     * @throws Exception When fails.
     */
    @Test
    void shouldFindBigDecimal() throws Exception {
        MatcherAssert.assertThat(
            "Json value from big decimal is a number",
            new JsonValueFound(BigDecimal.TEN).asJsonValue(),
            Matchers.instanceOf(JsonNumber.class)
        );
    }

    /**
     * Should find JSON false boolean.
     * @throws Exception When fails.
     */
    @Test
    void shouldFindBoolF() throws Exception {
        MatcherAssert.assertThat(
            "Json value from false is false",
            new JsonValueFound(false).asJsonValue(),
            Matchers.is(JsonValue.FALSE)
        );
    }

    /**
     * Should find JSON true boolean.
     * @throws Exception When fails.
     */
    @Test
    void shouldFindBoolT() throws Exception {
        MatcherAssert.assertThat(
            "Json value from true is true",
            new JsonValueFound(true).asJsonValue(),
            Matchers.is(JsonValue.TRUE)
        );
    }

    /**
     * Should find JSON null.
     * @throws Exception When fails.
     */
    @Test
    void shouldFindNull() throws Exception {
        MatcherAssert.assertThat(
            "Json value from null is null",
            new JsonValueFound(null).asJsonValue(),
            Matchers.is(JsonValue.NULL)
        );
    }

    /**
     * Should not find JSON date.
     * @throws Exception When fails.
     */
    @Test
    void shouldNotFindDate() throws Exception {
        final UnsupportedOperationException thrown = Assertions.assertThrows(
            UnsupportedOperationException.class,
            () -> new JsonValueFound(new Date()).asJsonValue()
        );
        MatcherAssert.assertThat(
            "Json value from date should not be supported",
            thrown.getMessage(),
            Matchers.is("\"class java.util.Date\" not supported")
        );
    }
}
