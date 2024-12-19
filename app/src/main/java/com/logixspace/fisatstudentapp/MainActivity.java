package com.logixspace.fisatstudentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText etFirstName = findViewById(R.id.etFirstName);
        EditText etLastName = findViewById(R.id.etLastName);
        EditText etPurpose = findViewById(R.id.etPurpose);
        EditText etWhomToMeet = findViewById(R.id.etWhomToMeet);
        Button btnSubmit = findViewById(R.id.btnSubmit);
        Button btnView = findViewById(R.id.viewall);

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(i);
            }
        });
        btnSubmit.setOnClickListener(view -> {
            String firstName = etFirstName.getText().toString().trim();
            String lastName = etLastName.getText().toString().trim();
            String purpose = etPurpose.getText().toString().trim();
            String whomToMeet = etWhomToMeet.getText().toString().trim();

            if (firstName.isEmpty() || lastName.isEmpty() || purpose.isEmpty() || whomToMeet.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            callApi(firstName, lastName, purpose, whomToMeet);
        });
    }

    private void callApi(String firstName, String lastName, String purpose, String whomToMeet) {
        String url = "https://log-app-demo-api.onrender.com/addvisitor";

        JSONObject postData = new JSONObject();
        try {
            postData.put("firstname", firstName);
            postData.put("lastname", lastName);
            postData.put("purpose", purpose);
            postData.put("whomToMeet", whomToMeet);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSON error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                postData,
                response -> Toast.makeText(MainActivity.this, "Data submitted successfully!", Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show()
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
}
