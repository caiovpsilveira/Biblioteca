/*
Caio Vinicius Perereira Silveira
Projeto Final Programacao de Computadores II - Biblioteca
22/08/2021
*/
package modelo;

public class IsbnRepetidoException extends Exception{

    private static final long serialVersionUID = 1L;
    public IsbnRepetidoException(String isbn){
        super("O ISBN informado \""+isbn+"\" ja esta cadastrado.\nO cadastro foi cancelado.");
    }
}
