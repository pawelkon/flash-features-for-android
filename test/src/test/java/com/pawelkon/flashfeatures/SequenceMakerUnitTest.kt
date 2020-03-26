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