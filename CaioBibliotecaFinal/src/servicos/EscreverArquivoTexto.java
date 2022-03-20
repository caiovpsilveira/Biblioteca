/*
Caio Vinicius Perereira Silveira
Projeto Final Programacao de Computadores II - Biblioteca
22/08/2021
*/
package servicos;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.FormatterClosedException;
import modelo.Autor;
import modelo.Biblioteca;
import modelo.Editora;
import modelo.Livro;

public class EscreverArquivoTexto {
    
    private static Formatter output;
    
    public static void abrirArquivo(String nomeArquivo){
        try{
            output = new Formatter(nomeArquivo);
        }
        catch (SecurityException securityException){
            System.err.println("Permissao de escrita negada. Encerrando");
            System.exit(1);
        } 
        catch (FileNotFoundException fileNotFoundException){
            System.err.printf("Arquivo \"%s\" nao encontrado. Encerrando.\n", nomeArquivo);
            System.exit(1);
        } 
    }

    /**
     * Adiciona a string informada no arquivo. Se o arquivo nao
     * existir, cria o arquivo informado em abrirArquivo.
     * @param escrito 
     */
    public static void adicionarString(String escrito){
        try{
            output.format(escrito);                         
        } 
        catch (FormatterClosedException formatterClosedException){
            System.err.println("Erro ao escrever no arquivo. Encerrando.");
            System.exit(1);
        }
    }
    
    /**
     * Adiciona todo o conteudo da biblioteca informada em um arquivo texto.
     * Primeriamente, salva as editoras, depois os autores, e por fim os livros.
     * @param biblioteca
     */
   public static void adicionarBiblioteca(Biblioteca biblioteca){
       ArrayList<Editora> editoras = biblioteca.getEditoras();
       ArrayList<Autor> autores = biblioteca.getAutores();
       ArrayList<Livro> arraylistLivro = biblioteca.getLivros();
       String conteudo = new String();
       
       conteudo += editoras.size() + ";";
       for(int i=0; i<editoras.size(); i++){
           conteudo += editoras.get(i).getNomeEditora() + ";";
       }
       
       conteudo += autores.size() + ";";
       for(int i=0; i<autores.size(); i++){
           conteudo += autores.get(i).getPrimeiroNome() + ";";
           conteudo += autores.get(i).getSegundoNome() + ";";
       }
       
       conteudo += arraylistLivro.size() + ";";
       for(int i=0; i< arraylistLivro.size(); i++){
           Livro livro = arraylistLivro.get(i);
           
           //adiciona as informacoes do livro, e informa por ultimo o numero de autores que o livro possui.
           conteudo += livro.getIsbn()+";"+livro.getTitulo()+";"+livro.getNumEdicao()+
                   ";"+livro.getCopyright()+";"+livro.getArqImagem()+";"+livro.getPreco()+";"+livro.getEditora().getNomeEditora();
           conteudo += ";"+livro.getAutores().size();
           
           for(int j=0; j<livro.getAutores().size();j++){
               Autor autor = livro.getAutores().get(j);
               
               conteudo += ";"+autor.getPrimeiroNome()+";"+autor.getSegundoNome();
               
               if(j == livro.getAutores().size()-1){
                   conteudo += ";";
               }
           }
        }
        output.format(conteudo);
   }
   
   public static void fecharArquivo(){
        if (output != null){
            output.close();
        }
    }
    
}
