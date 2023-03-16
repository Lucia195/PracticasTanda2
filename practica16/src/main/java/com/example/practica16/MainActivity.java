package com.example.practica16;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        SQLiteDatabase.deleteDatabase(getApplicationContext().getDatabasePath("biblioteca.db"));
        SpannableStringBuilder resultado = new SpannableStringBuilder("RESULTADO DE LA CONSULTA\n\n");
        resultado.setSpan(new UnderlineSpan(), 0, resultado.length(),
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        BibliotecaSQLiteHelper helper = new BibliotecaSQLiteHelper(this, 1);
        SQLiteDatabase db = helper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("select * from ejemplares", null);
            if (cursor.moveToFirst())
                do {
                    resultado.append(cursor.getString(1));
                    resultado.append("\n");
                    resultado.append(cursor.getString(2));
                    resultado.append("\n");
                    resultado.append(String.valueOf(cursor.getInt(3)));
                    resultado.append("\n\n");
                } while (cursor.moveToNext());
            else
                resultado.append("no hay datos");
            cursor.close();
        } catch (SQLiteException e) {
            int l = resultado.length();
            resultado.append("Error: ");
            resultado.append(e.getLocalizedMessage());
            resultado.setSpan(new ForegroundColorSpan(Color.RED), l, resultado.length(),
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        textView.setText(resultado);
        db.close();
    }
}