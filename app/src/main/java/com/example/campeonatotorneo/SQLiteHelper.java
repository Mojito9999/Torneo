package com.example.campeonatotorneo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "bdCampeonatoTorneo.db";

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creamos la tabla si no existe
        db.execSQL(EstructuraBBDD.SQL_CREATE_ENTRIES);
        db.execSQL(EstructuraBBDD.SQL_CREATE_PARTIDA);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //este método se lanzará cuando sea necesaria una actualización de la estructura de la base de datos
        //NOTA: Por simplicidad del ejemplo aquí utilizamos directamente
        // la opción de eliminar la tabla anterior y crearla de nuevo
        // vacía con el nuevo formato.
        // Sin embargo lo normal será que haya que migrar datos de la
        // tabla antigua a la nueva, por lo que este método debería
        // ser más elaborado.
        //Se elimina la versión anterior de la tabla
        db.execSQL(EstructuraBBDD.SQL_DELETE_ENTRIES);
        //Se crea la nueva versión de la tabla
        db.execSQL(EstructuraBBDD.SQL_CREATE_ENTRIES);
        //Se elimina la versión anterior de la tabla
        db.execSQL(EstructuraBBDD.SQL_CREATE_PARTIDA);
        //Se crea la nueva versión de la tabla
        db.execSQL(EstructuraBBDD.SQL_CREATE_PARTIDA);

    }
}

