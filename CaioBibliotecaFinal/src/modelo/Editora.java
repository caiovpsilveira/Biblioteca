/*
Caio Vinicius Perereira Silveira
Projeto Final Programacao de Computadores II - Biblioteca
22/08/2021
*/
package modelo;

public class Editora {
    //Por enquanto a classe so contem o nome da editora.
    //A biblioteca poderia ter um ArrayList<String> editoras, mas
    //ter uma classe para editora facilita modificacoes futuras.
    String nomeEditora;
    
    public Editora(){
    }

    public String getNomeEditora() {
        return nomeEditora;
    }

    public void setNomeEditora(String nomeEditora) {
        this.nomeEditora = nomeEditora;
    }
}
