/*
 * Created on Sep 1, 2006
 * Author : dragos balan 
 */
package net.sf.reportengine.out;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import net.sf.reportengine.util.ReportIoUtils;

import org.apache.log4j.Logger;


/**
 * XSLT based report output is created in two steps: <br/>
 * 
 * 1. Create a temporary XML report (STax) <br/> 
 * 2. Transform the temporary xml when closing the report
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3
 */
public class XsltOutput extends AbstractCharacterOutput {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = Logger.getLogger(XsltOutput.class);
	
	/**
	 * class path of the default transformer
	 */
	private static final String DEFAULT_XSLT_PATH = "net/sf/reportengine/xslt/defaultTemplate.xslt"; 
	
    /**
     * 
     */
    private Reader xsltReader;
    
    /**
     * 
     */
    private StaxReportOutput staxReportOutput = new StaxReportOutput();
    
    /**
     * 
     */
    private String tempXmlFilePath;
    
    /**
     * output into memory ( using a StringWriter)
     */
    public XsltOutput(){}
    
    
    /**
     * 
     * @param outFilePath
     */
    public XsltOutput(String outFileName){
    	super(outFileName); 
    }
    
    /**
     * 
     * @param filePath
     * @param xsltReader
     */
    public XsltOutput(String outFilePath, Reader xsltReader){
    	super(outFilePath); 
    	setXsltReader(xsltReader); 
    }
    
    /**
     * 
     * @param outputWriter
     */
    public XsltOutput(Writer outputWriter){
    	super(outputWriter);
    }
    
    /**
     * 
     * @param outputWriter
     * @param xsltReader
     */
    public XsltOutput(Writer outputWriter, Reader xsltReader){
    	super(outputWriter); 
    	setXsltReader(xsltReader); 
    }
    
    
    /**
     * opens the writers
     */
    public void open(){
    	super.open(); //prepare the xslt writer
    	try{
	    	if(xsltReader == null){
	    		if(LOGGER.isDebugEnabled())LOGGER.debug("No xslt reader found ... creating default xslt reader from classpath"); 
	    		setXsltReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(DEFAULT_XSLT_PATH)));
	    	}
	    	if(tempXmlFilePath == null){
	    		File tempXmlFile = File.createTempFile("report", ".tmp");
	    		if(LOGGER.isInfoEnabled())
	    			LOGGER.info("creating temporary xml file "+tempXmlFile.getAbsolutePath());
	    		setTempXmlFilePath(tempXmlFile.getAbsolutePath());
	    	}
	    	staxReportOutput.setFilePath(getTempXmlFilePath());
	    	
	    	staxReportOutput.open();
    	}catch(IOException ioEx){
    		throw new ReportOutputException(ioEx); 
    	}
    }
    
    /**
     * 
     */
    public void close(){
    	try{
	    	staxReportOutput.close(); 
	    	transform();
	    	getXsltReader().close(); 
    	}catch(IOException ioExc){
    		throw new ReportOutputException(ioExc); 
    	}
    	super.close(); 
    }	
    
    /**
     * 
     */
    protected void transform(){
    	if(LOGGER.isDebugEnabled())LOGGER.debug("applying xslt tranformation to the temporary xml file");
    	try{
    		//get the temporary xml file created and transform it
    		Source xmlSource = constructXmlSourceFromTempFile(); 
    		Result result = new StreamResult(getOutputWriter());
    	
    		Transformer transformer = TransformerFactory.newInstance()
    			.newTransformer(new StreamSource(getXsltReader()));
    		transformer.transform(xmlSource, result);
    		
    	}catch(TransformerConfigurationException exc){
    		throw new ReportOutputException(exc);
    	}catch(TransformerException exc){
    		throw new ReportOutputException(exc);
    	}
    }
    
    /**
     * 
     */
    public void startRow(RowProps rowProperties){
    	staxReportOutput.startRow(rowProperties);
    }
    
    /**
     * 
     */
    public void endRow(){
    	staxReportOutput.endRow();
    }
    
    /**
     * 
     */
    public void output(CellProps cellProps){
    	staxReportOutput.output(cellProps);
    }
    
    /**
     * 
     * @return
     */
    public Reader getXsltReader() {
        return xsltReader;
    }
    
    /**
     * 
     * @param xsltInputStream
     */
    public void setXsltReader(Reader xsltReader) {
        this.xsltReader = xsltReader;
    }
    
    
	/**
	 * @return the tempXmlFile
	 */
	public String getTempXmlFilePath() {
		return tempXmlFilePath;
	}

	/**
	 * @param tempXmlFile the tempXmlFile to set
	 */
	public void setTempXmlFilePath(String tempXmlPath) {
		this.tempXmlFilePath = tempXmlPath;
	}
	
	/**
	 * 
	 * @return
	 */
	protected Source constructXmlSourceFromTempFile(){
		Source result = null; 
		if(tempXmlFilePath != null){
			result = new StreamSource(ReportIoUtils.createReaderFromPath(tempXmlFilePath));
		}else{
			LOGGER.warn("the construction of the xml source failed. No temporary xml file path was found"); 
		}
		return result; 
	}
}
