package com.example.campeonatotorneo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    TextView txtTexto;
    SQLiteDatabase db;
    SQLiteHelper helper;
    EditText edtTitulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //hacemos que funcione el scroll del cuadro de texto, primero hemos dicho en el xml que tiene scrollbars verticales
        txtTexto.setMovementMethod(new ScrollingMovementMethod());


        helper = new SQLiteHelper(this);
        db = helper.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put("titulo", "Don Giovanni");
        values.put("compositor", "W.A. Mozart");
        values.put("year", 1787);
        values.put("foto",R.drawable.ic_launcher_background);
        db.insert("operas",null, values);

        //borramos todas las filas excepto la primera
        db.delete("operas","_id>1" ,null ) ;

        inserta("","",1,R.drawable.ic_launcher_background,1,1,1,1,1);
        //consultamos los datos de la tabla operas
        //esta query al poner todos los parámetros a null es equivalente a poner "select * from operas"
        Cursor cursor = db.query("operas", null, null, null, null, null, null);
        //mostrar contenido
        mostrarTabla(cursor);

        db.close();
    }
    private void inserta(String nombre, String ciudad, int partidasGanadas, int foto, int fecha, int jugador1, int jugador2, int puntuacionJugador1, int puntuacionJugador2) {

        ContentValues values = new ContentValues();
        values.put(EstructuraBBDD.EstructuraCampeonatoTorneo.COLUMN_NOMBRE_JUGADOR, nombre);
        values.put(EstructuraBBDD.EstructuraCampeonatoTorneo.COLUMN_CIUDAD, ciudad);
        values.put(EstructuraBBDD.EstructuraCampeonatoTorneo.COLUMN_PARTIDAS_GANADAS, partidasGanadas);
        values.put(EstructuraBBDD.EstructuraCampeonatoTorneo.COLUMN_FOTO_JUGADOR, foto);

        values.put(EstructuraBBDD.EstructuraPartida.COLUMN_FECHA, fecha);
        values.put(EstructuraBBDD.EstructuraPartida.COLUMN_JUGADOR_1, jugador1);
        values.put(EstructuraBBDD.EstructuraPartida.COLUMN_JUGADOR_2, jugador2);
        values.put(EstructuraBBDD.EstructuraPartida.COLUMN_PUNTUACION_JUGADOR_1, puntuacionJugador1);
        values.put(EstructuraBBDD.EstructuraPartida.COLUMN_PUNTUACION_JUGADOR_2, puntuacionJugador2);

        db.insert(EstructuraBBDD.EstructuraCampeonatoTorneo.TABLE_NAME_TORNEO, null, values);
    }
    private void mostrarTabla(Cursor c) {
        //los mostramos en el cuadro de texto que tenemos en el layout

        txtTexto.append("\n Tabla operas \n-----------");
        c.moveToFirst();

        int nfilas=c.getCount();
        int ncolumnas=c.getColumnCount();
        String fila="\n";

        for (int i = 0; i < nfilas; i++) {
            fila="\n";
            for(int j=0;j<ncolumnas;j++){
                fila=fila+c.getString(j)+" ";
            }
            txtTexto.append(fila);

            c.moveToNext();
        }
    }
}