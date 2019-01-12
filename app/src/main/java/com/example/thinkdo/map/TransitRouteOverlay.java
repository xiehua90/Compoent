package com.example.thinkdo.map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.amap.api.col.cr;
import com.amap.api.col.w;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusStep;
import com.amap.api.services.route.RouteBusLineItem;
import com.amap.api.services.route.WalkStep;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/23.
 */
public class TransitRouteOverlay extends OverlayManager {
    private BusPath busPath;

    public TransitRouteOverlay(AMap aMap, BusPath busPath, LatLonPoint start, LatLonPoint destination) {
        super(aMap, AMapUtil.convertToLatLng(start), AMapUtil.convertToLatLng(destination));
        this.busPath = busPath;
    }

    @Override
    List<PolylineOptions> getPolyLineOptions() {
        if (busPath == null) return null;

        List<PolylineOptions> overLayList = new ArrayList<>();
        if (busPath.getSteps() != null && busPath.getSteps().size() > 0) {
            LatLng lastPoint = null;
            List<LatLng> points = new ArrayList<>();
            for (BusStep step : busPath.getSteps()) {
                //draw walk line
                if (step.getWalk() != null && step.getWalk().getSteps().size() > 0) {
                    for (WalkStep walkStep : step.getWalk().getSteps()) {
                        if (walkStep.getPolyline() != null && walkStep.getPolyline().size() > 0)
                            points.addAll(AMapUtil.convertToLatLngList(walkStep.getPolyline()));
                    }

                    if (points.size() > 0) {
                        markerOptionses.add(new MarkerOptions().position(points.get(0)).anchor(0.5f, 0.5f).icon(BitmapDescriptorFactory.fromAsset("Icon_walk_route.png")));

                        if (lastPoint != null) {
                            overLayList.add(new PolylineOptions()
                                    .add(lastPoint, points.get(0))
                                    .width(getLineWidth())
                                    .zIndex(1)
                                    .color(getWalkLineColor()));
                        }

                        overLayList.add(new PolylineOptions()
                                .addAll(points)
                                .width(getLineWidth())
                                .zIndex(1)
                                .color(getWalkLineColor()));

                        lastPoint = points.get(points.size() - 1);
                        points.clear();
                    }
                }

                //draw bus line
                if (step.getBusLines() != null && step.getBusLines().size() > 0) {
                    RouteBusLineItem busItem = step.getBusLines().get(0);

                    if (busItem.getPolyline().size() > 0) {
                        points.addAll(AMapUtil.convertToLatLngList(busItem.getPolyline()));

                        markerOptionses.add(new MarkerOptions().position(points.get(0)).anchor(0.5f, 0.5f).icon(BitmapDescriptorFactory.fromAsset("Icon_bus_station.png")));
                        if (lastPoint != null) {
                            overLayList.add(new PolylineOptions()
                                    .add(lastPoint, points.get(0))
                                    .width(getLineWidth())
                                    .zIndex(10)
                                    .color(getWalkLineColor()));

                        }

                        overLayList.add(new PolylineOptions()
                                .addAll(points)
                                .width(getLineWidth())
                                .zIndex(1)
                                .color(getLineColor()));
                        lastPoint = points.get(points.size() - 1);
                        points.clear();
                    }
                }
            }
        }

        return overLayList;
    }

    @Override
    float getLineWidth() {
        return 8f;
    }

    int getLineColor() {
        return Color.parseColor("#428aef");
    }


    int getWalkLineColor() {
        return Color.parseColor("#31ba47");
    }


}
