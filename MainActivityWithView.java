package in.kestone.sap;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.ResultPoint;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
//import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.util.List;

public class MainActivityWithView extends AppCompatActivity {

//    DecoratedBarcodeView dbvScanner;
    IntentIntegrator integrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main_withview );

//        dbvScanner = (DecoratedBarcodeView) findViewById( R.id.dbv_barcode );
//        requestPermission();

//        findViewById( R.id.floatingActionButton ).setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                resumeScanner();
//                startQRScanner();
//            }
//
//        } );
//        dbvScanner.setStatusText( "Place your QR Code inside the rectangle to scan it." );
//
//        dbvScanner.decodeContinuous( new BarcodeCallback() {
//            @Override
//            public void barcodeResult(BarcodeResult result) {
//                updateText( result.getText() );
////                beepSound();
//            }
//
//            @Override
//            public void possibleResultPoints(List<ResultPoint> resultPoints) {
//
//            }
//        } );
//
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        // resumeScanner();
//    }
//
//    protected void resumeScanner() {
////        isScanDone = false;
//        if (!dbvScanner.isActivated())
//            dbvScanner.resume();
//    }
//
//    protected void pauseScanner() {
//        dbvScanner.pause();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        pauseScanner();
//    }
//
//    void requestPermission() {
//        if (ContextCompat.checkSelfPermission( this, Manifest.permission.CAMERA )
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions( this, new String[]{Manifest.permission.CAMERA}, 0 );
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
//        if (requestCode == 0 && grantResults.length < 1) {
//            requestPermission();
//        } else {
//            dbvScanner.resume();
//        }
//    }
//
//    private void updateText(String scanCode) {
//        Log.e( "CODE ", scanCode );
//        pauseScanner();
//    }
//
//    private void startQRScanner() {
//        integrator=new IntentIntegrator(this);
//        integrator.setPrompt("Scan your code");
//        integrator.initiateScan();
//
//
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        IntentResult result =   IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
//        if (result != null) {
//            if (result.getContents() == null) {
//                Toast.makeText(this,    "Cancelled", Toast.LENGTH_LONG).show();
//            } else {
//                updateText(result.getContents());
//            }
//        } else {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
    }
}
