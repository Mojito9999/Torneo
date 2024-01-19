package com.example.campeonatotorneo;

import androidx.appcompat.app.AppCompatActivity;

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

public class ConsultaJugadores extends AppCompatActivity implements AdapterView.OnItemClickListener {

    TextView txtTexto1, txtTexto2,txtTexto3;
    SQLiteDatabase db;
    SQLiteHelper helper;
    ListView lv;
    ImageView imgViewFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_jugadores);

        txtTexto1 = findViewById(R.id.textView2);
        txtTexto2 = findViewById(R.id.textView3);
        txtTexto3 = findViewById(R.id.textView4);
        imgViewFoto = findViewById(R.id.imageView);
        lv = findViewById(R.id.lstJugador_consulta);

        //realizamos la consulta
        consultaTorneo();

        lv.setOnItemClickListener(this);

    }

    private void consultaTorneo() {
        helper = new SQLiteHelper(this);

        db = helper.getReadableDatabase();
        Cursor cursor = db.query(EstructuraBBDD.EstructuraCampeonatoTorneo.TABLE_NAME_TORNEO, null, null, null, null, null, null);

        //adaptamos el cursor a nuestro ListView

        String[] from = {EstructuraBBDD.EstructuraCampeonatoTorneo.COLUMN_NOMBRE_JUGADOR, EstructuraBBDD.EstructuraCampeonatoTorneo.COLUMN_CIUDAD,EstructuraBBDD.EstructuraCampeonatoTorneo.COLUMN_PARTIDAS_GANADAS, EstructuraBBDD.EstructuraCampeonatoTorneo.COLUMN_FOTO_JUGADOR};
        int[] to = {R.id.textView2, R.id.textView3,R.id.textView4,R.id.imageView};

        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(this, R.layout.lista, cursor, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        lv.setAdapter(adaptador);

        db.close();
    }

    @Override
    public void onItemClick(AdapterView<?> listView, View view, int position, long id) {

        //obtenemos el objeto que se ha pulsado, que en nuestro caso será de tipo Cursor
        Cursor cursor=(Cursor) listView.getItemAtPosition(position);
        int _id=cursor.getInt(0);
        String jugador=cursor.getString(1) ;
        String ciudad=cursor.getString(2 ) ;
        int partidasGanadas=cursor.getInt(4);
        int foto= cursor.getInt(3);
        //mostramos los datos en los cuadros de texto de la parte superior del layout
        txtTexto1.setText(_id +", "+jugador);
        txtTexto2.setText(ciudad);
        txtTexto3.setText(partidasGanadas);
        imgViewFoto.setBackgroundResource(foto);


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