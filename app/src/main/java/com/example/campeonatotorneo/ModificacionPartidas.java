package com.example.campeonatotorneo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class ModificacionPartidas extends AppCompatActivity implements AdapterView.OnItemClickListener {

    TextView edtTexto,edtTexto1, edtTexto2,edtTexto3,edtTexto4,edtTexto5;
    SQLiteDatabase db;
    SQLiteHelper helper;
    ListView lv;
    ImageView imgViewFoto;
    int _idCursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificacion_partidas);

        edtTexto = findViewById(R.id.edtEncuentro_Modificar);
        edtTexto1 = findViewById(R.id.edtFecha_Modificar);
        edtTexto2 = findViewById(R.id.edtJ1_Modificar);
        edtTexto3 = findViewById(R.id.edtJ2_Modificar);
        edtTexto4 = findViewById(R.id.edtPuntosJ1_Modificar);
        edtTexto5 = findViewById(R.id.edtJ2_Modificar);
        lv = findViewById(R.id.lstJugador_consulta);
        helper = new SQLiteHelper(this);
        //realizamos la consulta
        consultaPartida();
        lv.setOnItemClickListener(this);


    }
    @Override
    public void onItemClick(AdapterView<?> listView, View view, int position, long id) {

        //obtenemos el objeto que se ha pulsado, que en nuestro caso será de tipo Cursor
        Cursor cursor=(Cursor) listView.getItemAtPosition(position);
        _idCursor=cursor.getInt(0);
        int numEncuentro=cursor.getInt(1);
        int fecha=cursor.getInt(2);
        String jugador1=cursor.getString(3) ;
        String jugador2=cursor.getString(5 ) ;
        int puntuacionJugador1=cursor.getInt(6);
        int puntuacionJugador2=cursor.getInt(7);
        int foto= cursor.getInt(4);
        //mostramos los datos en los cuadros de texto de la parte superior del layout
        edtTexto1.setText(numEncuentro +", "+fecha);
        edtTexto2.setText(jugador1);
        edtTexto3.setText(jugador2);
        edtTexto4.setText(puntuacionJugador1);
        edtTexto5.setText(puntuacionJugador2);
        imgViewFoto.setBackgroundResource(foto);


    }
    public void modificarPartidas(View view) {
        db = helper.getReadableDatabase();
        int encuentroModif = Integer.valueOf(edtTexto1.getText().toString());
        int fechaModif = Integer.valueOf(edtTexto2.getText().toString());
        String jugador1Modif = String.valueOf(edtTexto2.getText());
        String jugador2Modif = String.valueOf(edtTexto2.getText());
        int puntuacionJugador1Modif =Integer.valueOf(edtTexto2.getText().toString());
        int puntuacionJugador2Modif =Integer.valueOf(edtTexto2.getText().toString());
        if ((_idCursor >0) && (fechaModif >0) && (jugador1Modif != "")&& (jugador2Modif != "")&& (puntuacionJugador1Modif >0)&& (puntuacionJugador2Modif >0)) {
            //realizamos el update de los campos
            ContentValues values = new ContentValues();
            values.put(EstructuraBBDD.EstructuraPartida.COLUMN_NUM_ENCUENTRO, encuentroModif);
            values.put(EstructuraBBDD.EstructuraPartida.COLUMN_FECHA,
                    fechaModif);
            values.put(EstructuraBBDD.EstructuraPartida.COLUMN_JUGADOR_1,
                    jugador1Modif);
            values.put(EstructuraBBDD.EstructuraPartida.COLUMN_JUGADOR_2,
                    jugador1Modif);
            values.put(EstructuraBBDD.EstructuraPartida.COLUMN_PUNTUACION_JUGADOR_1, puntuacionJugador1Modif);
            values.put(EstructuraBBDD.EstructuraPartida.COLUMN_PUNTUACION_JUGADOR_2, puntuacionJugador2Modif);

            String selection = EstructuraBBDD.EstructuraPartida._ID + "=?";
            String[] selectionArgs = {String.valueOf(_idCursor)};
            int filasModificadas =
                    db.update(EstructuraBBDD.EstructuraPartida.TABLE_NAME_PARTIDA, values, selection,
                            selectionArgs);
            Toast.makeText(getApplicationContext(), "Se han modificado " +
                    Integer.toString(filasModificadas) + " filas", Toast.LENGTH_SHORT).show();
            consultaPartida();
        } else {
            Toast.makeText(getApplicationContext(), "Introduzca todos los datos en los campos a modificar", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
    private void consultaPartida() {
        helper = new SQLiteHelper(this);

        db = helper.getReadableDatabase();
        Cursor cursor = db.query(EstructuraBBDD.EstructuraPartida.TABLE_NAME_PARTIDA, null, null, null, null, null, null);

        //adaptamos el cursor a nuestro ListView

        String[] from = {EstructuraBBDD.EstructuraPartida.COLUMN_NUM_ENCUENTRO, EstructuraBBDD.EstructuraPartida.COLUMN_FECHA,EstructuraBBDD.EstructuraPartida.COLUMN_JUGADOR_1,EstructuraBBDD.EstructuraPartida.COLUMN_JUGADOR_2, EstructuraBBDD.EstructuraPartida.COLUMN_PUNTUACION_JUGADOR_1,EstructuraBBDD.EstructuraPartida.COLUMN_PUNTUACION_JUGADOR_2};
        int[] to = {R.id.txtEncuentror_consulta, R.id.textFecha_consulta, R.id.txtCiudad_consulta,R.id.txtPartidasGanadas_consulta, R.id.textPuntuacionJ1_consulta,R.id.textPuntacionJ2_consulta,R.id.imageView};

        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(this, R.layout.lista2, cursor, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        lv.setAdapter(adaptador);

        db.close();
    }
}

