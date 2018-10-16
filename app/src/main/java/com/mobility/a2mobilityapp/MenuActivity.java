package com.mobility.a2mobilityapp;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.util.LogPrinter;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import java.util.ArrayList;

import com.mobility.a2mobilityapp.project.bean.Endereco;
import com.mobility.a2mobilityapp.project.bean.Particular;
import com.mobility.a2mobilityapp.project.bean.TransportePublico;
import com.mobility.a2mobilityapp.project.bean.Uber;
import com.mobility.a2mobilityapp.project.model.Automovel;
import com.mobility.a2mobilityapp.project.services.CrudOperation;
import com.mobility.a2mobilityapp.project.services.ParticularOperation;
import com.mobility.a2mobilityapp.project.services.TransporteOperation;
import com.mobility.a2mobilityapp.project.services.UberOperation;
import com.mobility.a2mobilityapp.project.bean.MeioTransporte;
import com.mobility.a2mobilityapp.project.utils.FragmentList;
import com.mobility.a2mobilityapp.project.utils.HttpDataHandler;


import com.mobility.a2mobilityapp.project.utils.VariablesGlobal;
import com.mobility.a2mobilityapp.project.view.CadastroAutomovelFragment;
import com.mobility.a2mobilityapp.project.view.CadastroPessoaActivity;
import com.mobility.a2mobilityapp.project.view.GraficoFinanceiroFragment;
import com.mobility.a2mobilityapp.project.view.GraficoTempoFragment;
import com.mobility.a2mobilityapp.project.view.MeusAutomoveisFragment;
import com.mobility.a2mobilityapp.project.view.PerfilFragment;
import com.uber.sdk.android.core.UberSdk;
import com.uber.sdk.android.core.auth.AccessTokenManager;
import com.uber.sdk.android.rides.RideParameters;
import com.uber.sdk.core.auth.Scope;
import com.uber.sdk.rides.auth.OAuth2Credentials;
import com.uber.sdk.rides.client.CredentialsSession;
import com.uber.sdk.rides.client.ServerTokenSession;
import com.uber.sdk.rides.client.SessionConfiguration;
import com.uber.sdk.rides.client.UberRidesApi;
import com.uber.sdk.rides.client.model.Product;
import com.uber.sdk.rides.client.model.ProductsResponse;
import com.uber.sdk.rides.client.model.RideEstimate;
import com.uber.sdk.rides.client.model.RideRequestParameters;
import com.uber.sdk.rides.client.services.RidesService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.mobility.a2mobilityapp.project.utils.PlaceAutocompleteAdapter;
import retrofit2.Response;

import static java.lang.Double.parseDouble;
import static java.lang.Float.*;
import static java.lang.Thread.sleep;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, FragmentList.OnFragmentInteractionListener {

    //----Google API
    private static final String TAG = "Map";
    //localização aproximada
    private static final String FINAL_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    //localização exata
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    //variaveis
    private Boolean mLocationPermissionGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    //----ListView
    private FrameLayout fragmentContainer;
    private Button btnComparar;

    private AutoCompleteTextView  enderecoInicial;
    private AutoCompleteTextView  enderecoFinal;
    private Button btnCompara;
    private Button btnCompara2;
    protected Integer comparativo;
    private Button btnImprimi;
    private Endereco endereco;
    private Uber[] uber;
    private ArrayList<MeioTransporte> listaMeios = new ArrayList<>();
    TransportePublico transpPublico = null;
    Particular particular = null;

    LatLng startLatLgn;

    Geocoder geoCoder;

    //AutoComplete
    protected GeoDataClient mGeoDataClient;
    private PlaceAutocompleteAdapter mAdapter;
    private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
            new LatLng(-34.041458, 150.790100), new LatLng(-33.682247, 151.383362));

    //loading de processamento (Progress Bar)
    private ProgressBar progressBar;

    //imgs edit
    private ImageView ic_localizacao_img;
    private ImageView ic_pontos_img;

    double latitudeAtual = 0;
    double longitudeAtual = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //com o geo coder sendo iniciado no onCreate resolve o problema de cair o app
        geoCoder = new Geocoder(this, Locale.getDefault());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //----Google API
        getLocationPermission();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //geo-localizacao
        enderecoInicial = (AutoCompleteTextView) findViewById(R.id.edit_origem);
        enderecoFinal = (AutoCompleteTextView)  findViewById(R.id.edit_destino);
        btnCompara = (Button) findViewById(R.id.btn_comparar);
        btnCompara2 = (Button) findViewById(R.id.btn_comparar2);
        fragmentContainer = (FrameLayout) findViewById(R.id.fragment_container);

        //AutoComplete
        mGeoDataClient = Places.getGeoDataClient(this, null);

        enderecoInicial.setOnItemClickListener(mAutocompleteClickListener);
        enderecoFinal.setOnItemClickListener(mAutocompleteClickListener);

        mAdapter = new PlaceAutocompleteAdapter(this, mGeoDataClient, BOUNDS_GREATER_SYDNEY, null);
        enderecoInicial.setAdapter(mAdapter);
        enderecoFinal.setAdapter(mAdapter);

        //Toast.makeText(this, Auto, Toast.LENGTH_SHORT).show();

        //loading - ProgressBar
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        //imgs edits
        ic_localizacao_img = (ImageView) findViewById(R.id.ic_localizacao_img);
        ic_pontos_img = (ImageView) findViewById(R.id.ic_pontos_img);

        touchBtnCompara();

        chamaIcone();

    }

    public void clickBtnCompara(){
        //Ação do botão comparar
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
                        VariablesGlobal variablesGlobal = (VariablesGlobal) getApplication();
                        String id = variablesGlobal.getPessoa();
                        CrudOperation crudOperation = new CrudOperation();
                        Automovel automovel1 =crudOperation.retornaParticular(id);
                        if(!automovel1.getApelido().equals("")){
                            particular = new Particular();
                            particular.setPreco(automovel1.getMediaCombustivel()+"");
                            particular.setKmLitro(automovel1.getKmLitro()+"");
                            particular.setApelido(automovel1.getApelido()+"");
                        }

                        openFragment();
                    }
                });      
            }
        });
    }

    public void mostrarProgressBar(View view){
        btnCompara.setVisibility(View.GONE);
        btnCompara2.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        clickBtnCompara();
    }
    public void esconderProgressBar(View view){
        progressBar.setVisibility(View.GONE);
        btnCompara2.setVisibility(View.GONE);
        btnCompara.setVisibility(View.VISIBLE);
    }

    public void touchBtnCompara() {
        btnCompara.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    btnCompara.setBackgroundColor(Color.rgb(0, 100, 0));
                    if((enderecoInicial.getText() + "") != "" && (enderecoFinal.getText() + "") != ""){
                        mostrarProgressBar(findViewById(R.id.progressBar));
                    }
                    else{
                        Toast.makeText(MenuActivity.this,"Atenção! Preencha o endereço de origem e destino.",Toast.LENGTH_SHORT).show();
                    }

                } else if (event.getAction() == KeyEvent.ACTION_UP) {
                    btnCompara.setBackgroundColor(getResources().getColor(R.color.text_background_verde));
                }
                return false;
            }
        });
    }

    public void chamaIcone() {
        /*ic_localizacao_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MenuActivity.this,"FOIIIII",Toast.LENGTH_SHORT).show();

            }
        });*/

        ic_pontos_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MenuActivity.this,"FOIIIII 2",Toast.LENGTH_SHORT).show();

            }
        });
    }


    public void openFragment(){
        VariablesGlobal variablesGlobal = (VariablesGlobal) getApplication();
        FragmentList fragmentList = FragmentList.newInstance("1", "2",uber,transpPublico,particular,variablesGlobal.getPessoa());
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.add(R.id.fragment_container, fragmentList, "LIST_FRAGMENT").commit();
        esconderProgressBar(findViewById(R.id.progressBar));
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_mapa) {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_perfil) {
            PerfilFragment perfil = new PerfilFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_conteudo, perfil).commit();
        } else if (id == R.id.nav_automovel) {
            MeusAutomoveisFragment automovel = new MeusAutomoveisFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_conteudo, automovel).commit();
        } else if (id == R.id.nav_grafico_tempo) {
            GraficoTempoFragment graficoTempo = new GraficoTempoFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_conteudo, graficoTempo).commit();
        } else if (id == R.id.nav_grafico_custo) {
            GraficoFinanceiroFragment graficoCusto = new GraficoFinanceiroFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_conteudo, graficoCusto).commit();
        } /*else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    //------Métodos para Google API
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        //Toast.makeText(this, "Map está iniciado", Toast.LENGTH_SHORT).show();
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
            //desabilitar botão de localização padrão do google
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

            //cria ação do botão de localização personalizado
            ic_localizacao_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LatLng latLng = new LatLng(latitudeAtual, longitudeAtual);
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
                    googleMap.animateCamera(cameraUpdate);
                }
            });

        }
    }


    private void getDeviceLocation(){
        runOnUiThread(new Runnable(){
            public void run() {
                Log.d(TAG, "getDeviceLocation: getting the devices current location");

                mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MenuActivity.this);

                try{
                    if(mLocationPermissionGranted){

                        final Task location = mFusedLocationProviderClient.getLastLocation();
                        location.addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if(task.isSuccessful()){
                                    Log.d(TAG, "onComplete: found location!");
                                    Location currentLocation = (Location) task.getResult();

                                    if(currentLocation != null){
                                        moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                                DEFAULT_ZOOM);

                                        try{
                                            List<Address> myAddresses = geoCoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
                                            String address  = myAddresses.get(0).getAddressLine(0);
                                            String city     = myAddresses.get(0).getLocality();
                                            Log.d(TAG, "Seu endereço: " + address);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        try {
                                            List<Address> addresses = geoCoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
                                            if (addresses != null && addresses.size() > 0) {
                                                //Toast.makeText(MenuActivity.this, "Endereço: " + addresses.get(0).getAddressLine(0), Toast.LENGTH_LONG).show();
                                                enderecoInicial.setText(addresses.get(0).getAddressLine(0));
                                                latitudeAtual = addresses.get(0).getLatitude();
                                                longitudeAtual = addresses.get(0).getLongitude();
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }else{
                                        Toast.makeText(MenuActivity.this,"Por Favor! Ative sua localização.",Toast.LENGTH_SHORT).show();
                                    }

                                }else{
                                    Log.d(TAG, "onComplete: current location is null");
                                    Toast.makeText(MenuActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
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

    private void initMap() {
        Log.d(TAG, "initMap: Inicializando Map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MenuActivity.this);
    }

    //request para obeter permissão de localização
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
