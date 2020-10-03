package br.edu.ifsp.arq.dmos5_2020s1.agenda_dmos5.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import br.edu.ifsp.arq.dmos5_2020s1.agenda_dmos5.R;
import br.edu.ifsp.arq.dmos5_2020s1.agenda_dmos5.model.ContatoUsuario;

public class DetalhesContatoActivity extends AppCompatActivity {

    private TextView nomeCompletoTextView;
    private TextView telefoneFixoTextView;
    private TextView telefonContatoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_contato);

        nomeCompletoTextView = findViewById(R.id.textview_nome_completo);
        telefoneFixoTextView = findViewById(R.id.textview_telefone_fixo);
        telefonContatoTextView = findViewById(R.id.textview_telefone_contato);

        preencherDados();
    }

    private void preencherDados() {
        Bundle bundle = getIntent().getExtras();

        if (bundle == null) {
            Toast.makeText(this, R.string.unexpected_error, Toast.LENGTH_SHORT);
            finish();
        }
        ContatoUsuario contato = (ContatoUsuario) bundle.get(ContatoUsuario.CONTATO_KEY);

        nomeCompletoTextView.setText(contato.getNomeCompleto());
        telefoneFixoTextView.setText(contato.getTelefoneFixo());
        telefonContatoTextView.setText(contato.getTelefoneContato());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}