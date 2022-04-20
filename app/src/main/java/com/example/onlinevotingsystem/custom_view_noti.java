package com.example.onlinevotingsystem;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class custom_view_noti extends BaseAdapter {
    private final Context context;
    String[] cname;
    String[] pnsme;
    String[] yfrom;

    public custom_view_noti(Context context, String[] cname1, String[] pname1, String[] yfrom1) {
        this.context = context;
        this.cname = cname1;
        this.pnsme = pname1;
        this.yfrom = yfrom1;

    }

    @Override
    public int getCount() {
        return cname.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if (view == null) {
            gridView = new View(context);
            // gridView=inflator.inflate(R.layout.customview, null);
            gridView = inflator.inflate(R.layout.custom_view_noti, null);

        } else {
            gridView = (View) view;

        }
        TextView caname = (TextView) gridView.findViewById(R.id.textView14);
        TextView post = (TextView) gridView.findViewById(R.id.textView15);
        TextView yrfrom = (TextView) gridView.findViewById(R.id.textView16);

        post.setTextColor(Color.BLACK);
        caname.setTextColor(Color.BLACK);
        yrfrom.setTextColor(Color.BLACK);

        caname.setText(cname[i]);
        post.setText(pnsme[i]);
        yrfrom.setText(yfrom[i]);

        return gridView;
    }
}
