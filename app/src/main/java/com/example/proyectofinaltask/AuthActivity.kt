package com.example.proyectofinaltask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : AppCompatActivity() {
    private lateinit var btnRegister: AppCompatButton
    private lateinit var etEmail: TextInputEditText
    private lateinit var edPassword:TextInputEditText
    private lateinit var btnAcceder:AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message","Integracion de Firebase completa")
        analytics.logEvent("InitScreen",bundle)

        setup()
    }

    private fun setup() {
        title = "Autenticacion"
        btnRegister = findViewById(R.id.btnRegister)
        etEmail = findViewById(R.id.etEmail)
        edPassword = findViewById(R.id.etPassword)
        btnAcceder = findViewById(R.id.btnAcceder)
        btnRegister.setOnClickListener {
            val email = etEmail.text.toString()
            val password = edPassword.text.toString()
            if (email.isNotEmpty()  && password.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(etEmail.text.toString(),
                    edPassword.text.toString()).addOnCompleteListener {
                        if (it.isSuccessful){
                            showMenu(it.result?.user?.email ?:"", ProviderType.BASIC)
                        }else{
                            showAlert()
                        }
                }
            }
        }

        btnAcceder.setOnClickListener {
            val email = etEmail.text.toString()
            val password = edPassword.text.toString()
            if (email.isNotEmpty()  && password.isNotEmpty()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(etEmail.text.toString(),
                    edPassword.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful){
                        showMenu(it.result?.user?.email ?:"", ProviderType.BASIC)
                    }else{
                        showAlert()
                    }
                }
            }
        }
    }
    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando el usuario.")
        builder.setPositiveButton("Aceptar",null)
        val dialog:AlertDialog = builder.create()
        dialog.show()
    }
    private fun showMenu(email:String, provider:ProviderType){
        val menuIntent = Intent(this,MenuActivity::class.java).apply {
            putExtra("email",email)
            putExtra("provider",provider.name)
        }
        startActivity(menuIntent)
    }
}