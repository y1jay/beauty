package com.yijun.beauty;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.MarkerIcons;

import java.util.Map;

public class Address extends FragmentActivity implements OnMapReadyCallback {

    Map map;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {

        LatLng coord = new LatLng(37.560265,126.838157);

        Toast.makeText(Address.this,
                "위도: " + coord.latitude + ", 경도: " + coord.longitude,
                Toast.LENGTH_SHORT).show();
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_TRANSIT, true);
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, true);

        Marker marker = new Marker();
        marker.setPosition(coord);
        marker.setIcon(MarkerIcons.RED);
        marker.setIconTintColor(Color.RED);
        marker.setMap(naverMap);

    }
}