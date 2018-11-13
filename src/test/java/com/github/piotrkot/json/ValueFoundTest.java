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
import com.github.piotrkot.json.values.Vnum;
import com.github.piotrkot.json.values.Vstr;
import javax.json.Json;
import javax.json.JsonValue;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test for values found.
 *
 * @since 1.0
 */
public final class ValueFoundTest {
    /**
     * Should find Json array.
     * @throws Exception When fails.
     */
    @Test
    public void shouldFindArr() throws Exception {
        MatcherAssert.assertThat(
            new ValueFound(Json.createArrayBuilder().build()).asValue(),
            Matchers.instanceOf(JsonArr.class)
        );
    }

    /**
     * Should find Json object.
     * @throws Exception When fails.
     */
    @Test
    public void shouldFindObj() throws Exception {
        MatcherAssert.assertThat(
            new ValueFound(Json.createObjectBuilder().build()).asValue(),
            Matchers.instanceOf(JsonObj.class)
        );
    }

    /**
     * Should find Json string.
     * @throws Exception When fails.
     */
    @Test
    public void shouldFindStr() throws Exception {
        MatcherAssert.assertThat(
            new ValueFound(Json.createValue("")).asValue(),
            Matchers.instanceOf(Vstr.class)
        );
    }

    /**
     * Should find Json number.
     * @throws Exception When fails.
     */
    @Test
    public void shouldFindNum() throws Exception {
        MatcherAssert.assertThat(
            new ValueFound(Json.createValue(0)).asValue(),
            Matchers.instanceOf(Vnum.class)
        );
    }

    /**
     * Should find Json boolean.
     * @throws Exception When fails.
     */
    @Test
    public void shouldFindBool() throws Exception {
        MatcherAssert.assertThat(
            new ValueFound(JsonValue.FALSE).asValue(),
            Matchers.instanceOf(Vbool.class)
        );
        MatcherAssert.assertThat(
            new ValueFound(JsonValue.TRUE).asValue(),
            Matchers.instanceOf(Vbool.class)
        );
    }

    /**
     * Should not find Json null. It's not supported on purpose.
     * @throws Exception When fails.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void shouldNotFindNull() throws Exception {
        new ValueFound(JsonValue.NULL).asValue();
    }
}
