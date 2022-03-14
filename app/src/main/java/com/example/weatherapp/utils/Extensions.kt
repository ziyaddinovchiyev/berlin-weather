package com.example.weatherapp

import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

fun Long.toDateTimeStamp(): Long =
    Instant.ofEpochSecond(this).atZone(ZoneId.systemDefault()).toLocalDate().toEpochDay()

fun Long.toDate(): String {
    return Instant.ofEpochSecond(this).atZone(ZoneId.systemDefault()).format(
        DateTimeFormatter.ofPattern("EEEE dd.MM")
    )
}

fun Long.toTime(): String {
    return Instant.ofEpochSecond(this).atZone(ZoneId.of("GMT")).format(
        DateTimeFormatter.ofPattern("HH:mm")
    )
}

fun Long.toDateTime(): String {
    return Instant.ofEpochSecond(this).atZone(ZoneId.of("GMT")).format(
        DateTimeFormatter.ofPattern("EEEE dd.MM, HH:mm")
    )
}