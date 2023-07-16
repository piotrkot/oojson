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
 * Tests for deletion of attribute.
 *
 * @since 1.0
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class FitAttrDelTest {
    /**
     * Should not delete attribute when missing.
     * @throws Exception When fails.
     */
    @Test
    void shouldNotDelete() throws Exception {
        final JsonObj obj = new JsonObj(
            new Attr<>("val", "foo")
        );
        MatcherAssert.assertThat(
            new FitAttrDel("test").make(obj).jsonValue().toString(),
            Matchers.is("{\"val\":\"foo\"}")
        );
    }

    /**
     * Should delete attribute.
     * @throws Exception When fails.
     */
    @Test
    void shouldDeleteAttribute() throws Exception {
        final JsonObj obj = new JsonObj(
            new Attr<>("type", "bar")
        );
        MatcherAssert.assertThat(
            new FitAttrDel("type").make(obj).jsonValue().toString(),
            Matchers.is("{}")
        );
    }
}
