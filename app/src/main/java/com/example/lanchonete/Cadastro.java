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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class Cadastro extends AppCompatActivity {

    public final static String EXTRA_MESSAGE_NOME = "com.example.lanchonete.MENSAGEM";
    public final static String EXTRA_MESSAGE_SENHA = "com.example.lanchonete.SENHA";
    public final static String EXTRA_MESSAGE_EMAIL = "com.example.lanchonete.EMAIL";
    public final static String EXTRA_MESSAGE_TEL = "com.example.lanchonete.TEL";
    public final static String EXTRA_MESSAGE_CSENHA = "com.example.lanchonete.CSENHA";

    EditText nome;
    EditText senha;
    EditText tel;
    EditText email;
    EditText conSenha;
    Button okay;

    DBHelper db = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        getSupportActionBar().hide();


        nome = findViewById(R.id.nomeCad);
        email = findViewById(R.id.emailCad);
        tel = findViewById(R.id.TelCad);
        senha = findViewById(R.id.SenhaCad);
        conSenha = findViewById(R.id.ConfSenhaCad);
        okay = findViewById(R.id.btnCadastrar);


        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome1 = nome.getText().toString();
                String telefone = tel.getText().toString();
                String email1 = email.getText().toString();
                String senha1 = senha.getText().toString();
                String consenha1 = conSenha.getText().toString();

                if (senha1.isEmpty() || nome1.isEmpty() || email1.isEmpty() || telefone.isEmpty() || consenha1.isEmpty()) {
                    Toast.makeText(Cadastro.this, "Insira os dados corretamente", Toast.LENGTH_SHORT).show();
                } else if (!senha1.equals(consenha1)) {
                    Toast.makeText(Cadastro.this, "As senhas não correspondem", Toast.LENGTH_SHORT).show();
                } else if (db.Validacaoemail(email1)) {
                    Toast.makeText(Cadastro.this, "Email já utilizado", Toast.LENGTH_SHORT).show();

                } else if (db.Validacaonome(nome1)) {
                    Toast.makeText(Cadastro.this, "Nome já utilizado", Toast.LENGTH_SHORT).show();

                } else {
                    db.addUsuario(new Usuario(nome1,  email1, telefone, senha1));
                    Toast.makeText(Cadastro.this, "Cliente adicionado com sucesso", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Cadastro.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~SAVEINSTANCE~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    public void onSaveInstanceState(Bundle saveInstance) {
        super.onSaveInstanceState(saveInstance);
        saveInstance.putString(EXTRA_MESSAGE_NOME, nome.getText().toString());
        saveInstance.putString(EXTRA_MESSAGE_SENHA, senha.getText().toString());
        saveInstance.putString( EXTRA_MESSAGE_TEL, tel.getText().toString());
        saveInstance.putString( EXTRA_MESSAGE_EMAIL, email.getText().toString());
        saveInstance.putString( EXTRA_MESSAGE_CSENHA, conSenha.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstance) {
        super.onRestoreInstanceState(savedInstance);
        String nomeRecuperado = savedInstance.getString(EXTRA_MESSAGE_NOME);
        String senhaRecuperado = savedInstance.getString(EXTRA_MESSAGE_SENHA);
        String telRecuperado = savedInstance.getString(EXTRA_MESSAGE_TEL);
        String emailRecuperado = savedInstance.getString(EXTRA_MESSAGE_EMAIL);
        String conRecuperado = savedInstance.getString(EXTRA_MESSAGE_CSENHA);

        nome.setText(nomeRecuperado);
        senha.setText(senhaRecuperado);
        tel.setText(telRecuperado);
        email.setText(emailRecuperado);
        conSenha.setText(conRecuperado);

    }
}