package com.example.practicastanda2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    AlertDialog dialog;
    int almacenamiento;
    TextView texto;
    Button guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        texto = findViewById(R.id.textEdit);
        guardar = findViewById(R.id.buttonGuardar);
        guardar.setOnClickListener(this::onClick);
        //Diálogo para seleccionar el tipo de almacenamiento
        dialog = new AlertDialog.
                Builder(this).
                setTitle("Selecciona almacenamiento:").
                setSingleChoiceItems(R.array.opcionesAlmacenamiento, 0, this::onOpcionClick).
                setPositiveButton("Aceptar", this::onAceptarCancelarClick).
                setNegativeButton("Cancelar", this::onAceptarCancelarClick).
                create();

    }
    //Método del botón que muestra el dialog
    public void onClick(View v) {
        dialog.show();
    }

    private void onOpcionClick(DialogInterface dialogo, int opcion) {
        almacenamiento = opcion;
    }

    private void onAceptarCancelarClick(DialogInterface dialogInterface, int i) {
        try {
            switch (almacenamiento) {
                case 0:
                    guardar(openFileOutput("datos.txt",
                            Context.MODE_PRIVATE | Context.MODE_APPEND));
                    break;
                case 1:
                    File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
                    File file = new File(dir + "/" + "datos.txt");
                    guardar(new FileOutputStream(file, true));
                    break;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        dialog.dismiss();
    }

    private void guardar(FileOutputStream stream) {
        try (PrintWriter out = new PrintWriter(new OutputStreamWriter(stream, StandardCharsets.UTF_8))) {
            out.println(texto.getText().toString());
        }
    }

}
