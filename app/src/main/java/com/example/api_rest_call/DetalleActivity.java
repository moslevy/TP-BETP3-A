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
import retrofit2.http.DELETE;
import retrofit2.http.Path;


public class DetalleActivity extends AppCompatActivity {

    Auto miAuto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        Bundle extras = getIntent().getExtras();

        String id = extras.getString("id");

        AutoService autoService = APIUtils.getAutoService();

        final Call<Auto> http_call = autoService.getAuto(id);
        final Call<Auto> http_del = autoService.removeAuto(id);

//      getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        final Button buttonBack = findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    backToStart();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Button buttonDelete = (Button) findViewById(R.id.btnDel);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    http_del.enqueue(new Callback<Auto>() {
                        @Override
                        public void onResponse(Call<Auto> call, Response<Auto> response) {
                            Toast.makeText(DetalleActivity.this, "Deleted OK", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<Auto> call, Throwable t) {
                            Toast.makeText(DetalleActivity.this, "Error Deleting", Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                backToStart();
            }
        });


    }

    private void backToStart() {
        Intent intent = new Intent(DetalleActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
