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

    /**
     * The test checks if the executor performs all the steps given.
     */
    @Test
    fun executingTest() {
        val values: Array<Long> = arrayOf(0, 5, 10, 100, 123, 1000, 4728)
        val isExecuted: Array<Boolean> = arrayOf(false, false, false, false, false, false, false)
        val steps = arrayOf(
            SequenceStep(Runnable { isExecuted[0] = true }, values[0]),
            SequenceStep(Runnable { isExecuted[1] = true }, values[1]),
            SequenceStep(Runnable { isExecuted[2] = true }, values[2]),
            SequenceStep(Runnable { isExecuted[3] = true }, values[3]),
            SequenceStep(Runnable { isExecuted[4] = true }, values[4]),
            SequenceStep(Runnable { isExecuted[5] = true }, values[5]),
            SequenceStep(Runnable { isExecuted[6] = true }, values[6])
        )
        val executor = SequenceExecutor(steps)
        executor.start()

        var sumValues = 0L
        values.forEach { sumValues += it }
        Thread.sleep(sumValues)

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
        val values: Array<Long> = arrayOf(0, 5, 10, 100, 123, 1000, 4728)
        fun runnableContent(value: Long) {
            if(value + 10  > System.currentTimeMillis() - currentTimeMillis)
                assert(true)
            else
                assert(false)
            currentTimeMillis = System.currentTimeMillis()
        }
        val steps = arrayOf(
            SequenceStep(Runnable { runnableContent(values[0]) }, values[0]),
            SequenceStep(Runnable { runnableContent(values[1]) }, values[1]),
            SequenceStep(Runnable { runnableContent(values[2]) }, values[2]),
            SequenceStep(Runnable { runnableContent(values[3]) }, values[3]),
            SequenceStep(Runnable { runnableContent(values[4]) }, values[4]),
            SequenceStep(Runnable { runnableContent(values[5]) }, values[5]),
            SequenceStep(Runnable { runnableContent(values[6]) }, values[6])
        )
        val executor = SequenceExecutor(steps)
        currentTimeMillis = System.currentTimeMillis()
        executor.start()

        var sumValues = 0L
        values.forEach { sumValues += it }
        Thread.sleep(sumValues)
    }

}
