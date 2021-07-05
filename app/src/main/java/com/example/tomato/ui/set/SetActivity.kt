package com.example.tomato.ui.set

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tomato.R
import com.example.tomato.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_set.*

class SetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set)

        button_true.setOnClickListener {
            //设置番茄钟的工作、休息时长
            val taskName = task_name.editableText.toString()
            val taskType = task_type.editableText.toString()
            val workTime = Integer.parseInt(work_time.editableText.toString())*60*1000 //时间从分钟换成毫秒
            val restTime = Integer.parseInt(rest_time.editableText.toString())*60*1000
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("workTime", workTime)
            intent.putExtra("restTime", restTime)
            intent.putExtra("taskName", taskName)
            intent.putExtra("taskType", taskType)
            startActivity(intent)



        }
        button_false.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}