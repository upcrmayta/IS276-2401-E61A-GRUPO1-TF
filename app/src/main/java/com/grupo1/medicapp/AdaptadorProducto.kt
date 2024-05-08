package com.grupo1.medicapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.grupo1.medicapp.entidades.Producto

class AdaptadorProducto:RecyclerView.Adapter<AdaptadorProducto.MiViewHolder>() {

    private var listarProductos:ArrayList<Producto> = ArrayList()

    fun agregarDatos(item:ArrayList<Producto>){
        this.listarProductos = item
    }

    class MiViewHolder(var view:View):RecyclerView.ViewHolder(view) {
        private var filaProductoNombre = view.findViewById<TextView>(R.id.filaProductoNombre)
        private var filaProductoPrecio = view.findViewById<TextView>(R.id.filaProductoPrecio)
        private var filaFarmaciaNombre = view.findViewById<TextView>(R.id.filaFarmaciaNombre)
        private var filaFarmaciaDireccion = view.findViewById<TextView>(R.id.filaFarmaciaDireccion)

        fun bindView(producto:Producto){
            filaProductoNombre.text = producto.nombre
            filaProductoPrecio.text = "S/." + producto.precio.toString()
            filaFarmaciaNombre.text = producto.farmacia.nombre
            filaFarmaciaDireccion.text = producto.farmacia.direccion
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MiViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.fila_producto,parent,false)
    )

    override fun onBindViewHolder(holder: AdaptadorProducto.MiViewHolder, position: Int) {
        val productoItem = listarProductos[position]
        holder.bindView(productoItem)
    }

    override fun getItemCount(): Int {
        return listarProductos.size
    }

}