package com.acxelmorales.http

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log

import android.widget.Button
import android.widget.Toast

import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import okhttp3.Call

import okhttp3.OkHttpClient
import okhttp3.Response

import org.json.JSONObject

import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.jsonNative()

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

        val btnVolleyHttpHandler = findViewById<Button>(R.id.btnVolley)
        btnVolleyHttpHandler.setOnClickListener {
            if (Network.networkExist(this)) {
                this.httpHandlingVolley("https://reqres.in/api/users")
            } else {
                Toast.makeText(this, "No tenemos red", Toast.LENGTH_LONG).show()
            }
        }

        val btnOkHttp = findViewById<Button>(R.id.btnOkHttp)
        btnOkHttp.setOnClickListener {
            if (Network.networkExist(this)) {
                this.okHttpHandling("https://jsonplaceholder.typicode.com/todos/1")
            } else {
                Toast.makeText(this, "No tenemos red", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun okHttpHandling(url: String) {
        val client = OkHttpClient()
        val request = okhttp3.Request.Builder().url(url).build()

        client.newCall(request).enqueue(object: okhttp3.Callback {
            override fun onFailure(call: Call?, e: IOException?) {}

            override fun onResponse(call: Call?, response: Response?) {
                val result = response?.body()?.string()

                this@MainActivity.runOnUiThread {
                    try {
                        Log.d("OKHTTP", result.toString())
                    } catch (e: Exception) {
                        Log.e("Error", e.message.toString())
                    }
                }
            }
        })
    }

    private fun httpHandlingVolley(url: String) {
        val queue = Volley.newRequestQueue(this)
        val request = StringRequest(Request.Method.GET, url, {
            response ->
            try {
                Log.d("HTTP VOLLEY", response)
            } catch (e: Exception) {
                Log.e("Error", e.message.toString())
            }
        }, {  })

        queue.add(request)
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

    private fun jsonNative() {
        var peopleList: ArrayList<Persona>? = null

        var respuesta = "{ \"personas\" : [ " +
                "{" +
                " \"nombre\" : \"Marcos\" ," +
                " \"pais\" : \"México\" ," +
                " \"estado\" : \"soltero\" ," +
                " \"experiencia\" : 5}," +

                "{" +
                " \"nombre\" : \"Agustín\" ," +
                " \"pais\" : \"España\" ," +
                " \"estado\" : \"casado\" ," +
                " \"experiencia\" : 16}" +
                " ]" +
                " }"
        val json = JSONObject(respuesta)
        val personas = json.getJSONArray("personas")

        peopleList = ArrayList()

        for (i in 0 until personas.length()) {
            val nombre = personas.getJSONObject(i).getString("nombre")
            val pais = personas.getJSONObject(i).getString("pais")
            val estado = personas.getJSONObject(i).getString("estado")
            val exp = personas.getJSONObject(i).getInt("experiencia")

            // Add checkpoint
            peopleList.add(Persona(nombre, pais, estado, exp))
        }
    }

}
