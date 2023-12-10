package com.Joaquin.formulario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    EditText pregunta, textnombre, textapellido, textedad, textgenero;
    RadioButton respuestaSI, respuestaNO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_main);

        pregunta = findViewById(R.id.preguntaSI);
        respuestaSI = findViewById(R.id.opcionSI);
        respuestaNO = findViewById(R.id.opcionNO);
        textnombre = findViewById(R.id.nombre);
        textapellido = findViewById(R.id.apellido);
        textedad = findViewById(R.id.edad);
        textgenero = findViewById(R.id.genero);
    }

    public void verPregunta (View view) {
        if (respuestaSI.isChecked()) {
            pregunta.setVisibility(View.VISIBLE);
        } else if (respuestaNO.isChecked()){
            pregunta.setVisibility(View.GONE);
        }
    }

    public void validar (View view) {
        if (textnombre.getText().toString().isEmpty()) {
            Toast.makeText(this, "Introduce tu nombre", Toast.LENGTH_SHORT).show();
        } else if (textapellido.getText().toString().isEmpty()) {
            Toast.makeText(this, "Introduce tu apellido", Toast.LENGTH_SHORT).show();
        } else if (textedad.getText().toString().isEmpty()) {
            Toast.makeText(this, "Introduce tu edad", Toast.LENGTH_SHORT).show();
        } else if (Integer.parseInt(textedad.getText().toString()) <18) {
            Toast.makeText(this, "Debes ser mayor de 18 años", Toast.LENGTH_SHORT).show();
        } else if (textgenero.getText().toString().isEmpty()) {
            Toast.makeText(this, "Introduce tu genero", Toast.LENGTH_SHORT).show();
        }else if (respuestaSI.isChecked() && pregunta.getText().toString().isEmpty()) {
            Toast.makeText(this, "Introduce en que concurso participaste", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "La inscripcion se ha hecho con exito", Toast.LENGTH_SHORT).show();
        }
    }
}

