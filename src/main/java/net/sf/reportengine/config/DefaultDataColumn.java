/**
 * 
 */
package net.sf.reportengine.config;

import java.text.Format;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.ICalculator;

/**
 * <p>This is the basic implementation for a data column.</p> 
 * <p>
 * The functionality is based on an inputColumnIndex, so this column takes 
 * all its values from an input column (inputColumnIndex). 
 * </p>
 * 
 * Example: <br/>
 * For the input:
 * <table>
 * 		<tr><td>a1</td><td>b1</td><td>c1</td></tr>
 * 		<tr><td>a2</td><td>b2</td><td>c2</td></tr>
 * 		<tr><td>a3</td><td>b3</td><td>c3</td></tr>
 * </table>
 * 
 * I. a default column having inputColumnIndex=0 will display
 * <table>
 * 	<tr><td>a1</td></tr>
 * 	<tr><td>a2</td></tr>
 * 	<tr><td>a3</td></tr>
 * </table>
 * 
 * II. for inputColumnIndex=2 this column will display
 * <table>
 * 	<tr><td>c1</td></tr>
 * 	<tr><td>c2</td></tr>
 * 	<tr><td>c3</td></tr>
 * </table>
 * 
 * Please keep in mind that inputColumnIndex is a zero based index.<br/>
 * The other attributes of this column are : 
 *  1. header
 *  2. formatter
 *  3. horizontal alignment
 *  4. calculator
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public class DefaultDataColumn extends AbstractDataColumn {
	
	/**
	 * zero based index of the input column
	 */
	private int inputColumnIndex; 
	
	
	/**
	 * default constructor. 
	 * 
	 * Assumes 
	 * 	inputColumn=0
	 * 	no calculator
	 *  header=Column 0 
	 *  no formatter
	 */
	public DefaultDataColumn(){
		this(0); 
	}
	
	/**
	 * 
	 * @param inputColumnIndex
	 */
	public DefaultDataColumn(int inputColumnIndex){
		this("Column "+inputColumnIndex, inputColumnIndex);
	}
	
	/**
	 * 
	 * @param header
	 * @param inputColumnIndex
	 */
	public DefaultDataColumn(String header, int inputColumnIndex){
		this(header, inputColumnIndex, null);
	}
	
	/**
	 * 
	 * @param header
	 * @param inputColumnIndex
	 * @param calculatorsFactory
	 */
	public DefaultDataColumn(String header, int inputColumnIndex, ICalculator calculator){
		this(header, inputColumnIndex, calculator, null);
	}
	
	/**
	 * 
	 * @param header
	 * @param inputColumnIndex
	 * @param calculator
	 * @param formatter
	 */
	public DefaultDataColumn(	String header,
								int inputColumnIndex, 
								ICalculator calculator, 
								Format formatter){
		this(header, inputColumnIndex, calculator, formatter, HorizontalAlign.CENTER);
	}
	
	
	
	/**
	 * 
	 * @param header
	 * @param inputColumnIndex
	 * @param calculator
	 * @param formatter
	 * @param horizAlign	horizontal alignment 
	 */
	public DefaultDataColumn(	String header,
								int inputColumnIndex, 
								ICalculator calculator, 
								Format formatter, 
								HorizontalAlign horizAlign){
		
		super(header, calculator, formatter, horizAlign);
		setInputColumnIndex(inputColumnIndex);
	}
	
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.config.IDataColumn#getValue(net.sf.reportengine.core.algorithm.NewRowEvent)
	 */
	public Object getValue(NewRowEvent newRowEvent) {
		return newRowEvent.getInputDataRow()[inputColumnIndex];
	}

	/**
	 * returns the index of the original (input) column displayed on this column
	 * @return	the input column index
	 */
	public int getInputColumnIndex() {
		return inputColumnIndex;
	}

	/**
	 * sets the index of the original (input) column to be displayed on this column
	 * @param inputColumnIndex
	 */
	public void setInputColumnIndex(int inputColumnIndex) {
		this.inputColumnIndex = inputColumnIndex;
	}
}
