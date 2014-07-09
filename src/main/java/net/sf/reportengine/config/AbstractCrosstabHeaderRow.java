/**
 * 
 */
package net.sf.reportengine.config;

import java.text.Format;


/**
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public abstract class AbstractCrosstabHeaderRow implements ICrosstabHeaderRow {
	
	/**
	 * the formatter of the value
	 */
	private Format outFormat = null;
	
	
	/**
	 * 
	 * @param format			the formatter
	 */
	public AbstractCrosstabHeaderRow(Format format){
		setFormatter(format);
	}


	public Format getFormatter(){
		return outFormat; 
	}
	
	public void setFormatter(Format formatter){
		this.outFormat = formatter; 
	}
}
