package com.programacionmaster.almacenararchivos;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public String LOG_TAG = "Log_getAlbum";
    Button buttonCrearCarpeta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonCrearCarpeta = (Button) findViewById(R.id.buttonCrearCarpeta);

        buttonCrearCarpeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearDirectorioArchivo();
            }
        });

    }

    public void crearDirectorioArchivo() {
        if (isExternalStorageWritable() && isExternalStorageReadable()) {

            if (Environment.isExternalStorageRemovable()) {
                Log.i("isExtStorRemovable", "true");
            } else {
                Log.i("isExtStorRemovable", "false");
                File directorio = crearDirectorio(this, "base_datos");
                crearArchivo(this, "archivo_prueba", "Cuerpo de archivo Prueba", directorio);
            }
        } else {
            Log.e("ErrorExternal", "Almacenamiento externo no tiene permisos");
        }
    }

    /**
     * Crea un directorio en el almacenamiento externo.
     *
     * @param context
     * @param nombre
     * @return
     */
    public File crearDirectorio(Context context, String nombre) {
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_DOCUMENTS), nombre);
        if (!file.mkdirs()) {
            Log.e(LOG_TAG, "Directorio no creado");
        }
        return file;
    }

    /**
     * Crea archivo de texto plano en un directorio especificado.
     *
     * @param context
     * @param nombre
     * @param cuerpo
     * @param directorio
     */
    public void crearArchivo(Context context, String nombre, String cuerpo, File directorio) {
        try {
            File gpxfile = new File(directorio, nombre);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(cuerpo);
            writer.flush();
            writer.close();
            Log.i("CreacionArchivo", "Archivo Creado en: " + directorio.getAbsolutePath());
            Toast.makeText(context, "Archivo Creado en: " + directorio.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if external storage is available for read and write
     *
     * @return
     */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if external storage is available to at least read
     *
     * @return
     */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}
