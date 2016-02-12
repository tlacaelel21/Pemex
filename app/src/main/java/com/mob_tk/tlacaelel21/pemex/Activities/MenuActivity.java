package com.mob_tk.tlacaelel21.pemex.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.mob_tk.tlacaelel21.pemex.R;
import com.mob_tk.tlacaelel21.pemex.Utilities.Utils;

/**
 * Created by tlacaelel21 on 20/10/15.
 */
public class MenuActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final SharedPreferences preferences;
        preferences = getSharedPreferences("pemex_prefs", MODE_PRIVATE);
        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        Button btnIniAudit=(Button) findViewById(R.id.boton_audit);
        Button btnPermisos=(Button) findViewById(R.id.boton_permisos);
        Button btnGlosario=(Button) findViewById(R.id.boton_glosario);
        Button btnAlertas=(Button) findViewById(R.id.boton_alertas);
        Button btnPerfil=(Button) findViewById(R.id.boton_perfil);
        Button btnComunicacion=(Button) findViewById(R.id.boton_comunicacion);

       // Log.i("PREF-->", preferences.getString("emp_nombre", ""));


        btnIniAudit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                SharedPreferences.Editor editor = preferences.edit();
                //variables prim pantalla
                editor.putString("worc_center", "");
                editor.putString("departament", "");
                editor.putString("tipoauditoria", "");
                editor.putString("region", "");
                editor.putString("area", "");
                editor.putString("dep_id", "");
                editor.putString("reg_id","");
                editor.putString("dep_id","");
                editor.putString("audit_date","");
                editor.putString("audit_week","");
                //variables seg pantalla
                editor.putString("instalaciones","");
                editor.putString("nums","");
                editor.putString("conts","");
                editor.putString("jefes","");
                editor.putString("actividad", "");
                editor.putString("employ_size","");
                editor.putString("conts_size","");
                editor.clear();
                editor.commit();
                clearCalif();
                Intent intent =
                        new Intent(MenuActivity.this, AuditP1Activity.class);
                startActivity(intent);
            }
        });

        btnPermisos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });

        btnGlosario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, GlosarioActivity.class);
                startActivity(intent);
            }
        });

        btnAlertas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, AlertasActivity.class);
                startActivity(intent);
            }
        });

        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, PerfilActivity.class);
                startActivity(intent);
            }
        });

        btnComunicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ComunicacionActivity.class);
                startActivity(intent);
            }
        });

        ContextThemeWrapper ctw = new ContextThemeWrapper( this, R.style.AlertDialogCustom);
        AlertDialog alertDialog = new AlertDialog.Builder(ctw).create();
        //AlertDialog alertDialog = new AlertDialog.Builder(MenuActivity.this).create();
        alertDialog.setTitle("Pemex");
        alertDialog.setMessage(" Bienvenido  " + preferences.getString("emp_nombre", ""));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "  ACEPTAR    ",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
    /** Agregando a la BDLocal las calificaciones*/
    public void clearCalif(){
        String localQuery = "DELETE FROM calificaciones";
        try {
            Log.i("CLEAR-->", ""+localQuery);
            Utils.exeLocalInsQuery(getBaseContext(), localQuery);
        } catch(Exception E) {

        }
    }
}
