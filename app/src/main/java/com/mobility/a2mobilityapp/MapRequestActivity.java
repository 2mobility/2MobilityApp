package com.mobility.a2mobilityapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mobility.a2mobilityapp.project.bean.Endereco;
import com.mobility.a2mobilityapp.project.bean.MeioTransporte;
import com.mobility.a2mobilityapp.project.bean.TransportePublico;
import com.mobility.a2mobilityapp.project.bean.Uber;
import com.mobility.a2mobilityapp.project.services.TransporteOperation;
import com.mobility.a2mobilityapp.project.services.UberOperation;
import com.mobility.a2mobilityapp.project.utils.FragmentList;
import com.mobility.a2mobilityapp.project.utils.PlaceAutocompleteAdapter;

import java.util.ArrayList;

public class MapRequestActivity extends AppCompatActivity implements OnMapReadyCallback ,FragmentList.OnFragmentInteractionListener{

    private Boolean mLocationPermissionGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final String TAG = "MapActivity";
    //private static final String TAG = "MapRequestActivity";
    private static final String FINAL_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    //private EditText enderecoInicial
    //private EditText enderecoFinal;
    private AutoCompleteTextView enderecoInicial;
    private AutoCompleteTextView enderecoFinal;
    private Button btnCompara;
    protected Integer comparativo;
    private Button btnImprimi;
    private Endereco endereco;
    private Uber[] uber;
    private FrameLayout fragmentContainer;
    private ArrayList<MeioTransporte> listaMeios = new ArrayList<>();
    TransportePublico transpPublico = null;

    //AutoComplete
    protected GeoDataClient mGeoDataClient;
    private PlaceAutocompleteAdapter mAdapter;
    private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
            new LatLng(-34.041458, 150.790100), new LatLng(-33.682247, 151.383362));

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_request);
        getLocationPermission();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //geo-localizacao
        enderecoInicial = (AutoCompleteTextView) findViewById(R.id.edit_origem);
        //determina apartir de qual letra ele começa a pesquisar
        enderecoInicial.setThreshold(3);
        enderecoFinal = (AutoCompleteTextView) findViewById(R.id.edit_destino);
        //determina apartir de qual letra ele começa a pesquisar
        enderecoFinal.setThreshold(3);
        btnCompara = findViewById(R.id.btn_comparar);
        fragmentContainer = (FrameLayout) findViewById(R.id.fragment_container);

        //AutoComplete
        mGeoDataClient = Places.getGeoDataClient(this, null);

        enderecoInicial.setOnItemClickListener(mAutocompleteClickListener);
        enderecoFinal.setOnItemClickListener(mAutocompleteClickListener);

        mAdapter = new PlaceAutocompleteAdapter(this, mGeoDataClient, BOUNDS_GREATER_SYDNEY, null);
        enderecoInicial.setAdapter(mAdapter);
        enderecoFinal.setAdapter(mAdapter);

        btnCompara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runOnUiThread(new Runnable(){
                    public void run() {
                        //Fecha o teclado apos clicar no botão
                        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(enderecoInicial.getWindowToken(), 0);

                        endereco = new Endereco();
                        endereco.setNominalPartida(enderecoInicial.getText().toString());
                        endereco.setNominalChegada(enderecoFinal.getText().toString());
                        chamaUber();
                        TransporteOperation transp = new TransporteOperation();
                        String resposta = transp.getValuesTransport(endereco);
                        transpPublico = transp.getTransporte(resposta);
                        openFragment();
                    }
                });

            }
        });
    }

    public void openFragment(){
        FragmentList fragmentList = FragmentList.newInstance("1", "2",uber,transpPublico);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.add(R.id.fragment_container, fragmentList, "LIST_FRAGMENT").commit();
    }
    public void chamaUber(){
        runOnUiThread(new Runnable(){
            public void run() {
                UberOperation uberOperation = new UberOperation();
                uberOperation.chamaLatitudeLongitude(endereco);
                String response = uberOperation.getValuesUber(endereco);
                uber = uberOperation.valoresUber(response);
            }
        });

    }



    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map está iniciado", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map está iniciado");
        mMap = googleMap;

        if (mLocationPermissionGranted) {
            getDeviceLocation();

            //habilitar marcação de localização no mapa
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            //desabilitar botão de localização
            //mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }
    private void initMap() {
        Log.d(TAG, "initMap: Inicializando Map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapRequestActivity.this);
    }
    private void getLocationPermission() {

        Log.d(TAG, "getLocationPermission: coletando permissão de localização");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        //se a permissão aproximada for concedida
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINAL_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //se a permissão exata for concedida
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }
    private void moveCamera(LatLng latLng, float zoom){
        Log.d(TAG, "moveCamera: movendo a camera para: latitude: " + latLng.latitude + ", longitude: " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }
    private void getDeviceLocation(){

        runOnUiThread(new Runnable(){
            public void run() {
                Log.d(TAG, "getDeviceLocation: getting the devices current location");

                mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MapRequestActivity.this);

                try{
                    if(mLocationPermissionGranted){

                        final Task location = mFusedLocationProviderClient.getLastLocation();
                        location.addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if(task.isSuccessful()){
                                    Log.d(TAG, "onComplete: found location!");
                                    Location currentLocation = (Location) task.getResult();

                                    //moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                      //      DEFAULT_ZOOM);

                                }else{
                                    Log.d(TAG, "onComplete: current location is null");
                                    Toast.makeText(MapRequestActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }catch (SecurityException e){
                    Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
                }
            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: Chamado");
        mLocationPermissionGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: Permissão falhou");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: Permissão concedida");
                    mLocationPermissionGranted = true;
                    //iniciar o maps
                    initMap();
                }
            }
        }
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    //AutoComplete
    private AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            final AutocompletePrediction item = mAdapter.getItem(position);
            final String placeId = item.getPlaceId();
            final CharSequence primaryText = item.getPrimaryText(null);

            Log.i(TAG, "Autocomplete item selected: " + primaryText);

            Task<PlaceBufferResponse> placeResult = mGeoDataClient.getPlaceById(placeId);

            Toast.makeText(getApplicationContext(), "Clicked: " + primaryText,
                    Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Called getPlaceById to get Place details for " + placeId);
        }
    };
}
