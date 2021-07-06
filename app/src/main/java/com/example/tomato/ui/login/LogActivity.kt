package com.example.tomato.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tomato.R
import com.example.tomato.TomatoClockApplication
import com.example.tomato.data.userdata.User
import com.example.tomato.ui.main.MainActivity
import kotlin.concurrent.thread


class LogActivity : AppCompatActivity() {
    private var etusername: EditText? = null
    private var etpasswords: EditText? = null
    var username = ""
    var passwords = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)
        etusername = findViewById(R.id.et_username)
        etpasswords = findViewById(R.id.et_passwords)


        val registerButton = findViewById<Button>(R.id.register)
        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }


        var foundUser = User("", "")
        val logButton = findViewById<Button>(R.id.log)
        logButton.setOnClickListener {
            username = etusername!!.editableText.toString() //获取登录界面用户输入
            passwords = etpasswords!!.editableText.toString()

            thread {
                var foundUser = TomatoClockApplication.userDao.queryById(username)
            }

            if (username == "" || passwords == "") {
                Toast.makeText(this, "账号或密码不能为空", Toast.LENGTH_SHORT).show()
            } else {
                    if (foundUser.password != passwords) {
                        Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show()
                        TomatoClockApplication.currentUser = username
                        //登录成功，并将用户信息传给MainActivity
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("user", username)
                        startActivity(intent)
                    }
                }

            }

    }


}