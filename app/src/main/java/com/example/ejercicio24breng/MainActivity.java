package com.example.ejercicio24breng;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ejercicio24breng.transacciones.Transacciones;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    EditText txtdescripcion;
    Button btnsalvar, btn_firmas;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtdescripcion = (EditText) findViewById(R.id.txtdescripcion);
        view = (View) findViewById(R.id.viewfirma);

        btnsalvar = (Button)findViewById(R.id.btnguardar);
        btnsalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Salvarfirma();


            }
        });

        btn_firmas = (Button)findViewById(R.id.btnfirmas);
        btn_firmas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityGalery.class);
                startActivity(intent);
            }
        });
    }

    public void Salvarfirma(){

        SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDataBase, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        try{

            ContentValues valores = new ContentValues();

            valores.put(Transacciones.image, Viewfirma(view));
            valores.put(Transacciones.descripcion, txtdescripcion.getText().toString());

            Long resultado = db.insert(Transacciones.tabla_firmas, Transacciones.id, valores);

            Toast.makeText(getApplicationContext(), "Se ingreso la Firma: " + resultado.toString(), Toast.LENGTH_LONG).show();

            ClearScreen();

        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

    public static byte[]  Viewfirma(View view5) {
        view5.setDrawingCacheEnabled(true);
        Bitmap bitmap = view5.getDrawingCache();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        byte[] byteArray = stream.toByteArray();
        return byteArray;

    }

    private void ClearScreen() {
        txtdescripcion.setText("");
        view.setDrawingCacheEnabled(false);
    }
}