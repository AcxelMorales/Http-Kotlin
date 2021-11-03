package com.acxelmorales.http

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log

import android.widget.Button
import android.widget.Toast
import java.io.IOException

import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnRed = findViewById<Button>(R.id.btnValidateRed)
        btnRed.setOnClickListener {
            if (Network.networkExist(this)) {
                Toast.makeText(this, "Tenemos red", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "No tenemos red", Toast.LENGTH_LONG).show()
            }
        }

        val btnHttpHandler = findViewById<Button>(R.id.btnHttpHandler)
        btnHttpHandler.setOnClickListener {
            if (Network.networkExist(this)) {
                Log.d("HANDLE", this.downloadData("https://www.google.com/"))
            } else {
                Toast.makeText(this, "No tenemos red", Toast.LENGTH_LONG).show()
            }
        }
    }

    @Throws(IOException::class)
    private fun downloadData(url: String): String {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        var inputStream: InputStream? = null

        try {
            val url = URL(url)
            val connection = url.openConnection() as HttpURLConnection

            connection.requestMethod = "GET"
            connection.connect()

            inputStream = connection.inputStream
            return  inputStream.bufferedReader().use {
                it.readText()
            }
        } finally {
            if (inputStream != null) inputStream?.close()
        }
    }

}
