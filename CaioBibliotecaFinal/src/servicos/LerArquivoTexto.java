/*
Caio Vinicius Perereira Silveira
Projeto Final Programacao de Computadores II - Biblioteca
22/08/2021
*/
package servicos;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import modelo.Autor;
import modelo.Biblioteca;
import modelo.Editora;
import modelo.Livro;

public class LerArquivoTexto {
    
    private static Scanner input;
    
    public static void abrirArquivo(String nomeArquivo){
        //criar arquivo se nao existir. necessario ao iniciar pela primeira vez.
        if(! new File(nomeArquivo).isFile()){
            EscreverArquivoTexto.abrirArquivo(nomeArquivo);
            EscreverArquivoTexto.adicionarString("");
            EscreverArquivoTexto.fecharArquivo();
        }
        
        try{
            input = new Scanner(Paths.get(nomeArquivo)); 
            input.useDelimiter(";");
        } 
        catch (IOException ioException){
            System.err.println("Erro ao abrir o arquivo. Encerrando");
            System.exit(1);
        }
    }
    
    /**
     * Le o conteudo do arquivo texto, salvando na biblioteca informada.
     * Todos os parametros da biblioteca informada são sobrescritos.
     * @param biblioteca
     */
    public static void lerConteudo(Biblioteca biblioteca){

        try{
            while(input.hasNext()){
                int numEditoras = Integer.parseInt(input.next());
                ArrayList<Editora> editorasBiblioteca = new ArrayList<>();
                for(int i=0; i<numEditoras;i++){
                    Editora editora = new Editora();
                    editora.setNomeEditora(input.next());
                    editorasBiblioteca.add(editora);
                }
                biblioteca.setEditoras(editorasBiblioteca);

                int numAutoresBiblioteca = Integer.parseInt(input.next());
                ArrayList<Autor> autoresBiblioteca = new ArrayList<>();
                for(int i=0; i<numAutoresBiblioteca;i++){
                    String primeiroNome = input.next();
                    String segundoNome = input.next();
                    Autor autor = new Autor();
                    autor.setPrimeiroNome(primeiroNome);
                    autor.setSegundoNome(segundoNome);
                    autoresBiblioteca.add(autor);
                }
                biblioteca.setAutores(autoresBiblioteca);

                int numLivrosBiblioteca = Integer.parseInt(input.next());
                ArrayList<Livro> livrosBiblioteca = new ArrayList<>();
                for(int i=0; i<numLivrosBiblioteca;i++){
                    Livro livro = new Livro();
                    livro.setIsbn(input.next());
                    livro.setTitulo(input.next());
                    livro.setNumEdicao(input.next());
                    livro.setCopyright(input.next());
                    livro.setArqImagem(input.next());
                    livro.setPreco(Float.parseFloat(input.next()));
                    livro.setEditora(biblioteca.pesquisarEditoraNome(input.next()));

                    int numAutoresLivro = Integer.parseInt(input.next());
                    ArrayList<Autor> autoresLivro = new ArrayList<>();
                    for(int j=0; j<numAutoresLivro;j++){
                        String primeiroNome = input.next();
                        String segundoNome = input.next();
                        Autor autor = biblioteca.pesquisarAutorPrimeiroESegundoNome(primeiroNome, segundoNome);
                        autor.adicionarLivro(livro); //visibilidade dupla
                        autoresLivro.add(autor);
                    }
                    livro.setAutores(autoresLivro);
                    livrosBiblioteca.add(livro);
                }
                biblioteca.setLivros(livrosBiblioteca);
            }
        }
        catch(NumberFormatException numberFormatEx){
            System.err.println("Erro ao ler preco ou numero de autores de um livro: arquivo texto contem caracteres nao inteiros/nao numericos na posicao. Encerrando");
            System.exit(1);
        }
        catch (NoSuchElementException elementException){
            System.err.println("Arquivo formatado de forma incorreta. Encerrando.");
            System.exit(1);
        } 
        catch (IllegalStateException stateException){
            System.err.println("Erro ao ler o arquivo. Encerrando.");
            System.exit(1);
        }
    }
    
    public static void fecharArquivo(){
        if (input != null){
            input.close();
        }
    }
}
