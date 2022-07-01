package com.example.fichadigital;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class TelaPersonagem extends AppCompatActivity {

    private Spinner spinner;
    private TextView nomePers, classePers, racaPers, tendenciaPers, divindadePers, origemPers;
    private EditText edit_for, edit_des, edit_con, edit_int, edit_sab, edit_car;
    private TextView text_for, text_des, text_con, text_int, text_sab, text_car;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String usuarioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_personagem);

        IniciarComponents();

        spinner = (Spinner) findViewById(R.id.spinnerPersonagem);


        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.escolhas, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        AdapterView.OnItemSelectedListener escolha = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 1:
                        Intent it1 = new Intent(TelaPersonagem.this, Atributos.class);
                        startActivity(it1);
                        finish();
                        break;
                    case 2:
                        Intent it2 = new Intent(TelaPersonagem.this, Equipamentos.class);
                        startActivity(it2);
                        finish();
                        break;
                    case 3:
                        Intent it3 = new Intent(TelaPersonagem.this, Pericias.class);
                        startActivity(it3);
                        finish();
                        break;
                    case 4:
                        Intent it4 = new Intent(TelaPersonagem.this, Poderes.class);
                        startActivity(it4);
                        finish();
                        break;
                    case 5:
                        Intent it5 = new Intent(TelaPersonagem.this, Magias.class);
                        startActivity(it5);
                        finish();
                        break;
                    case 6:
                        Intent it6 = new Intent(TelaPersonagem.this, Notas.class);
                        startActivity(it6);
                        finish();
                        break;
                    case 7:
                        Intent it7 = new Intent(TelaPersonagem.this, TelaConta.class);
                        startActivity(it7);
                        finish();
                        break;
                    case 8:
                        Intent it8 = new Intent(TelaPersonagem.this, TelaPrincipal.class);
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


        edit_for.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalculaFor();
            }
        });

        edit_des.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalculaDes();
            }
        });

        edit_con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalculaCon();
            }
        });

        edit_int.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalculaInt();
            }
        });

        edit_sab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalculaSab();
            }
        });

        edit_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalculaCar();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();


        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Personagens").document(usuarioID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if(documentSnapshot != null){
                    nomePers.setText(documentSnapshot.getString("nome"));
                    racaPers.setText(documentSnapshot.getString("raça"));
                    classePers.setText(documentSnapshot.getString("classe"));
                    tendenciaPers.setText(documentSnapshot.getString("tendência"));
                    divindadePers.setText(documentSnapshot.getString("divindade"));
                    origemPers.setText(documentSnapshot.getString("origem"));
                }
            }
        });

        DocumentReference documentReference2 = db.collection("Forca").document(usuarioID);
        documentReference2.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                edit_for.setText(documentSnapshot.getString("forca"));
            }
        });

        DocumentReference documentReference3 = db.collection("ForcaBonus").document(usuarioID);
        documentReference3.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                text_for.setText(documentSnapshot.getString("forcaBonus"));
            }
        });

        DocumentReference documentReference4 = db.collection("Destrezas").document(usuarioID);
        documentReference4.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                edit_des.setText(documentSnapshot.getString("destreza"));
            }
        });

        DocumentReference documentReference5 = db.collection("destrezasBonus").document(usuarioID);
        documentReference5.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                text_des.setText(documentSnapshot.getString("destrezaBonus"));
            }
        });

        DocumentReference documentReference6 = db.collection("Constituicoes").document(usuarioID);
        documentReference6.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                edit_con.setText(documentSnapshot.getString("constituicao"));
            }
        });

        DocumentReference documentReference7 = db.collection("constituicaoBonus").document(usuarioID);
        documentReference7.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                text_con.setText(documentSnapshot.getString("constituicaoBonus"));
            }
        });

        DocumentReference documentReference8 = db.collection("Inteligencias").document(usuarioID);
        documentReference8.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                edit_int.setText(documentSnapshot.getString("inteligencia"));
            }
        });

        DocumentReference documentReference9 = db.collection("inteligenciaBonus").document(usuarioID);
        documentReference9.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                text_int.setText(documentSnapshot.getString("inteligenciaBonus"));
            }
        });


        DocumentReference documentReference12 = db.collection("Sabedorias").document(usuarioID);
        documentReference12.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                edit_sab.setText(documentSnapshot.getString("sabedoria"));
            }
        });

        DocumentReference documentReference13 = db.collection("sabedoriaBonus").document(usuarioID);
        documentReference13.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                text_sab.setText(documentSnapshot.getString("sabedoriaBonus"));
            }
        });

        DocumentReference documentReference14 = db.collection("Carismas").document(usuarioID);
        documentReference14.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                edit_car.setText(documentSnapshot.getString("carisma"));
            }
        });

        DocumentReference documentReference15 = db.collection("carismaBonus").document(usuarioID);
        documentReference15.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                text_car.setText(documentSnapshot.getString("carismaBonus"));
            }
        });
    }


    private void IniciarComponents(){
        nomePers = findViewById(R.id.textNome);
        classePers = findViewById(R.id.textClasse);
        racaPers = findViewById(R.id.textRaca);
        tendenciaPers = findViewById(R.id.textTendencao);
        divindadePers = findViewById(R.id.textDivindade);
        origemPers = findViewById(R.id.textOrigem);

        edit_for = findViewById(R.id.edt_for);
        edit_des = findViewById(R.id.edt_des);
        edit_con = findViewById(R.id.edt_con);
        edit_int = findViewById(R.id.edt_int);
        edit_sab = findViewById(R.id.edt_sab);
        edit_car = findViewById(R.id.edt_car);

        text_for = findViewById(R.id.edt_for2);
        text_des = findViewById(R.id.edt_des2);
        text_con = findViewById(R.id.edt_con2);
        text_int = findViewById(R.id.edt_int2);
        text_sab = findViewById(R.id.edt_sab2);
        text_car = findViewById(R.id.edt_car2);

    }

    private void CalculaAtributo(){
        String forca2 = edit_for.getText().toString();
        String forca = edit_for.getText().toString();

        Integer forDamage = Integer.parseInt(forca2);

        forDamage = (forDamage - 10)/2;

        String forc = forDamage.toString();

        String dest2 = edit_des.getText().toString();
        String destreza = edit_des.getText().toString();

        Integer desDamage = Integer.parseInt(dest2);

        desDamage = (desDamage - 10)/2;

        String des = desDamage.toString();

        String con2 = edit_con.getText().toString();
        String constituicao = edit_con.getText().toString();

        Integer conDamage = Integer.parseInt(con2);

        conDamage = (conDamage - 10)/2;

        String con = conDamage.toString();

        String int2 = edit_int.getText().toString();
        String inteligencia = edit_int.getText().toString();

        Integer intDamage = Integer.parseInt(int2);

        intDamage = (intDamage - 10)/2;

        String inte = intDamage.toString();

        String sab2 = edit_sab.getText().toString();
        String sabedoria = edit_sab.getText().toString();

        Integer sabDamage = Integer.parseInt(sab2);

        sabDamage = (sabDamage - 10)/2;

        String sab = sabDamage.toString();

        String car2 = edit_car.getText().toString();
        String carisma = edit_car.getText().toString();

        Integer carDamage = Integer.parseInt(car2);

        carDamage = (carDamage - 10)/2;

        String car = carDamage.toString();


        Map<String, Object> atributos = new HashMap<>();
        atributos.put("forca", forca);
        atributos.put("destreza", destreza);
        atributos.put("constituicao", constituicao);
        atributos.put("inteligencia", inteligencia);
        atributos.put("sabedoria", sabedoria);
        atributos.put("carisma", carisma);

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Atributos").document(usuarioID);
        documentReference.set(atributos).addOnSuccessListener(new OnSuccessListener<Void>() {
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

        Map<String, Object> atributos2 = new HashMap<>();
        atributos2.put("forcaBonus", forc);
        atributos2.put("destrezaBonus", des);
        atributos2.put("constituicaoBonus", con);
        atributos2.put("inteligenciaBonus", inte);
        atributos2.put("sabedoriaBonus", sab);
        atributos2.put("carismaBonus", car);



        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference2 = db.collection("Atributos2").document(usuarioID);
        documentReference2.set(atributos2).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    private void CalculaFor(){
        String forca2 = edit_for.getText().toString();
        String forca = edit_for.getText().toString();

        Integer forDamage = Integer.parseInt(forca2);

        forDamage = (forDamage - 10)/2;

        String forc = forDamage.toString();

        Map<String, Object> forcas = new HashMap<>();
        forcas.put("forca", forca);


        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Forca").document(usuarioID);
        documentReference.set(forcas).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("db","Sucesso ao atualizar os dados");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("db","Erro ao atualizar os dados" + e.toString());
                    }
                });

        Map<String, Object> forcaBonus = new HashMap<>();
        forcaBonus.put("forcaBonus", forc);

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference2 = db.collection("ForcaBonus").document(usuarioID);
        documentReference2.set(forcaBonus).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    private void CalculaDes(){
        String dest2 = edit_des.getText().toString();
        String destreza = edit_des.getText().toString();

        Integer desDamage = Integer.parseInt(dest2);

        desDamage = (desDamage - 10)/2;

        String des = desDamage.toString();


        Map<String, Object> destrezas = new HashMap<>();
        destrezas.put("destreza", destreza);

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Destrezas").document(usuarioID);
        documentReference.set(destrezas).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("db","Sucesso ao atualizar os dados");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("db","Erro ao atualizar os dados" + e.toString());
                    }
                });

        Map<String, Object> destrezasBonus = new HashMap<>();
        destrezasBonus.put("destrezaBonus", des);

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference2 = db.collection("destrezasBonus").document(usuarioID);
        documentReference2.set(destrezasBonus).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    private void CalculaCon(){
        String con2 = edit_con.getText().toString();
        String constituicao = edit_con.getText().toString();

        Integer conDamage = Integer.parseInt(con2);

        conDamage = (conDamage - 10)/2;

        String con = conDamage.toString();

        Map<String, Object> constituicoes = new HashMap<>();
        constituicoes.put("constituicao", constituicao);


        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Constituicoes").document(usuarioID);
        documentReference.set(constituicoes).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("db","Sucesso ao atualizar os dados");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("db","Erro ao atualizar os dados" + e.toString());
                    }
                });

        Map<String, Object> constituicaoBonus = new HashMap<>();
        constituicaoBonus.put("constituicaoBonus", con);

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference2 = db.collection("constituicaoBonus").document(usuarioID);
        documentReference2.set(constituicaoBonus).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    private void CalculaInt(){
        String int2 = edit_int.getText().toString();
        String inteligencia = edit_int.getText().toString();

        Integer intDamage = Integer.parseInt(int2);

        intDamage = (intDamage - 10)/2;

        String inte = intDamage.toString();

        Map<String, Object> inteligencias = new HashMap<>();
        inteligencias.put("inteligencia", inteligencia);


        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Inteligencias").document(usuarioID);
        documentReference.set(inteligencias).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("db","Sucesso ao atualizar os dados");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("db","Erro ao atualizar os dados" + e.toString());
                    }
                });

        Map<String, Object> inteligenciaBonus = new HashMap<>();
        inteligenciaBonus.put("inteligenciaBonus", inte);

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference2 = db.collection("inteligenciaBonus").document(usuarioID);
        documentReference2.set(inteligenciaBonus).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    private void CalculaSab(){

        String sab2 = edit_sab.getText().toString();
        String sabedoria = edit_sab.getText().toString();

        Integer sabDamage = Integer.parseInt(sab2);

        sabDamage = (sabDamage - 10)/2;

        String sab = sabDamage.toString();

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Map<String, Object> sabedorias = new HashMap<>();
        sabedorias.put("sabedoria", sabedoria);

        DocumentReference documentReference = db.collection("Sabedorias").document(usuarioID);
        documentReference.set(sabedorias).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("db","Sucesso ao atualizar os dados");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("db","Erro ao atualizar os dados" + e.toString());
                    }
                });

        Map<String, Object> sabedoriaBonus = new HashMap<>();
        sabedoriaBonus.put("sabedoriaBonus", sab);

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference2 = db.collection("sabedoriaBonus").document(usuarioID);
        documentReference2.set(sabedoriaBonus).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    private void CalculaCar(){


        String car2 = edit_car.getText().toString();
        String carisma = edit_car.getText().toString();

        Integer carDamage = Integer.parseInt(car2);

        carDamage = (carDamage - 10)/2;

        String car = carDamage.toString();

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Map<String, Object> carismas = new HashMap<>();
        carismas.put("carisma", carisma);

        DocumentReference documentReference = db.collection("Carismas").document(usuarioID);
        documentReference.set(carismas).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("db","Sucesso ao atualizar os dados");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("db","Erro ao atualizar os dados" + e.toString());
                    }
                });

        Map<String, Object> carismaBonus = new HashMap<>();
        carismaBonus.put("carismaBonus", car);

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference2 = db.collection("carismaBonus").document(usuarioID);
        documentReference2.set(carismaBonus).addOnSuccessListener(new OnSuccessListener<Void>() {
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



}




