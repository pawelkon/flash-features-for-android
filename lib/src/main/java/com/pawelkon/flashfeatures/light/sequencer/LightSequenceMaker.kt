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

package com.pawelkon.flashfeatures.light.sequencer

import com.pawelkon.flashfeatures.light.Light
import com.pawelkon.flashfeatures.sequencer.SequenceMaker

/**
 * A maker for creating a light sequence.
 *
 * @constructor Creates a new light sequence maker.
 * @param light an object of Light.
 */
open class LightSequenceMaker(private val light: Light) : SequenceMaker() {

    /**
     * Turns on the light for a specified time and turns it off.
     *
     * @param millis the time(in milliseconds) the light will be on.
     * @throws IllegalArgumentException if **millis** is equal or less than 0.
     */
    fun flash(millis: Long) {
        if(millis <= 0L)
            throw IllegalArgumentException("the value must be greater than zero")

        newStepDefinition(Runnable { light.setMode(true) }, 0)
        newStepDefinition(Runnable { light.setMode(false) }, millis)
    }
}