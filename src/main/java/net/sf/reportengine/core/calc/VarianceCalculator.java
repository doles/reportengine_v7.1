/*
 * Created on 05.04.2005
 */
package net.sf.reportengine.core.calc;

import java.math.BigDecimal;
import java.sql.Timestamp;

import net.sf.reportengine.IntermediateCrosstabReport;
import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.config.SecondProcessDataColumn;
import net.sf.reportengine.config.SecondProcessVarianceColumn;
import net.sf.reportengine.config.VarianceCrosstabData;
import net.sf.reportengine.config.VarianceDataColumn;
import net.sf.reportengine.core.algorithm.NewRowEvent;

/**
 * sum calculator implementation 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 */
public class VarianceCalculator extends AbstractNumericCalculator {
    
    
    /**
	 * serial version
	 */
	private static final long serialVersionUID = -1880382251596724984L;
	
	private BigDecimal sum1 = BigDecimal.ZERO;
	
	private BigDecimal sum2 = BigDecimal.ZERO;


    public VarianceCalculator(){
        super();    
    }
    
    @Override
    public void init() {
        value = BigDecimal.ZERO;
        sum1 = BigDecimal.ZERO;
        sum2 = BigDecimal.ZERO;
    }    
    
    @Override
	public void compute(IDataColumn column, NewRowEvent row){
    	if(column instanceof VarianceDataColumn){
    		VarianceDataColumn vColumn = (VarianceDataColumn)column;
    		sum1 = BigDecimal.valueOf(sum1.doubleValue() + ((Number)row.getInputDataRow()[vColumn.getInputColumnIndex1()]).doubleValue());
    		sum2 = BigDecimal.valueOf(sum2.doubleValue() + ((Number)row.getInputDataRow()[vColumn.getInputColumnIndex2()]).doubleValue());
    		if(sum2.intValue()!=0){
    			value = new BigDecimal((sum1.doubleValue()/sum2.doubleValue() * 100)-100);
    		}     		
    	}
    	if(column instanceof SecondProcessVarianceColumn){
    		SecondProcessVarianceColumn vColumn = (SecondProcessVarianceColumn)column;
    		
    		if(row.getInputDataRow()[vColumn.getRowIndex1()]!=null){
	    		if(row.getInputDataRow()[vColumn.getRowIndex1()] instanceof Timestamp){
	    			sum1 = BigDecimal.valueOf(sum1.doubleValue() + ((Timestamp)row.getInputDataRow()[vColumn.getRowIndex1()]).getTime());
	    		}
	    		else if(row.getInputDataRow()[vColumn.getRowIndex1()] instanceof Number) {
    			
    				sum1 = BigDecimal.valueOf(sum1.doubleValue() + ((Number)row.getInputDataRow()[vColumn.getRowIndex1()]).doubleValue());
    			}
    		}
    		
    		if(row.getInputDataRow()[vColumn.getRowIndex2()]!=null){
    		
    			if(row.getInputDataRow()[vColumn.getRowIndex2()] instanceof Timestamp){    			
    				sum2 = BigDecimal.valueOf(sum2.doubleValue() + ((Timestamp)row.getInputDataRow()[vColumn.getRowIndex2()]).getTime());
    			}
    			else if(row.getInputDataRow()[vColumn.getRowIndex2()] instanceof Number) {
    				System.out.println(row.getInputDataRow()[vColumn.getRowIndex2()]);
    				sum2 = BigDecimal.valueOf(sum2.doubleValue() + ((Number)row.getInputDataRow()[vColumn.getRowIndex2()]).doubleValue());
    			}

    		}
    		
    		if(sum2.intValue()!=0){
    			value = new BigDecimal(((sum1.doubleValue()/sum2.doubleValue() * 100)-100)/100.0);
    		}     		
    	}    	
    	
    	else if(column instanceof IntermediateCrosstabReport.IntermDataColumnFromCrosstabData){
    		IntermediateCrosstabReport.IntermDataColumnFromCrosstabData crosstabColum = (IntermediateCrosstabReport.IntermDataColumnFromCrosstabData)column;
    		VarianceCrosstabData data = (VarianceCrosstabData)crosstabColum.getCrosstabData();
    		sum1 = BigDecimal.valueOf(sum1.doubleValue() + ((Number)row.getInputDataRow()[data.getInputColumnIndex1()]).doubleValue());
    		sum2 = BigDecimal.valueOf(sum2.doubleValue() + ((Number)row.getInputDataRow()[data.getInputColumnIndex2()]).doubleValue());
    		if(sum2.intValue()!=0){
    			value = new BigDecimal((sum1.doubleValue()/sum2.doubleValue() * 100)-100);
    		}   		
			
		}
    	else {
    		throw new CalculatorException(" This cannot work with this column: "+column.getClass().getName());
    	}


	}
	
	
    public void compute(Object newValue) {
    	throw new CalculatorException(" Cannot use compute(newValue) for VarianceCalculator");
    }
    
	public ICalculator newInstance() {
		return new VarianceCalculator();
	}
}
