package com.example.notepad

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.notepad.databinding.ChangePasswordBinding

class ChangePassword  : AppCompatActivity() {

    private lateinit var binding: ChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ChangePasswordBinding.inflate(layoutInflater)
        binding.butChangePasswd.setOnClickListener {
            if (binding.password1.text.toString() == binding.password2.text.toString()) {
                if (binding.password1.text.toString().length > 4)
                    changePasswd(binding.password1.text.toString())
                else
                    binding.tvErrorMsgChange.text = "Your password must be at least 5 characters long"
            }
            else
                binding.tvErrorMsgChange.text = "The passwords don't match"
        }
        setContentView(binding.root)
    }

    private fun changePasswd(newPasswd : String) {
        val sharedPref = getSharedPreferences("everything", Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putString("password", encryptPassword(newPasswd))
            apply()
        }
        var ret = Intent()
        ret.putExtra("pass", newPasswd)
        setResult(0, ret)
        finish()
    }
}