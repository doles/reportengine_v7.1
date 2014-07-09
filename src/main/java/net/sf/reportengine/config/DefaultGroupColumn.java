/**
 * 
 */
package net.sf.reportengine.config;

import java.text.Format;

import net.sf.reportengine.core.algorithm.NewRowEvent;

/**
 * 
 * Grouping Column based on a single input column
 * 
 * @author dragos balan
 * @since 0.4
 */
public class DefaultGroupColumn extends AbstractGroupColumn {
	
	/**
	 * the index of the input column
	 */
	private int inputColumnIndex; 
	
	/**
	 * 
	 */
	public DefaultGroupColumn(){
		this(0);
	}
	
	/**
	 * 
	 * @param inputColumnIndex
	 */
	public DefaultGroupColumn(int inputColumnIndex){
		this("Column "+inputColumnIndex, inputColumnIndex, 0);
	}
	
	/**
	 * 
	 * @param inputColumnIndex
	 * @param groupingLevel
	 */
	public DefaultGroupColumn(int inputColumnIndex, int groupingLevel){
		this("Column "+inputColumnIndex, inputColumnIndex, groupingLevel);
	}
	
	/**
	 * 
	 * @param header
	 * @param inputColumnIndex
	 * @param groupingLevel
	 */
	public DefaultGroupColumn(	String header, 
								int inputColumnIndex, 
								int groupingLevel){
		this(header, inputColumnIndex, groupingLevel, null);
	}
	
	/**
	 * 
	 * @param header
	 * @param inputColumnIndex
	 * @param groupingLevel
	 * @param formatter
	 */
	public DefaultGroupColumn(	String header, 
								int inputColumnIndex, 
								int groupingLevel, 
								Format formatter){
		this(header, inputColumnIndex, groupingLevel, formatter, HorizontalAlign.CENTER); 
	}
	
	
	/**
	 * 
	 * @param header
	 * @param inputColumnIndex
	 * @param groupingLevel
	 * @param formatter
	 * @param horizAlign
	 */
	public DefaultGroupColumn(	String header, 
								int inputColumnIndex, 
								int groupingLevel, 
								Format formatter, 
								HorizontalAlign horizAlign){
		this(header, inputColumnIndex, groupingLevel, formatter, horizAlign, false); 
	}
	
	/**
	 * 
	 * @param header
	 * @param inputColumnIndex
	 * @param groupingLevel
	 * @param formatter
	 * @param horizAlign
	 * @param showDuplicates
	 */
	public DefaultGroupColumn(	String header, 
								int inputColumnIndex, 
								int groupingLevel, 
								Format formatter, 
								HorizontalAlign horizAlign, 
								boolean showDuplicates){
		super(header, groupingLevel, formatter, horizAlign, showDuplicates);
		setInputColumnIndex(inputColumnIndex);
	}
	

	/* (non-Javadoc)
	 * @see net.sf.reportengine.config.IGroupColumn#getValue(net.sf.reportengine.core.algorithm.NewRowEvent)
	 */
	public Object getValue(NewRowEvent newRowEvent) {
		return newRowEvent.getInputDataRow()[inputColumnIndex];
	}

	public int getInputColumnIndex() {
		return inputColumnIndex;
	}

	public void setInputColumnIndex(int inputColumnIndex) {
		this.inputColumnIndex = inputColumnIndex;
	}

}
