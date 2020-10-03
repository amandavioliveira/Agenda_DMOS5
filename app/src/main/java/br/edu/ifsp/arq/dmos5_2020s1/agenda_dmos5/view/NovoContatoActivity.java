package br.edu.ifsp.arq.dmos5_2020s1.agenda_dmos5.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.edu.ifsp.arq.dmos5_2020s1.agenda_dmos5.R;
import br.edu.ifsp.arq.dmos5_2020s1.agenda_dmos5.dao.ContatoUsuarioDAO;
import br.edu.ifsp.arq.dmos5_2020s1.agenda_dmos5.model.ContatoUsuario;

public class NovoContatoActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText nomeCompletoEditText;
    private EditText telefoneFixoEditText;
    private EditText telefonContatoEditText;
    private Button salvarButton;

    private ContatoUsuarioDAO mContatoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_contato);

        mContatoDao = new ContatoUsuarioDAO(this);

        nomeCompletoEditText = findViewById(R.id.edittext_nome_completo);
        telefoneFixoEditText = findViewById(R.id.edittext_telefone_fixo);
        telefonContatoEditText = findViewById(R.id.edittext_telefone_contato);
        salvarButton = findViewById(R.id.button_save);
        salvarButton.setOnClickListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_save:
                salvarContatoUsuario();
                break;
        }
    }

    private void salvarContatoUsuario(){
        String nomeCompleto, telefoneFixo, telefoneContato;
        nomeCompleto = nomeCompletoEditText.getText().toString();
        telefoneFixo = telefoneFixoEditText.getText().toString();
        telefoneContato = telefonContatoEditText.getText().toString();

        if(nomeCompleto.isEmpty() || telefoneFixo.isEmpty() || telefoneContato.isEmpty()){
            Toast.makeText(this, R.string.erro_empty_fields, Toast.LENGTH_SHORT).show();
        }else{
            mContatoDao = new ContatoUsuarioDAO(this);
            try{
                SharedPreferences mSharedPreferences = this.getPreferences(MODE_PRIVATE);
                mSharedPreferences = this.getSharedPreferences(getString(R.string.file_preferences), MODE_PRIVATE);

                mContatoDao.adicionar(new ContatoUsuario(nomeCompleto, telefoneFixo, telefoneContato));
                finalizar(true);
            }catch (NullPointerException e){
                Toast.makeText(this, R.string.erro_null_contato, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void finalizar(boolean sucesso){
        if(sucesso){
            setResult(Activity.RESULT_OK);
            Toast.makeText(this, R.string.success_message, Toast.LENGTH_SHORT).show();
        }else{
            setResult(Activity.RESULT_CANCELED);
        }
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, ContatosActivity.class));
                finishAffinity();
                break;
            default:break;
        }
        return true;
    }
}