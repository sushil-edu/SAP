package in.kestone.sap.activity.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import in.kestone.sap.R;
import in.kestone.sap.activity.main.MainActivity;
import in.kestone.sap.data.DataManager;
import in.kestone.sap.holder.Scan;
import in.kestone.sap.utils.APIInterface;
import in.kestone.sap.utils.ApiClient;
import in.kestone.sap.utils.CONSTANTS;
import in.kestone.sap.utils.LoginResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {

    EditText etEmail, etPassword;
    ProgressDialog progressDialog;
    APIInterface apiInterface;
    List<LoginResult> loginResultList;

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty( target ) && Patterns.EMAIL_ADDRESS.matcher( target ).matches());
    }

    private static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE );
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        initialiseView();
    }

    private void initialiseView() {

        etEmail = findViewById( R.id.etEmail );
        etPassword = findViewById( R.id.etPassword );

        progressDialog = new ProgressDialog( this );
        progressDialog.setCancelable( false );
        progressDialog.setMessage( getString( R.string.CheckCredentials ) );

        findViewById( R.id.btnLogIn ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidEmail( etEmail.getText().toString() ) && etPassword.getText().length() > 3) {
                    if (isNetworkConnected( LoginActivity.this )) {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put( "EmailID", etEmail.getText().toString() );
                            jsonObject.put( "Password", etPassword.getText().toString() );
                            new Login( LoginActivity.this, CONSTANTS.LOGIN_URL, jsonObject ).execute();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


//                        callApi( etEmail.getText().toString(), etPassword.getText().toString() );
                    } else {
                        Toast.makeText( LoginActivity.this, "Check your internet connection", Toast.LENGTH_LONG ).show();
                    }
                } else {
                    Toast.makeText( LoginActivity.this, "Please Enter Valid Email & Password", Toast.LENGTH_LONG ).show();
                }
            }
        } );
    }

    private void callApi(String email, String password) {
        final ProgressDialog dialog;
        dialog = new ProgressDialog( LoginActivity.this );
//        dialog.setTitle("Verifying");
        dialog.setCancelable( false );
        dialog.setMessage( "Verifying...." );
        dialog.show();

        apiInterface = ApiClient.getClient().create( APIInterface.class );
        LoginResult result = new LoginResult();
        result.setEmailID( email );
        result.setPassword( password );
        Call<List<LoginResult>> call1 = apiInterface.userLogin( result );
        call1.enqueue( new Callback<List<LoginResult>>() {
            @Override
            public void onResponse(Call<List<LoginResult>> call, Response<List<LoginResult>> response) {
                loginResultList = response.body();
                if (loginResultList.size() > 0) {
                    String email, id, name, organization, designation, mobile;
                    email = loginResultList.get( 0 ).getEmailID();
                    id = loginResultList.get( 0 ).getID();
                    name = loginResultList.get( 0 ).getName();
                    organization = loginResultList.get( 0 ).getOrganization();
                    designation = loginResultList.get( 0 ).getDesignation();
                    mobile = loginResultList.get( 0 ).getMobile();

                    new DataManager( getApplicationContext() ).saveDetail( id, name, email, designation, organization, mobile );

                    Intent intent = new Intent( LoginActivity.this, MainActivity.class );
                    startActivity( intent );
                    finish();

                } else {
                    Toast.makeText( LoginActivity.this, "Enter valid credential.", Toast.LENGTH_SHORT ).show();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<LoginResult>> call, Throwable t) {
                Toast.makeText( getApplicationContext(), "Server error\n" + t.getMessage(), Toast.LENGTH_SHORT ).show();
                Log.e( "Exception ", t.getMessage() );
                call.cancel();
                dialog.dismiss();
            }
        } );
    }

    private class Login extends AsyncTask<String, String, String> {
        String Url;
        Activity activity;
        JSONObject jsonObject;
        DataOutputStream printout;
        StringBuilder stringBuilder = new StringBuilder();
        ProgressDialog dialog;
        Scan sc;

        public Login(Activity activity, String Url, JSONObject jsonObject) {
            this.Url = Url;
            this.activity = activity;
            this.jsonObject = jsonObject;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog( activity );
            dialog.setCancelable( false );
            dialog.setMessage( "Verifying...." );
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                //Log.e("Inside","Do in Background");
                URL url = new URL( Url );
                HttpURLConnection htp = (HttpURLConnection) url.openConnection();

                Log.e( "Post", "Post" );
                Log.e( "URL", Url );
                Log.e( "ParamsUpload", jsonObject.toString() );
                htp.setDoOutput( true );
                // is output buffer writer
                htp.setRequestMethod( "POST" );
                htp.setRequestProperty( "Content-Type", "application/json" );
                printout = new DataOutputStream( htp.getOutputStream() );
                printout.writeBytes( jsonObject.toString() );
                printout.flush();
                printout.close();

                InputStream inputStream = htp.getInputStream();
                BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( inputStream ) );
                String Line;
                while ((Line = bufferedReader.readLine()) != null) {
                    stringBuilder.append( Line );
                }
                return stringBuilder.toString();
            } catch (Exception e) {
                Log.e( "Error", e.getMessage() );
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute( s );
            if (s != null) {
                try {
                    JSONArray array = new JSONArray( s );
                    if (array.length() > 0) {
                        String email, id, name, organization, designation, mobile;
                        JSONObject jsonObject = array.getJSONObject( 0 );
                        email = jsonObject.getString( "EmailID" );
                        id = jsonObject.getString( "ID" );
                        name = jsonObject.getString( "Name" );
                        organization = jsonObject.getString( "Organization" );
                        designation = jsonObject.getString( "Designation" );
                        mobile = jsonObject.getString( "Mobile" );

                        new DataManager( getApplicationContext() ).saveDetail( id, name, email, designation, organization, mobile );

                        Intent intent = new Intent( LoginActivity.this, MainActivity.class );
                        startActivity( intent );
                        finish();

                    } else {
                        Toast.makeText( LoginActivity.this, "Enter valid credential.", Toast.LENGTH_SHORT ).show();
                    }
                } catch (Exception e) {
                    Toast.makeText( activity, "Connection time out", Toast.LENGTH_SHORT ).show();
                }
            }
            dialog.dismiss();
        }

    }

}
