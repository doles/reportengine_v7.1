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
public abstract class AbstractGroupColumn implements IGroupColumn {
	
	private int groupingLevel; 
	private String header; 
	private Format formatter; 
	private HorizontalAlign horizAlign; 
	private boolean showDuplicates; 
	
	/**
	 * 
	 * @param header
	 * @param groupingLevel
	 * @param formatter
	 * @param horizAlign
	 */
	public AbstractGroupColumn(	String header, 
								int groupingLevel, 
								Format formatter, 
								HorizontalAlign horizAlign, 
								boolean showDuplicates){
		setHeader(header);
		setGroupingLevel(groupingLevel);
		setFormatter(formatter);
		setHorizAlign(horizAlign); 
		setShowDuplicates(showDuplicates); 
	}
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.config.IGroupColumn#getGroupingLevel()
	 */
	public int getGroupingLevel() {
		return groupingLevel;
	}
	
	
	public void setGroupingLevel(int groupingLevel) {
		this.groupingLevel = groupingLevel;
	}

	
	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getFormattedValue(Object value) {
		String result = "";
		if(value != null){
			if(formatter != null){
				result = formatter.format(value);
			}else{
				result = value.toString();
			}
		}
		return result; 
	}

	public Format getFormatter() {
		return formatter;
	}

	public void setFormatter(Format formatter) {
		this.formatter = formatter;
	}

	/**
	 * @return the horizAlign
	 */
	public HorizontalAlign getHorizAlign() {
		return horizAlign;
	}

	/**
	 * @param horizAlign the horizAlign to set
	 */
	public void setHorizAlign(HorizontalAlign horizAlign) {
		this.horizAlign = horizAlign;
	}
	
	/**
	 * 
	 */
	public boolean showDuplicates(){
		return showDuplicates; 
	}
	
	/**
	 * 
	 * @param flag
	 */
	public void setShowDuplicates(boolean flag){
		this.showDuplicates = flag; 
	}

}
