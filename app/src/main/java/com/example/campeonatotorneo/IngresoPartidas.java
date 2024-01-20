package com.example.campeonatotorneo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class IngresoPartidas extends AppCompatActivity implements AdapterView.OnItemClickListener {

    TextView edtTexto1, edtTexto2,edtTexto3,edtTexto4,edtTexto5,edtTexto6;
    SQLiteDatabase db;
    SQLiteHelper helper;
    ListView lv;
    ImageView imgViewFoto;
    int _idCursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_partidas);

        edtTexto1 = findViewById(R.id.edtEncuentro_Modificar);
        edtTexto2 = findViewById(R.id.edtFecha_Modificar);
        edtTexto3 = findViewById(R.id.edtJ1_Modificar);
        edtTexto4 = findViewById(R.id.edtJ2_Modificar);
        edtTexto5 = findViewById(R.id.edtPuntosJ1_Modificar);
        edtTexto6 = findViewById(R.id.edtPuntosJ2_Modificar);
        lv = findViewById(R.id.listPartidaConsulta);
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
        int numEncuentro = cursor.getInt(1);
        String fecha = cursor.getString(2);
        String jugador1 = cursor.getString(3);
        String jugador2 = cursor.getString(4);
        int puntuacionJugador1 = cursor.getInt(5);
        int puntuacionJugador2 = cursor.getInt(6);

        //mostramos los datos en los cuadros de texto de la parte superior del layout
        edtTexto1.setText(Integer.toString(numEncuentro));
        edtTexto2.setText(fecha);
        edtTexto3.setText(jugador1);
        edtTexto4.setText(jugador2);
        edtTexto5.setText(Integer.toString(puntuacionJugador1));
        edtTexto6.setText(Integer.toString(puntuacionJugador2));



    }
    public void ingresarPartidas(View view) {

        db = helper.getWritableDatabase();

        String numEncuentroTexto = String.valueOf(edtTexto1.getText());
        String fechaTexto = String.valueOf(edtTexto2.getText());
        String jugador1 = String.valueOf(edtTexto3.getText());
        String jugador2 = String.valueOf(edtTexto4.getText());
        String puntuacionJugador1Texto = String.valueOf(edtTexto5.getText());
        String puntuacionJugador2Texto = String.valueOf(edtTexto6.getText());

        if (!numEncuentroTexto.isEmpty() && !fechaTexto.isEmpty() && !jugador1.isEmpty() && !jugador2.isEmpty() && !puntuacionJugador1Texto.isEmpty() && !puntuacionJugador2Texto.isEmpty()) {
            int numEncuentro = Integer.parseInt(numEncuentroTexto);
            String fecha = fechaTexto;
            int puntuacionJugador1 = Integer.parseInt(puntuacionJugador1Texto);
            int puntuacionJugador2 = Integer.parseInt(puntuacionJugador2Texto);

            ContentValues values = new ContentValues();
            values.put(EstructuraBBDD.EstructuraPartida.COLUMN_NUM_ENCUENTRO, numEncuentro);
            values.put(EstructuraBBDD.EstructuraPartida.COLUMN_FECHA, fecha);
            values.put(EstructuraBBDD.EstructuraPartida.COLUMN_JUGADOR_1, jugador1);
            values.put(EstructuraBBDD.EstructuraPartida.COLUMN_JUGADOR_2, jugador2);
            values.put(EstructuraBBDD.EstructuraPartida.COLUMN_PUNTUACION_JUGADOR_1, puntuacionJugador1);
            values.put(EstructuraBBDD.EstructuraPartida.COLUMN_PUNTUACION_JUGADOR_2, puntuacionJugador2);

            long newRowId = db.insert(EstructuraBBDD.EstructuraPartida.TABLE_NAME_PARTIDA, null, values);

            if (newRowId != -1) {
                consultaPartida(); // Actualizamos la lista después de insertar
            }
        }

        db.close();
    }

    private void consultaPartida() {
        helper = new SQLiteHelper(this);

        db = helper.getReadableDatabase();
        Cursor cursor = db.query(EstructuraBBDD.EstructuraPartida.TABLE_NAME_PARTIDA, null, null, null, null, null, null);
// Mueve el cursor al principio
        if (cursor != null) {
            cursor.moveToFirst();
        }
        //adaptamos el cursor a nuestro ListView

        String[] from = {EstructuraBBDD.EstructuraPartida.COLUMN_NUM_ENCUENTRO, EstructuraBBDD.EstructuraPartida.COLUMN_FECHA,EstructuraBBDD.EstructuraPartida.COLUMN_JUGADOR_1,EstructuraBBDD.EstructuraPartida.COLUMN_JUGADOR_2, EstructuraBBDD.EstructuraPartida.COLUMN_PUNTUACION_JUGADOR_1,EstructuraBBDD.EstructuraPartida.COLUMN_PUNTUACION_JUGADOR_2};
        int[] to = {R.id.textView8,R.id.textView9,R.id.textView10,R.id.textView11,R.id.textView12,R.id.textView13 };

        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(this, R.layout.lista2, cursor, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        lv.setAdapter(adaptador);

        db.close();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_consultas) {
            Intent intent = new Intent(this, ConsultaDatos.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_modificar) {
            Intent intent = new Intent(this, ModificarDatos.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_ingresar) {
            Intent intent = new Intent(this, IngresoDatos.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_eliminar) {
            Intent intent = new Intent(this, EliminacionDatos.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

