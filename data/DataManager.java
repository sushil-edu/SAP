package in.kestone.sap.data;

import android.content.Context;

public class DataManager {
    private  SharedPrefsHelper mSharedPrefsHelper;

    public DataManager(Context context) {
        mSharedPrefsHelper = new SharedPrefsHelper( context );
    }

    public void clear() {
        mSharedPrefsHelper.clear();
    }

    public void saveDetail(String userId, String name, String email,  String designation,
                           String organization, String mobile) {
        mSharedPrefsHelper.putEmail(email);
        mSharedPrefsHelper.putUserId(userId);
        mSharedPrefsHelper.putName(name);
        mSharedPrefsHelper.putDesignation(designation);
        mSharedPrefsHelper.putOrganization(organization);
        mSharedPrefsHelper.putMobile(mobile);
        setLoggedIn();
    }


    public String getEmailId() {
        return mSharedPrefsHelper.getEmail();
    }
    public String getUserId() {
        return mSharedPrefsHelper.getUserId();
    }
    public String getName() {
        return mSharedPrefsHelper.getName();
    }
    public String getDesignation() {
        return mSharedPrefsHelper.getDesignation();
    }
    public String getOrganization() {
        return mSharedPrefsHelper.getOrganization();
    }
    public String getMobile() {
        return mSharedPrefsHelper.getMobile();
    }


    public void setLoggedIn() {
        mSharedPrefsHelper.setLoggedInMode(true);
    }

    public Boolean getLoggedInMode() {
        return mSharedPrefsHelper.getLoggedInMode();
    }
}
