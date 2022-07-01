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

public class Notas extends AppCompatActivity {

    private Button btn_criar_notas, btn_atualizar_notas;
    private EditText edit_nota;


    String usuarioID;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);

        IniciarComponents();

        btn_criar_notas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SalvarDadosPersonagem();
            }
        });

        btn_atualizar_notas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AtualizarDadosPersonagem();
            }
        });

        spinner = (Spinner) findViewById(R.id.spinnerPersonagem);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.escolhas, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        AdapterView.OnItemSelectedListener escolha = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 1:
                        Intent it1 = new Intent(Notas.this, Atributos.class);
                        startActivity(it1);
                        finish();
                        break;
                    case 2:
                        Intent it2 = new Intent(Notas.this, Equipamentos.class);
                        startActivity(it2);
                        finish();
                        break;
                    case 3:
                        Intent it3 = new Intent(Notas.this, Pericias.class);
                        startActivity(it3);
                        finish();
                        break;
                    case 4:
                        Intent it4 = new Intent(Notas.this, Poderes.class);
                        startActivity(it4);
                        finish();
                        break;
                    case 5:
                        Intent it5 = new Intent(Notas.this, Magias.class);
                        startActivity(it5);
                        finish();
                        break;
                    case 6:
                        Intent it6 = new Intent(Notas.this, Notas.class);
                        startActivity(it6);
                        finish();
                        break;
                    case 7:
                        Intent it7 = new Intent(Notas.this, TelaPersonagem.class);
                        startActivity(it7);
                        finish();
                        break;
                    case 8:
                        Intent it8 = new Intent(Notas.this, TelaPrincipal.class);
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

        DocumentReference documentReference = db.collection("Notas").document(usuarioID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot != null) {
                    edit_nota.setText(documentSnapshot.getString("nota"));

                }
            }
        });
    }

    private void IniciarComponents(){
        edit_nota = findViewById(R.id.edit_notas);
        btn_criar_notas = findViewById(R.id.btn_criar_notas);
        btn_atualizar_notas = findViewById(R.id.btn_atualizar_notas);
    }


    private void SalvarDadosPersonagem(){
        String nota = edit_nota.getText().toString();

        Map<String, Object> notas = new HashMap<>();
        notas.put("nota", nota);


        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Notas").document(usuarioID);
        documentReference.set(notas).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    private void AtualizarDadosPersonagem(){
        String nota = edit_nota.getText().toString();

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Task<Void> documentReference = db.collection("Notas").document(usuarioID)
                .update("nota", nota).addOnSuccessListener(new OnSuccessListener<Void>() {
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