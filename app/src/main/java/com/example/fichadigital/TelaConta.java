package com.example.fichadigital;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class TelaConta extends AppCompatActivity {

    private Button btn_criar_personagem, btn_entrar_personagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_conta);

        getSupportActionBar().hide();

        IniciarComponents();

        btn_criar_personagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaConta.this, CriarPersonagem.class);
                startActivity(intent);
                finish();
            }
        });

        btn_entrar_personagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaConta.this, TelaPersonagem.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void IniciarComponents(){
        btn_criar_personagem = findViewById(R.id.btn_criar_personagem);
        btn_entrar_personagem = findViewById(R.id.btn_entrar_personagem);
    }

}