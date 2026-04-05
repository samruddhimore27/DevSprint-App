package com.example.myapplication

data class CardResponse(
    val cards: List<Card>,
    val deck_id: String,
    val remaining: Int,
    val success: Boolean
)