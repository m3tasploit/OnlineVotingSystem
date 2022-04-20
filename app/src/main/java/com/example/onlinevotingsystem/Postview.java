package com.example.onlinevotingsystem;

import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Postview extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView L2;
    String[] postid, post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postview);
        L2 = (ListView) findViewById(R.id.pst);
        L2.setOnItemClickListener(this);

        //
        //
        // DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        // Date dateobj = new Date();
        // String mytime=dateobj.toString();
        // System.out.println(df.format(dateobj));
        //
        //
        //
        // Calendar calobj = Calendar.getInstance();
        // System.out.println(df.format(calobj.getTime()));
        //
        // String[] mytim=mytime.split(" ");
        // String curtime=mytim[1];
        // Toast.makeText(this, curtime, Toast.LENGTH_SHORT).show();

        SharedPreferences sh = getApplicationContext().getSharedPreferences("MyApp", Context.MODE_APPEND);
        String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":5000/view_candidate";

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        // response
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                                JSONArray js = jsonObj.getJSONArray("data");
                                postid = new String[js.length()];
                                post = new String[js.length()];

                                for (int i = 0; i < js.length(); i++) {
                                    JSONObject u = js.getJSONObject(i);
                                    postid[i] = u.getString("postid");
                                    post[i] = u.getString("postname");
                                    //

                                }
                                //
                                L2.setAdapter(new custom_view_post(getApplicationContext(), post));
                            }

                            // }
                            else {
                                Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences sh = getApplicationContext().getSharedPreferences("MyApp", MODE_APPEND);
                Map<String, String> params = new HashMap<String, String>();

                String id = sh.getString("uid", "");
                params.put("uid", id);
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        SimpleDateFormat sdf = new SimpleDateFormat("hh:MM:ss");
        String timeNow = sdf.format(new Date());
        Date timeN = null;
        try {
            timeN = sdf.parse(timeNow);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String attTime = "22:00:00";
        String attTime_1 = "10:00:00";
        Date timeA = null;
        Date timeb = null;
        try {
            timeA = sdf.parse(attTime);
            timeb = sdf.parse(attTime_1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (false) {

            Toast.makeText(getApplicationContext(), "Time over.......", Toast.LENGTH_LONG).show();

        }  else {
            SharedPreferences sh = getApplicationContext().getSharedPreferences("MyApp", MODE_APPEND);
            SharedPreferences.Editor ed = sh.edit();
            ed.putString("postid", postid[i]);
            ed.commit();
            Intent ii = new Intent(getApplicationContext(), ViewCandidate.class);
            startActivity(ii);

        }

    }
}
