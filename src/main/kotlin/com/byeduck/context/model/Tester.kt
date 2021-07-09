package com.byeduck.context.model

import java.time.LocalDateTime

data class Tester(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val country: String,
    val lastLogin: LocalDateTime
)
