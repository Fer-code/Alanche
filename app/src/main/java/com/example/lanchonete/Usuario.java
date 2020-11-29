package com.example.lanchonete;

public class Usuario {

    int codigo;
    String nome;
    String email;
    String telefone;
    String senha;

    public Usuario(){}

    public Usuario(int _codigo, String _nome, String _email,  String _telefone, String _senha){
        this.codigo = _codigo;
        this.nome = _nome;
        this.telefone = _telefone;
        this.email = _email;
        this.senha =  _senha;
    }

    public Usuario( String _nome, String _email,  String _telefone, String _senha){
        this.nome = _nome;
        this.telefone = _telefone;
        this.email = _email;
        this.senha =  _senha;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
