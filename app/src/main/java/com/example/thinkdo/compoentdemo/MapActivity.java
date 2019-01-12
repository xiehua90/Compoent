package com.example.thinkdo.compoentdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.example.thinkdo.map.DrivingRouteOverlay;
import com.example.thinkdo.map.TransitRouteOverlay;
import com.example.thinkdo.map.WalkingRouteOverlay;

/**
 * Created by Administrator on 2016/6/17.
 */
public class MapActivity extends AppCompatActivity implements LocationSource, AMapLocationListener, AMap.InfoWindowAdapter, AMap.OnMarkerClickListener, RouteSearch.OnRouteSearchListener, AMap.OnMapClickListener {
    MapView mMapView = null;
    AMap aMap = null;
    AMapLocationClient locationClient = null;
    AMapLocationClientOption locationOption = null;
    OnLocationChangedListener listener;
    RouteSearch mRouteSearch;

    LatLng destination = new LatLng(22.53694, 114.119767), nowLocation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initMap(savedInstanceState);
    }


    private void initMap(Bundle savedInstanceState) {
        mMapView = (MapView) findViewById(R.id.map);
        if (mMapView != null) {
            mMapView.onCreate(savedInstanceState);
            aMap = mMapView.getMap();

            //init location
            aMap.setLocationSource(this);
            aMap.getUiSettings().setMyLocationButtonEnabled(true);
            aMap.setMyLocationEnabled(true);
            aMap.setMyLocationStyle(new MyLocationStyle().anchor(0.5f, 0.5f)
                    .strokeWidth(2f)
                    .strokeColor(Color.WHITE)
                    .radiusFillColor(Color.BLUE)
                    .myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_mark)));

            aMap.setInfoWindowAdapter(this);

            locationOption = new AMapLocationClientOption();
            locationOption.setInterval(5000);
            locationOption.setOnceLocation(false);
            locationOption.setLocationCacheEnable(false);
            locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            locationClient = new AMapLocationClient(getApplicationContext());
            locationClient.setLocationListener(this);
            addMark();

            mRouteSearch = new RouteSearch(MapActivity.this);
            mRouteSearch.setRouteSearchListener(this);

            aMap.setOnMarkerClickListener(this);
            aMap.setOnMapClickListener(this);
        }
    }


    private void addMark() {
        Marker marker = aMap.addMarker(new MarkerOptions()
                .anchor(0.5f, 0.5f)
                .title("佳宁娜广场B座")
                .position(destination)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_mark))
                .draggable(false));

        location(destination, 15);
    }

    int i = 0;

    private void startRoute() {
        if (nowLocation != null) {

            LatLonPoint from = new LatLonPoint(nowLocation.latitude, nowLocation.longitude);
            LatLonPoint to = new LatLonPoint(destination.latitude, destination.longitude);

            RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(from, to);

            switch (i % 3) {
                case 0:
                    RouteSearch.BusRouteQuery bQuery = new RouteSearch.BusRouteQuery(fromAndTo,
                            RouteSearch.BusDefault,
                            "深圳",
                            0);
                    mRouteSearch.calculateBusRouteAsyn(bQuery);
                    break;
                case 1:
                    RouteSearch.DriveRouteQuery dQuery = new RouteSearch.DriveRouteQuery(fromAndTo,
                            RouteSearch.DrivingDefault,
                            null,
                            null,
                            "");
                    mRouteSearch.calculateDriveRouteAsyn(dQuery);
                    break;
                default:
                    RouteSearch.WalkRouteQuery wQuery = new RouteSearch.WalkRouteQuery(fromAndTo,
                            RouteSearch.WalkDefault);
                    mRouteSearch.calculateWalkRouteAsyn(wQuery);
                    break;

            }
            i++;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMapView != null) mMapView.onDestroy();
        if (locationClient != null) locationClient.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMapView != null) mMapView.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mMapView != null) mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMapView != null) mMapView.onSaveInstanceState(outState);
    }


    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        listener = onLocationChangedListener;
        if (locationClient != null && locationOption != null) {
            locationClient.setLocationOption(locationOption);
            locationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        if (locationClient != null) {
            locationClient.stopLocation();
        }
    }

    private void location(LatLng latLng, float zoom) {
        if (null != aMap) aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (locationClient != null) locationClient.stopLocation();
        nowLocation = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
//        location(nowLocation, 17);
//        aMapLocation.setAccuracy(0);
//        if (listener != null) listener.onLocationChanged(aMapLocation);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View view = LinearLayout.inflate(MapActivity.this, R.layout.view_showinfo, null);
        ((TextView) view.findViewById(R.id.textView)).setText(marker.getTitle());
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = LinearLayout.inflate(MapActivity.this, R.layout.view_showinfo, null);
        ((TextView) view.findViewById(R.id.textView)).setText(marker.getTitle());
        return view;
    }

    @Override
    public void onBusRouteSearched(BusRouteResult result, int i) {
        if (i == 1000 && result != null && result.getPaths() != null) {
            if (result.getPaths().size() > 0) {
                final BusPath path = result.getPaths().get(0);
                TransitRouteOverlay routeOverlay = new TransitRouteOverlay(
                        aMap,
                        path,
                        result.getStartPos(),
                        result.getTargetPos()
                );
                aMap.clear();
                routeOverlay.addToMap();
                routeOverlay.zoomToSpan();
            }
        }
    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult result, int i) {
        if (i == 1000 && result != null && result.getPaths() != null) {
            if (result.getPaths().size() > 0) {
                final DrivePath path = result.getPaths().get(0);
                DrivingRouteOverlay routeOverlay = new DrivingRouteOverlay(
                        aMap,
                        path,
                        result.getStartPos(),
                        result.getTargetPos()
                );
                aMap.clear();
                routeOverlay.addToMap();
                routeOverlay.zoomToSpan();
            }
        }
    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult result, int i) {
        if (i == 1000 && result != null && result.getPaths() != null) {
            if (result.getPaths().size() > 0) {
                final WalkPath path = result.getPaths().get(0);
                WalkingRouteOverlay routeOverlay = new WalkingRouteOverlay(
                        aMap,
                        path,
                        result.getStartPos(),
                        result.getTargetPos()
                );
                aMap.clear();
                routeOverlay.addToMap();
                routeOverlay.zoomToSpan();
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        startRoute();
        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        startRoute();
    }
}
