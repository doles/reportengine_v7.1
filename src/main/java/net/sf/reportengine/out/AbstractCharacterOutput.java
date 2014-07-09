/**
 * 
 */
package net.sf.reportengine.out;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import net.sf.reportengine.util.ReportIoUtils;

/**
 * <p>
 * abstract parent class for all your character based output.
 * </p>
 * 
 * <p>
 * What is a character based output? An output backed by a {@link java.io.Writer} to do the writing.<br/> 
 * Examples of character based outputs: text output, an html output, an xml also
 * </p>
 * 
 * <p>
 * 	How to extend this class ? <br/>
 *  This class has basic functionality for handling the opening/closing of the writer.
 *  Your subclasses should implement the methods :
 *  <ol> 
 *  	<li>{@link IReportOutput#output(CellProps)}</li>
 *  	<li>{@link IReportOutput#startRow(RowProps)} - for row counting use super.startRow()</li>
 *  	<li>{@link IReportOutput#endRow()}</li>
 *  </ol>
 *  inside these methods you can always use the {@link #getOutputWriter()} method for accessing
 *  the writer
 * </p>
 * 
 * @author dragos balan (dragos dot balan @ gmail dot com)
 * @since 0.7
 * @see {@link AbstractByteOutput} {@link TextOutput} {@link HtmlOutput} {@link StaxReportOutput}
 */
public abstract class AbstractCharacterOutput extends AbstractOutput {
	
	/**
	 * the writer behind this class
	 */
	private Writer outputWriter; 
	
	/**
	 * 
	 */
	public AbstractCharacterOutput() {
		this(new StringWriter());
	}

	/**
	 * @param filePath
	 */
	public AbstractCharacterOutput(String filePath) {
		setFilePath(filePath); 
	}

	/**
	 * @param writer
	 */
	public AbstractCharacterOutput(Writer writer) {
		setOutputWriter(writer); 
	}
	
	
    /**
     * flushes and closes the writer
     */
    public void close(){
    	try {
    		outputWriter.flush(); 
			outputWriter.close();
		} catch (IOException e) {
			throw new ReportOutputException(e);
		}
    	super.close();
    }

	/**
	 * @return the outputWriter
	 */
	public Writer getOutputWriter() {
		return outputWriter;
	}

	/**
	 * @param outputWriter the outputWriter to set
	 */
	public void setOutputWriter(Writer outputWriter) {
		this.outputWriter = outputWriter;
	}
	
	/**
	 * 
	 * @param filePath
	 */
	public void setFilePath(String filePath) {
		setOutputWriter(ReportIoUtils.createWriterFromPath(filePath));
	}
}
