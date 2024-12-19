package com.logixspace.fisatstudentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {

    private TextView tvVisitorData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        tvVisitorData = findViewById(R.id.tvVisitorData);
        fetchVisitors();
    }

    private void fetchVisitors() {
        String url = "https://log-app-demo-api.onrender.com/getvistors";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        StringBuilder visitorData = new StringBuilder();

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject visitorObj = response.getJSONObject(i);

                                String firstName = visitorObj.getString("firstname");
                                String lastName = visitorObj.getString("lastname");
                                String purpose = visitorObj.getString("purpose");
                                String whomToMeet = visitorObj.getString("whomToMeet");

                                // Append visitor details to the StringBuilder
                                visitorData.append("Name: ").append(firstName).append(" ").append(lastName).append("\n");
                                visitorData.append("Purpose: ").append(purpose).append("\n");
                                visitorData.append("Whom to Meet: ").append(whomToMeet).append("\n");
                                visitorData.append("----------------------\n");
                            }

                            // Display the data in the TextView
                            tvVisitorData.setText(visitorData.toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "JSON Parsing Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}
