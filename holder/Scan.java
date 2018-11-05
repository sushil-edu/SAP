
package in.kestone.sap.holder;


import com.google.gson.annotations.SerializedName;

public class Scan {

    @SerializedName("ID")
    private String mID;
    @SerializedName("RefLoginID")
    private String mRefLoginID;
    @SerializedName("RefLoginName")
    private String mRefLoginName;
    @SerializedName("ScanCode")
    private String mScanCode;
    @SerializedName("ScanON")
    private String mScanON;
    private int uploaded;

    public Scan( String mRefLoginID, String mScanCode, String mScanON, int uploaded) {
        this.mRefLoginID = mRefLoginID;
        this.mScanCode = mScanCode;
        this.mScanON = mScanON;
        this.uploaded = uploaded;
    }

    public Scan(String mScanCode) {
        this.mScanCode = mScanCode;
    }

    public Scan(String mScanCode, int uploaded) {
        this.mScanCode = mScanCode;
        this.uploaded = uploaded;
    }

    public String getID() {
        return mID;
    }

    public void setID(String iD) {
        mID = iD;
    }

    public String getRefLoginID() {
        return mRefLoginID;
    }

    public void setRefLoginID(String refLoginID) {
        mRefLoginID = refLoginID;
    }

    public String getRefLoginName() {
        return mRefLoginName;
    }

    public void setRefLoginName(String refLoginName) {
        mRefLoginName = refLoginName;
    }

    public String getScanCode() {
        return mScanCode;
    }

    public void setScanCode(String scanCode) {
        mScanCode = scanCode;
    }

    public String getScanON() {
        return mScanON;
    }

    public void setScanON(String scanON) {
        mScanON = scanON;
    }

    public int getUploaded() {
        return uploaded;
    }

    public void setUploaded(int uploaded) {
        this.uploaded = uploaded;
    }
}
