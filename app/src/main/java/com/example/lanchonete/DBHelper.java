package com.example.lanchonete;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Lanchonete";
    public static final int DATABASE_VERSION = 1;

    //CRIAÇÃO DA TABELA USUARIO
    public static final String CLIENTE_TABLE_NAME = "TBcliente";
    public static final String CLIENTE_COLUMN_ID = "idCli";
    public static final String CLIENTE_COLUMN_NAME = "nomeCli";
    public static final String CLIENTE_COLUMN_EMAIL = "emailCli";
    public static final String CLIENTE_COLUMN_TEL = "telCli";
    public static final String CLIENTE_COLUMN_SENHA = "senhaCli";

    //CRIAÇÃO DA TABELA PEDIDO
    public static final String PEDIDO_TABLE_NAME = "TBpedido";
    public static final String PEDIDO_COLUMN_ID = "idPed";
    public static final String PEDIDO_COLUMN_TOTAL = "totalPed";
    public static final String PEDIDO_COLUMN_ITENS = "itensPed";
    public static final String PEDIDO_COLUMN_PAG = "pagPed";
    public static final String PEDIDO_COLUMN_CLI = "codCliFK";


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String QUERY_CLIENTE = "CREATE TABLE " + CLIENTE_TABLE_NAME + "( " +
                CLIENTE_COLUMN_ID + " INTEGER PRIMARY KEY, " + CLIENTE_COLUMN_NAME + " TEXT, " +
                CLIENTE_COLUMN_EMAIL + " TEXT, " + CLIENTE_COLUMN_TEL + " TEXT, " +
                CLIENTE_COLUMN_SENHA + " TEXT ); ";

        String QUERY_PEDIDO = "CREATE TABLE " + PEDIDO_TABLE_NAME + "( " +
                PEDIDO_COLUMN_ID + " INTEGER PRIMARY KEY, " + PEDIDO_COLUMN_TOTAL + " DOUBLE, " +
                PEDIDO_COLUMN_ITENS + " TEXT, " + PEDIDO_COLUMN_PAG + " TEXT, " +
                PEDIDO_COLUMN_CLI + " INTEGER, " +
                " foreign key (" + PEDIDO_COLUMN_CLI +" ) references "  + CLIENTE_TABLE_NAME + "(" + CLIENTE_COLUMN_ID + "));";

        db.execSQL(QUERY_CLIENTE);
        db.execSQL(QUERY_PEDIDO);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CLIENTE_TABLE_NAME + ";" );
        db.execSQL("DROP TABLE IF EXISTS " + PEDIDO_TABLE_NAME + ";" );
        onCreate(db);
    }



    void addUsuario (Usuario usuario){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(CLIENTE_COLUMN_NAME, usuario.getNome());
        values.put(CLIENTE_COLUMN_EMAIL, usuario.getEmail());
        values.put(CLIENTE_COLUMN_TEL, usuario.getTelefone());
        values.put(CLIENTE_COLUMN_SENHA, usuario.getSenha());

        db.insert(CLIENTE_TABLE_NAME, null, values);
        db.close();
    }





    Usuario selecionarUsuario(int codigo){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(CLIENTE_TABLE_NAME, new String[]{CLIENTE_COLUMN_ID,
                        CLIENTE_COLUMN_NAME,  CLIENTE_COLUMN_EMAIL, CLIENTE_COLUMN_TEL, CLIENTE_COLUMN_SENHA },
                CLIENTE_COLUMN_ID + " = ?",
                new String[] {String.valueOf(codigo)}, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        Usuario usuario = new Usuario(Integer.parseInt(cursor.getString(0)),  cursor.getString(1),
                cursor.getString(2), cursor.getString(3), cursor.getString(4));

        return usuario;
    }

    void addPedido (Ped ped){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(PEDIDO_COLUMN_TOTAL, ped.getTotal());
        contentValues.put(PEDIDO_COLUMN_ITENS, ped.getItens());
        contentValues.put(PEDIDO_COLUMN_PAG, ped.getPag());
        contentValues.put(PEDIDO_COLUMN_CLI, ped.getCodCli());

        db.insert(PEDIDO_TABLE_NAME, null, contentValues);
        db.close();
    }

    Ped selecionarPed(int codigo){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(PEDIDO_TABLE_NAME, new String[]{PEDIDO_COLUMN_ID,
                        PEDIDO_COLUMN_TOTAL,  PEDIDO_COLUMN_ITENS, PEDIDO_COLUMN_PAG, PEDIDO_COLUMN_CLI },
                PEDIDO_COLUMN_ID + " = ?",
                new String[] {String.valueOf(codigo)}, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        Ped ped = new Ped(Integer.parseInt(cursor.getString(0)),  Double.parseDouble(cursor.getString(1)),
                cursor.getString(2), cursor.getString(3), Integer.parseInt(cursor.getString(4)));

        return ped;
    }



    public boolean autenticaUsuario(Usuario usuario){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql_busca_usuario =
                "SELECT * FROM " + CLIENTE_TABLE_NAME + " WHERE " + CLIENTE_COLUMN_NAME + " = " + "'" + usuario.getNome() + "'";
        Cursor c = db.rawQuery(sql_busca_usuario, null);
        while(c.moveToNext()){
            if(usuario.getNome().equals(c.getString(c.getColumnIndex(CLIENTE_COLUMN_NAME)))){
                if(usuario.getSenha().equals(c.getString(c.getColumnIndex(CLIENTE_COLUMN_SENHA)))){
                    return true;
                }

            }
        }
        db.close();
        c.close();

        return false;
    }

    public  boolean Validacaoemail(String string){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT ? FROM " + CLIENTE_TABLE_NAME + " WHERE " + CLIENTE_COLUMN_EMAIL + "=?", new String[]{string,string});
        if(c.getCount()>0){
            return true;
        }
        else {
            return false;
        }
    }

    public  boolean Validacaonome(String string){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT ? FROM " + CLIENTE_TABLE_NAME + " WHERE " + CLIENTE_COLUMN_NAME + "=?", new String[]{string,string});
        if(c.getCount()>0){
            return true;
        }
        else {
            return false;
        }
    }

    public int pegaCod (String nome){

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(CLIENTE_TABLE_NAME, new String[]{CLIENTE_COLUMN_ID},
                CLIENTE_COLUMN_NAME + " = ?" ,
                new String[] {String.valueOf(nome)}, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        int cod = cursor.getInt(0);

        cursor.close();

        return cod;

    }

}
