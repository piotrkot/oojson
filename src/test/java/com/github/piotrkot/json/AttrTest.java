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

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Tests for attribute class.
 *
 * @since 1.0
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class AttrTest {
    /**
     * Should same objects be equal.
     * @throws Exception When fails.
     */
    @Test
    public void shouldSameObjectsBeEqual() throws Exception {
        MatcherAssert.assertThat(
            new Attr<>("owner", true).equals(
                new Attr<>("owner", true)
            ),
            Matchers.is(true)
        );
    }

    /**
     * Should same objects have the same hash code.
     * @throws Exception When fails.
     */
    @Test
    public void shouldSameObjectsHaveSameHash() throws Exception {
        MatcherAssert.assertThat(
            new Attr<>("valid", "").hashCode(),
            Matchers.is(
                new Attr<>("valid", "").hashCode()
            )
        );
    }

    /**
     * Should be toString method.
     * @throws Exception When fails.
     */
    @Test
    public void shouldBeToString() throws Exception {
        MatcherAssert.assertThat(
            new Attr<>(
                "object",
                new JsonObj(
                    new Attr<>("b", true),
                    new Attr<>("s", "a"),
                    new Attr<>("n", 0.0),
                    new Attr<>("a", new JsonArr<>()),
                    new Attr<>("o", new JsonObj())
                )
            ).toString(),
            Matchers.is("Attr(key=object, obj={b=true, s=a, n=0.0, a=[], o={}})")
        );
    }
}
