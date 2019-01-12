package com.example.thinkdo.compoentdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/16.
 */
public class FragmentTestActivity extends AppCompatActivity {
    boolean flag = true;
    private int curPage = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_test);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fragmentTransA(curPage);
        log("onCreate");
    }

    public void mClick(View view) {

        int pos = Integer.parseInt(view.getTag().toString());
        curPage = pos;
        if (flag) {
            fragmentTransA(pos);
            getSupportActionBar().setTitle("Add");
        } else {
            fragmentTransR(pos);
            getSupportActionBar().setTitle("Replace");
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        log("onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        log("onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        log("onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        log("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        log("onDestroy");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            flag = !flag;
            return true;
        }

        if (R.id.go == item.getItemId()) {
            startActivity(new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fragmentTransR(int pos) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, PlaceFragment.newInstance(pos));
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void fragmentTransA(int pos) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        Fragment fragment;

        for (int i = 0; i < 3; ++i) {

            fragment = manager.findFragmentByTag(String.valueOf(i));

            if (i == pos) {
                if (null == fragment) {
                    fragment = PlaceFragment.newInstance(pos);
                    transaction.add(R.id.frameLayout, fragment, String.valueOf(pos));
                }

                transaction.show(fragment);
                continue;
            }
            if (null != fragment) transaction.hide(fragment);
        }

        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        transaction.commit();
    }

    public static class PlaceFragment extends Fragment {
        static final String ARG_INT = "ARG_INT";
        ArrayList<String> list;

        public static PlaceFragment newInstance(int index) {

            Bundle args = new Bundle();
            args.putInt(ARG_INT, index);
            PlaceFragment fragment = new PlaceFragment();
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceFragment() {

        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            log("onCreateView");
            int start = 5 * getArguments().getInt(ARG_INT);

            ListView lv = new ListView(getActivity());

            if (null == list) {
                list = new ArrayList<>();
                for (int i = start; i < start + 20; ++i) {
                    list.add(String.valueOf(i));
                }

            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, list);
            lv.setAdapter(adapter);
            return lv;
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            log("onAttach");
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            log("onCreate");
        }


        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            log("onActivityCreated");
        }

        @Override
        public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
            super.onViewStateRestored(savedInstanceState);
            log("onViewStateRestored");
        }

        @Override
        public void onStart() {
            super.onStart();
            log("onStart");
        }

        @Override
        public void onResume() {
            super.onResume();
            log("onResume");
        }

        @Override
        public void onPause() {
            super.onPause();
            log("onPause");
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            log("onSaveInstanceState");
        }

        @Override
        public void onStop() {
            super.onStop();
            log("onStop");
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            log("onDestroyView");
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            log("onDestroy");
        }

        @Override
        public void onDetach() {
            super.onDetach();
            log("onDetach");
        }

        private void log(String msg) {
            Log.d(String.valueOf(getArguments().getInt(ARG_INT)) + "--->>> TAG", msg);
        }
    }

    private void log(String msg) {
        Log.d("Activity" + "--->>> TAG", msg);
    }
}
