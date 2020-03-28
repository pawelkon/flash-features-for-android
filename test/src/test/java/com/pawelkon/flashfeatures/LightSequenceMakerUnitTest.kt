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

import com.pawelkon.flashfeatures.light.Light
import com.pawelkon.flashfeatures.light.sequencer.LightSequenceMaker
import com.pawelkon.flashfeatures.sequencer.SequenceStep
import org.junit.Assert.assertEquals
import org.junit.Test
import java.lang.IllegalArgumentException

class LightSequenceMakerUnitTest {

    //Light test object
    private val light = object: Light {
        private var mode: Boolean? = null

        override fun mode(): Boolean? {
            return mode
        }

        override fun setMode(mode: Boolean) {
            this.mode = mode
        }
    }

    //Test values
    private val sequenceTimes = arrayOf(1L, 200L, 0L, 9999999L, -3L)

    /**
     * This test checks whether the sequence is empty after the object has been initialized.
     */
    @Test
    fun emptySequence() {
        val sequenceMaker = LightSequenceMaker(light)
        val sequence = sequenceMaker.getSequence()
        assertEquals(0, sequence.size)
    }

    /**
     * This test checks whether the sequence is the same length as the test data.
     */
    @Test
    fun nonEmptySequence() {
        assertEquals(countSteps(), testSequence().size)
    }

    /**
     * This test checks whether all data of the sequence is the same as the test data.
     */
    @Test
    fun correctSequence() {
        val sequence = testSequence()
        var i = 0
        var sum = 0L
        sequenceTimes.forEach {
            if(it > 0) {
                assertEquals(sum, sequence[i].millis)
                sum += it
                assertEquals(sum, sequence[i+1].millis)
                sum += it
                assertEquals(sum, sequence[i+2].millis)
                i += 3
            }
        }
    }

    //generates and returns a test sequence
    private fun testSequence(): List<SequenceStep> {
        val sequenceMaker = LightSequenceMaker(light)
        sequenceTimes.forEach {
            try {
                sequenceMaker.flash(it)
                sequenceMaker.interval(it)
            } catch (e: IllegalArgumentException) {
                if(e.message == "the value must be greater than zero" && it <= 0L)
                    assert(true)
                else if(e.message == "the value cannot be negative" && it < 0L) {
                    assert(true)
                }
                else
                    assert(false)
            }
        }
        return sequenceMaker.getSequence()
    }

    //calculates the number of steps that should be
    private fun countSteps(): Int {
        var i = 0
        sequenceTimes.forEach {
            if(it > 0) {
                i+=3
            }
        }
        return i
    }
}