package com.example.mpteam;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener {

    ArrayList<Double> lng_list;
    ArrayList<Double> lat_list;
    ArrayList<String> name_list;
    ArrayList<Bitmap> images_list;
    ArrayList<Marker> marker_list;

    // DiaryActvity3에서 받아오는 것
    Intent intent;
    String title;

    String[] permission_list = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    LocationManager locationManager;
    GoogleMap map;
    MapView mapView;

    @Override
    public boolean onMarkerClick(@NonNull  Marker marker) {
        Toast.makeText(this, marker.getTitle() + "\n" + marker.getPosition(), Toast.LENGTH_SHORT).show();
        marker.showInfoWindow();
        return true;
    }
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permission_list, 0);
        } else {
            init();

            marker_list = new ArrayList<>();
            lng_list = new ArrayList<>();
            lat_list = new ArrayList<>();
            name_list = new ArrayList<>();
            images_list = new ArrayList<>();

        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        getMyLocation();

        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(@NonNull LatLng latLng) {
                MarkerOptions marker = new MarkerOptions().position(latLng);
                marker.title(title).draggable(true);
                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
                map.addMarker(marker);

                moveCameraPos(marker.getPosition());
            }
        });

        map.setOnMarkerClickListener(this::onMarkerClick);

    }

    public void moveCameraPos(LatLng latLng) {
        CameraUpdate updatePos = CameraUpdateFactory.newLatLng(latLng);
        map.animateCamera(updatePos);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                return;
            }
        }
        init();
    }

    public void init() {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.GoogleMap);
        intent = getIntent();

        title = intent.getStringExtra("title");


        mapFragment.getMapAsync(this::onMapReady);
    }


    // 구글맵에 표시된 정보 전부 지움
    public void setGoogleMapClear() {
        map.clear();
    }


    // 현재 위치를 측정하는 메서드
    public void getMyLocation() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // 권한 확인 작업
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED)
                return;
        }

        // 이전에 측정했던 값을 가져온다.
        Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location location2 = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if (location1 != null) {
            setMyLocation(location1);
        } else if (location2 != null)
            setMyLocation(location2);
        // 새롭게 측정한다.
        GetMyLocationListener listener = new GetMyLocationListener();

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10f, listener);
        }
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10f, listener);
        }
    }

    public void setMyLocation(Location location) {
        String TAG = "test123";
        Log.d(TAG, "위도: " + location.getLatitude());
        Log.d(TAG, "경도도: " + location.getLongitude());

        // 위도와 경도값을 관리하는 객체
        LatLng position = new LatLng(location.getLatitude(), location.getLongitude());

        // 위성 카메라의 위치를 옮긴다.
        CameraUpdate update1 = CameraUpdateFactory.newLatLng(position);
        CameraUpdate update2 = CameraUpdateFactory.zoomTo(20f);     // 현재보다 15배 확대

        map.moveCamera(update1);
        map.animateCamera(update2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED)
                return;
        }


        // 현재 위치 표시
        map.setMyLocationEnabled(true);
//        map.addMarker(new MarkerOptions().position(position).title("내 위치"));

    }

    // 조회하는 쪽 onMapReady에 넣어야함
    // 일기 조회할 때 마커 띄우기 여기서 쓰는 함수는 아니지만 미리 구현
    public void getMarkers() {
        String TAG = "GetMarkers";
        for (Marker marker : marker_list) {
            Log.d(TAG, " " + marker.getTitle());
            map.addMarker(new MarkerOptions().position(marker.getPosition()).title(marker.getTitle()));
        }
    }

    // 현재 위치 측정이 성공하면 반응하는 리스너 Location 이 변경되면 setMyLocation 위치변경
    class GetMyLocationListener implements LocationListener {
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {

        }

        // 위치 측정에 필요한 요소가 사용이 불가능 하게 되었을 때
        @Override
        public void onProviderDisabled(@NonNull String provider) {

        }

        @Override
        public void onLocationChanged(@NonNull Location location) {
            setMyLocation(location);
            locationManager.removeUpdates(this);
            //위치 측정 중단.
        }
    }


}