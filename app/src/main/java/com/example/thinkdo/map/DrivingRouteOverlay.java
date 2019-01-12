package com.example.thinkdo.map;

import android.graphics.Color;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveStep;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/23.
 */
public class DrivingRouteOverlay extends OverlayManager {
    private DrivePath drivePath;

    public DrivingRouteOverlay(AMap aMap, DrivePath DrivePath, LatLonPoint start, LatLonPoint destination) {
        super(aMap, AMapUtil.convertToLatLng(start), AMapUtil.convertToLatLng(destination));
        this.drivePath = DrivePath;
    }

    @Override
    List<PolylineOptions> getPolyLineOptions() {
        if (drivePath == null) return null;

        List<PolylineOptions> overLayList = new ArrayList<>();
        List<LatLng> points = new ArrayList<>();

        if (drivePath.getSteps() != null && drivePath.getSteps().size() > 0) {
            for (DriveStep step : drivePath.getSteps()) {
                List<LatLng> p = AMapUtil.convertToLatLngList(step.getPolyline());
                if (p != null) {
                    points.addAll(p);
                }
            }
        }

        overLayList.add(new PolylineOptions()
                .addAll(points)
                .width(getLineWidth())
                .zIndex(1)
                .color(getLineColor()));

        return overLayList;

    }

    @Override
    float getLineWidth() {
        return 8f;
    }

    @Override
    int getLineColor() {
        return Color.parseColor("#428aef");
    }
}
