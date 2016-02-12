package com.mob_tk.tlacaelel21.pemex.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.mob_tk.tlacaelel21.pemex.R;
import com.mob_tk.tlacaelel21.pemex.Utilities.Util;
import com.mob_tk.tlacaelel21.pemex.Utilities.Utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by tlacaelel21 on 20/10/15.
 */
public class MenuActivity extends Activity {
    //PUSH
    SharedPreferences prefs;
    GoogleCloudMessaging gcm;
    AtomicInteger msgId = new AtomicInteger();
    static final String TAG = "GCM";
    String regid;
    String msg;
    Context context;
    String emp_num;

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

        // Check device for Play Services APK. If check succeeds, proceed with GCM registration.
        SharedPreferences prefs = getSharedPreferences("pemex_prefs", MODE_PRIVATE);
        emp_num = prefs.getString("emp_num", "");
        context = getApplicationContext();
        if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(this);
            regid = getRegistrationId(context);

            if (regid.isEmpty()) {
                registerInBackground();
            }
        } else {
            Log.i("GCM", "No valid Google Play Services APK found.");
        }

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


    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        Util.PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(Util.PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing registration ID is not guaranteed to work with
        // the new app version.
        int registeredVersion = prefs.getInt(Util.PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    private boolean isUserRegistered(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String User_name = prefs.getString(Util.USER_NAME, "");
        if (User_name.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return false;
        }

        return true;
    }

    private void registerInBackground() {
        new AsyncTask() {
            ArrayList<HashMap<String, String>> results;

            @Override
            protected String doInBackground(Object[] params) {
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(MenuActivity.this);
                    }
                    regid = gcm.register(Util.SENDER_ID);
                    msg = "Device registered, registration ID=" + regid;

                    //Registrar en el servidor
                    List listFields = new ArrayList();
                    listFields.add("ci_id");
                    listFields.add("ci_id");
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    results = Utils.exeRemoteQuery(getApplicationContext(), "311|" + emp_num + "|A|"+ regid ,listFields);
                    //@"311|usuario.emp_num_emp|I|token"
                    storeRegistrationId(context, regid);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }
        }.execute();

    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Util.PROPERTY_REG_ID, regId);
        editor.putInt(Util.PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    private SharedPreferences getGCMPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the registration ID in your app is up to you.
        return getSharedPreferences(MainActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }
}
