package com.example.campeonatotorneo;
import android.provider.BaseColumns;

public final class EstructuraBBDD {

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS "+ EstructuraCampeonatoTorneo.TABLE_NAME_TORNEO +
                    "(" + EstructuraCampeonatoTorneo._ID + " integer PRIMARY KEY, "
                    + EstructuraCampeonatoTorneo.COLUMN_NOMBRE_JUGADOR+ " text, "
                    + EstructuraCampeonatoTorneo.COLUMN_CIUDAD + " text, "
                    + EstructuraCampeonatoTorneo.COLUMN_PARTIDAS_GANADAS + " integer, "
                    + EstructuraCampeonatoTorneo.COLUMN_FOTO_JUGADOR + " blob)";

    public static final String SQL_CREATE_PARTIDA =
            "CREATE TABLE IF NOT EXISTS "+ EstructuraPartida.TABLE_NAME_PARTIDA +
                    "("+ EstructuraPartida._ID + " integer PRIMARY KEY,"
                    + EstructuraPartida.COLUMN_NUM_ENCUENTRO+ " integer, "
                    + EstructuraPartida.COLUMN_FECHA + " text, "
                    + EstructuraPartida.COLUMN_JUGADOR_1 + " integer, "
                    + EstructuraPartida.COLUMN_JUGADOR_2+ " integer, "
                    + EstructuraPartida.COLUMN_PUNTUACION_JUGADOR_1 + " integer, "
                    + EstructuraPartida.COLUMN_PUNTUACION_JUGADOR_2+ " integer)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS  " + EstructuraCampeonatoTorneo.TABLE_NAME_TORNEO;

    public static final String SQL_DELETE_PARTIDA =
            "DROP TABLE IF EXISTS  " + EstructuraPartida.TABLE_NAME_PARTIDA;
    private EstructuraBBDD() {}

    /* Clase interna que define la estructura de la tabla de operas */
    public static class EstructuraCampeonatoTorneo implements BaseColumns {
        public static final String TABLE_NAME_TORNEO = "torneo";
        public static final String COLUMN_NOMBRE_JUGADOR = "nombre";
        public static final String COLUMN_CIUDAD = "ciudad";
        public static final String COLUMN_PARTIDAS_GANADAS = "partidas_ganadas";
        public static final String COLUMN_FOTO_JUGADOR = "foto";

    }

    public static class EstructuraPartida implements BaseColumns {

        public static final String TABLE_NAME_PARTIDA = "partida";
        public static final String COLUMN_NUM_ENCUENTRO = "numero_encuentro";
        public static final String COLUMN_FECHA = "fecha";
        public static final String COLUMN_JUGADOR_1 ="jugador1";
        public static final String COLUMN_JUGADOR_2 ="jugador2";
        public static final String COLUMN_PUNTUACION_JUGADOR_1 ="puntuacion_jugador1";
        public static final String COLUMN_PUNTUACION_JUGADOR_2 ="puntuacion_jugador2";

    }
}
