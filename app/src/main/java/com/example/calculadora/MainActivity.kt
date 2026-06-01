package com.example.calculadora

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState == null) {
            trocarFragment(CalculadoraFragment())
        }

        findViewById<ImageButton>(R.id.bixosabido).setOnClickListener {
            trocarFragment(CalculadoraFragment())
        }

        findViewById<ImageButton>(R.id.medidatomada).setOnClickListener {
            trocarFragment(ConversoresFragment())
        }

        findViewById<ImageButton>(R.id.pepecoin).setOnClickListener {
            trocarFragment(MoedasFragment())
        }
    }

    private fun trocarFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}