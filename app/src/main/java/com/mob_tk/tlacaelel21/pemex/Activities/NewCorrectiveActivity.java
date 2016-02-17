package com.mob_tk.tlacaelel21.pemex.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.mob_tk.tlacaelel21.pemex.R;
import com.mob_tk.tlacaelel21.pemex.Utilities.Utils;

import org.json.JSONArray;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by tlacaelel21 on 12/02/16.
 */
public class NewCorrectiveActivity extends Activity {
    ImageButton imageButtonClose, btn_recomendacion;
    ToggleButton tgRecom, tgComp, tgSi, tgNo;
    SharedPreferences preferences;
    EditText eTEvidencia,eTRecomenda;
    TextView tvTituloProced, fechaCompro, fechaReali,tituloEvidencia,tituloFechaRealizada;
    Button btnSave1,btnSave2;
    private int day,month,year,tipoF;
    String ai_id,id_emp;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_corrective);
        preferences = getSharedPreferences("pemex_prefs", MODE_PRIVATE);

        imageButtonClose=(ImageButton) findViewById(R.id.image_close);

        tgRecom=(ToggleButton) findViewById(R.id.btn_recomendacion);
        tgComp=(ToggleButton) findViewById(R.id.btn_com);
        tgSi=(ToggleButton) findViewById(R.id.btn_si);
        tgNo=(ToggleButton) findViewById(R.id.btn_no);

        tvTituloProced=(TextView) findViewById(R.id.titulo_procedure);
        fechaCompro=(TextView) findViewById(R.id.fecha_compromiso);
        fechaReali=(TextView) findViewById(R.id.fecha_realizada);
        tituloEvidencia=(TextView) findViewById(R.id.titulo_evidencia);
        tituloFechaRealizada=(TextView) findViewById(R.id.titulo_fecha_realizada);

        eTRecomenda=(EditText) findViewById(R.id.eTRecomenda);
        eTEvidencia=(EditText) findViewById(R.id.eTEvidencia);

        btnSave1=(Button) findViewById(R.id.btn_save_1);
        btnSave2=(Button) findViewById(R.id.btn_save_2);

        fechaReali.setEnabled(false);
        eTEvidencia.setEnabled(false);

        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("Mexico/General"));
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);

        fechaCompro.setText(currentDate());

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            id_emp=extras.getString("id_emp");
            ai_id=extras.getString("ai_id");
        }


        fechaCompro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipoF=1;
                showDialog(0);
            }
        });
        fechaReali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipoF=2;
                showDialog(0);
            }
        });

        tgRecom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                try {
                    tvTituloProced.setText(R.string.titulo1);
                    //tgRecom.setChecked(true);
                    tgRecom.setBackgroundResource(R.drawable.btn_new_acc_blue);
                    if (Build.VERSION.SDK_INT < 23) {
                        tgRecom.setTextAppearance(getBaseContext(), R.style.TextWhite);
                    } else {
                        tgRecom.setTextAppearance(R.style.TextWhite);
                    }
                    //tgComp.setChecked(false);
                    tgComp.setBackgroundResource(R.drawable.btn_new_acc_white);
                    if (Build.VERSION.SDK_INT < 23) {
                        tgComp.setTextAppearance(getBaseContext(), R.style.TextBlue);
                    } else {
                        tgComp.setTextAppearance(R.style.TextBlue);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        tgComp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                try {
                        tvTituloProced.setText(R.string.titulo2);
                        //tgRecom.setChecked(false);
                        tgRecom.setBackgroundResource(R.drawable.btn_new_acc_white);
                        if (Build.VERSION.SDK_INT < 23) {
                            tgRecom.setTextAppearance(getBaseContext(), R.style.TextBlue);
                        } else {
                            tgRecom.setTextAppearance(R.style.TextBlue);
                        }
                       // tgComp.setChecked(true);
                        tgComp.setBackgroundResource(R.drawable.btn_new_acc_blue);
                        if (Build.VERSION.SDK_INT < 23) {
                            tgComp.setTextAppearance(getBaseContext(), R.style.TextWhite);
                        } else {
                            tgComp.setTextAppearance(R.style.TextWhite);
                        }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        tgSi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                try {
                    //tgRecom.setChecked(true);
                    tituloFechaRealizada.setVisibility(View.VISIBLE);
                    tituloEvidencia.setVisibility(View.VISIBLE);
                    eTEvidencia.setVisibility(View.VISIBLE);
                    fechaReali.setVisibility(View.VISIBLE);
                    btnSave2.setVisibility(View.VISIBLE);
                    btnSave1.setVisibility(View.GONE);
                    eTEvidencia.setEnabled(true);
                    fechaReali.setEnabled(true);
                    fechaReali.setText(currentDate());
                    tgSi.setBackgroundResource(R.drawable.btn_new_acc_blue);
                    if (Build.VERSION.SDK_INT < 23) {
                        tgSi.setTextAppearance(getBaseContext(), R.style.TextWhite);
                    } else {
                        tgSi.setTextAppearance(R.style.TextWhite);
                    }
                    //tgComp.setChecked(false);
                    tgNo.setBackgroundResource(R.drawable.btn_new_acc_white);
                    if (Build.VERSION.SDK_INT < 23) {
                        tgNo.setTextAppearance(getBaseContext(), R.style.TextBlue);
                    } else {
                        tgNo.setTextAppearance(R.style.TextBlue);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        tgNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                try {
                    tituloFechaRealizada.setVisibility(View.INVISIBLE);
                    tituloEvidencia.setVisibility(View.INVISIBLE);
                    eTEvidencia.setVisibility(View.INVISIBLE);
                    fechaReali.setVisibility(View.INVISIBLE);
                    btnSave2.setVisibility(View.INVISIBLE);
                    btnSave1.setVisibility(View.VISIBLE);
                    eTEvidencia.setEnabled(false);
                    fechaReali.setEnabled(false);
                    fechaReali.setText("");
                    tgSi.setBackgroundResource(R.drawable.btn_new_acc_white);
                    if (Build.VERSION.SDK_INT < 23) {
                        tgSi.setTextAppearance(getBaseContext(), R.style.TextBlue);
                    } else {
                        tgSi.setTextAppearance(R.style.TextBlue);
                    }
                    // tgComp.setChecked(true);
                    tgNo.setBackgroundResource(R.drawable.btn_new_acc_blue);
                    if (Build.VERSION.SDK_INT < 23) {
                        tgNo.setTextAppearance(getBaseContext(), R.style.TextWhite);
                    } else {
                        tgNo.setTextAppearance(R.style.TextWhite);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        imageButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =
                        new Intent(NewCorrectiveActivity.this, AuditSummary.class);
                startActivity(intent);
                finish();
            }

        });
        btnSave1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tipo_accion="", desc_accion="", fecha_acc="",realizado="0",desc_evidencia="",
                        fecha_evidencia="";
                tipo_accion="1";
                desc_accion=eTRecomenda.getText().toString();
                fecha_acc=fechaCompro.getText().toString();
                if(desc_accion.length()>0)
                    insertCorrectiveAction(tipo_accion,desc_accion, fecha_acc,realizado,desc_evidencia,fecha_evidencia,
                        ai_id,id_emp);
            }

        });
        btnSave2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tipo_accion="", desc_accion="", fecha_acc="",realizado="1",desc_evidencia="",
                        fecha_evidencia="";
                tipo_accion="1";
                desc_accion=eTRecomenda.getText().toString();
                fecha_acc=fechaCompro.getText().toString();
                desc_evidencia=eTEvidencia.getText().toString();
                fecha_evidencia=fechaReali.getText().toString();
                if(desc_accion.length()>0&&desc_evidencia.length()>0)
                insertCorrectiveAction(tipo_accion,desc_accion, fecha_acc,realizado,desc_evidencia,fecha_evidencia,
                        ai_id,id_emp);
            }

        });
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
            if(tipoF==1)
                fechaCompro.setText(fecha);
            else
                fechaReali.setText(fecha);
            Date date = new Date();

            DateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
            try {
                date = format1.parse(fecha);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar cal= Calendar.getInstance();
            cal.setFirstDayOfWeek(Calendar.MONDAY);
            cal.setTimeZone(TimeZone.getTimeZone("UTC-06:00"));
            cal.setMinimalDaysInFirstWeek( 4 );
            cal.setTime(date);
            //int semana = c.WEEK_OF_MONTH ;
           /* int semana=cal.get(Calendar.WEEK_OF_YEAR);
            EditText semana_wc=(EditText) findViewById(R.id.semana_wc);
            semana_wc.setText("Semana " + semana);*/

            /*Utils.setValueSP(AuditP1Activity.this, "audit_date", fecha);
            Utils.setValueSP(AuditP1Activity.this,"audit_week",""+semana);*/
        }
    };

    public String currentDate(){
        String fechaAct="";
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        fechaAct = df.format(c.getTime());
        Log.i("FECHA","Fe->"+fechaAct);

        return fechaAct;
    }

    /** Agregando a la BDLocal las calificaciones*/
    public void insertCorrectiveAction(String tipo_accion,String desc_accion,String fecha_acc,
                            String realizado,String desc_evidencia,String fecha_evidencia,
                            String id_acto_inseguro,String id_empleado){
        String localQuery ="INSERT INTO acciones_correctivas " +
                " (tipo_accion,desc_accion,fecha_acc,realizado,desc_evidencia,fecha_evidencia,id_acto_inseguro,id_empleado) " +
                " VALUES('"+tipo_accion+"','"+desc_accion+"','"+fecha_acc+"','"+realizado+"','"+desc_evidencia+"','"+fecha_evidencia+"',"+
                ",'"+id_acto_inseguro+"','"+id_empleado+"')";
        try {
            Log.i("QUERY_LOCAL-->", ""+localQuery);
            Utils.exeLocalInsQuery(getBaseContext(), localQuery);
        } catch(Exception E) {

        }
    }
}
