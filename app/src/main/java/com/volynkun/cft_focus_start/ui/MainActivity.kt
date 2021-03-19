package com.volynkun.cft_focus_start.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.volynkun.cft_focus_start.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}