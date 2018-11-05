
package in.kestone.sap.utils;

import com.google.gson.annotations.SerializedName;

public class LoginResult {

    @SerializedName("Designation")
    private String mDesignation;
    @SerializedName("EmailID")
    private String mEmailID;
    @SerializedName("ID")
    private String mID;
    @SerializedName("Mobile")
    private String mMobile;
    @SerializedName("Name")
    private String mName;
    @SerializedName("Organization")
    private String mOrganization;
    @SerializedName("Password")
    private String mPassword;

    public String getDesignation() {
        return mDesignation;
    }

    public void setDesignation(String designation) {
        mDesignation = designation;
    }

    public String getEmailID() {
        return mEmailID;
    }

    public void setEmailID(String emailID) {
        mEmailID = emailID;
    }

    public String getID() {
        return mID;
    }

    public void setID(String iD) {
        mID = iD;
    }

    public String getMobile() {
        return mMobile;
    }

    public void setMobile(String mobile) {
        mMobile = mobile;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getOrganization() {
        return mOrganization;
    }

    public void setOrganization(String organization) {
        mOrganization = organization;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

}
