package com.grupo1.medicapp.modelo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.grupo1.medicapp.entidades.Farmacia
import com.grupo1.medicapp.entidades.Usuario
import com.grupo1.medicapp.util.BaseDatos

class UsuarioDAO(context: Context) {
    private var base: BaseDatos = BaseDatos(context)

    fun registrarUsuario(usuario: Usuario): String {
        var respuesta = ""
        val db = base.writableDatabase
        try {
            val valores = ContentValues()
            //valores.put("email", usuario.email)
            valores.put("username", usuario.username)
            valores.put("password", usuario.password)
            valores.put("nombres", usuario.nombres)
            valores.put("apellidos", usuario.apellidos)
            valores.put("dni", usuario.dni)
            val r = db.insert("Usuario", null, valores)
            if (r == -1L) {
                respuesta = "Ocurrió un error al insertar"
            } else {
                respuesta = "Se registró correctamente"
            }
        } catch (e: Exception) {
            respuesta = e.message.toString()
        } finally {
            db.close()
        }
        return respuesta
    }

    fun cargarUsuario(id: Int): ArrayList<Usuario> {
        val listarUsuarios: ArrayList<Usuario> = ArrayList()
        val query = "SELECT * FROM usuarios WHERE id = ?"
        val db = base.readableDatabase
        val cursor: Cursor

        val params: ArrayList<String> = ArrayList()
        params.add(id.toString())
        try {
            cursor = db.rawQuery(query, params.toTypedArray())
            if (cursor.count > 0) {
                cursor.moveToFirst()
                do {
                    val usuario = Usuario()
                    usuario.id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                    //usuario.email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
                    usuario.username = cursor.getString(cursor.getColumnIndexOrThrow("username"))
                    usuario.password = cursor.getString(cursor.getColumnIndexOrThrow("password"))
                    usuario.nombres = cursor.getString(cursor.getColumnIndexOrThrow("nombres"))
                    usuario.apellidos = cursor.getString(cursor.getColumnIndexOrThrow("apellidos"))
                    usuario.dni = cursor.getString(cursor.getColumnIndexOrThrow("dni"))
                    listarUsuarios.add(usuario)
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            Log.d("===", e.message.toString())
        } finally {
            db.close()
        }
        return listarUsuarios
    }

    fun loginUsuario(usuario: Usuario): Boolean {

        val username = usuario.username
        val password = usuario.password
        val db = base.readableDatabase
        var isValidUser = false

        try {
            val query = "SELECT * FROM Usuario WHERE username = ? AND password = ?"
            val cursor: Cursor? = db.rawQuery(query, arrayOf(username, password))
            val count = cursor?.count ?: 0
            isValidUser = count > 0
            cursor?.close()

        } catch (e: Exception) {
            Log.d("===", e.message.toString())

        } finally {
            db.close()
        }
        return isValidUser
    }

    fun obtenerUsuario(usuario: String): Usuario {
        val username = usuario
        val query = "SELECT * FROM Usuario WHERE username = ?"
        val db = base.readableDatabase
        var cursor: Cursor? = null
        val usuarioEncontrado = Usuario()

        try {
            cursor = db.rawQuery(query, arrayOf(username))
            if (cursor.moveToFirst()) {
                // Asignar los valores directamente a la instancia usuarioEncontrado
                usuarioEncontrado.id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                usuarioEncontrado.username =
                    cursor.getString(cursor.getColumnIndexOrThrow("username"))
                usuarioEncontrado.password =
                    cursor.getString(cursor.getColumnIndexOrThrow("password"))
                usuarioEncontrado.nombres =
                    cursor.getString(cursor.getColumnIndexOrThrow("nombres"))
                usuarioEncontrado.apellidos =
                    cursor.getString(cursor.getColumnIndexOrThrow("apellidos"))
                usuarioEncontrado.dni = cursor.getString(cursor.getColumnIndexOrThrow("dni"))
            }
        } catch (e: Exception) {
            Log.d("UsuarioDAO", "Error al obtener usuario: ${e.message}")
        } finally {
            cursor?.close()
            db.close()
        }

        return usuarioEncontrado
    }

    fun modificarUsuario(usuario: Usuario): String {
        var respuesta = ""
        var db = base.writableDatabase

        try {
            val valores = ContentValues()
            valores.put("password", usuario.password)
            valores.put("nombres", usuario.nombres)
            valores.put("apellidos", usuario.apellidos)
            valores.put("dni", usuario.dni)

            val r = db.update("Usuario", valores, "username like '" + usuario.username + "'", null)
            if (r == -1) {
                respuesta = "Ocurrió un error al actualizar"
            } else {
                respuesta = "Se actualizó correctamente"
            }

        } catch (e: Exception) {
            respuesta = e.message.toString()
        } finally {
            db.close()
        }

        return respuesta
    }
}