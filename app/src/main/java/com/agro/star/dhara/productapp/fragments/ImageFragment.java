package com.agro.star.dhara.productapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.agro.star.dhara.productapp.R;
import com.agro.star.dhara.productapp.utils.Global;
import com.agro.star.dhara.productapp.utils.ImageUtilities;

/**
 * Created by USER on 23-03-2016.
 */
public class ImageFragment extends Fragment {
    private static ImageFragment mFragment;
    private ImageView mImgProduct;
    private String mImageName;

    public static ImageFragment newInstance(String imageName){
        mFragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putString(Global.INTENT_IMAGE_NAME, imageName);
        mFragment.setArguments(args);
        return mFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            mImageName = getArguments().getString(Global.INTENT_IMAGE_NAME);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_featured_image, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mImgProduct = (ImageView)view.findViewById(R.id.imgProduct);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mImgProduct.setImageDrawable(ImageUtilities.getDrawable(mImageName));
    }
}
