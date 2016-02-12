package com.mob_tk.tlacaelel21.pemex.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mob_tk.tlacaelel21.pemex.Model.AreasModel;
import com.mob_tk.tlacaelel21.pemex.R;
import com.mob_tk.tlacaelel21.pemex.Utilities.Utils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by gabo on 11/02/16.
 */
public class ComunicacionActivity extends Activity{
    ArrayAdapter<String> adapter;
    ListView lista;
    private String listview_array[];
    ArrayList<AreasModel> areas;

    @Override
    protected void onCreate (Bundle savedInstanceState) {

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comunicacion);
        Context context=getApplicationContext();
        lista = (ListView) findViewById(R.id.list_com1);

        areas = new ArrayList<AreasModel>();
        final ArrayList<HashMap<String, String>> results;
        String strSQL = "select ci_id, ci_texto, ci_fecha, c.emp_id, ci_id_padre, emp_num_emp, " +
                "(emp_nombre || ' ' || emp_app || ' ' || emp_apm) emp_nom from comunicacion c " +
                "left outer join empleado e on c.emp_id = e.emp_id where ci_id_padre = 0 order by ci_update desc;";
        results= Utils.exeLocalQuery(this, strSQL);
        for(int idx=0;idx<results.size();idx++){
            String str1 = results.get(idx).get("ci_texto");
            String str2 = results.get(idx).get("ci_fecha").substring(0,16) + " (" +  results.get(idx).get("emp_num_emp") + ") " + results.get(idx).get("emp_nom");
            areas.add( new AreasModel(str1,str2));
        }
        //lista.setAdapter(new ArrayAdapter<String>(ComunicacionActivity.this, android.R.layout.simple_list_item_1, listview_array));
        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_2, android.R.id.text1, areas) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(areas.get(position).getAreaTitulo());
                text2.setText(areas.get(position).getAreaDato());

                text1.setTextColor(Color.parseColor("#000000"));
                text2.setTextColor(Color.parseColor("#000000"));
                text2.setTextSize(10);
                return view;
            }
        };
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ComunicacionActivity.this, ComunicacionDActivity.class);
                intent.putExtra("ci_id",results.get(position).get("ci_id"));
                intent.putExtra("ci_texto", results.get(position).get("ci_texto"));
                startActivity(intent);
            }
        });
    }

}
