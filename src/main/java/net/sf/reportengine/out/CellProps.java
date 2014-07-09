/*
 * Created on 25.03.2006
 * Author : dragos balan 
 */
package net.sf.reportengine.out;

import net.sf.reportengine.config.HorizontalAlign;
import net.sf.reportengine.core.ReportContent;

/**
 * immutable cell properties class. 
 * This is constructed internally to send data to the output.
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3
 */
public final class CellProps {
	
	/**
	 * empty cell having a colspan of 1 
	 */
	public static final CellProps EMPTY_CELL = new CellProps.Builder(IReportOutput.WHITESPACE).build(); 

	/**
     * the column span
     */
	private final int colspan;

	/**
     * the report content
     */
	private final ReportContent content;

	/**
     * the value to be displayed in the cell
     */
	private final Object value;

	/**
     * horizontal alignment
     */
	private final HorizontalAlign horizAlign;

	
	/**
	 * constructor using the fluent builder for CellProps
	 * 
	 * @param propsBuilder
	 */
	private CellProps(Builder propsBuilder){
		this.value = propsBuilder.value; 
		this.colspan = propsBuilder.colspan; 
		this.content = propsBuilder.content; 
		this.horizAlign = propsBuilder.horizAlign; 
	}
	
	/**
	 * column span
	 * @return
	 */
	public int getColspan() {
		return colspan;
	}
	
	/**
	 * the content type
	 * @return
	 */
	public ReportContent getContentType() {
		return content;
	}
	
	/**
	 * the value to be displayed
	 * @return
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * the horizontal align
	 * @return
	 */
	public HorizontalAlign getHorizontalAlign() {
		return horizAlign;
	}
	
	/**
	 * equals
	 */
	@Override public boolean equals(Object another) {
		boolean result = false;
		if (another instanceof CellProps) {
			CellProps anotherAsCP = (CellProps) another;
			result = value.equals(anotherAsCP.getValue()) 
					 && (colspan == anotherAsCP.getColspan())
					 && (content.equals(anotherAsCP.getContentType()));
					 // TODO include the horizontal alignment in the equals
					 //&& (horizAlign.equals(anotherAsCP.getHorizontalAlign())); 
		}
		return result;
	}
	
	/**
	 * hashCode
	 */
	@Override public int hashCode() {
		int result = 3;
		result = 97 * result + value.hashCode();
		result = 97 * result + colspan;
		result = 97 * result + content.hashCode();
		result = 97 * result + horizAlign.hashCode(); 
		return result;
	}
	
	/**
	 * toString
	 */
	@Override public String toString() {
		StringBuilder result = new StringBuilder("CP[");
		result.append(value);
		result.append(",cspan=").append(colspan);
		result.append(",type=").append(content);
		result.append(", hAlign=").append(horizAlign);
		result.append("]");
		return result.toString();
	}
	
	/**
	 * builder class for CellProps
	 * 
	 * @author dragos balan
	 *
	 */
	public static class Builder{
		
		private final Object value;
		private int colspan = 1;
		private ReportContent content = ReportContent.DATA;
		private HorizontalAlign horizAlign = HorizontalAlign.CENTER;
		
		public Builder(Object value){
			this.value = value; 
		}
		
		public Builder colspan(int colspan){
			this.colspan = colspan; 
			return this; 
		}		
		
		public Builder contentType(ReportContent type){
			this.content = type; 
			return this; 
		}
		
		public Builder horizAlign(HorizontalAlign align){
			this.horizAlign = align; 
			return this; 
		}
		
		public CellProps build(){
			return new CellProps(this); 
		}
	}
}