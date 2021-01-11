package com.emintolgahanpolat.retrofitex

import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.*
import androidx.annotation.ColorInt


fun String.toSpannable(): SpannableString {
    return SpannableString(this)
}

/** Üzeri Çizili */
fun SpannableString.strikethrough(start:Int,end:Int): SpannableString {
    val txt = SpannableString(this)
    txt.setSpan(StrikethroughSpan(), start,end, 0)
    return txt
}

/** Koyu */
fun SpannableString.boldText(start:Int,end:Int): SpannableString {
    val txt = SpannableString(this)
    txt.setSpan(StyleSpan(Typeface.BOLD),  start,end, 0)
    return txt
}

/** Altı Çizili*/
fun SpannableString.underline(start:Int,end:Int): SpannableString {
    val txt = SpannableString(this)
    txt.setSpan(UnderlineSpan(),  start,end,  0)
    return txt
}

/** Italic */
fun SpannableString.italicText(start:Int,end:Int): SpannableString {
    val txt = SpannableString(this)
    txt.setSpan(StyleSpan(Typeface.ITALIC),  start,end, 0)
    return txt
}

fun SpannableString.foregroundColorSpan(mColor: Int,start:Int,end:Int): SpannableString {
    val txt = SpannableString(this)
    txt.setSpan(ForegroundColorSpan(mColor),  start,end, 0)
    return txt
}

fun SpannableString.backgroundColorSpan(mColor: Int, start:Int, end:Int): SpannableString {
    val txt = SpannableString(this)
    txt.setSpan(BackgroundColorSpan(mColor),  start,end,  0)
    return txt
}

fun SpannableString.superscript(start:Int,end:Int): SpannableString {
    val txt = SpannableString(this)
    txt.setSpan(SuperscriptSpan(),  start,end, 0)
    return txt
}

fun SpannableString.subscript(start:Int,end:Int): SpannableString {
    val txt = SpannableString(this)
    txt.setSpan(SubscriptSpan(),  start,end, 0)
    return txt
}

fun SpannableString.relativeSizeSpan(size: Float,start:Int,end:Int): SpannableString {
    val txt = SpannableString(this)
    txt.setSpan(RelativeSizeSpan(size),  start,end, 0)
    return txt
}

fun SpannableString.mURLSpan(url: String,start:Int,end:Int): SpannableString {
    val txt = SpannableString(this)
    txt.setSpan(URLSpan(url),  start,end, 0)
    return txt
}


