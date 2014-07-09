/**
 * 
 */
package net.sf.reportengine.out;

import java.io.Writer;

/**
 * Abstract output for all xml based report output
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3
 */
public abstract class AbstractXmlOutput extends AbstractCharacterOutput {
	
	public final static String TAG_REPORT = "report";
	
    public final static String TAG_TITLE = "title";
    
    public final static String TAG_ROW = "row";
    
    public final static String TAG_CELL = "cell";
    
    public final static String TAG_TABLE_HEADER = "table-header";
    
    public final static String TAG_ROW_HEADER = "row-header";
    
    public final static String ATTR_ENGINE_VERSION = "engineVersion";
    
	public final static String ATTR_COLSPAN = "colspan";
	
    public final static String ATTR_COLUMN_NUMBER = "colNumber";
    
	public final static String ATTR_CONTENT_TYPE = "contentType";
	
	public final static String ATTR_ROW_NUMBER = "rowNumber";
	
	public final static String ATTR_HORIZ_ALIGN = "horizAlign";
	
	/**
	 * empty constructor
	 */
	public AbstractXmlOutput(){}
	
	/**
	 * 
	 * @param fileName
	 */
	public AbstractXmlOutput(String fileName){
		super(fileName); 
	}
	
	/**
     * 
     * @param writer
     */
    public AbstractXmlOutput(Writer writer){
    	super(writer);
    }
}
