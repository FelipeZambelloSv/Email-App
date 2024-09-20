package com.stars.locaweb.Utils

data class EmailItem(
    val subject: String,
    val sender: String,
    val preview: String,
    val isSpam: Boolean = false,
    val isSent: Boolean = false
)

