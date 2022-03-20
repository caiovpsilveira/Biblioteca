/*
Caio Vinicius Perereira Silveira
Projeto Final Programacao de Computadores II - Biblioteca
22/08/2021
*/
package modelo;

public class AutorJaCadastradoException extends Exception{

    private static final long serialVersionUID = 1L;
    public AutorJaCadastradoException(String primeiroNome, String segundoNome){
        super("O autor informado \""+primeiroNome+" "+segundoNome+"\" ja esta cadastrado.\nO cadastro do autor foi cancelado.");
    }
}
