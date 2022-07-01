package com.example.fichadigital;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CriarPersonagem extends AppCompatActivity {

    private Button btn_foto_personagem, btn_criar;
    private EditText edit_nome, edit_classe, edit_raca, edit_tendencia, edit_divindade, edit_origem;
    String[] mensagens = {"Preencha todos os campos", "Criado realizado com sucesso"};
    String usuarioID;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Uri mSelectedUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_personagem);

        IniciarComponents();

        btn_criar.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {

                SalvarDadosPersonagem();

                Intent intent = new Intent(CriarPersonagem.this, TelaPersonagem.class);
                startActivity(intent);
                finish();

                /*
                String nome = edit_nome.getText().toString();
                String classe = edit_classe.getText().toString();
                String raca = edit_raca.getText().toString();
                String tendencia = edit_tendencia.getText().toString();
                String divindade = edit_divindade.getText().toString();
                String origem = edit_origem.getText().toString();


                if(nome.isEmpty() || classe.isEmpty() || raca.isEmpty() || tendencia.isEmpty()
                        || divindade.isEmpty() || origem.isEmpty()){
                    Snackbar snackbar = Snackbar.make(v, mensagens[0], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();

                }else{
                    SalvarDadosPersonagem();
                    Intent intent = new Intent(CriarPersonagem.this, TelaPersonagem.class);
                    startActivity(intent);
                    finish();
                }*/
            }
        });

    }

    private void IniciarComponents(){
        edit_nome = findViewById(R.id.edit_nome);
        edit_classe = findViewById(R.id.edit_classe);
        edit_raca = findViewById(R.id.edit_raca);
        edit_tendencia = findViewById(R.id.edit_tendencao);
        edit_divindade = findViewById(R.id.edit_divindade);
        edit_origem = findViewById(R.id.edit_origem);
        btn_criar = findViewById(R.id.btn_criar);
    }



    private void SalvarDadosPersonagem(){
        String nome = edit_nome.getText().toString();
        String classe = edit_classe.getText().toString();
        String raca = edit_raca.getText().toString();
        String tendencia = edit_tendencia.getText().toString();
        String divindade = edit_divindade.getText().toString();
        String origem = edit_origem.getText().toString();

        Map<String, Object> personagem = new HashMap<>();
        personagem.put("nome", nome);
        personagem.put("classe", classe);
        personagem.put("raça", raca);
        personagem.put("tendência", tendencia);
        personagem.put("divindade", divindade);
        personagem.put("origem", origem);

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Personagens").document(usuarioID);
        documentReference.set(personagem).addOnSuccessListener(new OnSuccessListener<Void>() {
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


}