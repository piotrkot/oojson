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
import java.math.BigDecimal;
import java.math.BigInteger;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test for number value.
 *
 * @since 1.0
 */
public final class VnumTest {
    /**
     * Should create int value.
     * @throws Exception When fails.
     */
    @Test
    public void shouldCreateIntValue() throws Exception {
        MatcherAssert.assertThat(
            new Vnum(1).value().intValue(), Matchers.is(1)
        );
    }

    /**
     * Should create double value.
     * @throws Exception When fails.
     * @checkstyle MagicNumber (5 lines)
     */
    @Test
    public void shouldCreateDoubleValue() throws Exception {
        MatcherAssert.assertThat(
            new Vnum(0.124d).value().doubleValue(), Matchers.is(0.124)
        );
    }

    /**
     * Should create big int value.
     * @throws Exception When fails.
     * @checkstyle MagicNumber (6 lines)
     */
    @Test
    public void shouldCreateBigIntValue() throws Exception {
        MatcherAssert.assertThat(
            new Vnum(new BigInteger("12")).value().intValue(),
            Matchers.is(12)
        );
    }

    /**
     * Should create big decimal value.
     * @throws Exception When fails.
     * @checkstyle MagicNumber (6 lines)
     */
    @Test
    public void shouldCreateBigDecValue() throws Exception {
        MatcherAssert.assertThat(
            new Vnum(new BigDecimal("1.2")).value().doubleValue(),
            Matchers.is(1.2)
        );
    }

    /**
     * Should create JSON object with number value.
     * @throws Exception When fails.
     */
    @Test
    public void shouldCreateObjNum() throws Exception {
        MatcherAssert.assertThat(
            new JsonObj(
                new JsonObj.Attr("min", new Vnum(Integer.MIN_VALUE))
            ).jsonValue().toString(),
            Matchers.is("{\"min\":-2147483648}")
        );
    }

    /**
     * Should create JSON array with number value.
     * @throws Exception When fails.
     */
    @Test
    public void shouldCreateArrStr() throws Exception {
        // @checkstyle MagicNumber (5 lines)
        MatcherAssert.assertThat(
            new JsonArr(
                new Vnum(Long.MIN_VALUE),
                new Vnum(Long.MAX_VALUE),
                new Vnum(1d / 3)
            ).jsonValue().toString(),
            Matchers.is(
                "[-9223372036854775808,9223372036854775807,0.3333333333333333]"
            )
        );
    }
}
