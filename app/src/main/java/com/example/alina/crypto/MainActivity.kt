package com.example.alina.crypto

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.TextView


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textViewkey: TextView = findViewById(R.id.editText)

        var btnStart: Button = findViewById(R.id.button)
        btnStart.setOnClickListener {
            Log.d("",textViewkey.text.toString())
            val rc4 = rc4(textViewkey.text.toString().toByteArray(),textViewkey.text.toString().length, this.applicationContext)
            rc4.make()

            val textViewSize: TextView = findViewById(R.id.size)
            textViewSize.setText("Size: "+ rc4.fileSize.toString() +"m");

            val textView1: TextView = findViewById(R.id.java_time)
            textView1.setText("JAVA: "+rc4.wtime.toString()+" ms");
            val textView2: TextView = findViewById(R.id.cpp_time)
            textView2.setText("CPP: "+stringFromJNI(textViewkey.text.toString()).toString()+" ms");

        }
    }

    external fun stringFromJNI(s: String): Double
    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }
}
