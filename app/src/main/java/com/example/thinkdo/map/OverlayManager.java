package com.example.thinkdo.map;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.Polyline;
import com.amap.api.maps2d.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/23.
 */
public abstract class OverlayManager {
    protected List<PolylineOptions> lineOptions;
    protected List<Marker> markers;
    protected List<MarkerOptions> markerOptionses;
    protected List<Polyline> polyLines;
    protected AMap aMap;
    protected LatLng start, destination;


    public OverlayManager(AMap aMap, LatLng start, LatLng destination) {
        this.aMap = aMap;
        this.start = start;
        this.destination = destination;

        if (lineOptions == null) lineOptions = new ArrayList<>();
        if (polyLines == null) polyLines = new ArrayList<>();
        if (markerOptionses == null) markerOptionses = new ArrayList<>();
        if (markers == null) markers = new ArrayList<>();
    }

    abstract List<PolylineOptions> getPolyLineOptions();

    abstract int getLineColor();

    abstract float getLineWidth();

    public List<MarkerOptions> getMarkerOptionses() {
        return null;
    }


    public void addToMap() {
        if (aMap == null) return;

        removeFromMap();
        List<PolylineOptions> array = getPolyLineOptions();
        if (array != null) lineOptions.addAll(array);
        for (PolylineOptions i : lineOptions) {
            polyLines.add(aMap.addPolyline(i));
        }

        List<MarkerOptions> markerOp = getMarkerOptionses();
        if (markerOp != null) markerOptionses.addAll(markerOp);
        for (MarkerOptions i : markerOptionses) {
            markers.add(aMap.addMarker(i));
        }

        addStartMark();
        addDestinationMark();
    }

    void removeFromMap() {
        if (aMap == null) return;

        for (Polyline polyline : polyLines) {
            polyline.remove();
        }

        for (Marker i : markers) {
            i.remove();
        }

        markers.clear();
        polyLines.clear();
        lineOptions.clear();
        markerOptionses.clear();
    }


    protected void addStartMark() {
        BitmapDescriptor b = getStartMarkerIcon();
        if (b != null) {
            markers.add(aMap.addMarker(new MarkerOptions()
                    .anchor(0.5f, 0.5f)
                    .icon(b)
                    .position(start)
                    .title("起点")));
        }
    }

    protected void addDestinationMark() {
        BitmapDescriptor b = getDestinationMarkerIcon();
        if (b != null) {
            markers.add(aMap.addMarker(new MarkerOptions()
                    .anchor(0.5f, 0.5f)
                    .icon(b)
                    .position(destination)
                    .title("终点")));
        }
    }

    public BitmapDescriptor getStartMarkerIcon() {
        return null;
    }

    public BitmapDescriptor getDestinationMarkerIcon() {
        return null;
    }

    public void zoomToSpan() {
        if (aMap == null) return;

        LatLngBounds latLngBounds = this.getLatLngBounds();
        if (latLngBounds == null) return;

        aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 50));
    }


    public LatLngBounds getLatLngBounds() {
        if (start == null || destination == null) return null;

        LatLngBounds.Builder builder = LatLngBounds.builder();
        builder.include(start);
        builder.include(destination);

        return builder.build();
    }
}
