package com.volynkun.cft_focus_start.data

data class Currency(
    val ID: String,
    val NumCode: Int,
    val CharCode: String,
    val Nominal: Int,
    val Name: String,
    val Value: Double,
    val Previous: Double
)