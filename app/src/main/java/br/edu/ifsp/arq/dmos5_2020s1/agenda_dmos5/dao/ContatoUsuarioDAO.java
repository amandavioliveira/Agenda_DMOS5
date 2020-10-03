package br.edu.ifsp.arq.dmos5_2020s1.agenda_dmos5.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.arq.dmos5_2020s1.agenda_dmos5.model.ContatoUsuario;

public class ContatoUsuarioDAO {

    private SQLiteDatabase mSqLiteDatabase;
    private SQLiteHelper mHelper;

    public ContatoUsuarioDAO(Context context) {
        mHelper = new SQLiteHelper(context);
    }

    public void adicionar(ContatoUsuario contato) throws NullPointerException {
        if(contato == null){
            throw new NullPointerException();
        }
        ContentValues valores = new ContentValues();
        valores.put(SQLiteHelper.COLUMN_NOME_COMPLETO, contato.getNomeCompleto());
        valores.put(SQLiteHelper.COLUMN_TELEFONE_FIXO, contato.getTelefoneFixo());
        valores.put(SQLiteHelper.COLUMN_TELEFONE_CONTATO, contato.getTelefoneContato());

        mSqLiteDatabase = mHelper.getWritableDatabase();
        mSqLiteDatabase.insert(SQLiteHelper.TABLE_NAME_CONTATOS, null, valores);

        mSqLiteDatabase.close();
    }

    public List<ContatoUsuario> recuperaTodos(){
        List<ContatoUsuario> mContatoList;
        ContatoUsuario mContato;
        Cursor mCursor;

        mContatoList = new ArrayList<>();

        String colunas[] = new String[]{
                SQLiteHelper.COLUMN_NOME_COMPLETO,
                SQLiteHelper.COLUMN_TELEFONE_FIXO,
                SQLiteHelper.COLUMN_TELEFONE_CONTATO
        };

        mSqLiteDatabase = mHelper.getReadableDatabase();

        mCursor = mSqLiteDatabase.query(
                SQLiteHelper.TABLE_NAME_CONTATOS,
                colunas,
                null,
                null,
                null,
                null,
                SQLiteHelper.COLUMN_NOME_COMPLETO + " COLLATE NOCASE ASC"
        );

        while (mCursor.moveToNext()){
            mContato = new ContatoUsuario(
                    mCursor.getString(0),
                    mCursor.getString(1),
                    mCursor.getString(2)
            );

            mContatoList.add(mContato);
        }

        mCursor.close();
        mSqLiteDatabase.close();
        return mContatoList;
    }
}
