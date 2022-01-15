package com.room

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        val tombol = findViewById<ImageView>(R.id.viewxi)
        tombol.setOnClickListener {
            val intentges = Intent(this, MainActivity ::class.java)
            startActivity(intentges)

        }
    }
}