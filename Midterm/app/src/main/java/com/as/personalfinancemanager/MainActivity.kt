package com.`as`.personalfinancemanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // FragmentA-ს ჩატვირთვა მხოლოდ პირველი შექმნის დროს
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.as_input_fragment_container, FragmentA())
                .commit()
        }
    }
}