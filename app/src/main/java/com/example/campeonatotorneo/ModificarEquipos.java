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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class ModificarEquipos extends AppCompatActivity implements AdapterView.OnItemClickListener {
    SQLiteHelper helper;
    SQLiteDatabase db;
    TextView edtTexto1, edtTexto2,edtTexto3;
    ListView lv;
    int _idCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_equipos);

        edtTexto1 = findViewById(R.id.edtJugadorNuevo);
        edtTexto2 = findViewById(R.id.edtCiudadNueva);
        edtTexto3 = findViewById(R.id.edtPartidaNuevo);
        lv = findViewById(R.id.lstListaModif);
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
    public void modificarEquipos(View view) {
        db = helper.getReadableDatabase();
        String nombreModif = String.valueOf(edtTexto1.getText());
        String ciudadModif = String.valueOf(edtTexto2.getText());
        String partidasGanadasModif = String.valueOf(edtTexto2.getText());
        if ((_idCursor > 0) && (nombreModif != "") && (ciudadModif != "")) {
        //realizamos el update de los campos
            ContentValues values = new ContentValues();
            values.put(EstructuraBBDD.EstructuraCampeonatoTorneo.COLUMN_NOMBRE_JUGADOR, nombreModif);
            values.put(EstructuraBBDD.EstructuraCampeonatoTorneo.COLUMN_CIUDAD,
                    ciudadModif);
            values.put(EstructuraBBDD.EstructuraCampeonatoTorneo.COLUMN_PARTIDAS_GANADAS, partidasGanadasModif);
            String selection = EstructuraBBDD.EstructuraCampeonatoTorneo._ID + "=?";
            String[] selectionArgs = {String.valueOf(_idCursor)};
            int filasModificadas =
                    db.update(EstructuraBBDD.EstructuraCampeonatoTorneo.TABLE_NAME_TORNEO, values, selection,
                            selectionArgs);
            Toast.makeText(getApplicationContext(), "Se han modificado " +
                    Integer.toString(filasModificadas) + " filas", Toast.LENGTH_SHORT).show();
            consultaTorneo();
        } else {
            Toast.makeText(getApplicationContext(), "Introduzca todos los datos en los campos a modificar", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
    private void consultaTorneo() {
        helper = new SQLiteHelper(this);

        db = helper.getReadableDatabase();
        Cursor cursor = db.query(EstructuraBBDD.EstructuraCampeonatoTorneo.TABLE_NAME_TORNEO, null, null, null, null, null, null);

        //adaptamos el cursor a nuestro ListView

        String[] from = {EstructuraBBDD.EstructuraCampeonatoTorneo.COLUMN_NOMBRE_JUGADOR, EstructuraBBDD.EstructuraCampeonatoTorneo.COLUMN_CIUDAD,EstructuraBBDD.EstructuraCampeonatoTorneo.COLUMN_PARTIDAS_GANADAS, EstructuraBBDD.EstructuraCampeonatoTorneo.COLUMN_FOTO_JUGADOR};
        int[] to = {R.id.txtTexto, R.id.textView2,R.id.textView3,R.id.imageView};

        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(this, R.layout.lista, cursor, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        lv.setAdapter(adaptador);

        db.close();
    }
}

