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
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test for Json object.
 *
 * @since 1.0
 * @checkstyle ClassDataAbstractionCoupling (2 lines)
 */
public final class JsonObjTest {
    /**
     * Should create Json object.
     * @throws Exception When fails.
     */
    @Test
    public void shouldCreateObj() throws Exception {
        MatcherAssert.assertThat(
            new JsonObj(
                new JsonObj.Attr("b", new Vbool(true)),
                new JsonObj.Attr("s", new Vstr("a")),
                new JsonObj.Attr("n", new Vnum(0)),
                new JsonObj.Attr("a", new JsonArr()),
                new JsonObj.Attr("o", new JsonObj())
            ).jsonValue().toString(),
            Matchers.is("{\"b\":true,\"s\":\"a\",\"n\":0,\"a\":[],\"o\":{}}")
        );
    }

    /**
     * Should transfer Json object.
     * Suffix "str" attribute with 'Z', and multiply "num" attribute by 2.
     * <pre>{"str":"A","num":1} => {"str":"AZ","num":2}</pre>
     * @throws Exception When fails.
     */
    @Test
    public void shouldTransferObj() throws Exception {
        MatcherAssert.assertThat(
            new JsonObj(
                new JsonObj(
                    new JsonObj.Attr("str", new Vstr("A")),
                    new JsonObj.Attr("num", new Vnum(1))
                ),
                new Change.Attr(
                    "st.", str -> new Vstr(((String) str.value()).concat("Z"))
                ),
                new Change.Attr(
                    ".*um",
                    num -> new Vnum(((Number) num.value()).intValue() * 2)
                )
            ).jsonValue().toString(),
            Matchers.is("{\"str\":\"AZ\",\"num\":2}")
        );
    }

    /**
     * Should transfer Json object by filling missing entries.
     * Fills "text" attribute with 'message' when missing.
     * <pre>{"s":\"ss\"} => {"s":\"ss\","text":"text"}</pre>
     * @throws Exception When fails.
     */
    @Test
    public void shouldFillMissingObj() throws Exception {
        MatcherAssert.assertThat(
            new JsonObj(
                new JsonObj(
                    new JsonObj.Attr("s", new Vstr("ss"))
                ),
                new Change.AttrMiss("text", new Vstr("message"))
            ).jsonValue().toString(),
            Matchers.is("{\"s\":\"ss\",\"text\":\"message\"}")
        );
    }

    /**
     * Should transfer Json object by deleting given attribute.
     * Deletes "del" attribute.
     * <pre>{"del":\"priv\","keep":\"pub\"} => {"keep":\"pub\"}</pre>
     * @throws Exception When fails.
     */
    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void shouldDelAttrObj() throws Exception {
        MatcherAssert.assertThat(
            new JsonObj(
                new JsonObj(
                    new JsonObj.Attr("del", new Vstr("priv")),
                    new JsonObj.Attr("keep", new Vstr("pub"))
                ),
                new Change.AttrDel("del")
            ).jsonValue().toString(),
            Matchers.is("{\"keep\":\"pub\"}")
        );
    }
}
