package com.grupo1.medicapp

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.grupo1.medicapp.entidades.Usuario
import com.grupo1.medicapp.modelo.UsuarioDAO

class LoginActivity : AppCompatActivity() {

    private lateinit var txtUsername: EditText
    private lateinit var txtPassword: EditText
    private lateinit var btnIniciarSesion: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        asignarReferencias()
    }

    fun asignarReferencias(){

        txtUsername = findViewById(R.id.txtUsuario)
        txtPassword = findViewById(R.id.txtClave)
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion)

        btnIniciarSesion.setOnClickListener{
            capturarDatos()}
    }
    fun capturarDatos() {

        val usuario = txtUsername.text.toString()
        val password = txtPassword.text.toString()

        var validar = true

        if (usuario.isEmpty()) {
            validar = false
            txtUsername.setError("Usuario es obligatorio")
        }

        if (password.isEmpty()) {
            validar = false
            txtPassword.setError("Password es obligatorio")
        }

        if (validar) {
            val objeto = Usuario()
            objeto.username = usuario
            objeto.password = password

            loginUsuario(objeto)
        }
    }

    fun loginUsuario(objeto: Usuario){
        val usuarioDAO = UsuarioDAO(this)
        val veredicto = usuarioDAO.loginUsuario(objeto)
        mostrarMensaje(veredicto)

    }

    fun mostrarMensaje(veredicto:Boolean){
        if(veredicto){
            val ventana = AlertDialog.Builder(this)
            ventana.setTitle("Mensaje Informativo")
            ventana.setMessage("Login Exitoso")
            ventana.setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, which ->
                val intent = Intent (this,BienvenidoActivity::class.java)
                startActivity(intent)
            })
            ventana.create().show()
        } else{
            val ventana = AlertDialog.Builder(this)
            ventana.setTitle("Mensaje Informativo")
            ventana.setMessage("Usuario y/o contraseña incorrecta")
            ventana.setPositiveButton("Aceptar", null)
            ventana.create().show()

        }

    }

}