/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Inventory;
import static Model.Inventory.getAllParts;
import static Model.Inventory.getAllProducts;
import static Model.Inventory.modifyProductIndex;
import Model.Part;
import Model.Product;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author DaileyLuthier
 */
public class ModifyProductController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Product product = getAllProducts().get(modifyProductIndex);
        productId = getAllProducts().get(modifyProductIndex).getProductId();
        productIdField.setText(productId + "");
        productNameField.setText(product.getProductName());
        productInvField.setText(Integer.toString(product.getProductInventory()));
        productPriceField.setText(Double.toString(product.getProductPrice()));
        productMinField.setText(Integer.toString(product.getProductMin()));
        productMaxField.setText(Integer.toString(product.getProductMax()));
 
        currentParts = product.getAssociatedParts();
        
        addPartIdCol.setCellValueFactory(cellData -> cellData.getValue().partIdProperty().asObject());
        addPartNameCol.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        addPartInvLevelCol.setCellValueFactory(cellData -> cellData.getValue().partInventoryProperty().asObject());
        addPartPriceCol.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());

        currentPartIdCol.setCellValueFactory(cellData -> cellData.getValue().partIdProperty().asObject());
        currentPartNameCol.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        currentPartInvLevelCol.setCellValueFactory(cellData -> cellData.getValue().partInventoryProperty().asObject());
        currentPartPriceCol.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());
        
        updateProductTable1();
        updateProductTable2();
        
    }  
    
    public void updateProductTable1() {
        productsTable1.setItems(getAllParts());
    }
    
    public void updateProductTable2() {
        productsTable2.setItems(currentParts);
    }
    
    private ObservableList<Part> currentParts = FXCollections.observableArrayList();
    private String newExceptionMessage = new String();
    private int productId;
    
    
    @FXML
    private Label modifyProductLabel;

    @FXML
    private TextField productIdField;

    @FXML
    private TextField productNameField;

    @FXML
    private TextField productInvField;

    @FXML
    private TextField productPriceField;

    @FXML
    private TextField productMaxField;

    @FXML
    private TextField productMinField;

    @FXML
    private TableView<Part> productsTable1;

    @FXML
    private TableView<Part> productsTable2;

    @FXML
    private TableColumn<Part, Integer> addPartIdCol;

    @FXML
    private TableColumn<Part, String> addPartNameCol;

    @FXML
    private TableColumn<Part, Integer> addPartInvLevelCol;

    @FXML
    private TableColumn<Part, Double> addPartPriceCol;

    @FXML
    private TableColumn<Part, Integer> currentPartIdCol;

    @FXML
    private TableColumn<Part, String> currentPartNameCol;

    @FXML
    private TableColumn<Part, Integer> currentPartInvLevelCol;

    @FXML
    private TableColumn<Part, Double> currentPartPriceCol;

    @FXML
    private Button searchProductsButton;

    @FXML
    private Button addProductsButton;

    @FXML
    private Button deleteProductsButton;

    @FXML
    private Button cancelProductsButton;

    @FXML
    private Button saveProductsButton;
    
    @FXML
    private TextField modifySearchField;

    @FXML
    void handleAddProductsButton(ActionEvent event) {
        
        Part newPart = productsTable1.getSelectionModel().getSelectedItem();
        currentParts.add(newPart);
        updateProductTable2();

    }

    @FXML
    void handleCancelProductsButton(ActionEvent event) throws IOException {
        
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
    void handleDeleteProductsButton(ActionEvent event) {
        
        Part part = productsTable2.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to delete " + part.getPartName() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setContentText("Part " + part.getPartName() + " was removed!");
            currentParts.remove(part);
            updateProductTable2();
        } else {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setContentText("Part " + part.getPartName() + " was not removed!");
        }

    }

    @FXML
    void handleSaveProductsButton(ActionEvent event) throws IOException {
        
        String productName = productNameField.getText();
        String productInv = productInvField.getText();
        String productPrice = productPriceField.getText();
        String productMin = productMinField.getText();
        String productMax = productMaxField.getText();
        
        try {
            newExceptionMessage = Product.productValidation(productName, Integer.parseInt(productMin), Integer.parseInt(productMax), Integer.parseInt(productInv), Double.parseDouble(productPrice), currentParts, newExceptionMessage);
            if (newExceptionMessage.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(newExceptionMessage);
                alert.showAndWait();
                newExceptionMessage = "";
            } else {
            
            Product modifyProduct = new Product();
            modifyProduct.setProductId(productId);
            modifyProduct.setProductName(productName);
            modifyProduct.setProductPrice(Double.parseDouble(productPrice));
            modifyProduct.setProductInventory(Integer.parseInt(productInv));
            modifyProduct.setProductMin(Integer.parseInt(productMin));
            modifyProduct.setProductMax(Integer.parseInt(productMax));
            modifyProduct.setAssociatedParts(currentParts);
            Inventory.updateProduct(modifyProductIndex, modifyProduct);

            Parent productsSave = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(productsSave);
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

    @FXML
    void handleSearchProductsButton(ActionEvent event) {
        
        String partSearch = modifySearchField.getText();
        int partIndex = -1;
        
        if(Inventory.partLookup(partSearch) == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Searched part was not found!");
            alert.showAndWait();
        } else {
            partIndex = Inventory.partLookup(partSearch);
            Part partTemp = Inventory.getAllParts().get(partIndex);
            
            ObservableList<Part> tempProductList = FXCollections.observableArrayList();
            tempProductList.add(partTemp);
            productsTable1.setItems(tempProductList);
        }

    }

}
