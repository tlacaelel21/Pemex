package com.mob_tk.tlacaelel21.pemex.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mob_tk.tlacaelel21.pemex.Model.AreasModel;
import com.mob_tk.tlacaelel21.pemex.R;
import com.mob_tk.tlacaelel21.pemex.Utilities.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by gabo on 11/02/16.
 */
public class ComunicacionDActivity extends Activity{
    ArrayAdapter<String> adapter;
    ListView lista;
    TextView detalle;
    EditText texto;
    Button btn_enviar;
    private String listview_array[];
    ArrayList<AreasModel> areas;
    String idr;

    @Override
    protected void onCreate (Bundle savedInstanceState) {

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comunicacion_detalle);
        Context context = getApplicationContext();
        lista = (ListView) findViewById(R.id.list_com2);
        detalle = (TextView) findViewById(R.id.com_detalle);
        texto = (EditText) findViewById(R.id.com_text);
        btn_enviar = (Button) findViewById(R.id.com_enviar);


        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            detalle.setText(extras.getString("ci_texto"));
            idr = extras.getString("ci_id");
        }

        areas = new ArrayList<AreasModel>();
        final ArrayList<HashMap<String, String>> results;
        String strSQL = "select ci_id, ci_texto, ci_fecha, c.emp_id, ci_id_padre, emp_num_emp, (emp_nombre || ' ' || emp_app || ' ' || emp_apm) " +
                "emp_nom from comunicacion c left outer join empleado e on c.emp_id = e.emp_id where ci_id_padre = " + idr + " order by ci_update desc";
        results= Utils.exeLocalQuery(this, strSQL);
        for(int idx=0;idx<results.size();idx++){
            String str1 = results.get(idx).get("ci_texto");
            String str2 = results.get(idx).get("ci_fecha").substring(0,16) + " (" +  results.get(idx).get("emp_num_emp") + ") " + results.get(idx).get("emp_nom");
            areas.add( new AreasModel(str1,str2));
        }

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

        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (texto.getText().toString().length()>0){
                    enviar();
                }else{
                    Toast.makeText(ComunicacionDActivity.this, "No puede enviar un mensaje vacío", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void enviar(){
        SharedPreferences prefs = getSharedPreferences("pemex_prefs", MODE_PRIVATE);
        String emp_num = prefs.getString("emp_num", "");
        String emp_id = prefs.getString("emp_id", "");
        String usu_id = prefs.getString("id_usuario_sistema", "");

        new AsyncTaskEnviar().execute(idr, emp_id, texto.getText().toString());
    }

    private class AsyncTaskEnviar extends AsyncTask<String, Void, String> {
        ArrayList<HashMap<String, String>> results;
        String strSQL;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //mDialog = ProgressDialog.show(context, "Espera", "Descargando...");
            //mainProgress.setProgress(0);
        }

        @Override
        protected String doInBackground(String... params){
            //finishTask = true;
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List listFields = new ArrayList();
            listFields.add("ci_id");
            listFields.add("ci_id");
            strSQL = "'" + params[2] + "', datetime(), " + params[0] + ", " + params[1] + ", 1, datetime(), datetime(), ";

            Date fecha = new Date();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            results = Utils.exeRemoteQuery(getApplicationContext(), "310|" + params[0] + "|" + params[1] + "|"+ params[2] + "|" + df.format(fecha) ,listFields);
            return "";
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //if ((mDialog != null) && mDialog.isShowing()) {
            //    mDialog.dismiss();
            //}

            if (results == null){
                Toast.makeText(getApplicationContext(), "Error al guardar (null), favor de volver a intentar", Toast.LENGTH_LONG).show();
                return;
            }

            if (results.size() == 0) {
                Toast.makeText(getApplicationContext(), "Error al guardar (vacio), favor de volver a intentar", Toast.LENGTH_LONG).show();
            } else{
                //Resultados
                String ci_id = "";
                if (results.size()>0){
                    HashMap<String, String> tuple = results.get(0);
                    Iterator iter = tuple.entrySet().iterator();
                    while (iter.hasNext()) {
                        Map.Entry mEntry = (Map.Entry) iter.next();
                        if (mEntry.getKey().equals("ci_id")){
                            ci_id = mEntry.getValue().toString();
                        }
                    }
                }

                if (ci_id.length()>0){
                    strSQL = "insert into comunicacion (ci_id, ci_texto, ci_fecha, emp_id, ci_id_padre, ci_status, ci_insert, ci_update, mob_status) values (" + ci_id + ", " + strSQL +" 0);";
                }else{
                    strSQL = "insert into comunicacion (ci_id, ci_texto, ci_fecha, emp_id, ci_id_padre, ci_status, ci_insert, ci_update, mob_status) select coalesce(max(ci_id), 0)+1, " + strSQL +" 1;";
                }


                Utils.exeLocalQuery(getApplicationContext(), strSQL);
                Toast.makeText(getApplicationContext(), "Mensaje enviado.", Toast.LENGTH_LONG).show();
            }
        }
    }

}
