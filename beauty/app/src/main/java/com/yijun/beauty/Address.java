package com.yijun.beauty;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import com.yijun.beauty.activity.CheckoutActivity;

import java.util.Map;

public class Address extends FragmentActivity implements OnMapReadyCallback {

    Button btn_reservation;
    Button btn_home;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        btn_reservation = findViewById(R.id.btn_reservation);
        btn_home = findViewById(R.id.btn_home);

        btn_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              int add = getIntent().getIntExtra("add",0);
              if (add==1){
                  Toast.makeText(Address.this,"로그인 후 이용가능합니다.", Toast.LENGTH_SHORT).show();
                  return;
              }
              if (add == 3){
                  Intent i = new Intent(Address.this, Reservation.class);
                  startActivity(i);
              }
            }
        });

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Address.this, MainActivity.class);
                startActivity(i);
            }
        });

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

        LatLng coord = new LatLng(37.560296, 126.838154);

//        Toast.makeText(Address.this,
//                "위도: " + coord.latitude + ", 경도: " + coord.longitude,
//                Toast.LENGTH_SHORT).show();
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_TRANSIT, true);
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, true);

        Marker marker = new Marker();
        marker.setPosition(coord);
        marker.setCaptionText("미인닭발");
        marker.setCaptionColor(Color.RED);
        marker.setIcon(MarkerIcons.RED);
        marker.setIconTintColor(Color.RED);
        marker.setMap(naverMap);

        Marker parkingMarker = new Marker();
        parkingMarker.setPosition(new LatLng(37.560348, 126.838154));
        parkingMarker.setCaptionText("미인닭발 주차장입구");
        parkingMarker.setCaptionColor(Color.BLACK);
        parkingMarker.setIcon(MarkerIcons.LIGHTBLUE);
        parkingMarker.setMap(naverMap);

    }
}