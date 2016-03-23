package com.brioal.wifipassword.base;

/**
 * Created by Brioal on 2016/3/21.
 */
public class WifiItem {
    private String mId ;
    private String mPass ;

    public WifiItem(String mId, String mPass) {
        this.mId = mId;
        this.mPass = mPass;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmPass() {
        return mPass;
    }

    public void setmPass(String mPass) {
        this.mPass = mPass;
    }
}
