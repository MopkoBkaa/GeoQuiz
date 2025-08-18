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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.math.log

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var true_button : Button
    private lateinit var false_button : Button
    private lateinit var next_button : Button
    private lateinit var question_text : TextView
    private lateinit var before_button : Button

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true))

    private var currentIndex = 0
    private var correctCheckAnswer = 0
    private var uncorrectCheckAnswer = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"onCreate called")
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_main)

        true_button = findViewById(R.id.true_button)
        false_button = findViewById(R.id.false_button)
        next_button = findViewById(R.id.next_button)
        question_text = findViewById(R.id.question_text)
        before_button = findViewById(R.id.before_button)

        val questionTextResId = questionBank [currentIndex].textResId
        question_text.setText(questionTextResId)

        true_button.setOnClickListener{ view: View ->
            if (questionBank[currentIndex].checkAnswer) {
                checkHaveAnswersForQuestions()
                checkAnswer(true)
                checkCompleteAnswers(correctCheckAnswer, uncorrectCheckAnswer)
                nextAnswer()
            }
            else{
                MaterialAlertDialogBuilder(this)
                    .setTitle("Вы уже ответили на этот вопрос!")
                    .setMessage("")
                    .setPositiveButton("ОК") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }
        false_button.setOnClickListener{view: View ->
            if (questionBank[currentIndex].checkAnswer) {
                checkHaveAnswersForQuestions()
                checkAnswer(false)
                checkCompleteAnswers(correctCheckAnswer, uncorrectCheckAnswer)
                nextAnswer()
            }
            else{
                MaterialAlertDialogBuilder(this)
                    .setTitle("Вы уже ответили на этот вопрос!")
                    .setMessage("")
                    .setPositiveButton("ОК") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }
        next_button.setOnClickListener {
            nextAnswer()
        }
        before_button.setOnClickListener {
            beforeAnswer()
        }


        }

    override fun onStart() {
        super.onStart()
        Log.d(TAG,"onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG,"onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG,"onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG,"onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy called")
    }



    private fun checkHaveAnswersForQuestions(){
        var checkAnswersForQuestion = questionBank[currentIndex].checkAnswer

        if (!checkAnswersForQuestion){
            lockBnt()
        }
    }

    private fun lockBnt(){

            true_button.isEnabled = false  // Отключаем кнопку
            false_button.isEnabled = false
            // Опционально: меняем внешний вид
            true_button.alpha = 0.5f      // Полупрозрачность
            false_button.alpha = 0.5f

    }
    private fun checkCompleteAnswers(correctCheckAnswer: Int,uncorrectCheckAnswer : Int){
        if (correctCheckAnswer + uncorrectCheckAnswer >= questionBank.size){
            MaterialAlertDialogBuilder(this)
                .setTitle("Вы ответили на все вопросы!")
                .setMessage("Ваш результат $correctCheckAnswer верных из ${questionBank.size} возможных")
                .setPositiveButton("ОК") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

    }
    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = questionBank[currentIndex].answer

        val messageResId = if (userAnswer == correctAnswer){
            correctCheckAnswer ++
            questionBank[currentIndex].checkAnswer = false
            R.string.incorrect_toast
        }
        else{
            uncorrectCheckAnswer ++
            questionBank[currentIndex].checkAnswer = false
            R.string.uncorrect_toast
        }

        Toast.makeText(this,messageResId, Toast.LENGTH_SHORT).show()
    }
    private fun updateQuestion(){
        val questionTextResId = questionBank [currentIndex].textResId
        question_text.setText(questionTextResId)
    }
    private fun nextAnswer(){
        if (currentIndex + 1 < questionBank.size){
            currentIndex++
            updateQuestion()
        }
        if (currentIndex + 1 >= questionBank.size){
            Toast.makeText(this,
                R.string.finish,
                Toast.LENGTH_SHORT).show()
        }
        Log.i("index","$currentIndex , ${questionBank.size}")
    }
    private fun beforeAnswer(){
        if (currentIndex > 0){
            currentIndex --
            updateQuestion()
            Log.i("-index", "$currentIndex , ${questionBank.size}")
        }
        if (currentIndex == 0){
            Log.i("index", "$currentIndex , ${questionBank.size}")
            Toast.makeText(this,
                R.string.first_question,
                Toast.LENGTH_SHORT).show()
        }
        Log.i("index","$currentIndex , ${questionBank.size}")
    }
    }
