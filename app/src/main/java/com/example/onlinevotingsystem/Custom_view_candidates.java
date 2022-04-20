package com.example.onlinevotingsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Custom_view_candidates extends BaseAdapter {
    private final Context context;
    String[] name;
    String[] id;

    public Custom_view_candidates(Context context, String[] name1, String[] id1) {
        this.context = context;
        this.name = name1;
        this.id = id1;

    }

    @Override
    public int getCount() {
        return name.length;
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
            gridView = inflator.inflate(R.layout.custom_view_candidate, null);

        } else {
            gridView = (View) view;

        }
        // TextView names=(TextView)gridView.findViewById(R.id.textView8);
        TextView postt = (TextView) gridView.findViewById(R.id.textView7);
        final Button btn = (Button) gridView.findViewById(R.id.button4);
        btn.setTag(id[i]);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final String candid = btn.getTag().toString();
                SharedPreferences sh = context.getApplicationContext().getSharedPreferences("MyApp", Context.MODE_APPEND);
                String hu = sh.getString("ip", "");
                String url = "http://" + hu + ":5000/cast_vote";

                RequestQueue requestQueue = Volley.newRequestQueue(context);
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                // response
                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                        Intent ic = new Intent(context, Postview.class);
                                        ic.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(ic);

                                    }

                                    // }
                                    else {
                                        Toast.makeText(context, "Not found", Toast.LENGTH_LONG).show();
                                    }

                                } catch (Exception e) {
                                    Toast.makeText(context, "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT)
                                            .show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Toast.makeText(context, "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Context context1 = context.getApplicationContext();
                        SharedPreferences sh = context1.getSharedPreferences("MyApp", Context.MODE_APPEND);
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("stuid", sh.getString("uid", ""));
                        params.put("candid", candid);
                        // params.put("mac",maclis);

                        return params;
                    }
                };

                int MY_SOCKET_TIMEOUT_MS = 100000;

                postRequest.setRetryPolicy(new DefaultRetryPolicy(
                        MY_SOCKET_TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(postRequest);

            }
        });

        // names.setTextColor(Color.BLACK);
        postt.setTextColor(Color.BLACK);

        // names.setText(name[i]);
        postt.setText(name[i]);
        return gridView;

    }

}
