package com.in1;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.HashMap;
import java.util.Map;

import net.sf.reportengine.CrossTabReport;
import net.sf.reportengine.config.DefaultCrosstabData;
import net.sf.reportengine.config.DefaultCrosstabHeaderRow;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.core.ReportContent;
import net.sf.reportengine.core.calc.Calculators;
import net.sf.reportengine.core.steps.FlatReportTotalsOutputStep;
import net.sf.reportengine.in.ArrayReportInput;
import net.sf.reportengine.in.IReportInput;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.out.CellProps.Builder;
import net.sf.reportengine.out.CellPropsArrayOutput;
import net.sf.reportengine.out.HtmlOutput;
import net.sf.reportengine.out.IReportOutput;
import net.sf.reportengine.out.RowProps;

/**
 * Unit test for simple App.
 */
public class AppTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CrossTabReport classUnderTest = new CrossTabReport(); 
		
		Object[][] data = {{"May","m","xyz",100,25,8,1,"h1"},{"May","f","xyz",50,25,9,2,"h1"},{"May","f","abc",100,80,10,3,"h1"},{"June","m","xyz",100,70,11,4,"h1"},{"June","m","abc",100,60,12,5,"h1"},{"June","f","abc",100,90,13,6,"h1"}};
		
		Object[][] data2 = {{"M1","2013",100,100,0},{"M1","2013",100,100,0},{"M1","2012",99,0,99},{"M1","2012",200,0,200},{"M2","2013",5,5,0},{"M2","2013",5,5,0},{"M2","2012",10,0,10},{"M2","2012",10,0,10}};
		
		FlatReportTotalsOutputStep.GRAND_TOTAL_STRING = "GTotal";
		
		
		IReportInput input = new ArrayReportInput(data2);
		classUnderTest.setIn(input); 
		
		Writer out = new BufferedWriter(new OutputStreamWriter(System.out));		
		IReportOutput output = new HtmlOutput(out); 
		output.open();
		try {
			output.write("Hello");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//classUnderTest.setOut(output);
		
		CellPropsArrayOutput cellOutput = new CellPropsArrayOutput();
		classUnderTest.setOut(cellOutput);
		//set up the data columns
		//classUnderTest.addDataColumn(new DefaultDataColumn("Month", 0, Calculators.COUNT));
		//classUnderTest.addDataColumn(new DefaultDataColumn("M/F", 1, Calculators.COUNT));
		//classUnderTest.addDataColumn(new DefaultDataColumn("M1", 3, Calculators.SUM));
		//classUnderTest.addDataColumn(new DefaultDataColumn("M2", 4, Calculators.SUM));
		//classUnderTest.addDataColumn(new VarianceDataColumn("Variance", 4,3)); 		
		
		//classUnderTest.addDataColumn(new DefaultDataColumn("Sth", 2, Calculators.COUNT));
		classUnderTest.addGroupColumn(new DefaultGroupColumn("Metric", 0,0)); //<-- M1/M2 is my METRIC TYPE type
		//classUnderTest.addGroupColumn(new DefaultGroupColumn("M/F",1,1));	
		
		//set up the header rows
		//classUnderTest.addHeaderRow(new DefaultCrosstabHeaderRow(0));
		//classUnderTest.addHeaderRow(new DefaultCrosstabHeaderRow(7));
		classUnderTest.addHeaderRow(new DefaultCrosstabHeaderRow(1)); //<--is my YEAR TYPE
		
		//set up the crosstab data
			
		Format f1 = new DecimalFormat("#,###.00");
		Map<String,Format> formatters = new HashMap<String,Format>();
		formatters.put("M1", f1);
		
		DefaultCrosstabData crosstabData2 = new DefaultCrosstabData(2,Calculators.SUM);
		crosstabData2.setLabel("Value");
		crosstabData2.setFormatterKeyRowIndex(0);//metric
		crosstabData2.setFormatters(formatters);
		classUnderTest.addCrosstabData(crosstabData2);  //<-- is my diplyed value			
		
		/*DefaultCrosstabData crosstabData1 = new DefaultCrosstabData(3,Calculators.SUM);
		crosstabData1.setLabel("Metric1");
		classUnderTest.addCrosstabData(crosstabData1);
		
		DefaultCrosstabData crosstabData2 = new DefaultCrosstabData(4,Calculators.SUM);
		crosstabData2.setLabel("Metric");
		classUnderTest.addCrosstabData(crosstabData2);
		
		VarianceCrosstabData crosstabData5 = new VarianceCrosstabData(4,3,new VarianceCalculator());
		crosstabData5.setLabel("V");
		classUnderTest.addCrosstabData(crosstabData5);	
		*/
		
		
		/*
		DefaultCrosstabData crosstabData3 = new DefaultCrosstabData(5,Calculators.SUM);
		crosstabData3.setLabel("Metric3");
		classUnderTest.addCrosstabData(crosstabData3);
		DefaultCrosstabData crosstabData4 = new DefaultCrosstabData(6,Calculators.SUM);
		crosstabData4.setLabel("Metric4");
		classUnderTest.addCrosstabData(crosstabData4);
		*/
		
		
		classUnderTest.setShowTotals(false);//classUnderTest.setShowTotals(true);
		classUnderTest.setShowGrandTotal(false);
		classUnderTest.setCompareYears(true);
		classUnderTest.setCompareYearsRowIndex1(3);
		classUnderTest.setCompareYearsRowIndex2(4);
		
		classUnderTest.setReportTitle("Compare Years");
		
		
		//report execution
		classUnderTest.execute();
		
		
		

		
		
		
		CellProps[][] cellsOrig = cellOutput.getCellMatrix();
		CellProps[][] cells = transpose(cellsOrig);

		
		Writer hOut = new BufferedWriter(new OutputStreamWriter(System.out));		
		IReportOutput hOutput = new HtmlOutput(hOut);
		
		
		hOutput.open();
		for(int r = 0; r <cells.length; r ++){
			RowProps p = new RowProps(ReportContent.COLUMN_HEADER);
			if(r==0){
				
				hOutput.startRow(p);
			}
			else {
				RowProps p2 = new RowProps();
				hOutput.startRow(p2);
			}
			for(int c = 0;c<cells[r].length;c++){
				if(null==cells[r][c]){
					hOutput.output(CellProps.EMPTY_CELL);
				}
				else {
					hOutput.output(cells[r][c]);				
				}
			}
			hOutput.endRow(p);
			
		}
		hOutput.close();		
		
		
		
		
		
	}
	
	public static CellProps[][]  transpose(CellProps[][] cells) {

		int c = cells.length;
		int r = cells[0].length;
		CellProps[][] copy = new CellProps[r][c];
		
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j <= cells[i].length; j++) {                
                if(j>r){
                	r = j;
            		copy = new CellProps[r][c];
                }
            }
        }
        // transpose
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[i].length; j++) {
                	Builder b = new Builder(cells[i][j].getValue(), 1, cells[i][j].getContentType(), cells[i][j].getHorizontalAlign());                	
                	copy[j][i]=new CellProps(b);
                	}
            }
        
    		     
        return copy;
         
    }	
}
