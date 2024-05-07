package com.grupo1.medicapp.modelo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.grupo1.medicapp.entidades.Farmacia
import com.grupo1.medicapp.entidades.Ubicacion
import com.grupo1.medicapp.util.BaseDatos

class FarmaciaDAO(context:Context) {
    private var base: BaseDatos = BaseDatos(context)

    fun registrarFarmacia(farmacia: Farmacia):String{
        var respuesta = ""
        val db = base.writableDatabase
        try {
            val valores = ContentValues()
            valores.put("email", farmacia.email)
            valores.put("password", farmacia.password)
            valores.put("nombre_empresa", farmacia.nombre)
            valores.put("direccion", farmacia.direccion)
            val r = db.insert("farmacias",null,valores)
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

    fun cargarFarmacia(id:Int):ArrayList<Farmacia>{
        val listarFarmacia:ArrayList<Farmacia> = ArrayList()
        val query = "SELECT * FROM farmacias WHERE id = ?"
        val db = base.readableDatabase
        val cursor: Cursor

        val params:ArrayList<String> = ArrayList()
        params.add(id.toString())
        try{
            cursor = db.rawQuery(query,params.toTypedArray())
            if(cursor.count > 0){
                cursor.moveToFirst()
                do{
                    val farmacia = Farmacia()
                    farmacia.id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                    farmacia.email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
                    farmacia.password = cursor.getString(cursor.getColumnIndexOrThrow("password"))
                    farmacia.nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre_empresa"))
                    farmacia.direccion = cursor.getString(cursor.getColumnIndexOrThrow("direccion"))
                    listarFarmacia.add(farmacia)
                }while(cursor.moveToNext())
            }
        }catch (e:Exception){
            Log.d("===",e.message.toString())
        }finally {
            db.close()
        }
        return listarFarmacia
    }

    fun obtenerUbicacion(id:Int):Ubicacion{
        var ubicacion:Ubicacion = Ubicacion()
        val query = "SELECT * FROM ubicacion_mapa WHERE id_farmacia = ?"
        val db = base.readableDatabase
        val cursor: Cursor

        val params:ArrayList<String> = ArrayList()
        params.add(id.toString())
        try{
            cursor = db.rawQuery(query,params.toTypedArray())
            if(cursor.count > 0){
                cursor.moveToFirst()
                ubicacion.id_farmacia = cursor.getInt(cursor.getColumnIndexOrThrow("id_farmacia"))
                ubicacion.latitud = cursor.getDouble(cursor.getColumnIndexOrThrow("lat"))
                ubicacion.longitud = cursor.getDouble(cursor.getColumnIndexOrThrow("lon"))
            }
        }catch (e:Exception){
            Log.d("===",e.message.toString())
        }finally {
            db.close()
        }
        return ubicacion
    }
}