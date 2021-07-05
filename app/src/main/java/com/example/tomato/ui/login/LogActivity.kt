package com.example.tomato.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tomato.R
import com.example.tomato.RegisterActivity


class LogActivity : AppCompatActivity() {
    private var etusername: EditText? = null
    private var etpasswords: EditText? = null
    var username = ""
    var passwords = ""
    var MAX = 20
    var USERNAME: Array<String?>? = arrayOfNulls(MAX)
    var PASSWORDS: Array<String?>? = arrayOfNulls(MAX)

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

        val logButton = findViewById<Button>(R.id.log)
        logButton.setOnClickListener {
            username = etusername!!.text.toString() //获取登录界面用户输入
            passwords = etpasswords!!.text.toString()
            val sharedPreferences = getSharedPreferences("space2", MODE_PRIVATE)
            val i = Intent()
            USERNAME = i.getStringArrayExtra("username") //从register获取数组
            PASSWORDS = i.getStringArrayExtra("passwords")
            for (number in 0 until MAX) {
                val un = sharedPreferences.getString(USERNAME!![number], "") //从key中取出value
                val pw = sharedPreferences.getString(PASSWORDS!![number], "")
                if (username == un) { //判断是否与密码匹配
                    if (passwords == pw) {
                        val intent = Intent()
                        setResult(1001, intent) //匹配成功发送resultCode
                        finish()
                    } else {
                        Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "该账号没有被注册", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}