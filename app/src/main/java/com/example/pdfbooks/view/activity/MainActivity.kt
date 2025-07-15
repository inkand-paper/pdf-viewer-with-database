package com.example.pdfbooks.view.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.pdfbooks.view.fragment.Add
import com.example.pdfbooks.R
import com.example.pdfbooks.database.PdfDatabase
import com.example.pdfbooks.databinding.ActivityMainBinding
import com.example.pdfbooks.view.fragment.Home

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PdfDatabase.getInstance(this)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(R.id.FrameLayout, Home()).commit()

        binding.BottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.Home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.FrameLayout, Home()).commit()
                    true
                }
                R.id.Add -> {
                    supportFragmentManager.beginTransaction().replace(R.id.FrameLayout, Add()).commit()
                    true
                }
                else -> false
            }

        }
    }
}