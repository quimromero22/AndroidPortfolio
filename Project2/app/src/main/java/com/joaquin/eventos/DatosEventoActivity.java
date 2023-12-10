package com.joaquin.eventos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DatosEventoActivity extends AppCompatActivity {

    EditText edNombre,edDirecion;
    Long hora=0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_evento);

        edNombre=findViewById(R.id.tituloEvento);
        edDirecion=findViewById(R.id.lugarEvento);
        hora=getIntent().getLongExtra("hora",0L);
    }

    public void abrirPantalla1(View view){
        String nombre=edNombre.getText().toString();
        String direccion=edDirecion.getText().toString();

        //Muestre un toast si no se a rellenado los editTexts
        //Return para que no pase al siguente layout hasta que este todo rellenado
        if(nombre.isEmpty()){
            Toast.makeText(this, "Introduzca el nombre del evento", Toast.LENGTH_SHORT).show();
            return;
        }
        if(direccion.isEmpty()){
            Toast.makeText(this, "Introduzca la ubicación", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("nombre",nombre);
        intent.putExtra("direccion",direccion);
        intent.putExtra("hora",hora);
        startActivity(intent);
    }
}