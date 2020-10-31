package com.gruzini

import java.time.LocalDateTime
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import java.util.*

fun LocalDateTime.format() = this.format(englishDataFormatter)

private val daysLookup = (1..31).associate { i: Int -> i.toLong() to getOrdinal(i) } //we have a map which contains numbers as keys and '1st', '2nd', '23rd' etc as values

private val englishDataFormatter = DateTimeFormatterBuilder()
        .appendPattern("yyyy-MM-dd")
        .appendLiteral(" ")
        .appendText(ChronoField.DAY_OF_MONTH, daysLookup)
        .appendLiteral(" ")
        .appendPattern("yyyy")
        .toFormatter(Locale.ENGLISH)

private fun getOrdinal(number: Int) = when {
    number in 11..13 -> "${number}th"
    number%10 == 1 -> "${number}st"
    number%10 == 2 -> "${number}nd"
    number%10 == 3 -> "${number}rd"
    else -> "${number}th"
}

private fun String.toSlug() = toLowerCase() //this seems very cool! we build a function that combines several string operations
        .replace("\n", " ")
        .replace("[^a-z\\d\\s]".toRegex(), " ")
        .split(" ")
        .joinToString ("-")
        .replace("-+".toRegex(), "-")