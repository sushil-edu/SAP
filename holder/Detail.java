package in.kestone.sap.holder;

import java.util.ArrayList;

public class Detail {
     ArrayList<Scan> scanArrayList = new ArrayList<>(  );

    public ArrayList<Scan> getScanArrayList() {
        return scanArrayList;
    }

    public void setScanArrayList(ArrayList<Scan> scanArrayList) {
        this.scanArrayList = scanArrayList;
    }
}
