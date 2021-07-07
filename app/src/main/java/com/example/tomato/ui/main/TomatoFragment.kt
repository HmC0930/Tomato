package com.example.tomato.ui.main

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.os.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.annotation.RequiresApi
import androidx.core.animation.doOnCancel
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tomato.R
import com.example.tomato.TomatoClockApplication
import com.example.tomato.Utils
import com.example.tomato.data.taskdata.Task
import com.example.tomato.ui.data.DataActivity
import com.example.tomato.ui.login.LogActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_set.*
import kotlinx.android.synthetic.main.fragment_tomato.*
import java.text.SimpleDateFormat
import kotlin.concurrent.thread

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
    var task: Task? = Task("番茄钟","未定义类型", 25, 5,
        TomatoClockApplication.currentUser, Utils.READY)


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // Inflate the layout for this fragment
        mainViewModel.workTime = activity?.intent?.getIntExtra(
            "workTime",
            25 * 60 * 1000
        )!!
        mainViewModel.restTime = activity?.intent?.getIntExtra(
            "restTime",
            5 * 60 * 1000
        )!!
        mainViewModel.userID = activity?.intent?.getStringExtra("user").toString()
        task = activity?.intent?.getSerializableExtra("task") as Task?
        return inflater.inflate(R.layout.fragment_tomato, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //显示用户已获得番茄数目
        val numberOfTomatoes = view.findViewById<TextView>(R.id.number_of_tasks)
        var number = 0
        thread {
            val msg = Message()
            number =
                TomatoClockApplication.taskDao.queryById(TomatoClockApplication.currentUser).size
            msg.what = 9
        }
        numberOfTomatoes.text = number.toString()

        handler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                when (msg.what) {
                    9 -> {
                        numberOfTomatoes.text = number.toString()
                    }
                }
            }
        }

        val timeTextView = view.findViewById<TextView>(R.id.time_text_view)
        timeTextView.text = formatTime(mainViewModel.workTime)

        val statusTextView = view.findViewById<TextView>(R.id.status_text_view)

        val dataButton = view.findViewById<Button>(R.id.data_button)
        dataButton.setOnClickListener {
            val intent = Intent(context, DataActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context?.startActivity(intent)
        }

        //加载动画
        val countDownView: CountDownView = view.findViewById(R.id.count_down_view)
        var valueAnimator: ValueAnimator =
            ObjectAnimator.ofFloat(0f, mainViewModel.restTime.toFloat())

        val toggleButton: ToggleButton = view.findViewById(R.id.toggle_button)
        toggleButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                myBoolean = true
                activity?.findViewById<FloatingActionButton>(R.id.fab)?.hide()
                val msg = Message()
                //前三个番茄钟周期，也就是三个工作时间三个休息时间
                for (i in 1..3) {
                    statusTextView.text = "番茄钟已开启"
                    countDownView.setTotalTime(mainViewModel.workTime)

                    //工作计时
                    timer1 = object : CountDownTimer(mainViewModel.workTime.toLong(), 1000) {

                        override fun onTick(millisUntilFinished: Long) {
                            timeTextView.text = formatTime(millisUntilFinished)
                            if (!myBoolean) {
                                cancel()
                                valueAnimator.end()
                                time_text_view.text = formatTime(mainViewModel.workTime.toLong())
                            }
                        }

                        override fun onFinish() {
                            Toast.makeText(context, "开始休息", Toast.LENGTH_SHORT).show()
                            statusTextView.text = "休息中"

                            //休息计时
                            timer2 =
                                object : CountDownTimer(mainViewModel.restTime.toLong(), 1000) {

                                    override fun onTick(millisUntilFinished: Long) {
                                        timeTextView.text = formatTime(millisUntilFinished)
                                        if (!myBoolean) {
                                            cancel()
                                            valueAnimator.end()
                                            Toast.makeText(context, "继续", Toast.LENGTH_SHORT).show()
                                            time_text_view.text =
                                                formatTime(mainViewModel.workTime.toLong())
                                        }
                                    }

                                    override fun onFinish() {
                                    }
                                }.start()
                            // 设置属性动画
                            valueAnimator =
                                ObjectAnimator.ofFloat(0f, mainViewModel.restTime.toFloat())
                            valueAnimator.interpolator = LinearInterpolator() // 线性插值器，匀速变化

                            valueAnimator.duration = mainViewModel.restTime.toLong() // 执行时间

                            valueAnimator.addUpdateListener { animation ->
                                val currentTime = animation.animatedValue as Float
                                countDownView.setCurrentTime(currentTime.toInt())
                            }
                            valueAnimator.start()
                        }
                    }.start()
                    // 设置属性动画
                    valueAnimator =
                        ObjectAnimator.ofFloat(0f, mainViewModel.workTime.toFloat())
                    valueAnimator.interpolator = LinearInterpolator() // 线性插值器，匀速变化

                    valueAnimator.duration = mainViewModel.workTime.toLong() // 执行时间

                    valueAnimator.addUpdateListener { animation ->
                        val currentTime = animation.animatedValue as Float
                        countDownView.setCurrentTime(currentTime.toInt())
                    }
                    valueAnimator.start()
                    //最后一次工作时间
                    timer3 = object : CountDownTimer(mainViewModel.workTime.toLong(), 1000) {

                        override fun onTick(millisUntilFinished: Long) {
                            timeTextView.text = formatTime(millisUntilFinished)
                            if (!myBoolean) {
                                cancel()
                                valueAnimator.end()
                                Toast.makeText(context, "计时结束", Toast.LENGTH_SHORT).show()
                                time_text_view.text = formatTime(mainViewModel.workTime.toLong())
                            }
                        }

                        override fun onFinish() {

                            //进行此次数据存储
                            val taskName = activity?.intent?.getStringExtra("taskName")
                        }
                    }.start()
                    valueAnimator =
                        ObjectAnimator.ofFloat(0f, mainViewModel.workTime.toFloat())
                    valueAnimator.interpolator = LinearInterpolator() // 线性插值器，匀速变化

                    valueAnimator.duration = mainViewModel.workTime.toLong() // 执行时间

                    valueAnimator.addUpdateListener { animation ->
                        val currentTime = animation.animatedValue as Float
                        countDownView.setCurrentTime(currentTime.toInt())
                    }
                    valueAnimator.start()
                    valueAnimator.doOnEnd {
                        thread {
                            task?.let { it1 -> TomatoClockApplication.taskDao.insert(it1) }
                        }
                    }
                }
            } else {

                activity?.findViewById<FloatingActionButton>(R.id.fab)?.show()
                time_text_view.text = formatTime(mainViewModel.workTime.toLong())
                valueAnimator.pause()
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







