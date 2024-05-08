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
import com.grupo1.medicapp.entidades.Farmacia
import com.grupo1.medicapp.entidades.Usuario
import com.grupo1.medicapp.modelo.FarmaciaDAO
import com.grupo1.medicapp.modelo.UsuarioDAO

class RegistroFarmacia : AppCompatActivity() {

    private lateinit var txtemail: EditText
    private lateinit var txtPass: EditText
    private lateinit var txtFarmacia: EditText
    private lateinit var txtDireccion: EditText

    private lateinit var btnRegistrar: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro_farmacia)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        asignarReferencias()
    }

    fun asignarReferencias(){

        txtemail = findViewById(R.id.txtEmailFarma)
        txtPass = findViewById(R.id.txtPassFarma)
        txtFarmacia = findViewById(R.id.txtNombreFarma)
        txtDireccion = findViewById(R.id.txtDirecFarma)

        btnRegistrar = findViewById(R.id.btnRegistrarFarmacia)

        btnRegistrar.setOnClickListener{
            capturarDatos()}
    }

    fun capturarDatos() {

        val email = txtemail.text.toString()
        val password = txtPass.text.toString()
        val farmacia = txtFarmacia.text.toString()
        val direccion = txtDireccion.text.toString()

        var validar = true

        if (email.isEmpty()) {
            validar = false
            txtemail.setError("Email es obligatorio")
        }

        if (password.isEmpty()) {
            validar = false
            txtPass.setError("Password es obligatorio")
        }
        if (farmacia.isEmpty()) {
            validar = false
            txtFarmacia.setError("Nombre Farmacia es obligatorio")
        }

        if (direccion.isEmpty()) {
            validar = false
            txtDireccion.setError("Dirección es obligatorio")
        }

        if (validar) {
            val objeto = Farmacia()
            objeto.email = email
            objeto.password = password
            objeto.nombre = farmacia
            objeto.direccion = direccion

            registrarFarmacia(objeto)

        }
    }
    fun registrarFarmacia(objeto: Farmacia){

        val farmaciaDAO = FarmaciaDAO(this)
        val mensaje = farmaciaDAO.registrarFarmacia(objeto)
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
}