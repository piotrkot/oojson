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

import com.github.piotrkot.json.JsonArr;
import com.github.piotrkot.json.JsonObj;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test for string value.
 *
 * @since 1.0
 */
public final class VstringTest {
    /**
     * Should create string value.
     * @throws Exception When fails.
     */
    @Test
    public void shouldCreateStrValue() throws Exception {
        final String text = "text";
        MatcherAssert.assertThat(
            new Vstr(text).value(), Matchers.is(text)
        );
    }

    /**
     * Should create JSON object with string value.
     * @throws Exception When fails.
     */
    @Test
    public void shouldCreateObjStr() throws Exception {
        MatcherAssert.assertThat(
            new JsonObj(new JsonObj.Attr("test", new Vstr("")))
                .jsonValue().toString(),
            Matchers.is("{\"test\":\"\"}")
        );
    }

    /**
     * Should create JSON array with string value.
     * @throws Exception When fails.
     */
    @Test
    public void shouldCreateArrStr() throws Exception {
        MatcherAssert.assertThat(
            new JsonArr(new Vstr("one"), new Vstr("two"))
                .jsonValue().toString(),
            Matchers.is("[\"one\",\"two\"]")
        );
    }
}
