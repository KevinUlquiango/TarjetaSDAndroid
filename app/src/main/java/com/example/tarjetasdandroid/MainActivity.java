package com.example.tarjetasdandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private EditText nombre, contenido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nombre = findViewById(R.id.edi_nombre_archvio);
        contenido = findViewById(R.id.edi_contenido_archivo);
    }
    //btn guardar

    public void guardar(View view){
        String nombreString = nombre.getText().toString();
        String contenidoString = contenido.getText().toString();

        try {
            //se guaradara temporalmente la ruta
            File tarjetaSD = Environment.getExternalStorageDirectory();
            Toast.makeText(this,tarjetaSD.getPath(),Toast.LENGTH_SHORT).show();
            File rutaArchivo = new File(tarjetaSD.getPath(),nombreString);
            //flujo de salida
            OutputStreamWriter crearArchivo = new OutputStreamWriter(openFileOutput(nombreString, Activity.MODE_PRIVATE));
            crearArchivo.write(contenidoString);
            crearArchivo.flush();
            crearArchivo.close();
            Toast.makeText(this,"Guardado Correctamente",Toast.LENGTH_SHORT).show();
            nombre.setText("");
            contenido.setText("");
        }catch (IOException e){
            Toast.makeText(this,"No se pudo guardar",Toast.LENGTH_SHORT).show();
            System.out.println("Error al guardar archivo, error: " + e);
        }
    }
    //btn consultar
    public void consultar(View view){
        String nombreString = nombre.getText().toString();
        try {
            //se guaradara temporalmente la ruta
            File tarjetaSD = Environment.getExternalStorageDirectory();
            File rutaArchivo = new File(tarjetaSD.getPath(),nombreString);
            InputStreamReader abrirArchivo = new InputStreamReader(openFileInput(nombreString));
            //Leer archivo
            BufferedReader leerArchivo = new BufferedReader(abrirArchivo);
            String linea = leerArchivo.readLine();
            String contenidoCompleto = "";

            while(linea != null ){
                contenidoCompleto += linea + "\n";
                linea = leerArchivo.readLine();
            }
            leerArchivo.close();
            abrirArchivo.close();
            contenido.setText(contenidoCompleto);
        }catch (IOException e){
            Toast.makeText(this,"Error al leer el archivo",Toast.LENGTH_SHORT).show();
            System.out.println("Error al leer el archivo, error: " + e);
        }
    }
}