package com.grupo1.medicapp

import android.content.Intent
import android.os.Bundle

import android.widget.Button
import android.widget.TextView

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BienvenidoActivity : AppCompatActivity() {

    private lateinit var btnBuscarProducto:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bienvenido)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        asignarReferencias()
    }

    fun asignarReferencias(){
        val nombreUsuario = intent.getStringExtra("nombreUsuario")
        val textViewNombreUsuario = findViewById<TextView>(R.id.textViewNombreUsuario)
        textViewNombreUsuario.text = "Bienvenido, $nombreUsuario"
        
        btnBuscarProducto = findViewById(R.id.btnBuscarProducto)
        btnBuscarProducto.setOnClickListener{
            val intent = Intent(this,ProductosActivity::class.java)
            startActivity(intent)
        }
    }
}