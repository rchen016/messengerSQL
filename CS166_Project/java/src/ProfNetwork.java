/*
 * Template JAVA User Interface
 * =============================
 *
 * Database Management Systems
 * Department of Computer Science &amp; Engineering
 * University of California - Riverside
 *
 * Target DBMS: 'Postgres'
 *
 */


import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.sql.Timestamp;
/**
 * This class defines a simple embedded SQL utility class that is designed to
 * work with PostgreSQL JDBC drivers.
 *
 */
public class ProfNetwork {

   // reference to physical database connection.
   private Connection _connection = null;

   // handling the keyboard inputs through a BufferedReader
   // This variable can be global for convenience.
   static BufferedReader in = new BufferedReader(
                                new InputStreamReader(System.in));

   /**
    * Creates a new instance of Messenger
    *
    * @param hostname the MySQL or PostgreSQL server hostname
    * @param database the name of the database
    * @param username the user name used to login to the database
    * @param password the user login password
    * @throws java.sql.SQLException when failed to make a connection.
    */
   public ProfNetwork (String dbname, String dbport, String user, String passwd) throws SQLException {

      System.out.print("Connecting to database...");
      try{
         // constructs the connection URL
         String url = "jdbc:postgresql://localhost:" + dbport + "/" + dbname;
         System.out.println ("Connection URL: " + url + "\n");

         // obtain a physical connection
         this._connection = DriverManager.getConnection(url, user, passwd);
         System.out.println("Done");
      }catch (Exception e){
         System.err.println("Error - Unable to Connect to Database: " + e.getMessage() );
         System.out.println("Make sure you started postgres on this machine");
         System.exit(-1);
      }//end catch
   }//end ProfNetwork

   /**
    * Method to execute an update SQL statement.  Update SQL instructions
    * includes CREATE, INSERT, UPDATE, DELETE, and DROP.
    *
    * @param sql the input SQL string
    * @throws java.sql.SQLException when update failed
    */
   public void executeUpdate (String sql) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the update instruction
      stmt.executeUpdate (sql);

      // close the instruction
      stmt.close ();
   }//end executeUpdate

   /**
    * Method to execute an input query SQL instruction (i.e. SELECT).  This
    * method issues the query to the DBMS and outputs the results to
    * standard out.
    *
    * @param query the input query string
    * @return the number of rows returned
    * @throws java.sql.SQLException when failed to execute the query
    */
   public int executeQueryAndPrintResult (String query) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the query instruction
      ResultSet rs = stmt.executeQuery (query);

      /*
       ** obtains the metadata object for the returned result set.  The metadata
       ** contains row and column info.
       */
      ResultSetMetaData rsmd = rs.getMetaData ();
      int numCol = rsmd.getColumnCount ();
      int rowCount = 0;

      // iterates through the result set and output them to standard out.
      boolean outputHeader = true;
      while (rs.next()){
	 if(outputHeader){
	    for(int i = 1; i <= numCol; i++){
		System.out.print(rsmd.getColumnName(i) + "\tb");
	    }
	    System.out.println();
	    outputHeader = false;
	 }
         for (int i=1; i<=numCol; ++i)
            System.out.print (rs.getString (i) + "\ta");
         System.out.println ();
         ++rowCount;
      }//end while
      stmt.close ();
      return rowCount;
   }//end executeQuery
   //////////////
   //////////////
   public int executeQueryAndPrintResultVALUE (String query) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();
	  String result;
	  int count = 0;
      // issues the query instruction
      ResultSet rs = stmt.executeQuery (query);

      /*
       ** obtains the metadata object for the returned result set.  The metadata
       ** contains row and column info.
       */
      ResultSetMetaData rsmd = rs.getMetaData ();
      int numCol = rsmd.getColumnCount ();
      int rowCount = 0;

      // iterates through the result set and output them to standard out.
      boolean outputHeader = true;
      while (rs.next()){
         result = rs.getString(1); 
		 count = Integer.parseInt(result);
         System.out.println ();
	 }
      	 stmt.close ();
		 return count;
      //return rowCount;
   }//end executeQuery
   
   
   ///////////////
   //////////////
   /**
    * Method to execute an input query SQL instruction (i.e. SELECT).  This
    * method issues the query to the DBMS and returns the results as
    * a list of records. Each record in turn is a list of attribute values
    *
    * @param query the input query string
    * @return the query result as a list of records
    * @throws java.sql.SQLException when failed to execute the query
    */
   public List<List<String>> executeQueryAndReturnResult (String query) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the query instruction
      ResultSet rs = stmt.executeQuery (query);

      /*
       ** obtains the metadata object for the returned result set.  The metadata
       ** contains row and column info.
       */
      ResultSetMetaData rsmd = rs.getMetaData ();
      int numCol = rsmd.getColumnCount ();
      int rowCount = 0;

      // iterates through the result set and saves the data returned by the query.
      boolean outputHeader = false;
      List<List<String>> result  = new ArrayList<List<String>>();
      while (rs.next()){
          List<String> record = new ArrayList<String>();
         for (int i=1; i<=numCol; ++i)
            record.add(rs.getString (i));
         result.add(record);
      }//end while
      stmt.close ();
      return result;
   }//end executeQueryAndReturnResult

   /**
    * Method to execute an input query SQL instruction (i.e. SELECT).  This
    * method issues the query to the DBMS and returns the number of results
    *
    * @param query the input query string
    * @return the number of rows returned
    * @throws java.sql.SQLException when failed to execute the query
    */
   public int executeQuery (String query) throws SQLException {
       // creates a statement object
       Statement stmt = this._connection.createStatement ();

       // issues the query instruction
       ResultSet rs = stmt.executeQuery (query);

       int rowCount = 0;

       // iterates through the result set and count nuber of results.
       if(rs.next()){
          rowCount++;
       }//end while
       stmt.close ();
       return rowCount;
   }

   /**
    * Method to fetch the last value from sequence. This
    * method issues the query to the DBMS and returns the current
    * value of sequence used for autogenerated keys
    *
    * @param sequence name of the DB sequence
    * @return current value of a sequence
    * @throws java.sql.SQLException when failed to execute the query
    */
   public int getCurrSeqVal(String sequence) throws SQLException {
	Statement stmt = this._connection.createStatement ();

	ResultSet rs = stmt.executeQuery (String.format("Select currval('%s')", sequence));
	if (rs.next())
		return rs.getInt(1);
	return -1;
   }

   /**
    * Method to close the physical connection if it is open.
    */
   public void cleanup(){
      try{
         if (this._connection != null){
            this._connection.close ();
         }//end if
      }catch (SQLException e){
         // ignored.
      }//end try
   }//end cleanup

   /**
    * The main execution method
    *
    * @param args the command line arguments this inclues the <mysql|pgsql> <login file>
    */
   public static void main (String[] args) {
      if (args.length != 3) {
         System.err.println (
            "Usage: " +
            "java [-classpath <classpath>] " +
            ProfNetwork.class.getName () +
            " <dbname> <port> <user>");
         return;
      }//end if

      Greeting();
      ProfNetwork esql = null;
      try{
         // use postgres JDBC driver.
         Class.forName ("org.postgresql.Driver").newInstance ();
         // instantiate the Messenger object and creates a physical
         // connection.
         String dbname = args[0];
         String dbport = args[1];
         String user = args[2];
         esql = new ProfNetwork (dbname, dbport, user, "");

         boolean keepon = true;
         while(keepon) {
            // These are sample SQL statements
            System.out.println("MAIN MENU");
            System.out.println("---------");
            System.out.println("1. Create user");
            System.out.println("2. Log in");
            System.out.println("9. < EXIT");
            String authorisedUser = null;
            switch (readChoice()){
               case 1: CreateUser(esql); break;
               case 2: authorisedUser = LogIn(esql); break;
               case 9: keepon = false; break;
               default : System.out.println("Unrecognized choice!"); break;
            }//end switch
            if (authorisedUser != null) {
              boolean usermenu = true;
              while(usermenu) {
                System.out.println("MAIN MENU");
                System.out.println("---------");
			    System.out.println("1. Find a Homie");
			    System.out.println("2. Update Password");
			    System.out.println("3. Write a new message");
			    System.out.println("4. Send Connection Request");
			    System.out.println("5. Respond to Connection Request");
			    System.out.println("6. View Friends");
				System.out.println("7. View Messages");
                System.out.println(".........................");
                System.out.println("9. Log out");
                switch (readChoice()){
		   		   case 1: friendFinder(esql); break;
			       case 2: updatePassword(esql); break;
				   case 3: writeMessage(esql, authorisedUser); break;
				   case 4: sendRequest(esql,authorisedUser); break;
				   case 5: resondToRequest(esql,authorisedUser); break;
				   case 6: viewFriends(esql,authorisedUser); break;
				   case 7: viewMessage(esql,authorisedUser); break;
                   case 9: usermenu = false; break;
                   default : System.out.println("Unrecognized choice!"); break;
                }
              }
            }
         }//end while
      }catch(Exception e) {
         System.err.println (e.getMessage ());
      }finally{
         // make sure to cleanup the created table and close the connection.
         try{
            if(esql != null) {
               System.out.print("Disconnecting from database...");
               esql.cleanup ();
               System.out.println("Done\n\nBye !");
            }//end if
         }catch (Exception e) {
            // ignored.
         }//end try
      }//end try
   }//end main
   
   /////////////////////////////////////////////////////////////////////////////////////
   /////////////////////////////////////////////////////////////////////////////////////
   //Project 3
   public static void friendFinder(ProfNetwork esql)
   {
	   try{
		   //Prompt For friend lookup
		   System.out.println("Who to lookup?");
		   String whichFriend = in.readLine();
		   String query = String.format("SELECT userId FROM USR WHERE name='%s'",whichFriend);
		   esql.executeQueryAndPrintResult(query);
	   }
	   catch(Exception e){
	   		System.out.println("Friend Finder Error");
	   }
   }
   
   public static void updatePassword(ProfNetwork esql)
   {
	   try{
		   //Prompt For Password
		   System.out.println("New Password?: ");
		   String new_password = in.readLine();
		   System.out.println(new_password);
		   //Update
	       String query = String.format("UPDATE USR SET password='%s'",new_password);
	  	   esql.executeUpdate(query);
	   }
  	   catch(Exception e){
  		 System.out.println("Invalid Password Update");
  	   }
   }
   
   public static void writeMessage(ProfNetwork esql, String userId)
   {
	   int msgId = 0;
	   String faketimestamp = "11/11/11 9:43";
	   String fakestatus = "delievered";
	   Calendar calendar = Calendar.getInstance();
	   Date currentTimestamp = new Timestamp(calendar.getTime().getTime());
	   System.out.println("Time Breh "+currentTimestamp);
	   int status = 0;
	   try{
		   
		   //get messageId
		   String query = String.format("SELECT COUNT(*) AS ORDERS FROM MESSAGE");
		   msgId = esql.executeQueryAndPrintResultVALUE(query);
		   msgId++;
		   System.out.println(msgId);
		   System.out.println("Who to write to? ");
		   String getName = in.readLine();
		   System.out.println("Message? ");
		   String getMessage = in.readLine();
		   String query2 = String.format("INSERT INTO MESSAGE VALUES('%d','%s','%s','%s','%s','%d','%s')",msgId,userId,getName,getMessage,currentTimestamp,status,fakestatus);
		   esql.executeUpdate(query2);
	   }catch(Exception e)
	   {
		   System.err.println(e.getMessage());
	   }
   }
   
   public static void sendRequest(ProfNetwork esql, String userId)
   {
	   try{
		   //Prompt For who to add
		   System.out.println("Who to Add?");
		   String getName = in.readLine();
		   String req = "Request";
		   //Make sure user exist in DB
		   String query = String.format("SELECT * FROM USR WHERE userId='%s'",getName);
		   int isValid = esql.executeQuery(query);
		  // System.out.println(userId+" "+getName);
		   if(isValid > 0)
		   {
			   String query2 = String.format("INSERT INTO CONNECTION_USR VALUES('%s','%s','%s')",getName,userId,req);
			   esql.executeUpdate(query2);
			   System.out.println("User Added!");
		   }
		   else
		   {
			   System.out.println("User NOT Added!");
		   }
	   }catch(Exception e){
		   System.err.println(e.getMessage());
	   }
   }
   
   public static void resondToRequest(ProfNetwork esql, String userId)
   {
	   String status = "Request";
	   String acceptStatus = "Accept";
	   String rejectStatus = "Reject";
	   try{
		   //Display requesters
		   String query = String.format("SELECT * FROM CONNECTION_USR WHERE userId='%s' AND status='%s'",userId,status);
		   esql.executeQueryAndPrintResult(query);
		   //Prompt who to add
		   System.out.println("Who do you want to accept/reject?");
		   String getName = in.readLine();
		   System.out.println("1 to Accept, 2 to Reject");
		   String choice = in.readLine();
		   //Make sure enter name requested
		   String query2 =  String.format("SELECT * FROM CONNECTION_USR WHERE connectionId='%s'",getName);
		   int isValid = esql.executeQuery(query2);
		   //Added them
		   if(isValid > 0)
		   {
			   System.out.println("Choice "+choice);
			   if(choice.equals("1"))
			   {
				   //Change Request to Accepted
				   String query3 = String.format("UPDATE CONNECTION_USR SET status='%s' WHERE connectionId='%s'",acceptStatus,getName);
				   esql.executeUpdate(query3);
				   System.out.println("Accepted!");
				   //Add Friend on Requester's list
				   String query4 = String.format("INSERT INTO CONNECTION_USR VALUES('%s','%s','%s')",getName,userId,"Accept");
				   esql.executeUpdate(query4);
			   }
			   else
			   {
				   //change status to Reject
				   String query5 = String.format("UPDATE CONNECTION_USR SET status='%s' WHERE connectionId='%s'",rejectStatus,getName);
				   esql.executeUpdate(query5);
				   //remove from connection table
				   String query6 = String.format("DELETE FROM CONNECTION_USR WHERE connectionId='%s'",getName);
				   esql.executeUpdate(query6);
				   System.out.println("REJECTED!");
			   }
		   }

		   else
		   {
			   System.out.println("Not on request list");
		   }
	   }catch(Exception e)
	   {
		   System.out.println("Error!");
		   System.err.println(e.getMessage());
	   }
   }
   
   public static void viewFriends(ProfNetwork esql, String userId)
   {
	   String status = "Accept";
	  // System.out.println(userId);
	   try{
		   String query = String.format("SELECT * FROM CONNECTION_USR WHERE userId='%s' AND status='%s'",userId,status);
		   esql.executeQueryAndPrintResult(query);
		   System.out.println("Who to view? ");
		   String getName = in.readLine();
		   String query2 = String.format("SELECT U.name, W.company, W.role FROM WORK_EXPR W, USR U WHERE W.userId=U.userId AND U.userId='%s'",getName);
		   esql.executeQueryAndPrintResult(query2);
		   String query3 = String.format("SELECT ED.instituitionName, ED.degree FROM EDUCATIONAL_DETAILS ED, USR U WHERE U.userId=ED.userId AND ED.userId='%s'",getName);
		   esql.executeQueryAndPrintResult(query3);
	   }catch(Exception e)
	   {
		   System.err.println(e.getMessage());
	   }
   }
  
   public static void viewMessage(ProfNetwork esql, String userId)
   {
	   int msgId = 200;
	   try{
		   String query = String.format("SELECT M.contents FROM MESSAGE M, USR U WHERE M.receiverId=U.userId AND U.userId='%s'",userId);
		   esql.executeQueryAndPrintResult(query);
	   }catch(Exception e)
	   {
		   System.err.println(e.getMessage());
	   }
   }
   /////////////////////////////////////////////////////////////////////////////////////
   /////////////////////////////////////////////////////////////////////////////////////
   
   
   
   
   
   public static void Greeting(){
      System.out.println(
         "\n\n*******************************************************\n" +
         "              User Interface      	               \n" +
         "*******************************************************\n");
   }//end Greeting

   /*
    * Reads the users choice given from the keyboard
    * @int
    **/
   public static int readChoice() {
      int input;
      // returns only if a correct value is given.
      do {
         System.out.print("Please make your choice: ");
         try { // read the integer, parse it and break.
            input = Integer.parseInt(in.readLine());
            break;
         }catch (Exception e) {
            System.out.println("Your input is invalid!");
            continue;
         }//end try
      }while (true);
      return input;
   }//end readChoice

   /*
    * Creates a new user with privided login, passowrd and phoneNum
    * An empty block and contact list would be generated and associated with a user
    **/
   public static void CreateUser(ProfNetwork esql){
      try{
         System.out.print("\tEnter user login: ");
         String login = in.readLine();
         System.out.print("\tEnter user password: ");
         String password = in.readLine();
         System.out.print("\tEnter user email: ");
         String email = in.readLine();
         System.out.print("\tEnter name: ");
         String name = in.readLine();
         System.out.print("\tEnter user dateOfBirth: ");
         String dob = in.readLine();

	 //Creating empty contact\block lists for a user
	 String query = String.format("INSERT INTO USR (userId, password, email, name, dateOfBirth) VALUES ('%s','%s','%s','%s','%s')", login, password, email, name,dob);

         esql.executeUpdate(query);
         System.out.println ("User successfully created!");
      }catch(Exception e){
		  System.out.println ("FAILED!");
         System.err.println (e.getMessage ());
      }
   }//end

   /*
    * Check log in credentials for an existing user
    * @return User login or null is the user does not exist
    **/
   public static String LogIn(ProfNetwork esql){
      try{
         System.out.print("\tEnter user login: ");
         String login = in.readLine();
         System.out.print("\tEnter user password: ");
         String password = in.readLine();

         String query = String.format("SELECT * FROM USR WHERE userId = '%s' AND password = '%s'", login, password);
         int userNum = esql.executeQuery(query);
	 if (userNum > 0)
		return login;
         return null;
      }catch(Exception e){
         System.err.println (e.getMessage ());
         return null;
      }
   }//end

// Rest of the functions definition go in here

}//end ProfNetwork
