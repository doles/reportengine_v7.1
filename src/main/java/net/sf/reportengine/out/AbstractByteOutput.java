/**
 * 
 */
package net.sf.reportengine.out;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * <p>
 * abstract parent class for all your byte based output.
 * </p>
 * <p>
 * What is a byte output? An output backed by a {@link java.io.OutputStream} to do the writing.<br/> 
 * Examples of byte outputs: an image report, a pdf, an excel output.
 * </p>
 * 
 * <p>
 * 	How to extend this class ? <br/>
 *  This class has basic functionality for handling the opening/closing of the outputStream.
 *  Your subclasses should implement the methods :
 *  <ol> 
 *  	<li>{@link IReportOutput#output(CellProps)}</li>
 *  	<li>{@link IReportOutput#startRow(RowProps)} - for row counting please use super.startRow()</li>
 *  	<li>{@link IReportOutput#endRow()}</li>
 *  </ol>
 *  inside these methods you can always use the {@link #getOutputStream()} method for accessing
 *  the outputStream
 * </p>
 * 
 * @author dragos balan (dragos dot balan @ gmail dot com)
 * @since 0.7
 * @see {@link AbstractCharacterOutput} {@link ExcelOutput} {@link XslFoOutput}
 */
public abstract class AbstractByteOutput extends AbstractOutput {
	
	/**
	 * the output stream behind this report output
	 */
	private OutputStream outputStream; 
	
	/**
	 * 
	 */
	public AbstractByteOutput() {
		setOutputStream(new ByteArrayOutputStream()); 
	}

	/**
	 * @param filePath
	 */
	public AbstractByteOutput(String filePath) {
		setFilePath(filePath); 
	}

	/**
	 * @param out
	 */
	public AbstractByteOutput(OutputStream out) {
		setOutputStream(out); 
	}
	
    /**
     * flushes and closes the writer
     */
    public void close(){
    	try {
    		outputStream.flush(); 
			outputStream.close();
		} catch (IOException e) {
			throw new ReportOutputException(e);
		}
    	super.close();
    }

	
	/**
	 * @return the outputStream
	 */
	public OutputStream getOutputStream() {
		return outputStream;
	}

	/**
	 * @param outputStream the outputStream to set
	 */
	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}
	
	/**
	 * 
	 * @param filePath
	 */
	public void setFilePath(String filePath){
		try {
			setOutputStream(new FileOutputStream(filePath));
		} catch (FileNotFoundException e) {
			throw new ReportOutputException(e); 
		}
	}
}
