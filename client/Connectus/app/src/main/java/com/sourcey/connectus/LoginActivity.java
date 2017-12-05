package com.sourcey.connectus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

//    private String port = MainActivity.port;
    private String url = MainActivity.url;
    private String createlogin = MainActivity.createlogin;
    private String checklogin = MainActivity.checklogin;

    private RequestQueue queue;

    boolean status;

    @Bind(R.id.input_phone) EditText _phone;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_login) Button _loginButton;
    @Bind(R.id.link_signup) TextView _signupLink;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        queue = Volley.newRequestQueue(this);
        
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        final String phone = _phone.getText().toString();
        String password = _passwordText.getText().toString();

        status = true;

        try {
            sendRequest(generate_string_login(phone, password, checklogin), progressDialog, phone);
//            if (!status) {
//                onLoginFailed();
//                return;
//            }
        }


        catch (Exception e) {
            Log.v(TAG, e.toString());
        }

//        new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        // On complete call either onLoginSuccess or onLoginFailed
//
//                        if(status) {
//                            onLoginSuccess();
//                            MainActivity.userPhoneNumber = phone;
//                        }
//
//                        else {
//                            onLoginFailed();
//                        }
//
//                        progressDialog.dismiss();
//                    }
//                }, 3000);
    }

    private String generate_string_login(String id, String pass, String meth) {

        return String.format("%s%s?id=%s&pwd=%s ", url, meth, id, pass);
    }

//    private String generate_string(String id, String type, String data) {
//
//        return String.format("%s%s?id=%s&name=%s&data=%s ", url, editprofile, id, type, data);
//
//    }

    private void sendRequest(String request, final ProgressDialog progressDialog, final String phone) {

        Log.d(TAG, " sendRequest()");



//        String res = null;

        RequestFuture<String> future = RequestFuture.newFuture();


        Toast.makeText(getBaseContext(), request, Toast.LENGTH_LONG).show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, request,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        res = response;
                        Toast.makeText(getBaseContext(), response, Toast.LENGTH_LONG).show();
                        if(response.contains("error")) {
                            status = false;
                        }

                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        // On complete call either onLoginSuccess or onLoginFailed

                                        if(status) {
                                            onLoginSuccess();
                                            MainActivity.userPhoneNumber = phone;
                                        }

                                        else {
                                            onLoginFailed();
                                        }

                                        progressDialog.dismiss();
                                    }
                                }, 3000);

                        Log.v(TAG, response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                status = false;
                Toast.makeText(getBaseContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                // On complete call either onLoginSuccess or onLoginFailed

                                if(status) {
                                    onLoginSuccess();
                                    MainActivity.userPhoneNumber = phone;
                                }

                                else {
                                    onLoginFailed();
                                }

                                progressDialog.dismiss();
                            }
                        }, 3000);

                try {
                    Log.v(TAG, error.getLocalizedMessage());
                }
                catch (Exception e) {
                    Log.v(TAG, e.getMessage());
                }


            }
        });

        queue.add(stringRequest);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String phone = _phone.getText().toString();
        String password = _passwordText.getText().toString();

        if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches()) {
            _phone.setError("enter a valid phone address");
            valid = false;
        } else {
            _phone.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
