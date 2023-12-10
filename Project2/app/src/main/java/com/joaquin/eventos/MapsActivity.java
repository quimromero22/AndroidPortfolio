package com.joaquin.eventos;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.joaquin.eventos.databinding.ActivityMapsBinding;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    FusedLocationProviderClient fusedLocationClient;
    Geocoder geocoder;
    List<Address> direccion = new ArrayList<>();
    String nombre, lugar;
    LatLng direccionEvento;
    Long tiempoSeleccionado=0L;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Solicitar permisos de la ubicacion
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        geocoder = new Geocoder(this, Locale.getDefault());

        //Extraer los datos de los otros layouts
        nombre = getIntent().getStringExtra("nombre");
        lugar = getIntent().getStringExtra("direccion");

        //He añadido el tiempo long porque en int no me dejaba
        tiempoSeleccionado = getIntent().getLongExtra("hora",0L);

        //Hay nombre y lugar del evento que se visualiza en los TextView
        if (nombre != null && lugar != null) {
            binding.verNombre.setText(nombre);
            binding.verDireccion.setText(horaFechaEvento(tiempoSeleccionado));

            direccionEvento = obtenerUbicaciónDesdeDirección(MapsActivity.this, lugar);
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        obtenerUbicaciónActual();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        obtenerUbicaciónActual();
    }

    public void abrirPantalla2(View view) {
        Intent intent = new Intent(this, Fecha_HoraArctivity.class);
        startActivity(intent);
    }

    @Override
    //Solicitud permisos de tiempo de ejecución
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length != 0) {
            Toast.makeText(MapsActivity.this, "Permiso concedido", Toast.LENGTH_SHORT).show();
            obtenerUbicaciónActual();
        }
    }
    //Darle formato a la fecha y hora
    String horaFechaEvento(Long horaFecha){
        String hfEvento="";
        Date dateHour= new Date(horaFecha);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MM/dd/yyyy hh:mm");
        hfEvento=simpleDateFormat.format(dateHour);
        return hfEvento;
    }

    private void obtenerUbicaciónActual() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
            return;
        }
        //Conocer la ubicacion mas reciente
        Task<Location> task = fusedLocationClient.getLastLocation();
        //Cuando encuentra con exito la localizacion enviamos la referencia del metodo
        task.addOnSuccessListener(this::localizarUbicacion);
    }

    public LatLng obtenerUbicaciónDesdeDirección(Context context, String nombreDireccionEvento) {
        Geocoder coder = new Geocoder(context);
        List<Address> lugarEvento;
        LatLng sitio = null;

        try {
            //Maximo 3 resultados para averiguar el lugar del evento. Salta excepciones a veces
            lugarEvento = coder.getFromLocationName(nombreDireccionEvento, 2);
            //Sino se ha escrito ninguna direccion en la lista que empiece el buble
            if (lugarEvento == null ) {
                return null;
            }
            if (lugarEvento.size()==0){
                return null;
            }
            //La primera direccion sera el resultado a mostrar
            Address posicionEvento = lugarEvento.get(0);
            //Guarda las coordenadas de la direccion
            sitio = new LatLng(posicionEvento.getLatitude(), posicionEvento.getLongitude());

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return sitio;
    }

    private void localizarUbicacion(Location location) {
        //Si hay localizacion, chequea si tiene permiso y busca las coordenadas
        if (location != null) {
            if (ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                double getLongitude = Double.parseDouble(String.valueOf(location.getLongitude()));
                double getLatitude = Double.parseDouble(String.valueOf(location.getLatitude()));
                Geocoder geocoder;

                geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
                try {
                    //Si hay direccion del evento muestra el lugar del Evento con un marker
                    if (direccionEvento != null) {
                        mMap.addMarker(new MarkerOptions().position(direccionEvento).title("Lugar del Evento"));
                        //Se mueve hacia el lugar del evento
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(direccionEvento));
                    }else {
                        //Si no hay direccion muestra tu ubicacion
                        direccion = geocoder.getFromLocation(getLatitude, getLongitude, 1);
                        String address = direccion.get(0).getAddressLine(0);
                        LatLng currentLocation = new LatLng(getLatitude, getLongitude);
                        mMap.addMarker(new MarkerOptions().position(currentLocation).title("Tu ubicacion"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                //Si no hay localizacion, vuelve a preguntar por los permisos
                ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
            }
        }
    }
}