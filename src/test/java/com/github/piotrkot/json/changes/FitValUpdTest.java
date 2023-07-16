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
package com.github.piotrkot.json.changes;

import com.github.piotrkot.json.Attr;
import com.github.piotrkot.json.JsonObj;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Tests for Update value.
 *
 * @since 1.0
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class FitValUpdTest {
    /**
     * Should not update value for missing attribute.
     * @throws Exception When fails.
     */
    @Test
    void shouldNotUpdateValue() throws Exception {
        final JsonObj obj = new JsonObj(
            new Attr<>("val", "foo")
        );
        MatcherAssert.assertThat(
            new FitValUpd("test", "hello").make(obj)
                .jsonValue().toString(),
            Matchers.is("{\"val\":\"foo\"}")
        );
    }

    /**
     * Should update value.
     * @throws Exception When fails.
     */
    @Test
    void shouldUpdateValue() throws Exception {
        final JsonObj obj = new JsonObj(
            new Attr<>("type", "bar")
        );
        MatcherAssert.assertThat(
            new FitValUpd("type", "zoo").make(obj)
                .jsonValue().toString(),
            Matchers.is("{\"type\":\"zoo\"}")
        );
    }

    /**
     * Should update value dynamically.
     * @throws Exception When fails.
     */
    @Test
    void shouldUpdateValueDyn() throws Exception {
        final JsonObj obj = new JsonObj(
            new Attr<>("num", 1)
        );
        MatcherAssert.assertThat(
            new FitValUpd(
                "num",
                (obj.<Integer>get("num")) * 2
            ).make(obj).jsonValue().toString(),
            Matchers.is("{\"num\":2}")
        );
    }
}
