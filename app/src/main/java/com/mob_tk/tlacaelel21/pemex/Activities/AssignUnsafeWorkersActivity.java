package com.mob_tk.tlacaelel21.pemex.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
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

/**
 * Created by tlacaelel21 on 1/11/15.
 */
public class AssignUnsafeWorkersActivity extends Activity implements onSubmitListener{
    private String listview_array[];
    LinearLayout linearLayoutCal1;
    LinearLayout linearLayoutCal2;
    LinearLayout linearLayoutCal3;
    CheckBox chkGreen;
    CheckBox chkYell;
    CheckBox chkRed;
    TextView text1, text2,text3;
    EditText comment;
    ImageButton imageButtonSave;

    String suggestions[];
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

        chkGreen=(CheckBox) findViewById(R.id.chk_worker_1);
        chkYell=(CheckBox) findViewById(R.id.chk_worker_2);
        chkRed=(CheckBox) findViewById(R.id.chk_worker_3);

        final ImageButton popUpBtn=(ImageButton) findViewById(R.id.pop_up_menu);
        final Context context=getApplicationContext();

        text1=(TextView) findViewById(R.id.calif_text_1);
        text2=(TextView) findViewById(R.id.calif_text_2);
        text3=(TextView) findViewById(R.id.calif_text_3);

        comment=(EditText) findViewById(R.id.texto_coment);

        imageButtonSave=(ImageButton) findViewById(R.id.imgBSave);

        /**Se realiza la consulta a la base de datos */
        final ArrayList<HashMap<String, String>> results;
        results= Utils.exeLocalQuery(this, "SELECT emp_nombre|| ' ' ||emp_app|| ' ' ||emp_apm AS nombre,emp_num_emp FROM empleado");
        //Log.i("TAM->",""+results.size());
        listview_array=new String[results.size()];
        /*for(int idx=0;idx<results.size();idx++){
            listview_array[idx]=results.get(idx).get("campo");
            Log.i("CAMPO->", results.get(idx).get(""));
        }*/
        /** ******************************* */

        String[][] work_centers=new String[results.size()][2];

        for(int i=0;i<results.size();i++){
            for(int j=0;j<2;j++){
                if(j==0)
                    work_centers[i][j]=results.get(i).get("nombre");
                else
                    work_centers[i][j]=results.get(i).get("emp_num_emp");
            }
        }

        List<WorkersModel> listaWP = new ArrayList<WorkersModel>();

        for (int i=0; i< work_centers.length; i++) {
            String nameWorker = "", numberWorker = "";
            nameWorker = work_centers[i][0];
            numberWorker = work_centers[i][1];
            WorkersModel objeto = new WorkersModel(nameWorker, numberWorker);
            listaWP.add(objeto);
        }

        /*ListView lista = (ListView) findViewById(R.id.list_assign_unsafe);
        AssignUnsafeWorkersAdapter assignUnsafeWorkersAdapter= new AssignUnsafeWorkersAdapter(context,
                R.layout.list_assign_unsafe,listaWP);
        lista.setAdapter(assignUnsafeWorkersAdapter);*/

        imageButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.exeLocalInsQuery(getApplicationContext(),"");
                Intent intent =
                        new Intent(AssignUnsafeWorkersActivity.this, UnsafeActActivity.class);
                startActivity(intent);
                finish();
            }
        });

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
                    chkYell.setButtonDrawable(R.drawable.target_yellow);
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
                chkGreen.setButtonDrawable(R.drawable.target_green);
                chkRed.setButtonDrawable(R.drawable.target_b);
                chkYell.setButtonDrawable(R.drawable.target_b);
                comment.setText(text1.getText());
            }
        });
        linearLayoutCal2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //boolean isOk = ((CheckBox) view).isChecked();
                chkGreen.setButtonDrawable(R.drawable.target_b);
                chkRed.setButtonDrawable(R.drawable.target_b);
                chkYell.setButtonDrawable(R.drawable.target_yellow);
                comment.setText(text2.getText());
            }
        });
        linearLayoutCal3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //boolean isOk = ((CheckBox) view).isChecked();
                chkGreen.setButtonDrawable(R.drawable.target_b);
                chkRed.setButtonDrawable(R.drawable.target_red);
                chkYell.setButtonDrawable(R.drawable.target_b);
                comment.setText(text3.getText());
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

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.item_trabajador) {
                            Toast.makeText(context, "SISTEMA PEMEX", Toast.LENGTH_LONG).show();
                        }
                        return true;
                    }
                });
                popup.show();//showing popup menu}

            }
        });

        /**Se realiza la consulta a la base de datos */
        String query="SELECT sai_id, sai_desc,sai_calificacion FROM sugerenciaai " +
                " WHERE  sai_status=1";//AND ai_id=2
        Log.i("QUERY_PQ",""+query);
        final ArrayList<HashMap<String, String>> resultsSug;
        resultsSug= Utils.exeLocalQuery(this, query);
        suggestions=new String[3];
        for(int idx=0;idx<resultsSug.size();idx++){
            Log.i("Sugg","-> "+resultsSug.get(idx).get("sai_calificacion"));
            if(null!=resultsSug.get(idx).get("sai_calificacion")){
                if(resultsSug.get(idx).get("sai_calificacion").equals("1")){
                    text1.setText("" + resultsSug.get(idx).get("sai_desc"));
                }
            }
            if(null!=resultsSug.get(idx).get("sai_calificacion")){
                if(resultsSug.get(idx).get("sai_calificacion").equals("2")){
                    text2.setText(""+resultsSug.get(idx).get("sai_desc"));
                }
            }
            if(null!=resultsSug.get(idx).get("sai_calificacion")){
                if(resultsSug.get(idx).get("sai_calificacion").equals("3")){
                    text3.setText(""+resultsSug.get(idx).get("sai_desc"));
                }
            }
        }

    }
    @Override
    public void setOnSubmitListener(String arg) {
        //mTextView.setText(arg);
    }
}
