package com.example.practica15;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    ContentResolver resolver;
    String[] columnas = {CallLog.Calls.TYPE, CallLog.Calls.NUMBER};
    String[] tipos = {"ENTRANTE", "SALIENTE", "PERDIDA"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resolver = getContentResolver();
        textView = findViewById(R.id.textView);
        findViewById(R.id.button).setOnClickListener(this::consultar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CALL_LOG}, 1);
            }
    }
    @SuppressLint({"Range", "SetTextI18n"})
    private void consultar(View view) {
        textView.setText("");
        Cursor cursor = resolver.query(CallLog.Calls.CONTENT_URI, columnas, null, null, null);
        if (cursor.moveToFirst())
            do {
                String tipo = tipos[cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE)) - 1];
                String telefono = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                textView.append(String.format("%s : %s\n", tipo, telefono));
            } while (cursor.moveToNext());
        else
            textView.setText("NO HAY LLAMADAS EN EL HISTORIAL");
        cursor.close();
    }
}