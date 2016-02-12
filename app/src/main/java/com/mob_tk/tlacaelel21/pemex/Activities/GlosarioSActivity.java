package com.mob_tk.tlacaelel21.pemex.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mob_tk.tlacaelel21.pemex.R;
import com.mob_tk.tlacaelel21.pemex.Utilities.Utils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by gabo on 10/02/16.
 */
public class GlosarioSActivity extends Activity {
    String titulo, idr;
    ListView lista;
    TextView tvTitulo;
    private String listview_array[];

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glosario_sub);
        Context context=getApplicationContext();
        lista = (ListView) findViewById(R.id.list_glosario2);
        tvTitulo = (TextView) findViewById(R.id.sg_titulo);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            idr = extras.getString("id");
            titulo = extras.getString("titulo");
        }
        tvTitulo.setText(titulo);

        final ArrayList<HashMap<String, String>> results;
        results= Utils.exeLocalQuery(this, "select glo_titulo, glo_texto from glosario where gli_indice = " + idr + " and glo_status=2 order by glo_titulo");
        listview_array=new String[results.size()];
        for(int idx=0;idx<results.size();idx++){
            listview_array[idx]=results.get(idx).get("glo_titulo");
        }
        lista.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listview_array));

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(GlosarioSActivity.this, GlosarioDActivity.class);
                intent.putExtra("titulo",results.get(position).get("glo_titulo"));
                intent.putExtra("texto",results.get(position).get("glo_texto"));
                startActivity(intent);
            }
        });
    }
}
