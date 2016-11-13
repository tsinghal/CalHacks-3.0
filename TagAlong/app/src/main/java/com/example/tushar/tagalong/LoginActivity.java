package com.example.tushar.tagalong;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import static android.accounts.AccountManager.KEY_PASSWORD;
import static android.os.Build.VERSION_CODES.N;
import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class LoginActivity extends AppCompatActivity {

    private final String url = "http://104.236.204.102/login.php";

    public EditText username;
    public EditText password;
    private Button login;

    public String user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_login);

        TextView myTextView=(TextView)findViewById(R.id.welcomeText);
        Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/pacifico.ttf");
        myTextView.setTypeface(typeFace);

        username = (EditText) this.findViewById(R.id.usernameEditText);
        password = (EditText) this.findViewById(R.id.passwordEditText);
        login = (Button) this.findViewById(R.id.loginButton);

        login.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                try {
                    checkCredentials();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "Test Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //will be called when sign up is pressed
    public void openSignUp(View view){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }


    public void checkCredentials() throws JSONException{

        user = (LoginActivity.this).username.getText().toString();
        pass = (LoginActivity.this).password.getText().toString();


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
                            if(test.equals("flop")){
                                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(LoginActivity.this, "Error! Check Connection", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("mail", user);
                    params.put("pword", pass);
                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);


    }
}
