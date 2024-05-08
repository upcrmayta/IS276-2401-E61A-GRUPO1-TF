package com.grupo1.medicapp.util

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseDatos(context: Context):SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION) {
    companion object{
        private const val DATABASE_NAME = "medicapp.db"
        private const val VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        var sql1 = "CREATE TABLE IF NOT EXISTS Usuario (" +
                "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
                //"  email TEXT NOT NULL UNIQUE," +
                "  username TEXT NOT NULL," +
                "  password TEXT NOT NULL," +
                "  nombres TEXT NOT NULL," +
                "  apellidos TEXT NOT NULL," +
                "  dni TEXT NOT NULL" +
                ");"
        db.execSQL(sql1)

        var sql2 = "CREATE TABLE IF NOT EXISTS Farmacia (" +
                "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  email TEXT NOT NULL," +
                "  password TEXT NOT NULL," +
                "  nombre_empresa TEXT NOT NULL," +
                "  direccion TEXT NOT NULL" +
                ");"
        db.execSQL(sql2)

        var sql3 = "CREATE TABLE IF NOT EXISTS Ubicación_Mapa (" +
                "  id_farmacia INTEGER," +
                "  lat REAL NOT NULL," +
                "  lon REAL NOT NULL," +
                "  FOREIGN KEY(id_farmacia) REFERENCES farmacias(id)" +
                ");"
        db.execSQL(sql3)

        var sql4 = "CREATE TABLE IF NOT EXISTS Productos_Farmacia (" +
                "  id_producto INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  id_farmacia INTEGER," +
                "  nombre_producto TEXT NOT NULL," +
                "  precio_producto REAL NOT NULL," +
                "  stock INTEGER NOT NULL," +
                "  descripción TEXT," +
                "  FOREIGN KEY(id_farmacia) REFERENCES farmacias(id)" +
                ");"
        db.execSQL(sql4)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        var sql1 = "DROP TABLE IF EXISTS usuarios;"
        db.execSQL(sql1)

        var sql2 = "DROP TABLE IF EXISTS farmacias;"
        db.execSQL(sql2)

        var sql3 = "DROP TABLE IF EXISTS ubicacion_mapa;"
        db.execSQL(sql3)

        var sql4 = "DROP TABLE IF EXISTS productos_farmacia;"
        db.execSQL(sql4)
    }
}