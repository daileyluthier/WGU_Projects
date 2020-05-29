/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwarefinal_michaeldailey;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author DaileyLuthier
 */
public class SoftwareFinal_MichaelDailey extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
       
        GridPane grid = FXMLLoader.load(getClass().getResource("/View_Controller/MainScreen.fxml"));
        Scene scene = new Scene(grid, 800, 400);
        stage.setScene(scene);
        stage.setTitle("Software I - Final Project");
        stage.setAlwaysOnTop(false);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
