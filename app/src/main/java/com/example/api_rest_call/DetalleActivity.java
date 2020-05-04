package com.example.api_rest_call;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetalleActivity extends AppCompatActivity {

    AutoService autoService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        autoService = APIUtils.getAutoService();

        Bundle extras = getIntent().getExtras();
        String id = extras.getString("id");

/**
 * Retrofit retrofit = new Retrofit.Builder()
 *                 .baseUrl("https://us-central1-be-tp3-a.cloudfunctions.net/")
 *                 .addConverterFactory(GsonConverterFactory.create())
 *                 .build();
 *
 *         // Defnimos la interfaz para que utilice la base retrofit de mi aplicacion ()
 *         AutoService autoService = retrofit.create(AutoService.class);
 */

        RetrofitClient retrofitClient = new RetrofitClient();

        AutoService autoService = APIUtils.getAutoService();

        final Call<Auto> http_call = autoService.getAuto(id);

        http_call.enqueue(new Callback<Auto>() {
            @Override
            public void onResponse(Call<Auto> call, Response<Auto> response) {
                Auto auto = response.body();
                Log.i("Funciona", "OK");

                TextView idAuto = (TextView) findViewById(R.id.auto_id);
                idAuto.setText(auto.getId());
                TextView marca = (TextView) findViewById(R.id.auto_marca);
                marca.setText(auto.getMarca());
                TextView modelo = (TextView) findViewById(R.id.auto_modelo);
                modelo.setText(auto.getModelo());
            }

            @Override
            public void onFailure(Call<Auto> call, Throwable t) {
                Toast.makeText(DetalleActivity.this, "Hubo un error con la llamada a la API", Toast.LENGTH_LONG);
            }
        });

        Button buttonBack = findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        Button btnDel = findViewById(R.id.btnDel);
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    APIUtils.getAutoService().removeAuto("id");
                    http_call.enqueue(new Callback<Auto>() {
                        @Override
                        public void onResponse(Call<Auto> call, Response<Auto> response) {
                            Toast.makeText(DetalleActivity.this, "Auto Borrado con éxito!", Toast.LENGTH_SHORT);
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<Auto> call, Throwable t) {
                            Toast.makeText(DetalleActivity.this, "Hubo un error borrando un auto", Toast.LENGTH_LONG);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }


}
