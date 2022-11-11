package com.example.notepad

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.notepad.databinding.NoteHolderBinding

//TODO add back button, add IV and salt
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
        Log.d("result", "jjejejej")
        uncypheredPassword = data?.getStringExtra("pass")
        Log.d("Contraseña no cifrada", uncypheredPassword!!)
        if (firstTime) {
            val title = sharedPref.getString("title", null)
            val note = sharedPref.getString("note", null)

            Log.d("MENSAJE DE MIGUEL", "DESCIFRANDO")
            if (title != null)
                binding.etTitle.setText(decryptString(title, uncypheredPassword))
            if (note != null)
                binding.etNote.setText(decryptString(note, uncypheredPassword))

            setContentView(binding.root)
            firstTime = false
        }
    }

    override fun onPause() {
        super.onPause()
        if (uncypheredPassword != null) {
            Log.d("MENSAJE DE MIGUEL", "CIFRANDO")
            val sharedPref = getSharedPreferences("everything", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("title", encryptString(binding.etTitle.text.toString(), uncypheredPassword))
                putString("note", encryptString(binding.etNote.text.toString(), uncypheredPassword))
                apply()
            }
        }
    }
}