package com.example.thinkdo.compoentdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private String[] fragmentClassName = {
            "RecyclerViewFragment", "RecyclerViewFragment2", "PullToRefreshFragment", "WindowParamsFragment",
            "SizeFragment", "ToastFragment", "ListViewFragment", "ScrollFragment", "VolleyFragment", "LocationFragment",
            "ImageComFragment", "UILFragement", "CheckBoxFragment", "ItemClickFragment", "FrescoFragment", "GlideFragment",
            "NetFragment", "FileChoiceFragment", "AdjustNumBtnFragment", "ParcelableFragment", "JsFragment", "WebLoadFragment",
            "DialogFragment", "PickVehicelFragment", "AutoFitFragment", "CollapseTextFragment", "WeChatPayFragment",
            "ExpandListFragment","QRCodeFragment","ConstraintFragment","RemoteServiceFragment","GestureFragment",
            "FuncPreviewFragment","BaseUIFragment"
    };

    private final String packageName = "com.example.thinkdo.fragment";
    public static final String extra_className = "className";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] data = new String[fragmentClassName.length];
        for (int i = 0; i < fragmentClassName.length; i++) {
            data[i] = fragmentClassName[fragmentClassName.length - i - 1];
        }

        listView = (ListView) findViewById(R.id.listView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, data));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                String className = packageName + "." + fragmentClassName[fragmentClassName.length - i - 1];
                Intent intent = new Intent(MainActivity.this, TransactionsActivity.class);
                intent.putExtra(extra_className, className);
                startActivity(intent);


//                int[] loc = new int[2];
//                view.getLocationInWindow(loc);
//                Log.d("TAG", "pos=" + i + " width=" + loc[0] + " height=" + loc[1]);
//
//                int pos = i - listView.getFirstVisiblePosition();
//                View item = listView.getChildAt(pos);
//                item.getLocationInWindow(loc);
//                Log.d("TAG", "pos=" + pos + " width=" + loc[0] + " height=" + loc[1]);


            }
        });


    }

    private void reversal() {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_map:
                startActivity(new Intent(MainActivity.this, MapActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
