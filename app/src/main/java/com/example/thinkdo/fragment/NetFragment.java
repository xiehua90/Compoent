package com.example.thinkdo.fragment;

import android.app.Fragment;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Locale;

/**
 * Created by Administrator on 2016/7/26.
 */
public class NetFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView t = new TextView(getActivity());
        WifiManager wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);

//        String log = String.format(Locale.CHINA, "%s, %s, %s, %s,  %s, %s, %s, %s, %s, %s, %d, %d, %d",
//                telephonyManager.getDeviceId(),
//                telephonyManager.getSubscriberId(),
//                android.os.Build.SERIAL, android.os.Build.BRAND,
//                android.os.Build.MODEL,
//                telephonyManager.getSimSerialNumber(),
//                wifiInfo.getMacAddress(),
//                telephonyManager.getSimOperatorName(), wifiInfo.getSSID(),
//                Build.VERSION.RELEASE,
//                Build.VERSION.SDK_INT,
//                getResources().getDisplayMetrics().heightPixels,
//                getResources().getDisplayMetrics().widthPixels);
//        t.setText(log + " " + getLocalHostIp());

        String version = String.format(Locale.CHINA,
                "  RELEASE= %s, SDK_INT= %d, brand= %s, model= %s,INCREMENTAL= %s",
                Build.VERSION.RELEASE,
                Build.VERSION.SDK_INT,
                android.os.Build.BRAND,
                android.os.Build.MODEL,
                Build.VERSION.INCREMENTAL
        );


        t.append(version);
        return t;
    }


    public String getLocalHostIp() {
        String ipaddress = "";
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                NetworkInterface nif = en.nextElement();
                Enumeration<InetAddress> inet = nif.getInetAddresses();
                while (inet.hasMoreElements()) {
                    InetAddress ip = inet.nextElement();
                    if (!ip.isLoopbackAddress() && ip instanceof Inet4Address) {
                        return "本机的ip是: " + ip.getHostAddress();
                    }
                }

            }
        } catch (SocketException e) {
            Log.e("TAG", "获取本地ip地址失败");
            e.printStackTrace();
        }
        return ipaddress;

    }




}
