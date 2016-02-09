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



       // Log.i("PREF-->", preferences.getString("emp_nombre", ""));


        btnIniAudit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("worc_center", "");
                editor.putString("departament", "");
                editor.putString("tipoauditoria", "");
                editor.putString("region", "");
                editor.putString("dep_id", "");
                editor.putString("instalaciones","");
                editor.putString("jefes","");
                editor.putString("nums","");
                editor.putString("conts","");



                editor.commit();
                Intent intent =
                        new Intent(MenuActivity.this, AuditP1Activity.class);
                startActivity(intent);
            }
        });

        btnPermisos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });
        ContextThemeWrapper ctw = new ContextThemeWrapper( this, R.style.AlertDialogCustom);

        AlertDialog alertDialog = new AlertDialog.Builder(ctw).create();
        //AlertDialog alertDialog = new AlertDialog.Builder(MenuActivity.this).create();
        alertDialog.setTitle("Pemex");
        alertDialog.setMessage(" Bienvenido  "+preferences.getString("emp_nombre", ""));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "  ACEPTAR    ",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
