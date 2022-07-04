package com.example.fichadigital;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class Magias extends AppCompatActivity {

    private Button btn_criar_magias, btn_atualizar_magias;
    private EditText edit_magias;

    String usuarioID;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magias);


        IniciarComponents();


        btn_criar_magias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SalvarDadosMagias();
            }
        });

        btn_atualizar_magias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AtualizarDadosMagias();
            }
        });

        spinner = (Spinner) findViewById(R.id.spinnerMagia);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.escolhas, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        AdapterView.OnItemSelectedListener escolha = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 1:
                        Intent it1 = new Intent(Magias.this, Musica.class);
                        startActivity(it1);
                        finish();
                        break;

                    case 2:
                        Intent it2 = new Intent(Magias.this, Equipamentos.class);
                        startActivity(it2);
                        finish();
                        break;
                    case 3:
                        Intent it3 = new Intent(Magias.this, Pericias.class);
                        startActivity(it3);
                        finish();
                        break;
                    case 4:
                        Intent it4 = new Intent(Magias.this, Poderes.class);
                        startActivity(it4);
                        finish();
                        break;
                    case 5:
                        Intent it5 = new Intent(Magias.this, Magias.class);
                        startActivity(it5);
                        finish();
                        break;
                    case 6:
                        Intent it6 = new Intent(Magias.this, Notas.class);
                        startActivity(it6);
                        finish();
                        break;
                    case 7:
                        Intent it7 = new Intent(Magias.this, TelaPersonagem.class);
                        startActivity(it7);
                        finish();
                        break;
                    case 8:
                        Intent it8 = new Intent(Magias.this, TelaPrincipal.class);
                        startActivity(it8);
                        finish();
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        spinner.setOnItemSelectedListener(escolha);

    }

    @Override
    protected void onStart() {
        super.onStart();


        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Magias").document(usuarioID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot != null) {
                    edit_magias.setText(documentSnapshot.getString("magias"));

                }
            }
        });
    }

    private void IniciarComponents(){
        edit_magias = findViewById(R.id.edit_magias);
        btn_criar_magias = findViewById(R.id.btn_criar_magia);
        btn_atualizar_magias = findViewById(R.id.btn_atualizar_magia);
    }

    private void SalvarDadosMagias(){

        String magia = edit_magias.getText().toString();

        Map<String, Object> magias = new HashMap<>();
        magias.put("magias", magia);


        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Magias").document(usuarioID);
        documentReference.set(magias).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("db","Sucesso ao salvar os dados");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("db","Erro ao salvar os dados" + e.toString());
                    }
                });

    }

    private void AtualizarDadosMagias(){
        String magia = edit_magias.getText().toString();

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Task<Void> documentReference = db.collection("Magias").document(usuarioID)
                .update("magias", magia).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("db","Sucesso ao atualizar os dados");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("db","Erro ao atualizar os dados" + e.toString());
                    }
                });
    }
}