package br.edu.ifsp.arq.dmos5_2020s1.agenda_dmos5.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "agenda_dmos5.db";

    //Constantes da tabela ContatoUsuario
    public static final String TABLE_NAME_CONTATOS = "contatos";
    public static final String COLUMN_NOME_COMPLETO = "nome_completo";
    public static final String COLUMN_TELEFONE_FIXO = "telefone_fixo";
    public static final String COLUMN_TELEFONE_CONTATO = "telefone_contato";

    private Context mContext;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    public Context getContext(){
        return mContext;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql;

        sql = "CREATE TABLE " + TABLE_NAME_CONTATOS + " (";
        sql += COLUMN_NOME_COMPLETO + " TEXT NOT NULL, ";
        sql += COLUMN_TELEFONE_FIXO + " TEXT NOT NULL, ";
        sql += COLUMN_TELEFONE_CONTATO + " TEXT NOT NULL);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
