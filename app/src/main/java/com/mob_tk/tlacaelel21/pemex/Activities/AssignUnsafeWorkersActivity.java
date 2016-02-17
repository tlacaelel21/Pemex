package com.mob_tk.tlacaelel21.pemex.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Layout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.mob_tk.tlacaelel21.pemex.Adapter.AssignUnsafeWorkersAdapter;
import com.mob_tk.tlacaelel21.pemex.Model.WorkersModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mob_tk.tlacaelel21.pemex.R;
import com.mob_tk.tlacaelel21.pemex.Utilities.MyCustomDialog;
import com.mob_tk.tlacaelel21.pemex.Utilities.MyCustomDialog.onSubmitListener;
import com.mob_tk.tlacaelel21.pemex.Utilities.Utils;

import org.json.JSONArray;

/**
 * Created by tlacaelel21 on 1/11/15.
 */
public class AssignUnsafeWorkersActivity extends Activity implements onSubmitListener{
    private String listview_array[];
    LinearLayout linearLayoutCal1;
    LinearLayout linearLayoutCal2;
    LinearLayout linearLayoutCal3, linearLayoutCalifs;
    CheckBox chkGreen;
    CheckBox chkYell;
    CheckBox chkRed;
    TextView text1, text2,text3,tituloActoI, tvNumEmp, tvNomEmp,calif1,calif2,calif3;
    EditText comment;
    ImageButton imageButtonSave;
    String query,ai_id,titulo,empNum, nomEmp,empId;
    String suggestions[];
    String calificaciones[][];
    SharedPreferences prefs;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assign_unsafe_act_content);
        ImageButton imageButton=(ImageButton) findViewById(R.id.imageButton);
        linearLayoutCal1=(LinearLayout) findViewById(R.id.ly_txt_1);
        linearLayoutCal2=(LinearLayout) findViewById(R.id.ly_txt_2);
        linearLayoutCal3=(LinearLayout) findViewById(R.id.ly_txt_3);
        linearLayoutCalifs=(LinearLayout) findViewById(R.id.ly_txt_califs);

        chkGreen=(CheckBox) findViewById(R.id.chk_worker_1);
        chkYell=(CheckBox) findViewById(R.id.chk_worker_2);
        chkRed=(CheckBox) findViewById(R.id.chk_worker_3);

        final ImageButton popUpBtn=(ImageButton) findViewById(R.id.pop_up_menu);
        final Context context=getApplicationContext();

        text1=(TextView) findViewById(R.id.calif_text_1);
        text2=(TextView) findViewById(R.id.calif_text_2);
        text3=(TextView) findViewById(R.id.calif_text_3);
        calif1=(TextView) findViewById(R.id.calif_1);
        calif2=(TextView) findViewById(R.id.calif_2);
        calif3=(TextView) findViewById(R.id.calif_3);

        tituloActoI=(TextView) findViewById(R.id.titulo_acto_inseg);
        tvNumEmp=(TextView) findViewById(R.id.number_worker);
        tvNomEmp=(TextView) findViewById(R.id.name_worker);

        comment=(EditText) findViewById(R.id.texto_coment);

        imageButtonSave=(ImageButton) findViewById(R.id.imgBSave);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            query=extras.getString("query");
            ai_id=extras.getString("ai_id");
            titulo=extras.getString("titulo");
            empNum=extras.getString("emp_num_emp");
            nomEmp=extras.getString("nombre");
            empId=extras.getString("emp_id_calif");
        }
        tituloActoI.setText(titulo);
        tvNumEmp.setText(empNum);
        tvNomEmp.setText(nomEmp);

        /**Se realiza la consulta a la base de datos */
        final ArrayList<HashMap<String, String>> results;
        results= Utils.exeLocalQuery(this, query);
        listview_array=new String[results.size()];

        String[][] work_centers=new String[results.size()][2];

        for(int i=0;i<results.size();i++){
            for(int j=0;j<2;j++){
                if(j==0)
                    work_centers[i][j]=results.get(i).get("nombre");
                else
                    work_centers[i][j]=results.get(i).get("emp_num_emp");
            }
        }
        /** ******************************* */
        List<WorkersModel> listaWP = new ArrayList<WorkersModel>();
        for (int i=0; i< work_centers.length; i++) {
            String nameWorker = "", numberWorker = "";
            nameWorker = work_centers[i][0];
            numberWorker = work_centers[i][1];
            WorkersModel objeto = new WorkersModel(nameWorker, numberWorker);
            listaWP.add(objeto);
        }

        chkGreen.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    chkGreen.setButtonDrawable(R.drawable.target_green);
                    chkRed.setButtonDrawable(R.drawable.target_b);
                    chkYell.setButtonDrawable(R.drawable.target_b);
                }
        });
        chkYell.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    chkGreen.setButtonDrawable(R.drawable.target_b);
                    chkRed.setButtonDrawable(R.drawable.target_b);
                    chkYell.setButtonDrawable(R.drawable.target_yellow_2);
                }
        });
        chkRed.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    chkGreen.setButtonDrawable(R.drawable.target_b);
                    chkRed.setButtonDrawable(R.drawable.target_red);
                    chkYell.setButtonDrawable(R.drawable.target_b);
                }
        });
        linearLayoutCal1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //boolean isOk = ((CheckBox) view).isChecked();
                chkGreen.setButtonDrawable(R.drawable.target_green_2);
                chkRed.setButtonDrawable(R.drawable.target_b);
                chkYell.setButtonDrawable(R.drawable.target_b);
                comment.setText(text1.getText());
                chkGreen.setChecked(true);
            }
        });
        linearLayoutCal2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //boolean isOk = ((CheckBox) view).isChecked();
                chkGreen.setButtonDrawable(R.drawable.target_b);
                chkRed.setButtonDrawable(R.drawable.target_b);
                chkYell.setButtonDrawable(R.drawable.target_yellow);
                comment.setText(text2.getText());
                chkYell.setChecked(true);
            }
        });
        linearLayoutCal3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //boolean isOk = ((CheckBox) view).isChecked();
                chkGreen.setButtonDrawable(R.drawable.target_b);
                chkRed.setButtonDrawable(R.drawable.target_red);
                chkYell.setButtonDrawable(R.drawable.target_b);
                comment.setText(text3.getText());
                chkRed.setChecked(true);
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MyCustomDialog fragment1 = new MyCustomDialog();
                fragment1.mListener = (MyCustomDialog.onSubmitListener) AssignUnsafeWorkersActivity.this;
                fragment1.show(getFragmentManager(), "");
            }
        });

        popUpBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(context, popUpBtn);
                popup.getMenuInflater().inflate(R.menu.pop_up_auw, popup.getMenu());

                /*popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.item_trabajador) {
                            Toast.makeText(context, "SISTEMA PEMEX", Toast.LENGTH_LONG).show();
                        }
                        return true;
                    }
                });*/
                popup.show();//showing popup menu}

            }
        });

        /**Se realiza la consulta a la base de datos */
        final String querySug="SELECT sai_id, sai_desc,sai_calificacion FROM sugerenciaai " +
                " WHERE  sai_status=1 AND ai_id="+ai_id;
        Log.i("QUERY_PQ",""+querySug);
        final ArrayList<HashMap<String, String>> resultsSug;
        resultsSug= Utils.exeLocalQuery(this, querySug);
        suggestions=new String[3];
        int contador=0;
        for(int idx=0;idx<resultsSug.size();idx++){
            if(null!=resultsSug.get(idx).get("sai_calificacion")){
                if(resultsSug.get(idx).get("sai_calificacion").equals("1")){
                    calif1.setVisibility(View.VISIBLE);
                    linearLayoutCal1.setVisibility(View.VISIBLE);
                    text1.setText("" + resultsSug.get(idx).get("sai_desc"));
                    contador++;
                }
            }
            if(null!=resultsSug.get(idx).get("sai_calificacion")){
                if(resultsSug.get(idx).get("sai_calificacion").equals("2")){
                    calif2.setVisibility(View.VISIBLE);
                    linearLayoutCal2.setVisibility(View.VISIBLE);
                    text2.setText("" + resultsSug.get(idx).get("sai_desc"));
                    contador++;
                }
            }
            if(null!=resultsSug.get(idx).get("sai_calificacion")){
                if(resultsSug.get(idx).get("sai_calificacion").equals("3")){
                    calif3.setVisibility(View.VISIBLE);
                    linearLayoutCal3.setVisibility(View.VISIBLE);
                    text3.setText("" + resultsSug.get(idx).get("sai_desc"));
                    contador++;
                }
            }
        }
        if(contador>0)
            linearLayoutCalifs.setVisibility(View.VISIBLE);

        imageButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String calif="0", descri;
                if(chkGreen.isChecked())
                    calif="1";
                if(chkYell.isChecked())
                    calif="2";
                if(chkRed.isChecked())
                    calif="3";
                descri=comment.getText().toString();
                Intent intent =
                        new Intent(AssignUnsafeWorkersActivity.this, PeopleQActivity.class);
                intent.putExtra("query", "" + query);
                intent.putExtra("campo", "nombre");
                intent.putExtra("clave", "emp_num_emp");
                startActivity(intent);
                finish();
                if(!calif.equals("0"))
                    insertCalif(ai_id, empId, calif, descri, "",empNum);
            }
        });
        //Log.i("PREF_IDEMP-->", preferences.getString("emp_id", ""));
    }
    /** Agregando a la BDLocal las calificaciones*/
    public void insertCalif(String id_acto_inseguro,String id_emp,String calif,String descri,String foto,String num_emp){
        String localQuery = "INSERT INTO calificaciones (id_acto_inseguro,id_emp,calif,descri,foto,num_emp)"+
                " VALUES('"+id_acto_inseguro+"','"+id_emp+"','"+calif+"','"+descri+"','"+foto+"','"+num_emp+"')";
        try {
            Log.i("QUERY_LOCAL-->", ""+localQuery);
            Utils.exeLocalInsQuery(getBaseContext(), localQuery);
        } catch(Exception E) {

        }
    }

    @Override
    public void setOnSubmitListener(String arg) {
        //mTextView.setText(arg);
    }
}
