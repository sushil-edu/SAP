package in.kestone.sap.activity.main;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import in.kestone.sap.R;
import in.kestone.sap.activity.login.LoginActivity;
import in.kestone.sap.adapter.ScanAdapter;
import in.kestone.sap.data.DataManager;
import in.kestone.sap.holder.DBHelpher;
import in.kestone.sap.holder.Scan;
import in.kestone.sap.utils.CONSTANTS;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    IntentIntegrator integrator;
    RecyclerView recyclerView;
    ArrayList<Scan> arrayList = new ArrayList<>();
    ArrayList<Scan> uploadedList = new ArrayList<>();
    DBHelpher dbh;
    ScanAdapter adapter;
    Toolbar toolbar;
    TextView toolbarTitle, tvCount;
    boolean doubleBackToExitPressedOnce = false;
    LinearLayout layoutUpload, layoutScan, layoutEnterCode, LayoutLogout;
    List<String> codeList = new ArrayList<>();
    String[] mStringArray;

    private static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE );
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        //check permission
        requestPermission();

        //initialise view
        initialiseView();
    }

    public void initialiseView() {

        //toolbar
        toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
//        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        toolbarTitle = toolbar.findViewById( R.id.tvTitle );
        tvCount = toolbar.findViewById( R.id.tvCount );
        toolbarTitle.setText( R.string.app_name );


        //fetch data from local database
        dbh = new DBHelpher( this, DBHelpher.DATABASE_NAME );
        arrayList.clear();
        arrayList.addAll( dbh.getAllInfo() );

        //list of scan data
        recyclerView = findViewById( R.id.recyclerView );
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        adapter = new ScanAdapter( this, arrayList );
        recyclerView.setAdapter( adapter );
        adapter.notifyDataSetChanged();

        //for bottom menu
        layoutUpload = findViewById( R.id.layout_upload );
        layoutScan = findViewById( R.id.layout_scan );
        layoutEnterCode = findViewById( R.id.layout_enter_code );
        LayoutLogout = findViewById( R.id.layout_log_out );
        layoutUpload.setOnClickListener( this );
        layoutScan.setOnClickListener( this );
        layoutEnterCode.setOnClickListener( this );
        LayoutLogout.setOnClickListener( this );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e( "Size ", "" + dbh.getAllInfo().size() );
        tvCount.setText( "" + dbh.getAllInfo().size() );
        arrayList.clear();
        arrayList.addAll( dbh.getAllInfo() );
        adapter.notifyDataSetChanged();

//        layoutUpload.setBackgroundColor( getResources().getColor( R.color.colorPrimary ) );
//        layoutScan.setBackgroundColor( getResources().getColor( R.color.colorPrimary ) );
//        layoutEnterCode.setBackgroundColor( getResources().getColor( R.color.colorPrimary ) );
//        LayoutLogout.setBackgroundColor( getResources().getColor( R.color.colorPrimary ) );


//        for (int i = 0; i < arrayList.size(); i++) {
//            Log.e( "Code ", arrayList.get( i ).getScanCode() + "  " + arrayList.get( i ).getUploaded() );
//        }
    }

    void requestPermission() {
        if (ContextCompat.checkSelfPermission( this, Manifest.permission.CAMERA )
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions( this, new String[]{Manifest.permission.CAMERA}, 0 );
        }
    }

    private void updateText(String scanCode) {
        Log.e( "CODE ", scanCode );
        DateFormat df = new SimpleDateFormat( "dd-MM-yyyy HH:mm:ss" );
        String date = df.format( Calendar.getInstance().getTime() );
        dbh.addInfo( new Scan( new DataManager( getApplicationContext() ).getUserId(), scanCode, date, 0 ) );

    }

    private void startQRScanner() {
        integrator = new IntentIntegrator( this );
        integrator.setOrientationLocked( false );
        integrator.setCaptureActivity( Portrait.class );
        integrator.setPrompt( "Scan your code" );
        integrator.setBeepEnabled( true );
        integrator.setOrientationLocked( true );
        integrator.setCameraId( 0 );
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult( requestCode, resultCode, data );
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText( this, "Cancelled", Toast.LENGTH_LONG ).show();
            } else if (result.getContents().contains( "SAPTECHED" )) {
                updateText( result.getContents() );
            } else {
                Toast.makeText( this, "Scan valid QR", Toast.LENGTH_LONG ).show();
            }
        } else {
            super.onActivityResult( requestCode, resultCode, data );
        }
    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText( this, "Please click BACK again to exit", Toast.LENGTH_SHORT ).show();

        new Handler().postDelayed( new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000 );
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_upload:
                String[] aryCode;
                JSONArray jsonArray = new JSONArray();
                if (arrayList.size() > 0) {
                    try {
                        for (int i = 0; i < arrayList.size(); i++) {
                            if (arrayList.get( i ).getUploaded() == 0) {
                                JSONObject object = new JSONObject();
                                object.put( "RefLoginID", arrayList.get( i ).getRefLoginID() );
                                object.put( "ScanCode", arrayList.get( i ).getScanCode() );
                                object.put( "ScanOn", arrayList.get( i ).getScanON() );
                                jsonArray.put( object );
                                codeList.add( arrayList.get( i ).getScanCode() );
                            }
                        }
                        if (codeList.size() > 0) {
                            mStringArray = new String[codeList.size()];
                            mStringArray = codeList.toArray( mStringArray );
                            dbh.updateUploadFlag( mStringArray );
                        } else {
                            Toast.makeText( this, "No data to upload", Toast.LENGTH_SHORT ).show();
                        }
                    } catch (Exception ex) {
                    }
                    if (isNetworkConnected( MainActivity.this )) {
                        new Upload( MainActivity.this, CONSTANTS.UPLOAD_URL, jsonArray ).execute();
                    } else {
                        Toast.makeText( getApplicationContext(), "Check your internet connection", Toast.LENGTH_LONG ).show();
                    }

                } else {
                    Toast.makeText( this, "No data available", Toast.LENGTH_SHORT ).show();
                }
                break;
            case R.id.layout_enter_code:
                enterQrCode();
                break;
            case R.id.layout_scan:
                startQRScanner();
                break;
            case R.id.layout_log_out:
                logout();
                break;
        }
    }

    //for logout from the app
    private void logout() {
        final Dialog clearAll = new Dialog( this );
        clearAll.requestWindowFeature( Window.FEATURE_NO_TITLE );
        clearAll.setCancelable( true );
        clearAll.setContentView( R.layout.dialog_log_out );
        Button no = clearAll.findViewById( R.id.no );
        Button yes = clearAll.findViewById( R.id.yes );
        yes.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAll.dismiss();
                new DataManager( getApplicationContext() ).clear();
                dbh.deleteAll( DBHelpher.TABLE_NAME );
                startActivity( new Intent( MainActivity.this, LoginActivity.class ) );
                finish();
            }
        } );
        no.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAll.dismiss();
            }
        } );

//        clearAll.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
        clearAll.show();
    }

    //for enter manual QR code
    private void enterQrCode() {
        final Dialog dialog = new Dialog( this );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setCancelable( false );
        dialog.setContentView( R.layout.dialog );
        final EditText qrCode;
        qrCode = dialog.findViewById( R.id.et_qr_code );
        dialog.findViewById( R.id.btn_save ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateText( "SAPTECHED" + qrCode.getText().toString() );
                dialog.dismiss();
                onResume();
            }
        } );
        dialog.findViewById( R.id.btn_cancel ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        } );
        adapter.notifyDataSetChanged();

        dialog.show();
    }

    private class Upload extends AsyncTask<String, String, String> {
        String Url;
        Activity activity;
        JSONArray jsonArray;
        DataOutputStream printout;
        StringBuilder stringBuilder = new StringBuilder();
        ProgressDialog dialog;
        Scan sc;

        public Upload(Activity activity, String Url, JSONArray jsonArray) {
            this.Url = Url;
            this.activity = activity;
            this.jsonArray = jsonArray;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog( activity );
            dialog.setCancelable( false );
            dialog.setMessage( "Uploading...." );
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
                Log.e( "ParamsUpload", jsonArray.toString() );
                htp.setDoOutput( true );
                // is output buffer writer
                htp.setRequestMethod( "POST" );
                htp.setRequestProperty( "Content-Type", "application/json" );
                printout = new DataOutputStream( htp.getOutputStream() );
                printout.writeBytes( jsonArray.toString() );
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
                    JSONObject object = new JSONObject( s );
                    if (object.getString( "retval" ).contains( "Registration Successfull." )) {
                        Toast.makeText( activity, "Upload successfully", Toast.LENGTH_SHORT ).show();
                        dbh.updateUploadFlag( mStringArray );
                    } else {
                        Toast.makeText( activity, "Upload not uploaded", Toast.LENGTH_SHORT ).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText( activity, "Connection time out", Toast.LENGTH_SHORT ).show();
                }
            }
            dialog.dismiss();
        }

    }

}
