package br.edu.ifsp.arq.dmos5_2020s1.agenda_dmos5.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import br.edu.ifsp.arq.dmos5_2020s1.agenda_dmos5.R;
import br.edu.ifsp.arq.dmos5_2020s1.agenda_dmos5.exceptions.UsuarioDuplicadoException;
import br.edu.ifsp.arq.dmos5_2020s1.agenda_dmos5.model.ContatoUsuario;
import br.edu.ifsp.arq.dmos5_2020s1.agenda_dmos5.model.Usuario;

public class UsuarioDAO {
    private SQLiteDatabase mSqLiteDatabase;
    private SQLiteHelper mHelper;

    public UsuarioDAO(Context context) {
        mHelper = new SQLiteHelper(context);
    }

    public void adicionar(Usuario usuario) throws NullPointerException {
        if(usuario == null){
            throw new NullPointerException();
        }

        validarUsuarioExistente(usuario);

        ContentValues valores = new ContentValues();
        valores.put(SQLiteHelper.COLUMN_EMAIL, usuario.getEmail());
        valores.put(SQLiteHelper.COLUMN_SENHA, usuario.getSenha());

        mSqLiteDatabase = mHelper.getWritableDatabase();
        mSqLiteDatabase.insert(SQLiteHelper.TABLE_NAME_USUARIOS, null, valores);

        mSqLiteDatabase.close();

    }

    private void validarUsuarioExistente(Usuario usuario) {
        if (usuario == null) {
            throw new NullPointerException();
        }

        mSqLiteDatabase = mHelper.getReadableDatabase();

        String colunas[] = new String[]{
                SQLiteHelper.COLUMN_EMAIL
        };

        String clausulaWhere = SQLiteHelper.COLUMN_EMAIL + " = ?";
        String[] argumentos = {usuario.getEmail()};

        Cursor mCursor = mSqLiteDatabase.query(
                SQLiteHelper.TABLE_NAME_USUARIOS,
                colunas,
                clausulaWhere,
                argumentos,
                null,
                null,
                null
        );

        int count = mCursor.getCount();
        mCursor.close();
        mSqLiteDatabase.close();
        if(count > 0 ) {
            throw new UsuarioDuplicadoException();
        }
    }

    public boolean efetuarLogin(Usuario usuario) {
        if (usuario == null) {
            throw new NullPointerException();
        }

        mSqLiteDatabase = mHelper.getReadableDatabase();

        String columns[] = new String[]{
                SQLiteHelper.COLUMN_EMAIL,
                SQLiteHelper.COLUMN_SENHA
        };

        String[] args = {usuario.getEmail(), usuario.getSenha()};

        Cursor cursor = mSqLiteDatabase.query(
                SQLiteHelper.COLUMN_EMAIL,
                columns,
                "upper(" + SQLiteHelper.COLUMN_EMAIL + ") = upper(?)"
                        + " AND upper(" + SQLiteHelper.COLUMN_SENHA + ") = upper(?)",
                args,
                null,
                null,
                null
        );
        int count = cursor.getCount();
        cursor.close();
        mSqLiteDatabase.close();

        if(count < 1){
            return false;
        }
        return true;
    }


}
