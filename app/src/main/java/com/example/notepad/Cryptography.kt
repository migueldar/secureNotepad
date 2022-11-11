package com.example.notepad

import android.util.Log
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec


fun encryptString(str : String?, uncypheredPassword: String?): String {
    Log.d("MENSAJE DE MIGUEL", "En funcion de cifrado")
    val hashedPassword = MessageDigest.getInstance("SHA-256").digest(uncypheredPassword?.toByteArray())
    val key : SecretKey = SecretKeySpec(hashedPassword, "AES")
    val cipherer = Cipher.getInstance("AES")
    cipherer.init(Cipher.ENCRYPT_MODE, key)
    val ciphertext: ByteArray = cipherer.doFinal(str?.toByteArray())

    return android.util.Base64.encodeToString(ciphertext, android.util.Base64.DEFAULT)
}

fun decryptString(str : String?, uncypheredPassword: String?) : String {
    Log.d("MENSAJE DE MIGUEL", "En funcion de descifrado")
    Log.d("MENSAJE DE MIGUEL", uncypheredPassword!!)
    val hashedPassword = MessageDigest.getInstance("SHA-256").digest(uncypheredPassword?.toByteArray())
    val key: SecretKey = SecretKeySpec(hashedPassword, "AES")
    val decipherer = Cipher.getInstance("AES")
    decipherer.init(Cipher.DECRYPT_MODE, key)

    val value = android.util.Base64.decode(str?.toByteArray(), android.util.Base64.DEFAULT)
    Log.d("MENSAJE DE MIGUEL", value.size.toString())
    val ciphertext: ByteArray = decipherer.doFinal(value)

    return ciphertext.decodeToString()
}

fun encryptPassword(uncypheredPassword: String) : String {
    val factory = SecretKeyFactory.getInstance("PBKDF2withHmacSHA1")
    val funcion = PBEKeySpec(uncypheredPassword.toCharArray(), uncypheredPassword.toByteArray(), 2048, 50)
    val key = factory.generateSecret(funcion)
    return android.util.Base64.encodeToString(key.encoded.decodeToString().toByteArray(), android.util.Base64.DEFAULT)
}