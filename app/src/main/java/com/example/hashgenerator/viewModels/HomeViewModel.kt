package com.example.hashgenerator.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import java.security.MessageDigest
import java.security.MessageDigestSpi

class HomeViewModel : ViewModel() {


    // this fun will take the text that i'm warited and convert it into byteArray
    // and will take the algorithm that i'm choosen
    fun getHash(text: String, algorithm: String): String {
        val bytes = MessageDigest.getInstance(algorithm).digest(text.toByteArray())
        return toHex(bytes)
    }

    // create fun to convert byteArray value into hexDecimal
    private fun toHex(byteArray: ByteArray): String {
        // this (separator) to remove any space and comma betwwen digits
//        Log.d("viewModel", byteArray.joinToString("") { "%02x".format(it) })
        return byteArray.joinToString("") { "%02x".format(it) }

    }
}