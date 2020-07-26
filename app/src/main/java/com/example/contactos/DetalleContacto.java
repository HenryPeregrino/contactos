package com.example.contactos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetalleContacto extends Activity implements View.OnClickListener {

    TextView jNombre, jTelefono, jEmail, jDesc, jFecha;
    Button jbEditar;
    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_contacto);

        //views
        jNombre = findViewById(R.id.xtvNombreCompleto);
        jTelefono = findViewById(R.id.xtvTelefono);
        jEmail = findViewById(R.id.xtvEmail);
        jDesc = findViewById(R.id.xtvDescripcion);
        jFecha = findViewById(R.id.xtvFecha);
        jbEditar = findViewById(R.id.xbEditar);

        //obtener intent y bundle con la información del contacto
        Intent i = getIntent();
        b = i.getExtras();

        //Colocamos los extras en los respectivos textviews
        jNombre.setText(getString(R.string.nombre_header, b.getString("Nombre")));
        jFecha.setText(getString(R.string.fecha_header, b.getString("Fecha")));
        jTelefono.setText(getString(R.string.tel_header, b.getString("Tel")));
        jEmail.setText(getString(R.string.email_header, b.getString("Email")));
        jDesc.setText(b.getString("Desc"));

        //configuramos el evento del clic en el botón para editar
        jbEditar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent ri = new Intent(this, MainActivity.class);
        ri.putExtras(b);
        startActivity(ri);
        finish();
    }

    //Si el usuario presiona el botón de BACK, también regresamos a la otra activity
    @Override
    public void onBackPressed() {
        Intent ri = new Intent(this, MainActivity.class);
        ri.putExtras(b);
        startActivity(ri);
        finish();
    }
}
