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
        String jugador=cursor.getString(1) ;
        String ciudad=cursor.getString(2 ) ;
        int partidasGanadas=cursor.getInt(3);
        //mostramos los datos en los cuadros de texto de la parte superior de layout
        edtTexto1.setText(jugador);
        edtTexto2.setText(ciudad);
        edtTexto3.setText(Integer.toString(partidasGanadas));

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

        String[] from = {EstructuraBBDD.EstructuraCampeonatoTorneo.COLUMN_NOMBRE_JUGADOR, EstructuraBBDD.EstructuraCampeonatoTorneo.COLUMN_CIUDAD,EstructuraBBDD.EstructuraCampeonatoTorneo.COLUMN_PARTIDAS_GANADAS};
        int[] to = {R.id.textView2, R.id.textView3,R.id.textView4};

        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(this, R.layout.lista, cursor, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
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

