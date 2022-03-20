/*
Caio Vinicius Perereira Silveira
Projeto Final Programacao de Computadores II - Biblioteca
22/08/2021
*/
package modelo;

import controle.ControladorBiblioteca;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("/visao/bibliotecaFX.fxml"));
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/visao/bibliotecaFX.fxml"));
        Parent root = loader.load();
        ControladorBiblioteca controlador = loader.getController();
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
        //salvar a biblioteca ao fechar a janela
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
        @Override public void handle(WindowEvent t) {
            servicos.EscreverArquivoTexto.abrirArquivo("biblioteca.txt");
            servicos.EscreverArquivoTexto.adicionarBiblioteca(controlador.getBibliotecaAtual());
            servicos.EscreverArquivoTexto.fecharArquivo();
            System.exit(0);
        }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
