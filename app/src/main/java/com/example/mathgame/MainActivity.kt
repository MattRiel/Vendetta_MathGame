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

    lateinit var buttons: Array<Button>

    lateinit var validationTextView : TextView

    lateinit var timerProgressBar : ProgressBar
    var firstButtonPressed = false;
    var firstValue = 0;
    var secondValue = 0;

    //Rentang waktu 60 detik dengan interval 1 detik pada progress bar
    val maxTimeinMillis = 60000L
    val minTimeinMillis = 0L
    val intervalinMillis = 1000L

    var point = 0;
    var currentScore = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttons = arrayOf(
            findViewById(R.id.button1),
            findViewById(R.id.button2),
            findViewById(R.id.button3),
            findViewById(R.id.button4),
        )

        for (button in buttons)
        {
            button.setOnClickListener()
            {
                    view: View ->
                checkButton(view)
            }
        }

        validationTextView = findViewById(R.id.validationTextView)
        validationTextView.text =" "

        timerProgressBar = findViewById(R.id.timerProgressBar)
        timerProgressBar.max = (maxTimeinMillis/1000).toInt()
        timerProgressBar.min = (minTimeinMillis/1000).toInt()

        generateQuestion()

        val timer = object : CountDownTimer(maxTimeinMillis,intervalinMillis)
        {
            override fun onTick(millisUntilFinished: Long) {
                timerProgressBar.progress = (millisUntilFinished/1000.0).toInt()
            }

            override fun onFinish() {
                for (button in buttons)
                    button.isEnabled = false;

                //menghubungkan MainActivity dengan MainActivity2 dengan bantuan Inten
                val intent = Intent(this@MainActivity, MainActivity2::class.java)
                //mengirim data skor ke activity lain dengan menggunakan intent
                intent.putExtra("SCORE", currentScore.toString())
                startActivity(intent)
            }
        }
        timer.start()
    }

    private fun generateQuestion()
    {
        for (button in buttons)
            button.isEnabled = true;

        firstButtonPressed = false
        firstValue = 0
        secondValue = 0

        var randomGenerator = Random(System.currentTimeMillis())

        var firstCorrectValue = randomGenerator.nextInt(20, 50)
        var secondCorrectValue = randomGenerator.nextInt(20, 50)
        var result = firstCorrectValue + secondCorrectValue

        //value variabel result dimasukkan ke dalam variabel point (untuk validasi)
        point = result

        var firstRandomValue = randomGenerator.nextInt(10, result-1)
        var secondRandomValue = result-firstRandomValue
        
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

            //validate answer
            val answer = point
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