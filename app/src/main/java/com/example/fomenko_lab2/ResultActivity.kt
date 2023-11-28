package com.example.fomenko_lab2

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.DatabaseReference

class ResultActivity : AppCompatActivity() {

    private lateinit var usernameTextView: TextView
    private lateinit var scoreTextView: TextView
    private lateinit var allScoresTextView: TextView
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var exitButton:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        exitButton = findViewById(R.id.exitButton)
        usernameTextView = findViewById(R.id.usernameTextView)
        scoreTextView = findViewById(R.id.scoreTextView)
        allScoresTextView = findViewById(R.id.allScoresTextView)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("user_scores")

        val username = intent.getStringExtra("username") ?: ""
        val score = intent.getIntExtra("score", 0)

        usernameTextView.text = "Username: $username"
        scoreTextView.text = "Score: $score"

        displayAllScores()

        exitButton.setOnClickListener {
            finish()
        }
    }

    private fun displayAllScores() {
        val allScoresListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val stringBuilder = StringBuilder()
                for (data in snapshot.children) {
                    val username = data.child("username").value.toString()
                    val score = data.child("score").value.toString()
                    stringBuilder.append("Username: $username - Score: $score\n")
                }
                allScoresTextView.text = stringBuilder.toString()
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        databaseReference.addListenerForSingleValueEvent(allScoresListener)
    }
}
