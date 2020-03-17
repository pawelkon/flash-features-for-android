package com.pawelkon.flashfeatures

import com.pawelkon.flashfeatures.sequencer.SequenceMaker
import com.pawelkon.flashfeatures.sequencer.SequenceStep
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.absoluteValue

class SequenceMakerUnitTest {

    private val sequenceTimes = arrayOf(1L, 200L, 0L, 9999999L, -3L)

    @Test
    fun emptySequence() {
        val sequenceMaker = object: SequenceMaker() {}
        val sequence = sequenceMaker.getSequence()
        assertEquals(0, sequence.size)
    }

    @Test
    fun nonEmptySequence() {
        assertEquals(sequenceTimes.size, testSequence().size)
    }

    @Test
    fun correctSequence() {
        val sequence = testSequence()
        var sum = 0L
        var iterator = 0
        sequence.forEach {
            sum += sequenceTimes[iterator].absoluteValue
            assertEquals(sum, it.millis)
            iterator++
        }
    }

    private fun testSequence(): Array<SequenceStep> {
        val sequenceMaker = object: SequenceMaker() {}
        sequenceTimes.forEach { sequenceMaker.interval(it) }
        return sequenceMaker.getSequence()
    }
}