/*
Caio Vinicius Perereira Silveira
Projeto Final Programacao de Computadores II - Biblioteca
22/08/2021
*/
package modelo;

import java.util.ArrayList;

public class Livro {
    private String isbn;
    private String titulo;
    private String numEdicao;
    private String copyright;
    private String arqImagem; //representaria o nome de um arquivo, que poderia ser carregado.
    private float preco;
    private Editora editora;
    private ArrayList<Autor> autores;
    
    public Livro(){
        autores = new ArrayList<>();
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNumEdicao() {
        return numEdicao;
    }

    public void setNumEdicao(String numEdicao) {
        this.numEdicao = numEdicao;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getArqImagem() {
        return arqImagem;
    }

    public void setArqImagem(String arqImagem) {
        this.arqImagem = arqImagem;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public Editora getEditora() {
        return editora;
    }

    public void setEditora(Editora editora) {
        this.editora = editora;
    }

    public ArrayList<Autor> getAutores() {
        return autores;
    }

    public void setAutores(ArrayList<Autor> autores) {
        this.autores = autores;
    }
    
    /**
     * Adiciona um autor na colecao de autores do livro
     * @param autor 
     */
    public void adicionarAutor(Autor autor){
        autores.add(autor);
    }
    
    /**
     * Remove um autor da colecao de autores do livro
     * @param autor 
     */
    public void removerAutor(Autor autor){
        autores.remove(autor);
    }
}
