package com.example.onlinevotingsystem;

import android.content.SharedPreferences;
import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

public class ViewWinner extends AppCompatActivity {
    ListView L1;
    String[] cname;
    String[] pname;
    String[] yfrom;
    String[] yto;
    private final Context context = getApplicationContext();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_winner);
        L1 = (ListView) findViewById(R.id.lst22);

        SharedPreferences sh = context.getSharedPreferences("MyApp", Context.MODE_APPEND);
        String hu = sh.getString("ip", "");
        final String pd = sh.getString("postid", "");

        String url = "http://" + hu + ":5000/view_winner";

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
                                cname = new String[js.length()];
                                pname = new String[js.length()];
                                yfrom = new String[js.length()];
                                yto = new String[js.length()];

                                for (int i = 0; i < js.length(); i++) {
                                    JSONObject u = js.getJSONObject(i);
                                    cname[i] = u.getString("studentname");
                                    pname[i] = u.getString("postname");
                                    yfrom[i] = u.getString("yearfrom");
                                    yto[i] = u.getString("yearto");
                                    ////

                                }
                                //
                                L1.setAdapter(
                                        new custom_view_winner(getApplicationContext(), cname, pname, yfrom, yto));
                                // L.setAdapter(new Custom_view_candidates(getApplicationContext(),id));
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
                Map<String, String> params = new HashMap<String, String>();

                // params.put("postid",pd);

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
}
