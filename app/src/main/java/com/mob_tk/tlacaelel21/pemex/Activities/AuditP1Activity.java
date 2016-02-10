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
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.mob_tk.tlacaelel21.pemex.R;
import com.mob_tk.tlacaelel21.pemex.Utilities.Utils;

/**
 * Created by tlacaelel21 on 22/10/15.
 */
public class AuditP1Activity extends Activity implements OnClickListener{
    private int day;
    private int month;
    private int year;
    private Button fecha_wc, btTypeAudit, btRegion, btnArea;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit_p1);

        fecha_wc = (Button) findViewById(R.id.fecha_wc);
        btTypeAudit = (Button) findViewById(R.id.boton_ta);
        btRegion= (Button) findViewById(R.id.boton_region);
        btnArea= (Button) findViewById(R.id.boton_area_p1);

        Calendar cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        fecha_wc.setOnClickListener(this);

        final Button btnWorkplace=(Button) findViewById(R.id.boton_wc);
        Button btnDepartments=(Button) findViewById(R.id.boton_depto);
        Button btnContinue=(Button) findViewById(R.id.button_wc_continue);
        preferences = getSharedPreferences("pemex_prefs", MODE_PRIVATE);
        if(null!=preferences.getString("worc_center", "")) {
            if(preferences.getString("worc_center", "").length()>0){
                btnWorkplace.setText(preferences.getString("worc_center", ""));
            }
        }
        if(null!=preferences.getString("departament", "")) {
            if(preferences.getString("departament", "").length()>0)
                btnDepartments.setText(preferences.getString("departament", ""));
        }
        if(null!=preferences.getString("tipoauditoria", "")) {
            if(preferences.getString("tipoauditoria", "").length()>0){
                btTypeAudit.setText(preferences.getString("tipoauditoria", ""));
            }
        }
        if(null!=preferences.getString("region", "")) {
            if(preferences.getString("region", "").length()>0){
                btRegion.setText(preferences.getString("region", ""));
            }
        }

        if(null!=preferences.getString("area", "")) {
            if(preferences.getString("area", "").length()>0){
                btnArea.setText(preferences.getString("area", ""));
            }
        }


        btTypeAudit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent =
                        new Intent(AuditP1Activity.this, GeneralContainerActivity.class);
                intent.putExtra("query","SELECT taud_id, taud_desc FROM tipoauditoria");
                intent.putExtra("titulo","Tipo Auditoria");
                intent.putExtra("campo","taud_desc");
                intent.putExtra("clave","taud_id");
                intent.putExtra("tipo","tipoauditoria");
                intent.putExtra("procede","1");
                startActivity(intent);
                finish();
            }
        });

        btRegion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SharedPreferences.Editor editor = preferences.edit();
                //variables prim pantalla
                editor.putString("worc_center", "");
                editor.commit();
                btnWorkplace.setText("Centro Trabajo");
                Intent intent =
                        new Intent(AuditP1Activity.this, GeneralContainerActivity.class);
                intent.putExtra("query","SELECT reg_id, reg_desc FROM region");
                intent.putExtra("titulo","Region");
                intent.putExtra("campo","reg_desc");
                intent.putExtra("clave","reg_id");
                intent.putExtra("tipo","region");
                intent.putExtra("procede","1");
                startActivity(intent);
                finish();
            }
        });
        btnArea.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int dep_id=0;
                if(null!=preferences.getString("dep_id", "")) {
                    if(preferences.getString("dep_id", "").length()>0)
                        dep_id=Integer.parseInt(preferences.getString("dep_id", ""));
                }
                Log.i("ID_DEP",""+dep_id);
                if(dep_id>0){
                    Intent intent =
                            new Intent(AuditP1Activity.this, GeneralContainerActivity.class);
                    intent.putExtra("query","SELECT area_id, area_desc FROM area WHERE area_status=1 AND dep_id="+dep_id);
                    intent.putExtra("titulo","Area");
                    intent.putExtra("campo","area_desc");
                    intent.putExtra("clave","area_id");
                    intent.putExtra("tipo","area");
                    intent.putExtra("procede","1");
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(AuditP1Activity.this, "Debe de seleccionar un departamento antes que el área", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnWorkplace.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int reg_id=0;
                if(null!=preferences.getString("reg_id", "")) {
                    if(preferences.getString("reg_id", "").length()>0)
                        reg_id=Integer.parseInt(preferences.getString("reg_id", ""));
                }

                if(reg_id>0){
                    Intent intent =
                            new Intent(AuditP1Activity.this, WorkCentersActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(AuditP1Activity.this, "Debe de seleccionar una región antes que el área", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnDepartments.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                    SharedPreferences.Editor editor = preferences.edit();
                    //variables prim pantalla
                    editor.putString("area", "");
                    editor.commit();
                    btnArea.setText("Area");
                    Intent intent =
                            new Intent(AuditP1Activity.this, GeneralContainerActivity.class);
                    intent.putExtra("query","SELECT dep_id, dep_desc FROM departamento");
                    intent.putExtra("titulo","Departamento");
                    intent.putExtra("campo","dep_desc");
                    intent.putExtra("clave","dep_id");
                    intent.putExtra("tipo","departament");
                    intent.putExtra("procede","1");
                    startActivity(intent);
                    finish();
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int ctaOpc=0;
                if(null!=preferences.getString("worc_center", "")) {
                    if(preferences.getString("worc_center", "").length()>0){
                        ctaOpc++;
                    }
                }
                if(null!=preferences.getString("departament", "")) {
                    if(preferences.getString("departament", "").length()>0)
                        ctaOpc++;
                }
                if(null!=preferences.getString("tipoauditoria", "")) {
                    if(preferences.getString("tipoauditoria", "").length()>0){
                        ctaOpc++;
                    }
                }
                if(null!=preferences.getString("region", "")) {
                    if(preferences.getString("region", "").length()>0){
                        ctaOpc++;
                    }
                }
                if(null!=preferences.getString("area", "")) {
                    if(preferences.getString("area", "").length()>0){
                        ctaOpc++;
                    }
                }
                if(null!=preferences.getString("audit_date", "")) {
                    if(preferences.getString("audit_date", "").length()>0){
                        ctaOpc++;
                    }
                }
                if(null!=preferences.getString("audit_week", "")) {
                    if(preferences.getString("audit_week", "").length()>0){
                        ctaOpc++;
                    }
                }

                if(ctaOpc==7){
                    Intent intent =
                        new Intent(AuditP1Activity.this, AuditP2Activity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(AuditP1Activity.this, "Le faltan datos por seleccionar ", Toast.LENGTH_LONG).show();
                }
            }
        });

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        /*View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }*/
    }
    @Override
    public void onClick(View v) {
        showDialog(0);
    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, datePickerListener, year, month, day);
    }
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            String fecha=""+ (selectedDay + "/" + (selectedMonth + 1) + "/"
                    + selectedYear);
            fecha_wc.setText(fecha);
            Date date = new Date();

                DateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
            Log.i("FECHA",""+fecha);
            try {
                date = format1.parse(fecha);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar cal= Calendar.getInstance();
            cal.setFirstDayOfWeek( Calendar.MONDAY);
            cal.setMinimalDaysInFirstWeek( 4 );
            cal.setTime(date);
            //int semana = c.WEEK_OF_MONTH ;
            int semana=cal.get(Calendar.WEEK_OF_YEAR);
            EditText semana_wc=(EditText) findViewById(R.id.semana_wc);
            semana_wc.setText("Semana " + semana);

            Utils.setValueSP(AuditP1Activity.this, "audit_date", fecha);
            Utils.setValueSP(AuditP1Activity.this,"audit_week",""+semana);

        }
    };
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("¿Desea salir sin guardar los cambios?");

            alertDialogBuilder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    finish();
                    Toast.makeText(AuditP1Activity.this, "Los cambios no se han guardado", Toast.LENGTH_LONG).show();
                }
            });

            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //finish();
                    //Toast.makeText(AuditP1Activity.this, "You clicked yes button", Toast.LENGTH_LONG).show();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();


        }

        return super.onKeyDown(keyCode, event);
    }
}

