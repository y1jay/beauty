package com.yijun.beauty;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.MarkerIcons;

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
                    finish();
                    CheckTypesTask task = new CheckTypesTask();
                    task.execute();
                    startActivity(i);
                }
            }
        });

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
    private  class CheckTypesTask extends AsyncTask<Void, Integer, Boolean> {
        ProgressDialog asyncDialog = new ProgressDialog(Address.this);

        @Override
        protected void onPreExecute(){
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("로딩중..");
            asyncDialog.show();
            asyncDialog.setCancelable(false);
            super.onPreExecute();
        }
        @Override
        protected Boolean doInBackground(Void... strings){

            for(int i = 0; i<10000; i++){
                publishProgress(i);


            }
            return true;

        }

        @Override
        protected void onPostExecute(Boolean s){

            asyncDialog.dismiss();
            super.onPostExecute(s);
        }


        @Override
        protected void onCancelled(Boolean s){
            super.onCancelled(s);
        }

    }
}
