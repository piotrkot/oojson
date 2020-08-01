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
import com.github.piotrkot.json.Method;
import java.util.function.Predicate;

/**
 * Conditional fit.
 *
 * @param <T> Type of input.
 * @since 1.0
 */
public class FitCond<T> implements Fit<T> {
    /**
     * Condition to make the fit.
     */
    private final Predicate<T> cond;

    /**
     * Fitting method.
     */
    private final Method<T> method;

    /**
     * Ctor.
     * @param cond Condition to make the fit.
     * @param method Fitting method.
     */
    public FitCond(final Predicate<T> cond, final Method<T> method) {
        this.cond = cond;
        this.method = method;
    }

    @Override
    public final T make(final T input) throws Exception {
        final T result;
        if (this.cond.test(input)) {
            result = this.method.apply(input);
        } else {
            result = input;
        }
        return result;
    }
}
