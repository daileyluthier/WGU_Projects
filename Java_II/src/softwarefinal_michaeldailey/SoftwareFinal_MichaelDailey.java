/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwarefinal_michaeldailey;

import View_Controller.LogInScreenController;
import java.io.IOException;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author DaileyLuthier
 */
public class SoftwareFinal_MichaelDailey extends Application {
    
    @Override
    public void start(Stage stage) throws ClassNotFoundException, SQLException, IOException, Exception {
        FXMLLoader loader = new FXMLLoader(LogInScreenController.class.getResource("LogInScreen.fxml"));
        
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        stage.setTitle("Scheduling Software - C195");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

    /**
     * @param args the command line arguments
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws Exception
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException, Exception {
        DAO.DBConnection.startConnection();
        launch(args);
        DAO.DBConnection.closeConnection();
    }
    
}
