package com.example.fichadigital;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class TelaPersonagem extends AppCompatActivity {

    private Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /*public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.activity_tela_personagem, container, false);
        ArrayAdapter adapter=ArrayAdapter.createFromResource(getContext(), R.array.escolhas, android.R.layout.simple_spinner_item);


        spinner = view.findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        return view;
    }*/

}