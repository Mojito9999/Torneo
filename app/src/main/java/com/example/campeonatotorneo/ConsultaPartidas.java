package com.example.campeonatotorneo;

import androidx.appcompat.app.AppCompatActivity;

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

public class ConsultaPartidas extends AppCompatActivity implements AdapterView.OnItemClickListener {

    TextView txtTexto,txtTexto1, txtTexto2,txtTexto3,txtTexto4,txtTexto5;
    SQLiteDatabase db;
    SQLiteHelper helper;
    ListView lv;
    ImageView imgViewFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_partidas);

        txtTexto = findViewById(R.id.txtTexto);
        txtTexto1 = findViewById(R.id.textView1);
        txtTexto2 = findViewById(R.id.textView2);
        txtTexto3 = findViewById(R.id.textView3);
        txtTexto4 = findViewById(R.id.textView4);
        txtTexto5 = findViewById(R.id.textView5);
        imgViewFoto = findViewById(R.id.imageView);
        lv = findViewById(R.id.lstListaModif);

        //realizamos la consulta
        consultaPartida();

        lv.setOnItemClickListener(this);

        return false;
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

    @Override
    public void onItemClick(AdapterView<?> listView, View view, int position, long id) {

        //obtenemos el objeto que se ha pulsado, que en nuestro caso será de tipo Cursor
        Cursor cursor=(Cursor) listView.getItemAtPosition(position);
        int numEncuentro=cursor.getInt(0);
        int fecha=cursor.getInt(1);
        String jugador1=cursor.getString(2) ;
        String jugador2=cursor.getString(4 ) ;
        int puntuacionJugador1=cursor.getInt(5);
        int puntuacionJugador2=cursor.getInt(6);
        int foto= cursor.getInt(3);
        //mostramos los datos en los cuadros de texto de la parte superior del layout
        txtTexto1.setText(numEncuentro +", "+fecha);
        txtTexto2.setText(jugador1);
        txtTexto3.setText(jugador2);
        txtTexto4.setText(puntuacionJugador1);
        txtTexto5.setText(puntuacionJugador2);
        imgViewFoto.setBackgroundResource(foto);


    }

}
