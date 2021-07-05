package com.example.tomato.ui.data

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tomato.R
import com.example.tomato.data.taskdata.Task
import kotlinx.android.synthetic.main.activity_data.*

class DataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)

        //test
//        val datas = listOf<Task>(
//            Task("张三", "学习", 40,10),
//            Task("李四", "学习", 30,10),
//            Task("张五", "学习", 25,10)
//        )
//      val adapter = DataAdapter(this, R.layout.data_item, datas)
//      data_list_view.adapter = adapter

    }

}