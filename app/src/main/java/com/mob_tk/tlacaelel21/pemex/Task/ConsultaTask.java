package com.mob_tk.tlacaelel21.pemex.Task;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mob_tk.tlacaelel21.pemex.Activities.MainActivity;
import com.mob_tk.tlacaelel21.pemex.R;
import com.mob_tk.tlacaelel21.pemex.Utilities.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tlacaelel21 on 13/01/16.
 */
public class ConsultaTask {
    Activity reference;
    boolean mayExecute;
    int maxProgress, mainProgressValue;


    public ConsultaTask(Activity a) {
        this.reference = a;
    }


    public class TaskContainer extends AsyncTask<String, Void, Void> {
        private String[] tables = {
                "actoinseguro",
                "area",
                "centrotrabajo",
                "contratista",
                "departamento",
                "empleado",
                "glosario",
                "glosarioindice",
                "region",
                "permisotrabajo",
                "sugerenciaai",
                "tipoauditoria",
                "usuario",
                "calificacion",
                "instalacion",
                "comunicacion"
        };


        ArrayList<HashMap<String, String>> results;
        private final Context mContext;
        private ProgressDialog mDialog;
        private int insertados;
        ProgressBar mainProgress;
        TextView synchro_title;
        private boolean finishTask;

        public TaskContainer(Context context) {
            mContext = context;
            mainProgress = (ProgressBar)
                    reference.findViewById(R.id.synchro_main_progress);
            synchro_title= (TextView)
                    reference.findViewById(R.id.synchro_title);

        }


        protected void onPreExecute() {
            mDialog = ProgressDialog
                    .show(mContext, "Espera", "Actualizando...");
            mainProgress.setProgress(0);
        }
        @Override
        protected void onProgressUpdate(Void... values) {

            if(finishTask) {
                mainProgressValue += 1;
                finishTask = false;
                //       mainTask.setText(message);
                mainProgress.setProgress(mainProgressValue);
            } else {
                //     mainTask.setText(message);
                mainProgress.setProgress(mainProgressValue);
            }

        }

        @Override
        protected Void doInBackground(String... params) {


            Log.i("QUERY-->", "-> " + params[0]);
            maxProgress = 5;
            //noinspection ResourceType
            mainProgress.setMax(5);
            mainProgressValue = 0;

            //INICIALIZANDO LA BASE DE DATOS
            //message = "Inicializando base de datos";
            //finishTask = true;
            //noinspection ResourceType
            synchro_title.setText("Actualizando");
            finishTask = true;
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            publishProgress();//metodo nativo

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List listFields ;
            for(int idx=0; idx<tables.length;idx++) {
                listFields=getFields(tables[idx]);
                results = Utils.exeRemoteQuery(reference, "102|"+(idx+1)+"|"+params[0],listFields);
                if(results != null && !results.isEmpty()) {
                    if(persistValue(results, idx, tables[idx],listFields)) {
                        Log.i("RES","BD SUCCESS");
                    }
                }
            }

            updateMobAct();
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            // quitar dialogo de carga
            if ((mDialog != null) && mDialog.isShowing()) {
                mDialog.dismiss();
            }

            // validaciones correspondientes
            if (results == null) {
                Toast.makeText(mContext, "Sin conexión a Internet", Toast.LENGTH_LONG).show();
                Log.i("DESCARGA", "SIN INTERNET");
            } else if (insertados == 0) {
                Toast.makeText(mContext, "No hay datos", Toast.LENGTH_LONG).show();
                Log.i("DESCARGA", "SIN DATOS");
                // ejecución para un caso ideal donde resulto exitoso all
            } else {
                //All is succes
                Toast.makeText(mContext, "Base de datos actualizada", Toast.LENGTH_LONG).show();
            }
            Intent mainIntent = new Intent().setClass(reference, MainActivity.class);
            reference.startActivity(mainIntent);
            reference.finish();
        }

        public List getFields(String table){
            ArrayList<HashMap<String, String>> tablaResults;
            List listFields = new ArrayList();
            //for(int idxT=0; idxT<tables.length;idxT++) {
            tablaResults = Utils.exeLocalQuery(mContext, "pragma table_info('"+table+"') ");
            for (int idx = 0; idx < tablaResults.size(); idx++) {
                //fecha=results.get(idx).get("name");
                Log.i("TABLE-> ", " " + tablaResults.get(idx).get("name"));
                listFields.add(tablaResults.get(idx).get("name"));
            }
            if(table.equals("contratista")){
                Log.i("TAM",""+listFields.size());
                listFields.remove(listFields.size()-1);
            }
            if(table.equals("empleado")){
                Log.i("TAM",""+listFields.size());
                listFields.remove(10);
            }
            if(table.equals("comunicacion")){
                Log.i("TAM",""+listFields.size());
                listFields.remove(listFields.size()-1);
            }

            //}
            return  listFields;
        }


        /*public boolean persistValue(ArrayList<HashMap<String, String>> values,
                                    int scenario, String table, Calendar date) {*/
        public boolean persistValue(ArrayList<HashMap<String, String>> values,
                                    int scenario, String table,List listFields) {
            int index = 0;
            for(HashMap<String, String> tuple: values) {
                insertValues(tuple, table, scenario,listFields);
            }
            return true;
        }

        public boolean insertValues(HashMap<String, String> tuple, String table, int scenario,List listFields) {
            String queryValues = "";
            String queryValues2 = "";

            for(int idx=0; idx<listFields.size();idx++) {
                if(idx==0)
                    queryValues = "(";
                queryValues=queryValues+listFields.get(idx).toString()+",";

            }
            queryValues=queryValues.substring(0,queryValues.length()-1);
            queryValues=queryValues+" ) VALUES";

            /*for(int idx=0; idx<listFields.size();idx++) {
                if(idx==0){
                    queryValues2 = "(";
                    queryValues2=queryValues2+tuple.get(listFields.get(idx).toString())+",";
                }else
                    queryValues2=queryValues2+"'"+tuple.get(listFields.get(idx).toString())+"',";
            }
            queryValues2=queryValues2.substring(0,queryValues2.length()-1);
            queryValues2=queryValues2+ "')";*/

            switch(scenario) {
                case 0:                // actoinseguro
                    queryValues =queryValues+"(" + tuple.get(listFields.get(0).toString()) + ","+
                            "'" + tuple.get(listFields.get(1).toString()) + "',"+
                            "" + tuple.get(listFields.get(2).toString()) + ","+
                            "'" + tuple.get(listFields.get(3).toString()) + "',"+
                            "" + tuple.get(listFields.get(4).toString()) + ","+
                            "'" + dateConverter(tuple.get(listFields.get(5).toString())) + "',"+
                            "'" + dateConverter(tuple.get(listFields.get(6).toString())) + "',"+
                            "'" + tuple.get(listFields.get(7).toString()) + "')";
                break;
                case 1:                // area
                    queryValues =queryValues+"(" + tuple.get(listFields.get(0).toString()) + ","+
                            "'" + tuple.get(listFields.get(1).toString()) + "',"+
                            "" + tuple.get(listFields.get(2).toString()) + ","+
                            "'" + tuple.get(listFields.get(3).toString()) + "',"+
                            "'" + dateConverter(tuple.get(listFields.get(4).toString())) + "',"+
                            "'" + dateConverter(tuple.get(listFields.get(5).toString())) + "')";
                break;
                case 2:                // centro trabajo
                    queryValues =queryValues+"(" + tuple.get(listFields.get(0).toString()) + ","+
                            "'" + tuple.get(listFields.get(1).toString()) + "',"+
                            "" + tuple.get(listFields.get(2).toString()) + ","+
                            "'" + tuple.get(listFields.get(3).toString()) + "',"+
                            "'" + dateConverter(tuple.get(listFields.get(4).toString())) + "',"+
                            "'" + dateConverter(tuple.get(listFields.get(5).toString())) + "')";
                break;
                case 3:                // contratista
                    queryValues =queryValues+"(" + tuple.get(listFields.get(0).toString()) + ","+
                            "'" + tuple.get(listFields.get(1).toString()) + "',"+
                            "'" + tuple.get(listFields.get(2).toString()) + "',"+
                            "'" + tuple.get(listFields.get(3).toString()) + "',"+
                            "" + tuple.get(listFields.get(4).toString()) + ","+
                            "'" + dateConverter(tuple.get(listFields.get(5).toString())) + "',"+
                            "'" + dateConverter(tuple.get(listFields.get(6).toString())) + "')";
                break;
                case 4:                // departamento
                    queryValues =queryValues+"(" + tuple.get(listFields.get(0).toString()) + ","+
                            "'" + tuple.get(listFields.get(1).toString()) + "',"+
                            "" + tuple.get(listFields.get(2).toString()) + ","+
                            "" + tuple.get(listFields.get(3).toString()) + ","+
                            "'" + dateConverter(tuple.get(listFields.get(4).toString())) + "',"+
                            "'" + dateConverter(tuple.get(listFields.get(5).toString())) + "')";
                break;
                case 5:                // empleado
                    queryValues =queryValues+"(" + tuple.get(listFields.get(0).toString()) + ","+
                            "'" + tuple.get(listFields.get(1).toString()) + "',"+
                            "'" + tuple.get(listFields.get(2).toString()) + "',"+
                            "'" + tuple.get(listFields.get(3).toString()) + "',"+
                            "" + tuple.get(listFields.get(4).toString()) + ","+
                            "" + tuple.get(listFields.get(5).toString()) + ","+
                            "'" + dateConverter(tuple.get(listFields.get(6).toString())) + "',"+
                            "'" + dateConverter(tuple.get(listFields.get(7).toString())) + "',"+
                            "" + tuple.get(listFields.get(8).toString()) + ","+
                            "'" + tuple.get(listFields.get(9).toString()) + "',"+
                            //"" + tuple.get(listFields.get(10).toString()) + ","+
                            "" + tuple.get(listFields.get(10).toString()) + ","+
                            "" + tuple.get(listFields.get(11).toString()) + ","+
                            "" + tuple.get(listFields.get(12).toString()) + ","+
                            "'" + tuple.get(listFields.get(13).toString()) + "')";
                break;
                case 6:                // glosario
                    queryValues =queryValues+"(" + tuple.get(listFields.get(0).toString()) + ","+
                            "'" + tuple.get(listFields.get(1).toString()) + "',"+
                            "'" + tuple.get(listFields.get(2).toString()) + "',"+
                            "" + tuple.get(listFields.get(3).toString()) + ","+
                            "" + tuple.get(listFields.get(4).toString()) + ","+
                            "'" + dateConverter(tuple.get(listFields.get(5).toString())) + "',"+
                            "'" + dateConverter(tuple.get(listFields.get(6).toString())) + "')";
                break;
                case 7:                // glosario indice
                    queryValues =queryValues+"(" + tuple.get(listFields.get(0).toString()) + ","+
                            "'" + tuple.get(listFields.get(1).toString()) + "',"+
                            "" + tuple.get(listFields.get(2).toString()) + ","+
                            "'" + dateConverter(tuple.get(listFields.get(3).toString())) + "',"+
                            "'" + dateConverter(tuple.get(listFields.get(4).toString())) + "')";
                break;
                case 8:                // region
                    queryValues =queryValues+"(" + tuple.get(listFields.get(0).toString()) + ","+
                            "'" + tuple.get(listFields.get(1).toString()) + "',"+
                            "" + tuple.get(listFields.get(2).toString()) + ","+
                            "'" + dateConverter(tuple.get(listFields.get(3).toString())) + "',"+
                            "'" + dateConverter(tuple.get(listFields.get(4).toString())) + "')";
                break;
                case 9:                // permisotrabajo
                    queryValues =queryValues+"(" + tuple.get(listFields.get(0).toString()) + ","+
                            "" + tuple.get(listFields.get(1).toString()) + ","+
                            "'" + dateConverter(tuple.get(listFields.get(2).toString())) + "',"+
                            "'" + tuple.get(listFields.get(3).toString()) + "',"+
                            "" + tuple.get(listFields.get(4).toString()) + ","+
                            "'" + dateConverter(tuple.get(listFields.get(5).toString())) + "',"+
                            "'" + dateConverter(tuple.get(listFields.get(6).toString())) + "',"+
                            "'" + tuple.get(listFields.get(7).toString()) + "',"+
                            "'" + tuple.get(listFields.get(8).toString()) + "',"+
                            "'" + tuple.get(listFields.get(9).toString()) + "',"+
                            "'" + tuple.get(listFields.get(10).toString()) + "',"+
                            "'" + tuple.get(listFields.get(11).toString()) + "',"+
                            "'" + tuple.get(listFields.get(12).toString()) + "',"+
                            "'" + tuple.get(listFields.get(13).toString()) + "',"+
                            "'" + tuple.get(listFields.get(14).toString()) + "',"+
                            "'" + tuple.get(listFields.get(15).toString()) + "',"+
                            "'" + tuple.get(listFields.get(16).toString()) + "',"+
                            "'" + dateConverter(tuple.get(listFields.get(17).toString()))+"')";
                break;
                case 10:                // sugerenciaai
                    queryValues =queryValues+"(" + tuple.get(listFields.get(0).toString()) + ","+
                            "'" + tuple.get(listFields.get(1).toString()) + "',"+
                            "" + tuple.get(listFields.get(2).toString()) + ","+
                            "" + tuple.get(listFields.get(3).toString()) + ","+
                            "" + tuple.get(listFields.get(4).toString()) + ","+
                            "'" + dateConverter(tuple.get(listFields.get(5).toString())) + "',"+
                            "'" + dateConverter(tuple.get(listFields.get(6).toString())) + "')";
                break;
                case 11:                //tipoauditoria
                    queryValues =queryValues+"(" + tuple.get(listFields.get(0).toString()) + ","+
                            "'" + tuple.get(listFields.get(1).toString()) + "')";
                    break;

                case 12:                //usuario
                    queryValues =queryValues+"(" + tuple.get(listFields.get(0).toString()) + ","+
                            "" + tuple.get(listFields.get(1).toString()) + ","+
                            "'" + tuple.get(listFields.get(2).toString()) + "',"+
                            "'" + tuple.get(listFields.get(3).toString()) + "',"+
                            "'" + dateConverter(tuple.get(listFields.get(4).toString())) + "',"+
                            "'" + tuple.get(listFields.get(5).toString()) + "')";
                break;

                case 13:                //calificacion
                    queryValues =queryValues+"(" + tuple.get(listFields.get(0).toString()) + ","+
                            "'" + tuple.get(listFields.get(1).toString()) + "',"+
                            "" + tuple.get(listFields.get(2).toString()) + ")";
                break;
                case 14:                //instalacion
                    queryValues =queryValues+"(" + tuple.get(listFields.get(0).toString()) + ","+
                            "" + tuple.get(listFields.get(1).toString()) + ","+
                            "'" + tuple.get(listFields.get(2).toString()) + "',"+
                            "'" + tuple.get(listFields.get(3).toString()) + "',"+
                            "" + tuple.get(listFields.get(4).toString()) + ","+
                            "'" + dateConverter(tuple.get(listFields.get(5).toString())) + "',"+
                            "'" + dateConverter(tuple.get(listFields.get(6).toString())) + "')";
                break;
                case 15:                //comunicacion
                    queryValues =queryValues+"(" + tuple.get(listFields.get(0).toString()) + ","+
                            "'" + tuple.get(listFields.get(1).toString()) + "',"+
                            "'" + dateConverter(tuple.get(listFields.get(2).toString())) + "',"+
                            ""  + tuple.get(listFields.get(3).toString()) + ","+
                            ""  + tuple.get(listFields.get(4).toString()) + ","+
                            ""  + tuple.get(listFields.get(5).toString()) + ","+
                            "'" + dateConverter(tuple.get(listFields.get(6).toString())) + "',"+
                            "'" + dateConverter(tuple.get(listFields.get(7).toString())) + "')";
                    break;
            }
            String localQuery = "INSERT INTO " + table + " " + queryValues;
            try {
                Log.i("QUERY_LOCAL-->",""+localQuery);
                Utils.exeLocalInsQuery(reference, localQuery);
                insertados++;
                Log.i("insert", "executing insert");

                return true;
            } catch(Exception E) {
                Log.i("insert", "executing update instead of insert");
                return false;
            }
        }
    }//TaskContainer

    public String dateConverter(String fecha){
        String formatedDate="";
        fecha = fecha.replace("/Date(", "");
        fecha = fecha.replace(")/", "");
        Long fechaLlega=Long.parseLong(fecha);
        try{
            //SimpleDateFormat originalFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date = new Date(fechaLlega);
            formatedDate = originalFormat.format(date);
        }catch (Exception e){}
        return  formatedDate;
    }
    //mobactualizacion
    public void updateMobAct(){
        //String localQuery = "UPDATE mobactualizacion SET fecha='"+getDate()+"'";
        String localQuery = "UPDATE mobactualizacion SET fecha=datetime()";
        try {
            Log.i("QUERY_LOCAL-->", ""+localQuery);
            Utils.exeLocalInsQuery(reference, localQuery);
            //Log.i("insert", "executing insert");
        } catch(Exception E) {
            //Log.i("insert", "executing update instead of insert");
        }
    }

    private String getDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
    }
}