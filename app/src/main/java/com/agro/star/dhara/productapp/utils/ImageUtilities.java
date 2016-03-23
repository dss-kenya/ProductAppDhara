package com.agro.star.dhara.productapp.utils;

import android.graphics.drawable.Drawable;

import com.agro.star.dhara.productapp.ProductApp;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Dhara Shah on 23-03-2016.
 */
public class ImageUtilities {
    /**
     * Returns the drawable with the specified image name
     * from the assets folder
     * @param imageName
     * @return
     */
    public static Drawable getDrawable(String imageName) {
        // load image
        try {
            // get input stream
            InputStream ims = ProductApp.getAppContext().getAssets().open("images/" + imageName);
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // return the drawable
            return d;
        }
        catch(IOException ex) {
            return null;
        }
    }
}
