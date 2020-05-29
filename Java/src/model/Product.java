/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



/**
 *
 * @author DaileyLuthier
 */
public class Product {
    
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    
    private final IntegerProperty productId;
    private final StringProperty productName;
    private final DoubleProperty productPrice;
    private final IntegerProperty productInventory;
    private final IntegerProperty productMin;
    private final IntegerProperty productMax;
    
    public Product () {
        
        productId = new SimpleIntegerProperty();
        productName = new SimpleStringProperty();
        productPrice = new SimpleDoubleProperty();
        productInventory = new SimpleIntegerProperty();
        productMin = new SimpleIntegerProperty();
        productMax = new SimpleIntegerProperty();
        
    }
    
    public ObservableList getAssociatedParts() {
        return associatedParts;
    }

    public void setAssociatedParts(ObservableList<Part> associatedParts) {
        this.associatedParts = associatedParts;
    }
    

     public IntegerProperty productIdProperty() {
        return productId;
    }

    public StringProperty productNameProperty() {
        return productName;
    }

    public DoubleProperty productPriceProperty() {
        return productPrice;
    }

    public IntegerProperty productInventoryProperty() {
        return productInventory;
    }
    
    public IntegerProperty productMinProperty() {
        return productMin;
    }
    public IntegerProperty productMaxProperty() {
        return productMax;
    }
    
    public int getProductId() {        
        return this.productId.get();
    }
    
    public void setProductId(int productId) {        
        this.productId.set(productId);
    }

    public String getProductName() {       
        return this.productName.get();
    }

    public void setProductName(String productName) {       
        this.productName.set(productName);
    }

    public double getProductPrice() {   
        return  this.productPrice.get();
    }

    public void setProductPrice(double productPrice) {
        this.productPrice.set(productPrice);
    }

    public int getProductInventory() {
        return this.productInventory.get();
    }

    public void setProductInventory(int productInventory) {
        this.productInventory.set(productInventory);
    }

    public int getProductMin() {
        return this.productMin.get();
    }

    public void setProductMin(int productMin) {
        this.productMin.set(productMin);
    }

    public int getProductMax() {
        return this.productMax.get();
    }

    public void setProductMax(int productMax) {
        this.productMax.set(productMax);
    }

    public static String productValidation(String name, int min, int max, int inventory, double price, ObservableList<Part> associatedParts, String errorMessage) {
        double sumParts = 0.00;
        for (int i = 0; i < associatedParts.size(); i++) {
            sumParts = sumParts + associatedParts.get(i).getPartPrice();
        }
        
        if (name.equals("")) {
            errorMessage = errorMessage + ("Please enter a name.");
        }
        if (price < 0) {
            errorMessage = errorMessage + ("Price entered is less than $0");
        }
        if (min < 0) {
            errorMessage = errorMessage + ("The inventory count must be greater than 0.");
        }
        if (min > max) {
            errorMessage = errorMessage + ("The minimum value must be larger than the maximum value.");
        }
        if (inventory < min || inventory > max) {
            errorMessage = errorMessage + ("The inventory must be contained within the minimum and maximum values.");
        }
        if (associatedParts.size() < 1) {
            errorMessage = errorMessage + ("Product must have at least 1 part.");
        }
        if (sumParts > price) {
            errorMessage = errorMessage + ("Product total price must be greater than cost of parts.");
        }
        return errorMessage;
    }


}