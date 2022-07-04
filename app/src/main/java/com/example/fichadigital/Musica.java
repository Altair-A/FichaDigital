package com.example.fichadigital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Musica extends AppCompatActivity {
    MediaPlayer musica;
    Button btn_play, btn_pause, btn_stop;

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musica);

        musica = MediaPlayer.create(this, R.raw.sound);
        btn_pause = findViewById(R.id.buttonPause);
        btn_play = findViewById(R.id.buttonPlay);
        btn_stop = findViewById(R.id.buttonStop);

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicaStop(v);
            }
        });

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicaPlay(v);
            }
        });

        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicaPause(v);
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
                        Intent it1 = new Intent(Musica.this, Musica.class);
                        startActivity(it1);
                        finish();
                        break;
                    case 2:
                        Intent it2 = new Intent(Musica.this, Equipamentos.class);
                        startActivity(it2);
                        finish();
                        break;
                    case 3:
                        Intent it3 = new Intent(Musica.this, Pericias.class);
                        startActivity(it3);
                        finish();
                        break;
                    case 4:
                        Intent it4 = new Intent(Musica.this, Poderes.class);
                        startActivity(it4);
                        finish();
                        break;
                    case 5:
                        Intent it5 = new Intent(Musica.this, Magias.class);
                        startActivity(it5);
                        finish();
                        break;
                    case 6:
                        Intent it6 = new Intent(Musica.this, Notas.class);
                        startActivity(it6);
                        finish();
                        break;
                    case 7:
                        Intent it7 = new Intent(Musica.this, TelaPersonagem.class);
                        startActivity(it7);
                        finish();
                        break;
                    case 8:
                        Intent it8 = new Intent(Musica.this, TelaPrincipal.class);
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

    public void musicaPlay(View v){
        musica.start();
    }

    public void musicaPause(View v){
        musica.pause();
    }

    public void musicaStop(View v){
        musica.stop();
        musica = MediaPlayer.create(this, R.raw.sound);
    }
}