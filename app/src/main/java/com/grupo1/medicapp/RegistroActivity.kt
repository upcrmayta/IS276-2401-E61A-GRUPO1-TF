package com.grupo1.medicapp

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.grupo1.medicapp.entidades.Usuario
import com.grupo1.medicapp.modelo.UsuarioDAO

class RegistroActivity : AppCompatActivity() {

    private lateinit var txtTitulo: TextView
    private lateinit var txtUsername: EditText
    private lateinit var txtPassword: EditText
    private lateinit var txtNombres: EditText
    private lateinit var txtApeliidos: EditText
    private lateinit var txtDNI: EditText
    private var modificar = false

    private lateinit var btnRegistrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
            asignarReferencias()
            recuperarDatos()
    }

    fun recuperarDatos(){
        if (intent.hasExtra("var_id")){
            modificar = true
            txtTitulo.text = "Modificar Persona"
            btnRegistrar.text = "Guardar Cambios"
            //id = intent.getIntExtra("var_id",0)
            txtNombres.setText(intent.getStringExtra("var_nombres"))
            txtApeliidos.setText(intent.getStringExtra("var_apellido"))
            txtDNI.setText(intent.getStringExtra("var_dni"))
            txtUsername.setText(intent.getStringExtra("var_usuario"))
            txtUsername.isEnabled = false

        }
    }


    fun asignarReferencias(){
        txtTitulo = findViewById(R.id.Title_MedicAPP)
        txtUsername = findViewById(R.id.txtUsername)
        txtPassword = findViewById(R.id.txtClaveReg)
        txtNombres = findViewById(R.id.txtNombres)
        txtApeliidos = findViewById(R.id.txtApellidos)
        txtDNI = findViewById(R.id.txtDirecFarma)
        btnRegistrar = findViewById(R.id.btnRegistrarFarmacia)

        btnRegistrar.setOnClickListener{
            capturarDatos()}
    }

    fun capturarDatos() {
        val usuario = txtUsername.text.toString()
        val password = txtPassword.text.toString()
        val nombres = txtNombres.text.toString()
        val apellidos = txtApeliidos.text.toString()
        val dni = txtDNI.text.toString()

        var validar = true

        if (usuario.isEmpty()) {
            validar = false
            txtUsername.setError("Usuario es obligatorio")
        }

        if (password.isEmpty()) {
            validar = false
            txtPassword.setError("Password es obligatorio")
        }
        if (nombres.isEmpty()) {
            validar = false
            txtUsername.setError("Nombres es obligatorio")
        }

        if (apellidos.isEmpty()) {
            validar = false
            txtPassword.setError("Apellidos es obligatorio")
        }
        if (dni.isEmpty()) {
            validar = false
            txtPassword.setError("DNI es obligatorio")
        }

        if (validar) {
            val objeto = Usuario()
            objeto.username = usuario
            objeto.password = password
            objeto.nombres = nombres
            objeto.apellidos = apellidos
            objeto.dni = dni

            if(modificar){
                //objeto.username = usuario
                editar(objeto)
            } else{
                registrarUsuario(objeto)
            }

        }
    }

    fun editar(objeto: Usuario){
        val usurioDAO = UsuarioDAO(this)
        val mensaje = usurioDAO.modificarUsuario(objeto)
        val username = objeto.username
        mostrarMensaje2(mensaje,username)
    }
    fun registrarUsuario(objeto: Usuario){

            val usuarioDAO = UsuarioDAO(this)
            val mensaje = usuarioDAO.registrarUsuario(objeto)
            mostrarMensaje(mensaje)
    }

    fun mostrarMensaje(mensaje:String){
        val ventana = AlertDialog.Builder(this)
        ventana.setTitle("Mensaje Informativo")
        ventana.setMessage(mensaje)
        ventana.setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, which ->
            val intent = Intent (this,MainActivity::class.java)
            startActivity(intent)
        })
        ventana.create().show()
    }

    fun mostrarMensaje2(mensaje:String,username:String){
        val ventana = AlertDialog.Builder(this)
        ventana.setTitle("Mensaje Informativo")
        ventana.setMessage(mensaje)
        ventana.setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, which ->
            val intent = Intent (this,BienvenidoActivity::class.java)
            intent.putExtra("nombreUsuario",username)
            startActivity(intent)
        })
        ventana.create().show()
    }



}

