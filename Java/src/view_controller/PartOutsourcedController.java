/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DaileyLuthier
 */
public class PartOutsourcedController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        int max = 0;
        for(Part part : Inventory.getAllParts()){
            if(part.getPartId()>max){
                max=part.getPartId();
            }
        }
        partIdField.setText(max+1 + "");
        partIdField.setDisable(true);
        
        outsourcedRadio.setSelected(true);
    }    
    
    private String newExceptionMessage = new String();
    

    @FXML
    private Label addPartLabel;

    @FXML
    private RadioButton inHouseRadio;

    @FXML
    private RadioButton outsourcedRadio;

    @FXML
    private Label partIdLabel;

    @FXML
    private Label partNameLabel;

    @FXML
    private Label partInvLabel;

    @FXML
    private Label partPriceLabel;

    @FXML
    private Label partMaxLabel;

    @FXML
    private Label partCompanyNameLabel;

    @FXML
    private TextField partIdField;

    @FXML
    private TextField partNameField;

    @FXML
    private TextField partInvField;

    @FXML
    private TextField partPriceField;

    @FXML
    private TextField partMaxField;

    @FXML
    private TextField partCompanyNameField;

    @FXML
    private Label partMinLabel;

    @FXML
    private TextField partMinField;

    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;

    @FXML
    void handleCancelPartButton(ActionEvent event) throws IOException {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {

            Parent partsCancel = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(partsCancel);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(scene);
            window.show();
        } else {
            System.out.println("You clicked cancel. Please complete part info.");
        }
    }

    @FXML
    void handleInHouseRadio(ActionEvent event) throws IOException {
        
        Parent partsCancel = FXMLLoader.load(getClass().getResource("PartInhouse.fxml"));
            Scene scene = new Scene(partsCancel);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(scene);
            window.show();
    }

    @FXML
    void handleOutsourcedRadio(ActionEvent event) {
            

    }

    @FXML
    void handleSaveOutsourcedPartButton(ActionEvent event) throws IOException {
        
        Integer partId = Integer.parseInt(partIdField.getText());
        String partName = partNameField.getText();
        String partInv = partInvField.getText();
        String partPrice = partPriceField.getText();
        String partMin = partMinField.getText();
        String partMax = partMaxField.getText();
        String partCompanyName = partCompanyNameField.getText();
        
        try {
            newExceptionMessage = Part.partValidation(partName, Integer.parseInt(partMin), Integer.parseInt(partMax), Integer.parseInt(partInv), Double.parseDouble(partPrice), newExceptionMessage);
            if (newExceptionMessage.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(newExceptionMessage);
                alert.showAndWait();
                newExceptionMessage = "";
            } else {
                
        Outsourced newPart = new Outsourced();

        newPart.setPartId(partId);
        newPart.setPartName(partName);
        newPart.setPartPrice(Double.parseDouble(partPrice));
        newPart.setPartInventory(Integer.parseInt(partInv));
        newPart.setPartMin(Integer.parseInt(partMin));
        newPart.setPartMax(Integer.parseInt(partMax));
        newPart.setPartCompanyName((partCompanyName));
        Inventory.addPart(newPart);    

        Parent partsSave = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene scene = new Scene(partsSave);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        }
        } catch (NumberFormatException e) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Form is not completed.");
        alert.showAndWait();
        }
    }

}

