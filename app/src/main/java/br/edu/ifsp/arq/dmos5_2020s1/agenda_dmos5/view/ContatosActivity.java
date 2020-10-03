package br.edu.ifsp.arq.dmos5_2020s1.agenda_dmos5.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import br.edu.ifsp.arq.dmos5_2020s1.agenda_dmos5.R;
import br.edu.ifsp.arq.dmos5_2020s1.agenda_dmos5.constantes.Constantes;
import br.edu.ifsp.arq.dmos5_2020s1.agenda_dmos5.dao.ContatoUsuarioDAO;
import br.edu.ifsp.arq.dmos5_2020s1.agenda_dmos5.model.ContatoUsuario;

public class ContatosActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView contatosListView;
    private List<ContatoUsuario> mContatoList;
    private ContatoUsuarioDAO mContatoDao;
    private ArrayAdapter<ContatoUsuario> arrayAdapter;
    private FloatingActionButton adicionarActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contatos);

        contatosListView = findViewById(R.id.listview_contatos);
        adicionarActionButton = findViewById(R.id.fab_adicionar_contato);

        mContatoDao = new ContatoUsuarioDAO(this);
        mContatoList = mContatoDao.recuperaTodos();

        arrayAdapter = new ArrayAdapter<ContatoUsuario>(this, android.R.layout.simple_list_item_1, mContatoList);

        adicionarActionButton.setOnClickListener(this);

        contatosListView.setAdapter(arrayAdapter);
        contatosListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContatoUsuario contato = mContatoList.get(position);
                Intent intent = new Intent(getApplicationContext(), DetalhesContatoActivity.class);
                intent.putExtra(ContatoUsuario.CONTATO_KEY, contato);
                startActivity(intent);
            }
        });

        atualizaTela();
    }

    @Override
    public void onClick(View v) {
        if(v == adicionarActionButton){
            Intent novoContato = new Intent(this, NovoContatoActivity.class);
            startActivityForResult(novoContato, Constantes.NEW_CONTATO_REQUEST_CODE);
	        startActivity(novoContato);
        }
    }

    @Override
    protected void onResume() {
        mContatoList.clear();
        mContatoList.addAll(mContatoDao.recuperaTodos());
        super.onResume();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case Constantes.NEW_CONTATO_REQUEST_CODE:
                if(resultCode == RESULT_OK){
                    mContatoList = mContatoDao.recuperaTodos();
                    arrayAdapter.notifyDataSetChanged();
                }else{
                    if(resultCode == RESULT_CANCELED){
                        Toast.makeText(this, "Nenhum contato adicionado.", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
        }
        atualizaTela();
    }

    private void atualizaTela(){
        if(mContatoList.size() == 0){
            contatosListView.setVisibility(View.GONE);
        }else{
            contatosListView.setVisibility(View.VISIBLE);
        }
    }
}