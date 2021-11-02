package com.acxelmorales.http

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Button
import android.widget.Toast

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
    }

}
