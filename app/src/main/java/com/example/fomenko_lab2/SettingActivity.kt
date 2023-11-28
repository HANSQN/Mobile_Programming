package com.example.fomenko_lab2

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate

class SettingActivity : AppCompatActivity() {

    companion object {
        const val THEME_PREFS = "theme_prefs"
        const val THEME_KEY = "theme_key"
        const val LIGHT_THEME = 0
        const val DARK_THEME = 1
    }

    private lateinit var saveButton: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences(THEME_PREFS, Context.MODE_PRIVATE)
        val currentTheme = sharedPreferences.getInt(THEME_KEY, LIGHT_THEME)

        if (currentTheme == LIGHT_THEME) {
            setTheme(R.style.LightTheme)
        } else {
            setTheme(R.style.DarkTheme)
        }

        setContentView(R.layout.activity_setting)
        supportActionBar?.title = "Settings"

        saveButton = findViewById(R.id.exitButton)
        val radioGroup = findViewById<RadioGroup>(R.id.themeRadioGroup)
        val radioButtonLight = findViewById<RadioButton>(R.id.lightThemeRadioButton)
        val radioButtonDark = findViewById<RadioButton>(R.id.darkThemeRadioButton)

        if (currentTheme == LIGHT_THEME) {
            radioButtonLight.isChecked = true
        } else {
            radioButtonDark.isChecked = true
        }

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val editor = sharedPreferences.edit()
            when (checkedId) {
                R.id.lightThemeRadioButton -> {
                    editor.putInt(THEME_KEY, LIGHT_THEME)
                    editor.apply()
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    recreate()
                }
                R.id.darkThemeRadioButton -> {
                    editor.putInt(THEME_KEY, DARK_THEME)
                    editor.apply()
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    recreate()
                }
            }
        }
        saveButton.setOnClickListener {
            Toast.makeText(this@SettingActivity, "Save Successful!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}