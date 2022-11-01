package com.example.mathgame

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import kotlin.random.Random
import android.content.Intent
import android.content.Intent as Intent1

class MainActivity : AppCompatActivity() {

    // deklarasi tombol, textView, dan ProgressBar
    lateinit var buttons: Array<Button>
    lateinit var validationTextView : TextView
    lateinit var timerProgressBar : ProgressBar

    var firstButtonPressed = false;  // pengecekan tombol pertama
    var firstValue = 0;  // angka pertama yang diacak
    var secondValue = 0; // angaka kedua yang diacak

    //Rentang waktu 60 detik dengan interval 1 detik pada progress bar
    val maxTimeinMillis = 15000L  // waktu maksimal timer (15 detik)
    val minTimeinMillis = 0L      // waktu minimum timer
    val intervalinMillis = 1000L  // perubahan selang waktu (1 detik)

    var point = 0;                // nilai awal permainan
    var currentScore = 0          // nilai selama permainan berlangsung

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Membentuk tampilan awal pada activity_main
        setContentView(R.layout.activity_main)

        // pencarian id button lalu menginput ke dalam array
        buttons = arrayOf(
            findViewById(R.id.button1),
            findViewById(R.id.button2),
            findViewById(R.id.button3),
            findViewById(R.id.button4),
        )

        // menampilkan button melalui array
        for (button in buttons)
        {
            button.setOnClickListener()
            {
                    view: View ->
                checkButton(view)
            }
        }

        // koneksi textView ( CORRECT / INCORRECT)
        validationTextView = findViewById(R.id.validationTextView)
        validationTextView.text =" "

        // koneksi progressbar
        timerProgressBar = findViewById(R.id.timerProgressBar)
        timerProgressBar.max = (maxTimeinMillis/1000).toInt()
        timerProgressBar.min = (minTimeinMillis/1000).toInt()

        // membuat soal
        generateQuestion()

        val timer = object : CountDownTimer(maxTimeinMillis,intervalinMillis)
        {
            // menggerakkan progress bar
            override fun onTick(millisUntilFinished: Long) {
                timerProgressBar.progress = (millisUntilFinished/1000.0).toInt()
            }

            // ketika timer habis, maka tombol akan dimatikan
            override fun onFinish() {
                for (button in buttons)
                    button.isEnabled = false;

                // dilanjutkan dengan menghubungkan MainActivity dengan MainActivity2 dengan bantuan Inten
                val intent = Intent(this@MainActivity, MainActivity2::class.java)
                //mengirim data skor ke activity lain dengan menggunakan intent
                intent.putExtra("SCORE", currentScore.toString())
                startActivity(intent)
            }
        }
        // memulai timer
        timer.start()
    }

    private fun generateQuestion()
    {
        for (button in buttons)
            button.isEnabled = true;

        firstButtonPressed = false;  // pengecekan tombol pertama
        firstValue = 0;  // angka pertama yang diacak
        secondValue = 0; // angaka kedua yang diacak

        var randomGenerator = Random(System.currentTimeMillis())

        // nilai acak pertama yang benar
        var firstCorrectValue = randomGenerator.nextInt(20, 50)
        // nilai acak kedua yang benar
        var secondCorrectValue = randomGenerator.nextInt(20, 50)
        // hasil dari kedua nilai yang benar
        var result = firstCorrectValue + secondCorrectValue

        //value variabel result dimasukkan ke dalam variabel point (untuk validasi)
        point = result

        // nilai acak pertama
        var firstRandomValue = randomGenerator.nextInt(10, result-1)
        // nilai acak kedua
        var secondRandomValue = result-firstRandomValue
        // pengacakan peletakan tombol
        var arrayInt = arrayOf(0, 1, 2, 3)
        arrayInt.shuffle(randomGenerator)

        //fill buttons with incorrect values
        buttons[arrayInt[0]].text = firstCorrectValue.toString();
        buttons[arrayInt[1]].text = secondCorrectValue.toString();

        //fill buttons with incorrect values
        buttons[arrayInt[2]].text = firstRandomValue.toString();
        buttons[arrayInt[3]].text = secondRandomValue.toString();


    }

    private fun checkButton(view: View)
    {
        var buttonPressed = view as Button;

        if(firstButtonPressed)
        {
            secondValue = buttonPressed.text.toString().toInt()
            var result = firstValue + secondValue

            val answer = point
            // validasi jawaban dengan result sebelumnya
            if (result == answer)
            {
                validationTextView.text = "CORRECT"
                validationTextView.setTextColor(Color.GREEN)
                currentScore = currentScore + 1
            }
            else
            {
                validationTextView.text = "INCORRECT"
                validationTextView.setTextColor(Color.RED)
            }
            generateQuestion()

        }

        else
        {
            firstValue = buttonPressed.text.toString().toInt()
            firstButtonPressed = true
            buttonPressed.isEnabled = false
        }

    }

}