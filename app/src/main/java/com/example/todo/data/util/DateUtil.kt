package com.example.todo.data.util

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun Long.toDate(): LocalDateTime{
    val instant = Instant.ofEpochMilli(this)
    val zone = ZoneId.systemDefault()

    return LocalDateTime.ofInstant(instant, zone)
}

fun LocalDateTime.toLongMillis(): Long{
    val zone = ZoneId.systemDefault()
    val instant = atZone(zone).toInstant()

    return instant.toEpochMilli()
}