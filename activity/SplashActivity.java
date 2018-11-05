package in.kestone.sap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import in.kestone.sap.R;
import in.kestone.sap.activity.login.LoginActivity;
import in.kestone.sap.activity.main.MainActivity;
import in.kestone.sap.data.DataManager;
import in.kestone.sap.data.SharedPrefsHelper;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView( R.layout.activity_splash );

        new Handler().postDelayed( new Runnable() {
            @Override
            public void run() {
                openActivity();
            }
        }, 2000 );
    }

    private void openActivity() {
        Intent intent;
        Log.e("Status ", String.valueOf( new DataManager(getApplicationContext()).getLoggedInMode() ) );
        if (new DataManager(getApplicationContext()).getLoggedInMode()) {
            intent = new Intent( this, MainActivity.class );
        } else {
            intent = new Intent( this, LoginActivity.class );
        }
        startActivity( intent );
        finish();
    }
}
