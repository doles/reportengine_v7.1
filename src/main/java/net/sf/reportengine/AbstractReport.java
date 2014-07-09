/*
 * Created on 22.10.2005
 * Author : dragos balan 
 */
package net.sf.reportengine;

import java.util.ArrayList;
import java.util.List;

import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.config.IGroupColumn;
import net.sf.reportengine.core.ConfigValidationException;
import net.sf.reportengine.in.IReportInput;
import net.sf.reportengine.out.IReportOutput;


/**
 * <p>Abstract report support class covering basic functionality for reports</p>
 * <p>
 * 	the features provided to the children are:
 * <ul>
 * 	<li>title</li>
 * 	<li>input</li>
 * 	<li>output</li>
 * 	<li>dataColumns</li>
 *  <li>groupColumns</li>
 *  <li>show methods</li>	
 *  <li>validation of input/output</li>
 * </ul> 
 * 
 * When extending this class you should only override 
 * <ul>
 * 	<li>{@link #validateConfig()} - basic input/output functionality already provided so you may consider using super.validateConfig()</li>
 * 	<li>{@link #configAlgorithmSteps()}</li>
 * 	<li>{@link #executeAlgorithm()}</li>
 * <ul>
 * </p>
 *  
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.2
 * @see FlatReport
 * @see CrossTabReport
 */
public abstract class AbstractReport {
    
	
    /**
     * the title of the report
     */
    private String reportTitle;
    
    /**
     * the input of the report
     */
    private IReportInput input;
    
    /**
     * the output of the report
     */
    private IReportOutput output;
    
    
    /**
     * whether the report will display totals or not 
     */
    private boolean showTotals = false; 
    
    /**
     * whether the report will display grand total
     */
    private boolean showGrandTotal = false;
    
    /**
     * whether the report will display normal data rows or not 
     */
    private boolean showDataRows = true;
   
    /**
     * the data columns
     */
    private List<IDataColumn> dataColsAsList; 
    
    /**
     * grouping columns
     */
    private List<IGroupColumn> groupColsAsList; 
    
    /**
     * this is a constructor for 
     * a single algorithm 
     */
    public AbstractReport(){
    	dataColsAsList = new ArrayList<IDataColumn>();
    	groupColsAsList = new ArrayList<IGroupColumn>();
    }
   
    /**
     * validation of the configuration and default values setter
     * 
     * @throws ConfigValidationException if the report was miss-configured
     */
    protected void validateConfig() {
        if(getIn() == null) throw new ConfigValidationException("The report has no input");
        if(getOut() == null) throw new ConfigValidationException("The report has no output");
    }
    
    /**
     * configures the reports 
     */
    protected abstract void configAlgorithmSteps();
    
    /**
     * the main execution of the algorithms 
     */
    protected abstract void executeAlgorithm();
    
    
    /**
     * Call this method for execution of the report<br> 
     * What this method does: <br>
     * <ul>
     *  <li>validates the configuration - validateConfig() method call</li>
     *  <li>configures the algorithms - configAlgorithms() method call</li>
     *  <li>opens the output - output.open()</li>
     *  <li>runs each algorithm execute() method</li>
     *  <li>closes the output - output.close()</li>
     * </ul>
     */
    public void execute(){
        validateConfig();
        configAlgorithmSteps();
        executeAlgorithm();
    }
    
    /**
     * getter for the report title
     * @return  reportTitle 
     */
    public String getReportTitle() {
        return reportTitle;
    }
    
    /**
     * report title setter
     * @param reportTitle   the title of the report
     */
    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }
    
    /**
     * setter method for report input
     * @param in
     */
    public void setIn(IReportInput in){
    	this.input = in;
    }
    
    /**
     * setter method fo report output
     * @param out
     */
    public void setOut(IReportOutput out){
    	this.output = out;
    }

	/**
	 * @return the input
	 */
	public IReportInput getIn() {
		return input;
	}

	/**
	 * @return the output
	 */
	public IReportOutput getOut() {
		return output;
	}
	
	
	/**
	 * getter for data colums of this report
	 * @return an ordered list of data columns
	 */
	public List<IDataColumn> getDataColumns() {
		return dataColsAsList; 
	}

	/**
	 * setter for the list of data columns 
	 * @param dataColsList
	 */
	public void setDataColumns(List<IDataColumn> dataColsList){
		this.dataColsAsList = dataColsList; 
	}
	
	/**
	 * adds a data column to the existing list
	 * @param newColumn
	 */
	public void addDataColumn(IDataColumn newColumn){
		this.dataColsAsList.add(newColumn); 
	}
	
	/**
	 * getter for the list of group columns
	 * @return an ordered list of group columns
	 */
	public List<IGroupColumn> getGroupColumns() {
		return groupColsAsList; 
	}
	
	/**
	 * sets the given columns lists. 
	 * Please note that you cannot use {@link #setGroupColumns(List)} after {@link #addGroupColumn(IGroupColumn)}
	 * because it will override the existing columns. 
	 * 
	 * @param groupColsList the new list of group columns
	 */
	public void setGroupColumns(List<IGroupColumn> groupColsList){
		this.groupColsAsList = groupColsList;
	}
	
	/**
	 * adds a new group column to the list of the existing columns. 
	 * Please note that your column is added at the end of the existing list
	 * 
	 * @param newGroupCol the new group column
	 */
	public void addGroupColumn(IGroupColumn newGroupCol){
		this.groupColsAsList.add(newGroupCol); 
	}
	
	 /**
     * Usually when setting totals you should also set some calculators 
     * otherwise the default calculators (Count) will be shown
     * @param flag  true if you want to show the results of aggregation (sums, counts, max etc)
     */
    public void setShowTotals(boolean flag){
        this.showTotals = flag;
    }
    
    /**
     * show totals getter
     * @return  true if this report has to show totals
     */
    public boolean getShowTotals(){
        return showTotals;
    }
    
    
    /**
     * setter for showDataRows
     * @param flag  true if you want to show data rows 
     */
    public void setShowDataRows(boolean flag){
        this.showDataRows = flag;
    }
    
    /**
     * show data rows getter
     * @return  true if this report has to show data rows
     */
    public boolean getShowDataRows(){
        return showDataRows;
    }
    
    /**
     * getter for showGrandTotal flag
     * @return true if this report will show the grand totals
     */
    public boolean getShowGrandTotal(){
    	return showGrandTotal;
    }
    
    /**
     * setter for showGrandTotal flag
     * @param flag
     */
    public void setShowGrandTotal(boolean flag){
    	this.showGrandTotal = flag;
    }
    
    /**
     * check if all data columns have calculators assigned
     * @return
     */
    protected boolean atLeastOneDataColumHasCalculators(){
    	boolean atLeastOneCalculator = false; 
    	for(IDataColumn dataColumn: getDataColumns()){
    		if(dataColumn.getCalculator() != null){
    			atLeastOneCalculator = true;
    			break; 
    		}
    	}
    	return atLeastOneCalculator; 
    }
}
