/*
 Date: 17-11-2004
 */
package net.sf.reportengine.out;

import java.io.IOException;
import java.io.Writer;

import net.sf.reportengine.core.ReportContent;
import net.sf.reportengine.util.ReportIoUtils;


/**
 * Simple implementation of the IReportOutput having 
 * as result a html page.
 * The available options are :
 * 	1. Construct a html having inside default css (no control whatsoever)
 *  2. Construct a html based on outside css. (full view control)
 *  This outside css should contain any of following css definitions: 
 *  	table.reportTable
 *  	tr.reportTableHeader
 *  	tr.even
 *  	tr.odd
 *  
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.2
 */
public class XHtmlOutput extends AbstractCharacterOutput {
    /**
     * css style
     */
    private static final String DEFAULT_INSIDE_PAGE_CSS_DECLARATION = new StringBuilder()
    																	.append("<style>@page {size: A4 landscape;} " +
    																			"@font-face { "+
    																	        "font-family: Lucida Sans Unicode; "+
    																	        "src: url(http://www.portalin1.com/backend/includes/reporting/l_10646.ttf); "+
    																	        "-fs-pdf-font-embed: embed; "+
    																	        "-fs-pdf-font-encoding: Identity-H; "+
    																	        "} "+
    																			"body{font-family:'Lucida Sans Unicode';font-size:12px;}table{width:100%;table-layout:fixed;max-width:100%;background-color:transparent;border-collapse:collapse;border-spacing:0;}.table{width:100%;margin-bottom:20px;}.table th,.table td{padding:8px;text-align:left;vertical-align:top;border-top:1px solid #dddddd;}.table th{font-weight:bold;}.table thead th{vertical-align:bottom;}.table caption+thead tr:first-child th,.table caption+thead tr:first-child td,.table colgroup+thead tr:first-child th,.table colgroup+thead tr:first-child td,.table thead:first-child tr:first-child th,.table thead:first-child tr:first-child td{border-top:0;}.table tbody+tbody{border-top:2px solid #dddddd;}.table .table{background-color:#ffffff;}.table-condensed th,.table-condensed td{padding:4px 5px;}.table-bordered{border:1px solid #dddddd;border-collapse:separate;*border-collapse:collapse;border-left:0;-webkit-border-radius:4px;-moz-border-radius:4px;border-radius:4px;}.table-bordered th,.table-bordered td{border-left:1px solid #dddddd;}.table-bordered caption+thead tr:first-child th,.table-bordered caption+tbody tr:first-child th,.table-bordered caption+tbody tr:first-child td,.table-bordered colgroup+thead tr:first-child th,.table-bordered colgroup+tbody tr:first-child th,.table-bordered colgroup+tbody tr:first-child td,.table-bordered thead:first-child tr:first-child th,.table-bordered tbody:first-child tr:first-child th,.table-bordered tbody:first-child tr:first-child td{border-top:0;}.table-bordered thead:first-child tr:first-child>th:first-child,.table-bordered tbody:first-child tr:first-child>td:first-child,.table-bordered tbody:first-child tr:first-child>th:first-child{-webkit-border-top-left-radius:4px;-moz-border-radius-topleft:4px;border-top-left-radius:4px;}.table-bordered thead:first-child tr:first-child>th:last-child,.table-bordered tbody:first-child tr:first-child>td:last-child,.table-bordered tbody:first-child tr:first-child>th:last-child{-webkit-border-top-right-radius:4px;-moz-border-radius-topright:4px;border-top-right-radius:4px;}.table-bordered thead:last-child tr:last-child>th:first-child,.table-bordered tbody:last-child tr:last-child>td:first-child,.table-bordered tbody:last-child tr:last-child>th:first-child,.table-bordered tfoot:last-child tr:last-child>td:first-child,.table-bordered tfoot:last-child tr:last-child>th:first-child{-webkit-border-bottom-left-radius:4px;-moz-border-radius-bottomleft:4px;border-bottom-left-radius:4px;}.table-bordered thead:last-child tr:last-child>th:last-child,.table-bordered tbody:last-child tr:last-child>td:last-child,.table-bordered tbody:last-child tr:last-child>th:last-child,.table-bordered tfoot:last-child tr:last-child>td:last-child,.table-bordered tfoot:last-child tr:last-child>th:last-child{-webkit-border-bottom-right-radius:4px;-moz-border-radius-bottomright:4px;border-bottom-right-radius:4px;}.table-bordered tfoot+tbody:last-child tr:last-child td:first-child{-webkit-border-bottom-left-radius:0;-moz-border-radius-bottomleft:0;border-bottom-left-radius:0;}.table-bordered tfoot+tbody:last-child tr:last-child td:last-child{-webkit-border-bottom-right-radius:0;-moz-border-radius-bottomright:0;border-bottom-right-radius:0;}.table-bordered caption+thead tr:first-child th:first-child,.table-bordered caption+tbody tr:first-child td:first-child,.table-bordered colgroup+thead tr:first-child th:first-child,.table-bordered colgroup+tbody tr:first-child td:first-child{-webkit-border-top-left-radius:4px;-moz-border-radius-topleft:4px;border-top-left-radius:4px;}.table-bordered caption+thead tr:first-child th:last-child,.table-bordered caption+tbody tr:first-child td:last-child,.table-bordered colgroup+thead tr:first-child th:last-child,.table-bordered colgroup+tbody tr:first-child td:last-child{-webkit-border-top-right-radius:4px;-moz-border-radius-topright:4px;border-top-right-radius:4px;}.table-striped tbody>tr:nth-child(odd)>td,.table-striped tbody>tr:nth-child(odd)>th{background-color:#f9f9f9;}.table-hover tbody tr:hover>td,.table-hover tbody tr:hover>th{background-color:#f5f5f5;}table td[class*=\"span\"],table th[class*=\"span\"],.row-fluid table td[class*=\"span\"],.row-fluid table th[class*=\"span\"]{display:table-cell;float:none;margin-left:0;}.table td.span1,.table th.span1{float:none;width:44px;margin-left:0;}.table td.span2,.table th.span2{float:none;width:124px;margin-left:0;}.table td.span3,.table th.span3{float:none;width:204px;margin-left:0;}.table td.span4,.table th.span4{float:none;width:284px;margin-left:0;}.table td.span5,.table th.span5{float:none;width:364px;margin-left:0;}.table td.span6,.table th.span6{float:none;width:444px;margin-left:0;}.table td.span7,.table th.span7{float:none;width:524px;margin-left:0;}.table td.span8,.table th.span8{float:none;width:604px;margin-left:0;}.table td.span9,.table th.span9{float:none;width:684px;margin-left:0;}.table td.span10,.table th.span10{float:none;width:764px;margin-left:0;}.table td.span11,.table th.span11{float:none;width:844px;margin-left:0;}.table td.span12,.table th.span12{float:none;width:924px;margin-left:0;}.table tbody tr.success>td{background-color:#dff0d8;}.table tbody tr.error>td{background-color:#f2dede;}.table tbody tr.warning>td{background-color:#fcf8e3;}.table tbody tr.info>td{background-color:#d9edf7;}.table-hover tbody tr.success:hover>td{background-color:#d0e9c6;}.table-hover tbody tr.error:hover>td{background-color:#ebcccc;}.table-hover tbody tr.warning:hover>td{background-color:#faf2cc;}.table-hover tbody tr.info:hover>td{background-color:#c4e3f3;}"
    																	        +"</style>")
    																	.toString();
    /**
     * the utf-8 content type meta declaration
     */
    private static final String ENCODING_DECLARATION = "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />";
    
    /**
     * html start
     */
    private static final String HTML_HEAD_START = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\"><head><title>report</title>";
    
    /**
     * html head end and body start
     */
    private static final String HTML_HEAD_END = "</head><body>";
    
    /**
     * html end
     */
    private static final String HTML_END = "</body></html>";
    
    /**
     * flag for standalone pages (default true)                    <br/>
     * If this flag is true then a full html page will be generated <br/>
     * otherwise only the table will be written as output
     */
    private boolean isStandalonePage = true;
    
    /**
     * the path to a css file
     */
    private String cssPath ; 
    
    /**
     * outputs everything into a stringWriter if no other writer is set
     */
    public XHtmlOutput(){
    	super(); 
    }
    
    /**
     * html output to the specified file
     * 
     * @param fileName
     */
    public XHtmlOutput(String fileName){
    	super(fileName); 
    }
    
    /**
     * html output to the specified writer
     * 
     * @param writer
     */
    public XHtmlOutput(Writer writer){
    	super(writer);
    }
    
   /**
    * opens the output
    */
    public void open() {
    	if(super.isOutputOpen()){
    		return;
    	}
        try {
            super.open();
            if(isStandalonePage){
            	Writer writer = getOutputWriter(); 
            	writer.write(HTML_HEAD_START);
            	writer.write(ReportIoUtils.LINE_SEPARATOR);
            	writer.write(ENCODING_DECLARATION);
            	writer.write(ReportIoUtils.LINE_SEPARATOR); 
            	if(cssPath != null){
            		writer.write("<link rel=\"stylesheet\" href=\"");
            		writer.write(cssPath); 
            		writer.write("\"/>"); 
            	}else{
            		writer.write(DEFAULT_INSIDE_PAGE_CSS_DECLARATION); 
            	}
            	writer.write(HTML_HEAD_END);
            }
            getOutputWriter().write("<p></p><table class=\"table table-bordered table-condensed\" cellpading=\"0\" cellspacing=\"0\" >");
        } catch (IOException ioExc) {
        	throw new RuntimeException(ioExc);
        }
    }
    
    public void openTable() {

        try {
            super.open();            
            getOutputWriter().write("<p></p><table class=\"table table-bordered table-condensed\" cellpading=\"0\" cellspacing=\"0\" >");
        } catch (IOException ioExc) {
        	throw new RuntimeException(ioExc);
        }
    }    
    
    public void openPageOnly() {
    	if(super.isOutputOpen()){
    		return;
    	}
        try {
            super.open();
            	Writer writer = getOutputWriter(); 
            	writer.write(HTML_HEAD_START);
            	writer.write(ReportIoUtils.LINE_SEPARATOR);
            	writer.write(ENCODING_DECLARATION);
            	writer.write(ReportIoUtils.LINE_SEPARATOR); 
            	if(cssPath != null){
            		writer.write("<link rel=\"stylesheet\" href=\"");
            		writer.write(cssPath); 
            		writer.write("\"/>"); 
            	}else{
            		writer.write(DEFAULT_INSIDE_PAGE_CSS_DECLARATION); 
            	} 
            	writer.write(HTML_HEAD_END);
        } catch (IOException ioExc) {
        	throw new RuntimeException(ioExc);
        }
    }    
    
    /**
     * closes the output
     */
    public void close() {
        try {
            getOutputWriter().write("</table>");
            if(isStandalonePage){
            	getOutputWriter().write(HTML_END);
            }
            super.close();//this closes the writer
        } catch (IOException ioExc) {
        	throw new RuntimeException(ioExc);
        }
    }
    
    public void closeTable() {
        try {
            getOutputWriter().write("</table>");
            super.close();//this closes the writer
        } catch (IOException ioExc) {
        	throw new RuntimeException(ioExc);
        }
    }    
    
    public void closePageOnly() {
        try {
            getOutputWriter().write(HTML_END);
            super.close();//this closes the writer
        } catch (IOException ioExc) {
        	throw new RuntimeException(ioExc);
        }
    }    
    
    /**
     * start report row
     */
    public void startRow(RowProps rowProperties) {
        try {
            super.startRow(rowProperties);
            StringBuilder buffer = new StringBuilder();
            if(ReportContent.COLUMN_HEADER.equals(rowProperties.getContent())){
            	buffer.append("<tr style=\"background-color: gray;color: white;\">");
            }else{
            	buffer.append("<tr class=\"");
            	if(rowProperties.isTotal()){
            		buffer.append(" warning ");
            	}
                if(getRowCount() %2 ==0){
                    buffer.append("even");
                }else{
                    buffer.append("odd");
                }
                buffer.append("\" >");
            }
            getOutputWriter().write(buffer.toString());            
        } catch (IOException ioExc) {
        	throw new ReportOutputException(ioExc);
        }
    }
    
   /**
    * end row
    */
    public void endRow(RowProps rowProperties){
        try{
            super.endRow(rowProperties);
            getOutputWriter().write("</tr>");
            getOutputWriter().write(ReportIoUtils.LINE_SEPARATOR);
        }catch (IOException ioExc) {
        	throw new ReportOutputException(ioExc);
        }
    }
    
    /**
     * output cell contents
     */
    public void output(CellProps cellProps) {
        String toWrite = purifyData(cellProps.getValue());
        try {
            StringBuilder strContent = new StringBuilder("<td style=\"");
            strContent.append("text-align: ");
            strContent.append(cellProps.getHorizontalAlign().toString());
            strContent.append(";\" align =\"");
            strContent.append(cellProps.getHorizontalAlign().toString()); 
            strContent.append("\" colspan=\"");
            strContent.append(cellProps.getColspan());            
            strContent.append("\" rowspan=\"1\" >");
            strContent.append(toWrite);
            strContent.append("</td>");
            getOutputWriter().write(strContent.toString());
        } catch (IOException ioExc) {
            throw new ReportOutputException(ioExc);
        }
    }
    
    
    /**
     * checks null & other impossible to print values
     */
    protected String purifyData(Object val) {
    	String result = null;
    	if(val != null){
    		if(IReportOutput.WHITESPACE.equals(val)){
    			result = "&nbsp;";
    		}else{
    			result = val.toString();
    		}
    	}else{
    		result = getNullsReplacement();
    	}
        return result;
    }
    
    /**
     * getter for css file path
     * @return
     */
	public String getCssPath() {
		return cssPath;
	}
	
	/**
	 * setter for css file path
	 * @param cssPath
	 */
	public void setCssPath(String cssPath) {
		this.cssPath = cssPath;
	}
	
	
	public void write(Object string) throws IOException{
		getOutputWriter().write(purifyData(string));		
	}
	
	public void setStandalonePage(boolean isStandalonePage) {
		this.isStandalonePage = isStandalonePage;
	}
}