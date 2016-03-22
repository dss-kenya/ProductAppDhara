package com.agro.star.dhara.productapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by Dhara Shah on 22-03-2016.
 */
public class ProductApp extends Application{
    private static ProductApp mApp;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mApp = this;
    }

    /**
     * Gets an instance of the app context
     * @return
     */
    public static ProductApp getAppContext(){
        /**
         * Checks if the instance of the app is null or not
         */
        if(mApp == null) {
            mApp = (ProductApp)mContext;
        }
        return mApp;
    }
}
