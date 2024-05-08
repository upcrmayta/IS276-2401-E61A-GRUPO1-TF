package com.grupo1.medicapp

import android.content.Intent
import android.os.Bundle

import android.widget.Button
import android.widget.TextView

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.grupo1.medicapp.entidades.Usuario
import com.grupo1.medicapp.modelo.UsuarioDAO

class BienvenidoActivity : AppCompatActivity() {

    private lateinit var btn_Actualizar_Datos: Button
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

        val cargaUsuario = nombreUsuario.toString()
        loadUsuario(cargaUsuario)
    }

    fun loadUsuario(datosusuario:String) {
        btn_Actualizar_Datos = findViewById(R.id.Actualizar_Datos)
        var datosUsuario = Usuario()
        val usuarioDAO = UsuarioDAO(this)
        datosUsuario = usuarioDAO.obtenerUsuario(datosusuario)

        btn_Actualizar_Datos.setOnClickListener{
            val intent = Intent (this,RegistroActivity::class.java)
            intent.putExtra("var_id",datosUsuario.id)
            intent.putExtra("var_usuario",datosUsuario.username)
            intent.putExtra("var_nombres",datosUsuario.nombres)
            intent.putExtra("var_apellido",datosUsuario.apellidos)
            intent.putExtra("var_dni",datosUsuario.dni)
            startActivity(intent)
        }
        
        btnBuscarProducto = findViewById(R.id.btnBuscarProducto)
        btnBuscarProducto.setOnClickListener{
            val intent = Intent(this,ProductosActivity::class.java)
            startActivity(intent)
        }
    }

}