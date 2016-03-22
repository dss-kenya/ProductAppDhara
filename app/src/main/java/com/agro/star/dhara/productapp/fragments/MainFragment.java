package com.agro.star.dhara.productapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.agro.star.dhara.productapp.R;

/**
 * Created by Dhara Shah on 22-03-2016.
 */
public class MainFragment extends Fragment {
    private static MainFragment mFragment;
    private ListView mListViewProducts;

    public static MainFragment newInstance(){
        mFragment = new MainFragment();
        return mFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /**
         * initialize all the views here using view
         */
        mListViewProducts = (ListView)view.findViewById(android.R.id.list);
    }
}
