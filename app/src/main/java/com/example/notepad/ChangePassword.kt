package com.example.notepad

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Base64
import androidx.appcompat.app.AppCompatActivity
import com.example.notepad.databinding.ChangePasswordBinding
import kotlin.random.Random

class ChangePassword  : AppCompatActivity() {

    private lateinit var binding: ChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ChangePasswordBinding.inflate(layoutInflater)
        binding.butChangePasswd.setOnClickListener {
            if (binding.password1.text.toString() == binding.password2.text.toString()) {
                if (binding.password1.text.toString().length > 11)
                    changePasswd(binding.password1.text.toString())
                else
                    binding.tvErrorMsgChange.text = "Your password must be at least 12 characters long"
            }
            else
                binding.tvErrorMsgChange.text = "The passwords don't match"
        }
        setContentView(binding.root)
    }

    private fun changePasswd(newPasswd : String) {
        val sharedPref = getSharedPreferences("everything", Context.MODE_PRIVATE)
        val salt = Base64.encodeToString(Random.nextBytes(10), Base64.DEFAULT).trim()
        with (sharedPref.edit()) {
            putString("saltPBKDF", salt)
            putString("password", encryptPassword(newPasswd, salt))
            apply()
        }
        val ret = Intent()
        ret.putExtra("pass", newPasswd)
        setResult(0, ret)
        finish()
    }
}