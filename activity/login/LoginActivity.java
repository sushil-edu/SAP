package in.kestone.sap.activity.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import in.kestone.sap.R;
import in.kestone.sap.activity.main.MainActivity;
import in.kestone.sap.data.DataManager;
import in.kestone.sap.utils.APIInterface;
import in.kestone.sap.utils.ApiClient;
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
                        callApi( etEmail.getText().toString(), etPassword.getText().toString() );
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
                Toast.makeText( getApplicationContext(), "Server error", Toast.LENGTH_SHORT ).show();
                Log.e( "Exception ", t.getMessage() );
                call.cancel();
                dialog.dismiss();
            }
        } );
    }

}
