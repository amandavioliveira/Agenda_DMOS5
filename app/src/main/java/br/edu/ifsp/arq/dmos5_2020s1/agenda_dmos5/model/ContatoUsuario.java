package br.edu.ifsp.arq.dmos5_2020s1.agenda_dmos5.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class ContatoUsuario implements Serializable {
    private String nomeCompleto;
    private String telefoneFixo;
    private String telefoneContato;
    private String idUsuario;

    public static final String CONTATO_KEY = "CONTATO_KEY";

    public ContatoUsuario(String nomeCompleto, String telefoneFixo, String telefoneContato){
        this.nomeCompleto = nomeCompleto;
        this.telefoneFixo = telefoneFixo;
        this.telefoneContato = telefoneContato;
    }

    public ContatoUsuario(String nomeCompleto, String telefoneFixo, String telefoneContato, String idUsuario){
        this.nomeCompleto = nomeCompleto;
        this.telefoneFixo = telefoneFixo;
        this.telefoneContato = telefoneContato;
        this.idUsuario = idUsuario;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getTelefoneFixo() {
        return telefoneFixo;
    }

    public void setTelefoneFixo(String telefoneFixo) {
        this.telefoneFixo = telefoneFixo;
    }

    public String getTelefoneContato() {
        return telefoneContato;
    }

    public void setTelefoneContato(String telefoneContato) {
        this.telefoneContato = telefoneContato;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    @NonNull
    @Override
    public String toString() {
        return getNomeCompleto();
    }
}
