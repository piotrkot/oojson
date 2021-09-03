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

import com.github.piotrkot.json.Fit;
import com.github.piotrkot.json.JsonObj;
import org.cactoos.iterable.Filtered;

/**
 * Make it fit by removing all attributes that have null values.
 * It removes only the direct attributes. It's NOT the deep search and remove.
 *
 * @since 1.0
 */
public final class FitNotNull implements Fit<JsonObj> {
    @Override
    public JsonObj make(final JsonObj input) throws Exception {
        return new JsonObj(
            new Filtered<>(
                attr -> attr.value() != null,
                input.attributes()
            )
        );
    }
}
