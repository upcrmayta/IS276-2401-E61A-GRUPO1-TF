package com.grupo1.medicapp

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.grupo1.medicapp.entidades.Farmacia
import com.grupo1.medicapp.entidades.Usuario
import com.grupo1.medicapp.modelo.FarmaciaDAO
import com.grupo1.medicapp.modelo.UsuarioDAO

class LoginActivity : AppCompatActivity() {

    private lateinit var txtUsername: EditText
    private lateinit var txtPassword: EditText
    private lateinit var btnIniciarSesion: Button
    private lateinit var switchButton: SwitchCompat
    private var check: Boolean = false


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
        switchButton = findViewById<SwitchCompat>(R.id.switchButton)
        txtUsername = findViewById(R.id.txtUsuario)
        txtPassword = findViewById(R.id.txtClave)
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion)

        switchButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                switchButton.text = "Farmacia"
                check  = true
            } else {
                switchButton.text = "Usuario"
                check = false
            }
        }

        btnIniciarSesion.setOnClickListener{
            capturarDatos()}
    }
    fun capturarDatos() {

        val usuario = txtUsername.text.toString()
        val password = txtPassword.text.toString()
        var switch = check
        var validar= true

        if (usuario.isEmpty()) {
            validar = false
            txtUsername.setError("Usuario es obligatorio")
        }

        if (password.isEmpty()) {
            validar = false
            txtPassword.setError("Password es obligatorio")
        }

        if (validar) {

            if (switch){
                val objeto = Farmacia()
                objeto.email = usuario
                objeto.password = password
                loginFarmacia(objeto)

            }else{
                val objeto = Usuario()
                objeto.username = usuario
                objeto.password = password
                loginUsuario(objeto)
            }



    }
    }

    fun loginUsuario(objeto: Usuario){
        val usuarioDAO = UsuarioDAO(this)
        val veredicto = usuarioDAO.loginUsuario(objeto)
        mostrarMensaje(veredicto)

    }

    fun loginFarmacia(objeto: Farmacia){
        val farmaciaDAO = FarmaciaDAO(this)
        val veredicto = farmaciaDAO.loginFarmacia(objeto)
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