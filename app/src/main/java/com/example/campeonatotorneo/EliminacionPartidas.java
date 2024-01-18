package com.example.campeonatotorneo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class EliminacionPartidas extends AppCompatActivity implements AdapterView.OnItemClickListener {

    TextView edtTexto,edtTexto1, edtTexto2,edtTexto3,edtTexto4,edtTexto5;
    SQLiteDatabase db;
    SQLiteHelper helper;
    ListView lv;
    ImageView imgViewFoto;
    int _idCursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminacion_partidas);

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
    public void eliminarPartidas(View view) {
        db = helper.getWritableDatabase();

        if (_idCursor > 0) {
            String selection = EstructuraBBDD.EstructuraPartida._ID + "=?";
            String[] selectionArgs = {String.valueOf(_idCursor)};

            int filasEliminadas = db.delete(
                    EstructuraBBDD.EstructuraPartida.TABLE_NAME_PARTIDA,
                    selection,
                    selectionArgs
            );

            if (filasEliminadas > 0) {
                consultaPartida(); // Actualizamos la lista después de eliminar
            }
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

