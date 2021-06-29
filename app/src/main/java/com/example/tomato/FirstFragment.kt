package com.example.tomato

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    companion object{
        lateinit var handler: Handler
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val timeTextView = view.findViewById<TextView>(R.id.time_text_view)

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            val intent = Intent(context, SetActivity::class.java)
            context?.startActivity(intent)
        }

    }

    //开始番茄钟时调用
    fun startWorking(){
        val task = MyTimerTask()
        Timer().schedule(task, 1000, 1000)
    }


    fun formatTime(time: Int): String? {
        val dateFormat = SimpleDateFormat("mm:ss")
        return dateFormat.format(time)
    }


    inner class MyTimerTask() : TimerTask() {
        override fun run() {
                val msg = Message()
                msg.what = updateText
                handler.sendMessage(msg)
        }
    }
    val updateText = 0
}