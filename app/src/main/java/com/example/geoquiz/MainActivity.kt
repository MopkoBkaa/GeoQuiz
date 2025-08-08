package com.example.geoquiz

import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import kotlin.math.log


class MainActivity : AppCompatActivity() {

    private lateinit var true_button : Button
    private lateinit var false_button : Button
    private lateinit var next_button : Button
    private lateinit var question_text : TextView

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true))

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_main)

        true_button = findViewById(R.id.true_button)
        false_button = findViewById(R.id.false_button)
        next_button = findViewById(R.id.next_button)
        question_text = findViewById(R.id.question_text)

        val questionTextResId = questionBank [currentIndex].textResId
        question_text.setText(questionTextResId)

        true_button.setOnClickListener{ view: View ->
            checkAnswer(true)
        }

        false_button.setOnClickListener{view: View ->
            checkAnswer(false)
        }

        next_button.setOnClickListener {

            if (currentIndex + 1 < questionBank.size){
                currentIndex++
                updateQuestion()
            }
            if (currentIndex + 1 >= questionBank.size){
                Toast.makeText(this,
                    R.string.finish,
                    Toast.LENGTH_SHORT).show()
            }
        }


        }

    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = questionBank[currentIndex].answer

        val messageResId = if (userAnswer == correctAnswer){
            R.string.incorrect_toast
        }
        else{
            R.string.uncorrect_toast
        }

        Toast.makeText(this,messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun updateQuestion(){
        val questionTextResId = questionBank [currentIndex].textResId
        question_text.setText(questionTextResId)
    }
    }
