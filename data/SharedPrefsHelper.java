package in.kestone.sap.data;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SharedPrefsHelper {
    private static final String MY_PREFS = "MY_PREFS";
     SharedPreferences mSharedPreferences;
    private String EMAIL = "EmailID";
    private String USERID = "ID";
    private String NAME = "Name";
    private String DESIGNATION = "Designation";
    private String ORGANIZATION = "Organization";
    private String MOBILE = "Mobile";
    private boolean isLogin=false;


    public SharedPrefsHelper(Context context){
        mSharedPreferences = context.getSharedPreferences( MY_PREFS, MODE_PRIVATE );
    }

    public void clear() {
        mSharedPreferences.edit().clear().apply();
    }

    public void putEmail(String email) {
        mSharedPreferences.edit().putString( EMAIL, email ).apply();
    }

    public String getEmail() {
        return mSharedPreferences.getString( EMAIL, null );
    }

    public String getUserId() {
        return mSharedPreferences.getString( USERID, null );
    }

    public void putUserId(String uID) {
        mSharedPreferences.edit().putString( USERID, uID ).apply();
    }

    public void putName(String name) {
        mSharedPreferences.edit().putString( NAME, name ).apply();
    }

    public String getName() {
        return mSharedPreferences.getString( NAME, null );
    }


    public void putDesignation(String path) {
        mSharedPreferences.edit().putString( DESIGNATION, path ).apply();
    }

    public String getDesignation() {
        return mSharedPreferences.getString( DESIGNATION, null );
    }

    public void putOrganization(String organization) {
        mSharedPreferences.edit().putString( ORGANIZATION, organization ).apply();
    }

    public String getOrganization() {
        return mSharedPreferences.getString( ORGANIZATION, null );
    }

    public void putMobile(String mobile) {
        mSharedPreferences.edit().putString( MOBILE, mobile ).apply();
    }

    public String getMobile() {
        return mSharedPreferences.getString( MOBILE, null );
    }

    public boolean getLoggedInMode() {
        return mSharedPreferences.getBoolean( "IS_LOGGED_IN", false );
    }

    public void setLoggedInMode(boolean loggedIn) {
        mSharedPreferences.edit().putBoolean( "IS_LOGGED_IN", loggedIn ).apply();
    }
}
