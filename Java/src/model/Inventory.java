/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 *
 * @author DaileyLuthier
 */
public class Inventory {
    
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    public Inventory() {
        
    }
    
    public static int modifyPartIndex;
    
    public static int modifyProductIndex;
    
    
    
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
    
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }
    
    public static void addProduct (Product newProduct) {
        allProducts.add(newProduct);
    }
    
    public static void removePart(Part newPart) {
        allParts.remove(newPart);
    }
    
    public static void removeProduct(Product newProduct) {
        allProducts.remove(newProduct);
    }
    
    public static void updatePart(int index, Part newPart) {
        allParts.set(index, newPart);
    }
    
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }
    
    
    public static boolean partDelete(Part newPart) {
        boolean isFound = false;
        
        for(int i=0; i<allProducts.size(); i++) {
            if(allProducts.get(i).getAssociatedParts().contains(newPart)) {
                isFound = true;
            }
        }
        return isFound;
    }
   
    
    public static int partLookup (String partSearch) {
        boolean isFound = false;
        int index = 0;
        if (isInteger(partSearch)) {
            for (int i =0; i < allParts.size(); i++) {
                if (Integer.parseInt(partSearch) == allParts.get(i).getPartId()) {
                    index = i;
                    isFound = true;
                }
            }
        } else {
            for (int i = 0; i < allParts.size(); i++) {
                if (partSearch.equals(allParts.get(i).getPartName())) {
                    index = i;
                    isFound = true;
                }
            }
        }
        if (isFound = true) {
            return index;
        } else {
            
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setContentText("part not found!");
            
            return -1;
        }
    }
        
    
    public static int productLookup (String productSearch) {
       boolean isFound = false;
        int index = 0;
        if (isInteger(productSearch)) {
            for (int i =0; i < allProducts.size(); i++) {
                if (Integer.parseInt(productSearch) == allProducts.get(i).getProductId()) {
                    index = i;
                    isFound = true;
                }
            }
        } else {
            for (int i = 0; i < allProducts.size(); i++) {
                if (productSearch.equals(allProducts.get(i).getProductName())) {
                    index = i;
                    isFound = true;
                }
            }
        }
        if (isFound = true) {
            return index;
        } else {
            
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setContentText("part not found!");
            
            return -1;
        }
    }
   
    public static boolean isInteger(String newString) {
        try {
            Integer.parseInt(newString);
            return true;
        } catch (NumberFormatException e) {
            return false;  
        }
    }
}
