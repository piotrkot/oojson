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

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import javax.json.JsonArray;
import org.cactoos.iterable.Filtered;
import org.cactoos.iterable.Mapped;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test for JSON array.
 *
 * @since 1.0
 * @checkstyle ClassDataAbstractionCoupling (2 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class JsonArrTest {
    /**
     * Should create JSON array.
     * @throws Exception When fails.
     */
    @Test
    public void shouldCreateArr() throws Exception {
        MatcherAssert.assertThat(
            new JsonArr<>(
                true,
                "a",
                1,
                new JsonArr<>(),
                new JsonObj()
            ).jsonValue().toString(),
            Matchers.is("[true,\"a\",1,[],{}]")
        );
    }

    /**
     * Should create safe JSON boolean array.
     * @throws Exception When fails.
     */
    @Test
    public void shouldCreateBoolArr() throws Exception {
        MatcherAssert.assertThat(
            new JsonArr<>(
                true, false, true
            ).jsonValue().toString(),
            Matchers.is("[true,false,true]")
        );
    }

    /**
     * Should create JSON string array.
     * @throws Exception When fails.
     */
    @Test
    public void shouldCreateStrArr() throws Exception {
        MatcherAssert.assertThat(
            new JsonArr<>(
                "a", "ba", "acd"
            ).jsonValue().toString(),
            Matchers.is("[\"a\",\"ba\",\"acd\"]")
        );
    }

    /**
     * Should create JSON number array.
     * @throws Exception When fails.
     */
    @Test
    public void shouldCreateNumArr() throws Exception {
        MatcherAssert.assertThat(
            new JsonArr<>(
                // @checkstyle MagicNumberCheck (1 line)
                1, 1.4, -1.3, 3L, BigDecimal.TEN
            ).jsonValue().toString(),
            Matchers.is("[1,1.4,-1.3,3,10]")
        );
    }

    /**
     * Should create JSON array from string.
     * @throws Exception When fails.
     */
    @Test
    public void shouldCreateArrString() throws Exception {
        final String array = "[false,\"x\",1,[],{}]";
        MatcherAssert.assertThat(
            new JsonArr(new StringReader(array)).jsonValue().toString(),
            Matchers.is(array)
        );
    }

    /**
     * Should create JSON array from input stream.
     * @throws Exception When fails.
     */
    @Test
    public void shouldCreateArrStream() throws Exception {
        final String array = "[false,\"y\",2,[\"Z\"],{\"x\":0}]";
        MatcherAssert.assertThat(
            new JsonArr(
                new ByteArrayInputStream(
                    array.getBytes(StandardCharsets.UTF_8)
                )
            ).jsonValue().toString(),
            Matchers.is(array)
        );
    }

    /**
     * Should create JSON array from JSON API.
     * @throws Exception When fails.
     */
    @Test
    public void shouldCreateArrApiCopy() throws Exception {
        MatcherAssert.assertThat(
            new JsonArr(JsonArray.EMPTY_JSON_ARRAY).jsonValue().toString(),
            Matchers.is("[]")
        );
    }

    /**
     * Should return value of array.
     * @throws Exception When fails.
     */
    @Test
    public void shouldReturnValue() throws Exception {
        MatcherAssert.assertThat(
            new JsonArr<>().value(),
            Matchers.instanceOf(Collection.class)
        );
    }

    /**
     * Should transfer JSON array.
     * All boolean positive values transfer to strings.
     * <pre>[true,false] => ["true"]</pre>
     * @throws Exception When fails.
     */
    @Test
    public void shouldTransferArr() throws Exception {
        MatcherAssert.assertThat(
            new JsonArr<>(
                new Mapped<>(
                    elem -> Boolean.toString(elem),
                    new Filtered<>(
                        elem -> elem,
                        new JsonArr<>(
                            false, true
                        )
                    )
                )
            ).jsonValue().toString(),
            Matchers.is("[\"true\"]")
        );
    }

    /**
     * Should create object based on iterable and not iterator.
     * @throws Exception When fails.
     */
    @Test
    public void shouldIterateTwice() throws Exception {
        final String array = "[1,2,3]";
        final JsonArr<Integer> json = new JsonArr<>(new StringReader(array));
        MatcherAssert.assertThat(
            json.jsonValue().toString(), Matchers.is(array)
        );
        MatcherAssert.assertThat(
            json.jsonValue().toString(), Matchers.is(array)
        );
    }
}
