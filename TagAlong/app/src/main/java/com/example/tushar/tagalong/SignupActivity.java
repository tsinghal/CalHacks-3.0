package com.example.tushar.tagalong;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    private EditText email;
    private EditText pass;
    private EditText name;
    private Button signup;

    private final String url = "http://104.236.204.102/register_user.php";

    public String emailid, password, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_signup);

        email = (EditText)findViewById(R.id.emailEditText);
        pass = (EditText)findViewById(R.id.passwordEditText);
        name = (EditText)findViewById(R.id.nameEditText);

        signup = (Button)findViewById(R.id.doneButton);
        signup.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                try {
                    openHome();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(SignupActivity.this, "Sorry! Check Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void openHome() throws JSONException {

        emailid = (SignupActivity.this).email.getText().toString();
        password = (SignupActivity.this).pass.getText().toString();
        username = (SignupActivity.this).name.getText().toString();

        if(!emailid.isEmpty() && !password.isEmpty() && !username.isEmpty()) {
            Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
            Toast.makeText(SignupActivity.this, "Congrats! You have been added", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }else {
            Toast.makeText(SignupActivity.this, "Sorry! Account creation failed", Toast.LENGTH_SHORT).show();
        }

        /*
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        String test;

                        public void onResponse(String response) {
                            try {
                                JSONObject reader = new JSONObject(response);
                                test = reader.getString("response");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (test.equals("flop")) {
                                Toast.makeText(SignupActivity.this, "Sorry! Account creation failed", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
                                Toast.makeText(SignupActivity.this, "Congrats! You have been added", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SignupActivity.this, "Error! Check Connection", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("name", username);
                    params.put("mail", emailid);
                    params.put("pword", password);
                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }*/
    }
}
