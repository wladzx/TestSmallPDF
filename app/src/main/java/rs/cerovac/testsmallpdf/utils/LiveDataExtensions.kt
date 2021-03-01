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


/**
 * Returns an [Observer] of Non-nullable values
 */
fun <T> createObserverNotNull(work: (T) -> Unit): Observer<T> {
    return Observer { value -> value?.let<T, Unit> { work(it) } }
}

/**
 * Returns a [LiveData] for only non-nullable value changes
 */
fun <T> LiveData<T>.observeNotNull(owner: LifecycleOwner, work: (T) -> Unit): Observer<T> {

    val observer: Observer<T> = createObserverNotNull(work)
    observe(owner, observer)
    return observer

}