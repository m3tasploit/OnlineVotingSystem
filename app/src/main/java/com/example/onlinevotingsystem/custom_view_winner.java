package com.example.onlinevotingsystem;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class custom_view_winner extends BaseAdapter {
    private final Context context;
    String[] cname;
    String[] pnsme;
    String[] yfrom;
    String[] yto;

    public custom_view_winner(Context context, String[] cname1, String[] pname1, String[] yfrom1, String[] yto1) {
        this.context = context;
        this.cname = cname1;
        this.pnsme = pname1;
        this.yfrom = yfrom1;
        this.yto = yto1;

    }

    @Override
    public int getCount() {
        return yto.length;
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
            gridView = inflator.inflate(R.layout.custom_view_winner, null);

        } else {
            gridView = (View) view;

        }
        TextView caname = (TextView) gridView.findViewById(R.id.textView14);
        TextView post = (TextView) gridView.findViewById(R.id.textView15);
        TextView yrfrom = (TextView) gridView.findViewById(R.id.textView16);
        TextView yrto = (TextView) gridView.findViewById(R.id.textView17);

        post.setTextColor(Color.BLACK);
        caname.setTextColor(Color.BLACK);
        yrfrom.setTextColor(Color.BLACK);
        yrto.setTextColor(Color.BLACK);

        caname.setText(cname[i]);
        post.setText(pnsme[i]);
        yrfrom.setText(yfrom[i]);
        yrto.setText(yto[i]);

        return gridView;
    }
}
