/*
Caio Vinicius Perereira Silveira
Projeto Final Programacao de Computadores II - Biblioteca
22/08/2021
*/
package modelo;

import java.util.ArrayList;

public class Autor {
    private String primeiroNome;
    private String segundoNome;
    private ArrayList<Livro> livros;
    
    public Autor(){
        livros = new ArrayList<>();
    }

    public String getPrimeiroNome() {
        return primeiroNome;
    }

    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }

    public String getSegundoNome() {
        return segundoNome;
    }

    public void setSegundoNome(String segundoNome) {
        this.segundoNome = segundoNome;
    }

    public ArrayList<Livro> getLivros() {
        return livros;
    }

    public void setLivros(ArrayList<Livro> livros) {
        this.livros = livros;
    }
    
    /**
     * Adiciona um livro na colecao de livros do autor
     * @param livro 
     */
    public void adicionarLivro(Livro livro){
        livros.add(livro);
    }
    
    /**
     * Remove um livro da colecao de livros do autor
     * @param livro 
     */
    public void removerLivro(Livro livro){
        livros.remove(livro);
    }
}
