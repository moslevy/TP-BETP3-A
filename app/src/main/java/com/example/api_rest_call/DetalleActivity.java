package com.example.api_rest_call;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetalleActivity extends AppCompatActivity {
    private TextView idAuto, marca, modelo;
    private Auto miAuto;
    private Button btnEditAuto, btnDeleteAuto;

//    Bundle bundle = getIntent().getExtras();
//    String id = bundle.getString("id");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        String miAutoId = this.fetchAutoId();
        this.getAuto(miAutoId);
        this.setItemView();

//        backToMain = (Button) findViewById(R.id.button_back);
//        btnBackToMain.setOnClickListener(setBackClick());

//        editAuto = (Button) findViewById(R.id.button_update);
        btnEditAuto.setOnClickListener(setEditClick());

//        deleteAuto = (Button) findViewById(R.id.btnDel);
        btnDeleteAuto.setOnClickListener(setDeleteClick());

    }

    private void setItemView() {
        setContentView(R.layout.activity_detalle);
        modelo = (TextView) findViewById(R.id.auto_modelo);
        marca = (TextView) findViewById(R.id.auto_marca);
        btnDeleteAuto = (Button) findViewById(R.id.btnDel);
        btnEditAuto = (Button) findViewById(R.id.button_update);
    }

    private void updateTextContent() {
        if (miAuto != null) {
            marca.setText(miAuto.getMarca());
            modelo.setText(miAuto.getModelo());
        }
    }

    public String fetchAutoId() {
        Bundle b = getIntent().getExtras();
        String value = "-1"; // or other values
        if (b != null)
            value = b.getString("id");
        return value;
    }

    public void getAuto(String id) {
        Call<Auto> http_call = APIUtils.getAutoService().getAuto(id);
        http_call.enqueue(new Callback<Auto>() {
            @Override
            public void onResponse(Call<Auto> call, Response<Auto> response) {
                miAuto = response.body();
                updateTextContent();
            }

            @Override
            public void onFailure(Call<Auto> call, Throwable t) {
                Toast.makeText(DetalleActivity.this, "Hubo un error con la llamada a la API", Toast.LENGTH_LONG);
            }
        });
    }



//    private View.OnClickListener setBackClick() {
//        View.OnClickListener backClickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                goToMainActivity();
//            }
//        };
//        return backClickListener;
//    }

    private void backToStart() {
        Intent intent = new Intent(DetalleActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void onBackPressed(){
        backToStart();
    }

    private View.OnClickListener setEditClick() {
        View.OnClickListener editClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (miAuto != null) {
                    Intent intent = new Intent(DetalleActivity.this, EditActivity.class);
                    Bundle b = new Bundle();
                    b.putString("id", miAuto.getId());
                    intent.putExtras(b);
                    startActivity(intent);
                    finish();
                }
            }
        };
        return editClickListener;
    }


    private View.OnClickListener setDeleteClick() {
        View.OnClickListener deleteClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (miAuto != null) {
                    new AlertDialog.Builder(DetalleActivity.this)
                            .setTitle("Delete Entry")
                            .setMessage("Are you sure you want to delete this entry?")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteAuto(miAuto.getId());// Continue with delete operation
                                    updateTextContent();
                                }
                            })
                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        };
        return deleteClickListener;
    }

    private void deleteAuto(String id) {
        Call<Void> http_delete = APIUtils.getAutoService().removeAuto(id);
        http_delete.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(DetalleActivity.this, "Deleted OK", Toast.LENGTH_LONG).show();
                backToStart();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(DetalleActivity.this, "Error deleting auto", Toast.LENGTH_LONG).show();
            }
        });
    }



    //    public void showAuto() {
//
//        setContentView(R.layout.activity_detalle);
//        idAuto = findViewById(R.id.auto_id);
//        idAuto.setText(miAuto.getId());
//        marca = findViewById(R.id.auto_marca);
//        marca.setText(miAuto.getMarca());
//        modelo = findViewById(R.id.auto_modelo);
//        modelo.setText(miAuto.getModelo());
//    }

    //    Button buttonUpdate = (Button) findViewById(R.id.button_update);
//        buttonUpdate.setOnClickListener(new View.OnClickListener()
//
//    {
//        @Override
//        public void onClick (View v){
//
//        Toast.makeText(DetalleActivity.this, "Updated OK", Toast.LENGTH_LONG).show();
//        Intent intent = new Intent(DetalleActivity.this, EditActivity.class);
//        intent.putExtra("id", miAuto.getId());
//        startActivity(intent);
//
//    }
//    });

//    Button buttonDelete = (Button) findViewById(R.id.btnDel);
//        buttonDelete.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            try {
//                Call<Void> http_delete = APIUtils.getAutoService().removeAuto(id);
//                http_delete.enqueue(new Callback<Void>() {
//                    @Override
//                    public void onResponse(Call<Void> call, Response<Void> response) {
//                        Toast.makeText(DetalleActivity.this, "Deleted OK", Toast.LENGTH_LONG).show();
//                        goToMainActivity();
//                    }
//
//                    @Override
//                    public void onFailure(Call<Void> call, Throwable t) {
//                        Toast.makeText(DetalleActivity.this, "Error deleting auto", Toast.LENGTH_LONG).show();
//                    }
//                });
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    });
//
//    Call<Auto> http_call = APIUtils.getAutoService().getAuto(miAutoId);
//        http_call.enqueue(new Callback<Auto>() {
//        @Override
//        public void onResponse(Call<Auto> call, Response<Auto> response) {
//            Auto auto = response.body();
//            Log.i("Funciona", "OK");
//
//            idAuto = findViewById(R.id.auto_id);
//            idAuto.setText(auto.getId());
//            marca = findViewById(R.id.auto_marca);
//            marca.setText(auto.getMarca());
//            modelo = findViewById(R.id.auto_modelo);
//            modelo.setText(auto.getModelo());
//        }
//
//        @Override
//        public void onFailure(Call<Auto> call, Throwable t) {
//            Toast.makeText(DetalleActivity.this, "Hubo un error con la llamada a la API", Toast.LENGTH_LONG);
//        }
//    });

    //

}

