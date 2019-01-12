package com.example.thinkdo.compoentdemo;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.Locale;

/**
 * Created by Administrator on 2016/4/22.
 */
public class TransactionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame);

        String name = getIntent().getStringExtra(MainActivity.extra_className);

        try {
            Object fragment = Class.forName(name).newInstance();

            if (fragment instanceof Fragment) {
                getFragmentManager().beginTransaction().replace(R.id.frameLayout, (Fragment) fragment).commit();
            } else if (fragment instanceof android.support.v4.app.Fragment) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,
                        (android.support.v4.app.Fragment) fragment).commit();
            }


        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }


    private void test(Context context, String language) {
        Configuration config = context.getResources().getConfiguration();
        Locale sysLocale = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sysLocale = config.getLocales().get(0);
        } else {
            sysLocale = config.locale;
        }


        if (!language.equals("") && !sysLocale.getLanguage().equals(language)) {

            Locale locale = new Locale(language);
            Locale.setDefault(locale);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                config.setLocale(locale);
            } else {
                config.locale = locale;
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                context = context.createConfigurationContext(config);
            } else {
            }
            context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        }
    }

}
