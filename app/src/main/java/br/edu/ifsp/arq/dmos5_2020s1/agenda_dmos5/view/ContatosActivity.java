package br.edu.ifsp.arq.dmos5_2020s1.agenda_dmos5.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import br.edu.ifsp.arq.dmos5_2020s1.agenda_dmos5.R;
import br.edu.ifsp.arq.dmos5_2020s1.agenda_dmos5.constantes.Constantes;
import br.edu.ifsp.arq.dmos5_2020s1.agenda_dmos5.dao.ContatoUsuarioDAO;
import br.edu.ifsp.arq.dmos5_2020s1.agenda_dmos5.model.ContatoUsuario;

public class ContatosActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView contatosRecyclerView;
    private FloatingActionButton sairActionButton;
    private FloatingActionButton adicionarActionButton;
    private ItemContatoAdapter mContatoAdapter;

    private List<ContatoUsuario> mContatoList;
    private ContatoUsuarioDAO mContatoDao;
    private String usuarioLogado;

    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contatos);

        contatosRecyclerView = findViewById(R.id.recyclerview_contatos);
        adicionarActionButton = findViewById(R.id.fab_adicionar_contato);
        adicionarActionButton.setOnClickListener(this);

        sairActionButton = findViewById(R.id.fab_sair_contato);
        sairActionButton.setOnClickListener(this);

        mContatoDao = new ContatoUsuarioDAO(this);

        mSharedPreferences = this.getPreferences(MODE_PRIVATE);
        mSharedPreferences = this.getSharedPreferences(getString(R.string.file_preferences), MODE_PRIVATE);

        usuarioLogado = mSharedPreferences.getString(getString(R.string.key_usuario_logado), "");

        mContatoList = mContatoDao.recuperaTodos(usuarioLogado);

        contatosRecyclerView = findViewById(R.id.recyclerview_contatos);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        contatosRecyclerView.setLayoutManager(layoutManager);

        mContatoAdapter = new ItemContatoAdapter(mContatoList);
        contatosRecyclerView.setAdapter(mContatoAdapter);

        mContatoAdapter.setClickListener(new RecyclerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ContatoUsuario contato = mContatoList.get(position);
                Intent intent = new Intent(getApplicationContext(), DetalhesContatoActivity.class);
                intent.putExtra(ContatoUsuario.CONTATO_KEY, contato);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == sairActionButton) {
            SharedPreferences.Editor mEditor = mSharedPreferences.edit();
            mEditor.putString(getString(R.string.key_usuario_logado), "");
            mEditor.commit();
            Intent intent = new Intent(this, NovoUsuarioActivity.class);
            startActivity(intent);
            finish();
        }else if(v == adicionarActionButton){
            Intent novoContato = new Intent(this, NovoContatoActivity.class);
            startActivityForResult(novoContato, Constantes.NEW_CONTATO_REQUEST_CODE);
            startActivity(novoContato);
        }
    }

    @Override
    protected void onResume() {
        mContatoList.clear();
        mContatoList.addAll(mContatoDao.recuperaTodos(usuarioLogado));
        mContatoAdapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.novo_contato_menu:
                Intent in = new Intent(this, NovoContatoActivity.class);
                startActivity(in);
        }
        return super.onOptionsItemSelected(item);
    }
}