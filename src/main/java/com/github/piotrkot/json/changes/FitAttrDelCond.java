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
package com.github.piotrkot.json.changes;

import com.github.piotrkot.json.Fit;
import com.github.piotrkot.json.JsonObj;
import java.util.function.Predicate;
import org.cactoos.iterable.Filtered;

/**
 * Make it fit by deleting attribute under given condition.
 * @param <T> Type of attribute.
 * @since 1.0
 */
public final class FitAttrDelCond<T> implements Fit<JsonObj> {
    /**
     * Attribute's name to delete.
     */
    private final String name;

    /**
     * Condition that value of the to be deleted attribute must satisfy.
     */
    private final Predicate<T> cond;

    /**
     * Ctor.
     * @param name Attribute's name to delete.
     * @param cond Condition that value of the to be deleted attribute
     *  must satisfy.
     */
    public FitAttrDelCond(final String name, final Predicate<T> cond) {
        this.name = name;
        this.cond = cond;
    }

    @Override
    public JsonObj make(final JsonObj input) throws Exception {
        final JsonObj result;
        if (input.contains(this.name)
            && this.cond.test(input.<T>get(this.name))) {
            result = new JsonObj(
                new Filtered<>(
                    attr -> !attr.name().equals(this.name),
                    input.attributes()
                )
            );
        } else {
            result = input;
        }
        return result;
    }
}
