package com.example.thinkdo.fragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.thinkdo.compoentdemo.R;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Administrator on 2016/6/13.
 */
public class VolleyFragment extends Fragment implements View.OnClickListener {
    private RequestQueue mQueue;
    private TextView tv;
    private ImageView iv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQueue = Volley.newRequestQueue(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_volley, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        Button btn = (Button) view.findViewById(R.id.btn1);
        btn.setOnClickListener(this);
        btn = (Button) view.findViewById(R.id.btn2);
        btn.setOnClickListener(this);
        btn = (Button) view.findViewById(R.id.btn3);
        btn.setOnClickListener(this);
        btn = (Button) view.findViewById(R.id.btn4);
        btn.setOnClickListener(this);
        iv = (ImageView) view.findViewById(R.id.imageView);
        tv = (TextView) view.findViewById(R.id.textView);
        tv.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                StringRequest stringRequest = new StringRequest("http://www.baidu.com", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        tv.setText(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tv.setText(error.toString());
                    }
                }) {
                    //
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        return super.getParams();
                    }
                };

                mQueue.add(stringRequest);
                break;
            case R.id.btn2:
                JsonObjectRequest request = new JsonObjectRequest("http://www.weather.com.cn/data/sk/101010100.html", null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        tv.setText(response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tv.setText(error.toString());
                    }
                });

                mQueue.add(request);
                break;

            case R.id.btn3:
                ImageRequest imageRequest = new ImageRequest("http://a.hiphotos.baidu.com/image/pic/item/8b82b9014a90f603e301db003a12b31bb151edc0.jpg",
                        new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap response) {
                                iv.setImageBitmap(response);
                            }
                        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tv.setText(error.toString());
                    }
                });

                mQueue.add(imageRequest);
                break;
            case R.id.btn4:
                ImageLoader imageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
                    @Override
                    public Bitmap getBitmap(String url) {
                        return null;
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {

                    }
                });
                ImageLoader.ImageListener listener = ImageLoader.getImageListener(iv, R.drawable.ic_mark, R.drawable.ic_mark);
                imageLoader.get("http://i0.sinaimg.cn/ent/v/m/2008-12-25/U2184P28T3D2313854F326DT20081225164931.jpg", listener);
                break;
        }
    }
}
