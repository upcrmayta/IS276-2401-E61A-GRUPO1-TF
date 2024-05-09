package com.grupo1.medicapp

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.grupo1.medicapp.entidades.Farmacia
import com.grupo1.medicapp.entidades.Producto
import com.grupo1.medicapp.modelo.FarmaciaDAO
import com.grupo1.medicapp.modelo.ProductoDAO

class RegistroProductoActivity : AppCompatActivity() {

    private lateinit var txtProductoNombre: TextView
    private lateinit var txtProductoDescripcion: TextView
    private lateinit var txtProductoPrecio: TextView
    private lateinit var txtProductoExistencias: TextView
    private lateinit var txtProductoFarmacia: TextView
    private lateinit var btnGuardarProducto: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro_producto)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        asignarReferencias()
    }

    fun asignarReferencias(){
        txtProductoNombre = findViewById(R.id.txtProductoNombre)
        txtProductoDescripcion = findViewById(R.id.txtProductoDescripcion)
        txtProductoPrecio = findViewById(R.id.txtProductoPrecio)
        txtProductoExistencias = findViewById(R.id.txtProductoExistencias)
        txtProductoFarmacia = findViewById(R.id.txtProductoFarmacia)
        btnGuardarProducto = findViewById(R.id.btnGuardarProducto)

        btnGuardarProducto.setOnClickListener{
            capturarDatos()
        }
    }

    fun capturarDatos() {

        val nombre = txtProductoNombre.text.toString()
        val descripcion = txtProductoDescripcion.text.toString()
        val precio = txtProductoPrecio.text.toString()
        val existencias = txtProductoExistencias.text.toString()
        val id_farmacia = txtProductoFarmacia.text.toString()

        var validar = true

        if (nombre.isEmpty()) {
            validar = false
            txtProductoNombre.setError("Nombre es obligatorio")
        }

        if (descripcion.isEmpty()) {
            validar = false
            txtProductoDescripcion.setError("Descripcion es obligatorio")
        }

        if (precio.isEmpty()) {
            validar = false
            txtProductoPrecio.setError("v es obligatorio")
        }

        if (existencias.isEmpty()) {
            validar = false
            txtProductoExistencias.setError("Existencias es obligatorio")
        }

        if (id_farmacia.isEmpty()) {
            validar = false
            txtProductoFarmacia.setError("Farmacia es obligatorio")
        }

        if (validar) {
            val objeto = Producto()
            objeto.nombre = nombre
            objeto.descripcion = descripcion
            objeto.precio = precio.toDouble()
            objeto.stock = existencias.toInt()
            objeto.id_farmacia = id_farmacia.toInt()

            registrarProducto(objeto)
        }
    }

    fun registrarProducto(objeto: Producto){
        val productoDAO = ProductoDAO(this)
        val mensaje = productoDAO.registrarProducto(objeto)
        mostrarMensaje(mensaje)
    }

    fun mostrarMensaje(mensaje:String){
        val ventana = AlertDialog.Builder(this)
        ventana.setTitle("Mensaje Informativo")
        ventana.setMessage(mensaje)
        ventana.setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, which ->
            val intent = Intent (this,ProductosActivity::class.java)
            startActivity(intent)
        })
        ventana.create().show()
    }
}