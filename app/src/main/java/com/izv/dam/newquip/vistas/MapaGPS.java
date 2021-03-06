package com.izv.dam.newquip.vistas;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.izv.dam.newquip.R;
import com.izv.dam.newquip.basedatos.AyudanteMapa;
import com.izv.dam.newquip.pojo.Mapa;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.List;

public class MapaGPS extends FragmentActivity
        implements OnMapReadyCallback,
        com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks,
        com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;
    private com.google.android.gms.common.api.GoogleApiClient GoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        init();
    }

    protected void onStart() {
        GoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        GoogleApiClient.disconnect();
        super.onStop();
    }


    private void init() {
        if (GoogleApiClient == null) {
            GoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    //OnMapReadyCallback
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        AyudanteMapa am = new AyudanteMapa(this);
        RuntimeExceptionDao<Mapa, Integer> simpleDao = am.getDataDao();

        List<Mapa> mapa = simpleDao.queryForAll();
        for (Mapa map: mapa){
            if(map.toString().isEmpty()){
                Toast.makeText(this, "No hay ubicaciones guardadas", Toast.LENGTH_SHORT).show();
            }else{
                LatLng punto = new LatLng(map.getLatitud(), map.getLongitud());
                mMap.addMarker(new MarkerOptions().position(punto).title("Titulo: "+map.getTitulo()));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(punto, 12));
            }
        }
    }


    //GoogleApiClient.ConnectionCallbacks
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Toast.makeText(this, "Conectado", Toast.LENGTH_SHORT).show();


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

        }
        permisos();
    }

    //GoogleApiClient.ConnectionCallbacks
    @Override
    public void onConnectionSuspended(int i) {

    }

    //GoogleApiClient.OnConnectionFailedListener
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        AyudanteMapa am = new AyudanteMapa(this);
        RuntimeExceptionDao<Mapa, Integer> simpleDao = am.getDataDao();

        List<Mapa> mapa = simpleDao.queryForAll();
        for (Mapa map: mapa){
            if(map.toString().isEmpty()){
                Toast.makeText(this, "No hay ubicaciones guardadas", Toast.LENGTH_SHORT).show();
            }else{
                LatLng punto = new LatLng(map.getLatitud(), map.getLongitud());
                mMap.addMarker(new MarkerOptions().position(punto).title("Titulo: "+map.getTitulo()));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(punto, 12));
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permiso concedido", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void permisos(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(GoogleApiClient);
            if (mLastLocation != null) {
                //Toast.makeText(this, mLastLocation.getLatitude() + " - " + mLastLocation.getLongitude(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Sin datos", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
