package br.edu.ifsp.arq.dmos5_2020s1.agenda_dmos5.exceptions;

public class UsuarioDuplicadoException extends RuntimeException  {

    public UsuarioDuplicadoException(){};

    public UsuarioDuplicadoException(String message) {
        super(message);
    }
}
