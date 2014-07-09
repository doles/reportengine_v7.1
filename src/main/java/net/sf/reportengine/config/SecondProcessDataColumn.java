/**
 * 
 */
package net.sf.reportengine.config;

import java.math.BigDecimal;
import java.text.Format;
import java.util.Arrays;
import java.util.Map;

import net.sf.reportengine.IntermediateCrosstabReport.IntermDataColumnFromCrosstabData;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.ICalculator;
import net.sf.reportengine.core.steps.crosstab.IntermComputedDataList;

/**
 * this is only for internal use. 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public class SecondProcessDataColumn extends AbstractDataColumn{
	
	private int[] positionRelativeToHeader;
	
	
	private ICrosstabData crosstabData;
	
	/**
	 * 
	 * @param positionRelativeToHeader
	 * @param calculator
	 * @param formatter
	 */
	public SecondProcessDataColumn(	int[] positionRelativeToHeader, 	
									ICrosstabData crosstabdata, 
									Format formatter) {
		//normally we don't need the column header
		super("Data"+Arrays.toString(positionRelativeToHeader), crosstabdata.getCalculator(), formatter);
		this.positionRelativeToHeader = positionRelativeToHeader; 
		this.crosstabData = crosstabdata;
		this.setFormatter(crosstabdata.getFormatter());
		this.setFormatters(crosstabdata.getFormatters());
		this.setCalculator(crosstabdata.getCalculator());
		this.setFormatterKeyRowIndex(crosstabdata.getFormatterKeyRowIndex());
	}
	
	public SecondProcessDataColumn(	int[] positionRelativeToHeader, 	
			ICalculator calc, 
			Format formatter,
			int formatterKeyRowIndex,
			Map<String, Format> formatters) {
		//normally we don't need the column header
		super("Data"+Arrays.toString(positionRelativeToHeader), calc, formatter);
		this.positionRelativeToHeader = positionRelativeToHeader; 
		this.setFormatters(formatters);
		this.setCalculator(calc);
		this.setFormatterKeyRowIndex(formatterKeyRowIndex);		
	}	
	
	
	public Object getValue(NewRowEvent newRowEvent) {
		
		//according to the contract the third object in each row array is an 
		//instance of IntermCtDataList
		Object[] newRow = newRowEvent.getInputDataRow(); 
		IntermComputedDataList intermDataList = (IntermComputedDataList)newRow[2]; 
		
		IDataColumn column = this;
		if(this.crosstabData != null){
			column = new IntermDataColumnFromCrosstabData(crosstabData);
		}
		
		Object result = intermDataList.getValueFor(positionRelativeToHeader, column); 
		if(result == null){
			result = BigDecimal.ZERO; 
		}
		return result; 
	}
	
	public int[] getPosition(){
		return positionRelativeToHeader; 
	}
}
