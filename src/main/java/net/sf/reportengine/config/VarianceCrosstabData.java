/**
 * 
 */
package net.sf.reportengine.config;

import java.text.Format;
import java.util.Map;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.ICalculator;


/**
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public class VarianceCrosstabData extends AbstractCrosstabData {
	
	/**
	 * 
	 */
	private int inputColumnIndex1;
	
	private int inputColumnIndex2;
	

	
	/**
	 * 
	 */
	public VarianceCrosstabData(){
		this(0,0); 
	}
	
	/**
	 * 
	 * @param inputColumnIndex
	 */
	public VarianceCrosstabData(int inputColumnIndex1, int inputColumnIndex2){
		this(inputColumnIndex1, inputColumnIndex2, null); 
	}
	
	/**
	 * 
	 * @param inputColumnIndex
	 * @param calcFactory
	 */
	public VarianceCrosstabData(int inputColumnIndex1, int inputColumnIndex2, ICalculator calc){
		this(inputColumnIndex1, inputColumnIndex2, calc, null);
	}
	
	/**
	 * 
	 * @param inputColumIndex
	 * @param calcFactory
	 * @param formatter
	 */
	public VarianceCrosstabData(	int inputColumIndex1,
								int inputColumnIndex2,
								ICalculator calc, 
								Format formatter){
		this(inputColumIndex1, inputColumnIndex2, calc, formatter, HorizontalAlign.CENTER);
	}
	
	/**
	 * 
	 * @param inputColumIndex
	 * @param calcFactory
	 * @param formatter
	 * @param horizAlign
	 */
	public VarianceCrosstabData(	int inputColumIndex1,
								int inputColumIndex2,
								ICalculator calc, 
								Format formatter, 
								HorizontalAlign horizAlign){
		super(calc, formatter, horizAlign);
		setInputColumnIndex1(inputColumIndex1);
		setInputColumnIndex2(inputColumIndex2);
	}
	
	public Object getValue(NewRowEvent newRowEvent) {
		Object[] newRow = newRowEvent.getInputDataRow(); 
		return (((Number)newRow[inputColumnIndex1]).doubleValue()/((Number)newRow[inputColumnIndex2]).doubleValue() * 100)-100;
	}

	public int getInputColumnIndex1() {
		return inputColumnIndex1;
	}

	public void setInputColumnIndex1(int inputColumnIndex1) {
		this.inputColumnIndex1 = inputColumnIndex1;
	}

	public int getInputColumnIndex2() {
		return inputColumnIndex2;
	}

	public void setInputColumnIndex2(int inputColumnIndex2) {
		this.inputColumnIndex2 = inputColumnIndex2;
	}

	
	

	
}
