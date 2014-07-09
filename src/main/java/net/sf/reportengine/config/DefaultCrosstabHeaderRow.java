/**
 * 
 */
package net.sf.reportengine.config;

import java.text.Format;

import net.sf.reportengine.core.algorithm.NewRowEvent;


/**
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public class DefaultCrosstabHeaderRow extends AbstractCrosstabHeaderRow {

	

	/**
	 * 
	 */
	private int inputColumnIndex; 
	
	
	public DefaultCrosstabHeaderRow(int inputColumnIndex){
		this(inputColumnIndex, null);
	}
	
	public DefaultCrosstabHeaderRow(int inputColumnIndex, 
									Format formatter){
		super(formatter); 
		setInputColumnIndex(inputColumnIndex);
	}
	
	
	public int getInputColumnIndex() {
		return inputColumnIndex;
	}
	
	public void setInputColumnIndex(int index){
		this.inputColumnIndex = index; 
	}
	
	public Object getValue(NewRowEvent newRowEvent){
		return newRowEvent.getInputDataRow()[inputColumnIndex];
	}
	
}
