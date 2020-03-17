/*
MIT License

Copyright (c) 2020 pawelkon

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

package com.pawelkon.flashfeatures.sequencer

import android.util.Log
import kotlin.math.absoluteValue

/**
 * An abstract maker for creating a sequence.
 *
 * @constructor Creates a pure maker.
 */
abstract class SequenceMaker {

    private val steps = mutableListOf<SequenceStep>()

    private var timeSum: Long = 0

    /**
     * Adds a time interval between two steps.
     *
     * @param millis length(in milliseconds) of the time interval.
     */
    fun interval(millis: Long) {
        newStepDefinition(Runnable {  }, millis)

        if(millis == 0L)
            Log.w("illegal argument:", "this calling does nothing - parameter value cannot be zero")
    }

    /**
     * Returns the created sequence.
     *
     * @return the sequence array.
     */
    fun getSequence(): Array<SequenceStep> {
        return steps.toTypedArray()
    }

    /**
     * Removes all steps from the sequence.
     */
    fun reset() {
        timeSum = 0
        steps.clear()
    }

    /**
     * Creates a new step definition for the maker.
     * This method extends the maker with a new step definition for a specific implementation.
     *
     * @param runnable the code to be executed.
     * @param millis time(in milliseconds) after which the code will be executed.
     */
    protected fun newStepDefinition(runnable: Runnable, millis: Long) {
        timeSum += millis.absoluteValue
        val step = SequenceStep(runnable, timeSum)
        steps.add(step)
    }
}