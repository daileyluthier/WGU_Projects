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

/**
 *
 * @author DaileyLuthier
 */
public abstract class Part {
    
    private final IntegerProperty partId;
    private final StringProperty partName;
    private final DoubleProperty partPrice;
    private final IntegerProperty partInventory;
    private final IntegerProperty partMin;
    private final IntegerProperty partMax;
    
    public Part () {
        
        partId = new SimpleIntegerProperty();
        partName = new SimpleStringProperty();
        partPrice = new SimpleDoubleProperty();
        partInventory = new SimpleIntegerProperty();
        partMin = new SimpleIntegerProperty();
        partMax = new SimpleIntegerProperty();
    }

    public IntegerProperty partIdProperty() {
        return partId;
    }

    public StringProperty partNameProperty() {
        return partName;
    }

    public DoubleProperty partPriceProperty() {
        return partPrice;
    }

    public IntegerProperty partInventoryProperty() {
        return partInventory;
    }

    public IntegerProperty partMinProperty() {
        return partMin;
    }

    public IntegerProperty partMaxProperty() {
        return partMax;
    }
    
    public int getPartId() {
        
        return this.partId.get();
    }
    
    public void setPartId(int partId) {
        
        this.partId.set(partId);
    }
    
    public String getPartName() {
        
        return this.partName.get();
    }
    
    public void setPartName(String partName) {
        this.partName.set(partName);
    }
    
    public Double getPartPrice() {
        return this.partPrice.get();
    }
    
    public void setPartPrice(Double partPrice) {
        this.partPrice.set(partPrice);
    }
    
    public Integer getPartInventory() {
        return this.partInventory.get();
    }
    
    public void setPartInventory(Integer partInventory) {
        this.partInventory.set(partInventory);
    }
    
    public Integer getPartMin() {
        return this.partMin.get();
    }
    
    public void setPartMin(Integer partMin) {
        this.partMin.set(partMin);
    }
    
    public Integer getPartMax() {
        return this.partMax.get();
    }
    
    public void setPartMax(Integer partMax) {
        this.partMax.set(partMax);
    }
    
    public static String partValidation(String partName, int partMin, int partMax, int partInventory, double partPrice, String errorMessage) {
        if (partName == null) {
            errorMessage = errorMessage + ("Please enter part name.");
        }
        if (partPrice < 1) {
            errorMessage = errorMessage + ("Price entered is less than $0");
        }
        if (partInventory < 1) {
            errorMessage = errorMessage + ("The inventory count must be greater than 0.");
        }
        if (partMin > partMax) {
            errorMessage = errorMessage + ("The minimum value must be larger than the maximum value.");
        }
        if (partInventory < partMin || partInventory > partMax) {
            errorMessage = errorMessage + ("The inventory must be contained within the minimum and maximum values.");
        }
        return errorMessage;
    }
    }
    
    

