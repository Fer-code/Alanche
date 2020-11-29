package com.example.lanchonete;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Pagamento extends AppCompatActivity {

    private TextView visProd;
    private TextView visPag;
    private TextView visTotal;

    DBHelper db = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);

        getSupportActionBar().hide();

        Bundle extras = getIntent().getExtras();
        String msg = null;
         visProd = (TextView)findViewById(R.id.visProd);
         visPag = (TextView)findViewById(R.id.visPag);
         visTotal = (TextView)findViewById(R.id.visTotal);

        if (extras != null) {
            msg = extras.getString(Produto.EXTRA_MESSAGE);
            String total = extras.getString(Produto.EXTRA_MESSAGE_VALOR);
            String page = extras.getString(Produto.EXTRA_MESSAGE_PAGAMENTO);

            String txt = String.format("Pedido:\n %s ",msg);
            String txt1 = String.format("Total:  %s",total);


            visProd.setText(txt );
            visTotal.setText(txt1);
            visPag.setText("Forma de Pagamento: "+ page);



        }
    }

    public void telefone(View tl)
    {
        Uri uri = Uri.parse("tel: 11 983676652");
        Intent it = new Intent(Intent.ACTION_DIAL, uri);
        startActivity(it);
    }

    public void localiza(View l){
        Intent local = new Intent(this, Local.class);
        startActivity(local);
    }
    public void enviarEmail(View view) throws UnsupportedEncodingException {

        String uriText =
                "mailto:fernandakui28@gmail.com" +
                        "?subject=" + URLEncoder.encode("Informe o assunto", "utf-8") +
                        "&body=" + URLEncoder.encode("Desenvolva", "utf-8");
        Uri uri = Uri.parse(uriText);
        Intent it = new Intent(Intent.ACTION_SENDTO);
        it.setData(uri);
        startActivity(Intent.createChooser(it, "Para enviar email"));
    }
}