package com.example.onlinevotingsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Capturephoto extends AppCompatActivity implements View.OnClickListener {

    ImageView img;
    Button bt;
    private final Context context = getApplicationContext();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capturephoto);

        img = (ImageView) findViewById(R.id.imageView2);
        bt = (Button) findViewById(R.id.button5);

        img.setOnClickListener(this);
        bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (img == view) {
            open();
        } else {

            SharedPreferences sh = context.getSharedPreferences("MyApp", Context.MODE_PRIVATE);
            String hu = sh.getString("ip", "");
            String url = "http://" + hu + ":5000/face_recognition";

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                            // response
                            try {
                                JSONObject jsonObj = new JSONObject(response);
                                if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                                    String stuid = jsonObj.getString("id");

                                    SharedPreferences sh = context.getSharedPreferences("MyApp", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor ed = sh.edit();
                                    ed.putString("uid", stuid);
                                    ed.commit();

                                    Intent ii = new Intent(getApplicationContext(), Home.class);
                                    startActivity(ii);

                                }

                                // }
                                else {
                                    // Toast.makeText(getApplicationContext(), "Not found",
                                    // Toast.LENGTH_LONG).show();
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
                            Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("photo", encodedImage);

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

    public void open() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }

    String encodedImage = "";

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Bitmap bp = (Bitmap) data.getExtras().get("data");
        // imgFavorite.setImageBitmap(bp);
        byte[] bitmapdata;

        if (resultCode == RESULT_OK) {

            if (data == null) {
                Toast.makeText(getApplicationContext(), "null data ", Toast.LENGTH_SHORT).show();
            }

            if (requestCode == 0) {
                if (resultCode == RESULT_OK) {

                    try {
                        Bitmap image = (Bitmap) data.getExtras().get("data");
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        image.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap object

                        byte[] b = baos.toByteArray();
                        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                        // Toast.makeText(this,encodedImage, Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
                        Toast.makeText(this, "String :" + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }

                    byte[] bt = Base64.decode(encodedImage, Base64.DEFAULT);

                    // byte bt[]=re.getBytes();
                    // Log.d("imag.....", bt.toString());
                    Bitmap bmp = BitmapFactory.decodeByteArray(bt, 0, bt.length);
                    if (bmp != null) {
                        img.setImageBitmap(bmp);
                    }

                }

            }

        }
    }
}
