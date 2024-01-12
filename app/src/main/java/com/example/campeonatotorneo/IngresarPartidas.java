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


public class IngresarPartidas extends AppCompatActivity implements AdapterView.OnItemClickListener {

    TextView edtTexto,edtTexto1, edtTexto2,edtTexto3,edtTexto4,edtTexto5;
    SQLiteDatabase db;
    SQLiteHelper helper;
    ListView lv;
    ImageView imgViewFoto;
    int _idCursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_partidas);

        edtTexto = findViewById(R.id.edtEncuentroNuevo);
        edtTexto1 = findViewById(R.id.edtFechaNueva);
        edtTexto2 = findViewById(R.id.edtJugadorNuevo1);
        edtTexto3 = findViewById(R.id.edtJugadorNuevo2);
        edtTexto4 = findViewById(R.id.edtPuntosJugadorNuevo1);
        edtTexto5 = findViewById(R.id.edtJugadorNuevo2);
        lv = findViewById(R.id.lstListaModif);
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
    public void insertarPartida(View view) {
        db = helper.getWritableDatabase();

        String numEncuentroTexto = String.valueOf(edtTexto1.getText());
        String fechaTexto = String.valueOf(edtTexto1.getText());
        String jugador1 = String.valueOf(edtTexto2.getText());
        String jugador2 = String.valueOf(edtTexto3.getText());
        String puntuacionJugador1Texto = String.valueOf(edtTexto4.getText());
        String puntuacionJugador2Texto = String.valueOf(edtTexto5.getText());

        if (!numEncuentroTexto.isEmpty() && !fechaTexto.isEmpty() && !jugador1.isEmpty() && !jugador2.isEmpty() && !puntuacionJugador1Texto.isEmpty() && !puntuacionJugador2Texto.isEmpty()) {
            int numEncuentro = Integer.parseInt(numEncuentroTexto);
            int fecha = Integer.parseInt(fechaTexto);
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

        //adaptamos el cursor a nuestro ListView

        String[] from = {EstructuraBBDD.EstructuraPartida.COLUMN_NUM_ENCUENTRO, EstructuraBBDD.EstructuraPartida.COLUMN_FECHA,EstructuraBBDD.EstructuraPartida.COLUMN_JUGADOR_1,EstructuraBBDD.EstructuraPartida.COLUMN_JUGADOR_2, EstructuraBBDD.EstructuraPartida.COLUMN_PUNTUACION_JUGADOR_1,EstructuraBBDD.EstructuraPartida.COLUMN_PUNTUACION_JUGADOR_2};
        int[] to = {R.id.txtTexto, R.id.textView1, R.id.textView2,R.id.textView3, R.id.textView4,R.id.textView5,R.id.imageView};

        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(this, R.layout.lista2, cursor, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        lv.setAdapter(adaptador);

        db.close();
    }
}

