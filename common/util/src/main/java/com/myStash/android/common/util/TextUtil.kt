package com.myStash.android.common.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

fun highlightTextBuilder(fullText: String, highlightText: String, highlightColor: Color = Color(0xFF95A70F)): AnnotatedString {
    return buildAnnotatedString {
        when(highlightText.length) {
            0 -> {
                append(fullText)
            }
            1-> {
                fullText.forEach {
                    if(highlightText == it.toString()) {
                        withStyle(
                            style = SpanStyle(color = highlightColor),
                            block = { append(highlightText) }
                        )
                    } else {
                        append(it)
                    }
                }
            }
            else -> {
                var tempString = ""
                var checkHighlightString = highlightText

                fullText.forEach { char ->
                    if(highlightText.contains(char.toString()) && checkHighlightString.firstOrNull() == char) {
                        tempString += char
                        checkHighlightString = checkHighlightString.substring(1)

                        if(tempString == highlightText) {
                            withStyle(
                                style = SpanStyle(color = highlightColor),
                                block = {
                                    append(tempString)
                                    tempString = ""
                                    checkHighlightString = highlightText
                                }
                            )
                        }
                    } else {
                        append(tempString)
                        tempString = ""
                        checkHighlightString = highlightText
                        append(char)
                    }
                }
            }
        }
    }
}

fun CharSequence.removeBlank(): String {
    return replace(Regex("\\s+"), "")
}

fun CharSequence.checkBlank(): Boolean {
    return contains(Regex("\\s"))
}