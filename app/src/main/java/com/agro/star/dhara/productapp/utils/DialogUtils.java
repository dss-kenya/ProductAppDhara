package com.agro.star.dhara.productapp.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.agro.star.dhara.productapp.ProductApp;
import com.agro.star.dhara.productapp.R;
import com.agro.star.dhara.productapp.adapters.LanguageAdapter;
import com.agro.star.dhara.productapp.db.DBHelper;
import com.agro.star.dhara.productapp.interfaces.ILanguageSelectionListener;
import com.agro.star.dhara.productapp.models.Language;
import com.agro.star.dhara.productapp.utils.prefs.SharedPrefs;

import java.util.List;

/**
 * Created by Dhara Shah on 23-03-2016.
 */
public class DialogUtils {
    /**
     * Displays the language box
     * @param context
     * @param listener
     */
    public static void showLanguageBox(Context context,final ILanguageSelectionListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(null);
        builder.setView(R.layout.layout_language);

        final AlertDialog dialog = builder.create();
        dialog.show();

        String langSelected = SharedPrefs.getString(SharedPrefs.LANGUAGE_SELECTED,
                ProductApp.getAppContext().getString(R.string.english_code));
        final List<Language> languageList = DBHelper.getInstance(ProductApp.getAppContext())
                .getAllLanguages(langSelected);

        int langSelectionVal = -1;
        if(languageList != null) {
            for(int i=0;i<languageList.size();i++) {
                if(languageList.get(i).getLanguageCode().equalsIgnoreCase(langSelected)) {
                    langSelectionVal = i;
                    break;
                }
            }
        }

        /**
         * Sets the adapter and the views
         */
        final LanguageAdapter languageAdapter = new LanguageAdapter(ProductApp.getAppContext(),
                R.layout.individual_language_row, languageList);

        ListView lstLangaugeView = (ListView)dialog.findViewById(android.R.id.list);
        Button btnCancel = (Button)dialog.findViewById(R.id.btnCancel);
        Button btnOK = (Button)dialog.findViewById(R.id.btnOK);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) {
                    if(languageList.size() > 0) {
                        listener.onLanguageSelected(languageList.get(languageAdapter.getSelectedIndex()).getLanguageCode());
                    }
                }
                dialog.dismiss();
            }
        });

        lstLangaugeView.setAdapter(languageAdapter);
        lstLangaugeView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lstLangaugeView.setSelection(langSelectionVal);
        lstLangaugeView.setSelected(true);
        languageAdapter.setSelectedIndex(langSelectionVal);
        languageAdapter.notifyDataSetChanged();
    }
}
