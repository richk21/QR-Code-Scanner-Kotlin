package com.example.qrcodescanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button

class MainActivity : AppCompatActivity() {
    //where the user selects what they want to do - generate a qr code or scan it.
    private lateinit var scanBtn: Button
    private lateinit var generateBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.requestFeature(Window.FEATURE_ACTION_BAR)
        actionBar?.hide()
        //window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_main)
        scanBtn = findViewById(R.id.scanButton)
        generateBtn = findViewById(R.id.generateButton)

        scanBtn.setOnClickListener{
            val intent = Intent(this, scanqr::class.java)
            startActivity(intent)
        }

        generateBtn.setOnClickListener{
            val intent = Intent(this, genqrcode::class.java)
            startActivity(intent)
        }

    }
}