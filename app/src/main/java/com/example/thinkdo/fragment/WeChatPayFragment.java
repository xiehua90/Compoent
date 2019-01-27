package com.example.thinkdo.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.thinkdo.compoentdemo.R;
import com.example.thinkdo.utils.Util;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.OnClick;

/**
 * Created by xh on 2018/4/25.
 */

public class WeChatPayFragment extends BaseFragment {
    private IWXAPI api;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(getActivity(), null);
//        api.registerApp("wxb4ba3c02aa476ea1");
    }

    @Override
    public int getLayoutId() {
        return R.layout.frag_we_chat_pay;
    }

    @OnClick({R.id.btn})
    public void onClick(View view) {
        final String url = "http://wxpay.wxutil.com/pub_v2/app/app_pay.php";


        new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] buf = Util.httpGet(url);
                if (buf != null && buf.length > 0) {
                    String content = new String(buf);
                    Log.e("get server pay params:", content);
                    JSONObject json = null;
                    try {
                        json = new JSONObject(content);
                        if (null != json && !json.has("retcode")) {
                            PayReq req = new PayReq();
                            //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
                            req.appId			= "6";
                            req.partnerId		= "ads";
                            req.prepayId		= "hello";
                            req.nonceStr		= "hello";
                            req.timeStamp		= "hello";
                            req.packageValue	= "hello";
                            req.sign			= "hello";
                            req.extData = "app data"; // optional
                            api.sendReq(req);

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "正常调起支付", Toast.LENGTH_SHORT).show();
                                }
                            });
                            // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                        } else {
                            Log.d("PAY_GET", "返回错误" + json.getString("retmsg"));
                            final String msg = json.getString("retmsg");
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "返回错误" + msg, Toast.LENGTH_SHORT).show();

                                }
                            });

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }


}
