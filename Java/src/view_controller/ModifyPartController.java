/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.InHouse;
import Model.Inventory;
import static Model.Inventory.getAllParts;
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
import static Model.Inventory.modifyPartIndex;

/**
 * FXML Controller class
 *
 * @author DaileyLuthier
 */
public class ModifyPartController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
        Part part = getAllParts().get(modifyPartIndex);
        partId = getAllParts().get(modifyPartIndex).getPartId();
        partIdField.setText(partId + "");
        partNameField.setText(part.getPartName());
        partInvField.setText(Integer.toString(part.getPartInventory()));
        partPriceField.setText(Double.toString(part.getPartPrice()));
        partMinField.setText(Integer.toString(part.getPartMin()));
        partMaxField.setText(Integer.toString(part.getPartMax()));
      
        try { 
            partExtraField.setText(Integer.toString(((InHouse) getAllParts().get(modifyPartIndex)).getPartMachineId()));
            inHouseRadio.setSelected(true);
        
        } catch (Exception e) {
            isOutsourced = true;
            partExtraField.setText(((Outsourced) getAllParts().get(modifyPartIndex)).getCompanyName());
            partExtraLabel.setText("Company Name");
            outsourcedRadio.setSelected(true);
        }
        
        

    
    }    
    
    private String newExceptionMessage = new String();
    int partId;
    private boolean isOutsourced;
    
    

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
    private Label partExtraLabel;

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
    private TextField partExtraField;

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
    void handleInHouseRadio(ActionEvent event) {
        
        isOutsourced = false;
        partExtraLabel.setText("Machine Id");
        inHouseRadio.setSelected(true);

    }

    @FXML
    void handleOutsourcedRadio(ActionEvent event) throws IOException {
        isOutsourced = true;
        partExtraLabel.setText("Company Name");
            outsourcedRadio.setSelected(true);

    }

    @FXML
        void handleSavePartButton(ActionEvent event) throws IOException {
        
            
            
        String partName = partNameField.getText();
        String partInv = partInvField.getText();
        String partPrice = partPriceField.getText();
        String partMin = partMinField.getText();
        String partMax = partMaxField.getText();
       
        String partExtra = partExtraField.getText();
        
        try {
            newExceptionMessage = Part.partValidation(partName, Integer.parseInt(partMin), Integer.parseInt(partMax), Integer.parseInt(partInv), Double.parseDouble(partPrice), newExceptionMessage);
            if (newExceptionMessage.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(newExceptionMessage);
                alert.showAndWait();
                newExceptionMessage = "";
            } else {
        
        
        if (isOutsourced) {
            
            Outsourced outsourced = new Outsourced();
            outsourced.setPartId(partId);
            outsourced.setPartName(partName);
            outsourced.setPartPrice(Double.parseDouble(partPrice));
            outsourced.setPartInventory(Integer.parseInt(partInv));
            outsourced.setPartMin(Integer.parseInt(partMin));
            outsourced.setPartMax(Integer.parseInt(partMax));
            outsourced.setPartCompanyName(partExtra);
            Inventory.updatePart(modifyPartIndex, outsourced);
            
        } else {
            InHouse inhouse = new InHouse();
            inhouse.setPartId(partId);
            inhouse.setPartName(partName);
            inhouse.setPartPrice(Double.parseDouble(partPrice));
            inhouse.setPartInventory(Integer.parseInt(partInv));
            inhouse.setPartMin(Integer.parseInt(partMin));
            inhouse.setPartMax(Integer.parseInt(partMax));
            inhouse.setPartMachineId(Integer.parseInt(partExtra));
            Inventory.updatePart(modifyPartIndex, inhouse);
                

        }
        
        Parent partsCancel = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene scene = new Scene(partsCancel);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

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

