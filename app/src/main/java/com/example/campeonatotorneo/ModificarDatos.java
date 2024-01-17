package com.example.campeonatotorneo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ModificarDatos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_datos);


        Button botonIrAJugadores = findViewById(R.id.modificar_datos_jugadores);
        Button botonIrAPartidas = findViewById(R.id.modificar_datos_partidas);
        botonIrAJugadores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ModificarDatos.this, ModificacionJugadores.class);
                startActivity(intent);
            }
        });

        botonIrAPartidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ModificarDatos.this, ModificacionPartidas.class);
                startActivity(intent);
            }
        });
    }
}