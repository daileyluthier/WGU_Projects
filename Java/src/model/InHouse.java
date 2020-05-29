/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author DaileyLuthier
 */
public class InHouse extends Part {
    
    private final IntegerProperty machineId;
    
    public InHouse () {
        super();
        machineId = new SimpleIntegerProperty();
    }
    
    public void setPartMachineId (int machineId) {
        this.machineId.set(machineId);
    }
    
    public int getPartMachineId() {
        return this.machineId.get();
    }
    
}
