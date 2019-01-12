package com.example.thinkdo.map;

import android.graphics.Color;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkStep;
import com.example.thinkdo.compoentdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/23.
 */
public class WalkingRouteOverlay extends OverlayManager {
    private WalkPath walkPath;

    public WalkingRouteOverlay(AMap aMap, WalkPath walkPath, LatLonPoint start, LatLonPoint destination) {
        super(aMap, AMapUtil.convertToLatLng(start), AMapUtil.convertToLatLng(destination));
        this.walkPath = walkPath;
    }

    @Override
    List<PolylineOptions> getPolyLineOptions() {
        if (walkPath == null) return null;

        List<PolylineOptions> overLayList = new ArrayList<>();
        List<LatLng> points = new ArrayList<>();
        if (walkPath.getSteps() != null && walkPath.getSteps().size() > 0) {
            for (WalkStep step : walkPath.getSteps()) {
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

    @Override
    public BitmapDescriptor getDestinationMarkerIcon() {
        return BitmapDescriptorFactory.fromResource(R.drawable.ic_mark);
    }

    @Override
    public BitmapDescriptor getStartMarkerIcon() {
        return BitmapDescriptorFactory.fromResource(R.drawable.ic_mark);
    }
}
