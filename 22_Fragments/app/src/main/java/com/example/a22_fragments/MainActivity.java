package com.example.a22_fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.a22_fragments.Fragments.FragmentA;
import com.example.a22_fragments.Fragments.FragmentB;

public class MainActivity extends AppCompatActivity {
    Button btnA,btnB;
    FragmentA fragmentA;
    FragmentB fragmentB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnA = findViewById(R.id.main_btn_fr1);
        btnB = findViewById(R.id.main_btn_fr2);

        fragmentA = new FragmentA();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.main_fr_container,fragmentA);
        ft.commit();
        btnA.setEnabled(false);
        btnB.setEnabled(true);

        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentB = new FragmentB();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.main_fr_container,fragmentB);
                ft.commit();
                btnA.setEnabled(true);
                btnB.setEnabled(false);
            }
        });
        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentA = new FragmentA();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.main_fr_container,fragmentA);
                ft.commit();
                btnA.setEnabled(false);
                btnB.setEnabled(true);
            }
        });




    }
}