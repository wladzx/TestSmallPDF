package rs.cerovac.testsmallpdf.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Observes a [LiveData] that can return null values
 */
fun <T> LiveData<T>.observeNullable(owner: LifecycleOwner, work: (T?) -> Unit): Observer<T> {
    val observer: Observer<T> = Observer { value -> work(value) }
    observe(owner, observer)
    return observer

}