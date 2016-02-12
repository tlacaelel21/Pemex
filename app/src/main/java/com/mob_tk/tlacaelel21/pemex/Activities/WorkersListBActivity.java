package com.mob_tk.tlacaelel21.pemex.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
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
 * Created by tlacaelel21 on 30/10/15.
 */
public class WorkersListBActivity extends Activity {
    ImageButton imageButtonClose;
    ListView lista, listaCont;
    private String listview_array[];
    private String listview_array_cont[];
    int tipo=0;
    SharedPreferences preferences;
    @Override
    protected void onCreate (Bundle savedInstanceState) {

        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workers_list_content);
        Context context=getApplicationContext();
        imageButtonClose=(ImageButton) findViewById(R.id.imageButtonClose);
        preferences = getSharedPreferences("pemex_prefs", MODE_PRIVATE);
        TextView tvTituloWSummary=(TextView) findViewById(R.id.tvTituloWSummary);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            tipo=Integer.parseInt(extras.getString("tipo"));
        }
        if(tipo==1)
            tvTituloWSummary.setText(WorkersListBActivity.this.getString(R.string.title_list_workers_1));
        else
            tvTituloWSummary.setText(WorkersListBActivity.this.getString(R.string.title_rec_comp));

        lista = (ListView) findViewById(R.id.list_workers_black);
        listaCont = (ListView) findViewById(R.id.list_contrac_black);

        imageButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =
                        new Intent(WorkersListBActivity.this, AuditSummary.class);
                startActivity(intent);
                finish();
            }
        });
        /** Obtengo los ids de emp y contr****************************/
        String array[];
        String idsEmpl="", idsContra="";
        if(null!=preferences.getString("nums", "")) {
            if(preferences.getString("nums", "").length()>0){
                int size = preferences.getInt("employ_size", 0);
                array = new String[size];
                for (int i = 0;i<size;i++) {
                    array[i] = preferences.getString("employ_" + i, null);
                    idsEmpl=idsEmpl+","+array[i];
                }
            }
        }
        if(null!=preferences.getString("conts", "")) {
            if(preferences.getString("conts", "").length()>0){
                int size = preferences.getInt("conts_size", 0);
                array = new String[size];
                for (int i = 0;i<size;i++) {
                    array[i] = preferences.getString("conts_" + i, null);
                    idsContra=idsContra+","+array[i];
                }
            }
        }
        idsEmpl=idsEmpl.substring(1,idsEmpl.length());
        idsContra=idsContra.substring(1,idsContra.length());

        /**Se realiza la consulta a la base de datos para empleados y contratistas*/
        String queryEmp="SELECT emp_id,emp_nombre|| ' ' ||emp_app|| ' ' ||emp_apm AS nombre,emp_num_emp "+
                " FROM empleado WHERE emp_num_emp IN ("+idsEmpl+")";
        final ArrayList<HashMap<String, String>> results;
        results= Utils.exeLocalQuery(this, queryEmp);
        listview_array=new String[results.size()];
        for(int idx=0;idx<results.size();idx++){
            String query2="SELECT calif FROM calificaciones " +
                    "WHERE num_emp='"+results.get(idx).get("emp_num_emp")+"'";
            final ArrayList<HashMap<String, String>> empCals;
            empCals= Utils.exeLocalQuery(this, query2);
            if(empCals.size()>0){
                listview_array[idx]="  *  "+results.get(idx).get("nombre");
            }else {
                listview_array[idx]="    "+results.get(idx).get("nombre");
            }
        }
        /********************************* */
        lista.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listview_array));

        /**Se realiza la consulta a la base de datos para empleados y contratistas*/
        String queryCont=" SELECT con_id,con_nombre AS nombre, con_contacto FROM contratista WHERE con_id IN ("+idsContra+")";
        final ArrayList<HashMap<String, String>> resultsConts;
        resultsConts= Utils.exeLocalQuery(this, queryCont);
        listview_array_cont=new String[resultsConts.size()];
        for(int idx=0;idx<resultsConts.size();idx++){
            String query2="SELECT calif FROM calificaciones " +
                    "WHERE num_emp='"+resultsConts.get(idx).get("emp_num_emp")+"'";
            final ArrayList<HashMap<String, String>> empCals;
            empCals= Utils.exeLocalQuery(this, query2);
            if(empCals.size()>0){
                listview_array_cont [idx]="  *  "+resultsConts.get(idx).get("nombre");
            }else {
                listview_array_cont [idx]="    "+resultsConts.get(idx).get("nombre");
            }
        }
        /********************************* */
       listaCont.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listview_array_cont));

        /** ******************************************/

        if(tipo==2){
            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent =
                            new Intent(WorkersListBActivity.this, ActInsSummaryActivity.class);
                    intent.putExtra("tipo", "2");
                    intent.putExtra("id_persona", results.get(position).get("emp_id"));
                    startActivity(intent);
                }
            });
            listaCont.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent =
                            new Intent(WorkersListBActivity.this, ActInsSummaryActivity.class);
                    intent.putExtra("tipo","2");
                    intent.putExtra("id_persona",resultsConts.get(position).get("con_id"));
                    startActivity(intent);
                }
            });

        }
    }
}


