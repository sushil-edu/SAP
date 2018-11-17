package in.kestone.sap;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.ArrayList;

import in.kestone.sap.adapter.HelpAdapter;
import in.kestone.sap.holder.Help;
import in.kestone.sap.holder.Scan;
import in.kestone.sap.utils.CONSTANTS;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHelp extends Fragment {

//    RecyclerView recyclerView;
//    ArrayList<Help> arrayList = new ArrayList<>();
//    HelpAdapter adapter;
    View view;

    public FragmentHelp() {
        // Required empty public constructor
    }

    private static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE );
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate( R.layout.fragment_help, container, false );

//        recyclerView = view.findViewById( R.id.recyclerView );
//        recyclerView.setHasFixedSize( true );
//        recyclerView.setLayoutManager( new LinearLayoutManager( getContext() ) );
//        if (isNetworkConnected( getActivity() )) {
//           // new HelpDesk( getActivity(), CONSTANTS.GET_HELP_DESK ).execute();
//        } else {
//            Toast.makeText( getActivity(), "Check your internet connection", Toast.LENGTH_LONG ).show();
//        }
        return view;
    }

//    private class HelpDesk extends AsyncTask<String, String, String> {
//        String Url;
//        Activity activity;
//        JSONArray jsonArray;
//        DataOutputStream printout;
//        StringBuilder stringBuilder = new StringBuilder();
//        ProgressDialog dialog;
//        Scan sc;
//
//        public HelpDesk(Activity activity, String Url) {
//            this.Url = Url;
//            this.activity = activity;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            dialog = new ProgressDialog( activity );
//            dialog.setCancelable( false );
//            dialog.setMessage( "Please wait...." );
//            dialog.show();
//        }
//
//        @Override
//        protected String doInBackground(String... strings) {
//            try {
//                //Log.e("Inside","Do in Background");
//                URL url = new URL( Url );
//                HttpURLConnection htp = (HttpURLConnection) url.openConnection();
//
//                Log.e( "Post", "Post" );
//                Log.e( "URL", Url );
//                htp.setDoOutput( true );
//                // is output buffer writer
//                htp.setRequestMethod( "POST" );
//                htp.setRequestProperty( "Content-Type", "application/json" );
//                printout = new DataOutputStream( htp.getOutputStream() );
////                printout.writeBytes( jsonArray.toString() );
//                printout.flush();
//                printout.close();
//
//                InputStream inputStream = htp.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( inputStream ) );
//                String Line;
//                while ((Line = bufferedReader.readLine()) != null) {
//                    stringBuilder.append( Line );
//                }
//                return stringBuilder.toString();
//            } catch (Exception e) {
//                Log.e( "Error", e.getMessage() );
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute( s );
//            if (s != null) {
//                try {
//
//                    JSONArray jsonArray = new JSONArray( s );
//
//                    if (jsonArray.length() > 0) {
//                        arrayList.clear();
//                        for (int i = 0; i < arrayList.size(); i++) {
//                            JSONObject object = jsonArray.getJSONObject( i );
//                            Log.e( "Response ", "" + object.getString( "Name" ) );
//                            Help hlp = new Help();
//                            hlp.setName( object.getString( "Name" ) );
//                            hlp.setEmail( object.getString( "Email" ) );
//                            hlp.setPhoneNumber( object.getString( "PhoneNumber" ) );
//                            arrayList.add( hlp );
//                        }
//                        adapter = new HelpAdapter( getActivity(), arrayList );
//                        recyclerView.setAdapter( adapter );
//                        adapter.notifyDataSetChanged();
//                    }
//
//
//                } catch (JSONException e) {
//                    Toast.makeText( activity, "Connection time out", Toast.LENGTH_SHORT ).show();
//                }
//            }
//            dialog.dismiss();
//        }
//
//    }
}
