package com.example.downsaveinfo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoDeDados extends SQLiteOpenHelper {

    // 1 - Definindo as contantes (static final) que irao compor o "STATEMENT" para criacao
    // da tabalea "Mensagem"
    public static final String NOME_BANCO = "MeuBancodeDados.db";
    public static final int VERSAO_BANCO = 1;
    public static final String TABLE_NAME = "Mensagem";
    public static final String COLUNA0 = "_id";
    public static final String COLUNA1 = "mensagemrecebida";

    // 2 - Criando o "STATEMENT" para criacao da tabela "Mensagem"
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + COLUNA0 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUNA1 + " TEXT);";

    // 3 - Declaranto o construtor da classe
    public BancoDeDados(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    // 4 - Criando o metodo onCreate para criacao da tabela "Mensagem"
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    // 5 - Criando o metodo onUpgrade para atualizacao da tabela "Mensagem"
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // 6 - Criando o metodo para insercao de dados na tabela "Mensagem"
    public long salvaMensagem(String mensagem) {
        // variavel que ira receber o retorno do metodo insert (-1 falha, 0 sucesso)
        long id;

        // abrindo conexao com o banco de dados
        SQLiteDatabase db = getWritableDatabase();
        try {
            // inserindo dados na tabela "Mensagem"
            ContentValues valores = new ContentValues();
            valores.put(COLUNA1, mensagem);

            // evocando o metodo insert para insercao dos dados na tabela "Mensagem"
            id = db.insert(TABLE_NAME, null, valores);
            return id;

        } finally {
            // fechando conexao com o banco de dados
            db.close();
        }
    }

    // 7 - Criando o metodo para apagar todos os dados da tabela "Mensagem"
    public int apagaMensagem() {
        // variavel que ira receber o retorno do metodo delete (0 se a tabela estiver vazia, -1 falha)
        int count;

        // abrindo conexao com o banco de dados
        SQLiteDatabase db = getWritableDatabase();
        try {
            // apagando todos os dados da tabela "Mensagem"
            count = db.delete(TABLE_NAME, null, null);
            return count;

        } finally {
            // fechando conexao com o banco de dados
            db.close();
        }
    }

    // 8 - Criando o metodo para recuperar todos os dados da tabela "Mensagem"
    @SuppressLint("Range")
    public String findAll() {
        // variavel que ira receber o retorno do metodo query (null se nao encontrar nenhum dado)
        String mensagem = "";

        // abrindo conexao com o banco de dados
        SQLiteDatabase db = getReadableDatabase();
        try {
            // criando um cursor para recuperar os dados da tabela "Mensagem"
            Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
            // posicionando o cursor no primeiro registro
            c.moveToFirst();
            // verificando se o cursor possui dados
            if (c.moveToFirst()) {
                // recuperando o valor da coluna "mensagemrecebida"
                mensagem = c.getString(c.getColumnIndex("mensagemrecebida"));
            }
            return mensagem;

        } finally {
            // fechando conexao com o banco de dados
            db.close();
        }
    }
}
