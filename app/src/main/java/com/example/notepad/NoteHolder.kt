package com.example.notepad

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.notepad.databinding.NoteHolderBinding

class NoteHolder : AppCompatActivity() {

    private lateinit var binding: NoteHolderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = getSharedPreferences("everything", Context.MODE_PRIVATE)
        val password = sharedPref.getString("password", null)
        var intent : Intent

        if (password == null)
            intent = Intent(this, ChangePassword::class.java)
        else
            intent = Intent(this, Login::class.java)

        startActivity(intent)

        val title = sharedPref.getString("title", null)
        val note = sharedPref.getString("note", null)

        binding = NoteHolderBinding.inflate(layoutInflater)
        binding.etTitle.setText(title)
        binding.etNote.setText(note)
        binding.butGoToChangePassword.setOnClickListener {
            intent = Intent(this, ChangePassword::class.java)
            startActivity(intent)
        }
        setContentView(binding.root)
    }

    override fun onPause() {
        super.onPause()
        val sharedPref = getSharedPreferences("everything", Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putString("title", binding.etTitle.text.toString())
            putString("note", binding.etNote.text.toString())
            apply()
        }
    }
}