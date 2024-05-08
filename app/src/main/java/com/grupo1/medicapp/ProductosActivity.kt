package com.grupo1.medicapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.grupo1.medicapp.modelo.ProductoDAO

class ProductosActivity : AppCompatActivity() {

    private lateinit var btnIrABusqueda:FloatingActionButton
    private lateinit var rvProductos:RecyclerView
    private val productoDAO:ProductoDAO = ProductoDAO(this)
    private var adaptadorProducto:AdaptadorProducto = AdaptadorProducto()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_productos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        asignarReferencias()
        mostrarProductos()
    }

    fun mostrarProductos(){
        val listarProductos = productoDAO.cargarProducto();
    }

    fun asignarReferencias(){
        btnIrABusqueda = findViewById(R.id.btnIrABusqueda)
        btnIrABusqueda.setOnClickListener{
            val intent = Intent(this,BienvenidoActivity::class.java)
            startActivity(intent)
        }

        rvProductos = findViewById(R.id.rvProductos)
        rvProductos.layoutManager = LinearLayoutManager(this)
        rvProductos.adapter = adaptadorProducto
    }
}