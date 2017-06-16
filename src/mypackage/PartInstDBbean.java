/******************************************************************************
 * @author  : Stefanie Husak
 * @Version : 2.0
 *
 * Development Environment        :  JDeveloper 10g
 * Name of the file               :  PartInstDBbean.java
 * Creation/Modification History  :
 *
 * SH   7/26/07      Created
 *
 * Description
 * This class will store/retrieve the information about participating institutions involved 
 * in CO grants.  It has methods to insert, update, and delete a participating institution
 * from the co_institutions table.
 *****************************************************************************/
package mypackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.sql.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.naming.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.*;

public class PartInstDBbean 
{
  public PartInstDBbean()
  {
  }
  
  Connection conn;
  PreparedStatement ps;
  ResultSet rs;
    
  private static Connection initializeConn() throws Exception
  {
      Context namingContext = new InitialContext();
      DataSource ds = (DataSource) namingContext.lookup("jdbc/LDGrantsDS");
      return ds.getConnection();   
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
  
  public void Close(PreparedStatement stmt)
  {
    try
    {
      if(stmt != null)
      {
        stmt.close();
      }
    }catch(Exception e){}
  }

 public void Close(ResultSet rs)
  {
    try
    {
      if(rs != null)
      {
        rs.close();
      }
    }catch(Exception e){}
  }


  /**
   * This method only used to get coordinated participating libraries
   * @return 
   * @param request
   */
  public Vector getPartLibrariesCoord(HttpServletRequest request)
  {
    HttpSession sess = request.getSession();
    String grantid = (String)sess.getAttribute("grantid");
    int count = 1;
    Vector results = new Vector();
    
    try {
      conn = initializeConn(); 
             
      ps = conn.prepareStatement("select co_institutions.id, gra_id, co_institutions.inst_id, is_lead, "+
      " initcap(popular_name) as popular_name from CO_INSTITUTIONS left join SED_INSTITUTIONS "+
      " on CO_INSTITUTIONS.INST_ID=SED_INSTITUTIONS.INST_ID where IS_LEAD='N' and GRA_ID=?");
     
      ps.setString(1, grantid);
      rs = ps.executeQuery();
      
      while(rs.next())
      {
        PartInstBean pb = new PartInstBean();
        pb.setGrantid(rs.getLong("GRA_ID"));
        pb.setInstid(rs.getLong("INST_ID"));
        pb.setIslead(rs.getString("IS_LEAD"));
        pb.setId(rs.getLong("ID"));
        pb.setInstName(rs.getString("POPULAR_NAME"));        
        
        sess.setAttribute("instBean"+count, pb);
        results.add(pb);
        count++;
      }  
      
    } catch (Exception ex){
        System.err.println("error getPartLibrariesCoord()  " + ex.toString());
    }
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    } 
    return results;
  }
    
  
  /**
   * This method only used for print-view participating libraries. 
   * @return 
   * @param grantid
   */
  public Vector getParticipants(long grantid)
  {
    Vector results = new Vector();
    
    try {
      conn = initializeConn(); 
             
      ps = conn.prepareStatement("select co_institutions.id, gra_id, co_institutions.inst_id, is_lead, "+
      " initcap(popular_name) as popular_name from CO_INSTITUTIONS left join SED_INSTITUTIONS "+
      " on CO_INSTITUTIONS.INST_ID=SED_INSTITUTIONS.INST_ID where IS_LEAD='N' and GRA_ID=?");
     
      ps.setLong(1, grantid);
      rs = ps.executeQuery();
      
      while(rs.next())
      {
        PartInstBean pb = new PartInstBean();
        pb.setGrantid(rs.getLong("GRA_ID"));
        pb.setInstid(rs.getLong("INST_ID"));
        pb.setIslead(rs.getString("IS_LEAD"));
        pb.setId(rs.getLong("ID"));
        pb.setInstName(rs.getString("POPULAR_NAME"));       
        results.add(pb);
      }  
      
    } catch (Exception ex){
        System.err.println("error getParticipants()  " + ex.toString());
    }
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    } 
    return results;  
  }


  /**
   * Insert a participating institution in CO_INSTITUTIONS table
   */
  public int insertPartLib(long grantid, UserBean userb, long instid)
  {
    int outcome = 0;
   
    try {
      conn = initializeConn(); 
      
      ps = conn.prepareStatement("insert into CO_INSTITUTIONS (ID, IS_LEAD, INST_ID, GRA_ID, "+
        " CREATED_BY, DATE_CREATED) values (co_inst_seq.nextval, 'N', ?,?,?,sysdate)");
      ps.setLong(1, instid );
      ps.setLong(2, grantid);
      ps.setString(3, userb.getUserid());
      outcome = ps.executeUpdate();
      
    } catch (Exception ex){
        System.err.println("error insertPartLib()  " + ex.toString());
    }
    finally
    {
      Close(ps);
      Close(conn);
    }    
    return outcome;
  }
  
  

  public int updatePartLib(UserBean userb, long instid, long id)
  {
    int outcome = 0;
   
    try {
      conn = initializeConn(); 
      
      ps = conn.prepareStatement("update CO_INSTITUTIONS set INST_ID=?, MODIFIED_BY=?, "+
        " DATE_MODIFIED=sysdate where ID=?");
      ps.setLong(1, instid);
      ps.setString(2, userb.getUserid());
      ps.setLong(3, id);
      outcome = ps.executeUpdate();
      
    } catch (Exception ex){
        System.err.println("error updatePartLib()  " + ex.toString());
    }
    finally
    {
      Close(ps);
      Close(conn);
    }    
    return outcome;
  }
  
  
 /**
     * Delete participant from co_institutions table. Used for co/di/fl/al/lg.
     * Also need to delete any participant eligibility records for lg.
     * @param id
     * @return
     */
  public int deletePartLib(long id)
  {
    int outcome = 0;   
    try {
      conn = initializeConn(); 
      
      ps = conn.prepareStatement("delete from grant_eligibles where coi_id=?");
      ps.setLong(1, id );
      outcome = ps.executeUpdate();
        
      ps = conn.prepareStatement("delete from CO_INSTITUTIONS where ID=?");
      ps.setLong(1, id );
      outcome = ps.executeUpdate();
      
    } catch (Exception ex){
        System.err.println("error deletePartLib()  " + ex.toString());
    }
    finally
    {
      Close(ps);
      Close(conn);
    }    
    return outcome;
  }

  public Vector searchForPartInst(String searchName)
  {
    Vector results = new Vector();
    
    try {
      conn = initializeConn(); 
      
      ps = conn.prepareStatement("select sed_institutions.inst_id, initcap(POPULAR_NAME) as popular_name, "+
      " initcap(addr_line1) as addr_line1, initcap(city) as city, state_code from sed_institutions, "+
      " sed_addresses where sed_institutions.inst_id = sed_addresses.inst_id and addr_type_code =1 and "+
      " inst_successor_id is null and popular_name like upper( ? ) ");
      
      ps.setString(1, "%" +searchName + "%");
      rs = ps.executeQuery();      
      while(rs.next())
      {
        PartInstBean pb = new PartInstBean();
        pb.setInstid(rs.getLong("inst_id"));
        pb.setInstName(rs.getString("popular_name"));
        pb.setAddress(rs.getString("addr_line1"));
        pb.setCity(rs.getString("city"));
        pb.setState(rs.getString("state_code"));
        results.add(pb);
      }      
      
    } catch (Exception ex){
        System.err.println("error searchForPartInst()  " + ex.toString());
    }
    finally{
      Close(rs);
      Close(ps);
      Close(conn);
    }  
    return results;
  }


  /**
   * This method will get all participating inst name,address info (for di/fl/al/lg)
   * @return 
   * @param grantid
   */
  public Vector getPartInstAddressInfo(long grantid)
  {
    Vector results = new Vector();
    
    try {
      conn = initializeConn(); 
      
      ps = conn.prepareStatement("select co_institutions.id, co_institutions.inst_id, gra_id, "+
      " initcap(POPULAR_NAME) as popular_name, initcap(addr_line1) as addr_line1, initcap(city) as city, "+
      " state_code from CO_INSTITUTIONS, SED_INSTITUTIONS left join sed_addresses on sed_institutions.inst_id = sed_addresses.inst_id where "+
      " CO_INSTITUTIONS.INST_ID=SED_INSTITUTIONS.INST_ID and IS_LEAD='N' and addr_type_code=1 and GRA_ID=?");   
      ps.setLong(1, grantid);
      rs = ps.executeQuery();
      
      while(rs.next())
      {
        PartInstBean pb = new PartInstBean();
        pb.setGrantid(rs.getLong("GRA_ID"));
        pb.setInstid(rs.getLong("INST_ID"));
        pb.setId(rs.getLong("ID"));
        pb.setInstName(rs.getString("popular_name"));  
        pb.setAddress(rs.getString("addr_line1"));
        pb.setCity(rs.getString("city"));
        pb.setState(rs.getString("state_code"));
                
        results.add(pb);
      }  
      
    } catch (Exception ex){
        System.err.println("error getPartInstAddressInfo()  " + ex.toString());
    }
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    } 
    return results;
  }  
  
  /**
   * This method will get the participants eligibility from grant_eligibles table.
   * Used for lgrmif add/update participants page.
   */  
  public PartCollectionBean getEligibleForParts(Vector allpartinst)
  {
    PartCollectionBean pcb = new PartCollectionBean();
    Format yearformat = new SimpleDateFormat("yyyy");    
    
    try {
      conn = initializeConn();       
      ps = conn.prepareStatement("select * from grant_eligibles where coi_id=?");   
      
      for(int i=0; i<allpartinst.size(); i++)
      {
          PartInstBean p = (PartInstBean) allpartinst.get(i);
          
          ps.setLong(1, p.getId());
          rs = ps.executeQuery();          
          while(rs.next())
          {
            int eltype = rs.getInt("inse_id");
        
            String theDate = null;
            if(rs.getDate("eligible_date")!=null &&  !rs.getDate("eligible_date").equals(""))
              theDate = yearformat.format(rs.getDate("eligible_date"));
                
            switch(eltype)
            {                   
              case 6://for lgrmif only - check logic closely before changing again 8/26/09
                p.setRmodate(theDate);
                if(theDate!=null)
                    p.setRmoappointed(0);
                else
                    p.setRmoappointed(1);//user choose No
                break;
              case 8://lgrmif only - FOR RMO APPOINTED SEE CASE 6 AND 8
                p.setRmodate(theDate);
                p.setRmoappointed(2);//user choose N/A
                break;
                
                
              case 7://for lgrmif only - check logic closely before changing again 8/26/09
                p.setScheduledate(theDate);
                if(theDate!=null)
                    p.setScheduleadopted(0);
                else
                    p.setScheduleadopted(1);//user choose No
                break;
              case 9://for lgrmif only - FOR SCHEDULE SEE CASE 7 AND 9
                p.setScheduledate(theDate);
                p.setScheduleadopted(2);//user choose N/A
                break;
            }
          }  
      }
      pcb.setAllPartInst(allpartinst);
      
    } catch (Exception ex){
        System.err.println("error getEligibleForParts()  " + ex.toString());
    }
    finally{
      Close(ps);
      Close(conn);
      Close(rs);
    } 
    return pcb;
  }    
    
  
    /**
     * Method will get all doris agencies for lg applicant drop down on coversheet
     * @return 
     * @param request
     */
    public ArrayList loadDorisFromFile(HttpServletRequest request)
    {  
      ArrayList allagencies = new ArrayList(); 
      
      try{            
          InputStream is=request.getSession().getServletContext().getResourceAsStream("/docs/dorisagencies.txt") ;         
          BufferedReader bis = null; 
          if(is !=null) {
            bis = new BufferedReader(new InputStreamReader(is));            
            
            String line="";
            while((line=bis.readLine())!=null)
            {
             DropDownListBean dd = new DropDownListBean();
             dd.setDescription(line);
             allagencies.add(dd);
            }
          }      
          if(is!=null)
            is.close();
          if(bis!=null)
            bis.close();   
                                      
         
        /*Scanner input = null;                
        input = new Scanner(new BufferedReader(new FileReader("/docs/dorisagencies.txt")));
        
          // keep looping as long as there's more data to read
          while ( input.hasNext() ) {
              DropDownListBean dd = new DropDownListBean();
              dd.setDescription(input.next());
              allagencies.add(dd);
          }  */
        //System.out.println("num agencies "+allagencies.size() );        
     
      }catch(Exception e){
        System.err.println("error loadDorisFromFile() " + e.getMessage().toString());
      }  
      return allagencies;
    }
}