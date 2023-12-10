package joaquin.romero.joaquin;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

     TextView nombreTextView;
     List<String> nombres, edades, ofensas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombreTextView = findViewById(R.id.nombreTextView);
        nombres = new ArrayList<>();
        edades = new ArrayList<>();
        ofensas = new ArrayList<>();

        Button botonGuardar = findViewById(R.id.boton1);
        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nombreEditText = findViewById(R.id.nombreEditText);
                EditText edadEditText = findViewById(R.id.edadEditText);
                EditText ofensaEditText = findViewById(R.id.ofensaEditText);

                String nombre = nombreEditText.getText().toString();
                String edad = edadEditText.getText().toString();
                String texto = ofensaEditText.getText().toString();

                if (nombre.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Introduce tu nombre", Toast.LENGTH_SHORT).show();
                } else if (edad.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Introduce tu edad", Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(edad) < 0) {
                    Toast.makeText(MainActivity.this, "Debes ser mayor de 0 años", Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(edad) > 150) {
                    Toast.makeText(MainActivity.this, "Debes ser menor de 150 años", Toast.LENGTH_SHORT).show();
                } else if (texto.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Explica tu ofensa", Toast.LENGTH_SHORT).show();
                } else {
                    nombres.add(nombre);
                    edades.add(edad);
                    ofensas.add(texto);

                    mostrarRegistros();

                    nombreEditText.setText("");
                    edadEditText.setText("");
                    ofensaEditText.setText("");
                }
            }
        });

        nombreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener la posición del registro
                int position = (int) v.getTag();

                // Obtener la información del registro seleccionado
                String nombre = nombres.get(position);
                String edad = edades.get(position);
                String ofensa = ofensas.get(position);

                // Crear un Intent para abrir enemigos.xml
                Intent intent = new Intent(MainActivity.this, DetalleActivity.class);
                intent.putExtra("nombre", nombre);
                intent.putExtra("edad", edad);
                intent.putExtra("ofensa", ofensa);
                startActivity(intent);
            }
        });
    }

    private void mostrarRegistros() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < nombres.size(); i++) {
            stringBuilder.append(nombres.get(i));
            stringBuilder.append("\n");

            // Asignar la posición del registro
            nombreTextView.setTag(i);
        }

        nombreTextView.setText(stringBuilder.toString());
    }
}
