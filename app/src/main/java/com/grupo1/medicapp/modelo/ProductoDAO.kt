package com.grupo1.medicapp.modelo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.grupo1.medicapp.entidades.Producto
import com.grupo1.medicapp.util.BaseDatos

class ProductoDAO(context:Context) {
    private var base: BaseDatos = BaseDatos(context)

    fun registrarProducto(producto: Producto):String{
        var respuesta = ""
        val db = base.writableDatabase
        try {
            val valores = ContentValues()
            valores.put("id_producto", producto.id)
            valores.put("id_farmacia", producto.id_farmacia)
            valores.put("nombre_producto", producto.nombre)
            valores.put("precio_producto", producto.precio)
            valores.put("stock", producto.stock)
            valores.put("descripcion", producto.descripcion)
            val r = db.insert("productos_farmacia",null,valores)
            if(r == -1L){
                respuesta = "Ocurrió un error al insertar"
            }else{
                respuesta = "Se registró correctamente"
            }
        }catch(e:Exception){
            respuesta = e.message.toString()
        }finally {
            db.close()
        }
        return respuesta
    }

    fun cargarProducto(id:Int):ArrayList<Producto>{
        val listarProducto:ArrayList<Producto> = ArrayList()
        val query = "SELECT * FROM productos_farmacia WHERE id_producto = ?"
        val db = base.readableDatabase
        val cursor: Cursor

        val params:ArrayList<String> = ArrayList()
        params.add(id.toString())
        try{
            cursor = db.rawQuery(query,params.toTypedArray())
            if(cursor.count > 0){
                cursor.moveToFirst()
                do{
                    val producto = Producto()
                    producto.id = cursor.getInt(cursor.getColumnIndexOrThrow("id_producto"))
                    producto.id_farmacia = cursor.getInt(cursor.getColumnIndexOrThrow("id_farmacia"))
                    producto.nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre_producto"))
                    producto.precio = cursor.getDouble(cursor.getColumnIndexOrThrow("precio_producto"))
                    producto.stock = cursor.getInt(cursor.getColumnIndexOrThrow("stock"))
                    producto.descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripción"))
                    listarProducto.add(producto)
                }while(cursor.moveToNext())
            }
        }catch (e:Exception){
            Log.d("===",e.message.toString())
        }finally {
            db.close()
        }
        return listarProducto
    }
}