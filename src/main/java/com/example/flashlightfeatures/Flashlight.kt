package com.example.flashlightfeatures

interface Flashlight {

    fun mode(): Boolean?

    fun setMode(enabled: Boolean): Boolean?
}