package com.example.notepad

import android.util.Base64
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

fun encryptString(str : String?, uncypheredPassword: String?, salt : String): String {
    val hashedPassword = MessageDigest.getInstance("SHA-256").digest((uncypheredPassword + salt).toByteArray())
    val key : SecretKey = SecretKeySpec(hashedPassword, "AES")
    val cipherer = Cipher.getInstance("AES")
    cipherer.init(Cipher.ENCRYPT_MODE, key)
    val ciphertext: ByteArray = cipherer.doFinal(str?.toByteArray())

    return Base64.encodeToString(ciphertext, Base64.DEFAULT)
}

fun decryptString(str : String?, uncypheredPassword: String?, salt : String) : String {
    val hashedPassword = MessageDigest.getInstance("SHA-256").digest((uncypheredPassword + salt).toByteArray())
    val key: SecretKey = SecretKeySpec(hashedPassword, "AES")
    val decipherer = Cipher.getInstance("AES")
    decipherer.init(Cipher.DECRYPT_MODE, key)
    val value = Base64.decode(str?.toByteArray(), Base64.DEFAULT)
    val ciphertext: ByteArray = decipherer.doFinal(value)

    return ciphertext.decodeToString()
}

fun encryptPassword(uncypheredPassword: String, salt : String) : String {
    val factory = SecretKeyFactory.getInstance("PBKDF2withHmacSHA1")
    val keySp = PBEKeySpec(uncypheredPassword.toCharArray(), salt.toByteArray(), 2048, 200)
    val key = factory.generateSecret(keySp)

    return Base64.encodeToString(key.encoded.decodeToString().toByteArray(), Base64.DEFAULT).trim()
}