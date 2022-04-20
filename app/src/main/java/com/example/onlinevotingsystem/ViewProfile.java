package com.example.onlinevotingsystem;

import android.content.SharedPreferences;
import android.content.Context;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ViewProfile extends AppCompatActivity {
    ImageView I1;
    private final Context context = getApplicationContext();
    TextView name, admno, course, semester, gender, guardian, dob, phoneno, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        I1 = (ImageView) findViewById(R.id.imageView);
        name = (TextView) findViewById(R.id.textView);
        admno = (TextView) findViewById(R.id.textView2);
        course = (TextView) findViewById(R.id.textView3);
        semester = (TextView) findViewById(R.id.textView4);
        gender = (TextView) findViewById(R.id.textView5);
        guardian = (TextView) findViewById(R.id.textView6);
        dob = (TextView) findViewById(R.id.textView9);
        phoneno = (TextView) findViewById(R.id.textView10);
        email = (TextView) findViewById(R.id.textView12);

        final SharedPreferences sh = context.getSharedPreferences("MyApp", Context.MODE_PRIVATE);

        String hu = sh.getString("ip", "");

        String url = "http://" + hu + ":5000/view_profile";

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

                                final String sname = jsonObj.getString("name");
                                name.setText(sname);
                                final String sadmno = jsonObj.getString("admno");
                                admno.setText(sadmno);
                                final String scourse = jsonObj.getString("course");
                                course.setText(scourse);
                                final String ssemester = jsonObj.getString("semester");
                                semester.setText(ssemester);
                                final String sgender = jsonObj.getString("gender");
                                gender.setText(sgender);
                                final String sguardian = jsonObj.getString("guardian");
                                guardian.setText(sguardian);
                                final String sdob = jsonObj.getString("dob");
                                dob.setText(sdob);
                                final String sphoneno = jsonObj.getString("phone");
                                phoneno.setText(sphoneno);
                                final String semail = jsonObj.getString("email");
                                email.setText(semail);

                                try {
                                    String hu = sh.getString("ip", "");
                                    String url1 = "http://" + hu + ":5000" + jsonObj.getString("image");
                                    // Toast.makeText(ViewProfile.this, ""+url1, Toast.LENGTH_SHORT).show();
                                    Picasso.with(ViewProfile.this).load(url1.toString())
                                            .memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE)
                                            .transform(new CircleTransform()).error(R.drawable.ic_launcher_background)
                                            .into(I1);

                                } catch (Exception e) {
                                    Toast.makeText(ViewProfile.this, e.toString(), Toast.LENGTH_SHORT).show();
                                }

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
                SharedPreferences shp = context.getSharedPreferences("MyApp", Context.MODE_PRIVATE);
                Map<String, String> params = new HashMap<String, String>();
                String uid = shp.getString("uid", "");
                params.put("idd", uid);

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
