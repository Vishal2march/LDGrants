package mypackage;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DbName 
{
  String dbName;
  String displayName;
  public boolean production;
  

  public DbName()
  {
    Connection cn = null;
    try{
    
        cn = openCon();
        String URL = cn.getMetaData().getURL();
        //Are we connected to the production database?
        if(URL.indexOf("pref")>=0)
        {
          this.production=true;
          this.dbName="PREF";
          this.displayName="Production";
        }
        else if(URL.indexOf("PREF")>=0){
          this.production=true;
          this.dbName="PREF";
          this.displayName="Production";
        }
        else
        {
          //Not connection to production
          this.production=false;
          if(URL.indexOf("tref")>=0)  
          {
            this.dbName="TEST";
            this.displayName="Test";
          }
          else
          {
            this.dbName="Other";
            this.displayName="Other";
          }
        }
        cn.close();
    }catch(Exception e){
      System.err.println(e.getMessage());
    }
    finally{
      Close(cn);
    }
  }

  public String getDbName()
  {
    return dbName;
  }

  public void setDbName(String dbName)
  {
    this.dbName = dbName;
  }
  
  
  private static Connection openCon() {
    DataSource ds = null;
    Connection cn = null;
    try
      {
      //look up the datasource and return a connection
      Context namingContext = new InitialContext();
      ds = (DataSource) namingContext.lookup("jdbc/LDGrantsDS");
      cn = ds.getConnection();
      }
    catch(Exception e)
      {
        System.err.println(e.getMessage());
      }
    return cn;
  }

  public String getDisplayName()
  {
    return displayName;
  }

  public void setDisplayName(String displayName)
  {
    this.displayName = displayName;
  }



  public boolean isProduction()
  {
    return production;
  }

  public void setProduction(boolean production)
  {
    this.production = production;
  }
  
  public void Close(Connection conn)
  {
    try
    {
      if(conn != null)
      {
        conn.close();
      }
    }catch(Exception e){}
  }
}