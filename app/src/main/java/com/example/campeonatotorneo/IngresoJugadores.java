package com.example.campeonatotorneo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class IngresoJugadores extends AppCompatActivity implements AdapterView.OnItemClickListener {
    SQLiteHelper helper;
    SQLiteDatabase db;
    TextView edtTexto1, edtTexto2,edtTexto3;
    ListView lv;
    int _idCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_jugadores);

        edtTexto1 = findViewById(R.id.edtJugador_Modificar);
        edtTexto2 = findViewById(R.id.edtCiudad_Modificar);
        edtTexto3 = findViewById(R.id.edtPartida_Moodificar);
        lv = findViewById(R.id.lstJugador_consulta);
        helper = new SQLiteHelper(this);
        //realizamos la consulta
        consultaTorneo();
        lv.setOnItemClickListener(this);

    }
    public void onItemClick(AdapterView<?> listView, View view, int position, long
            id) {
        //obtenemos el objeto que se ha pulsado, que en nuestro caso será de tipo Cursor
        Cursor cursor=(Cursor) listView.getItemAtPosition(position);
        _idCursor=cursor.getInt(0);
        String titulo=cursor.getString(1) ;
        String compositor=cursor.getString(2 ) ;
        int year=cursor.getInt(5);
        //mostramos los datos en los cuadros de texto de la parte superior de layout
        edtTexto1.setText(titulo);
        edtTexto2.setText(compositor);
        edtTexto3.setText(Integer.toString(year));
    }
    public void ingresarEquipos(View view) {
        db = helper.getWritableDatabase();

        String nombreNuevo = String.valueOf(edtTexto1.getText());
        String ciudadNueva = String.valueOf(edtTexto2.getText());
        String partidasGanadasNuevas = String.valueOf(edtTexto3.getText());

        if (!nombreNuevo.isEmpty() && !ciudadNueva.isEmpty()) {
            ContentValues values = new ContentValues();
            values.put(EstructuraBBDD.EstructuraCampeonatoTorneo.COLUMN_NOMBRE_JUGADOR, nombreNuevo);
            values.put(EstructuraBBDD.EstructuraCampeonatoTorneo.COLUMN_CIUDAD, ciudadNueva);
            values.put(EstructuraBBDD.EstructuraCampeonatoTorneo.COLUMN_PARTIDAS_GANADAS, partidasGanadasNuevas);

            long newRowId = db.insert(EstructuraBBDD.EstructuraCampeonatoTorneo.TABLE_NAME_TORNEO, null, values);

            if (newRowId != -1) {
                consultaTorneo(); // Actualizamos la lista después de insertar
            }
        }

        db.close();
    }
    private void consultaTorneo() {
        helper = new SQLiteHelper(this);

        db = helper.getReadableDatabase();
        Cursor cursor = db.query(EstructuraBBDD.EstructuraCampeonatoTorneo.TABLE_NAME_TORNEO, null, null, null, null, null, null);

        //adaptamos el cursor a nuestro ListView

        String[] from = {EstructuraBBDD.EstructuraCampeonatoTorneo.COLUMN_NOMBRE_JUGADOR, EstructuraBBDD.EstructuraCampeonatoTorneo.COLUMN_CIUDAD,EstructuraBBDD.EstructuraCampeonatoTorneo.COLUMN_PARTIDAS_GANADAS, EstructuraBBDD.EstructuraCampeonatoTorneo.COLUMN_FOTO_JUGADOR};
        int[] to = {R.id.txtEncuentror_consulta, R.id.txtCiudad_consulta,R.id.txtPartidasGanadas_consulta,R.id.imageView};

        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(this, R.layout.lista, cursor, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        lv.setAdapter(adaptador);

        db.close();
    }
}

