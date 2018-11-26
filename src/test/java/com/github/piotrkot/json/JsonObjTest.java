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
import java.util.Collection;
import java.util.Map;
import javax.json.Json;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Test for JSON object.
 *
 * @since 1.0
 * @checkstyle ClassDataAbstractionCoupling (2 lines)
 */
public final class JsonObjTest {
    /**
     * Expected exception.
     */
    @Rule
    public final ExpectedException exp = ExpectedException.none();

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
     * Should return value of object.
     * @throws Exception When fails.
     */
    @Test
    public void shouldReturnValue() throws Exception {
        MatcherAssert.assertThat(
            new JsonObj().value(),
            Matchers.instanceOf(Map.class)
        );
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
     * Should not fetch JSON object attribute.
     * @throws Exception When fails.
     */
    @Test
    public void shouldNotGetObjAttr() throws Exception {
        this.exp.expect(
            Matchers.allOf(
                Matchers.isA(JsonException.class),
                Matchers.hasProperty(
                    "message",
                    Matchers.is("attribute name \"missing\" not found")
                )
            )
        );
        new JsonObj().get("missing");
    }

    /**
     * Should fetch JSON simple object default attribute.
     * @throws Exception When fails.
     */
    @Test
    public void shouldGetSimpleObjDefAttr() throws Exception {
        MatcherAssert.assertThat(
            new JsonObj().get("miss", new Vstr("X")).value(),
            Matchers.is("X")
        );
    }

    /**
     * Should fetch JSON complex object default attribute.
     * @throws Exception When fails.
     */
    @Test
    public void shouldGetComplexObjDefAttr() throws Exception {
        MatcherAssert.assertThat(
            new JsonObj().get("other", new JsonArr()).value(),
            Matchers.instanceOf(Collection.class)
        );
    }
}
