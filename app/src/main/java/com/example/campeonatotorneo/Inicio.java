package com.example.campeonatotorneo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Inicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Manejar los eventos de selección de la BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        if (item.getItemId() == R.id.menu_ubicacion) {
                            // Abrir el enlace web correspondiente a la opción 1
                            abrirEnlaceWeb("https://www.google.com/maps/place/C.+Libertad,+17,+13200+Manzanares,+Ciudad+Real/@39.001861,-3.3701392,17z/data=!3m1!4b1!4m6!3m5!1s0xd691442a02278dd:0x52a681a0a0968adb!8m2!3d39.0018569!4d-3.3675589!16s%2Fg%2F11c2218yv7?hl=es-ES&entry=ttu");
                            return true;
                        } else if (item.getItemId() == R.id.menu_correo) {
                            abrirEnlaceWeb("http://conclavemanzanares.weebly.com/");
                            return true;
                        } else if (item.getItemId() == R.id.menu_ig) {
                            abrirEnlaceWeb("https://www.instagram.com/conclavejuegosdemesa/?igsh=eHdqcXd1OGhnc3lr");
                            return true;
                        }
                        return false;
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
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

    private void abrirEnlaceWeb(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
