
package in.kestone.sap.holder;

import com.google.gson.annotations.SerializedName;

public class Help {

    @SerializedName("Email")
    private String mEmail;
    @SerializedName("Name")
    private String mName;
    @SerializedName("PhoneNumber")
    private String mPhoneNumber;

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

}
