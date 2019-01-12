package com.example.thinkdo.compoentdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.thinkdo.model.Task;
import com.example.thinkdo.model.UploadPresenter;
import com.example.thinkdo.model.UploadTask;
import com.example.thinkdo.model.UploadView;

import java.util.List;
import java.util.Locale;

/**
 * Created by xh on 2018/3/15.
 */

public class UploadActivity extends Activity implements UploadView<UploadTask> {
    private ListView listView;
    private XhAdapter<UploadTask> listAdapter;
    private UploadPresenter presenter = new UploadPresenter(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listView = new ListView(this);
        setContentView(listView);

        initViews();
    }

    void initViews() {
        presenter.init();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.upload_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_task:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void initListView(List<UploadTask> list) {
        listAdapter = new XhAdapter<>(list);
        listView.setAdapter(listAdapter);
    }

    @Override
    public void updateListView(List<UploadTask> list) {
        listAdapter.updateData(list);
    }

    private class XhAdapter<T extends Task> extends BaseAdapter {
        List<T> data;

        XhAdapter(List<T> list) {
            this.data = list;
        }

        void updateData(List<T> list) {
            this.data = list;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return data == null ? 0 : data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder<T> holder;

            if (convertView == null) {
                convertView = LayoutInflater.from(UploadActivity.this).inflate(0, parent, false);
                holder = new ViewHolder<>(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder<T>) convertView.getTag();
            }
            holder.setData(data.get(position));
            return convertView;
        }

        class ViewHolder<F extends Task> {
            private TextView tvName, tVstatus, tvCancel;

            ViewHolder(View view) {
                tvName = (TextView) view.findViewById(R.id.tv_name);
                tVstatus = (TextView) view.findViewById(R.id.tv_status);
                tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
            }

            public void setData(F data) {
                tvName.setText(data.getName());
                tvCancel.setText(R.string.cancel);


                switch (data.getStatus()) {
                    case Task.Status_init:
                        tVstatus.setText("等待中...");
                        tVstatus.setCompoundDrawables(ContextCompat.getDrawable(UploadActivity.this, R.drawable.ic_dir), null, null, null);
                        tvCancel.setVisibility(View.VISIBLE);
                        break;
                    case Task.Status_progress:
                        tVstatus.setText(String.format(Locale.getDefault(),"%.1f", (double)data.getProgressLength()/data.getLength()));
                        tVstatus.setCompoundDrawables(ContextCompat.getDrawable(UploadActivity.this, R.drawable.ic_dir), null, null, null);
                        tvCancel.setVisibility(View.VISIBLE);
                        break;
                    case Task.Status_finish:
                        tvCancel.setVisibility(View.INVISIBLE);
                        break;
                    default:
                        tvCancel.setVisibility(View.INVISIBLE);
                }
            }

        }
    }
}
