package com.example.lanchonete;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE_NOME = "com.example.lanchonete.MENSAGEM";
    public final static String EXTRA_MESSAGE_SENHA = "com.example.lanchonete.EMAI";

    public final static String EXTRA_MESSAGE_COD = "com.example.lanchonete.COD";

    private EditText nome;
    private EditText senha;


    DBHelper db = new DBHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportActionBar().hide();

        /*nome = (EditText) findViewById(R.id.txtNome);
        senha = (EditText) findViewById(R.id.Senha);

        if (savedInstanceState != null) {
            nome.setText(savedInstanceState.getString(EXTRA_MESSAGE_NOME));
            senha.setText(savedInstanceState.getString(EXTRA_MESSAGE_SENHA));
        }*/


    }

    public void okay(View c) {

        EditText nome = findViewById(R.id.txtNome);
        EditText senha = findViewById(R.id.Senha);

        String senha1 = senha.getText().toString();
        String nome1 = nome.getText().toString();

        if(senha1.isEmpty() || nome1.isEmpty() ){
            Toast.makeText(this, "Insira os dados corretamente", Toast.LENGTH_SHORT).show();
        }
        else{
                autenticaUsuario(nome1, senha1);

            }
        }

        private void autenticaUsuario (String nome, String senha){


            EditText nomes = findViewById(R.id.txtNome);
            EditText senhas = findViewById(R.id.Senha);

                Usuario usuario = new Usuario();
                usuario.setNome(nome);
                usuario.setSenha(senha);
            DBHelper db = new DBHelper(this);
            boolean resultado = db.autenticaUsuario(usuario);

            if(resultado == true){
                int codigo =  db.pegaCod(nome);
                String codi =  String.valueOf(codigo);

                    Intent intent = new Intent(this, Produto.class);
                    intent.putExtra(EXTRA_MESSAGE_COD, codi);

                startActivity(intent);

                    //finish();


            }else{
                nomes.setText("");
                senhas.setText("");
                nomes.requestFocus();
                Toast.makeText(this, "Usuario ou senha invãlidos", Toast.LENGTH_SHORT).show();
            }
        }

        /*
    @Override
    public void onSaveInstanceState(Bundle saveInstance) {
        super.onSaveInstanceState(saveInstance);
        saveInstance.putString(EXTRA_MESSAGE_NOME, nome.getText().toString());
        saveInstance.putString(EXTRA_MESSAGE_SENHA, senha.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstance) {
        super.onRestoreInstanceState(savedInstance);
        String nomeRecuperado = savedInstance.getString(EXTRA_MESSAGE_NOME);
        String emailRecuperado = savedInstance.getString(EXTRA_MESSAGE_SENHA);
        nome.setText(nomeRecuperado);
        senha.setText(emailRecuperado);
    }*/
    public void Cadastro(View v){
        Intent c = new Intent(this, Cadastro.class);
        startActivity(c);
    }
}
