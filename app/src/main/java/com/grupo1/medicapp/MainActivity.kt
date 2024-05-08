package com.grupo1.medicapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.grupo1.medicapp.entidades.Usuario
import com.grupo1.medicapp.modelo.UsuarioDAO

class MainActivity : AppCompatActivity() {

    private lateinit var btnRedirCrearCuen: Button
    private lateinit var btnRedirIniciarSesion: Button

    private lateinit var txtEmail:EditText
    private lateinit var txtUsername:EditText
    private lateinit var txtPassword:EditText
    private lateinit var txtNombres:EditText
    private lateinit var txtApellidos:EditText
    private lateinit var txtDNI:EditText
    private lateinit var btnRegistrar:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
       asignarReferencias()
    }

    fun asignarReferencias(){
        btnRedirCrearCuen = findViewById(R.id.btnRedirCrearCuenta)
        btnRedirCrearCuen.setOnClickListener{
            val intent = Intent (this,RegistroActivity::class.java)
            startActivity(intent)
        }

        btnRedirIniciarSesion = findViewById(R.id.btnRedirIniciarSesion)
        btnRedirIniciarSesion.setOnClickListener{
            val intent = Intent (this,LoginActivity::class.java)
            startActivity(intent)
        }



//        txtEmail = findViewById(R.id.txtEmail)
//        txtUsername = findViewById(R.id.txtUsername)
//        txtPassword = findViewById(R.id.txtPassword)
//        txtNombres = findViewById(R.id.txtNombres)
//        txtApellidos = findViewById(R.id.txtApellidos)
//        txtDNI = findViewById(R.id.txtDNI)
//        btnRegistrar = findViewById(R.id.btnRegistrar)

//        btnRegistrar.setOnClickListener{
//            capturarDatos()
//        }
    }

    /*fun capturarDatos(){
        val email = txtEmail.text.toString()
        val username = txtUsername.text.toString()
        val password = txtPassword.text.toString()
        val nombres = txtNombres.text.toString()
        val apellidos = txtApellidos.text.toString()
        val dni = txtDNI.text.toString()
        var valida = true;

        if(email.isEmpty()){
            valida = false
            txtEmail.setError("Email es obligatorio")
        }
        if(username.isEmpty()){
            valida = false
            txtUsername.setError("Usuario es obligatorio")
        }
        if(password.isEmpty()){
            valida = false
            txtPassword.setError("Clave es obligatorio")
        }
        if(nombres.isEmpty()){
            valida = false
            txtNombres.setError("Nombres es obligatorio")
        }
        if(apellidos.isEmpty()){
            valida = false
            txtApellidos.setError("Apellidos es obligatorio")
        }
        if(dni.isEmpty()){
            valida = false
            txtDNI.setError("DNI es obligatorio")
        }

        if(valida){
            val objeto = Usuario()
            //objeto.email = email
            objeto.username = username
            objeto.password = password
            objeto.nombres = nombres
            objeto.apellidos = apellidos
            objeto.dni = dni
            registrar(objeto)
        }
    }*/

    fun registrar(objeto:Usuario){
        val usuarioDAO = UsuarioDAO(this)
        val mensaje = usuarioDAO.registrarUsuario(objeto)
        mostrarMensaje(mensaje)
    }

    fun mostrarMensaje(mensaje:String){
        val ventana = AlertDialog.Builder(this)
        ventana.setTitle("Mensaje Informativo")
        ventana.setMessage(mensaje)
        ventana.setPositiveButton("Aceptar", null)
        ventana.create().show()
    }
}