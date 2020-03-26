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

import java.util.*

/**
 * This class executes the sequence passed by the constructor.
 *
 * @constructor Creates a sequence executor with the passed sequence.
 * @param sequence an array with a sequence.
 */
class SequenceExecutor(private val sequence: List<SequenceStep>) {

    private var timer: Timer? = null

    private var delay: Long = 0

    companion object { const val TIMER_THREAD_NAME = "SequenceExecutor" }

    /**
     * Starts the sequence executing.
     */
    fun start() {
        stop()
        setNewTimer()
        loadSteps()
    }

    /**
     * Stops and cancels the sequence executing.
     */
    fun stop() {
        timer?.cancel()
        timer?.purge()
        timer = null
    }

    /**
     * Returns the current delay.
     *
     * @return Long
     */
    fun delay(): Long {
        return delay
    }

    /**
     * Sets the current delay.
     *
     * @param delay the delay value
     */
    fun setDelay(delay: Long) {
        this.delay = delay
    }

    private fun setNewTimer() {
        if(timer == null)
            timer = Timer(TIMER_THREAD_NAME)
    }

    private fun setNewSchedule(runnable: Runnable, delay: Long) {
        timer?.schedule(object: TimerTask() {
            override fun run() {
                runnable.run()
            }
        }, delay + this.delay)
    }

    private fun loadSteps() {
        sequence.forEach { setNewSchedule(it.runnable, it.millis) }
    }
}