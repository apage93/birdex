package javaxt.sql;
import java.sql.SQLException;
import javaxt.utils.Generator;

//******************************************************************************
//**  Connection Class
//******************************************************************************
/**
 *   Used to connect to a database via JDBC
 *
 ******************************************************************************/

public class Connection {


    private java.sql.Connection Conn = null;
    private long Speed;
    private Database database;


  //**************************************************************************
  //** Constructor
  //**************************************************************************
    public Connection(){}


  //**************************************************************************
  //** Constructor
  //**************************************************************************
    public Connection(java.sql.Connection conn){
        open(conn);
    }


  //**************************************************************************
  //** isOpen
  //**************************************************************************
  /** Used to determine whether the connection is open.
   */
    public boolean isOpen(){
        return !isClosed();
    }


  //**************************************************************************
  //** isClosed
  //**************************************************************************
  /** Used to determine whether the connection is closed.
   */
    public boolean isClosed(){
        try{
            return Conn.isClosed();
        }
        catch(Exception e){
            return true;
        }
    }


  //**************************************************************************
  //** getConnectionSpeed
  //**************************************************************************
  /** Used to retrieve the time it took to open the database connection
   * (in milliseconds)
   */
    public long getConnectionSpeed(){
        return Speed;
    }


  //**************************************************************************
  //** getConnection
  //**************************************************************************
  /** Used to retrieve the java.sql.Connection for this Connection
   */
    public java.sql.Connection getConnection(){
        return Conn;
    }



  //**************************************************************************
  //** Open
  //**************************************************************************
  /** Used to open a connection to the database.
   *
   *  @param ConnectionString A jdbc connection string/url. All connection URLs
   *  have the following form:
   *  <pre> jdbc:[dbVendor]://[dbName][propertyList] </pre>
   *
   *  Example:
   *  <pre> jdbc:derby://temp/my.db;user=admin;password=mypassword </pre>
   */
    public boolean open(String ConnectionString) throws SQLException {
        return open(new Database(ConnectionString));
    }


  //**************************************************************************
  //** Open
  //**************************************************************************
  /** Used to open a connection to the database.
   */
    public boolean open(Database database) throws SQLException {

        long startTime = System.currentTimeMillis();
        this.database = database;
        boolean isClosed = true;


      //Load JDBC Driver
        java.sql.Driver Driver = (java.sql.Driver) database.getDriver().load();


        //if (Conn!=null && Conn.isOpen()) Conn.close();


        String url = database.getURL();
        String username = database.getUserName();
        String password = database.getPassword();

        java.util.Properties properties = database.getProperties();
        if (properties==null) properties = new java.util.Properties();
        if (username!=null){
            properties.put("user", username);
            properties.put("password", password);
        }


        Conn = Driver.connect(url, properties);


        isClosed = Conn.isClosed();


        long endTime = System.currentTimeMillis();
        Speed = endTime-startTime;
        return isClosed;
    }


  //**************************************************************************
  //** open
  //**************************************************************************
  /** Used to open a connection to the database using a JDBC Connection. This
   *  is particularly useful when using JDBC connection pools.
   */
    public boolean open(java.sql.Connection conn){

        boolean isClosed = true;
        try{
            database = new Database(conn);
            Conn = conn;
            isClosed = Conn.isClosed();
        }
        catch(Exception e){
            //System.out.println("Failed");
            //System.out.println(database.getDriver().getVendor() + " ERROR: " + e.toString());
            isClosed = true;
        }

        Speed = 0;
        return isClosed;
    }


  //**************************************************************************
  //** close
  //**************************************************************************
  /** Used to close a connection to the database, freeing up connections
   */
    public void close(){
        try{Conn.close();}
        catch(Exception e){
            //e.printStackTrace();
        }
    }


  //**************************************************************************
  //** getRecordset
  //**************************************************************************
  /** Used to execute a SQL statement and returns a Recordset as an iterator.
   *  This simplifies using the Recordset object insofar as it eliminate the
   *  need to call the hasNext(), moveNext(), and close() methods. Instead,
   *  you can execute a query and iterate through records like this:
    <pre>
        Connection conn = db.getConnection();
        for (Recordset rs : conn.getRecordset("select distinct(name) from contacts")){
            System.out.println(rs.getValue(0));
        }
        conn.close();
    </pre>
   */
    public Generator<Recordset> getRecordset(String sql, boolean readOnly) throws SQLException {

        final Recordset rs = new Recordset();
        if (readOnly) rs.setFetchSize(1000);
        rs.open(sql, this, readOnly);


        return new Generator<Recordset>() {
            @Override
            public void run() {
                while (rs.hasNext()){
                    try{
                        this.yield(rs);
                    }
                    catch(InterruptedException e){
                        return;
                    }
                    rs.moveNext();
                }
                rs.close();
            }
        };
    }


  //**************************************************************************
  //** getRecordset
  //**************************************************************************
  /** Used to execute a SQL statement and returns a Recordset as an iterator.
   *  The Recordset is read-only. Use the other getRecordset() method for
   *  creating and updating records.
   */
    public Generator<Recordset> getRecordset(String sql) throws SQLException {
        return getRecordset(sql, true);
    }


  //**************************************************************************
  //** execute
  //**************************************************************************
  /** Used to execute a prepared sql statement (e.g. "delete from my_table").
   */
    public void execute(String sql) throws SQLException {
        java.sql.PreparedStatement preparedStmt = Conn.prepareStatement(sql);
        preparedStmt.execute();
        preparedStmt.close();
        preparedStmt = null;
    }


  //**************************************************************************
  //** commit
  //**************************************************************************
  /** Used to explicitly commit changes made to the database.
   */
    public void commit() throws SQLException {
        execute("COMMIT");
    }


  //**************************************************************************
  //** getDatabase
  //**************************************************************************
  /** Used to return database information associated with this connection.
   */
    public Database getDatabase(){
        return database;
    }
}