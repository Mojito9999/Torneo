package com.example.campeonatotorneo;

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


public class EliminacionEquipos extends AppCompatActivity implements AdapterView.OnItemClickListener {
    SQLiteHelper helper;
    SQLiteDatabase db;
    TextView edtTexto1, edtTexto2,edtTexto3;
    ListView lv;
    int _idCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminacion_equipos);

        edtTexto1 = findViewById(R.id.edtJugadorNuevo);
        edtTexto2 = findViewById(R.id.edtCiudadNueva);
        edtTexto3 = findViewById(R.id.edtPartidaNuevo);
        lv = findViewById(R.id.lstListaModif);
        helper = new SQLiteHelper(this);
        //realizamos la consulta
        consultaTorneo();
        lv.setOnItemClickListener(this);

        return false;
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
    public void eliminarEquipos(View view) {
        db = helper.getWritableDatabase();

        if (_idCursor > 0) {
            String selection = EstructuraBBDD.EstructuraCampeonatoTorneo._ID + "=?";
            String[] selectionArgs = {String.valueOf(_idCursor)};

            int filasEliminadas = db.delete(
                    EstructuraBBDD.EstructuraCampeonatoTorneo.TABLE_NAME_TORNEO,
                    selection,
                    selectionArgs
            );

            if (filasEliminadas > 0) {
                consultaTorneo(); // Actualizamos la lista después de eliminar
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
        int[] to = {R.id.txtTexto, R.id.textView2,R.id.textView3,R.id.imageView};

        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(this, R.layout.lista, cursor, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        lv.setAdapter(adaptador);

        db.close();
    }
}

