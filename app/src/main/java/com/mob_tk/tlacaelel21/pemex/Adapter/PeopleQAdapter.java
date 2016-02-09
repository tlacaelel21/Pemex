package com.mob_tk.tlacaelel21.pemex.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.mob_tk.tlacaelel21.pemex.Model.PeopleQModel;
import com.mob_tk.tlacaelel21.pemex.R;

import java.util.List;

/**
 * Created by tlacaelel21 on 1/02/16.
 */
public class PeopleQAdapter extends ArrayAdapter<PeopleQModel> {
    AQuery aquery;

    public PeopleQAdapter(Context context, int resource, List<PeopleQModel> items) {
        super(context, resource,items);
        aquery = new AQuery(context);
    }

    public static class ViewHolder {
        TextView nombreP;
        TextView numeroP;

        public ViewHolder(View v) {
            nombreP = (TextView) v.findViewById(R.id.list_people_name);
            numeroP = (TextView) v.findViewById(R.id.list_people_number);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_people_qualify, null);
            holder = new ViewHolder(v);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        PeopleQModel p=getItem(position);

        if (p != null) {
            //final String urlLink=p.getUrlExterno();
            holder.nombreP.setText(p.getnameP());
            holder.numeroP.setText(p.getnumberP());
        }
        return v;
    }



}
