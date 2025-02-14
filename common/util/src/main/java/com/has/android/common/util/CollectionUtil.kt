package com.has.android.common.util

fun <T> MutableList<T>.offerOrRemove(new: T, predicate: (T) -> Boolean) {
    val index = indexOfFirst(predicate = predicate)
    if(index != -1) {
        removeAt(index)
    } else {
        add(new)
    }
}

fun <T> MutableList<T>.offer(new: T, predicate: (T) -> Boolean) {
    val index = indexOfFirst(predicate = predicate)
    if(index == -1) {
        add(new)
    } else {
        removeAt(index)
        add(index, new)
    }
}

fun <T> MutableList<T>.checkContain(predicate: (T) -> Boolean): Boolean {
    val index = indexOfFirst(predicate = predicate)
    return index != -1
}

fun <T> MutableList<T>.offerFirst(new: T) {
    clear()
    add(new)
}