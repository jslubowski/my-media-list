package com.example.mymedialist.util

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

private val PUNCTUATION = listOf(", ", "; ", ": ", " ")

fun String.smartTruncate(length: Int): String {
    val words = split(" ")
    var added = 0
    var hasMore = false
    val builder = StringBuilder()
    for (word in words) {
        if (builder.length > length) {
            hasMore = true
            break
        }
        builder.append(word)
        builder.append(" ")
        added += 1
    }

    PUNCTUATION.map {
        if (builder.endsWith(it)) {
            builder.replace(builder.length - it.length, builder.length, "")
        }
    }

    if (hasMore) {
        builder.append("...")
    }
    return builder.toString()
}

fun Activity.hideKeyboard() {
    currentFocus?.windowToken?.let {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager?)
            ?.hideSoftInputFromWindow(it, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}
