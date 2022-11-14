package com.example.notepad

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Base64
import androidx.appcompat.app.AppCompatActivity
import com.example.notepad.databinding.NoteHolderBinding
import kotlin.random.Random

class NoteHolder : AppCompatActivity() {

    private lateinit var binding: NoteHolderBinding
    private var uncypheredPassword : String? = null
    private lateinit var sharedPref : SharedPreferences
    private var firstTime = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = getSharedPreferences("everything", Context.MODE_PRIVATE)

        val password = sharedPref.getString("password", null)
        var intent : Intent
        if (password == null)
            intent = Intent(this, ChangePassword::class.java)
        else
            intent = Intent(this, Login::class.java)

        binding = NoteHolderBinding.inflate(layoutInflater)
        startActivityForResult(intent, 0)

        binding.butGoToChangePassword.setOnClickListener {
            intent = Intent(this, ChangePassword::class.java)
            startActivityForResult(intent, 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        uncypheredPassword = data?.getStringExtra("pass")
        if (firstTime) {
            val title = sharedPref.getString("title", null)
            val note = sharedPref.getString("note", null)
            val salt = sharedPref.getString("saltAES", null)

            if (title != null)
                binding.etTitle.setText(decryptString(title, uncypheredPassword, salt!!))
            if (note != null)
                binding.etNote.setText(decryptString(note, uncypheredPassword, salt!!))

            setContentView(binding.root)
            firstTime = false
        }
    }

    override fun onPause() {
        super.onPause()
        if (uncypheredPassword != null) {
            val sharedPref = getSharedPreferences("everything", Context.MODE_PRIVATE)
            val salt = Base64.encodeToString(Random.nextBytes(10), Base64.DEFAULT).trim()
            with(sharedPref.edit()) {
                putString("saltAES", salt)
                putString("title", encryptString(binding.etTitle.text.toString(), uncypheredPassword, salt))
                putString("note", encryptString(binding.etNote.text.toString(), uncypheredPassword, salt))
                apply()
            }
        }
    }
}