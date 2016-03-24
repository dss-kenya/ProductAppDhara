package com.agro.star.dhara.productapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;

import com.agro.star.dhara.productapp.R;
import com.agro.star.dhara.productapp.models.Language;

import java.util.List;

/**
 * Created by Dhara Shah on 23-03-2016.
 */
public class LanguageAdapter extends ArrayAdapter<Language>{
    private int RESOURCE;
    private List<Language> mLanguageList;
    private static int selectedIndex = 0;

    public LanguageAdapter(Context context, int resource, List<Language> objects) {
        super(context, resource, objects);
        RESOURCE = resource;
        mLanguageList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder vh = null;

        if(view == null) {
            view= LayoutInflater.from(parent.getContext()).inflate(RESOURCE, parent, false);
            vh = new ViewHolder();
            vh.chkLanguage = (CheckBox)view.findViewById(R.id.chkTxtLanguage);
            view.setTag(vh);
        }else {
            vh = (ViewHolder)view.getTag();
        }

        vh.chkLanguage.setText(mLanguageList.get(position).getLanguageName());
        vh.chkLanguage.setTag(String.valueOf(position));

        vh.chkLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                int position = Integer.parseInt((String) checkBox.getTag());
                selectedIndex = position;
                notifyDataSetChanged();
            }
        });

        if(selectedIndex == position) {
            vh.chkLanguage.setChecked(true);
        }else {
            vh.chkLanguage.setChecked(false);
        }

        return view;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    class ViewHolder {
        CheckBox chkLanguage;
    }
}
