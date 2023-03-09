package com.example.practica14;


import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    private ContentResolver resolver;
    private AlumnosContentProvider AlumnosProvider;
    private final String[] cols = new String[]{
            BaseColumns._ID,
            AlumnosProvider.COL_NOMBRE,
            AlumnosProvider.COL_APELLIDOS,
            AlumnosProvider.COL_CURSO
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.botonTodos).setOnClickListener(this::consultarTodosClick);
        findViewById(R.id.botonConsultar).setOnClickListener(this::consultarUnoClick);
        findViewById(R.id.insertar).setOnClickListener(this::insertarClick);
        findViewById(R.id.eliminar).setOnClickListener(this::eliminarClick);
        textView = findViewById(R.id.textView);
        resolver = getContentResolver();
    }

    private void consultarTodosClick(View view) {
        actualizarConsulta(resolver.query(AlumnosProvider.TABLA_ALUMNOS_URI, cols, null, null, null));
    }

    private void consultarUnoClick(View view) {
        pedirProcesarId(this::consultar);
    }

    private void insertarClick(View view) {
    }

    private void eliminarClick(View view) {
        pedirProcesarId(this::eliminar);
    }

    private void consultar(long id) {
        Uri uri = ContentUris.withAppendedId(AlumnosProvider.TABLA_ALUMNOS_URI, id);
        actualizarConsulta(resolver.query(uri, cols, null, null, null));
    }


    private void eliminar(long id) {
        Uri uri = ContentUris.withAppendedId(AlumnosProvider.TABLA_ALUMNOS_URI, id);
        int n = resolver.delete(uri, null, null);
        if (n == 1) {
            StringBuilder resultado = new StringBuilder("URI DEL ALUMNO ELIMINADO\n\n");
            resultado.append(uri);
            runOnUiThread(() -> textView.setText(resultado));
        } else
            runOnUiThread(() -> textView.setText("NINGÚN ALUMNO ELIMINADO"));
    }

    private void actualizarConsulta(Cursor cursor) {
        StringBuilder resultado = new StringBuilder("RESULTADO DE LA CONSULTA\n\n");
        if (cursor.moveToFirst()) {
            do {
                resultado.append(cursor.getInt(0));
                resultado.append(": ");
                resultado.append(cursor.getString(1));
                resultado.append(" ");
                resultado.append(cursor.getString(2));
                resultado.append(" (");
                resultado.append(cursor.getString(3));
                resultado.append(")\n");
            } while (cursor.moveToNext());
            runOnUiThread(() -> textView.setText(resultado));
        } else
            runOnUiThread(() -> textView.setText("LA CONSULTA NO RETORNA DATOS"));
    }

    private void pedirProcesarId(Consumer<Long> accion) {
        final View layout = getLayoutInflater().inflate(R.layout.alert_dialog_id_layout, null);
        new AlertDialog.Builder(this).
                setTitle("Identificación del alumno").
                setView(layout).
                setPositiveButton("Aceptar", (d, w) -> {
                    EditText editText = layout.findViewById(R.id.editTextId);
                    long id = Long.parseLong(editText.getText().toString());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        accion.accept(id);
                    }
                }).
                setNegativeButton("Cancelar", (d, w) -> {
                }).
                show();
    }
}