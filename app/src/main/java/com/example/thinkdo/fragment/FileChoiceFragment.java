package com.example.thinkdo.fragment;

import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thinkdo.compoentdemo.R;
import com.example.thinkdo.model.FileInfo;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Created by xh on 2018/3/5.
 */

public class FileChoiceFragment extends Fragment implements View.OnClickListener {
    ListView listView;
    TextView titleTextView;
    List<FileInfo> data = new ArrayList<>();
    MenuItem menuItem;
    Button btn;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_filechoice, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.listView);
        titleTextView = (TextView) view.findViewById(R.id.textView_title);
        btn = (Button) view.findViewById(R.id.button1);

        btn.setOnClickListener(this);

        if (ActivityCompat.checkSelfPermission(getActivity(), SD_READ_PERMISSIONS) == PackageManager.PERMISSION_GRANTED) {
            listViewInit();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), SD_READ_PERMISSIONS)) {
                Toast.makeText(getActivity(), R.string.sd_no_read_permission, Toast.LENGTH_SHORT).show();
            } else {
                if (Build.VERSION.SDK_INT >= 23) {
                    requestPermissions(new String[]{SD_READ_PERMISSIONS}, SD_READ_REQUEST);
                }
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.pick_file, menu);
        menuItem = menu.findItem(R.id.menu_pick);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_pick) {
            if (item.getTitle().toString().equals(getString(R.string.pick))) {
                for (FileInfo f: data){
                    f.setVisible(true);
                }
                btn.setVisibility(View.VISIBLE);
                updateBtnStatus();
                item.setTitle(getString(R.string.cancel));
            } else {
                for (FileInfo f: data){
                    f.setVisible(false);
                    f.setChecked(false);
                }
                btn.setVisibility(View.GONE);
                item.setTitle(getString(R.string.pick));
            }
            ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void listViewInit() {
        File sdFile = getSdDir();
        if (sdFile != null) {
            data = getFileInfoList(sdFile);
            titleTextView.setText(sdFile.getAbsolutePath());
            Madapter adapter = new Madapter(data);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    FileInfo info = data.get(position);

                    if (info.isVisible()) {
                        info.setChecked(!info.isChecked());
                        ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
                        updateBtnStatus();
                    } else {
                        if (info.isDir()) {
                            titleTextView.setText(info.getFilePath());
                            data.clear();
                            data.addAll(getFileInfoList(new File(info.getFilePath())));
                            ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
                        }
                    }
                }
            });

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    boolean change = false;
                    for (FileInfo info : data) {
                        if (!info.isVisible()) {
                            info.setVisible(true);
                            change = true;
                        }
                    }
                    if (change) {
                        ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
                        menuItem.setTitle(getString(R.string.cancel));
                        btn.setVisibility(View.VISIBLE);
                        updateBtnStatus();
                    }

                    return true;
                }
            });
        } else {
            titleTextView.setText(R.string.sd_error);
        }
    }


    File getSdDir() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory();
        } else {
            return null;
        }
    }

    private void updateBtnStatus(){
        int total = 0;
        for (FileInfo f: data){
            if (f.isChecked()){
                total += 1;
            }
        }

        if (total == 0){
            btn.setText(R.string.send);
            btn.setEnabled(false);
        }else{
            btn.setEnabled(true);
            btn.setText(String.format(Locale.getDefault(), "%s(%d)",getString(R.string.send), total));
        }
    }


    private final int SD_READ_REQUEST = 1;
    private final String SD_READ_PERMISSIONS = "android.permission.READ_EXTERNAL_STORAGE";

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SD_READ_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                listViewInit();
            }
        }
    }

    @Override
    public void onClick(View v) {

        Toast.makeText(getActivity(), "Click", Toast.LENGTH_SHORT).show();
        List<FileInfo> list = new ArrayList<>();

        for (FileInfo f: list){
            if (f.isChecked()){
                list.add(f);
            }
        }
        //transform list

    }

    private class Madapter extends BaseAdapter {

        private List<FileInfo> data;

        public Madapter(List<FileInfo> data) {
            this.data = data;
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
            Holder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_listview_file_choice, parent, false);
                holder = new Holder();

                holder.imageView1 = (ImageView) convertView.findViewById(R.id.imageView1);
                holder.imageView2 = (ImageView) convertView.findViewById(R.id.imageView2);
                holder.tv1 = (TextView) convertView.findViewById(R.id.textView1);
                holder.tv2 = (TextView) convertView.findViewById(R.id.textView2);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }


            FileInfo info = data.get(position);

            if (info.isVisible()) {
                holder.imageView1.setVisibility(View.VISIBLE);
                holder.imageView1.setImageResource(info.isChecked() ? R.drawable.ic_checked : R.drawable.ic_unchecked);
            } else {
                holder.imageView1.setVisibility(View.GONE);
            }

            holder.imageView2.setImageResource(info.isDir() ? R.drawable.ic_dir : R.drawable.ic_file);
            holder.tv1.setText(info.getFileName());
            holder.tv2.setText(fileSizeFormat(info.getSize()));
            return convertView;
        }


        final long tb = 1024L * 1024 * 1024 * 1024;
        final long gb = 1024L * 1024 * 1024;
        final long mb = 1024 * 1024;
        final long kb = 1024;

        private String fileSizeFormat(long length) {

            DecimalFormat decimalFormat = new DecimalFormat(".0");
            if (length >= tb) {
                return decimalFormat.format((double) length / tb) + " TB";
            } else if (length >= gb) {
                return decimalFormat.format((double) length / gb) + " GB";
            } else if (length >= mb) {
                return decimalFormat.format((double) length / mb) + " MB";
            } else if (length >= kb) {
                return length / kb + " KB";
            } else {
                return length + " B";
            }
        }

        class Holder {
            ImageView imageView1, imageView2;
            TextView tv1, tv2;
        }
    }

    long getFileSize(File file) {
        if (file != null && file.exists()) {
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                if (children == null) return 0;
                long total = 0;
                for (File f : children) {
                    total += getFileSize(f);
                }
                return total;
            } else {
                return file.length();
            }
        }
        return 0;
    }

    List<FileInfo> getFileInfoList(File file) {
        ArrayList<FileInfo> list = new ArrayList<>();

        if (file != null && file.exists() && file.listFiles() != null) {
            for (File f : file.listFiles()) {
                list.add(new FileInfo(f.getAbsolutePath(), f.getName(), getFileSize(f), f.isDirectory()));
                Collections.sort(list);
            }
        }

        return list;
    }

    List<FileInfo> getAllSonFileFromDir(File file) {
        ArrayList<FileInfo> list = new ArrayList<>();

        if (file != null && file.exists() && file.isDirectory()) {
            File[] children = file.listFiles();
            if (children == null) return list;
            for (File f : children) {
                if (f.isFile()) {
                    list.add(new FileInfo(f.getAbsolutePath(), f.getName(), getFileSize(f), false));
                } else {
                    list.addAll(getAllSonFileFromDir(f));
                }
            }
        }

        return list;
    }

}



