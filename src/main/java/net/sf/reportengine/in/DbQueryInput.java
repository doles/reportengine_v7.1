/*
 * Created on 29.10.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.in;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.log4j.Logger;
/**
 * <p>
 * Report Input implementation for database queries <br/>
 * There are two ways of setting up this data provider :<br/>
 *      Use case 1: providing database url, driver class , user and password<br/> 
 *      Use case 2: providing a connection  
 * </p> 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.2
 */
public class DbQueryInput extends AbstractReportInput {
	
	/**
	 * the LOGGER
	 */
	private static final Logger LOGGER = Logger.getLogger(DbQueryInput.class);
    
    /**
     * the sql query
     */
    private String sqlStatement;
    
    /**
     * database user
     */
    private String dbUser;
    
    /**
     * database password
     */
    private String dbPassword;
    
    /**
     * database connection string 
     */
    private String dbConnString;
    
    /**
     * driver class
     */
    private String dbDriverClass;
    
    /**
     * database connection 
     */
    private Connection dbConnection;
    
    /**
     * columns count
     */
    private int columnsCount = Integer.MIN_VALUE;
    
    /**
     * the result set holding query resutls
     */
    private ResultSet resultSet;
    
    /**
     * result set meta data
     */
    private ResultSetMetaData metaData;
    
    /**
     * next row with data
     */
    private Object[] nextRow ;
    
    /**
     * hasMoreRows flag 
     */
    private boolean hasMoreRows = false;
    
    /**
     * Empty constructor for query data provider. Just using this simple constructor it's not enough <br/>
     * for calling open() and proceed in reading data. 
     * You have to add some more properties like dbUser, dbPassword, sqlStatement or you can 
     * provide your own connection and use the <source>QueryDataProvider(Connection conn)</source>
     */
    public DbQueryInput(){
        
    }
    
    /**
     * Use this constructor if you have a connection pooling system and you want to provide your own connection.
     * Besides the connection you have to provide a query statement using <source>setSqlStatement(String)</source>
     * @param conn      the connection provided 
     */
    public DbQueryInput(Connection conn){
        this.dbConnection = conn;
    }
    
    
    /**
     * this is the preffered way to construct this provider
     * @param dbConnString      the database connection string 
     * @param driverClass       the driver fully qualified class name
     * @param dbUser            the database user
     * @param dbPassword        database password
     */
    public DbQueryInput(  String dbConnString, 
                          String driverClass, 
                          String dbUser, 
                          String dbPassword){
        this.dbConnString = dbConnString;
        this.dbDriverClass = driverClass;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }
    
    /**
     * initializes the connection 
     *
     * @throws ReportInputException
     */
    private void initDBConnection() throws ReportInputException {
        try{
            Class.forName(dbDriverClass);
            dbConnection = DriverManager.getConnection(dbConnString,dbUser,dbPassword);
            dbConnection.setAutoCommit(false);
        }catch(ClassNotFoundException exc){
            throw new ReportInputException("The driver class ("+dbDriverClass+") cold not be intantiated !", exc);
        }catch(SQLException exc){
            throw new ReportInputException("SQL Connection error ! ", exc);
        }
    }
    
    /**
     * opens the Input in order to start reading from it
     */
    public void open() throws ReportInputException {
        super.open();
        readMetaData();
    }
    
    /**
     * Closes the input meaning : "the reading session it's done !"
     */
    public void close() throws ReportInputException {
        super.close();
        closeDbConnection();
    }
    
    /**
     * closes the result set and the database connection
     */
	private void closeDbConnection() {
		try {
            if(resultSet != null ){
                resultSet.close();
            }
            if(dbConnection != null && !dbConnection.isClosed()){
                dbConnection.close();
            }
        } catch (SQLException e) {
            throw new ReportInputException("SQL Error when closing Input ! See the cause !", e);            
        }
	}
    
    /**
     * returns the number of columns
     */
    public int getColumnsCount(){
        return columnsCount;
    }
    
    /**
     * returns the next row
     */
    public Object[] nextRow() throws ReportInputException {
        try {
            //a hidden verification if it's open
            if (hasMoreRows()) {
                //nextRow = new Object[columnsCount];
                for (int i = 0; i < columnsCount; i++) {
                    nextRow[i] = resultSet.getObject(i + 1);
                }
            }else{
                nextRow = null;
            }
            
            //moving the pointer to the next row 
            hasMoreRows = resultSet.next();
            
        } catch (SQLException e) {
        	closeDbConnection();
            throw new ReportInputException("An SQL Exception occured !", e);
        }
        return nextRow;
    }
    
    /**
     * returns true if there are more rows to read
     */
    public boolean hasMoreRows() {
        return hasMoreRows;
        
    }
    
    /**
     * @return Returns the dbConnString.
     */
    public String getDbConnString() {
        return dbConnString;
    }
    /**
     * @param dbConnString The dbConnString to set.
     */
    public void setDbConnString(String dbConnString) {
        this.dbConnString = dbConnString;
    }
    /**
     * @return Returns the dbDriverClass.
     */
    public String getDbDriverClass() {
        return dbDriverClass;
    }
    /**
     * @param dbDriverClass The dbDriverClass to set.
     */
    public void setDbDriverClass(String dbDriverClass) {
        this.dbDriverClass = dbDriverClass;
    }
    /**
     * @return Returns the dbPassword.
     */
    public String getDbPassword() {
        return dbPassword;
    }
    /**
     * @param dbPassword The dbPassword to set.
     */
    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }
    /**
     * @return Returns the dbUser.
     */
    public String getDbUser() {
        return dbUser;
    }
    /**
     * @param dbUser The dbUser to set.
     */
    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }
    /**
     * @return Returns the sqlStatement.
     */
    public String getSqlStatement() {
        return sqlStatement;
    }
    /**
     * @param sqlStatement The sqlStatement to set.
     */
    public void setSqlStatement(String sqlStatement) {
        this.sqlStatement = sqlStatement;
    }
    
    /**
     * database connection setter . 
     * Use this method to avoid the lazy database connection construction  
     * Please note that when using this method you are not required to 
     * provide also the dbUser, password or any other parameters 
     * @param conn  the connection 
     */
    public void setConnection(Connection conn){
        this.dbConnection = conn;
    }
    
    /**
     * getter for database connection
     * @return  the database connectio 
     */
    public Connection getConnection(){
        return this.dbConnection;
    }
    
    /**
     * reads the metadata from the result set for getting the 
     * column names, column types and other 
     * 
     * @throws ReportInputException
     */
    private void readMetaData() throws ReportInputException{
        try {
        	if(LOGGER.isDebugEnabled())LOGGER.debug("reading metadata"); 
            if(dbConnection == null){
                initDBConnection();
            }
            
            PreparedStatement stmt = dbConnection.prepareStatement(sqlStatement,   
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_READ_ONLY);
            
            //execute the statement : the return should be true 
            //if it's a select query or false if it's an update
            resultSet = stmt.executeQuery();
            metaData = resultSet.getMetaData();
            columnsCount = metaData.getColumnCount();

            int[] tempColType = new int[columnsCount];
            String[] tempColHeaders = new String[columnsCount];
            //looping through columns to get their type
            for(int i=0; i < columnsCount; i++){
                tempColType[i] = metaData.getColumnType(i+1);
                tempColHeaders[i] = metaData.getColumnLabel(i+1);
            }
            //setColumnTypes(tempColType);
            //setColumnHeaders(tempColHeaders);
                        
            hasMoreRows = resultSet.first();
            //hasMoreRows = resultSet.next();
            
            if(hasMoreRows){
                nextRow = new Object[columnsCount];
            }else{
                nextRow = null;
            }
            
            //stmt.close();
            
        } catch (SQLException e) {
           throw new ReportInputException("When reading Input data an SQL Error occured - see the cause !", e);
        } finally{
        	//scloseDbConnection();
        }
    }
}
