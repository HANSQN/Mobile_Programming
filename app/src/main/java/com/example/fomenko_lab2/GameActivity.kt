package com.example.fomenko_lab2

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class GameActivity : AppCompatActivity() {

    private lateinit var clickButton: Button
    private lateinit var scoreTextView: TextView
    private lateinit var timerTextView: TextView
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private var score = 0
    private val GAME_DURATION: Long = 10000 // 10 seconds in milliseconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        clickButton = findViewById(R.id.clickButton)
        scoreTextView = findViewById(R.id.scoreTextView)
        timerTextView = findViewById(R.id.timerTextView)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("user_scores")

        clickButton.setOnClickListener {
            score++
            scoreTextView.text = "Score: $score"
        }

        object : CountDownTimer(GAME_DURATION, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = millisUntilFinished / 1000
                timerTextView.text = "Time left: $secondsLeft  seconds"
            }

            override fun onFinish() {
                saveUserScore()
                navigateToResultActivity()
            }
        }.start()
    }

    private fun saveUserScore() {
        val currentUser = firebaseAuth.currentUser
        currentUser?.let {
            val userName = currentUser.email ?: ""
            val userScoreMap = HashMap<String, Any>()
            userScoreMap["username"] = userName
            userScoreMap["score"] = score
            database.push().setValue(userScoreMap)
        }
    }

    private fun navigateToResultActivity() {
        val currentUser = firebaseAuth.currentUser
        val userName = currentUser?.email ?: ""

        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("username", userName)
        intent.putExtra("score", score)
        startActivity(intent)
        finish()
    }
}
