package com.example.notepad

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Log.DEBUG
import androidx.appcompat.app.AppCompatActivity
import com.example.notepad.databinding.LoginBinding

class Login : AppCompatActivity() {

    private lateinit var binding: LoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = getSharedPreferences("everything", Context.MODE_PRIVATE)
        val password : String? = sharedPref.getString("password", null)

        binding = LoginBinding.inflate(layoutInflater)
        binding.butLogIn.setOnClickListener {
            validate(binding.etPassword.text.toString(), password)
            binding.tvErrorMsgLogin.text = "Wrong password, try again"
        }
        setContentView(binding.root)
    }

    private fun validate(input : String, password : String?) {
        Log.d("hola", password!!)
        Log.d("adios", encryptPassword(input!!))
        if (encryptPassword(input) == password) {
            var ret = Intent()
            ret.putExtra("pass", password)
            setResult(0, ret)
            finish()
        }
    }
}