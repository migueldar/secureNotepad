package com.example.notepad

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.notepad.databinding.LoginBinding

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = getSharedPreferences("everything", Context.MODE_PRIVATE)
        val password : String? = sharedPref.getString("password", null)
        val salt : String? = sharedPref.getString("saltPBKDF", null)

        val binding : LoginBinding = LoginBinding.inflate(layoutInflater)
        binding.butLogIn.setOnClickListener {
            validate(binding.etPassword.text.toString(), salt!!, password!!)
            binding.tvErrorMsgLogin.text = "Wrong password, try again"
        }
        setContentView(binding.root)
    }

    private fun validate(input : String, salt : String, password : String) {
        if (input == "")
            return
        if (encryptPassword(input, salt) == password) {
            val ret = Intent()
            ret.putExtra("pass", input)
            setResult(0, ret)
            finish()
        }
    }
}