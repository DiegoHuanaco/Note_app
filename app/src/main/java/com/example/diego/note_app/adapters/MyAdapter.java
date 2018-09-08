package com.example.diego.note_app.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.diego.note_app.R;
import com.example.diego.note_app.models.Nota;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    private List<Nota> list;
    private int layout;
    private Context context;

    public MyAdapter(List<Nota> list, int layout, Context context) {
        this.list = list;
        this.layout = layout;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Nota getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layout, null);
            vh = new ViewHolder();
            vh.nota = (TextView) convertView.findViewById(R.id.textViewNota);
            vh.id = (TextView) convertView.findViewById(R.id.textViewID);
            vh.lay = (LinearLayout) convertView.findViewById(R.id.linerLayaout);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        Nota n = list.get(position);
       // vh.id.setText(n.getId() + "");
        vh.nota.setText(n.getNota());
        if (n.getColor() == 1)
            vh.lay.setBackgroundColor(Color.BLUE);
       else if (n.getColor() == 2)
            vh.lay.setBackgroundColor(Color.GREEN);
        else if (n.getColor() == 3)
            vh.lay.setBackgroundColor(Color.MAGENTA);
        else
            vh.lay.setBackgroundColor(Color.WHITE);


        return convertView;
    }

    public class ViewHolder {
        TextView nota;
        TextView id;
        LinearLayout lay;
    }
}