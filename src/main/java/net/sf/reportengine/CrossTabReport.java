/**
 * 
 */
package net.sf.reportengine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;

import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.ICrosstabData;
import net.sf.reportengine.config.ICrosstabHeaderRow;
import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.config.IGroupColumn;
import net.sf.reportengine.config.SecondProcessDataColumn;
import net.sf.reportengine.config.SecondProcessDataColumnFromOriginalDataColumn;
import net.sf.reportengine.config.SecondProcessGroupColumn;
import net.sf.reportengine.config.SecondProcessTotalColumn;
import net.sf.reportengine.config.SecondProcessVarianceColumn;
import net.sf.reportengine.config.VarianceCrosstabData;
import net.sf.reportengine.config.VarianceDataColumn;
import net.sf.reportengine.core.ConfigValidationException;
import net.sf.reportengine.core.algorithm.IReportContext;
import net.sf.reportengine.core.calc.Calculators;
import net.sf.reportengine.core.calc.ICalculator;
import net.sf.reportengine.core.calc.VarianceCalculator;
import net.sf.reportengine.in.IReportInput;
import net.sf.reportengine.in.IntermediateCrosstabReportInput;
import net.sf.reportengine.out.IReportOutput;
import net.sf.reportengine.out.IntermediateCrosstabOutput;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.CtMetadata;
import net.sf.reportengine.util.IDistinctValuesHolder;

import org.apache.log4j.Logger;

/**
 * <p>
 *  This is the main class to be used for Cross tab reports (or Pivot tables).
 *  The layout of pivot tables will look like: <br/>
 *  <table>
 *  	<tr><td>&nbsp;</td><td>&nbsp;</td>						<td colspan="4" align="center"><b>Row header 1</b></td></tr>
 *  	<tr><td><b>Column 1</b></td><td><b>Column 2</b></td>	<td colspan="4" align="center"><b>Row header 2</b></td></tr>
 *  	<tr><td>value 1</td><td>value 2</td>					<td>ct data 11</td><td>ct data 12</td><td>ct data 13</td><td>ct data 14</td></tr>
 *  	<tr><td>value 3</td><td>value 4</td>					<td>ct data 21</td><td>ct data 22</td><td>ct data 23</td><td>ct data 24</td></tr>
 *  	<tr><td>value 5</td><td>value 6</td>					<td>ct data 31</td><td>ct data 32</td><td>ct data 33</td><td>ct data 34</td></tr>
 *  </table><br/>
 * Each pivot table report needs five elements configured: 
 * <ul>
 * 	<li>input</li>
 * 	<li>column config</li>
 *  <li>row headers config</li> 
 *  <li>crosstab data</li>
 * 	<li>output</li>
 * </ul>
 * 
 * A simple pivot table report example is: 
 * <pre>
 * {@code
 *  CrossTabReport report = new CrossTabReport(); 
 *	
 *  //set up the input/output			
 *  IReportInput in = new TextInput(new FileInputStream("expenses.csv"));
 *  report.setIn(input); 
 *			
 *  IReportOutput output = new HtmlOutput(new FileOutputStream("xpenses.html")); 
 *  report.setOut(output);
 *			
 *  //set up data column
 *  report.addDataColumn(new DefaultDataColumn("Month", 0)); 
 *			
 *  //set up the header rows (from the second column)
 *  report.addHeaderRow(new DefaultCrosstabHeaderRow(1));
 *			
 *  //set up the crosstab data
 *  report.setCrosstabData(new DefaultCrosstabData(2));
 *			
 *  //report execution
 *  report.execute();
 *
 * }
 * </pre>
 * </p>
 * 
 * @see IReportInput
 * @see IReportOutput
 * @see ICrosstabHeaderRow
 * @see IDataColumn
 * @see IGroupColumn
 * @see ICrosstabData
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.2 
 */
public class CrossTabReport extends AbstractReport {
	
	/**
	 * the one and only logger
	 */
	private static final Logger logger = Logger.getLogger(CrossTabReport.class);
	
	private IntermediateCrosstabReport firstReport;
	private IntermediateCrosstabOutput firstReportOutput;
	
	private SecondCrosstabProcessorReport secondReport; 
	
	private List<ICrosstabHeaderRow> crosstabHeaderRowsAsList; 
	
	private List<ICrosstabData> crosstabDataList; 
	
	private boolean compareYears;
	private int compareYearsRowIndex1;
	private int compareYearsRowIndex2;
	
	public boolean isCompareYears() {
		return compareYears;
	}
	
	public void setCompareYears(boolean compareYears) {
		this.compareYears = compareYears;
	}
	
	
	
	public int getCompareYearsRowIndex1() {
		return compareYearsRowIndex1;
	}

	public void setCompareYearsRowIndex1(int compareYearsRowIndex1) {
		this.compareYearsRowIndex1 = compareYearsRowIndex1;
	}

	public int getCompareYearsRowIndex2() {
		return compareYearsRowIndex2;
	}

	public void setCompareYearsRowIndex2(int compareYearsRowIndex2) {
		this.compareYearsRowIndex2 = compareYearsRowIndex2;
	}

	public CrossTabReport(){
		this.crosstabHeaderRowsAsList = new ArrayList<ICrosstabHeaderRow>();
		this.crosstabDataList = new ArrayList<ICrosstabData>();
	}
	
	
	/**
	 * validates the configuration
	 */
	@Override protected void validateConfig(){
		super.validateConfig();
		//if(getCrosstabDataList() == null || getCrosstabDataList().size()==0){
		//	throw new ConfigValidationException("Crosstab reports need crosstab data configured"); 
		//}
		
		//if(getCrosstabHeaderRows() == null || getCrosstabHeaderRows().size() ==0){
		//	throw new ConfigValidationException("Crosstab reports need header rows configured");
		//}
		
		if((getDataColumns() == null || getDataColumns().size() == 0) 
			&& (getGroupColumns() == null || getGroupColumns().size() == 0)
			&& (getCrosstabDataList() == null || getCrosstabDataList().size()==0)){
			throw new ConfigValidationException("Crosstab reports need data and/or group columns configured"); 
		}
		
		//if totals are needed then check if any Calculators have been added to CrosstabData
		if(	(getShowTotals() || getShowGrandTotal()) ) {
			for (ICrosstabData crosstabData : getCrosstabDataList()){		
				if (crosstabData.getCalculator() == null){
					throw new ConfigValidationException("If you want to see totals please configure at least one calculator to CrosstabData");
				}
			}
		}
	}

    
	/**
	 * configures the algorithm steps
	 */
	@Override protected void configAlgorithmSteps() {
		try{
			List<IGroupColumn> groupCols = getGroupColumns(); 
			List<IDataColumn> dataColsList = getDataColumns(); 
			
			int groupColsLength = groupCols != null ? groupCols.size() : 0;
			int dataColsLength = dataColsList != null ? dataColsList.size() : 0;
					
			firstReportOutput = new IntermediateCrosstabOutput(); 		
			firstReport = new IntermediateCrosstabReport(groupColsLength, dataColsLength); 
			firstReport.setIn(getIn()); 
			firstReport.setOut(firstReportOutput); 
			firstReport.setGroupColumns(groupCols); 
			firstReport.setDataColumns(dataColsList); 
			firstReport.setCrosstabHeaderRows(getCrosstabHeaderRows()); 
			firstReport.setCrosstabDataList(getCrosstabDataList()); 
			firstReport.setShowDataRows(true); 
			firstReport.setShowTotals(false);
			firstReport.setShowGrandTotal(getShowGrandTotal()); 
			firstReport.setReportTitle(getReportTitle());
			//the execute method
			firstReport.execute(); 
			
			//transfer data from first report to the second
			IReportContext firstReportContext = firstReport.getAlgorithm().getContext(); 
			IDistinctValuesHolder distinctValuesHolder = (IDistinctValuesHolder)firstReportContext.get(ContextKeys.CONTEXT_KEY_INTERMEDIATE_DISTINCT_VALUES_HOLDER);
			CtMetadata crosstabMetadata = new CtMetadata(distinctValuesHolder);
			crosstabMetadata.computeCoefficients(); 
			
			//config the second report
			secondReport = new SecondCrosstabProcessorReport(crosstabMetadata);  
			InputStream secondReportInput = new FileInputStream(firstReportOutput.getSerializedOutputFile()); 
			secondReport.setIn(new IntermediateCrosstabReportInput(secondReportInput)); 
			secondReport.setOut(getOut()); 
			secondReport.setReportTitle(getReportTitle());
			
			List<IDataColumn> secondReportDataCols = 
					constructDataColumnsForSecondProcess(crosstabMetadata, 
														getDataColumns(), 
														getShowTotals(), 
														isCompareYears(),
														getShowGrandTotal());
			List<IGroupColumn> secondReportGroupCols = constructGroupColumnsForSecondProcess(getGroupColumns()); 
			
			secondReport.setGroupColumns(secondReportGroupCols); 
			secondReport.setDataColumns(secondReportDataCols);
			secondReport.setShowDataRows(true); 
			secondReport.setShowTotals(getShowTotals());
			secondReport.setCompareYears(isCompareYears());
			secondReport.setShowGrandTotal(!isCompareYears() && getShowGrandTotal());//(false); 
		}catch(FileNotFoundException fnfExc){
			throw new ConfigValidationException(fnfExc); 
		}
	}

	/**
	 * executes the second report algorithm
	 */
	@Override protected void executeAlgorithm() {
		secondReport.execute(); 
	}
	
	/**
	 * getter method for crosstabl header rows
	 * @return	a list of header rows
	 */
	public List<ICrosstabHeaderRow> getCrosstabHeaderRows() {
		return crosstabHeaderRowsAsList; 
	}
	
	/**
	 * setter for the header rows of the crosstab report
	 * @param crosstabHeaderRowsList	
	 */
	public void setHeaderRows(List<ICrosstabHeaderRow> crosstabHeaderRowsList) {
		this.crosstabHeaderRowsAsList = crosstabHeaderRowsList; 
	}
	
	/**
	 * adds a new header row at the end of the existing header rows list
	 * @param newHeaderRow
	 */
	public void addHeaderRow(ICrosstabHeaderRow newHeaderRow){
		this.crosstabHeaderRowsAsList.add(newHeaderRow);
	}
	
	
	
	public List<ICrosstabData> getCrosstabDataList() {
		return crosstabDataList;
	}


	public void addCrosstabData(ICrosstabData crosstabData) {
		this.crosstabDataList.add(crosstabData);
	}


	/**
	 * creates a list of group columns for the second report based on the original group columns
	 * 
	 * @param originalGroupCols
	 * @return	a list of group columns necessary to the second processing
	 */
	protected List<IGroupColumn> constructGroupColumnsForSecondProcess(List<IGroupColumn> originalGroupCols){
		List<IGroupColumn> result = null; 
		if(originalGroupCols != null && originalGroupCols.size() > 0){
			result = new ArrayList<IGroupColumn>(originalGroupCols.size());
			for (IGroupColumn originalGroupColumn : originalGroupCols) {
				result.add(new SecondProcessGroupColumn(originalGroupColumn));
			}
		}
		return result; 
	}
	
	
	
	/**
	 * creates a list of IDataColumn objects from 
	 * 
	 * 1. original data columns 
	 * 2. columns needed for displaying the values under the header values ( computed form crosstab-data) 
	 * 3. (if needed ) columns needed to display the totals and grand total 
	 * 
	 * @param crosstabMetadata
	 * @param originalDataColumns
	 * @param hasTotals
	 * @param hasGrandTotal
	 * @return	a list data columns necessary to the second process
	 */
	protected List<IDataColumn> constructDataColumnsForSecondProcess(	CtMetadata crosstabMetadata, 
																		List<IDataColumn> originalDataColumns, 
																		boolean hasTotals, 
																		boolean isCompareYears,
																		boolean hasGrandTotal){
		int dataColsCount = crosstabMetadata.getDataColumnCount(); 
		int headerRowsCount = crosstabMetadata.getHeaderRowsCount(); 
		
		List<IDataColumn> resultDataColsList = new ArrayList<IDataColumn>();
		
		//first we add the original data columns (those declared by the user in his configuration)
		for(int i=0; i < originalDataColumns.size(); i++){
			resultDataColsList.add(new SecondProcessDataColumnFromOriginalDataColumn(originalDataColumns.get(i), i));
		}

		// then we construct the columns 
		for(int column=0; column < dataColsCount; column++){
		
			//construct total columns 
			if(hasTotals){
				//we start bottom to top (from last header row -1 to first header row) 
				for(int row=headerRowsCount-2; row >= 0; row--){
					int colspan = crosstabMetadata.getColspanForLevel(row); 
				
					if(column != 0 && (column % colspan)==0){
						int[] positionForCurrentTotal = new int[row+1]; //new int[headerRowsCount-1];
					
						for(int j=0; j < positionForCurrentTotal.length; j++){
							positionForCurrentTotal[j] = ((column-1) / crosstabMetadata.getColspanForLevel(j)) % crosstabMetadata.getDistinctValuesCountForLevel(j);
						}
		
						resultDataColsList.add(
								new SecondProcessTotalColumn(	positionForCurrentTotal, 
																Calculators.SUM, 
																null, 
																"Total column="+column+ ",colspan= "+colspan,0));
					}
				}//end for
			}//end if has totals

			//data columns coming from data columns
			int[] positionForCurrentColumn = new int[headerRowsCount];
			for(int j=0; j < headerRowsCount; j++){
				positionForCurrentColumn[j] = (column / crosstabMetadata.getColspanForLevel(j)) % crosstabMetadata.getDistinctValuesCountForLevel(j);
			}
		
			//get position from header zero
			int crosstabData_index = positionForCurrentColumn[headerRowsCount-1];//[0];
			
			resultDataColsList.add(new SecondProcessDataColumn(positionForCurrentColumn, getCrosstabDataList().get(crosstabData_index)/*Calculators.SUM*/, null)); 
		}//end for columns
		
		//at the end we add one more total 
		if(hasTotals){
			//final total columns
			for(int row=headerRowsCount-2; row >= 0; row--){
				int colspan = crosstabMetadata.getColspanForLevel(row); 
			
				if(dataColsCount!= 0 && (dataColsCount % colspan)==0){
					int[] positionForCurrentTotal = new int[row+1];
			
					for(int j=0; j < positionForCurrentTotal.length; j++){
						positionForCurrentTotal[j] = ((dataColsCount-1) / crosstabMetadata.getColspanForLevel(j)) % crosstabMetadata.getDistinctValuesCountForLevel(j);
					}
					
					resultDataColsList.add(new SecondProcessTotalColumn(positionForCurrentTotal,Calculators.COUNT /* Calculators.SUM */, null, "Total column="+(dataColsCount)+ ",colspan= "+colspan, 0));
				}
			}
		}//end if has totals

		if(isCompareYears){
			//at the moment just for last c (we expect only 2 crosstab output rows, and one grouping column)
				Format f = new DecimalFormat("0.00%");
				resultDataColsList.add(new SecondProcessVarianceColumn(null,new VarianceCalculator(),f,"Variance",getCompareYearsRowIndex1(),getCompareYearsRowIndex2()));	
		}
		
		
		// .. and finally the grand total
		if(hasGrandTotal){
			for(int c = 0; c<getCrosstabDataList().size();c++) {
				
				resultDataColsList.add(new SecondProcessTotalColumn(null, getCrosstabDataList().get(c).getCalculator(), getCrosstabDataList().get(c).getFormatter(), "Total "+getCrosstabDataList(), c));	
			}
			 
		}

		return resultDataColsList; 
	}
}
