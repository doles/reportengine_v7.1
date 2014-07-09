/**
 * 
 */
package net.sf.reportengine.out;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import net.sf.reportengine.core.ReportEngineRuntimeException;
import net.sf.reportengine.core.steps.crosstab.IntermediateReportRow;

import org.apache.log4j.Logger;

/**
 * @author dragos
 *
 */
public class IntermediateCrosstabOutput implements IReportOutput {
	
	/**
	 * the logger
	 */
	private static final Logger logger = Logger.getLogger(IntermediateCrosstabOutput.class);
	
	/**
	 * the output stream where all inermediaterows will be serialized
	 */
	private ObjectOutputStream objectOutputStream = null; 
	
	/**
	 * this is the file that holds all intermediary data
	 */
	private File result = null; 
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.IAlgorithmOutput#open()
	 */
	public void open() {
		try {
			result = File.createTempFile("crosstab", ".temp");
			if(logger.isInfoEnabled()){
				logger.info("creating temporary file on "+result.getAbsolutePath());
			}
			objectOutputStream = new ObjectOutputStream(new FileOutputStream(result));
		} catch (FileNotFoundException e) {
			throw new ReportEngineRuntimeException(e);
		} catch (IOException e) {
			throw new ReportEngineRuntimeException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.out.IReportOutput#startRow()
	 */
	public void startRow(RowProps rowProperties) {}
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.out.IReportOutput#endRow()
	 */
	public void endRow() {
		try {
			objectOutputStream.reset();
		} catch (IOException e) {
			throw new ReportEngineRuntimeException(e);
		} 
	}

	/* (non-Javadoc)
	 * @see net.sf.reportengine.out.IReportOutput#output(net.sf.reportengine.out.CellProps)
	 */
	public void output(CellProps cellProps) {
		Object value = cellProps.getValue();
		if(value instanceof IntermediateReportRow){
			IntermediateReportRow intermediateRow = (IntermediateReportRow)value; 
			//serialize
			try {
				if(logger.isDebugEnabled()){
					logger.debug("writting object to intermediate object stream "+intermediateRow);
				}
				objectOutputStream.writeObject(intermediateRow);
			} catch (IOException e) {
				throw new ReportEngineRuntimeException(e);
			}
		}else{
			throw new IllegalArgumentException("The intermediate crosstab output works only with IntermediateReportRow.");
		}
	}
	

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.IAlgorithmOutput#close()
	 */
	public void close() {
		try {
			objectOutputStream.flush();
			objectOutputStream.close(); 
			
			if(logger.isInfoEnabled()){
				logger.info("IntermediateCrosstabReport closed"); 
			}
		} catch (IOException e) {
			throw new ReportEngineRuntimeException(e); 
		} 
	}
	
	public File getSerializedOutputFile(){
		return result; 
	}
}
