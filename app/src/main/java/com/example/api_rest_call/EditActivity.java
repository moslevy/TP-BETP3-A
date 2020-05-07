package com.example.api_rest_call;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditActivity extends AppCompatActivity {
    private Auto miAuto;
    private EditText marca;
    private EditText modelo;
    private Button buttonUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setItemView();
        String autoId = fetchAutoId();
        getAuto(autoId);
        setTextContent();
        buttonUpdate.setOnClickListener(setEditClick());
    }

    private View.OnClickListener setEditClick() {
        View.OnClickListener editClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (miAuto != null) {
                    new AlertDialog.Builder(EditActivity.this)
                            .setTitle("Edit Entry")
                            .setMessage("Are you sure you want to edit this entry?")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with edit operation
                                    updateTextContent();
                                    editAuto(miAuto);
                                    backToStart();
                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        };
        return editClickListener;
    }

    private void setItemView() {

        marca = (EditText) findViewById(R.id.auto_marca);
        modelo = (EditText) findViewById(R.id.auto_modelo);
        buttonUpdate = (Button) findViewById(R.id.button_update);

    }

    private void updateTextContent() {
        if (miAuto != null) {
            miAuto.setMarca(marca.getText().toString());
            miAuto.setModelo(modelo.getText().toString());
        }
    }

    private void setTextContent() {
        if (miAuto != null) {
            marca.setText(miAuto.getMarca());
            modelo.setText(miAuto.getModelo());

//            TextView marca = (TextView) findViewById(R.id.auto_marca);
//            marca.setText(miAuto.getMarca());
//            TextView modelo = (TextView) findViewById(R.id.auto_modelo);
//            modelo.setText(miAuto.getModelo());
        }
    }

    public String fetchAutoId() {
        Bundle b = getIntent().getExtras();
        String value = "-1"; // or other values
        if (b != null)
            value = b.getString("id");
        return value;
    }

    public void editAuto(Auto auto) {
        // Establezco una relacion de mi app con este endpoint:
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://us-central1-be-tp3-a.cloudfunctions.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Defnimos la interfaz para que utilice la base retrofit de mi aplicacion ()
        AutoService autoService = retrofit.create(AutoService.class);
        Call<Auto> http_call = autoService.updateAuto(auto.getId(), auto);
        http_call.enqueue(new Callback<Auto>() {
            @Override
            public void onResponse(Call<Auto> call, Response<Auto> response) {
                //Toast.makeText(EditActivity.this,"Success", Toast.LENGTH_LONG);
                backToStart();
            }

            @Override
            public void onFailure(Call<Auto> call, Throwable t) {
                Toast.makeText(EditActivity.this, "Hubo un error con la llamada a la API", Toast.LENGTH_LONG);
            }
        });
    }

    public void getAuto(String id) {
//        // Establezco una relacion de mi app con este endpoint:
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://us-central1-be-tp3-a.cloudfunctions.net/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        // Defnimos la interfaz para que utilice la base retrofit de mi aplicacion ()
//        AutoService autoService = retrofit.create(AutoService.class);

        Call<Auto> http_call = APIUtils.getAutoService().getAuto(id);
        http_call.enqueue(new Callback<Auto>() {
            @Override
            public void onResponse(Call<Auto> call, Response<Auto> response) {
                miAuto = response.body();
                setTextContent();
            }

            @Override
            public void onFailure(Call<Auto> call, Throwable t) {
                Toast.makeText(EditActivity.this, "Hubo un error con la llamada a la API", Toast.LENGTH_LONG);
            }
        });
    }

    private void backToStart() {
        Intent intent = new Intent(EditActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}