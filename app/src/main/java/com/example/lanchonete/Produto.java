package com.example.lanchonete;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Produto extends AppCompatActivity {

    public final static String EXTRA_MESSAGE_VALOR = "com.example.restau.VALUE";
    public final static String EXTRA_MESSAGE = "com.example.restau.MENSAGEM";
    public final static String EXTRA_MESSAGE_PAGAMENTO = "com.example.restau.PAG";

    DBHelper db = new DBHelper(this);

    Button prod;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        getSupportActionBar().hide();

            prod= findViewById(R.id.btnProd);

            Bundle extras = getIntent().getExtras();

            String cod = extras.getString(MainActivity.EXTRA_MESSAGE_COD);

            final String cg = String.valueOf(cod);
            Toast.makeText(this, cg, Toast.LENGTH_SHORT).show();



      //  final int fkCodCli = Integer.parseInt(cg);

            prod.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox ckCoxinha = findViewById(R.id.ckCoxinha);
                    CheckBox ckEmpada = findViewById(R.id.ckEmpada);
                    CheckBox ckPaoQ = findViewById(R.id.ckPaoQueijo);
                    CheckBox ckEsfira = findViewById(R.id.ckEsfiraCarne);

                    CheckBox ckBrigadeiro = findViewById(R.id.ckBrigadeiro);
                    CheckBox ckCocaCola = findViewById(R.id.ckCoca);
                    CheckBox ckGuarana = findViewById(R.id.ckGuarana);
                    CheckBox ckAgua = findViewById(R.id.ckAgua);

                    RadioButton rdCartao = findViewById(R.id.rdCartao);
                    RadioButton rdDinheiro = findViewById(R.id.rdDinheiro);


                    String pagamento = "";
                    String gostos = "";
                    double valor = 0;
                    if(ckCoxinha.isChecked()) {
                        gostos = " Coxinha\n";
                        valor += 4;
                    }
                    if (ckEmpada.isChecked()){
                        gostos += " Empada\n";
                        valor += 3;
                    }
                    if (ckPaoQ.isChecked()){
                        gostos += " Pão de queijo\n";
                        valor += 3.5;
                    }
                    if (ckEsfira.isChecked()){
                        gostos += " Esfira de Carne\n";
                        valor += 6;
                    }

                    if (ckBrigadeiro.isChecked()){
                        gostos += " Brigadeiro\n";
                        valor += 2;
                    }
                    if (ckCocaCola.isChecked()){
                        gostos += " Coca-Cola\n";
                        valor += 5;
                    }
                    if (ckGuarana.isChecked()){
                        gostos += " Guaraná\n";
                        valor += 5;
                    }
                    if (ckAgua.isChecked()){
                        gostos += " Água\n";
                        valor += 4.5;
                    }


                    if(rdCartao.isChecked()){
                        pagamento = "Cartão";
                    }
                    if(rdDinheiro.isChecked()){
                        pagamento = "Dinheiro";
                        valor = valor - (valor * 1/10);
                    }

                    int fkCli = Integer.parseInt(cg);

                    String total1 = String.valueOf(valor);

                    if(total1.isEmpty() || gostos.isEmpty() || pagamento.isEmpty()){
                        Toast.makeText(Produto.this, "Escolha corretamente", Toast.LENGTH_SHORT).show();
                    }else{
                        db.addPedido(new Ped(valor, gostos, pagamento, fkCli ));


                        Intent intent = new Intent(Produto.this, Pagamento.class);
                        intent.putExtra(EXTRA_MESSAGE, gostos);
                        intent.putExtra(EXTRA_MESSAGE_VALOR, total1);
                        intent.putExtra(EXTRA_MESSAGE_PAGAMENTO, pagamento);
                        startActivity(intent);
                    }

                }


            });

        }



    public void menu(View p) {

        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }

}