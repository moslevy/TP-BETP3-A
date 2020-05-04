package com.example.api_rest_call;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;

public class AutoActivity extends AppCompatActivity {

//    EditText txtId;
    EditText txtMarca;
    EditText txtModelo;
    Auto auto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto);


//        final EditText id = (EditText) findViewById(R.id.txtId);
        final EditText marca = (EditText) findViewById(R.id.txtMarca);
        final EditText modelo = (EditText) findViewById(R.id.txtModelo);

        Button createAutoButton = (Button) findViewById(R.id.btnSave);
        createAutoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auto auto = new Auto(
                        marca.getText().toString(),
                        modelo.getText().toString()
                );
                try{
                    Call<Void> http_call =APIUtils.getAutoService().createAuto(auto);
                    http_call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(AutoActivity.this, "Se creo el auto exitosamente!", Toast.LENGTH_SHORT);
                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(AutoActivity.this, "Hubo un error con la creacion del auto", Toast.LENGTH_LONG);
                        }
                    });
                    Log.i("success", ":)");

                }catch (Exception e){
                    e.printStackTrace();
                    Log.i("error", e.getMessage());
                }
                clean();

            }
        });
    }



    private void deleteDeal() {
        if (auto == null) {
            Toast.makeText(this, "Please save the deal before deleting", Toast.LENGTH_SHORT).show();
            return;
        }
        //mDatabaseReference.child(deal.getId()).removeValue();
    }

    private void backToList() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void clean() {
//        txtId.setText("");
        txtMarca.setText("");
        txtModelo.setText("");
        txtMarca.requestFocus();
//        txtId.requestFocus();
    }

    private void enableEditTexts(boolean isEnabled) {
//        txtId.setEnabled(isEnabled);
        txtMarca.setEnabled(isEnabled);
        txtModelo.setEnabled(isEnabled);
    }
}
