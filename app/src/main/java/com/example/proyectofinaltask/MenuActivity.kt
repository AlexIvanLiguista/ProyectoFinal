package com.example.proyectofinaltask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.FirebaseAuth

class MenuActivity : AppCompatActivity() {

    private lateinit var tvEmail: TextView
    private lateinit var tvProvider: TextView
    private lateinit var btnCloseSesion: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")

        setup(email ?: "", provider ?: "")
    }

    private fun setup(email: String, provider: String) {
        title = "Menu de Inicio"
        tvEmail = findViewById(R.id.tvEmail)
        tvProvider = findViewById(R.id.tvProvider)
        btnCloseSesion = findViewById(R.id.btnCloseSesion)

        tvEmail.text = email
        tvProvider.text = provider

        btnCloseSesion.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
    }
}

enum class ProviderType {
    BASIC
}
