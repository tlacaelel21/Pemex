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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mob_tk.tlacaelel21.pemex.R;
import com.mob_tk.tlacaelel21.pemex.Utilities.Cifrado;
import com.mob_tk.tlacaelel21.pemex.Utilities.Utils;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity {
    public static String NAMESPACE = "PEMEXSPACE";
    EditText numEmp, psw;
    Button loginButton,recoveryButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton = (Button)findViewById(R.id.boton_ingresar);
        recoveryButton = (Button)findViewById(R.id.button_recovery);
        numEmp=(EditText)findViewById(R.id.num_emp);
        psw=(EditText)findViewById(R.id.psw);

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String nEmp=numEmp.getText().toString().trim();
                String pswE=psw.getText().toString().trim();
                if(nEmp!=null&&psw!=null){
                    if(numEmp.length()>0&&psw.length()>0){
                            boolean login=userQuery(nEmp,pswE);
                            if(login){
                                Intent intent =
                                new Intent(MainActivity.this, MenuActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(MainActivity.this, "Número de empleado o contraseña incorrecta", Toast.LENGTH_LONG).show();
                            }
                    }else{
                        Toast.makeText(MainActivity.this, "Escriba valores validos", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Datos incorrectos", Toast.LENGTH_LONG).show();
                }
            }
        });
        recoveryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent =
                        new Intent(MainActivity.this, RecoveryPswActivity.class);
                startActivity(intent);
            }
        });
        Cifrado obj=new Cifrado();
        //String valorEnc=obj.encriptar("305|prueba@cptm.gob|1234|(null)");
        //Servicio|id_hotel|id_huesped|id_servicio|fecha|hora|comentarios
        //String valorEnc=obj.encriptar("203|1|2015/5/5");
        String valorEnc=obj.encriptar("34|1");
        valorEnc=valorEnc.replaceAll("\\+", "%2B");
        valorEnc=valorEnc.replaceAll("\\/", "%2F");
        Log.i("ENC", MainActivity.this.getString(R.string.base_url)+valorEnc);
    }
    private boolean userQuery(String numEmp, String psw){
        boolean ingreso=false;
        int idUsuario=0;
        ArrayList<HashMap<String, String>> results;
        String query="SELECT us.id_usuario_sistema as id, us.name, em.* FROM usuario us, empleado em "+
                " WHERE nip='"+psw+"' and emp_num_emp="+numEmp+" "+
                " and us.id_usuario_sistema=em.id_usuario_sistema";
        Log.i("Query",query);
        results= Utils.exeLocalQuery(this, query);
            for(int idx=0;idx<results.size();idx++){
                idUsuario=Integer.parseInt(results.get(idx).get("id"));
                //Resultados
                SharedPreferences prefs = getSharedPreferences("pemex_prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("id_usuario_sistema", "" + results.get(0).get("id"));
                editor.putString("emp_id", "" + results.get(0).get("emp_id"));
                editor.putString("emp_nombre", "" + results.get(0).get("name"));
                editor.putString("emp_num", "" + results.get(0).get("emp_num_emp"));
                editor.commit();
                //Log.i("ResMain--> ",""+idUsuario);
                View view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
            if(idUsuario>0)
                ingreso=true;


        /*String fecha="";
        ArrayList<HashMap<String, String>> results;
        results=Utils.exeLocalQuery(this, "SELECT * FROM mobactualizacion");
        for(int idx=0;idx<results.size();idx++){
            fecha=results.get(idx).get("fecha");
            Log.i("ResMain--> ",fecha);
        }*/
        return ingreso;
    }
}
