package com.mob_tk.tlacaelel21.pemex.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mob_tk.tlacaelel21.pemex.Adapter.WorkersAdapter;
import com.mob_tk.tlacaelel21.pemex.Model.WorkersModel;
import com.mob_tk.tlacaelel21.pemex.R;
import com.mob_tk.tlacaelel21.pemex.Utilities.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by gabo on 10/02/16.
 */
public class GlosarioActivity extends Activity {
    ArrayAdapter<String> adapter;
    ListView lista;
    private String listview_array[];

    @Override
    protected void onCreate (Bundle savedInstanceState) {

        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glosario);
        Context context=getApplicationContext();
        lista = (ListView) findViewById(R.id.list_glosario1);

        final ArrayList<HashMap<String, String>> results;
        results=Utils.exeLocalQuery(this, "select gli_id, gli_titulo from glosarioIndice where gli_status=2 order by gli_titulo");
        listview_array=new String[results.size()];
        for(int idx=0;idx<results.size();idx++){
            listview_array[idx]=results.get(idx).get("gli_titulo");
        }
        lista.setAdapter(new ArrayAdapter<String>(GlosarioActivity.this, android.R.layout.simple_list_item_1, listview_array));

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(GlosarioActivity.this, GlosarioSActivity.class);
                intent.putExtra("id",results.get(position).get("gli_id"));
                intent.putExtra("titulo", results.get(position).get("gli_titulo"));
                startActivity(intent);
            }
        });
    }
}
