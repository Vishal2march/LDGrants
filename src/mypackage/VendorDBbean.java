package mypackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;

import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.sql.DataSource;

public class VendorDBbean {
    public VendorDBbean() {
    }
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;  
    
    
    
    /**
     * This creates connection to oracle ldgrants db
     * @throws java.lang.Exception
     * @return 
     */
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
    
    
    public ArrayList getAllVendors() {
        ArrayList results = new ArrayList();
        
        try{            
            conn = initializeConn();
            ps = conn.prepareStatement("select * from VENDORS, ADDRESSES where vendors.id=addresses.ven_id order by name");
            rs = ps.executeQuery();
            
            while(rs.next())
            {
              VendorBean v = new VendorBean();
              v.setId(rs.getLong("ID"));
              v.setName(rs.getString("NAME"));
              v.setStatecontractnum(rs.getString("STATE_CONTRACT_NO"));
              v.setPreferredvendor(rs.getBoolean("PREFERRED_STATUS"));
              v.setAddress(rs.getString("ADDR_LINE1"));
              v.setCity(rs.getString("CITY"));
              v.setState(rs.getString("STATE"));
              v.setZipcode(rs.getString("ZIPCODE"));
              results.add(v);
            }
                 
        }
        catch(Exception e){
        System.err.println("error getAllVendors() " + e.getMessage().toString());
        }
        finally{
        Close(conn);
        Close(ps);
        Close(rs);
        }
        return results;
    }
    
    
    public ArrayList searchVendorByName(String vendorStr) {
        ArrayList results = new ArrayList();
        
        try{            
            conn = initializeConn();
            ps = conn.prepareStatement("select * from VENDORS, ADDRESSES where "+
            " vendors.id=addresses.ven_id and upper(name) like upper(?) order by name");
            ps.setString(1, "%" +vendorStr + "%");
            rs = ps.executeQuery();
            
            while(rs.next())
            {
              VendorBean v = new VendorBean();
              v.setId(rs.getLong("ID"));
              v.setName(rs.getString("NAME"));
              v.setStatecontractnum(rs.getString("STATE_CONTRACT_NO"));
              v.setPreferredvendor(rs.getBoolean("PREFERRED_STATUS"));
              v.setAddress(rs.getString("ADDR_LINE1"));
              v.setCity(rs.getString("CITY"));
              v.setState(rs.getString("STATE"));
              v.setZipcode(rs.getString("ZIPCODE"));
              results.add(v);
            }                 
            //System.out.println("num vens "+results.size());
        }
        catch(Exception e){
        System.err.println("error searchVendorByName() " + e.getMessage().toString());
        }
        finally{
        Close(conn);
        Close(ps);
        Close(rs);
        }
        return results;
    }
    
    
    
    public VendorBean getVendor(long vendorId) {
        
        VendorBean v = new VendorBean();
        try{            
            conn = initializeConn();
            ps = conn.prepareStatement("select * from VENDORS, ADDRESSES where vendors.id= "+
            " addresses.ven_id and vendors.id=?");
            ps.setLong(1, vendorId);
            rs = ps.executeQuery();
            
            while(rs.next())
            {              
              v.setId(rs.getLong("ID"));
              v.setName(rs.getString("NAME"));
              v.setStatecontractnum(rs.getString("STATE_CONTRACT_NO"));
              v.setPreferredvendor(rs.getBoolean("PREFERRED_STATUS"));
              v.setAddress(rs.getString("ADDR_LINE1"));
              v.setCity(rs.getString("CITY"));
              v.setState(rs.getString("STATE"));
              v.setZipcode(rs.getString("ZIPCODE"));
            }
                 
        }
        catch(Exception e){
        System.err.println("error getVendor() " + e.getMessage().toString());
        }
        finally{
        Close(conn);
        Close(ps);
        Close(rs);
        }
        return v;
    }
    
    public int insertVendor(VendorBean v, UserBean u) {
        int outcome=0;
        try{
        
          conn = initializeConn();
          ps = conn.prepareStatement("insert into vendors (id, name, state_contract_no, "+
          "preferred_status, date_created, created_by) values (ven_seq.nextval, ?, ?, ?, sysdate, ?)");
          ps.setString(1, v.getName());
          ps.setString(2, v.getStatecontractnum());
          ps.setBoolean(3, v.isPreferredvendor());
          ps.setString(4, u.getUserid());
          outcome = ps.executeUpdate();
          
          ps = conn.prepareStatement("insert into ADDRESSES (ID, ADDR_LINE1, CITY, STATE, ZIPCODE, " + 
          "IS_ACTIVE, DATE_CREATED, CREATED_BY, VEN_ID, ADDR_TYPE_CODE) values (ADDRESS_SEQ.nextval, " + 
          " ?,?,?,?,'Y',sysdate,?, ven_seq.currval,'2') ");
          ps.setString(1, v.getAddress());
          ps.setString(2, v.getCity());
          ps.setString(3, v.getState());
          ps.setString(4, v.getZipcode());
          ps.setString(5, u.getUserid());
          //ps.setLong(6, v.getId());
          outcome = ps.executeUpdate();        
            
        }catch(Exception e){
        System.err.println("error insertVendor() " + e.getMessage().toString());
        }
        finally{
        Close(conn);
        Close(ps);
        Close(rs);
        }
        return outcome;
    }
    
    
    
    public int updateVendor(VendorBean v, UserBean u) {
        int outcome=0;
        try{
        
          conn = initializeConn();
          ps = conn.prepareStatement("update vendors set name=?, state_contract_no=?, "+
          "preferred_status=?, date_modified=sysdate, modified_by=? where id=?");
          ps.setString(1, v.getName());
          ps.setString(2, v.getStatecontractnum());
          ps.setBoolean(3, v.isPreferredvendor());
          ps.setString(4, u.getUserid());
          ps.setLong(5, v.getId());
          outcome = ps.executeUpdate();
          
          ps = conn.prepareStatement("update ADDRESSES set ADDR_LINE1=?, CITY=?, STATE=?, ZIPCODE=?, " + 
          " DATE_MODIFIED=sysdate, MODIFIED_BY=? where VEN_ID=?");
          ps.setString(1, v.getAddress());
          ps.setString(2, v.getCity());
          ps.setString(3, v.getState());
          ps.setString(4, v.getZipcode());
          ps.setString(5, u.getUserid());
          ps.setLong(6, v.getId());
          outcome = ps.executeUpdate();        
            
        }catch(Exception e){
        System.err.println("error updateVendor() " + e.getMessage().toString());
        }
        finally{
        Close(conn);
        Close(ps);
        Close(rs);
        }
        return outcome;
    }
      
    
    public ArrayList getVendorQuotesForGrant(long grantid) {
        ArrayList results = new ArrayList();
        
        try{            
            conn = initializeConn();
            ps = conn.prepareStatement("select v.id, v.name, v.state_contract_no, "+
            " vq.id as vqid, vq.desc_item_service, vq.sole_source, vq.preferred_product, "+
            " vq.contract_num, vq.selected_quote, vq.price_quote "+
            " , vq.procurement_req "+
            " from VENDORS v, VENDOR_QUOTES vq where v.id=vq.ven_id and gra_id=?");
            ps.setLong(1, grantid);
            rs = ps.executeQuery();
            
            while(rs.next())
            {
              VendorBean v = new VendorBean();
              v.setId(rs.getLong("ID"));
              v.setName(rs.getString("NAME"));
              v.setStatecontractnum(rs.getString("STATE_CONTRACT_NO"));
              v.setVendorquoteId(rs.getLong("vqid"));
              v.setDescription(rs.getString("desc_item_service"));
              v.setSolesource(rs.getBoolean("sole_source"));
              v.setSelectedquote(rs.getBoolean("selected_quote"));
              v.setPricequote(rs.getString("price_quote"));
              v.setPreferredvendor(rs.getBoolean("preferred_product"));
              v.setContractNum(rs.getString("contract_num"));
              v.setProcurementreq(rs.getBoolean("procurement_req"));
              results.add(v);
            }                 
        }
        catch(Exception e){
        System.err.println("error getVendorQuotesForGrant() " + e.getMessage().toString());
        }
        finally{
        Close(conn);
        Close(ps);
        Close(rs);
        }
        return results;
    }  
    
    
    public int insertVendorQuote(VendorBean v, UserBean u) {
        int outcome=0;
        try{
                
        float price_float=0;
        if(v.getPricequote()!= null && !v.getPricequote().equals(""))
            price_float = parseDollarSign(v.getPricequote());
        
          conn = initializeConn();
         
        ps = conn.prepareStatement("insert into vendor_quotes (id, desc_item_service, "+
        " sole_source, selected_quote, price_quote, date_created, created_by, ven_id, gra_id, "+
        " preferred_product, procurement_req) values (vq_seq.nextval, ?, ?, ?, ?, sysdate, ?, ?,?,?,?)");
            
          ps.setString(1, v.getDescription());
          ps.setBoolean(2, v.isSolesource());
          ps.setBoolean(3, v.isSelectedquote());
          ps.setFloat(4, price_float);
          ps.setString(5, u.getUserid());
          ps.setLong(6, v.getId());
          ps.setLong(7, v.getGrantid());
          ps.setBoolean(8, v.isPreferredvendor());
          ps.setBoolean(9, v.isProcurementreq());
          outcome = ps.executeUpdate();
                                
        }catch(Exception e){
        System.err.println("error insertVendorQuote() " + e.getMessage().toString());
        }
        finally{
        Close(conn);
        Close(ps);
        Close(rs);
        }
        return outcome;
    }
    
    public int updateVendorQuote(VendorBean v, UserBean u) {
        int outcome=0;
        try{        
            float price_float=0;
            if(v.getPricequote()!= null && !v.getPricequote().equals(""))
              price_float = parseDollarSign(v.getPricequote());
              
          conn = initializeConn();
          
            ps = conn.prepareStatement("update vendor_quotes set desc_item_service=?, "+
            " sole_source=?, selected_quote=?, price_quote=?, date_modified=sysdate, modified_by=?, "+
            " ven_id=?, preferred_product=?, procurement_req=? where id=?");
          ps.setString(1, v.getDescription());
          ps.setBoolean(2, v.isSolesource());
          ps.setBoolean(3, v.isSelectedquote());
          ps.setFloat(4, price_float);
          ps.setString(5, u.getUserid());
          ps.setLong(6, v.getId());
          ps.setBoolean(7, v.isPreferredvendor());
          ps.setBoolean(8, v.isProcurementreq());
          ps.setLong(9, v.getVendorquoteId());
          outcome = ps.executeUpdate();
                                
        }catch(Exception e){
        System.err.println("error updateVendorQuote() " + e.getMessage().toString());
        }
        finally{
        Close(conn);
        Close(ps);
        Close(rs);
        }
        return outcome;
    }
    
    
    public float parseDollarSign(String amount)
    {
      float ans =0;
      try{
          char[] amtString = amount.toCharArray();//convert string to array of char
          Vector newAmtString = new Vector();//vector to hold new amount - just integers, no chars
           
          //loop on all items in the old string array
          for(int i=0; i<amtString.length; i++) 
          {
              //check if char is a number  - if yes then add to new vector
              if( Character.isDigit(amtString[i])  )
              {  
                  //cannot add char to vector - must wrap in a character object
                  newAmtString.add(new Character(amtString[i]) ); //it works!
              }
              else if( amtString[i]=='.')//keep any decimal points
              {
                newAmtString.add(new Character(amtString[i]));
              }
          }
          
          String tmpAmtString = "";
          //now convert all the numbers in the vector back to a string
          for(int i=0; i<newAmtString.size();i++)
          {
            tmpAmtString+= newAmtString.get(i);
          }
          
          //convert the string to a float
          ans = Float.parseFloat(tmpAmtString);
      }catch(Exception e){System.out.println("error parseDollarSign "+e.getMessage().toString());}
      return ans;
    }
    
    public int deleteVendorQuote(long vendorQuoteId) {
        int outcome=0;
        try{        
          conn = initializeConn();
          
          ps = conn.prepareStatement("delete from vendor_quotes where id=?");
          ps.setLong(1, vendorQuoteId);
          outcome = ps.executeUpdate();
          
        }catch(Exception e){
        System.err.println("error deleteVendorQuote() " + e.getMessage().toString());
        }
        finally{
        Close(conn);
        Close(ps);
        Close(rs);
        }
        return outcome;
    }
    
}
