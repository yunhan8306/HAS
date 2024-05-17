package com.myStash.common.util

fun <T> MutableList<T>.offerOrRemove(new: T, predicate: (T) -> Boolean) {
    val index = indexOfFirst(predicate = predicate)
    if(index != -1) {
        removeAt(index)
    } else {
        add(new)
    }
}