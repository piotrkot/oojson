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
import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonArray;
import org.cactoos.iterable.Filtered;
import org.cactoos.iterable.Mapped;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test for JSON array.
 *
 * @since 1.0
 * @checkstyle ClassDataAbstractionCoupling (2 lines)
 */
public final class JsonArrTest {
    /**
     * Should create JSON array.
     * @throws Exception When fails.
     */
    @Test
    public void shouldCreateArr() throws Exception {
        MatcherAssert.assertThat(
            new JsonArr(
                new Vbool(true),
                new Vstr("a"),
                new Vnum(1),
                new JsonArr(),
                new JsonObj()
            ).jsonValue().toString(),
            Matchers.is("[true,\"a\",1,[],{}]")
        );
    }

    /**
     * Should create JSON array from string.
     * @throws Exception When fails.
     */
    @Test
    public void shouldCreateArrString() throws Exception {
        final String array = "[false,\"x\",1,[],{}]";
        MatcherAssert.assertThat(
            new JsonArr(new StringReader(array)).jsonValue().toString(),
            Matchers.is(array)
        );
    }

    /**
     * Should create JSON array from JSON API.
     * @throws Exception When fails.
     */
    @Test
    public void shouldCreateArrApiCopy() throws Exception {
        MatcherAssert.assertThat(
            new JsonArr(JsonArray.EMPTY_JSON_ARRAY).jsonValue().toString(),
            Matchers.is("[]")
        );
    }

    /**
     * Should create JSON array from JSON API.
     * @throws Exception When fails.
     */
    @Test
    public void shouldCreateArrCopy() throws Exception {
        MatcherAssert.assertThat(
            new JsonArr(Json.createArrayBuilder().add(0).build(), new Vnum(1))
                .jsonValue().toString(),
            Matchers.is("[0,1]")
        );
    }

    /**
     * Should transfer JSON array.
     * All boolean positive values transfer to strings.
     * <pre>[true,false] => ["true"]</pre>
     * @throws Exception When fails.
     */
    @Test
    public void shouldTransferArr() throws Exception {
        MatcherAssert.assertThat(
            new JsonArr(
                new Mapped<>(
                    elem -> new Vstr(Boolean.toString(elem)),
                    new Filtered<>(
                        elem -> elem,
                        new Mapped<>(
                            elem -> (Boolean) elem.value(),
                            new JsonArr(
                                new Vbool(false),
                                new Vbool(true)
                            ).elements()
                        )
                    )
                )
            ).jsonValue().toString(),
            Matchers.is("[\"true\"]")
        );
    }
}
