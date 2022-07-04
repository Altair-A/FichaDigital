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

public class Equipamentos extends AppCompatActivity {

    private Button btn_criar_equipamento, btn_atualizar_equipamento;
    private EditText edit_item1, edit_item2, edit_item3, edit_item4, edit_item5;
    private EditText edit_peso1, edit_peso2, edit_peso3, edit_peso4, edit_peso5;

    String usuarioID;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipamentos);


        IniciarComponents();


        btn_criar_equipamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SalvarDadosEquipamento();
            }
        });

        btn_atualizar_equipamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AtualizarEquipamentos();
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
                        Intent it1 = new Intent(Equipamentos.this, Musica.class);
                        startActivity(it1);
                        finish();
                        break;
                    case 2:
                        Intent it2 = new Intent(Equipamentos.this, Equipamentos.class);
                        startActivity(it2);
                        finish();
                        break;
                    case 3:
                        Intent it3 = new Intent(Equipamentos.this, Pericias.class);
                        startActivity(it3);
                        finish();
                        break;
                    case 4:
                        Intent it4 = new Intent(Equipamentos.this, Poderes.class);
                        startActivity(it4);
                        finish();
                        break;
                    case 5:
                        Intent it5 = new Intent(Equipamentos.this, Magias.class);
                        startActivity(it5);
                        finish();
                        break;
                    case 6:
                        Intent it6 = new Intent(Equipamentos.this, Notas.class);
                        startActivity(it6);
                        finish();
                        break;
                    case 7:
                        Intent it7 = new Intent(Equipamentos.this, TelaPersonagem.class);
                        startActivity(it7);
                        finish();
                        break;
                    case 8:
                        Intent it8 = new Intent(Equipamentos.this, TelaPrincipal.class);
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

        DocumentReference documentReference = db.collection("Equipamentos").document(usuarioID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot != null) {
                    edit_item1.setText(documentSnapshot.getString("item1"));
                    edit_item2.setText(documentSnapshot.getString("item2"));
                    edit_item3.setText(documentSnapshot.getString("item3"));
                    edit_item4.setText(documentSnapshot.getString("item4"));
                    edit_item5.setText(documentSnapshot.getString("item5"));
                    edit_peso1.setText(documentSnapshot.getString("peso1"));
                    edit_peso2.setText(documentSnapshot.getString("peso2"));
                    edit_peso3.setText(documentSnapshot.getString("peso3"));
                    edit_peso4.setText(documentSnapshot.getString("peso4"));
                    edit_peso5.setText(documentSnapshot.getString("peso5"));

                }
            }
        });
    }

    private void IniciarComponents(){
        edit_item1 = findViewById(R.id.edit_item1);
        edit_item2 = findViewById(R.id.edit_item2);
        edit_item3 = findViewById(R.id.edit_item3);
        edit_item4 = findViewById(R.id.edit_item4);
        edit_item5 = findViewById(R.id.edit_item5);
        edit_peso1 = findViewById(R.id.edit_Peso1);
        edit_peso2 = findViewById(R.id.edit_Peso2);
        edit_peso3 = findViewById(R.id.edit_Peso3);
        edit_peso4 = findViewById(R.id.edit_Peso4);
        edit_peso5 = findViewById(R.id.edit_Peso5);
        btn_criar_equipamento = findViewById(R.id.btn_criar_equipamento);
        btn_atualizar_equipamento = findViewById(R.id.btn_atualizar_equipamentos);
    }


    private void SalvarDadosEquipamento(){
        String item1 = edit_item1.getText().toString();
        String item2 = edit_item2.getText().toString();
        String item3 = edit_item3.getText().toString();
        String item4 = edit_item4.getText().toString();
        String item5 = edit_item5.getText().toString();
        String peso1 = edit_peso1.getText().toString();
        String peso2 = edit_peso2.getText().toString();
        String peso3 = edit_peso3.getText().toString();
        String peso4 = edit_peso4.getText().toString();
        String peso5 = edit_peso5.getText().toString();

        Map<String, Object> equipamentos = new HashMap<>();
        equipamentos.put("item1", item1);
        equipamentos.put("item2", item2);
        equipamentos.put("item3", item3);
        equipamentos.put("item4", item4);
        equipamentos.put("item5", item5);
        equipamentos.put("peso1", peso1);
        equipamentos.put("peso2", peso2);
        equipamentos.put("peso3", peso3);
        equipamentos.put("peso4", peso4);
        equipamentos.put("peso5", peso5);


        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Equipamentos").document(usuarioID);
        documentReference.set(equipamentos).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("db","Sucesso ao equipamentos os dados");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("db","Erro ao equipamentos os dados" + e.toString());
                    }
                });

    }

    private void AtualizarEquipamentos(){
        String item1 = edit_item1.getText().toString();
        String item2 = edit_item2.getText().toString();
        String item3 = edit_item3.getText().toString();
        String item4 = edit_item4.getText().toString();
        String item5 = edit_item5.getText().toString();
        String peso1 = edit_peso1.getText().toString();
        String peso2 = edit_peso2.getText().toString();
        String peso3 = edit_peso3.getText().toString();
        String peso4 = edit_peso4.getText().toString();
        String peso5 = edit_peso5.getText().toString();


        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Task<Void> documentReference = db.collection("Equipamentos").document(usuarioID)
                .update("item1", item1, "item2", item2, "item3", item3, "item4", item4, "item5", item5,
                        "peso1", peso1, "peso2", peso2, "peso3", peso3, "peso4", peso4, "peso5", peso5).addOnSuccessListener(new OnSuccessListener<Void>() {
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