package com.sbda.gamal.sbda_android_porject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Base64;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Login extends AppCompatActivity {
    private static final String TAG = "Login";
    private static final int REQUEST_SIGNUP = 0;
    private static String LoggedEmail;
    private String URL = "https://sbda.000webhostapp.com/connect/Android/index.php";

    private JSONParser jsonParser = new JSONParser();

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

    @BindView(R.id.input_email)
    EditText _emailText;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.btn_login)
    Button _loginButton;
    @BindView(R.id.link_signup)
    TextView _signupLink;
    @BindView(R.id.progress)
    ProgressBar _progress;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        _emailText=(EditText) findViewById(R.id.input_email);
        _passwordText=(EditText) findViewById(R.id.input_password);
        _loginButton=(Button) findViewById(R.id.btn_login);
        ButterKnife.bind(this);
        _progress.getIndeterminateDrawable().setColorFilter(0xFF00FF00, android.graphics.PorterDuff.Mode.MULTIPLY);

        if (getSharedPreferences("loggedIn", MODE_PRIVATE).getBoolean("loggedIn", false)) {
            String emailPref = getSharedPreferences("email", MODE_PRIVATE).getString("email", "");
            _emailText.setText(emailPref);
            String passPref = getSharedPreferences("pass", MODE_PRIVATE).getString("pass", "");
            _passwordText.setText(passPref);
            Log.e("XX",emailPref+"...."+passPref);
            login(emailPref,passPref);
        }
            _loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("XX","login btn clicked");

                    login(_emailText.getText().toString(),_passwordText.getText().toString());
                }
            });

            _signupLink.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO open sign up page
                }
            });
        }



       public void login(String email,String password) {
            Log.d(TAG, "Login");

            if (!validate()) {
                _progress.setVisibility(View.VISIBLE);
                onLoginFailed();
                return;
            }


//            LoggedEmail = email;



            AttemptLogin attemptLogin = new AttemptLogin();
            attemptLogin.execute(email, password);


        }


        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
            if (requestCode == REQUEST_SIGNUP) {
                if (resultCode == RESULT_OK) {

                    // TODO: Implement successful signup logic here
                    // By default we just finish the Activity and log them in automatically
                    this.finish();
                }
            }
        }

        @Override
        public void onBackPressed () {
            // disable going back to the MainActivity
            moveTaskToBack(true);
        }

        public void onLoginSuccess () {
//        _loginButton.setEnabled(true);
            finish();
        }

        public void onLoginFailed () {
            Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

//        _loginButton.setEnabled(true);
        }

        public boolean validate () {
            boolean valid = true;

            String email = _emailText.getText().toString();
            String password = _passwordText.getText().toString();

            if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                _emailText.setError("enter a valid email address");
                valid = false;
            } else {
                _emailText.setError(null);
            }

            if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
                _passwordText.setError("between 4 and 10 alphanumeric characters");
                valid = false;
            } else {
                _passwordText.setError(null);
            }

            return valid;
        }

        private class AttemptLogin extends AsyncTask<String, String, JSONObject> {

            @Override

            protected void onPreExecute() {

                super.onPreExecute();

            }

            @Override

            protected JSONObject doInBackground(String... args) {


                String email = args[0];
                String password = args[1];


                ArrayList params = new ArrayList();

                params.add(new BasicNameValuePair("password", password));
                if (email.length() > 0) {
                    params.add(new BasicNameValuePair("email", email));
                }

                JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);


                return json;

            }

            @Override
            protected void onPostExecute(JSONObject result) {

                // dismiss the dialog once product deleted
                //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();

                try {
                    if (result != null) {
                        Toast.makeText(getApplicationContext(), result.getString("message"), Toast.LENGTH_LONG).show();
                        if (result.getInt("success") == 1) {
                            _progress.setVisibility(View.INVISIBLE);
                            startActivity(new Intent(getApplicationContext(), HomePage.class));
                            getSharedPreferences("loggedIn", MODE_PRIVATE)
                                    .edit()
                                    .putBoolean("loggedIn", true)
                                    .commit();

                            getSharedPreferences("email", MODE_PRIVATE)
                                    .edit()
                                    .putString("email", _emailText.getText().toString())
                                    .commit();

                            getSharedPreferences("pass", MODE_PRIVATE)
                                    .edit()
                                    .putString("pass", _passwordText.getText().toString())
                                    .commit();
                            Log.e("XXX",_emailText.getText().toString()+"..."+_passwordText.getText().toString());
                            String emailPref = getSharedPreferences("email", MODE_PRIVATE).getString("email", "");
                            String passPref = getSharedPreferences("pass", MODE_PRIVATE).getString("pass", "");
                            Log.e("XX","after login"+emailPref+"...."+passPref);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
                        if(getSharedPreferences("loggedIn",MODE_PRIVATE).getBoolean("loggedIn",true)){
                            getSharedPreferences("loggedIn", MODE_PRIVATE)
                                    .edit()
                                    .putBoolean("loggedIn", false)
                                    .commit();

                            startActivity(new Intent(getApplicationContext(), Login.class));
                        }

                    }

                    _progress.setVisibility(View.INVISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }


    }

