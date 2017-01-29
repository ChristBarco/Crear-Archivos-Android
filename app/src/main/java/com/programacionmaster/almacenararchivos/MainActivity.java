package com.programacionmaster.almacenararchivos;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;

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
            crearDirectorio(this, "base_datos");
        } else {
            Log.e("ErrorExternal", "Almacenamiento externo no tiene permisos");
        }
    }

    public File crearDirectorio(Context context, String nombre) {
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_DOWNLOADS), nombre);
        if (!file.mkdirs()) {
            Log.e(LOG_TAG, "Directorio no creado");
        }
        return file;
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
