package com.example.myapplication

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val c1 = findViewById<TextView>(R.id.character1)
        val c2 = findViewById<TextView>(R.id.character2)
        val img1 = findViewById<ImageView>(R.id.image1)
        val img2 = findViewById<ImageView>(R.id.image2)
        val winnerText = findViewById<TextView>(R.id.winner)
        val roastText = findViewById<TextView>(R.id.joke)
        val cardText = findViewById<TextView>(R.id.cardText)
        val cardImage = findViewById<ImageView>(R.id.cardImage)
        val btn = findViewById<Button>(R.id.btn)

        fun loadBattle() {

            // 🔥 RANDOM CHARACTERS
            val id1 = (1..826).random()
            val id2 = (1..826).random()

            winnerText.text = "⚔️ Summoning fighters..."
            roastText.text = ""

            var name1 = "Fighter 1"
            var name2 = "Fighter 2"

            // 🔹 CHARACTER 1
            RickInstance.api.getCharacter(id1).enqueue(object : Callback<Character> {
                override fun onResponse(call: Call<Character>, response: Response<Character>) {
                    val data = response.body()

                    name1 = data?.name ?: "Unknown"
                    c1.text = name1

                    Glide.with(this@MainActivity)
                        .load(data?.image)
                        .into(img1)
                }

                override fun onFailure(call: Call<Character>, t: Throwable) {
                    c1.text = "Error"
                }
            })

            // 🔹 CHARACTER 2
            RickInstance.api.getCharacter(id2).enqueue(object : Callback<Character> {
                override fun onResponse(call: Call<Character>, response: Response<Character>) {
                    val data = response.body()

                    name2 = data?.name ?: "Unknown"
                    c2.text = name2

                    Glide.with(this@MainActivity)
                        .load(data?.image)
                        .into(img2)
                }

                override fun onFailure(call: Call<Character>, t: Throwable) {
                    c2.text = "Error"
                }
            })

            // 🔹 CARD
            CardInstance.api.getCard().enqueue(object : Callback<CardResponse> {
                override fun onResponse(call: Call<CardResponse>, response: Response<CardResponse>) {

                    val card = response.body()?.cards?.get(0)

                    cardText.text = "🃏 ${card?.value} of ${card?.suit}"

                    Glide.with(this@MainActivity)
                        .load(card?.image)
                        .into(cardImage)
                }

                override fun onFailure(call: Call<CardResponse>, t: Throwable) {
                    cardText.text = "Card Error"
                }
            })

            // 🔹 WINNER + ROAST
            YesNoInstance.api.getDecision().enqueue(object : Callback<YesNo> {
                override fun onResponse(call: Call<YesNo>, response: Response<YesNo>) {

                    val firstWins = response.body()?.answer == "yes"

                    val winner = if (firstWins) name1 else name2
                    val loser = if (firstWins) name2 else name1

                    winnerText.text = "🏆 $winner DESTROYS $loser 💥"

                    // 🔹 ROAST
                    InsultInstance.api.getInsult().enqueue(object : Callback<Insult> {
                        override fun onResponse(call: Call<Insult>, response: Response<Insult>) {
                            roastText.text = "💀 $loser got roasted 😂\n${response.body()?.insult}"
                        }

                        override fun onFailure(call: Call<Insult>, t: Throwable) {
                            roastText.text = "Roast failed"
                        }
                    })
                }

                override fun onFailure(call: Call<YesNo>, t: Throwable) {
                    winnerText.text = "Winner Error"
                }
            })
        }

        // 🔘 BUTTON CLICK
        btn.setOnClickListener {
            loadBattle()
        }

        // 🚀 FIRST LOAD
        loadBattle()
    }
}