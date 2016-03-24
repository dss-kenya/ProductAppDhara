package com.agro.star.dhara.productapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.agro.star.dhara.productapp.fragments.ImageFragment;

import java.util.List;

/**
 * Created by Dhara Shah on 23-03-2016.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {
    private List<String> mFeaturedImages;

    public MyPagerAdapter(FragmentManager fm, List<String> featuredImages) {
        super(fm);
        mFeaturedImages = featuredImages;
    }

    @Override
    public Fragment getItem(int i) {
        /**
         * Loads the image fragment with the image to be displayed
         */
        return ImageFragment.newInstance(mFeaturedImages.get(i));
    }

    @Override
    public int getCount() {
        return mFeaturedImages.size();
    }
}
