package com.example.thinkdo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import com.example.thinkdo.compoentdemo.R;

/**
 * Created by xh on 2018/4/1.
 */

public class JsFragment extends Fragment {

    WebView webView;
    EditText et;
    Button btn;
    final String URL = "file:///android_asset/webview.html";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_js, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        et = (EditText) view.findViewById(R.id.input_et);
        btn = (Button) view.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log("btn.onClick");
                webView.loadUrl("javascript:btnAction()");

            }
        });
        webView = (WebView) view.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(URL);
        //在js中调用本地java方法
        webView.addJavascriptInterface(new JSHook(), "hello");

//        //添加客户端支持
        webView.setWebChromeClient(new WebChromeClient());

    }


    public class JSHook {

        @JavascriptInterface
        public void javaMethod(String p) {
            log("JSHook.JavaMethod() called! + " + p);
        }

        @JavascriptInterface
        public void showAndroid() {
//            log("showAndroid");
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String info = "来自手机内的内容:" + et.getText().toString();
                    webView.loadUrl("javascript:show('" + info + "')");
                }
            });

        }

        @JavascriptInterface
        public void getInfo(final String str) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    et.setText("来自webview的内容: "+str);
                }
            });
        }
    }

    public void log(String str) {
        Log.d("TAG", str);
    }

}
