/**
 * 
 */
package net.sf.reportengine.in;


/**
 * Report Input implementation based on a 2 dimensional in memory array
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3
 */
public class ArrayReportInput extends AbstractReportInput {

	private Object[][] data;
	
	private int currentRow = 0;
	
	
	/**
	 * 
	 * @param data
	 */
	public ArrayReportInput(Object[][] data){
		this.data = data;
	}
	
	/**
     * 
     */
    public void open() throws ReportInputException{
    	super.open(); 
    	currentRow = 0;
    }

    /**
     * 
     */
    public void close() throws ReportInputException {
       super.close(); 
    }
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.in.AbstractReportInput#hasMoreRows()
	 */
	@Override
	public boolean hasMoreRows() {
		return currentRow < data.length ;
	}

	/* (non-Javadoc)
	 * @see net.sf.reportengine.in.AbstractReportInput#nextRow()
	 */
	@Override
	public Object[] nextRow(){
		return data[currentRow++];
	}

}
