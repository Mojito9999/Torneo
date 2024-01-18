package com.example.campeonatotorneo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class GestionDatos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_datos);
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