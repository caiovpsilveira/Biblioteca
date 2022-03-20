/*
Caio Vinicius Perereira Silveira
Projeto Final Programacao de Computadores II - Biblioteca
22/08/2021
*/
package modelo;

public class EditoraJaCadastradaException extends Exception{

    private static final long serialVersionUID = 1L;
    public EditoraJaCadastradaException(String nomeEditora){
        super("A editora informada \""+nomeEditora+"\" ja esta cadastrada.\nO cadastro de editora foi cancelado.");
    }
}
