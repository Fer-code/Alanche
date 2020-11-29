package com.example.lanchonete;

public class Ped {

    int CodPed;
    double total;
    String itens;
    String pag;
    int codCli;

    public Ped(){}


    public Ped(int _codped, double _total, String _itens,  String _pag, int _codCli){
        this.CodPed =  _codped;
        this.total = _total;
        this.itens = _itens;
        this.pag = _pag;
        this.codCli = _codCli;
    }

    public Ped( double _total, String _itens,  String _pag, int _codCli){
        this.total = _total;
        this.itens = _itens;
        this.pag = _pag;
        this.codCli = _codCli;
    }

    public int getCodPed() {
        return CodPed;
    }

    public void setCodPed(int codPed) {
        CodPed = codPed;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getItens() {
        return itens;
    }

    public void setItens(String itens) {
        this.itens = itens;
    }

    public String getPag() {
        return pag;
    }

    public void setPag(String pag) {
        this.pag = pag;
    }

    public int getCodCli() {
        return codCli;
    }

    public void setCodCli(int codCli) {
        this.codCli = codCli;
    }
}
