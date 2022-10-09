package com.example.mathgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity2 : AppCompatActivity() {

    lateinit var resultScore: TextView
    lateinit var btnRestart : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        btnRestart = findViewById(R.id.btnRestart)
        btnRestart.setOnClickListener(){
            //menghubungkan MainActivity2 dengan MainActivity
            val intent = Intent(this@MainActivity2, MainActivity::class.java)
            startActivity(intent)
        }
        //mengambil value skor dari MainActivity, disimpan dalam variabel score
        val score = intent.getStringExtra("SCORE")
        resultScore = findViewById(R.id.resultScore)
        resultScore.text = "YOUR SCORE : "+ score
    }
}