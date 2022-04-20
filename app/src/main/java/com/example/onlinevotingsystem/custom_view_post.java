package com.example.onlinevotingsystem;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

public class custom_view_post extends BaseAdapter {
    private final Context context;
    String[] post;

    public custom_view_post(Context context, String[] post1) {
        this.context = context;
        // this.name=name1;
        this.post = post1;
    }

    @Override
    public int getCount() {
        return post.length;
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
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if (view == null) {
            gridView = new View(context);
            // gridView=inflator.inflate(R.layout.customview, null);
            gridView = inflator.inflate(R.layout.custom_view_post, null);

        } else {
            gridView = (View) view;

        }
        // TextView names=(TextView)gridView.findViewById(R.id.textView8);
        TextView postt = (TextView) gridView.findViewById(R.id.textView11);

        // names.setTextColor(Color.BLACK);
        postt.setTextColor(Color.BLACK);

        // names.setText(name[i]);
        postt.setText(post[i]);

        return gridView;
    }

}
