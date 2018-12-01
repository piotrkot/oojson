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

import com.github.piotrkot.json.Attr;
import com.github.piotrkot.json.JsonArr;
import com.github.piotrkot.json.JsonObj;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test for null value.
 *
 * @since 1.0
 */
public final class VnullTest {
    /**
     * Should return value for null.
     * @throws Exception When fails.
     */
    @Test
    public void shouldReturnValue() throws Exception {
        MatcherAssert.assertThat(
            new Vnull().value(), Matchers.nullValue()
        );
    }

    /**
     * Should create JSON object with null value.
     * @throws Exception When fails.
     */
    @Test
    public void shouldCreateObjNull() throws Exception {
        MatcherAssert.assertThat(
            new JsonObj(new Attr.Null("test"))
                .jsonValue().toString(),
            Matchers.is("{\"test\":null}")
        );
    }

    /**
     * Should create JSON array with null value.
     * @throws Exception When fails.
     */
    @Test
    public void shouldCreateArrBool() throws Exception {
        MatcherAssert.assertThat(
            new JsonArr(new Vnull()).jsonValue().toString(),
            Matchers.is("[null]")
        );
    }
}
