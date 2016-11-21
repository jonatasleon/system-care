package com.systemcare.systemcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Button btnHelp = (Button) findViewById(R.id.btn_ajuda);
        btnHelp.setOnClickListener(this);

        Button btnTerms = (Button) findViewById(R.id.btn_termos);
        btnTerms.setOnClickListener(this);

        Button btnPolitic = (Button) findViewById(R.id.btn_politica);
        btnPolitic.setOnClickListener(this);

        Button btnLicence = (Button) findViewById(R.id.btn_licenca);
        btnLicence.setOnClickListener(this);

        Button btnContact = (Button) findViewById(R.id.btn_contato);
        btnContact.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
       Intent i;

       switch (v.getId()){
           case R.id.btn_ajuda:
               i = new Intent(AboutActivity.this, HelpActivity.class);
               break;

           case R.id.btn_contato:
               i = new Intent(AboutActivity.this, ContactActivity.class);
               break;

           case R.id.btn_termos:
               i = new Intent(AboutActivity.this, TermosActivity.class);
               break;

           case R.id.btn_licenca:
               i = new Intent(AboutActivity.this, LicencaActivity.class);
               break;



           case R.id.btn_politica:
               i = new Intent(AboutActivity.this, PoliticaActivity.class);
               break;
           default:
               return;
       }

        startActivity(i);
    }
}

