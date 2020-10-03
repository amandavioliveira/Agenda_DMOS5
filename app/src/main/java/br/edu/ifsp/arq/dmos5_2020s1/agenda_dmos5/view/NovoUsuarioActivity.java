package br.edu.ifsp.arq.dmos5_2020s1.agenda_dmos5.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import br.edu.ifsp.arq.dmos5_2020s1.agenda_dmos5.R;
import br.edu.ifsp.arq.dmos5_2020s1.agenda_dmos5.dao.ContatoUsuarioDAO;
import br.edu.ifsp.arq.dmos5_2020s1.agenda_dmos5.dao.UsuarioDAO;
import br.edu.ifsp.arq.dmos5_2020s1.agenda_dmos5.model.ContatoUsuario;
import br.edu.ifsp.arq.dmos5_2020s1.agenda_dmos5.model.Usuario;

public class NovoUsuarioActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText emailEditText;
    private EditText senhaEditText;
    private Button buttonCadastrar;
    private Button buttonEntrar;
    private CheckBox lembrarCheckBox;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private UsuarioDAO mUsuarioDAO;

    private ContatoUsuarioDAO mContatoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_usuario);

        mUsuarioDAO = new UsuarioDAO(this);
        mContatoDAO = new ContatoUsuarioDAO(this);

        emailEditText = findViewById(R.id.edittext_email);
        senhaEditText = findViewById(R.id.edittext_senha);

        lembrarCheckBox = findViewById(R.id.lembrar_login);

        buttonEntrar = findViewById(R.id.btn_entrar);
        buttonEntrar.setOnClickListener(this);

        buttonCadastrar = findViewById(R.id.btn_novo_usuario);
        buttonCadastrar.setOnClickListener(this);

        mSharedPreferences = this.getPreferences(MODE_PRIVATE);
        mSharedPreferences = this.getSharedPreferences(getString(R.string.file_preferences), MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

    }

    @Override
    protected void onResume() {
        verificarPreferencias();
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        if (v == buttonCadastrar) {
            cadastrarNovoUsuario();
        } else if (v == buttonEntrar) {
            realizarLogin();
        }
    }

    private void realizarLogin() {
        ConstraintLayout layout = findViewById(R.id.layout_novo_usuario);
        Usuario usuario = recuperarValores();
        boolean entrar = mUsuarioDAO.efetuarLogin(usuario);
        salvarPreferencias(usuario);
        if (entrar) {
            mEditor.putString(getString(R.string.key_usuario_logado), usuario.getEmail());
            mEditor.commit();
            Intent intent = new Intent(this, ContatosActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void cadastrarNovoUsuario() {
        ConstraintLayout layout = findViewById(R.id.layout_novo_usuario);
        try {
            Usuario usuario = recuperarValores();
            mUsuarioDAO.adicionar(usuario);
            boolean primeiro = mSharedPreferences.getBoolean(getString(R.string.key_primeiro_usuario), true);
            if (primeiro) {
                mContatoDAO.updateUsuario(usuario);
                mEditor.putBoolean(getString(R.string.key_primeiro_usuario), false);
                mEditor.commit();
            }
            finalizar(true);
        }catch (NullPointerException e){
            Toast.makeText(this, R.string.erro_null_contato, Toast.LENGTH_SHORT).show();
        }
    }

    private void finalizar(boolean sucesso){
        if(sucesso){
            setResult(Activity.RESULT_OK);
            Toast.makeText(this, R.string.success_message_usuario, Toast.LENGTH_SHORT).show();
        }else{
            setResult(Activity.RESULT_CANCELED);
        }
        finish();
    }

    private Usuario recuperarValores() {
        String email = emailEditText.getText().toString();
        String password = senhaEditText.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, R.string.erro_empty_fields, Toast.LENGTH_SHORT).show();
        }

        return new Usuario(email, password);
    }

    private void salvarPreferencias(Usuario usuario){
        if(lembrarCheckBox.isChecked()){
            mEditor.putString(getString(R.string.key_email), usuario.getEmail());
            mEditor.commit();

            mEditor.putString(getString(R.string.key_senha), usuario.getSenha());
            mEditor.commit();

            mEditor.putBoolean(getString(R.string.key_lembrar), true);
            mEditor.commit();
        }else{
            mEditor.putString(getString(R.string.key_email), "");
            mEditor.commit();

            mEditor.putString(getString(R.string.key_senha), "");
            mEditor.commit();

            mEditor.putBoolean(getString(R.string.key_lembrar), false);
            mEditor.commit();
        }

    }

    private void verificarPreferencias() {
        String usuario, senha;
        usuario = mSharedPreferences.getString(getString(R.string.key_email), "");
        senha = mSharedPreferences.getString(getString(R.string.key_senha), "");
        boolean lembrar = mSharedPreferences.getBoolean(getString(R.string.key_lembrar), false);

        if(lembrar){
            emailEditText.setText(usuario);
            senhaEditText.setText(senha);
            lembrarCheckBox.setChecked(true);
        }
    }
}