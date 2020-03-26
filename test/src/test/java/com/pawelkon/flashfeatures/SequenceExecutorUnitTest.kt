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

package com.pawelkon.flashfeatures

import com.pawelkon.flashfeatures.sequencer.SequenceExecutor
import com.pawelkon.flashfeatures.sequencer.SequenceStep
import org.junit.Assert.assertEquals
import org.junit.Test
import java.lang.Exception
import java.util.*

/**
 * Unit tests for SequenceExecutor.
 */
class SequenceExecutorUnitTest {

    //test values
    private val values: Array<Long> = arrayOf(0, 5, 10, 100, 123, 1000, 4728)

    /**
     * The test checks if the executor performs all the steps given.
     */
    @Test
    fun executingTest() {
        val steps = mutableListOf<SequenceStep>()
        val isExecuted: Array<Boolean> = arrayOf(false, false, false, false, false, false, false)
        values.forEachIndexed { index, _ ->
            steps.add(SequenceStep(Runnable { isExecuted[index] = true }, values[index]))
        }
        val executor = SequenceExecutor(steps)
        executor.start()

        waiting()

        isExecuted.forEach {
            assertEquals(true, it)
        }

    }

    /**
     * This test checks the time accuracy of sequence execution with +10 milliseconds inaccuracy.
     */
    @Test
    fun executingAccordingTime() {
        var currentTimeMillis = 0L
        fun runnableContent(value: Long) {
            if(value + 10  > System.currentTimeMillis() - currentTimeMillis)
                assert(true)
            else
                assert(false)
            currentTimeMillis = System.currentTimeMillis()
        }
        val steps = mutableListOf<SequenceStep>()
        values.forEachIndexed { index, _ ->
            steps.add(SequenceStep(Runnable { runnableContent(values[index]) }, values[index]))
        }
        val executor = SequenceExecutor(steps)
        currentTimeMillis = System.currentTimeMillis()
        executor.start()

        waiting()
    }

    private fun waiting() {
        var sumValues = 0L
        values.forEach { sumValues += it }
        Thread.sleep(sumValues)
    }
}
