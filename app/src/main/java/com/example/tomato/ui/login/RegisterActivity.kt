package com.example.tomato.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tomato.R
import com.example.tomato.TomatoClockApplication
import com.example.tomato.data.userdata.User
import com.example.tomato.ui.set.SetActivity
import kotlinx.android.synthetic.main.activity_register.*
import kotlin.concurrent.thread

class RegisterActivity : AppCompatActivity() {
    private var etusername: EditText? = null
    private var etpasswords: EditText? = null
    private var etcheck: EditText? = null
    var username = ""
    var passwords = ""
    var check = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        etusername = findViewById(R.id.et_username_register)
        etpasswords = findViewById(R.id.et_passwords_register)
        etcheck = findViewById(R.id.et_check_register)


        var foundUser: User = User("id", "pswd")
        thread {
            foundUser = TomatoClockApplication.userDao.queryById(username)
        }

        val intent = Intent(this, LogActivity::class.java)

        button_true_register.setOnClickListener {
            username = etusername!!.editableText.toString() //获取登录界面用户输入
            passwords = etpasswords!!.editableText.toString()
            check = etcheck!!.text.toString()

            thread {
                foundUser = TomatoClockApplication.userDao.queryById(username)
            }

            if (username == "" || passwords == "" || check == ""){
                Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show()
            } else {
                if (passwords != check ){
                    Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show()
                }else{
                    if (foundUser == User(username, passwords)){
                        Toast.makeText(this, "用户名已存在", Toast.LENGTH_SHORT).show()
                    }else{
                        val user = User (username, passwords)
                        thread {
                            TomatoClockApplication.userDao.insert(user)
                        }
                        Toast.makeText(this, "注册成功，返回登录界面", Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                    }
                }
            }

//            if (username == "" || passwords == "") {
//                Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show()
//            } else {
//                if (foundUser.id == username) {
//                    Toast.makeText(this, "用户名已存在", Toast.LENGTH_SHORT).show()
//                } else {
//                    if (passwords != check) {
//                        Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show()
//                    } else {
//                        val user = User(username, passwords)
//                        thread {
//                            TomatoClockApplication.userDao.insert(user)
//                        }
//                        Toast.makeText(this, "注册成功，返回登录界面", Toast.LENGTH_SHORT).show()
//                        startActivity(intent)
//                    }
//                }
//            }
        }

        button_false_register.setOnClickListener {
            startActivity(intent)
        }
    }
}