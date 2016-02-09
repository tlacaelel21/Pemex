package com.mob_tk.tlacaelel21.pemex.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.mob_tk.tlacaelel21.pemex.Model.DepartamentsModel;
import com.mob_tk.tlacaelel21.pemex.R;

import java.util.List;

/**
 * Created by tlacaelel21 on 26/10/15.
 */
public class DepartamentsAdapter extends ArrayAdapter<DepartamentsModel> {
    AQuery aquery;

    public DepartamentsAdapter(Context context, int resource, List<DepartamentsModel> items) {
        super(context, resource,items);
        aquery = new AQuery(context);
    }

    public static class ViewHolder {
        TextView tituloWP;
        TextView datoWP;

        public ViewHolder(View v) {
            tituloWP = (TextView) v.findViewById(R.id.list_wp_titulo);
            datoWP = (TextView) v.findViewById(R.id.list_wp_dato);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_departaments, null);
            holder = new ViewHolder(v);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        DepartamentsModel p=getItem(position);

        if (p != null) {
            //final String urlLink=p.getUrlExterno();
            holder.tituloWP.setText(p.getWpTitulo());
            holder.datoWP.setText(p.getWpDato());
        }
        return v;
    }
}
