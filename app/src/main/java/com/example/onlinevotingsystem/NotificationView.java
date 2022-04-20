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

public class NotificationView extends AppCompatActivity {
    ListView L1;
    String[] notid;
    String[] notcont, date, exp_date;
    private final Context context = getApplicationContext();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_view);

        L1 = (ListView) findViewById(R.id.ListView);
        // L1.setOnItemClickListener(this);

        SharedPreferences sh = context.getSharedPreferences("MyApp", Context.MODE_PRIVATE);
        String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":5000/View_notification";

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
                                notid = new String[js.length()];
                                notcont = new String[js.length()];
                                date = new String[js.length()];
                                exp_date = new String[js.length()];

                                for (int i = 0; i < js.length(); i++) {
                                    JSONObject u = js.getJSONObject(i);
                                    notid[i] = u.getString("notid");
                                    notcont[i] = u.getString("notcontent");
                                    date[i] = u.getString("notdate");
                                    exp_date[i] = u.getString("expirydate");
                                    //

                                }
                                //
                                L1.setAdapter(new custom_view_noti(getApplicationContext(), notcont, date, exp_date));
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
                SharedPreferences sh = getApplicationContext().getSharedPreferences("MyApp", MODE_PRIVATE);
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
}
