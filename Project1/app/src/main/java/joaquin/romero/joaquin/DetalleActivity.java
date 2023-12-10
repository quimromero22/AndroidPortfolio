package joaquin.romero.joaquin;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetalleActivity extends AppCompatActivity {

    TextView nombreTextView, edadTextView, ofensaTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enemigos);

        nombreTextView = findViewById(R.id.nombreTextView);
        edadTextView = findViewById(R.id.edadTextView);
        ofensaTextView = findViewById(R.id.ofensaTextView);

        // Obtener la información del intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String nombre = extras.getString("nombre");
            String edad = extras.getString("edad");
            String texto = extras.getString("ofensa");

            nombreTextView.setText(nombre);
            edadTextView.setText(edad);
            ofensaTextView.setText(texto);
        }
    }
}
