package com.mob_tk.tlacaelel21.pemex.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mob_tk.tlacaelel21.pemex.Adapter.AreasAdapter;
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
 * Created by gabo on 10/02/16.
 */
public class AlertasActivity extends Activity {
    ArrayAdapter<String> adapter;
    ListView lista;
    private String listview_array[];
    ContextThemeWrapper ctw;
    Context context;
    ArrayList<AreasModel> areas;

    @Override
    protected void onCreate (Bundle savedInstanceState) {

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alertas);
        context=getApplicationContext();
        ctw = new ContextThemeWrapper( this, R.style.AlertDialogCustom);

        SharedPreferences prefs = getSharedPreferences("pemex_prefs", MODE_PRIVATE);
        String idr = prefs.getString("emp_num", "");
        new AsyncTaskSelect().execute(idr);
    }


    public class AsyncTaskSelect extends AsyncTask<String, Void, String> {
        private ProgressDialog mDialog;
        ProgressBar mainProgress;
        private boolean finishTask;
        TextView synchro_title;
        Activity reference;
        ArrayList<HashMap<String, String>> results;

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
            listFields.add("alt_fecha");
            listFields.add("alt_mensaje");
            results = Utils.exeRemoteQuery(getApplicationContext(), "205|"+params[0],listFields);

            return "";
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //if ((mDialog != null) && mDialog.isShowing()) {
            //    mDialog.dismiss();
            //}

            if (results.size() == 0) {
                Toast.makeText(getApplicationContext(), "Sin resultados",
                        Toast.LENGTH_SHORT).show();
                finish();
            } else{
                //Resultados
                lista = (ListView) findViewById(R.id.list_alertas);

                AreasModel area;
                //final ArrayList<AreasModel> areas = new ArrayList<AreasModel>();
                areas = new ArrayList<AreasModel>();
                String fecha, mensaje;

                for(HashMap<String, String> tuple: results) {
                    fecha = "";
                    mensaje = "";
                    Iterator iter = tuple.entrySet().iterator();
                    while (iter.hasNext()) {
                        Map.Entry mEntry = (Map.Entry) iter.next();
                        if (mEntry.getKey().equals("alt_fecha")){
                            fecha = (String)mEntry.getValue();
                            fecha = fecha.substring(6,19);
                            Date d = new Date(Long.parseLong(fecha));
                            DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                            fecha = df.format(d);
                        }else{
                            mensaje = (String)mEntry.getValue();
                        }
                    }
                    area = new AreasModel(mensaje,fecha);
                    areas.add(area);
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
                        return view;
                    }
                };
                lista.setAdapter(adapter);



                lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        AlertDialog alertDialog = new AlertDialog.Builder(ctw).create();
                        alertDialog.setTitle(areas.get(position).getAreaDato());
                        alertDialog.setMessage(areas.get(position).getAreaTitulo());
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "  ACEPTAR    ",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                });
            }
        }
    }
}
