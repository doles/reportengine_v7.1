/*
 * Created on 12.02.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.in;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import net.sf.reportengine.util.ReportIoUtils;

import org.apache.log4j.Logger;

/**
 * <p>
 * Report input implementation for streams (files, urls, etc) backed by a java.io.BufferedReader<br/> 
 * By default this implementation expects a comma (,) as data separator
 * but you can use the #setSeparator(String) method to specify whatever 
 * separator you want 
 * </p>
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.2
 */
public class TextInput extends AbstractReportInput{
    
	/**
	 * the message for cases when no writer is set
	 */
	protected static final String NO_WRITER_SET_ERROR_MESSAGE = "The input cannot be opened. Please set a writer or a filePath to your input.";

	/**
	 * the LOGGER
	 */
	private static final Logger LOGGER = Logger.getLogger(TextInput.class);
    
    /**
     * the default separator
     */
    private static final String DEFAULT_SEPARATOR = ",";
	
	/**
     * the reader of the file 
     */
	private BufferedReader reader = null;
    
    /**
     * the separator (default value is <code>comma separator</code>
     */
	private String separator = DEFAULT_SEPARATOR;
    
	/**
     * the columns count
     */
    private int columnsCount = Integer.MIN_VALUE;
    
    /**
     * this field represents the number of lines that should be skipped 
     * from the beginning of the stream
     */
    private int linesToBeSkipped = 0; 
    

	/**
     * the raw data (unparsed and un-split in columns)
     */
    private String nextRawDataRow;
	
    /**
     * empty text imput constructor. 
     * Please use one of the {@link #setInputReader(Reader)}
     */
    public TextInput(){
    	
    }
    
    /**
     * creates a report input for the given file with comma as separator in the default encoding
     * 
     * @param filePath      the path name of the file containing data
     * @throws FileNotFoundException
     */
    public TextInput(String filePath){
		this(filePath, DEFAULT_SEPARATOR);
	}
	
    /**
     * Creates a report input from the given fileName (in the default encoding) using the separator
     * 
     * @param filePath	path and filename
     * @param separator	data-separator
     */
    public TextInput(String filePath, String separator){
    	this(filePath, separator, ReportIoUtils.UTF8_ENCODING);
    }
    
    /**
     * Creates an input for the given fileName using the provided separator.
     * When reading the specified encoding is used.
     * 
     * @param filePath		the path of the file containing data
     * @param separator     the separator used to identify data/column
     * @param encoding 		the encoding used when reading the file
     * 
     * @throws FileNotFoundException 
     */
	public TextInput(String filePath, String separator, String encoding){
		setInputReader(ReportIoUtils.createReaderFromPath(filePath, encoding));
		setSeparator(separator);
	}
	
	
	/**
	 * creates a report input for the given InputStream using the specified encoding and data-separator
	 * 
	 * @param is			the input stream
	 * @param separator		data separator
	 * @param encoding		the encoding 
	 * @throws UnsupportedEncodingException
	 */
	public TextInput(InputStream is, String separator, String encoding){
		try{
			setInputReader(new InputStreamReader(is, encoding));
			setSeparator(separator); 
		}catch(UnsupportedEncodingException uee){
			throw new ReportInputException(uee); 
		}
	}
	
	/**
	 * creates a report input for the given InputStream using the utf-8 encoding 
	 * and the specified data-separator
	 * 
	 * @param is			the input stream
	 * @param separator		data-separator
	 * @throws UnsupportedEncodingException
	 */
	public TextInput(InputStream is, String separator){
		this(is, separator, ReportIoUtils.UTF8_ENCODING);
	}
	
	/**
	 * creates a report input for the given input stream using the utf-8 encoding 
	 * and comma as data-separator
	 * 
	 * @param is	the input stream
	 * @throws UnsupportedEncodingException
	 */
	public TextInput(InputStream is){
		this(is, DEFAULT_SEPARATOR);
	}
	
	/**
	 * creates a report-input based on the provided reader using comma as data-separator
	 * 
	 * @param inReader	the reader
	 */
	public TextInput(Reader inReader){
		this(inReader, DEFAULT_SEPARATOR);
	}
	
	/**
	 * creates a report-input based on the provided reader and using separator to distinguish between
	 * data/columns
	 * 
	 * @param inReader		the reader
	 * @param separator		the separator used to identify data/columns
	 */
	public TextInput(Reader inReader, String separator){
		setInputReader(inReader); 
		setSeparator(separator);
	}
	
	/**
     * prepares the reader for further usaga. Actually this implementation
     * already reads the first line in order to be prepared for calls to 
     * #hasMoreRows() and #nextRow()
     */
    public void open() {
    	super.open();
        try{
        	if(reader == null) throw new ReportInputException(NO_WRITER_SET_ERROR_MESSAGE); 
        	
        	//we need to read at least one row even if the 
        	// linesToBeSkipped is zero because after open() 
        	//we have to be able to respond to hasMoreRows() 
        	int skipped = 0;
        	while(skipped < linesToBeSkipped){
        		reader.readLine();
        		skipped++;
        	};
        	if(LOGGER.isDebugEnabled()) LOGGER.debug("skipped first "+skipped+" lines");
        	//read and keep the first real-row
        	nextRawDataRow = reader.readLine();
        	
            if(nextRawDataRow != null){
                columnsCount = new StringTokenizer(nextRawDataRow, separator).countTokens();
            }else{
            	LOGGER.warn("After skipping "+skipped+" lines the input doesn't have any more rows"); 
            }
        }catch(IOException ioExc){
            throw new ReportInputException("IO Error occurred when opening the TextInput", ioExc);
        }
    }
    
    
    /**
     * closes the input and releases all resources
     */
    public void close() {
        super.close();
        try{
            if(reader != null){
                reader.close();
                reader = null;
            }
        }catch(IOException exc){
            throw new ReportInputException(" An IO Error occured when closing the input reader !", exc);
        }
    }
	
	
	/**
	 * returns the column count
	 */
	public int getColumnsCount()  {
	    return columnsCount;
	}
	
   
   /**
    * returns the next row of data if any row available otherwise returns null<br>
    * You should combine this method with #hasMoreRows()
    * or you can iterate through the rows until null is returned<br/>
    * Example:
    * <pre>
    * while(input.hasMoreRows()){             
    *   Object[] dataRow = input.nextRow(); 
    *   //do something with data            
    * } 
    * </pre> 
    */
    public Object[] nextRow() throws ReportInputException {
    	Object[] result = null;
        try {
            //if read not performed  && read next row of data
            if(hasMoreRows()){
            	ArrayList<Object> tempDataRow = new ArrayList<Object>();
                StringTokenizer strTokenizer = new StringTokenizer(nextRawDataRow, separator);
                
                while(strTokenizer.hasMoreTokens()){
                    tempDataRow.add(strTokenizer.nextToken());
                };
       
                assert tempDataRow.size() == columnsCount : 
                       " Normally each row should have the same length !";
                
                
                //now we read the next raw row
                nextRawDataRow = reader.readLine();
                result = tempDataRow.toArray(new Object[tempDataRow.size()]);
            }
        } catch (IOException e) {
            throw new ReportInputException("An IO Error occured !",e);
        }
        return result; 
    }
    
    
    /**
     * returns true if there are more rows to read otherwise false
     */
    public boolean hasMoreRows(){
        return nextRawDataRow != null;
    }
    
    /**
     * getter for separator
     * @return the 
     */
    public String getSeparator() {
        return separator;
    }

    /**
     * registers the data-separator which is used to differentiate among colums-data
     * @param separator     the data/column separator 
     */    
    public void setSeparator(String separator) {
        this.separator = separator;
    }
    
    /**
     * registers the provided input reader
     * @param reader
     */
    public void setInputReader(Reader reader){
    	this.reader = new BufferedReader(reader);
    }
    
    /**
     * returns the input reader
     * @return	a reader
     */
    public Reader getInputReader(){
    	return reader; 
    }
    
    /**
     * retrieves the number of lines to be skipped at the begining of the stream
     * @return
     */
    public int getLinesToBeSkipped() {
		return linesToBeSkipped;
	}

    /**
     * tells this input to skip the first specified lines. 
     * This is useful when your input already has some column headers
     * 
     * @param linesToBeSkipped
     */
	public void setLinesToBeSkipped(int skipFirstLines) {
		this.linesToBeSkipped = skipFirstLines;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		setInputReader(ReportIoUtils.createReaderFromPath(filePath));
	}
}
