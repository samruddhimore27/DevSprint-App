package com.example.myapplication

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    // 🔥 Rick & Morty (RANDOM CHARACTER)
    @GET("api/character/{id}")
    fun getCharacter(@Path("id") id: Int): Call<Character>

    // 🃏 Card API
    @GET("api/deck/new/draw/?count=1")
    fun getCard(): Call<CardResponse>

    // 🤖 Yes/No API (winner decision)
    @GET("api")
    fun getDecision(): Call<YesNo>

    // 💀 Insult API (roast)
    @GET("generate_insult.php?lang=en&type=json")
    fun getInsult(): Call<Insult>
}