package com.example.tomato

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_set.*

class SetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set)

        button_true.setOnClickListener {
            val workTime = Integer.parseInt(work_time.editableText.toString())
            val restTime = Integer.parseInt(rest_time.editableText.toString())
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("workTime", workTime)
            intent.putExtra("restTime", restTime)
            startActivity(intent)
        }
        button_false.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}