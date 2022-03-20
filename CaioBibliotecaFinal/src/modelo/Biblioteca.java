/*
Caio Vinicius Perereira Silveira
Projeto Final Programacao de Computadores II - Biblioteca
22/08/2021
*/
package modelo;

import java.util.ArrayList;

public class Biblioteca {
    private ArrayList<Livro> livros;
    private ArrayList<Autor> autores;
    private ArrayList<Editora> editoras;
    
    public Biblioteca(){
        livros = new ArrayList<>();
        autores = new ArrayList<>();
        editoras = new ArrayList<>();
    }

    public ArrayList<Livro> getLivros() {
        return livros;
    }

    public void setLivros(ArrayList<Livro> livros) {
        this.livros = livros;
    }

    public ArrayList<Autor> getAutores() {
        return autores;
    }

    public void setAutores(ArrayList<Autor> autores) {
        this.autores = autores;
    }

    public ArrayList<Editora> getEditoras() {
        return editoras;
    }

    public void setEditoras(ArrayList<Editora> editoras) {
        this.editoras = editoras;
    }
    
    /**
     * Cadastra um livro na biblioteca, desde que a biblioteca nao possua
     * outro livro com o mesmo ISBN. Caso contrario, lanca uma excecao 
     * informado de que tentaram cadastrar um livro com ISBN repetido.
     * @param livro
     * @throws IsbnRepetidoException 
     */
    public void cadastrarLivro(Livro livro) throws IsbnRepetidoException{
        String isbn = livro.getIsbn();
        if(this.pesquisarLivroIsbn(isbn).isEmpty()){
            livros.add(livro);
        }
        else{
            throw new IsbnRepetidoException(isbn);
        }
    }
    
    /**
     * Remove o livro da colecao de livros da biblioteca
     * @param livro 
     */
    public void removerLivro(Livro livro){
        livros.remove(livro);
    }
    
    /**
     * Adiciona o autor na colecao de autores da biblioteca, caso a bibliteca nao
     * possua outro autor com os mesmos primeiroNome e segundoNome. Caso contrario,
     * lanca uma excecao informando de que tentaram cadastrar duas vezes o mesmo autor.
     * @param autor 
     * @throws AutorJaCadastradoException
     */
    public void cadastrarAutor(Autor autor) throws AutorJaCadastradoException{
        String primeiroNome = autor.getPrimeiroNome();
        String segundoNome = autor.getSegundoNome();
        if(this.pesquisarAutorPrimeiroESegundoNome(primeiroNome, segundoNome) == null){
            autores.add(autor);
        }
        else{
            throw new AutorJaCadastradoException(primeiroNome, segundoNome);
        }
    }
    
    /**
     * Remove o autor da colecao de autores da biblioteca
     * @param autor 
     */
    public void removerAutor(Autor autor){
        autores.remove(autor);
    }
    
    /**
     * Adiciona uma editora na colecao de editoras da biblioteca. Caso a biblioteca
     * Ja possua outra editora com o mesmo nome, lanca uma excecao informando
     * sobre o nome repetido.
     * @param editora 
     * @throws EditoraJaCadastradaException
     */
    public void cadastrarEditora(Editora editora) throws EditoraJaCadastradaException{
        if(this.pesquisarEditoraNome(editora.getNomeEditora()) == null){ //se nao existe outra editora com esse nome
            editoras.add(editora);
        }
        else{
            throw new EditoraJaCadastradaException(editora.getNomeEditora());
        }
    }
    
    /**
     * Remove a editora da colecao de editoras da biblioteca
     * @param editora 
     */
    public void removerEditora(Editora editora){
        editoras.remove(editora);
    }
    
    /**
     * Pesquisa se a bibliteca possui uma editora com o nome pesquisado.
     * Se existir, retorna a editora. Se não, retorna null.
     * @param nomeEditora
     * @return Editora pesquisada, caso exista, null, caso contrario.
     */
    public Editora pesquisarEditoraNome(String nomeEditora){
        Editora retorno = null;
        for(int i=0; i<editoras.size();i++){
            if(editoras.get(i).getNomeEditora().equals(nomeEditora)){
                retorno = editoras.get(i);
            }
        }
        return retorno;
    }
    /**
     * Retorna um ArrayList de Livro, que contem como editora
     * a editora informada. Esse metodo NAO acessa o arraylist
     * editoras da biblioteca, que somente serve para controlar quais editoras
     * ja estao cadastradas. Isso acontece porque na interface as editoras
     * do livro são acessadas pelas que ja estao cadastradas.
     * @param nomeEditora
     * @return ArrayList Livro
     */
    public ArrayList<Livro> pesquisarLivrosEditora(String nomeEditora){
        ArrayList<Livro> retorno = new ArrayList<>();
        
        for(int i=0; i<livros.size(); i++){
            if(livros.get(i).getEditora().getNomeEditora().equals(nomeEditora)){
                retorno.add(livros.get(i));
            }
        }
        
        return retorno;
    }
    
    /**
     * Retorna um ArrayList dos Autores que contenham o primeiroNome informado
     * @param primeiroNome
     * @return ArrayList Autor
     */
    public ArrayList<Autor> pesquisarAutorPrimeiroNome(String primeiroNome){
        ArrayList<Autor> retorno = new ArrayList<>();
        
        for(int i=0; i<autores.size(); i++){
            if(autores.get(i).getPrimeiroNome().equals(primeiroNome)){
                retorno.add(autores.get(i));
            }
        }
        
        return retorno;
    }
    
    /**
     * Retorna um ArrayList dos Autores que contenham o segundoNome informado
     * @param segundoNome
     * @return ArrayList Autor
     */
    public ArrayList<Autor> pesquisarAutorSegundoNome(String segundoNome){
        ArrayList<Autor> retorno = new ArrayList<>();
        
        for(int i=0; i<autores.size(); i++){
            if(autores.get(i).getSegundoNome().equals(segundoNome)){
                retorno.add(autores.get(i));
            }
        }
        
        return retorno;
    }
    
    /**
     * Retorna um ArrayList dos Autores que contenham o primeiroNome E o segundoNome informados
     * @param primeiroNome 
     * @param segundoNome
     * @return ArrayList Autor
     */
    public Autor pesquisarAutorPrimeiroESegundoNome(String primeiroNome, String segundoNome){
        Autor retorno = null;
        
        for(int i=0; i<autores.size(); i++){
            if(autores.get(i).getPrimeiroNome().equals(primeiroNome) && autores.get(i).getSegundoNome().equals(segundoNome)){
                retorno = autores.get(i);
            }
        }
        
        return retorno;
    }
    
    /**
     * Retorna um arraylist contendo todos os livros da biblioteca que possuam a string
     * informada como isbn.
     * @param isbn
     * @return 
     */
    public ArrayList<Livro> pesquisarLivroIsbn(String isbn){
        //Apesar da biblioteca aceitar somente um livro por ISBN, retornar um ArrayList
        //facilita mudancas posteriores.
        ArrayList<Livro> retorno = new ArrayList<>();
        
        for(int i=0; i<livros.size(); i++){
            if(livros.get(i).getIsbn().equals(isbn)){
                retorno.add(livros.get(i));
            }
        }
        
        return retorno;
    }
    
    /**
     * Retorna um arraylist contendo todos os livros da biblioteca que possuam a string
     * informada como Titulo.
     * @param titulo
     * @return ArrayList Livro
     */
    public ArrayList<Livro> pesquisarLivroTitulo(String titulo){
        ArrayList<Livro> retorno = new ArrayList<>();
        
        for(int i=0; i<livros.size(); i++){
            if(livros.get(i).getTitulo().equals(titulo)){
                retorno.add(livros.get(i));
            }
        }
        
        return retorno;
    }
    
    /**
     * Retorna um arraylist contendo todos os livros da biblioteca que possuam a string
     * informada como numEdicao.
     * @param numEdicao
     * @return ArrayList Livro
     */
    public ArrayList<Livro> pesquisarLivroNumEdicao(String numEdicao){
        ArrayList<Livro> retorno = new ArrayList<>();
        
        for(int i=0; i<livros.size(); i++){
            if(livros.get(i).getNumEdicao().equals(numEdicao)){
                retorno.add(livros.get(i));
            }
        }
        
        return retorno;
    }
    
    /**
     * Retorna um arraylist contendo todos os livros da biblioteca que possuam a string
     * informada como copyright.
     * @param copyright
     * @return ArrayList Livro
     */
    public ArrayList<Livro> pesquisarLivroCopyright(String copyright){
        ArrayList<Livro> retorno = new ArrayList<>();
        
        for(int i=0; i<livros.size(); i++){
            if(livros.get(i).getCopyright().equals(copyright)){
                retorno.add(livros.get(i));
            }
        }
        
        return retorno;
    }
    
    /**
     * Retorna um arraylist contendo todos os livros na biblioteca que contenham a String como
     * arquivo imagem
     * @param arqImagem
     * @return ArrayList Livro
     */
    public ArrayList<Livro> pesquisarLivroArquivoImagem(String arqImagem){
        ArrayList<Livro> retorno = new ArrayList<>();
        
        for(int i=0; i<livros.size(); i++){
            if(livros.get(i).getArqImagem().equals(arqImagem)){
                retorno.add(livros.get(i));
            }
        }
        
        return retorno;
    }
    
    /**
     * Retorna um arraylist contendo todos os livros da biblioteca que possuam um
     * preco abaixo ou igual do preco informado.
     * @param preco
     * @return 
     */
    public ArrayList<Livro> pesquisarLivroPrecoAbaixo(float preco){
        ArrayList<Livro> retorno = new ArrayList<>();
        
        for(int i=0; i<livros.size();i++){
            if(livros.get(i).getPreco() <= preco){
                retorno.add(livros.get(i));
            }
        }
        
        return retorno;
    }
    
    /**
     * Retorna um arraylist dos livros que contenham pelo menos 1 dos autores informados no arraylist.
     * Utilidade: quando informar uma busca generica: todos os livros que contenham "caio" como
     * primeiro nome do autor, ou todos os livros que contenham "vinicius" como segundo nome no autor, etc,
     * visto que o pesquisarAutorPrimeiroNome e derivados retornam um arraylist com qualquer ocorrencia em
     * primeiro nome.
     * 
     * Esse metodo NAO serve para procurar livros que contenham TODOS os autores informados no arraylist.
     * Para isso, usar pesquisarLivroAutoresExatos.
     * 
     * @param arraylist
     * @return Um arrayList de livros que contenham como um de seus autores qualquer um dos autores informados no arraylist
     */
    public ArrayList<Livro> pesquisarLivroAutores(ArrayList<Autor> arraylist){
        
        /*System.out.println("Autores recebidos");
        for(int i=0; i<arraylist.size();i++){
            System.out.println(arraylist.get(i).getPrimeiroNome() +" "+ arraylist.get(i).getSegundoNome());
        }*/
        
        ArrayList<Livro> retorno = new ArrayList<>();
        ArrayList<Autor> livroAutores;
        
        if(!arraylist.isEmpty()){ //se estiver vazio e nao verificar retorna todos os livros da biblioteca.
            for(int i=0; i<livros.size(); i++){
                livroAutores = livros.get(i).getAutores();
                for(int j=0; j<arraylist.size();j++){
                    if(livroAutores.contains(arraylist.get(j))){ //se o livro nao contem um dos autores informados
                        retorno.add(livros.get(i));
                        break;
                    }
                }
            }
        }
        
        return retorno;
    }
    
    /**
     * Retorna um arrraylist de livros que contenham, dentro dos seus autores, pelo menos
     * todos os autores listados. O arraylist retornado tambem pode conter livros que contenham mais
     * autores do que o informado, desde que todos os informados estejam contidos
     * dentro da sua colecao.
     * @param arraylist
     * @return 
     */
    public ArrayList<Livro> pesquisarLivroAutoresExatos(ArrayList<Autor> arraylist){
        ArrayList<Livro> retorno = new ArrayList<>();
        for(int i=0; i<livros.size();i++){
            if(livros.get(i).getAutores().containsAll(arraylist)){
                retorno.add(livros.get(i));
            }
        }
        
        return retorno;
    }
}
