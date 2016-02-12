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
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
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
public class PerfilActivity extends Activity {
    Button btnRegion, btnCentro, btnDepto, btnArea, btnGuardar;
    EditText txtNombre, txtApp, txtApm, txtPuesto, txtEmail, txtPsw1, txtPsw2;
    String reg_id, ctr_id, area_id, dep_id;
    TextView lblNum;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Context context=getApplicationContext();

        btnRegion = (Button) findViewById(R.id.pf_reg);
        btnCentro = (Button) findViewById(R.id.pf_ctr);
        btnDepto = (Button) findViewById(R.id.pf_dep);
        btnArea = (Button) findViewById(R.id.pf_area);
        btnGuardar = (Button) findViewById(R.id.pf_guardar);
        lblNum = (TextView) findViewById(R.id.pf_num);
        txtNombre = (EditText) findViewById(R.id.pf_nombre);
        txtApp = (EditText) findViewById(R.id.pf_app);
        txtApm = (EditText) findViewById(R.id.pf_apm);
        txtPuesto = (EditText) findViewById(R.id.pf_puesto);
        txtEmail = (EditText) findViewById(R.id.pf_email);
        txtPsw1 = (EditText) findViewById(R.id.pf_psw1);
        txtPsw2 = (EditText) findViewById(R.id.pf_psw2);

        SharedPreferences prefs = getSharedPreferences("pemex_prefs", MODE_PRIVATE);
        String idr = prefs.getString("emp_num", "");
        lblNum.setText(idr);
        final ArrayList<HashMap<String, String>> results;
        String strSQL = "select u.id_usuario_sistema, id_perfiles, name, emp_id, emp_nombre, emp_app, emp_apm, " +
                "emp_num_emp, emp_status, emp_puesto, e.reg_id, e.ctr_id, e.dep_id, e.area_id, nip, username, " +
                "reg_desc, ctr_desc, dep_desc, area_desc " +
                "from usuario u " +
                "left outer join empleado e on u.id_usuario_sistema = e.id_usuario_sistema " +
                "left outer join region r on e.reg_id = r.reg_id " +
                "left outer join centrotrabajo c on e.ctr_id = c.ctr_id " +
                "left outer join departamento d on e.dep_id = d.dep_id " +
                "left outer join area a on e.area_id = a.area_id " +
                "where emp_num_emp = '" + idr + "'";
        results= Utils.exeLocalQuery(this, strSQL);
        if(results.size()>0){
            txtNombre.setText(results.get(0).get("emp_nombre"));
            txtApp.setText(results.get(0).get("emp_app"));
            txtApm.setText(results.get(0).get("emp_apm"));
            txtPuesto.setText(results.get(0).get("emp_puesto"));
            txtEmail.setText(results.get(0).get("username"));
            txtPsw1.setText(results.get(0).get("nip"));
            txtPsw2.setText(results.get(0).get("nip"));

            String reg, ctr, area, dep;
            reg = results.get(0).get("reg_desc");
            ctr = results.get(0).get("ctr_desc");
            area = results.get(0).get("area_desc");
            dep = results.get(0).get("dep_desc");
            if (reg.equals("null")){
                reg_id = "0";
                btnRegion.setText("");
            }else{
                reg_id = results.get(0).get("reg_id");
                btnRegion.setText(reg);
            }
            if (ctr.equals("null")){
                ctr_id = "0";
                btnCentro.setText("");
            }else{
                ctr_id = results.get(0).get("ctr_id");
                btnCentro.setText(ctr);
            }
            if (dep.equals("null")){
                dep_id = "0";
                btnDepto.setText("");
            }else{
                dep_id = results.get(0).get("dep_id");
                btnDepto.setText(dep);
            }
            if (area.equals("null")){
                area_id = "0";
                btnArea.setText("");
            }else{
                area_id = results.get(0).get("reg_id");
                btnArea.setText(area);
            }
        }

        btnRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PerfilActivity.this, GeneralContainerActivity2.class);
                intent.putExtra("query","SELECT reg_id, reg_desc FROM region WHERE reg_status = 1");
                intent.putExtra("titulo","Region");
                intent.putExtra("campo","reg_desc");
                intent.putExtra("clave","reg_id");
                startActivityForResult(intent, 1);
                //finish();
            }
        });

        btnCentro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reg_id.equals(0)){
                    Toast.makeText(PerfilActivity.this, "Primero debe seleccionar Región", Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(PerfilActivity.this, GeneralContainerActivity2.class);
                    intent.putExtra("query","SELECT ctr_id, ctr_desc FROM centroTrabajo WHERE ctr_status = 1 AND reg_id = " + reg_id);
                    intent.putExtra("titulo","Centro de Trabajo");
                    intent.putExtra("campo","ctr_desc");
                    intent.putExtra("clave","ctr_id");
                    startActivityForResult(intent, 2);
                    //finish();
                }
            }
        });

        btnDepto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ctr_id.equals(0)){
                    Toast.makeText(PerfilActivity.this, "Primero debe seleccionar Centro de trabajo", Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(PerfilActivity.this, GeneralContainerActivity2.class);
                    intent.putExtra("query","SELECT dep_id, dep_desc FROM departamento WHERE dep_status = 1 AND ctr_id = " + ctr_id);
                    intent.putExtra("titulo","Departamento");
                    intent.putExtra("campo","dep_desc");
                    intent.putExtra("clave","dep_id");
                    startActivityForResult(intent,3);
                    //finish();
                }
            }
        });

        btnArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dep_id.equals(0)){
                    Toast.makeText(PerfilActivity.this, "Primero debe seleccionar Departamento", Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(PerfilActivity.this, GeneralContainerActivity2.class);
                    intent.putExtra("query","SELECT area_id, area_desc FROM area WHERE area_status = 1 AND dep_id = " + dep_id);
                    intent.putExtra("titulo","Area");
                    intent.putExtra("campo","area_desc");
                    intent.putExtra("clave","area_id");
                    startActivityForResult(intent,4);
                    //finish();
                }
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String ident;
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                ident = data.getStringExtra("clave");
                if (!reg_id.equals(ident)){
                    reg_id = ident;
                    btnRegion.setText(data.getStringExtra("desc"));

                    ctr_id = "0";
                    dep_id = "0";
                    area_id = "0";
                    btnCentro.setText("");
                    btnDepto.setText("");
                    btnArea.setText("");
                }
            }
        }else if (requestCode == 2) {
            if(resultCode == RESULT_OK){
                ident = data.getStringExtra("clave");
                if (!ctr_id.equals(ident)){
                    ctr_id = ident;
                    btnCentro.setText(data.getStringExtra("desc"));

                    dep_id = "0";
                    area_id = "0";
                    btnDepto.setText("");
                    btnArea.setText("");
                }
            }
        }else if (requestCode == 3) {
            if(resultCode == RESULT_OK){
                ident = data.getStringExtra("clave");
                if (!dep_id.equals(ident)){
                    dep_id = ident;
                    btnDepto.setText(data.getStringExtra("desc"));

                    area_id = "0";
                    btnArea.setText("");
                }
            }
        }else if (requestCode == 4) {
            if(resultCode == RESULT_OK){
                area_id = data.getStringExtra("clave");
                btnArea.setText(data.getStringExtra("desc"));
            }
        }
    }

    private void guardar(){
        if(validar()){
            SharedPreferences prefs = getSharedPreferences("pemex_prefs", MODE_PRIVATE);
            String emp_num = prefs.getString("emp_num", "");
            String emp_id = prefs.getString("emp_id", "");
            String usu_id = prefs.getString("id_usuario_sistema", "");

            new AsyncTaskUpdate().execute(emp_id, usu_id, txtNombre.getText().toString(), txtApp.getText().toString(),
                    txtApm.getText().toString(), emp_num, txtPuesto.getText().toString(), reg_id,
                    ctr_id, dep_id, area_id, txtPsw1.getText().toString(), txtEmail.getText().toString());

        }
    }

    private boolean validar(){
        boolean bandera = true;
        String mensaje = "";

        if (ctr_id.equals("0")) {
            mensaje = mensaje + "Debe seleccionar al menos centro de trabajo.\n";
        }
        if (txtNombre.getText().toString().length()<=0) {
            mensaje = mensaje + "Debe introducir el nombre.\n";
        }
        if (txtApp.getText().toString().length()<=0) {
            mensaje = mensaje + "Debe introducir el apellido paterno.\n";
        }
        if (txtApm.getText().toString().length()<=0) {
            mensaje = mensaje + "Debe introducir el apellido materno.\n";
        }
        if (txtEmail.getText().toString().length()<=0) {
            mensaje = mensaje + "Debe introducir el número de empleado.\n";
        }
        if (txtPuesto.getText().toString().length()<=0) {
            mensaje = mensaje + "Debe introducir el puesto.\n";
        }
        if (txtPsw1.getText().toString().length()<=0) {
            mensaje = mensaje + "Debe introducir la contraseña.\n";
        }
        if (txtPsw2.getText().toString().length()<=0) {
            mensaje = mensaje + "Debe confirmar la contraseña.\n";
        }
        if (!txtPsw1.getText().toString().toString().equals(txtPsw2.getText().toString())) {
            mensaje = mensaje + "Las contraseñas no coinciden.\n";
        }

        if (mensaje.length()>0) {
            bandera = false;
            Toast.makeText(PerfilActivity.this, mensaje, Toast.LENGTH_LONG).show();
        }

        return bandera;
    }

    private class AsyncTaskUpdate extends AsyncTask<String, Void, String> {
        private ProgressDialog mDialog;
        ProgressBar mainProgress;
        private boolean finishTask;
        TextView synchro_title;
        Activity reference;
        ArrayList<HashMap<String, String>> results;
        String strSQL, strSQL2;

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
            listFields.add("OK");
            listFields.add("OK");
            strSQL ="UPDATE empleado set emp_nombre='" + params[2] + "', emp_app='" + params[3] +
                    "', emp_apm='" + params[4] + "', emp_num_emp=" + params[5] + ", emp_puesto='" + params[6] +
                    "', reg_id=" + params[7] + ", ctr_id=" + params[8] + ", dep_id=" + params[9] +
                    ", area_id=" + params[10] + " where emp_id=" + params[0];
            strSQL2 = "UPDATE usuario set nip='" + params[11] + "', username='" + params[12] + "' where id_usuario_sistema=" + params[1];

            results = Utils.exeRemoteQuery(getApplicationContext(), "301|" + params[0] + "|" + params[1] + "|"+ params[2] + "|" + params[3] + "|" + params[4] + "|" +  params[5]
                    + "|" + params[6] + "|" + params[7] + "|" + params[8] + "|" + params[9] + "|" + params[10] + "|" + params[11] + "|" + params[12],listFields);
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
                Boolean bandera = false;
                if (results.size()>0){
                    HashMap<String, String> tuple = results.get(0);
                    Iterator iter = tuple.entrySet().iterator();
                    while (iter.hasNext()) {
                        Map.Entry mEntry = (Map.Entry) iter.next();
                        if (mEntry.getKey().equals("OK")){
                            if(mEntry.getValue().equals("OK")){
                                bandera = true;
                            }
                        }
                    }
                }

                if (bandera){
                    //guardar localmente
                    Utils.exeLocalQuery(getApplicationContext(), strSQL);
                    Utils.exeLocalQuery(getApplicationContext(), strSQL2);

                    Toast.makeText(getApplicationContext(), "Perfil actualizado correctamente", Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Error al guardar, favor de volver a intentar", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}
