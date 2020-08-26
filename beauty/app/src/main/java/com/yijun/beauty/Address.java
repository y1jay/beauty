package com.yijun.beauty;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yijun.beauty.activity.CheckoutActivity;

public class Address extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
   Button btnReservation;
   Button btnHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        btnReservation = findViewById(R.id.btnReservation);
        btnHome = findViewById(R.id.btnHome);

        btnReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Address.this, CheckoutActivity.class);
                int add = getIntent().getIntExtra("add",0);
                if(add==1){
                    Toast.makeText(Address.this,"로그인 후 사용해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    startActivity(i);
                }
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        final LatLng main;

        main = new LatLng (37.5593,126.8362133);

        //지도의 중심으로 잡고 싶은 좌표를 넣어주면 지도의 중심으로 표시된다.
        mMap.addMarker(new MarkerOptions().position(main).title("미인닭발 마곡본점"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(main,16));

    }
}
