/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import Model.*;
import static Model.Inventory.getAllParts;
import static Model.Inventory.getAllProducts;
import static Model.Inventory.removePart;
import javafx.scene.control.Alert.AlertType;
import static Model.Inventory.modifyPartIndex;
import static Model.Inventory.modifyProductIndex;
import static Model.Inventory.removeProduct;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;



/**
 * FXML Controller class
 *
 * @author DaileyLuthier
 */
public class MainScreenController implements Initializable {

    public MainScreenController() {
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        MainPartIdCol.setCellValueFactory(cellData -> cellData.getValue().partIdProperty().asObject());
        MainPartNameCol.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        MainPartInventoryCol.setCellValueFactory(cellData -> cellData.getValue().partInventoryProperty().asObject());
        MainPartPriceCol.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());
        MainProductIdCol.setCellValueFactory(cellData -> cellData.getValue().productIdProperty().asObject());
        MainProductNameCol.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        MainProductInventoryCol.setCellValueFactory(cellData -> cellData.getValue().productInventoryProperty().asObject());
        MainProductPriceCol.setCellValueFactory(cellData -> cellData.getValue().productPriceProperty().asObject());
        updateAllParts();
        updateAllProducts();
        
    }
// Tables components FXML    
    @FXML
    private TableView<Part> partsTable;
    
    @FXML
    private TableColumn<Part, Integer> MainPartIdCol;

    @FXML
    private TableColumn<Part, String> MainPartNameCol;

    @FXML
    private TableColumn<Part, Integer> MainPartInventoryCol;

    @FXML
    private TableColumn<Part, Double> MainPartPriceCol;
    
    @FXML
    private TableView<Product> productsTable;
    
    @FXML
    private TableColumn<Product, Integer> MainProductIdCol;

    @FXML
    private TableColumn<Product, String> MainProductNameCol;

    @FXML
    private TableColumn<Product, Integer> MainProductInventoryCol;

    @FXML
    private TableColumn<Product, Double> MainProductPriceCol;

    public void updateAllParts() {
        partsTable.setItems(Inventory.getAllParts());
    }
    
    public void updateAllProducts() {
        productsTable.setItems(Inventory.getAllProducts());
    }

    @FXML
    private Label partsLabel;

    @FXML
    private Button searchPartsButton;

    @FXML
    private Button addPartsButton;

    @FXML
    private Button deletePartsButton;

    @FXML
    private Button modifyPartsButton;

    @FXML
    private Label productsLabel;

    @FXML
    private Button searchProductsButton;

    @FXML
    private TextField partsSearchField;
    
    @FXML
    private TextField productsSearchField;


    @FXML
    private Button addProductsButton;

    @FXML
    private Button modifyProductsButton;

    @FXML
    private Button deleteProductsButton;

    @FXML
    private Label mainScreenLabel;

    @FXML
    private Button exitButton;

    @FXML
    void handleAddPartButtonAction(ActionEvent event) throws IOException {
        
        Parent addPart = FXMLLoader.load(getClass().getResource("PartInhouse.fxml"));
        Scene scene = new Scene(addPart);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    @FXML
    void handleAddProductButton(ActionEvent event) throws IOException {
        
        Parent addPart = FXMLLoader.load(getClass().getResource("Product.fxml"));
        Scene scene = new Scene(addPart);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    @FXML
    void handleDeletePartButtonAction(ActionEvent event) throws IOException {
        
        
        Part part = partsTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to delete " + part.getPartName() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
           removePart(part);
           updateAllParts();
            Alert alert1 = new Alert(AlertType.WARNING);
            alert1.setContentText("Part " + part.getPartName() + " was removed!");
           
        } else {
            Alert alert2 = new Alert(AlertType.WARNING);
            alert2.setContentText("Part " + part.getPartName() + " was not removed!");
            
        }

    }

    @FXML
    void handleDeleteProductButton(ActionEvent event) throws IOException {
        
        Product product = productsTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to delete " + product.getProductName() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            removeProduct(product);
            updateAllProducts();
            Alert alert1 = new Alert(AlertType.WARNING);
            alert1.setContentText("Product " + product.getProductName() + " was removed!");
        } else {
            Alert alert2 = new Alert(AlertType.WARNING);
            alert2.setContentText("Product " + product.getProductName() + " was not removed!");
        }

    }

    @FXML
    void handleModifyPartButtonAction(ActionEvent event) throws IOException {
    
        Part modifyPart1 = partsTable.getSelectionModel().getSelectedItem();
        modifyPartIndex = getAllParts().indexOf(modifyPart1);
        Parent modifyParts = FXMLLoader.load(getClass().getResource("ModifyPart.fxml"));
        Scene scene = new Scene(modifyParts);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    @FXML
    void handleModifyProductButton(ActionEvent event) throws IOException {
    // put an if statement that throws a pop up window if nothing is selected    
        Product modifyProduct = productsTable.getSelectionModel().getSelectedItem();
        modifyProductIndex = getAllProducts().indexOf(modifyProduct);
        Parent modifyProducts = FXMLLoader.load(getClass().getResource("ModifyProduct.fxml"));
        Scene scene = new Scene(modifyProducts);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }
// figure out how to do these
    @FXML
    void handleSearchPartButton(ActionEvent event) {
       
        String partSearch = partsSearchField.getText();
        int partIndex = -1;
        if (Inventory.partLookup(partSearch) == -1) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("No products found.");
            alert.showAndWait();
        } else {
            partIndex = Inventory.partLookup(partSearch);
            Part tempPart = Inventory.getAllParts().get(partIndex);
            ObservableList<Part> tempPartList = FXCollections.observableArrayList();
            tempPartList.add(tempPart);
            partsTable.setItems(tempPartList);
        }
    }
    
    @FXML
    void handleSearchProductsButton(ActionEvent event) throws IOException {
        
        String productSearch = productsSearchField.getText();
        int productIndex = -1;
        if (Inventory.productLookup(productSearch) == -1) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("No products found.");
            alert.showAndWait();
        } else {
            productIndex = Inventory.productLookup(productSearch);
            Product tempProduct = Inventory.getAllProducts().get(productIndex);
            ObservableList<Product> tempProductList = FXCollections.observableArrayList();
            tempProductList.add(tempProduct);
            productsTable.setItems(tempProductList);
        }
    }
    
    @FXML
    void handleExitButton(ActionEvent event) {
        
        Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirmation.initModality(Modality.WINDOW_MODAL);
        alertConfirmation.setContentText("Are you sure?");
        
        Optional<ButtonType> result = alertConfirmation.showAndWait();
            
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        } else {
            //if an alternative thing is decided
        }
    }

}
