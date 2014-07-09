/**
 * 
 */
package net.sf.reportengine.config;

import java.text.Format;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.ICalculator;


/**
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public class DefaultCrosstabData extends AbstractCrosstabData {
	
	/**
	 * 
	 */
	private int inputColumnIndex; 
	
	
	/**
	 * 
	 */
	public DefaultCrosstabData(){
		this(0); 
	}
	
	/**
	 * 
	 * @param inputColumnIndex
	 */
	public DefaultCrosstabData(int inputColumnIndex){
		this(inputColumnIndex, null); 
	}
	
	/**
	 * 
	 * @param inputColumnIndex
	 * @param calcFactory
	 */
	public DefaultCrosstabData(int inputColumnIndex, ICalculator calc){
		this(inputColumnIndex, calc, null);
	}
	
	/**
	 * 
	 * @param inputColumIndex
	 * @param calcFactory
	 * @param formatter
	 */
	public DefaultCrosstabData(	int inputColumIndex, 
								ICalculator calc, 
								Format formatter){
		this(inputColumIndex, calc, formatter, HorizontalAlign.CENTER);
	}
	
	/**
	 * 
	 * @param inputColumIndex
	 * @param calcFactory
	 * @param formatter
	 * @param horizAlign
	 */
	public DefaultCrosstabData(	int inputColumIndex, 
								ICalculator calc, 
								Format formatter, 
								HorizontalAlign horizAlign){
		super(calc, formatter, horizAlign);
		setInputColumnIndex(inputColumIndex);
	}
	
	public Object getValue(NewRowEvent newRowEvent) {
		Object[] newRow = newRowEvent.getInputDataRow(); 
		return newRow[inputColumnIndex];
	}

	public int getInputColumnIndex() {
		return inputColumnIndex;
	}

	public void setInputColumnIndex(int inputColumnIndex) {
		this.inputColumnIndex = inputColumnIndex;
	}
}
