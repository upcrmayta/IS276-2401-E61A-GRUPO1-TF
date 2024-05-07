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
        var sql = "CREATE TABLE IF NOT EXISTS usuarios (" +
                "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  email TEXT NOT NULL UNIQUE," +
                "  username TEXT NOT NULL," +
                "  password TEXT NOT NULL," +
                "  nombres TEXT NOT NULL," +
                "  apellidos TEXT NOT NULL," +
                "  dni TEXT" +
                ");"
        db.execSQL(sql)

        sql = "CREATE TABLE farmacias (" +
                "  id INTEGER," +
                "  email TEXT NOT NULL," +
                "  password TEXT NOT NULL," +
                "  nombre_empresa TEXT NOT NULL," +
                "  direccion TEXT NOT NULL," +
                "  PRIMARY KEY(id AUTOINCREMENT)" +
                ");"
        db.execSQL(sql)

        sql = "CREATE TABLE IF NOT EXISTS ubicacion_mapa (" +
                "  id_farmacia INTEGER," +
                "  lat REAL NOT NULL," +
                "  lon REAL NOT NULL," +
                "  FOREIGN KEY(id_farmacia) REFERENCES farmacias(id)" +
                ");"
        db.execSQL(sql)

        sql = "CREATE TABLE IF NOT EXISTS productos_farmacia (" +
                "  id_producto INTEGER," +
                "  id_farmacia INTEGER," +
                "  nombre_producto TEXT NOT NULL," +
                "  precio_producto REAL NOT NULL," +
                "  stock INTEGER NOT NULL," +
                "  descripción TEXT," +
                "  FOREIGN KEY(id_farmacia) REFERENCES farmacias(id)," +
                "  PRIMARY KEY(id_producto AUTOINCREMENT)" +
                ");"
        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        var sql = "DROP TABLE IF EXISTS usuarios;"
        db.execSQL(sql)

        sql = "DROP TABLE IF EXISTS farmacias;"
        db.execSQL(sql)

        sql = "DROP TABLE IF EXISTS ubicacion_mapa;"
        db.execSQL(sql)

        sql = "DROP TABLE IF EXISTS productos_farmacia;"
        db.execSQL(sql)
    }
}