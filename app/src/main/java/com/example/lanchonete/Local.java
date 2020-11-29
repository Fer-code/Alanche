package com.example.lanchonete;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class Local extends AppCompatActivity implements
        FetchAddressTask.OnTaskCompleted {

    public static final String PREFERENCIAS_NAME = "com.example.android.localizacao";
    public final static String ENDERECO = "com.example.lanchonete.END";
    private static final String TRACKING_LOCATION_KEY = "tracking_location";
    private static final String FILE_NAME = "example.txt";

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final String LATITUDE_KEY = "latitude";
    private static final String LONGITUDE_KEY = "longitude";

    private Button btnmLocation;
    private TextView txtMLocation;
    private static final String LASTADRESS_KEY = "adress";
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private boolean mTrackingLocation;

    private SharedPreferences mPreferences;
    private String lastLatitude = "";
    private String lastLongitude = "";
    private String lastAdress = "";



    private boolean usesExternalStorage;
    Switch switcher;

    public static final String PREFERENCIAS_VALOR = "com.example.lanchonete";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local);

        switcher = findViewById(R.id.switch1);

        getSupportActionBar().hide();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(
                this);

        btnmLocation = (Button) findViewById(R.id.btLocal);
        txtMLocation = (TextView) findViewById(R.id.txtLocal);

        if (savedInstanceState != null) {
            mTrackingLocation = savedInstanceState.getBoolean(
                    TRACKING_LOCATION_KEY);
        }

        btnmLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!mTrackingLocation) {
                    startTrackingLocation();
                } else {
                    stopTrackingLocation();
                }
            }
        });

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {

                if (mTrackingLocation) {
                    new FetchAddressTask(Local.this, Local.this)
                            .execute(locationResult.getLastLocation());
                }
            }
        };

        mPreferences = getSharedPreferences(PREFERENCIAS_NAME, MODE_PRIVATE);
        recuperar();

    }



    public void switchClick(View v)
    {
        usesExternalStorage = switcher.isChecked();
        SharedPreferences settings = getSharedPreferences(PREFERENCIAS_NAME , 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(PREFERENCIAS_VALOR, usesExternalStorage);
        editor.commit();

        if(usesExternalStorage == false){
            Toast.makeText(this, "Permissão negada", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Permissão aceita", Toast.LENGTH_SHORT).show();

        }

    }
    private void recuperar()
    {
        SharedPreferences settings = getSharedPreferences(PREFERENCIAS_NAME , 0);
        usesExternalStorage = settings.getBoolean(PREFERENCIAS_VALOR, false);
        switcher.setChecked(usesExternalStorage);
    }

    private void startTrackingLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            mTrackingLocation = true;
            mFusedLocationClient.requestLocationUpdates
                    (getLocationRequest(),
                            mLocationCallback,
                            null);

            txtMLocation.setText(getString(R.string.address_text,
                    getString(R.string.carregando), null, null));
            btnmLocation.setText(R.string.buscar);

        }
    }

    private LocationRequest getLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    private void stopTrackingLocation() {
        if (mTrackingLocation) {
            mTrackingLocation = false;
            btnmLocation.setText(R.string.iniciar);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(TRACKING_LOCATION_KEY, mTrackingLocation);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startTrackingLocation();
                } else {
                    Toast.makeText(this, R.string.permissao_negada,
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onTaskCompleted(String[] result) {
        if (mTrackingLocation) {
            // Update the UI
            lastLatitude = result[1];
            lastLongitude = result[2];
            lastAdress = result[0];
            txtMLocation.setText(getString(R.string.address_text,
                    lastAdress, lastLatitude, lastLongitude));
        }
    }



    public void continuar(View continuar){
        txtMLocation = findViewById(R.id.txtLocal);

        String txtLocal = txtMLocation.getText().toString();

            Toast.makeText(this, "Endereço Registrado com sucesso estamos a caminho", Toast.LENGTH_SHORT).show();

    }

    public void save(View v){
        String text = txtMLocation.getText().toString();
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(text.getBytes());

           // txtMLocation.getText().clear();
            Toast.makeText(this, "Savo em " + getFilesDir()+ "/" + FILE_NAME, Toast.LENGTH_LONG).show();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(fos != null){
                try {
                    fos.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
    public void load(View v){

        FileInputStream fis = null;

        try{
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null){
                sb.append(text).append("\n");
            }

            txtMLocation.setText(sb.toString());

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(fis != null){
                try{
                    fis.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

    }
}