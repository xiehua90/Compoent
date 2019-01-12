package com.example.thinkdo.map;

import com.amap.api.maps2d.model.LatLng;
import com.amap.api.services.core.LatLonPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/23.
 */
public class AMapUtil {
    /**
     * 把LatLonPoint对象转化为LatLon对象
     */
    public static LatLng convertToLatLng(LatLonPoint latLonPoint) {
        return new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());
    }


    public static List<LatLng> convertToLatLngList(List<LatLonPoint> list) {
        if (list == null) return null;

        List<LatLng> result = new ArrayList<>();
        for (LatLonPoint i : list) {
            result.add(convertToLatLng(i));
        }
        return result;
    }
}
