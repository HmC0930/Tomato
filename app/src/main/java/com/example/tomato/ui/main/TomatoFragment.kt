package com.example.tomato.ui.main

import android.content.Intent
import android.os.*
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.lifecycle.ViewModelProvider
import com.example.tomato.R
import com.example.tomato.ui.data.DataActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_tomato.*
import java.text.SimpleDateFormat

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class TomatoFragment : Fragment() {
    //记录状态的变量
    var myBoolean = true

    companion object {
        lateinit var handler: Handler
    }

    lateinit var mainViewModel: MainViewModel
    var timer1: CountDownTimer? = null
    var timer2: CountDownTimer? = null
    var timer3: CountDownTimer? = null


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // Inflate the layout for this fragment
        mainViewModel.workTime = activity?.intent?.getIntExtra("workTime",
                25 * 60 * 1000)!!
        mainViewModel.restTime = activity?.intent?.getIntExtra("restTime",
                5 * 60 * 1000)!!
        mainViewModel.userID = activity?.intent?.getStringExtra("user").toString()
        return inflater.inflate(R.layout.fragment_tomato, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val numberOfTomatoes = view.findViewById<TextView>(R.id.number_of_tasks)
        numberOfTomatoes.text = mainViewModel.numberOfTomatoes.toString()

        val timeTextView = view.findViewById<TextView>(R.id.time_text_view)
        timeTextView.text = formatTime(mainViewModel.workTime)

        val statusTextView = view.findViewById<TextView>(R.id.status_text_view)

        val dataButton = view.findViewById<Button>(R.id.data_button)
        dataButton.setOnClickListener {
            val intent = Intent(context, DataActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context?.startActivity(intent)
        }

        val toggleButton: ToggleButton = view.findViewById(R.id.toggle_button)
        toggleButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                myBoolean = true
                activity?.findViewById<FloatingActionButton>(R.id.fab)?.hide()
                //前三个番茄钟周期，也就是三个工作时间三个休息时间
                for (i in 1..3) {
                    statusTextView.text = "番茄钟已开启"
                    timer1 = object : CountDownTimer(mainViewModel.workTime.toLong(), 1000) {

                        override fun onTick(millisUntilFinished: Long) {
                            timeTextView.text = formatTime(millisUntilFinished)
                            if (!myBoolean) {
                                cancel()
                                time_text_view.text = formatTime(mainViewModel.workTime.toLong())
                            }
                        }

                        override fun onFinish() {
                            Toast.makeText(context, "开始休息", Toast.LENGTH_SHORT).show()
                            statusTextView.text = "休息中"
                            timer2 = object : CountDownTimer(mainViewModel.restTime.toLong(), 1000) {

                                override fun onTick(millisUntilFinished: Long) {
                                    timeTextView.text = formatTime(millisUntilFinished)
                                    if (!myBoolean) {
                                        cancel()
                                        Toast.makeText(context, "继续", Toast.LENGTH_SHORT).show()
                                        time_text_view.text = formatTime(mainViewModel.workTime.toLong())
                                    }
                                }

                                override fun onFinish() {
                                }
                            }.start()
                        }
                    }.start()
                    //最后一次工作时间
                    timer3 = object : CountDownTimer(mainViewModel.workTime.toLong(), 1000) {

                        override fun onTick(millisUntilFinished: Long) {
                            timeTextView.text = formatTime(millisUntilFinished)
                            if (!myBoolean) {
                                cancel()
                                Toast.makeText(context, "计时结束", Toast.LENGTH_SHORT).show()
                                time_text_view.text = formatTime(mainViewModel.workTime.toLong())
                            }
                        }

                        override fun onFinish() {

                            //进行此次数据存储
                            val taskName = activity?.intent?.getStringExtra("taskName")
                        }
                    }.start()

                }
            } else {

                activity?.findViewById<FloatingActionButton>(R.id.fab)?.show()
                time_text_view.text = formatTime(mainViewModel.workTime.toLong())
                myBoolean = false
                statusTextView.text = ""
            }
        }
    }
    fun formatTime(time: Int): String? {
        val dateFormat = SimpleDateFormat("mm:ss")
        return dateFormat.format(time)
    }

    fun formatTime(time: Long): String? {
        val dateFormat = SimpleDateFormat("mm:ss")
        return dateFormat.format(time)
    }
}







