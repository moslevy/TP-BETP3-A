package com.example.api_rest_call;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ListAdapter adaptador;
    ArrayList<Auto> data_autos = new ArrayList<>();
    //Button btnAddAuto;
    //Button btnGetAutosList;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adaptador = new AutoAdapter(this, data_autos);

        Button btnGetAutosList = (Button) findViewById(R.id.btnGetAutosList);
        Button btnAddAuto = (Button) findViewById(R.id.btnAddAuto);
        btnGetAutosList.setOnClickListener(this);
        btnAddAuto.setOnClickListener(this);

//        btnGetAutosList.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                getAutosList();
//            }
//        });

        listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(adaptador);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetalleActivity.class);
                intent.putExtra("id", data_autos.get(position).getId());
                Log.i("DATA:", data_autos.get(position).getId() + " - " + data_autos.get(position).getModelo());
                startActivity(intent);
            }

            ;
        });


//        btnAddAuto.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
////                    Intent intent = new Intent(MainActivity.this, AutoActivity.class);
////                    startActivity(intent);
//            }
//        });

//        Deshabilité la vista de la lista automatica ya que implementé
//        un boton para que mostrar la lista cuando el usuario presiona el boton "Get Autos' List"
//        this.getAutosList();
    }



    public void getAutosList() {
        /**
         * Reemplace este codigo en una clase llamada RetrofitClient
         * // Establezco una relacion de mi app con este endpoint:
         *       Retrofit retrofit = new Retrofit.Builder()
         *                .baseUrl("https://us-central1-be-tp3-a.cloudfunctions.net/")
         *                .addConverterFactory(GsonConverterFactory.create())
         *                .build();
         */

        /**
         * Reemplace este codigo en una clase llamada APIUtils
         * // Defnimos la interfaz para que utilice la base retrofit de mi aplicacion ()
         *        AutoService autoService = retrofit.create(AutoService.class);
         */

        //RetrofitClient retrofitClient = new RetrofitClient();

        AutoService autoService = APIUtils.getAutoService();

        Call<List<Auto>> http_call = autoService.getAutos();

        http_call.enqueue(new Callback<List<Auto>>() {
            @Override
            public void onResponse(Call<List<Auto>> call, Response<List<Auto>> response) {
                data_autos.clear();
                for (Auto auto : response.body()) {
                    data_autos.add(auto);
                }
                // Aviso al base adapter que cambio mi set de datos.
                // Renderizacion general de mi ListView
                ((BaseAdapter) adaptador).notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Auto>> call, Throwable t) {
                // SI el servidor o la llamada no puede ejecutarse, muestro un mensaje de eror:
                Toast.makeText(MainActivity.this, "Hubo un error con la llamada a la API", Toast.LENGTH_LONG);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGetAutosList:
                Toast.makeText(this, "Button Get Autos List", Toast.LENGTH_SHORT).show();
                getAutosList();
                break;
            case R.id.btnAddAuto:
                Toast.makeText(this, "Button AddAuto clicekd", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, AutoActivity.class);
                break;
        }
    }
}
