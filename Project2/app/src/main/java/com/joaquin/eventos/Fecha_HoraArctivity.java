package com.joaquin.eventos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Fecha_HoraArctivity extends AppCompatActivity implements TimePicker.OnTimeChangedListener, DatePicker.OnDateChangedListener {

    TimePicker hour;
    DatePicker date;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fecha_hora_arctivity);

        hour = findViewById(R.id.hora);
        date = findViewById(R.id.fecha);

        hour.setOnTimeChangedListener(this);
        //Que sea compatible con versiones anteriores
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        date.setOnDateChangedListener(this);
        }

        calendar = new GregorianCalendar();
    }

    @Override
    //Obtener la hora seleccionada
    public void onTimeChanged(TimePicker timePicker, int hora, int minuto) {
        calendar.set(Calendar.HOUR_OF_DAY, hora);
        calendar.set(Calendar.MINUTE, minuto);
    }

    @Override
    //Obtener la fecha seleccionada
    public void onDateChanged(DatePicker datePicker, int anio, int mes, int dia) {
        calendar.set(anio, mes, dia);
    }

    public void abrirPantalla3(View view){
        Long time= calendar.getTimeInMillis();
        Intent intent = new Intent(this, DatosEventoActivity.class);
        intent.putExtra("hora",time);
        startActivity(intent);
    }
}