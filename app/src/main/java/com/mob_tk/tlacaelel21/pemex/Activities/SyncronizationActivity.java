package com.mob_tk.tlacaelel21.pemex.Activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.mob_tk.tlacaelel21.pemex.R;
import com.mob_tk.tlacaelel21.pemex.Task.ConsultaTask;
import com.mob_tk.tlacaelel21.pemex.Utilities.Cifrado;
import com.mob_tk.tlacaelel21.pemex.Utilities.DataBaseHelper;
import com.mob_tk.tlacaelel21.pemex.Utilities.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by tlacaelel21 on 13/01/16.
 */
public class SyncronizationActivity extends Activity {
    public int flag = 0;
    public int error = 0;
    public String idperfil;
    public String idusuario;

    @Override
    public void onBackPressed() {

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sincronization);

        /**
         * Copio la estructura de la BD al dispositivo*/
        try {
            DataBaseHelper dbh = new DataBaseHelper(this);
            dbh.initializedatabase();
            dbh.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //SharedPreferences preferencias = this.getSharedPreferences(MainActivity.NAMESPACE, 0);
        /**
         * consultando de un query local
         */
        String fecha="";
        ArrayList<HashMap<String, String>> results;
        results=Utils.exeLocalQuery(this, "SELECT * FROM mobactualizacion");
        for(int idx=0;idx<results.size();idx++){
            fecha=results.get(idx).get("fecha");
        }
        ConsultaTask tasks = new ConsultaTask(this);
        tasks.new TaskContainer(this).execute(fecha);
    }
}
