/*
 * Created on 14.12.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.algorithm;

import java.util.Arrays;

/**
 * 
 * @author dragos balan
 * @version $Revision$
 * $log$
 */
public class NewRowEvent  {
    
    private Object[] inputData;
    //private Object[] computedData;
    
    public NewRowEvent(Object[] inputDataRow){
        this.inputData = inputDataRow; 
    }
    
    public Object[] getInputDataRow(){
        return inputData;
    }
    
    public String toString(){
        return "NewRowEvent:"+Arrays.toString(inputData);
    }
    
}
