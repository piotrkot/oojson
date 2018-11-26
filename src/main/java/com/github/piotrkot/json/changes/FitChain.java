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
package com.github.piotrkot.json.changes;

import com.github.piotrkot.json.Fit;
import java.util.Arrays;

/**
 * Apply all fits.
 *
 * @param <T> Type of input.
 * @since 1.0
 */
public final class FitChain<T> implements Fit<T> {
    /**
     * Fits to be applied.
     */
    private final Iterable<Fit<T>> fits;

    /**
     * Ctor.
     * @param fits Fits collection.
     */
    public FitChain(final Fit<T>... fits) {
        this(Arrays.asList(fits));
    }

    /**
     * Ctor.
     * @param fits Fits collection.
     */
    public FitChain(final Iterable<Fit<T>> fits) {
        this.fits = fits;
    }

    @Override
    public T make(final T input) throws Exception {
        T result = input;
        for (final Fit<T> fit : this.fits) {
            result = fit.make(result);
        }
        return result;
    }
}
