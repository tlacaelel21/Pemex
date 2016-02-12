package com.mob_tk.tlacaelel21.pemex.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.mob_tk.tlacaelel21.pemex.R;
import com.mob_tk.tlacaelel21.pemex.Utilities.Utils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by gabo on 10/02/16.
 */
public class GlosarioDActivity extends Activity {
    String titulo, texto;
    TextView tvTitulo;
    TextView tvTexto;
    private String listview_array[];

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glosario_detalle);
        Context context=getApplicationContext();
        tvTexto = (TextView) findViewById(R.id.dg_texto);
        tvTitulo = (TextView) findViewById(R.id.dg_titulo);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            texto = extras.getString("texto");
            titulo = extras.getString("titulo");
        }
        tvTitulo.setText(titulo);
        tvTexto.setText(texto);

    }
}
