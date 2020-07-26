package com.example.contactos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends Activity implements View.OnClickListener {

    EditText jNombre, jTelefono, jEmail, jDesc;
    TextInputLayout jNombreLayout, jTelefonoLayout, jEmailLayout, jDescLayout;
    DatePicker datepicker;
    Button jSiguiente;
    Date nuevaFecha;
    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //views
        jNombre = findViewById(R.id.xNombreCompleto);
        jTelefono = findViewById(R.id.xTelefono);
        jEmail = findViewById(R.id.xEmail);
        jDesc = findViewById(R.id.xDescripcion);
        jNombreLayout = findViewById(R.id.xNombreCompletoLayout);
        jTelefonoLayout = findViewById(R.id.xTelefonoLayout);
        jEmailLayout = findViewById(R.id.xEmailLayout);
        jDescLayout = findViewById(R.id.xDescripcionLayout);
        datepicker = findViewById(R.id.xDatePicker);
        jSiguiente = findViewById(R.id.xSiguiente);

        //PRIMERO se debe revisar si existe un intent, que indica que los datos ya deben configurarse
        Intent basei = getIntent();
        if (basei != null){
            b = basei.getExtras();
            if (b != null){
                jNombre.setText(b.getString("Nombre"));
                //Configuramos la fecha en el datepicker
                try {
                    //aqui convertimos el string que recibimos de DetalleContacto a un objeto de tipo Date
                    nuevaFecha = stringToDate(b.getString("Fecha"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //Obtenemos una instancia de Calendar y la asignamos a la fecha que obtuvimos previamente
                Calendar c = Calendar.getInstance();
                c.setTime(nuevaFecha);

                //Ahora ya podemos obtener el día, mes y año
                int dia = c.get(Calendar.DAY_OF_MONTH);
                int mes = c.get(Calendar.MONTH);
                int anio = c.get(Calendar.YEAR);

                //Configuramos el datepicker para que se muestre con los valores obtenidos
                datepicker.updateDate(anio, mes, dia);

                jTelefono.setText(b.getString("Tel"));
                jEmail.setText(b.getString("Email"));
                jDesc.setText(b.getString("Desc"));
            }
        }

        //capturar evento de clic
        jSiguiente.setOnClickListener(this);
    }

    private boolean checkFields(EditText[] edittexts){
        boolean flag = true;
        //Para cada uno de los campos, verificar que no esten vacíos
        for (int i = 0; i < edittexts.length; i++){
            EditText current = edittexts[i];
            TextInputLayout til = (TextInputLayout) current.getParent().getParent();
            if(current.getText().toString().equalsIgnoreCase("")){
                //si el campo esta vacio, colocar un mensaje de error en ese campo
                til.setErrorEnabled(true);
                til.setError("Este campo es obligatorio");
                flag = false;
            }
            else if (til.isErrorEnabled()){
                til.setError(null);
            }
        }
        return flag;
    }

    private static java.util.Date getDateFromDatePicker(DatePicker datePicker){
        //Obtenemos dia, mes y año del datepicker
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        //Damos formato de fecha en calendario
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    private Date stringToDate(String fecha) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return sdf.parse(fecha);
    }

    @Override
    public void onClick(View v) {
        //Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show();
        //Verificar que ningún campo del formulario este vacío
        if (checkFields(new EditText[]{jNombre, jTelefono, jEmail, jDesc})) {

            //Capturando fecha y convirtiendola a String
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            java.util.Date fecha = getDateFromDatePicker(datepicker);
            String fechaEnString = df.format(fecha);

            //Creando bundle y capturando valores
            b = new Bundle();
            b.putString("Nombre", jNombre.getText().toString());
            b.putString("Fecha", fechaEnString);
            b.putString("Tel", jTelefono.getText().toString());
            b.putString("Email", jEmail.getText().toString());
            b.putString("Desc", jDesc.getText().toString());

            //Usamos un intent para llamar a la activity DetalleContacto
            Intent i = new Intent(this, DetalleContacto.class);

            //Agregamos el bundle al intent
            i.putExtras(b);

            //Iniciamos actividad que muestra el contacto a detalle, le pasamos toda la información del contacto en un bundle
            startActivity(i);

            //Terminamos la activity una vez iniciada la activity detalle contacto
            finish();
        }
    }
}
