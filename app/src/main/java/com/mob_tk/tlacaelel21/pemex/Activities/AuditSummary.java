package com.mob_tk.tlacaelel21.pemex.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mob_tk.tlacaelel21.pemex.Manifest;
import com.mob_tk.tlacaelel21.pemex.R;
import com.mob_tk.tlacaelel21.pemex.Utilities.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by tlacaelel21 on 30/10/15.
 */
public class AuditSummary extends Activity {
    private SharedPreferences preferences;
    String aud_latitud="",aud_longitud="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit_summary);
        Button btnEmp = (Button)findViewById(R.id.boton_employ);
        Button btnActIns = (Button)findViewById(R.id.boton_act_insec);
        Button btnProcAcc = (Button)findViewById(R.id.boton_proced);
        Button btnRecCom = (Button)findViewById(R.id.boton_recom_com);
        Button btnSave = (Button)findViewById(R.id.boton_save);
        Button btnDwPDF= (Button)findViewById(R.id.boton_dw_pdf);
        Button btnTerm = (Button)findViewById(R.id.btn_terminar);

        preferences = getSharedPreferences("pemex_prefs", MODE_PRIVATE);


        /* Use the LocationManager class to obtain GPS locations */
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        MyLocationListener mlocListener = new MyLocationListener();
        mlocListener.setMainActivity(this);
        if( ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED )
            mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
                (LocationListener) mlocListener);

        btnEmp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent =
                        new Intent(AuditSummary.this, WorkersListBActivity.class);
                intent.putExtra("tipo","1");
                startActivity(intent);
            }
        });
        btnActIns.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent =
                        new Intent(AuditSummary.this, ActInsSummaryActivity.class);
                intent.putExtra("tipo","1");
                startActivity(intent);
            }
        });
        btnProcAcc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent =
                        new Intent(AuditSummary.this, ProcedureActionActivity.class);
                startActivity(intent);
            }
        });
        btnRecCom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent =
                        new Intent(AuditSummary.this, WorkersListBActivity.class);
                intent.putExtra("tipo","2");
                startActivity(intent);
            }
        });

        btnTerm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent =
                        new Intent(AuditSummary.this, MenuActivity.class);
                startActivity(intent);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                saveAuditLocal();
            }
        });
    }

    public void saveAuditLocal(){
        //aud_fecha_inicio|aud_fecha_fin|emp_id_realizo|area_id_realizo|
        // dep_id_realizo|ctr_id_realizo|
        // area_id|dep_id|ctr_id|reg_id|ptr_id|taud_id|aud_semana|aud_instalacion|aud_actividad|
        // aud_latitud|aud_longitud|aud_procedimiento|aud_fortaleza|aud_acciones_inmediatas|emp_id_jefe
        /** Agregando a la BDLocal la auditoria*/
        String aud_fecha_inicio="",aud_fecha_fin="",aud_id="",emp_id_realizo="",area_id_realizo="",
                dep_id_realizo="",ctr_id_realizo,area_id="",dep_id,ctr_id,reg_id,ptr_id,taud_id,
                aud_semana="",aud_instalacion="",aud_actividad="",
                aud_procedimiento="",aud_fortaleza="",aud_acciones_inmediatas="",emp_id_jefe;
        aud_id=getMaxId("aud_id", "auditoria");
        aud_fecha_inicio= getValOfPrefs("audit_date");
        aud_fecha_fin =currentDate();
        emp_id_realizo= getValOfPrefs("emp_id");
        area_id_realizo=getValOfPrefs("area_id");
        dep_id_realizo=getValOfPrefs("dep_id");
        ctr_id_realizo=getValOfPrefs("ctr_id");
        area_id=getValOfPrefs("area_id");
        dep_id=getValOfPrefs("dep_id");
        ctr_id=getValOfPrefs("ctr_id");
        reg_id=getValOfPrefs("reg_id");
        ptr_id=getValOfPrefs("ptr_id");
        taud_id=getValOfPrefs("taud_id");
        aud_semana=getValOfPrefs("audit_week");
        aud_instalacion=getValOfPrefs("inst_id");
        aud_actividad=getValOfPrefs("actividad");
        /*aud_latitud="";
        aud_longitud="";*/
        aud_procedimiento=getValOfPrefs("pa_proc");
        aud_fortaleza=getValOfPrefs("pa_for");
        aud_acciones_inmediatas=getValOfPrefs("pa_acc");
        emp_id_jefe=getValOfPrefs("emp_num_emp");

        Log.i("VAL_aud_id", "-> " + aud_id);
        Log.i("VAL_FI", "-> " + aud_fecha_inicio);
        Log.i("VAL_FF", "-> " + aud_fecha_fin);
        Log.i("VAL_EmpID", "-> " + emp_id_realizo);
        Log.i("VAL_areaR", "-> " + area_id_realizo);
        Log.i("VAL_depR", "-> " + dep_id_realizo);
        Log.i("VAL_ctr_R", "-> " + ctr_id_realizo);
        Log.i("VAL_area", "-> " + area_id);
        Log.i("VAL_dep_id", "-> " + dep_id);
        Log.i("VAL_ctr_id", "-> " + ctr_id);
        Log.i("VAL_reg_id", "-> " + reg_id);
        Log.i("VAL_ptr_id", "-> " + ptr_id);
        Log.i("VAL_taud_id", "-> " + taud_id);
        Log.i("VAL_aud_semana", "-> " + aud_semana);
        Log.i("VAL_aud_instalacion", "-> " + aud_instalacion);
        Log.i("VAL_aud_actividad", "-> " + aud_actividad);
        Log.i("VAL_aud_latitud", "-> " + aud_latitud);
        Log.i("VAL_aud_longitud", "-> " + aud_longitud);
        Log.i("VAL_aud_procedimiento", "-> " + aud_procedimiento);
        Log.i("VAL_aud_fortaleza", "-> " + aud_fortaleza);
        Log.i("VAL_acc_in", "-> " + aud_acciones_inmediatas);
        Log.i("VAL_emp_id_jefe", "-> " + emp_id_jefe);



        String localQuery = "INSERT INTO auditoria (aud_id,aud_fecha_inicio,aud_fecha_fin,emp_id_realizo,area_id_realizo,"+
                "dep_id_realizo,ctr_id_realizo,area_id,dep_id,ctr_id,reg_id,ptr_id,taud_id,aud_semana,aud_instalacion,aud_actividad,"+
                "aud_latitud,aud_longitud,aud_procedimiento,aud_fortaleza,aud_acciones_inmediatas,"+
                "emp_id_jefe)"+
                " VALUES("+aud_id+",'"+aud_fecha_inicio+"','"+aud_fecha_inicio+"',"+emp_id_realizo+","+area_id_realizo+","
                        +dep_id_realizo+","+ctr_id_realizo+","+area_id+","+dep_id+","+ctr_id+","+reg_id+","+ptr_id+","+taud_id+","+aud_semana+","
                        +aud_instalacion+",'"+aud_actividad+"',"+aud_latitud+","+aud_longitud+",'"+aud_procedimiento+"','"+aud_fortaleza+"','"
                        +aud_acciones_inmediatas+"',"+emp_id_jefe+")";
        try {
            Log.i("QUERY_LOCAL-->", "" + localQuery);
            Utils.exeLocalInsQuery(getBaseContext(), localQuery);
        } catch(Exception E) {

        }


        /**Empleados**/
        String idsEmpl="", idsContra="";
        String array[];
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
        idsEmpl=idsEmpl.substring(1,idsEmpl.length());
        String empleados[]=idsEmpl.split(",");

        for(int idx=0;idx<empleados.length;idx++){
            String ae_id=getMaxId("ae_id","auditoriaempleado");
            String queryAuditEmpl="INSERT INTO auditoriaempleado(ae_id,aud_id,emp_id)"+
                        " VALUES("+ae_id+","+aud_id+","+empleados[idx]+")";
            try {
                Utils.exeLocalInsQuery(getBaseContext(), queryAuditEmpl);
            } catch(Exception E) {
            }
        }
        /**Contratistas*/
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
        idsContra=idsContra.substring(1,idsContra.length());
        String contratistas[]=idsContra.split(",");

        for(int idx=0;idx<contratistas.length;idx++){
            String ae_id=getMaxId("ae_id","auditoriaempleado");
            String queryAuditEmpl="INSERT INTO auditoriaempleado(ae_id,aud_id,con_id)"+
                    " VALUES("+ae_id+","+aud_id+","+contratistas[idx]+")";
            try {
                Utils.exeLocalInsQuery(getBaseContext(), queryAuditEmpl);
            } catch(Exception E) {
            }
        }

        /**Actos inseguros*/
        /**Se realiza la consulta a la base de datos */
        final ArrayList<HashMap<String, String>> results;
        results= Utils.exeLocalQuery(this, "SELECT id_acto_inseguro,id_emp,calif,descri FROM calificaciones");
        for(int idx=0;idx<results.size();idx++){
            String queryAI="INSERT INTO auditoriaai(ae_id,ai_id,aa_calif,aa_desc)"+
                    " VALUES("+results.get(idx).get("id_emp")+","+results.get(idx).get("id_acto_inseguro")+","+
                    " "+results.get(idx).get("calif")+",'"+results.get(idx).get("descri")+"')";
            Utils.exeLocalInsQuery(getBaseContext(), queryAI);
        }
        /********************************* */

        /**Querys remotos*/
        /*aud_fecha_inicio|aud_fecha_fin|emp_id_realizo|area_id_realizo|dep_id_realizo|ctr_id_realizo|area_id|
        dep_id|ctr_id|reg_id|ptr_id|taud_id|aud_semana|aud_instalacion|aud_actividad|aud_latitud|aud_longitud|
        aud_procedimiento|aud_fortaleza|aud_acciones_inmediatas|emp_id_jefe*/
        new AsyncTaskUpdate().execute(aud_fecha_inicio,aud_fecha_fin,emp_id_realizo,area_id_realizo,dep_id_realizo,ctr_id_realizo,
                area_id,dep_id,ctr_id,reg_id,ptr_id,taud_id,aud_semana,aud_instalacion,aud_actividad,
                aud_latitud,aud_longitud,aud_procedimiento,aud_fortaleza,aud_acciones_inmediatas,emp_id_jefe);
    }
    public String getValOfPrefs(String variableName){
        String valorP="";
        if(null!=preferences.getString(variableName, "")) {
            if(preferences.getString(variableName, "").length()>0){
                valorP=preferences.getString(variableName, "");
            }else
                valorP="0";
        }
        return valorP;
    }


    public String getMaxId(String campo, String table){
        String maxId="",query="SELECT MAX("+campo+") AS "+campo+" FROM "+table;
        /**Se realiza la consulta a la base de datos */
        final ArrayList<HashMap<String, String>> results;
        results= Utils.exeLocalQuery(this, query);
        for(int idx=0;idx<results.size();idx++){
            maxId=results.get(idx).get(campo);
        }
        if(null!=maxId)
            if (!maxId.equals("null"))
                maxId=""+(Integer.parseInt(maxId)+1);
            else
                maxId="1";
        /** ******************************* */
        return maxId;
    }

    public String currentDate(){
        String fechaAct="";
        Calendar c = Calendar.getInstance();
        //dd-M-yyyy hh:mm:s
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fechaAct = df.format(c.getTime());
        Log.i("FECHA","Fe->"+fechaAct);
        return fechaAct;
    }

    /* Class My Location Listener */
    public class MyLocationListener implements LocationListener {
        AuditSummary mainActivity;

        public AuditSummary getMainActivity() {
            return mainActivity;
        }

        public void setMainActivity(AuditSummary mainActivity) {
            this.mainActivity = mainActivity;
        }

        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la detecci�n de un cambio de ubicacion
            loc.getLatitude();
            loc.getLongitude();
            String Text = "Mi ubicacion actual es: " + "\n Lat = "
                    + loc.getLatitude() + "\n Long = " + loc.getLongitude();
            aud_latitud=""+loc.getLatitude();
            aud_longitud=""+loc.getLongitude();
        }

        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
            Toast.makeText(AuditSummary.this, "GPS Desactivado", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
            //Toast.makeText(AuditSummary.this, "GPS Activado", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // Este metodo se ejecuta cada vez que se detecta un cambio en el
            // status del proveedor de localizacion (GPS)
            // Los diferentes Status son:
            // OUT_OF_SERVICE -> Si el proveedor esta fuera de servicio
            // TEMPORARILY_UNAVAILABLE -> Temporalmente no disponible pero se
            // espera que este disponible en breve
            // AVAILABLE -> Disponible
        }

    }/* End of Class MyLocationListener */

    private class AsyncTaskUpdate extends AsyncTask<String, Void, String> {
        ArrayList<HashMap<String, String>> results;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params){

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List listFields = new ArrayList();
            listFields.add("aud_id");
            //listFields.add("aud_id");
            /*aud_fecha_inicio|aud_fecha_fin|emp_id_realizo|area_id_realizo|dep_id_realizo|ctr_id_realizo|area_id|
        dep_id|ctr_id|reg_id|ptr_id|taud_id|aud_semana|aud_instalacion|aud_actividad|aud_latitud|aud_longitud|
        aud_procedimiento|aud_fortaleza|aud_acciones_inmediatas|emp_id_jefe*/
            String remoteQuery="302|"+params[0] + "|" + params[1] + "|"+ params[2] + "|" + params[3] + "|" + params[4] + "|" +  params[5]
                    + "|" + params[6] + "|" + params[7] + "|" + params[8] + "|" + params[9] + "|" + params[10] + "|" + params[11] + "|" + params[12]+
                    "|" + params[13]+"|" + params[14]+"|" + params[15]+"|" + params[16]+"|" + params[17]+"|" + params[18]+"|" + params[19]+"|" + params[20];

            Log.i("REM_Q","-> "+remoteQuery);
            results = Utils.exeRemoteQuery(getApplicationContext(), remoteQuery,listFields);
            return "";
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //if ((mDialog != null) && mDialog.isShowing()) {
            //    mDialog.dismiss();
            //}

            if (results == null){
                Toast.makeText(getApplicationContext(), "Datos guardados", Toast.LENGTH_LONG).show();
                return;
            }

            if (results.size() == 0) {
                Toast.makeText(getApplicationContext(), "Datos guardados", Toast.LENGTH_LONG).show();
            } else{
                //Resultados
                Boolean bandera = false;
                String aud_id="";
                if (results.size()>0){
                    HashMap<String, String> tuple = results.get(0);
                    Iterator iter = tuple.entrySet().iterator();
                    while (iter.hasNext()) {
                        Map.Entry mEntry = (Map.Entry) iter.next();
                        if (mEntry.getKey().equals("aud_id")){
                            //if(mEntry.getValue().equals("aud_id")){
                                bandera = true;
                                aud_id=""+mEntry.getValue();
                            //}
                        }
                    }
                }

                if (bandera){
                    //guardar localmente
                    //Utils.exeLocalQuery(getApplicationContext(), strSQL);
                    //Utils.exeLocalQuery(getApplicationContext(), strSQL2);

                    Toast.makeText(getApplicationContext(), "Id Auditoria"+aud_id, Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Error al guardar, favor de volver a intentar", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}
