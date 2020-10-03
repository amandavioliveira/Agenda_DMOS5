package br.edu.ifsp.arq.dmos5_2020s1.agenda_dmos5.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.ifsp.arq.dmos5_2020s1.agenda_dmos5.R;
import br.edu.ifsp.arq.dmos5_2020s1.agenda_dmos5.model.ContatoUsuario;

public class ItemContatoAdapter extends RecyclerView.Adapter<ItemContatoAdapter.ContatosViewHolder> {

    private List<ContatoUsuario> mContatoList;

    private static RecyclerItemClickListener clickListener;

    public ItemContatoAdapter(List<ContatoUsuario> mContatoList) {
        this.mContatoList = mContatoList;
    }

    public void setClickListener(RecyclerItemClickListener clickListener) {
        ItemContatoAdapter.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ContatosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contato_recyclerview, parent, false);
        ContatosViewHolder viewHolder = new ContatosViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContatosViewHolder holder, final int position) {
        holder.nomeTextView.setText(mContatoList.get(position).getNomeCompleto());
    }

    @Override
    public int getItemCount() {
        return mContatoList.size();
    }

    public static class ContatosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView nomeTextView;
        public ContatosViewHolder(@NonNull View itemView) {
            super(itemView);

            nomeTextView = itemView.findViewById(R.id.list_item_nome);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null)
                clickListener.onItemClick(getAdapterPosition());
        }
    }
}