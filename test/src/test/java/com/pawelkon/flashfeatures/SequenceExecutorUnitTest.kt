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
