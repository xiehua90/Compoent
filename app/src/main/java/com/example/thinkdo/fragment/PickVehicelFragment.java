package com.example.thinkdo.fragment;


import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.quicksidebar.QuickSideBarTipsView;
import com.bigkoo.quicksidebar.QuickSideBarView;
import com.bigkoo.quicksidebar.listener.OnQuickSideBarTouchListener;
import com.example.thinkdo.compoentdemo.R;
import com.example.thinkdo.model.VehicleBean;

import java.util.ArrayList;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by xh on 2018/4/6.
 */

public class PickVehicelFragment extends BaseFragment implements OnQuickSideBarTouchListener {
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.quickSideBarTipsView)
    QuickSideBarTipsView tipsView;
    @BindView(R.id.quickSideBarView)
    QuickSideBarView sideBarView;

    final int spanCount = 4;

    ArrayList<VehicleBean> vehicles = new ArrayList<>();
    ArrayList<String> region = new ArrayList<>();
    ArrayList data = new ArrayList();

    @Override
    public int getLayoutId() {
        return R.layout.frag_pick_vhicle;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, region);
        spinner.setAdapter(spinnerAdapter);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), region.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), spanCount, GridLayoutManager.VERTICAL, false) {

            @Override
            public View onInterceptFocusSearch(View focused, int direction) {
                int pos = getPosition(focused);
                if (pos == 1 && direction == View.FOCUS_UP) {

                    return spinner;
                }


                return super.onInterceptFocusSearch(focused, direction);
            }
        };


        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (data.get(position) instanceof String) {
                    return spanCount;
                } else if (data.get(position) instanceof VehicleBean) {
                    return 1;
                }
                return 0;
            }
        });


        recyclerView.setLayoutManager(gridLayoutManager);

        MyAdapter adapter = new MyAdapter(data);
        recyclerView.setAdapter(adapter);

        ArrayList<String> letter = new ArrayList<>();
        letter.add("\uD83D\uDD0D\n");
        for (int i = 68; i < 79; i++) {
            letter.add(String.valueOf((char) i));
        }

        sideBarView.setLetters(letter);
        sideBarView.setOnQuickSideBarTouchListener(this);

    }


    private void initData() {
        region.add("国产车");
        region.add("进口车");

        VehicleBean vehicleBean = new VehicleBean("阿", 01, R.drawable.vehicle50033);
        vehicles.add(vehicleBean);
        vehicleBean = new VehicleBean("阿", 01, R.drawable.vehicle50033);
        vehicles.add(vehicleBean);
        vehicleBean = new VehicleBean("阿", 01, R.drawable.vehicle50033);
        vehicles.add(vehicleBean);
        vehicleBean = new VehicleBean("宾利", 2, R.drawable.vehicle50033);
        vehicles.add(vehicleBean);
        vehicleBean = new VehicleBean("奔驰", 4, R.drawable.vehicle50033);
        vehicles.add(vehicleBean);
        vehicleBean = new VehicleBean("宝马", 5, R.drawable.vehicle50033);
        vehicles.add(vehicleBean);
        vehicleBean = new VehicleBean("宾利1", 2, R.drawable.vehicle50033);
        vehicles.add(vehicleBean);
        vehicleBean = new VehicleBean("奔驰1", 4, R.drawable.vehicle50033);
        vehicles.add(vehicleBean);
        vehicleBean = new VehicleBean("宝马1", 5, R.drawable.vehicle50033);
        vehicles.add(vehicleBean);
        vehicleBean = new VehicleBean("宾利2", 6, R.drawable.vehicle50033);
        vehicles.add(vehicleBean);
        vehicleBean = new VehicleBean("奔驰3", 7, R.drawable.vehicle50033);
        vehicles.add(vehicleBean);
        vehicleBean = new VehicleBean("宝马3", 9, R.drawable.vehicle50033);
        vehicles.add(vehicleBean);
        vehicleBean = new VehicleBean("东风", 10, R.drawable.vehicle50033);
        vehicles.add(vehicleBean);
        vehicleBean = new VehicleBean("福特", 11, R.drawable.vehicle50033);
        vehicles.add(vehicleBean);


        vehicleBean = new VehicleBean("zz", 23, getResources().getIdentifier("vehicle50033", "drawable", getActivity().getPackageName()));
        vehicles.add(vehicleBean);


        for (int i = 0; i < vehicles.size(); i++) {
            VehicleBean bean = vehicles.get(i);
            if (i == 0) data.add(bean.getFirstLetter());
            else if (!bean.getFirstLetter().equalsIgnoreCase(vehicles.get(i - 1).getFirstLetter())) {
                data.add(bean.getFirstLetter());
            }
            data.add(bean);
        }


    }

    @Override
    public void onLetterChanged(String letter, int position, float y) {
        tipsView.setText(letter, position, y);

        recyclerView.scrollToPosition(3);

    }

    @Override
    public void onLetterTouching(boolean touching) {
        tipsView.setVisibility(touching ? View.VISIBLE : View.INVISIBLE);

    }

    class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        static final int Type1 = 1;
        static final int Type2 = 2;

        ArrayList vehicles;

        MyAdapter(ArrayList vehicles) {
            this.vehicles = vehicles;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == Type1) {
                return new Holder1(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_text, parent, false));
            } else if (viewType == Type2) {
                return new Holder2(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_image, parent, false));
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof Holder1) {
                ((Holder1) holder).tv.setText((String) data.get(position));

            } else if (holder instanceof Holder2) {
                ((Holder2) holder).imageView.setBackgroundResource(((VehicleBean) data.get(position)).getImageId());
            }
        }

        @Override
        public int getItemCount() {
            return vehicles == null ? 0 : vehicles.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (data.get(position) instanceof String) {
                return Type1;
            } else if (data.get(position) instanceof VehicleBean) {
                return Type2;
            }

            return -1;
        }


    }

    class Holder1 extends RecyclerView.ViewHolder {
        @BindView(R.id.textView)
        TextView tv;

        public Holder1(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.textView);
        }
    }

    class Holder2 extends RecyclerView.ViewHolder {
        ImageView imageView;

        public Holder2(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);

            imageView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        imageView.setImageResource(R.drawable.shape_recycler_image);
                    } else {
                        imageView.setImageDrawable(null);

                    }
                }
            });
        }

    }
}
