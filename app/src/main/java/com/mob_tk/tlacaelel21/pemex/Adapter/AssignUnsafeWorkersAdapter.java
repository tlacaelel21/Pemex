package com.mob_tk.tlacaelel21.pemex.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.mob_tk.tlacaelel21.pemex.Model.WorkersModel;
import com.mob_tk.tlacaelel21.pemex.R;

import java.util.List;

/**
 * Created by tlacaelel21 on 1/11/15.
 */
public class AssignUnsafeWorkersAdapter extends ArrayAdapter<WorkersModel> {

    AQuery aquery;

    public AssignUnsafeWorkersAdapter(Context context, int resource, List<WorkersModel> items) {
        super(context, resource,items);
        aquery = new AQuery(context);
    }
    public static class ViewHolder {
        TextView workerName;
        TextView workerNumber;
        CheckBox chkGreen;
        CheckBox chkYell;
        CheckBox chkRed;

        public ViewHolder(View v) {
            workerName = (TextView) v.findViewById(R.id.assign_name_worker);
            workerNumber = (TextView) v.findViewById(R.id.assign_number_worker);
            chkGreen=(CheckBox)v.findViewById(R.id.chk_worker_0);
            chkYell=(CheckBox)v.findViewById(R.id.chk_worker_1);
            chkRed=(CheckBox)v.findViewById(R.id.chk_worker_3);
        }


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        final ViewHolder holder;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_assign_unsafe, null);
            holder = new ViewHolder(v);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        WorkersModel p=getItem(position);

        if (p != null) {
            holder.workerName.setText(p.getWorkerName());
            holder.workerNumber.setText(p.getWorkerNumber());
            //holder.workerChk.setVisibility(v.VISIBLE);

            holder.chkGreen.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    boolean isOk = ((CheckBox) view).isChecked();
                    holder.chkGreen.setButtonDrawable(R.drawable.target_green);
                    holder.chkRed.setButtonDrawable(R.drawable.target_b);
                    holder.chkYell.setButtonDrawable(R.drawable.target_b);
                    //colocaLeyChk(chkRed,isOk);
                }
            });

            holder.chkYell.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    boolean isOk = ((CheckBox) view).isChecked();
                    holder.chkYell.setButtonDrawable(R.drawable.target_yellow);
                    holder.chkGreen.setButtonDrawable(R.drawable.target_b);
                    holder.chkRed.setButtonDrawable(R.drawable.target_b);
                    //colocaLeyChk(chkRed,isOk);
                }
            });

            holder.chkRed.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    boolean isOk = ((CheckBox) view).isChecked();
                    holder.chkRed.setButtonDrawable(R.drawable.target_red);
                    holder.chkGreen.setButtonDrawable(R.drawable.target_b);
                    holder.chkYell.setButtonDrawable(R.drawable.target_b);
                    //colocaLeyChk(chkRed,isOk);
                }
            });
        }

        return v;
    }
}

