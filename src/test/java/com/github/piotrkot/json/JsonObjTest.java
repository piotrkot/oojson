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
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import javax.json.Json;
import org.cactoos.iterable.IterableOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test for JSON object.
 *
 * @since 1.0
 * @checkstyle ClassDataAbstractionCoupling (2 lines)
 */
public final class JsonObjTest {
    /**
     * Should create JSON object.
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
     * Should create JSON object from string.
     * @throws Exception When fails.
     */
    @Test
    public void shouldCreateObjString() throws Exception {
        final String obj =
            "{\"b\":false,\"s\":\"a\",\"n\":0,\"a\":[],\"o\":{}}";
        MatcherAssert.assertThat(
            new JsonObj(new StringReader(obj)).jsonValue().toString(),
            Matchers.is(obj)
        );
    }

    /**
     * Should create JSON object from input stream.
     * @throws Exception When fails.
     */
    @Test
    public void shouldCreateObjStream() throws Exception {
        final String obj =
            "{\"f\":false,\"y\":\"\",\"z\":[\"Z\"],\"x\":{\"X\":0}}";
        MatcherAssert.assertThat(
            new JsonObj(
                new ByteArrayInputStream(
                    obj.getBytes(StandardCharsets.UTF_8)
                )
            ).jsonValue().toString(),
            Matchers.is(obj)
        );
    }

    /**
     * Should create JSON object from string with API.
     * @throws Exception When fails.
     */
    @Test
    public void shouldCreateObjStringApi() throws Exception {
        MatcherAssert.assertThat(
            new JsonObj(
                Json.createObjectBuilder().add("name", "Mark").build(),
                new JsonObj.Attr("id", new Vnum(1))
            ).jsonValue().toString(),
            Matchers.is("{\"name\":\"Mark\",\"id\":1}")
        );
    }

    /**
     * Should not return value of object.
     * @throws Exception When fails.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void shouldNotReturnValue() throws Exception {
        new JsonObj().value();
    }

    /**
     * Should fetch JSON object attribute.
     * @throws Exception When fails.
     */
    @Test
    public void shouldGetObjAttr() throws Exception {
        final String empty = "empty";
        MatcherAssert.assertThat(
            new JsonObj(
                new JsonObj.Attr(empty, new Vstr(""))
            ).get(empty).value(),
            Matchers.is("")
        );
    }

    /**
     * Should fetch JSON object default attribute.
     * @throws Exception When fails.
     */
    @Test
    public void shouldGetObjDefAttr() throws Exception {
        MatcherAssert.assertThat(
            new JsonObj().getOrDefault("miss", new Vstr("X")).value(),
            Matchers.is("X")
        );
    }

    /**
     * Should transfer JSON object.
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
     * Should transfer JSON object by filling missing entries.
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
                new Change.AttrMiss("text", new Vstr("message")),
                new Change.AttrDel("none")
            ).jsonValue().toString(),
            Matchers.is("{\"s\":\"ss\",\"text\":\"message\"}")
        );
    }

    /**
     * Should transfer JSON object by deleting given attribute.
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
                new IterableOf<>(
                    new Change.AttrDel("del"),
                    new Change.AttrMiss("keep", new Vstr("xxx"))
                )
            ).jsonValue().toString(),
            Matchers.is("{\"keep\":\"pub\"}")
        );
    }
}