package com.doug.showcase.test

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

class TestObserver<T> : Observer<T> {

    private val observedValues = mutableListOf<T?>()

    override fun onChanged(value: T?) {
        observedValues.add(value)
    }
}

fun <T> LiveData<T>.testObserver() = TestObserver<T>().also {
    observeForever(it)
}
