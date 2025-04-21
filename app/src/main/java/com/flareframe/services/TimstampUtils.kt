package com.flareframe.services

import com.google.firebase.Timestamp
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object TimstampUtils {
   public fun convertFirebaseTimestampToTimestampZ(timestamp: Timestamp): String {
        val instant: Instant = timestamp.toDate().toInstant()

        // 2. Convert Instant to ZonedDateTime (with UTC)
        val zonedDateTime: ZonedDateTime = instant.atZone(ZoneId.of("UTC"))

        // 3. Format the ZonedDateTime as an ISO 8601 string
        val formatter: DateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        return zonedDateTime.format(formatter)
    }
}