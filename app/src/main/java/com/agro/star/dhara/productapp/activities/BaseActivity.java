package com.agro.star.dhara.productapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.agro.star.dhara.productapp.utils.CustomExceptionHandler;

/**
 * Created by Dhara Shah on 22-03-2016.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * Add exception handling
         */
        if (!(Thread.getDefaultUncaughtExceptionHandler() instanceof CustomExceptionHandler)) {
            Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(this));
        }
    }
}
