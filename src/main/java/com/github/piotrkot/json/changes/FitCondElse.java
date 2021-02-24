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
import com.github.piotrkot.json.Method;
import java.util.function.Predicate;

/**
 * Conditional fit with else statement.
 *
 * @param <T> Type of input.
 * @since 1.0
 */
public class FitCondElse<T> implements Fit<T> {
    /**
     * Condition to make the fit.
     */
    private final Predicate<T> cond;

    /**
     * Fitting method when condition is satisfied.
     */
    private final Method<T> satisf;

    /**
     * Fitting method when condition is not satisfied.
     */
    private final Method<T> notsatisf;

    /**
     * Ctor.
     * @param cond Condition to make the fit.
     * @param satisf Fitting method when condition is satisfied.
     * @param notsatisf Fitting method when condition is not satisfied.
     */
    public FitCondElse(final Predicate<T> cond, final Method<T> satisf,
        final Method<T> notsatisf) {
        this.cond = cond;
        this.satisf = satisf;
        this.notsatisf = notsatisf;
    }

    @Override
    public final T make(final T input) throws Exception {
        final T result;
        if (this.cond.test(input)) {
            result = this.satisf.apply(input);
        } else {
            result = this.notsatisf.apply(input);
        }
        return result;
    }
}
