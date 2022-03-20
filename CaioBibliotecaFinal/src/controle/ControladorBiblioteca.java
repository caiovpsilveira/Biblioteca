/*
Caio Vinicius Perereira Silveira
Projeto Final Programacao de Computadores II - Biblioteca
22/08/2021
*/
package controle;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import modelo.Autor;
import modelo.AutorJaCadastradoException;
import modelo.Biblioteca;
import modelo.Editora;
import modelo.EditoraJaCadastradaException;
import modelo.IsbnRepetidoException;
import modelo.Livro;

public class ControladorBiblioteca implements Initializable {
    
    private Biblioteca biblioteca;
    private HashMap<String, Editora> itensComboEditora; //responsavel por associar os itens String da combo box com a respectiva editora
    private HashMap <CheckMenuItem, Autor> itensMenuAutor; //responsavel por associar CheckMenuItem com os respectivos autores
    private HashMap <String, Livro> itensBusca;  //responsavel por associar os itens de ListView<String> com os respectivos livros
    
    public ControladorBiblioteca(){
        biblioteca = new Biblioteca();
        
        servicos.LerArquivoTexto.abrirArquivo("biblioteca.txt");
        servicos.LerArquivoTexto.lerConteudo(biblioteca);
        servicos.LerArquivoTexto.fecharArquivo();
        
        itensComboEditora = new HashMap<>();
        itensMenuAutor = new HashMap<>();
        itensBusca = new HashMap<>();
    }
    
    @FXML
    private Label labelEditoraDados, labelEditoraNome,
            labelAutorDados, labelAutorPrimeiroNome, labelAutorSegundoNome,
            labelLivroIsbn, labelLivroTitulo, labelLivroNumEdicao, labelLivroCopyright, labelLivroArquivoImagem, labelLivroPreco,
            labelLivroEditora, labelLivroAutores,
            labelBuscaLivros;
    @FXML
    private TextField textEditoraNome,
            textAutorPrimeiroNome, textAutorSegundoNome,
            textLivroIsbn, textLivroTitulo, textLivroNumEdicao, textLivroCopyright, textLivroArquivoImagem, textLivroPreco;
    @FXML
    private Button buttonEditoraIncluir, buttonEditoraPesquisar, buttonEditoraCancelar,
            buttonAutorIncluir, buttonAutorPesquisar, buttonAutorCancelar,
            buttonLivroIncluir, buttonLivroPesquisar, buttonLivroCancelar,
            buttonBuscaTodos, buttonBuscaLimpar,
            buttonSair;
    @FXML
    private ComboBox<String> comboEditora;
    @FXML
    private MenuButton menuAutores;
    @FXML
    private ListView<String> listResultado;
    
    @FXML
    private void handleButtonAction(ActionEvent e) {
        //EDITORA
            //INCLUIR
        if(e.getSource().equals(buttonEditoraIncluir)){
            String nomeEditora = textEditoraNome.getText();
            if(nomeEditora.isEmpty()){
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Por favor, informe um campo da editora");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, preencha o campo da editora para cadastra-la.");
                alert.showAndWait();
            }
            //Caso contenha ; no nome, ficara formatado de forma incorreta na hora de salvar o arquivo texto
            else if(nomeEditora.contains(";")){
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Erro ao cadastrar editora");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, nao coloque ; nos campos do cadastro.");
                alert.showAndWait();
            }
            else{
                try{
                    Editora editora = new Editora();
                    editora.setNomeEditora(nomeEditora);
                    biblioteca.cadastrarEditora(editora);
                    
                    atualizarComboEditora(editora);
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Cadastro Realizado");
                    alert.setHeaderText(null);
                    alert.setContentText("A editora \""+ nomeEditora+"\" foi cadastrada com sucesso.");
                    alert.showAndWait();
                    
                    limparEditora();
                }
                catch(EditoraJaCadastradaException ex){
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Erro ao cadastrar editora");
                    alert.setHeaderText(null);
                    alert.setContentText(ex.getMessage());
                    alert.showAndWait();
                }
            }
        }
            //PESQUISAR
        else if (e.getSource().equals(buttonEditoraPesquisar)){
            String nomeEditora = textEditoraNome.getText();
            if(!nomeEditora.isEmpty()){
                if(biblioteca.pesquisarEditoraNome(nomeEditora) != null){
                    if(biblioteca.pesquisarLivrosEditora(nomeEditora).isEmpty()){
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Erro ao pesquisar editora");
                        alert.setHeaderText(null);
                        alert.setContentText("A editora \""+nomeEditora+"\" esta cadastrada, mas nao possui livros na biblioteca.");
                        alert.showAndWait(); 
                    }
                    else{
                        labelBuscaLivros.setText("Exibindo resultados para busca editora \""+nomeEditora+"\":");
                        atualizarBuscaLivro(biblioteca.pesquisarLivrosEditora(nomeEditora));
                        
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Pesquisa realizada");
                        alert.setHeaderText(null);
                        alert.setContentText("os livros encontradas para a editora \""+nomeEditora+"\" foram listados na aba \"Resultado Busca\".");
                        alert.showAndWait(); 
                    }
                    
                }
                else{ //Se a biblioteca nao possui a editora.
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Erro ao pesquisar editora");
                    alert.setHeaderText(null);
                    alert.setContentText("A editora \""+nomeEditora+"\" nao esta cadastrada na biblioteca.");
                    alert.showAndWait();
                }
            }
        }
            //CANCELAR
        else if (e.getSource().equals(buttonEditoraCancelar)){
            limparEditora();
        }
        //AUTOR
            //INCLUIR
        else if (e.getSource().equals(buttonAutorIncluir)){
            String primeiroNome = textAutorPrimeiroNome.getText();
            String segundoNome = textAutorSegundoNome.getText();
            if(primeiroNome.isEmpty() || segundoNome.isEmpty()){ //Primeiro ou segundo campo vazios
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Erro no cadastro de autor");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, informe todos os campos para cadastrar o autor");
                alert.showAndWait(); 
            }
            //Caso contenha ; no nome, ficara formatado de forma incorreta na hora de salvar o arquivo texto
            else if(primeiroNome.contains(";") || segundoNome.contains(";")){
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Erro ao cadastrar autor");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, nao coloque ; nos campos do cadastro.");
                alert.showAndWait();
            }
            else{
                try{
                    Autor autor = new Autor();
                    autor.setPrimeiroNome(primeiroNome);
                    autor.setSegundoNome(segundoNome);
                    biblioteca.cadastrarAutor(autor);
                    atualizarMenuAutores(autor);
                    
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Autor cadastrado");
                    alert.setHeaderText(null);
                    alert.setContentText("O autor \""+primeiroNome+" "+segundoNome+"\" foi cadastrado com sucesso.");
                    alert.showAndWait();
                    
                    limparAutor();
                }
                catch(AutorJaCadastradoException ex){
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Erro ao cadastrar autor");
                    alert.setHeaderText(null);
                    alert.setContentText(ex.getMessage());
                    alert.showAndWait(); 
                }
            }
        }
            //PESQUISAR
        else if (e.getSource().equals(buttonAutorPesquisar)){
            String primeiroNome = textAutorPrimeiroNome.getText();
            String segundoNome = textAutorSegundoNome.getText();
            ArrayList<Livro> resultado;
            ArrayList<Autor> autoresEncontrados = null;
            
            if(!primeiroNome.isEmpty() && segundoNome.isEmpty()){ //Somente primeiro campo preenchido
                autoresEncontrados = biblioteca.pesquisarAutorPrimeiroNome(primeiroNome);
            }
            else if(primeiroNome.isEmpty() && !segundoNome.isEmpty()){ //Somente segundo campo preenchido
                autoresEncontrados = biblioteca.pesquisarAutorSegundoNome(segundoNome);
            }
            else if(!primeiroNome.isEmpty() && !segundoNome.isEmpty()){ //Ambos os campos preenchidos
                autoresEncontrados = new ArrayList<>(); // retirar o null, que sera verificado adiante para mostrar avisos.
                if(biblioteca.pesquisarAutorPrimeiroESegundoNome(primeiroNome, segundoNome) != null){
                    autoresEncontrados.add(biblioteca.pesquisarAutorPrimeiroESegundoNome(primeiroNome, segundoNome));
                }
            }
            else{ //NENHUM campo prenchido e tentou pesquisar
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Erro ao pesquisar autor");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, informe pelo menos um dos campos de autor para pesquisar.");
                alert.showAndWait();
            }
            
            if(autoresEncontrados != null){//prevenir nullPointerException: so aconteceria no caso de nao informar nenhum campo
                if(autoresEncontrados.isEmpty()){ //Se a biblioteca nao encontrou nenhum autor com o(s) nome(s) informados
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Erro ao pesquisar autor");
                    alert.setHeaderText(null);
                    alert.setContentText("Nao existem autores cadastrados com com o(s) campo(s) informado(s).");
                    alert.showAndWait();
                }
                else{
                    resultado = biblioteca.pesquisarLivroAutores(autoresEncontrados);
                    if(resultado.isEmpty()){ //Se encontrou autores, mas esses nao possuem livros na biblioteca
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Erro ao pesquisar autor");
                        alert.setHeaderText(null);
                        alert.setContentText("Existem autores com o(s) campo(s) informado(s), mas estes nao possuem livros cadastrados.");
                        alert.showAndWait();
                    }
                    else{
                        labelBuscaLivros.setText("Exibindo resultados para busca autor: \""+primeiroNome+" "+segundoNome+"\":");
                        atualizarBuscaLivro(resultado);
                        
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Pesquisa realizada");
                        alert.setHeaderText(null);
                        alert.setContentText("os livros encontradas para o autor \""+primeiroNome+" "+segundoNome+"\" foram listados na aba \"Resultado Busca\".");
                        alert.showAndWait(); 
                    }
                }    
            }
        }
            //CANCELAR
        else if (e.getSource().equals(buttonAutorCancelar)){
            limparAutor();
        }
        //LIVRO
            //INCLUIR
        else if (e.getSource().equals(buttonLivroIncluir)){
            String isbn, titulo, numEdicao, copyright, arqImagem, stringPreco;
            Editora editora;
            ArrayList<Autor> autoresSelecionados = buscarAutoresSelecionados();
            
            isbn = textLivroIsbn.getText();
            titulo = textLivroTitulo.getText();
            numEdicao = textLivroNumEdicao.getText();
            copyright = textLivroCopyright.getText();
            arqImagem = textLivroArquivoImagem.getText();
            stringPreco = textLivroPreco.getText();
            float preco;
            editora = itensComboEditora.get(comboEditora.getSelectionModel().getSelectedItem());
            
            //Se qualquer um dos campos estiver vazio:
            if(isbn.isEmpty() || titulo.isEmpty() || numEdicao.isEmpty() || copyright.isEmpty() || arqImagem.isEmpty() || stringPreco.isEmpty() || editora == null || autoresSelecionados.isEmpty()){
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Erro ao cadastrar livro");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, informe todos os campos para cadastrar o livro.");
                alert.showAndWait();
            }
            //Caso contenha ; no nome, ficara formatado de forma incorreta na hora de salvar o arquivo texto
            else if(isbn.contains(";") || titulo.contains(";") || numEdicao.contains(";") || copyright.contains(";") || arqImagem.contains(";") || stringPreco.contains(";")){
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Erro ao cadastrar livro");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, nao coloque ; nos campos do cadastro.");
                alert.showAndWait();
            }
            else{
                try{
                    preco = Float.parseFloat(stringPreco);
                    
                    Livro livro = new Livro();
                    livro.setIsbn(isbn);
                    livro.setTitulo(titulo);
                    livro.setNumEdicao(numEdicao);
                    livro.setCopyright(copyright);
                    livro.setArqImagem(arqImagem);
                    livro.setPreco(preco);
                    livro.setEditora(editora);
                    livro.setAutores(autoresSelecionados);
                    biblioteca.cadastrarLivro(livro);

                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Cadastro de livro realizado");
                    alert.setHeaderText(null);
                    alert.setContentText("O livro \""+titulo+"\" foi cadastrado com sucesso.");
                    alert.showAndWait();
                    
                    limparLivro();
                }
                catch(NumberFormatException numberFormatEx){
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Erro ao cadastrar livro");
                    alert.setHeaderText(null);
                    alert.setContentText("Por favor, informe um valor NUMERICO para preco.\nPara valores decimais, utilize o ponto (.).\nO cadastro foi cancelado.");
                    alert.showAndWait();
                }
                catch(IsbnRepetidoException ex){
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Erro ao cadastrar livro");
                    alert.setHeaderText(null);
                    alert.setContentText(ex.getMessage());
                    alert.showAndWait();
                }
            }
        }
            //PESQUISAR
        else if (e.getSource().equals(buttonLivroPesquisar)){
            //A pesquisa no livro ocorre buscando os campos de cima pra baixo, pesquisando somente o primeiro campo preenchido
            if(!textLivroIsbn.getText().isEmpty()){
                String isbn = textLivroIsbn.getText();
                ArrayList<Livro> resultado = biblioteca.pesquisarLivroIsbn(isbn);
                if(resultado.isEmpty()){
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Nao foram encontrados livros");
                    alert.setHeaderText(null);
                    alert.setContentText("Nao foram encontrados livros com o ISBN \""+isbn+"\".");
                    alert.showAndWait();
                }
                else{
                    labelBuscaLivros.setText("Exibindo resultados para o ISBN: "+isbn);
                    atualizarBuscaLivro(resultado);
                    criarAvisoPesquisaLivroSucesso();
                }
            }
            else if(!textLivroTitulo.getText().isEmpty()){
                String titulo = textLivroTitulo.getText();
                ArrayList<Livro> resultado = biblioteca.pesquisarLivroTitulo(titulo);
                if(resultado.isEmpty()){
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Nao foram encontrados livros");
                    alert.setHeaderText(null);
                    alert.setContentText("Nao foram encontrados livros com o Titulo \""+titulo+"\".");
                    alert.showAndWait();
                }
                else{
                    labelBuscaLivros.setText("Exibindo resultados para o Titulo: "+titulo);
                    atualizarBuscaLivro(resultado);
                    criarAvisoPesquisaLivroSucesso();
                }
            }
            else if(!textLivroNumEdicao.getText().isEmpty()){
                String numEdicao = textLivroNumEdicao.getText();
                ArrayList<Livro> resultado = biblioteca.pesquisarLivroNumEdicao(numEdicao);
                if(resultado.isEmpty()){
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Nao foram encontrados livros");
                    alert.setHeaderText(null);
                    alert.setContentText("Nao foram encontrados livros com o numero de edicao \""+numEdicao+"\".");
                    alert.showAndWait();
                }
                else{
                    labelBuscaLivros.setText("Exibindo resultados para numero de edicao: "+numEdicao);
                    atualizarBuscaLivro(resultado);
                    criarAvisoPesquisaLivroSucesso();
                }
            }
            else if(!textLivroCopyright.getText().isEmpty()){
                String copyright = textLivroCopyright.getText();
                ArrayList<Livro> resultado = biblioteca.pesquisarLivroCopyright(copyright);
                if(resultado.isEmpty()){
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Nao foram encontrados livros");
                    alert.setHeaderText(null);
                    alert.setContentText("Nao foram encontrados livros com Copyright \""+copyright+"\".");
                    alert.showAndWait();
                }
                else{
                    labelBuscaLivros.setText("Exibindo resultados para Copyright: "+copyright);
                    atualizarBuscaLivro(resultado);
                    criarAvisoPesquisaLivroSucesso();
                }
            }
            else if(!textLivroArquivoImagem.getText().isEmpty()){
                String arqImagem = textLivroArquivoImagem.getText();
                ArrayList<Livro> resultado = biblioteca.pesquisarLivroArquivoImagem(arqImagem);
                if(resultado.isEmpty()){
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Nao foram encontrados livros");
                    alert.setHeaderText(null);
                    alert.setContentText("Nao foram encontrados livros com Arquivo imagem \""+arqImagem+"\".");
                    alert.showAndWait();
                }
                else{
                    labelBuscaLivros.setText("Exibindo resultados para arquivo imagem: "+arqImagem);
                    atualizarBuscaLivro(resultado);
                    criarAvisoPesquisaLivroSucesso();
                }
            }
            else if(!textLivroPreco.getText().isEmpty()){
                String stringPreco = textLivroPreco.getText();
                try{
                    float preco = Float.parseFloat(stringPreco);
                    ArrayList<Livro> resultado = biblioteca.pesquisarLivroPrecoAbaixo(preco);
                    if(resultado.isEmpty()){
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Nao foram encontrados livros");
                        alert.setHeaderText(null);
                        alert.setContentText("Nao foram encontrados livros com preco igual ou abaixo de "+preco);
                        alert.showAndWait();
                    }
                    else{
                        labelBuscaLivros.setText("Exibindo resultados para preco menor ou igual a: "+preco);
                        atualizarBuscaLivro(resultado);
                        criarAvisoPesquisaLivroSucesso();
                    }
                }
                catch(NumberFormatException numberFormatEx){
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Erro ao cadastrar livro");
                    alert.setHeaderText(null);
                    alert.setContentText("Por favor, informe um valor NUMERICO para preco.\nPara valores decimais, utilize o ponto (.).");
                    alert.showAndWait();
                }
            }
            else if(buscarEditoraSelecionada() != null){ //se algo esta selecionado para editora
                String nomeEditora = buscarEditoraSelecionada().getNomeEditora();
                ArrayList<Livro> resultado= biblioteca.pesquisarLivrosEditora(nomeEditora);
                if(resultado.isEmpty()){
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Nao foram encontrados livros");
                    alert.setHeaderText(null);
                    alert.setContentText("Nao foram encontrados livros para a editora \""+nomeEditora+"\".");
                    alert.showAndWait();
                }
                else{
                    labelBuscaLivros.setText("Exibindo resultados para busca editora \""+nomeEditora+"\":");
                    atualizarBuscaLivro(resultado);
                    criarAvisoPesquisaLivroSucesso();
                }
            }
            else{ //menu autor
                ArrayList<Autor> autores_selecionados = buscarAutoresSelecionados();
                if(!autores_selecionados.isEmpty()){
                    ArrayList<Livro> resultado = biblioteca.pesquisarLivroAutoresExatos(autores_selecionados);
                    String nomeAutores = new String();
                    for(int i=0; i<autores_selecionados.size(); i++){
                        nomeAutores += autores_selecionados.get(i).getPrimeiroNome();
                        nomeAutores += " ";
                        nomeAutores += autores_selecionados.get(i).getSegundoNome();
                        if(i != autores_selecionados.size()-1){
                            nomeAutores += " E ";
                        }
                        else{
                            nomeAutores += ".";
                        }
                    }
                    if(resultado.isEmpty()){
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Nao foram encontrados livros");
                        alert.setHeaderText(null);
                        alert.setContentText("Nao foram encontrados livros para o(s) autor(es): "+nomeAutores);
                        alert.showAndWait();
                    }
                    else{
                        labelBuscaLivros.setText("Exibindo resultados para o(s) autor(es): "+nomeAutores);
                        atualizarBuscaLivro(biblioteca.pesquisarLivroAutoresExatos(autores_selecionados));
                        criarAvisoPesquisaLivroSucesso();
                    }
                }
                else{
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Erro ao pesquisar");
                    alert.setHeaderText(null);
                    alert.setContentText("Por favor, informe pelo menos um campo para pesquisar livros.");
                    alert.showAndWait(); 
                }
            }
        }
            //CANCELAR
        else if (e.getSource().equals(buttonLivroCancelar)){
            limparLivro();
        }
        //RESULTADO BUSCA
            //LISTAR TODOS
        else if(e.getSource().equals(buttonBuscaTodos)){
            labelBuscaLivros.setText("Exibindo todos os livros cadastrados.");
            atualizarBuscaLivro(biblioteca.getLivros());
        }
            //LIMPAR LISTA BUSCA
        else if(e.getSource().equals(buttonBuscaLimpar)){
            labelBuscaLivros.setText("");
            listResultado.getItems().clear();
        }
        //SALVAR E SAIR
        else if(e.getSource().equals(buttonSair)){
            servicos.EscreverArquivoTexto.abrirArquivo("biblioteca.txt");
            servicos.EscreverArquivoTexto.adicionarBiblioteca(biblioteca);
            servicos.EscreverArquivoTexto.fecharArquivo();
            System.exit(0);
        }
    }
    
    @FXML
    private void handleMouseClick(MouseEvent e) { //Evento de click na ListView (lista de resultados das pesquisas)
        if(listResultado.getSelectionModel().getSelectedItem() != null){
            String stringSelecionada = listResultado.getSelectionModel().getSelectedItem();
            Livro livroSelecionado = itensBusca.get(stringSelecionada);

            //Criar um alerta com todos os dados do livro selecionado
            String conteudoDialogo = new String();
            conteudoDialogo += "ISBN: " + livroSelecionado.getIsbn();
            conteudoDialogo += "\nTitulo: " + livroSelecionado.getTitulo();
            conteudoDialogo += "\nNumero de edicao: " + livroSelecionado.getNumEdicao();
            conteudoDialogo += "\nCopyright: " + livroSelecionado.getCopyright();
            conteudoDialogo += "\nArquivo Imagem: " + livroSelecionado.getArqImagem();
            conteudoDialogo += "\nPreco: " + String.valueOf(livroSelecionado.getPreco());
            conteudoDialogo += "\nEditora: " + livroSelecionado.getEditora().getNomeEditora();
            conteudoDialogo += "\nAutores: ";
            for(int i=0; i<livroSelecionado.getAutores().size(); i++){
                conteudoDialogo += livroSelecionado.getAutores().get(i).getPrimeiroNome() + " " + livroSelecionado.getAutores().get(i).getSegundoNome();
                if(i<livroSelecionado.getAutores().size()-1){
                    conteudoDialogo += ", ";
                }
            }
            conteudoDialogo += ".";

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle(livroSelecionado.getTitulo());
            alert.setHeaderText(livroSelecionado.getTitulo());
            alert.setContentText(conteudoDialogo);
            alert.showAndWait();
        }
    }
    
    /**
     * Limpa todos os campos da aba "Editora"
     */
    private void limparEditora(){
        textEditoraNome.setText("");
    }
    
    /**
     * Inicializa a combobox editora apos carregar as editoras do arquivo texto.
     * Tambem associa cada item String da comboBox editora a uma editora.
     * @param editoras 
     */
    private void inicializarComboEditora(ArrayList<Editora> editoras){
        for(int i=0; i<editoras.size();i++){
            Editora editora = editoras.get(i);
            String nomeEditora = editora.getNomeEditora();
            itensComboEditora.put(nomeEditora, editora);
            comboEditora.getItems().add(nomeEditora);
        }
    }
    
    /**
     * Cria uma nova opcao na comboBox Editora, localizada na aba "Livro",
     * referente a uma nova editora cadastrada. Como a editora e uma String
     * e nao possui classe propria, nao e necessario associar a String do
     * item ComboEditora<String> a um objeto do tipo Editora, que poderia
     * ser realizado por um HashMap<String, Editora>.
     * @param novaEditora 
     */
    private void atualizarComboEditora(Editora novaEditora){
        itensComboEditora.put(novaEditora.getNomeEditora(), novaEditora);
        comboEditora.getItems().add(novaEditora.getNomeEditora());
    }
    
    /**
     * Retorna a editora selecionada, atraves do HashMap <String, Editora>
     * itensComboEditora. Caso nao exista nenhuma selecao na combo box,
     * retorna null.
     * @return 
     */
    private Editora buscarEditoraSelecionada(){
        Editora retorno = null;
        String nomeEditora = comboEditora.getSelectionModel().getSelectedItem();
        retorno = itensComboEditora.get(nomeEditora);
        return retorno;
    }
    
    /**
     * Limpa todos os campos da aba "Autor"
     */
    private void limparAutor(){
        textAutorPrimeiroNome.setText("");
        textAutorSegundoNome.setText("");
    }
    
    /**
     * Busca os CheckMenuItens presentes em menuAutores que estao selecionados,
     * e busca no HashMap<CheckMenuItem, Autor> o autor equivalente.
     * Retorna um ArrayList contendo todos os autores selecionados
     * @return 
     */
    private ArrayList<Autor> buscarAutoresSelecionados(){
        //for each CheckMenuItem item : itensMenuAutor.keySey()... if item.isSelected()... autores_selecionados.add item
        //em programacao funcional. (Sugestao do netbeans...)
        ArrayList<Autor> autores_selecionados = new ArrayList<>();
                itensMenuAutor.keySet().stream().filter(item -> (item.isSelected())).forEachOrdered(item -> {
                    autores_selecionados.add(itensMenuAutor.get(item));
                });
        return autores_selecionados;
    }
    
    /**
     * Inicializa a selecao de autores apos carregar do arquivo texto,
     * criando e mapeando um checkMenuItem para cada autor.
     * @param arraylist 
     */
    private void inicializarMenuAutor(ArrayList<Autor> arraylist){
        for(int i=0; i<arraylist.size();i++){
            Autor autor = arraylist.get(i);
            CheckMenuItem item = new CheckMenuItem(autor.getPrimeiroNome()+" "+autor.getSegundoNome());
            menuAutores.getItems().add(item);
            itensMenuAutor.put(item, autor);
        }
    }
    
    /**
     * Adiciona um novo CheckMenuItem no menuButton menuAutores, localizado
     * na aba "Livro". Também associa esse novo CheckMenuItem a um autor,
     * por meio do HashMap<CheckMenuItem, Autor> itensMenuAutor, para que se possa
     * obter os autores do cadastro do livro, buscando os autores selecionados.
     * @param novoAutor 
     */
    private void atualizarMenuAutores(Autor novoAutor){
        CheckMenuItem item = new CheckMenuItem(novoAutor.getPrimeiroNome()+" "+novoAutor.getSegundoNome());
        menuAutores.getItems().add(item);
        itensMenuAutor.put(item, novoAutor);
    }
    
    /**
     * Limpa todos os campos da aba "Livro".
     */
    private void limparLivro(){
        textLivroIsbn.setText("");
        textLivroTitulo.setText("");
        textLivroNumEdicao.setText("");
        textLivroCopyright.setText("");
        textLivroArquivoImagem.setText("");
        textLivroPreco.setText("");
        comboEditora.getSelectionModel().clearSelection();
        ObservableList<MenuItem> lista = menuAutores.getItems();
        for(int i=0;i<lista.size();i++){
            if(lista.get(i) instanceof CheckMenuItem){
                CheckMenuItem item = (CheckMenuItem) lista.get(i);
                item.setSelected(false);
            }
        }
    }
    
    /**
     * Cria um aviso que a lista de resultados foi atualizada.
     * Invocado em qualquer item de pesquisa na aba livros.
     */
    private void criarAvisoPesquisaLivroSucesso(){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Autor cadastrado");
        alert.setHeaderText(null);
        alert.setContentText("Os livros encontrados foram listados na aba \"Resultados Busca\".");
        alert.showAndWait();
    }
    
    /**
     * Cria um item <String>, com valor do titulo do livro, para cada livro
     * informado no ArrayList. Associa cada String ao seu livro, por meio
     * do HashMap<String, Livro> itensBusca, para que possa
     * realizar a consulta clicando no objeto String da ListView.
     * @param arraylist 
     */
    private void atualizarBuscaLivro(ArrayList<Livro> arraylist){
        String titulo;
        listResultado.getItems().clear();
        itensBusca.clear();
        for(int i=0; i<arraylist.size();i++){
            titulo = arraylist.get(i).getTitulo();
            listResultado.getItems().add(titulo);
            itensBusca.put(titulo, arraylist.get(i));
        }
    }
    
    /**
     * Retorna a biblioteca atual. Utilizado no default close operation,
     * para salvar a biblioteca no arquivo texto.
     * @return 
     */
    public Biblioteca getBibliotecaAtual(){
        return this.biblioteca;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarComboEditora(biblioteca.getEditoras());
        inicializarMenuAutor(biblioteca.getAutores());
    }
}
