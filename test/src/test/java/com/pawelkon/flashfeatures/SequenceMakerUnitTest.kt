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

import com.pawelkon.flashfeatures.sequencer.SequenceMaker
import com.pawelkon.flashfeatures.sequencer.SequenceStep
import org.junit.Assert.assertEquals
import org.junit.Test
import java.lang.IllegalArgumentException

/**
 * Unit tests for SequenceMaker.
 */
class SequenceMakerUnitTest {

    //test values
    private val sequenceTimes = arrayOf(1L, 200L, 0L, 9999999L, -3L)

    /**
     * Checks whether the sequence is empty after the object has been initialized.
     */
    @Test
    fun emptySequence() {
        val sequenceMaker = object: SequenceMaker() {}
        val sequence = sequenceMaker.getSequence()
        assertEquals(0, sequence.size)
    }

    /**
     * Checks whether the sequence is the same length as the test data.
     */
    @Test
    fun nonEmptySequence() {
        assertEquals(sequenceTimes.size, testSequence().size)
    }

    /**
     * Checks whether all data of the sequence is the same as the test data.
     */
    @Test
    fun correctSequence() {
        val sequence = testSequence()
        var sum = 0L
        var iterator = 0
        sequence.forEach {
            sum += sequenceTimes[iterator]
            assertEquals(sum, it.millis)
            iterator++
        }
    }

    //generates and returns a test sequence
    private fun testSequence(): List<SequenceStep> {
        val sequenceMaker = object: SequenceMaker() {}
        sequenceTimes.forEach {
            try {
                sequenceMaker.interval(it)
            } catch (e: IllegalArgumentException) {
                if(e.message == "the value must be greater than zero" && it <= 0L)
                    assert(true)
                else if(e.message == "the value cannot be negative" && it < 0L)
                    assert(true)
                else
                    assert(false)
            }
        }
        return sequenceMaker.getSequence()
    }
}