package org.izv.aad.appwebview;

import android.content.Context;
import android.webkit.JavascriptInterface;

public class WebAppInterface {
    String data, data2;
    boolean data3;

    WebAppInterface(){
    }

    //public Context getmContext() {
        //return mContext;
    //}

    public String getData() {
        return data;
    }

    public String getData2() {
        return data2;
    }

    public boolean getData3() {
        return data3;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }

    public void setData3(boolean data3) {
        this.data3 = data3;
    }

    @JavascriptInterface
    public void sendData(String data, String data2, boolean data3) {
        //Get the string value to process
        setData(data);
        setData2(data2);
        setData3(data3);
    }
}
